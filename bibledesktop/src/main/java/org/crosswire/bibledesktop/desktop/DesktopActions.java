/**
 * Distribution License:
 * BibleDesktop is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License, version 2 or later
 * as published by the Free Software Foundation. This program is distributed
 * in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even
 * the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * The License is available on the internet at:
 *       http://www.gnu.org/copyleft/gpl.html
 * or by writing to:
 *      Free Software Foundation, Inc.
 *      59 Temple Place - Suite 330
 *      Boston, MA 02111-1307, USA
 *
 * © CrossWire Bible Society, 2005 - 2016
 */
package org.crosswire.bibledesktop.desktop;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.util.Arrays;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.ToolTipManager;

import org.crosswire.bibledesktop.BDMsg;
import org.crosswire.bibledesktop.book.BibleViewPane;
import org.crosswire.bibledesktop.book.install.InternetWarning;
import org.crosswire.bibledesktop.book.install.SitesPane;
import org.crosswire.bibledesktop.display.BookDataDisplay;
import org.crosswire.bibledesktop.display.basic.SplitBookDataDisplay;
import org.crosswire.bibledesktop.display.basic.TabbedBookDataDisplay;
import org.crosswire.common.config.swing.ConfigEditorFactory;
import org.crosswire.common.swing.ActionFactory;
import org.crosswire.common.swing.CWOptionPane;
import org.crosswire.common.swing.desktop.LayoutPersistence;
import org.crosswire.common.swing.desktop.ViewVisitor;
import org.crosswire.common.util.CWProject;
import org.crosswire.common.util.ClassUtil;
import org.crosswire.common.util.FileUtil;
import org.crosswire.common.util.OSType;
import org.crosswire.common.util.ReflectionUtil;
import org.crosswire.common.util.Reporter;
import org.crosswire.jsword.passage.Key;
import org.crosswire.jsword.passage.NoSuchKeyException;
import org.crosswire.jsword.util.WebWarning;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * DesktopAction is nothing more than a holder of the behavior of the Desktop.
 * It could easily be member methods in that class. It is here simply to
 * simplify the Desktop class and minimize maintenance cost.
 * 
 * Previously each of the "do" methods was a separate class.
 * 
 * @see gnu.gpl.License for license details.
 * @author Joe Walker
 * @author DM Smith
 */
public class DesktopActions {
    /**
     * Create the actions for the desktop
     * 
     * @param desktop
     *            the desktop for which these actions apply
     */
    public DesktopActions(Desktop desktop) {
        this.desktop = desktop;
        actions = new ActionFactory(this);

        osxRegistered = macOSXRegistration();
        fixGnome();
    }

    /**
     * @return the desktop to which these actions apply
     */
    public Desktop getDesktop() {
        return desktop;
    }

    /**
     * @return the action factory
     */
    public ActionFactory getActions() {
        return actions;
    }

    /**
     * Register the application with Apple EAWT, which provides support for the
     * Application Menu, with About, Preferences (Options) and Quit (Exit).
     * 
     * @return true on success
     */
    public boolean macOSXRegistration() {
        if (OSType.MAC.equals(OSType.getOSType())) {
            try {
                Class<?> osxAdapter = ClassUtil.forName("org.crosswire.common.aqua.OSXAdapter");
                Object[] registerOSXArgs = {
                        actions, "About", "Options", "Exit"
                };
                ReflectionUtil.invoke(osxAdapter, osxAdapter, "registerMacOSXApplication", registerOSXArgs);

                // To call a method taking a type of boolean, the type has to
                // match but the object has to be wrapped
                Class<?>[] enablePrefTypes = {
                    boolean.class
                };
                Object[] enablePrefArgs = {
                    Boolean.TRUE
                };
                ReflectionUtil.invoke(osxAdapter, osxAdapter, "enablePrefs", enablePrefArgs, enablePrefTypes);
                return true;
            } catch (NoClassDefFoundError ex) {
                // This is thrown when EAWT or MacOSXadapter is not present.
                log.error("This version of Mac OS X does not support the Apple EAWT.  Application Menu handling has been disabled.", ex);
            } catch (ClassNotFoundException ex) {
                // Should not happen
                log.error("This version of Mac OS X does not support the Apple EAWT.  Application Menu handling has been disabled.", ex);
            } catch (NoSuchMethodException ex) {
                // Should not happen
                log.error("This version of Mac OS X does not support the Apple EAWT.  Application Menu handling has been disabled.", ex);
            } catch (IllegalAccessException ex) {
                // Should not happen
                log.error("This version of Mac OS X does not support the Apple EAWT.  Application Menu handling has been disabled.", ex);
            } catch (InvocationTargetException ex) {
                // Should not happen
                log.error("This version of Mac OS X does not support the Apple EAWT.  Application Menu handling has been disabled.", ex);
            }
        }
        return false;
    }

    public void fixGnome() {
        // BUGFIX: openJDK and Gnome 3 have an odd problem when JFrame is maximized.
        // The window location is not updated so mouse movements are relative to the location of the window
        // prior to the window being maximized.
        // This workaround is from netbeans and is adapted based upon problems with DESKTOP_SESSION having
        // other values than just "gnome_shell" for Gnome 3.
        // See http://hg.netbeans.org/core-main/rev/409566c2aa65
        if (Arrays.asList("gnome-shell", "gnome", "mate", "other...").contains(System.getenv("DESKTOP_SESSION"))) {
            Throwable th = null;
            try {
                Class<?> xwm = Class.forName("sun.awt.X11.XWM");
                Field awtMgr = xwm.getDeclaredField("awt_wmgr");
                awtMgr.setAccessible(true);
                Field otherWm = xwm.getDeclaredField("OTHER_WM");
                otherWm.setAccessible(true);
                if (awtMgr.get(null).equals(otherWm.get(null))) {
                    Field metacityWm = xwm.getDeclaredField("METACITY_WM");
                    metacityWm.setAccessible(true);
                    awtMgr.set(null, metacityWm.get(null));
                }
            } catch (ClassNotFoundException ex) {
                th = ex;
            } catch (SecurityException ex) {
                th = ex;
            } catch (NoSuchFieldException ex) {
                th = ex;
            } catch (IllegalArgumentException ex) {
                th = ex;
            } catch (IllegalAccessException ex) {
                th = ex;
            }
            if (th != null) {
                log.error("Could not fix Gnome3 mouse problem", th);
            } else {
                log.warn("Fixed Gnome3 mouse problem");
            }
        }
    }

    /**
     * Determines whether MacOSX has been registered.
     * 
     * @return true when there is full MacOSX integration.
     */
    public boolean isOSXRegistered() {
        return osxRegistered;
    }

    /**
     * @return the Bible installer dialog
     */
    public SitesPane getSites() {
        if (sites == null) {
            sites = new SitesPane();
        }
        return sites;
    }

    /**
     * Open a new passage window from a file.
     */
    public void doOpen() {
        try {
            BibleViewPane view = (BibleViewPane) getDesktop().getViews().getSelected();
            view.open();
        } catch (NoSuchKeyException e) {
            Reporter.informUser(getDesktop(), e);
        } catch (IOException e) {
            Reporter.informUser(getDesktop(), e);
        }
    }

    /**
     * Save the current passage window.
     */
    public void doSave() {
        try {
            BibleViewPane view = (BibleViewPane) getDesktop().getViews().getSelected();
            if (!view.maySave()) {
                // TRANSLATOR: The user is trying to save the passage in the visible
                // Bible View pane, but it is empty.
                Reporter.informUser(getDesktop(), BDMsg.gettext("No Passage to Save"));
                return;
            }

            view.save();
        } catch (IOException ex) {
            Reporter.informUser(getDesktop(), ex);
        }
    }

    /**
     * Save the current passage window under a new name.
     */
    public void doSaveAs() {
        try {
            BibleViewPane view = (BibleViewPane) getDesktop().getViews().getSelected();
            if (!view.maySave()) {
                // TRANSLATOR: The user is trying to save the passage in the visible
                // Bible View pane, but it is empty.
                Reporter.informUser(getDesktop(), BDMsg.gettext("No Passage to Save"));
                return;
            }

            view.saveAs();
        } catch (IOException ex) {
            Reporter.informUser(getDesktop(), ex);
        }
    }

    /**
     * Save all the passage windows.
     */
    public void doSaveAll() {
        boolean ok = false;

        for (Component comp : getDesktop().getViews()) {
            BibleViewPane view = (BibleViewPane) comp;
            if (view.maySave()) {
                ok = true;
            }
        }

        if (!ok) {
            // TRANSLATOR: The user is trying to save the passage in all the
            // Bible View panes, but they are all empty.
            Reporter.informUser(getDesktop(), BDMsg.gettext("No Passage to Save"));
            return;
        }

        for (Component comp : getDesktop().getViews()) {
            try {
                BibleViewPane view = (BibleViewPane) comp;
                view.save();
            } catch (IOException ex) {
                Reporter.informUser(getDesktop(), ex);
            }
        }
    }

    /**
     * Exits the VM.
     */
    public void doExit() {
        LayoutPersistence.instance().saveLayout(desktop);
        System.exit(0);
    }

    /**
     * Copy the selected text from the "active" display area to the clipboard.
     */
    public void doCopy() {
        BibleViewPane view = (BibleViewPane) getDesktop().getViews().getSelected();
        SplitBookDataDisplay da = view.getPassagePane();
        da.copy();
    }

    /**
     * Go to previous passage.
     */
    public void doBack() {
        getDesktop().selectHistory(-1);
    }

    /**
     * Go to next passage.
     */
    public void doForward() {
        getDesktop().selectHistory(1);
    }

    public void doStrongs(ActionEvent ev) {
        doToggle(ev, XSLTProperty.STRONGS_NUMBERS);
    }

    public void doMorph(ActionEvent ev) {
        doToggle(ev, XSLTProperty.MORPH);
    }

    public void doVLine(ActionEvent ev) {
        doToggle(ev, XSLTProperty.START_VERSE_ON_NEWLINE);
    }

    public void doVNum() {
        XSLTProperty.VERSE_NUMBERS.setState(true);
        XSLTProperty.CV.setState(false);
        XSLTProperty.BCV.setState(false);
        XSLTProperty.NO_VERSE_NUMBERS.setState(false);
        BibleViewPane view = (BibleViewPane) getDesktop().getViews().getSelected();
        SplitBookDataDisplay da = view.getPassagePane();
        da.getBookDataDisplay().refresh();
    }

    public void doTinyVNum(ActionEvent ev) {
        doToggle(ev, XSLTProperty.TINY_VERSE_NUMBERS);
    }

    public void doBCVNum() {
        XSLTProperty.VERSE_NUMBERS.setState(false);
        XSLTProperty.CV.setState(false);
        XSLTProperty.BCV.setState(true);
        XSLTProperty.NO_VERSE_NUMBERS.setState(false);
        BibleViewPane view = (BibleViewPane) getDesktop().getViews().getSelected();
        SplitBookDataDisplay da = view.getPassagePane();
        da.getBookDataDisplay().refresh();
    }

    public void doCVNum() {
        XSLTProperty.VERSE_NUMBERS.setState(false);
        XSLTProperty.CV.setState(true);
        XSLTProperty.BCV.setState(false);
        XSLTProperty.NO_VERSE_NUMBERS.setState(false);
        BibleViewPane view = (BibleViewPane) getDesktop().getViews().getSelected();
        SplitBookDataDisplay da = view.getPassagePane();
        da.getBookDataDisplay().refresh();
    }

    public void doNoVNum() {
        XSLTProperty.VERSE_NUMBERS.setState(false);
        XSLTProperty.CV.setState(false);
        XSLTProperty.BCV.setState(false);
        XSLTProperty.NO_VERSE_NUMBERS.setState(true);
        BibleViewPane view = (BibleViewPane) getDesktop().getViews().getSelected();
        SplitBookDataDisplay da = view.getPassagePane();
        da.getBookDataDisplay().refresh();
    }

    /**
     * Show differences between Bible Book versions.
     */
    public void doCompareToggle(ActionEvent ev) {
        JCheckBoxMenuItem toggle = (JCheckBoxMenuItem) ev.getSource();
        getDesktop().setCompareShowing(toggle.getState());
    }

    public void doHeadings(ActionEvent ev) {
        doToggle(ev, XSLTProperty.HEADINGS);
    }

    public void doGloss(ActionEvent ev) {
        doToggle(ev, XSLTProperty.GLOSS);
    }

    public void doNotes(ActionEvent ev) {
        doToggle(ev, XSLTProperty.NOTES);
    }

    public void doXRef(ActionEvent ev) {
        doToggle(ev, XSLTProperty.XREF);
    }

    private void doToggle(ActionEvent ev, XSLTProperty prop) {
        JCheckBoxMenuItem toggle = (JCheckBoxMenuItem) ev.getSource();
        prop.setState(toggle.isSelected());
        BibleViewPane view = (BibleViewPane) getDesktop().getViews().getSelected();
        SplitBookDataDisplay da = view.getPassagePane();
        da.getBookDataDisplay().refresh();
    }
 
    /**
     * View the HTML as interpreted by the current window. This HTML will not
     * return the styling present in the viewer. That is all class="" are
     * stripped out. Also you may find additional whitespace added to the
     * original.
     */
    public void doViewSource() {
        // Limit view source to the current tab.
        BibleViewPane view = (BibleViewPane) getDesktop().getViews().getSelected();
        SplitBookDataDisplay da = view.getPassagePane();
        BookDataDisplay bdd = da.getBookDataDisplay();
        if (bdd instanceof TabbedBookDataDisplay) {
            bdd = ((TabbedBookDataDisplay) bdd).getInnerDisplayPane();
        }

        Key key = bdd.getKey();

        if (key == null) {
            // TRANSLATOR: The user is trying to view the source of the passage in the visible
            // Bible View pane, but it is empty.
            Reporter.informUser(getDesktop(), BDMsg.gettext("No current passage to view"));
            return;
        }

        ViewSourcePane viewer = new ViewSourcePane(da.getBooks(), key);
        viewer.showInFrame(getDesktop());
    }

    /**
     * Opens the Book installer window (aka a SitesPane)
     */
    public void doBooks() {
        int webAccess = InternetWarning.GRANTED;
        if (WebWarning.instance().isShown()) {
            webAccess = InternetWarning.showDialog(desktop, "?");
        }

        if (webAccess == InternetWarning.GRANTED) {
            getSites().showInDialog(getDesktop());
        }
    }

    /**
     * Opens the Options window
     */
    public void doOptions() {
        URI configUri = CWProject.instance().getWritableURI("desktop", FileUtil.EXTENSION_PROPERTIES);
        ConfigEditorFactory.showDialog(desktop.getConfig(), desktop, configUri);
    }

    /**
     * For opening a help file.
     */
    public void doContents() {
        StringBuilder buf = new StringBuilder(200);
        // TRANSLATOR: Someday we'll have real help but for now this points them to the Bible Desktop web site.
        buf.append(BDMsg.gettext("Currently on-line help is only available via the Bible Desktop's website:"));
        buf.append("\nhttp://www.crosswire.org/bibledesktop");
        CWOptionPane.showMessageDialog(getDesktop(), buf.toString());
    }

    /**
     * For opening the About window
     */
    public void doAbout() {
        if (atp == null) {
            atp = new AboutPane();
        }

        atp.showInDialog(getDesktop());
    }

    /**
     * Show large or small tool bar icons.
     */
    public void doToolTipToggle(ActionEvent ev) {
        JCheckBoxMenuItem toggle = (JCheckBoxMenuItem) ev.getSource();
        ToolTipManager.sharedInstance().setEnabled(toggle.isSelected());
    }

    /**
     * Show large or small tool bar icons.
     */
    public void doStatusToggle(ActionEvent ev) {
        JCheckBoxMenuItem toggle = (JCheckBoxMenuItem) ev.getSource();
        desktop.showStatusBar(toggle.isSelected());
    }

    /**
     * Show large or small tool bar icons.
     */
    public void doSidebarToggle(ActionEvent ev) {
        JCheckBoxMenuItem toggle = (JCheckBoxMenuItem) ev.getSource();
        final boolean show = toggle.isSelected();
        desktop.getViews().visit(new ShowSideBarVisitor(show));
    }

    /**
     *
     */
    private static final class ShowSideBarVisitor implements ViewVisitor {
        /**
         * @param show
         */
        ShowSideBarVisitor(boolean show) {
            this.show = show;
        }

        /* (non-Javadoc)
         * @see org.crosswire.common.swing.desktop.ViewVisitor#visitView(java.awt.Component)
         */
        @Override
        public void visitView(Component component) {
            if (component instanceof BibleViewPane) {
                BibleViewPane view = (BibleViewPane) component;
                SplitBookDataDisplay sbDisplay = view.getPassagePane();
                sbDisplay.showSidebar(show);
            }
        }

        private boolean show;
    }

    // Enumeration of all the keys to known actions
//    static final String FILE = "File";
//    static final String EDIT = "Edit";
//    static final String GO = "Go";
//    static final String VIEW = "View";
//    static final String TOOLS = "Tools";
//    static final String HELP = "Help";
//    static final String OPEN = "Open";
//    static final String SAVE = "Save";
//    static final String SAVE_AS = "SaveAs";
//    static final String SAVE_ALL = "SaveAll";
//    static final String EXIT = "Exit";
//    static final String COPY = "Copy";
//    static final String BACK = "Back";
//    static final String FORWARD = "Forward";
//    static final String COMPARE_TOGGLE = "CompareToggle";
//    static final String TOOLTIP_TOGGLE = "ToolTipToggle";
//    static final String STATUS_TOGGLE = "StatusToggle";
//    static final String SIDEBAR_TOGGLE = "SidebarToggle";
//    static final String VERSE = "Verse";
//    static final String VIEW_SOURCE = "ViewSource";
//    static final String BOOKS = "Books";
//    static final String OPTIONS = "Options";
//    static final String CONTENTS = "Contents";
//    static final String ABOUT = "About";

    /**
     * The desktop on which these actions work
     */
    protected Desktop desktop;

    /**
     * The factory for actions that this class works with
     */
    private ActionFactory actions;

    /**
     * Indicates whether there is MacOSX integration.
     */
    private boolean osxRegistered;

    /**
     * The About window
     */
    private AboutPane atp;

    /**
     * The Book installer window
     */
    private SitesPane sites;

    /**
     * The log stream
     */
    protected static final Logger log = LoggerFactory.getLogger(DesktopActions.class);
}
