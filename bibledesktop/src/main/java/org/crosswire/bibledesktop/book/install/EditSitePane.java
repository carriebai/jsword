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
package org.crosswire.bibledesktop.book.install;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.crosswire.bibledesktop.BDMsg;
import org.crosswire.common.swing.ActionFactory;
import org.crosswire.common.swing.CWAction;
import org.crosswire.common.swing.CWLabel;
import org.crosswire.common.swing.CWOptionPane;
import org.crosswire.common.swing.CWScrollPane;
import org.crosswire.common.swing.FixedSplitPane;
import org.crosswire.common.swing.GuiUtil;
import org.crosswire.jsword.book.install.InstallManager;
import org.crosswire.jsword.book.install.Installer;
import org.crosswire.jsword.book.install.InstallerFactory;

/**
 * An editor for the list of available update sites.
 * 
 * @see gnu.gpl.License for license details.
 * @author Joe Walker
 * @author DM Smith
 */
public class EditSitePane extends JPanel {
    /**
     * This is the default constructor
     */
    public EditSitePane(InstallManager imanager) {
        this.imanager = imanager;
        userInitiated = true;

        init();
        setState(EditState.DISPLAY, null);
        select();
    }

    /**
     * GUI init
     */
    private void init() {
        actions = new ActionFactory(this);

        lstSite = new JList(new InstallManagerComboBoxModel(imanager));
        JScrollPane scrSite = new CWScrollPane(lstSite);

        lstSite.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lstSite.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent ev) {
                if (ev.getValueIsAdjusting()) {
                    return;
                }

                select();
            }
        });

        // TRANSLATOR: This is the label for the button that allows the user to add a new download site
        CWAction action = actions.addAction("Add", BDMsg.gettext("Add"));
        // TRANSLATOR: This is the tooltip for the button that allows the user to add a new download site
        action.setTooltip(BDMsg.gettext("Add a new installation site."));
        JButton btnAdd = new JButton(action);

        // TRANSLATOR: This is the label for the button that allows the user to edit an existing download site
        action = actions.addAction("Edit", BDMsg.gettext("Edit"));
        // TRANSLATOR: This is the tooltip for the button that allows the user to edit an existing download site
        action.setTooltip(BDMsg.gettext("Edit the current installation site."));
        JButton btnEdit = new JButton(action);

        // TRANSLATOR: This is the label for the button that allows the user to remove an existing download site
        action = actions.addAction("Delete", BDMsg.gettext("Delete"));
        // TRANSLATOR: This is the tooltip for the button that allows the user to remove an existing download site
        action.setTooltip(BDMsg.gettext("Delete Site?"));
        JButton btnDelete = new JButton(action);

        JPanel pnlBtn1 = new JPanel();
        pnlBtn1.add(btnAdd, null);
        pnlBtn1.add(btnEdit, null);
        pnlBtn1.add(btnDelete, null);

        JPanel pnlSite = new JPanel();
        pnlSite.setLayout(new BorderLayout());
        pnlSite.add(scrSite, BorderLayout.CENTER);
        pnlSite.add(pnlBtn1, BorderLayout.SOUTH);

        txtName = new JTextField();
        txtName.setColumns(10);
        txtName.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void changedUpdate(DocumentEvent ev) {
                siteUpdate();
            }

            @Override
            public void insertUpdate(DocumentEvent ev) {
                siteUpdate();
            }

            @Override
            public void removeUpdate(DocumentEvent ev) {
                siteUpdate();
            }
        });

        // TRANSLATOR: This is the label for a Site Name text box
        JLabel lblName = CWLabel.createJLabel(BDMsg.gettext("Site Name:"));
        lblName.setLabelFor(txtName);

        cboType = new JComboBox(new InstallerFactoryComboBoxModel(imanager));
        cboType.setEditable(false);
        cboType.setSelectedIndex(0);
        cboType.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ev) {
                newType();
            }
        });

        // TRANSLATOR: This is the label for the dropdown giving the type of the site
        // either FTP or HTTP
        // This is currently unused, as we only do HTTP, but will be present soon.
        JLabel lblType = CWLabel.createJLabel(BDMsg.gettext("Site Type"));
        lblType.setLabelFor(cboType);

        lblMesg = new JLabel();
        lblMesg.setText(" ");

        // TRANSLATOR: This is the label for a button that resets the details for a download site
        // to what was last saved.
        action = actions.addAction("Reset", BDMsg.gettext("Reset"));
        // TRANSLATOR: This is the tooltip for a button that resets the details for a download site
        // to what was last saved.
        action.setTooltip(BDMsg.gettext("Reset the details."));
        JButton btnReset = new JButton(action);

        // TRANSLATOR: This is the label for a button that saves the details for a download site
        action = actions.addAction("Save", BDMsg.gettext("Save"));
        // TRANSLATOR: This is the tooltip for a button that saves the details for a download site
        action.setTooltip(BDMsg.gettext("Save the current changes."));
        JButton btnSave = new JButton(action);

        JPanel pnlBtn2 = new JPanel();
        pnlBtn2.add(btnSave, null);
        pnlBtn2.add(btnReset, null);

        siteEditorPane = new JPanel();
        siteEditorPane.setLayout(new GridBagLayout());
        JPanel pnlMain = new JPanel();
        pnlMain.setPreferredSize(new Dimension(300, 300));
        pnlMain.setLayout(new GridBagLayout());
        pnlMain.add(lblMesg, new GridBagConstraints(0, 0, 2, 1, 0.0, 0.0, GridBagConstraints.LINE_START, GridBagConstraints.HORIZONTAL, new Insets(10, 10, 10, 10), 0, 0));
        pnlMain.add(lblName, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.LINE_END, GridBagConstraints.NONE, new Insets(2, 10, 2, 2), 0, 0));
        pnlMain.add(txtName, new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(2, 2, 2, 10), 0, 0));
        // If there is only one type, then don't give the user a choice
        if (imanager.getInstallerFactoryNames().size() > 1) {
            pnlMain.add(lblType, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.LINE_END, GridBagConstraints.NONE, new Insets(2, 10, 2, 2), 0, 0));
            pnlMain.add(cboType, new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(2, 2, 2, 10), 0, 0));
        }
        pnlMain.add(new JSeparator(), new GridBagConstraints(0, 3, 2, 1, 0.0, 0.0, GridBagConstraints.LINE_START, GridBagConstraints.HORIZONTAL, new Insets(10, 10, 10, 10), 0, 0));
        pnlMain.add(siteEditorPane, new GridBagConstraints(0, 4, 2, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        pnlMain.add(pnlBtn2, new GridBagConstraints(0, 5, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));

        JSplitPane sptMain = new FixedSplitPane();
        sptMain.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        // Make resizing affect the right only
        sptMain.setResizeWeight(0.0D);
        sptMain.setLeftComponent(pnlSite);
        sptMain.setRightComponent(pnlMain);

        this.setLayout(new BorderLayout());
        this.add(sptMain, BorderLayout.CENTER);

        // TRANSLATOR: This is the label for a button that closes the dialog
        btnClose = new JButton(actions.addAction("Close", BDMsg.gettext("Close")));

        pnlAction = new JPanel();
        pnlAction.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        pnlAction.setLayout(new FlowLayout(FlowLayout.TRAILING));
        pnlAction.add(btnClose, null);
        GuiUtil.applyDefaultOrientation(this);
    }

    /**
     * Open us in a new modal dialog window
     * 
     * @param parent
     *            The component to which to attach the new dialog
     */
    public void showInDialog(Component parent) {
        Frame root = JOptionPane.getFrameForComponent(parent);
        dlgMain = new JDialog(root);

        ActionListener closer = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ev) {
                doClose();
            }
        };

        KeyStroke esc = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);

        dlgMain.getContentPane().setLayout(new BorderLayout());
        dlgMain.getContentPane().add(new JPanel(), BorderLayout.NORTH);
        dlgMain.getContentPane().add(pnlAction, BorderLayout.SOUTH);
        dlgMain.getContentPane().add(this, BorderLayout.CENTER);
        dlgMain.getContentPane().add(new JPanel(), BorderLayout.LINE_END);
        dlgMain.getContentPane().add(new JPanel(), BorderLayout.LINE_START);
        dlgMain.getRootPane().setDefaultButton(btnClose);
        dlgMain.getRootPane().registerKeyboardAction(closer, esc, JComponent.WHEN_IN_FOCUSED_WINDOW);
        dlgMain.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        // TRANSLATOR: Title for the dialog allowing the editing of SWORD download sites.
        dlgMain.setTitle(BDMsg.gettext("Edit Update Sites"));
        dlgMain.setResizable(true);
        dlgMain.setModal(true);

        GuiUtil.setSize(dlgMain, new Dimension(750, 400));
        GuiUtil.centerOnScreen(dlgMain);
        GuiUtil.applyDefaultOrientation(dlgMain);
        dlgMain.setVisible(true);
        dlgMain.toFront();
    }

    /**
     * Close the window, and save the install manager state
     */
    public void doClose() {
        imanager.save();
        dlgMain.dispose();
    }

    /**
     * The name field has been updated, so we need to check the entry is valid
     */
    public final void siteUpdate() {
        if (txtName.isEditable()) {
            String name = txtName.getText().trim();

            if (name.length() == 0) {
                // TRANSLATOR: Indicate to the user that they did not supply a download site name.
                setState(EditState.EDIT_ERROR, BDMsg.gettext("Missing site name"));
                return;
            }

            if (imanager.getInstaller(name) != null) {
                // TRANSLATOR: Indicate that the user supplied a name that matched a download site that they already have.
                setState(EditState.EDIT_ERROR, BDMsg.gettext("Duplicate site name"));
                return;
            }

            setState(EditState.EDIT_OK, "");
        }
    }

    /**
     * The installer type combo box has been changed
     */
    /*private*/final void newType() {
        if (userInitiated) {
            String type = (String) cboType.getSelectedItem();
            InstallerFactory ifactory = imanager.getInstallerFactory(type);
            Installer installer = ifactory.createInstaller();

            setInstaller(installer);
        }
    }

    /**
     * Someone has picked a new installer
     */
    protected final void select() {
        String name = (String) lstSite.getSelectedValue();
        if (name == null) {
            actions.findAction("Edit").setEnabled(false);
            clear();
        } else {
            actions.findAction("Edit").setEnabled(true);

            Installer installer = imanager.getInstaller(name);
            display(name, installer);
        }

        // Since setting the display undoes any work done to set the edit state
        // of the bean panel we need to redo it here. Since we are always in
        // display mode at this point, this is fairly easy.
        if (siteEditor != null) {
            siteEditor.setEditable(false);
        }
    }

    /**
     * Add a new installer to the list
     */
    public void doAdd() {
        newType();

        editName = null;
        editInstaller = null;

        // We need to call setState() to enable the text boxes so that
        // siteUpdate() works properly
        setState(EditState.EDIT_OK, null);
        siteUpdate();

        GuiUtil.refresh(this);
    }

    /**
     * Move the selected installer to the installer edit panel
     */
    public void doEdit() {
        String name = (String) lstSite.getSelectedValue();
        if (name == null) {
            // TRANSLATOR: Dialog title letting the user know that they they have not selected a download site to edit.
            String title = BDMsg.gettext("No Site");
            // TRANSLATOR: Let the user know that they have not selected a download site to edit.
            String msg = BDMsg.gettext("No selected site to edit");
            CWOptionPane.showMessageDialog(this, msg, title, JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        editName = name;
        editInstaller = imanager.getInstaller(name);

        imanager.removeInstaller(name);

        setState(EditState.EDIT_OK, null);
        siteUpdate();

        txtName.grabFocus();
    }

    /**
     * Delete the selected installer from the list (on the left hand side)
     */
    public void doDelete() {
        String name = (String) lstSite.getSelectedValue();
        if (name == null) {
            return;
        }
        // TRANSLATOR: Dialog title asking the user to confirm the delete of a download site.
        String title = BDMsg.gettext("Delete Site?");
        // TRANSLATOR: Message asking the user to confirm the delete of a download site. {0} is a placeholder for the name of the download site.
        String msg = BDMsg.gettext("Are you sure you want to delete {0}?", name);
        if (CWOptionPane.showConfirmDialog(this, msg, title, JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            imanager.removeInstaller(name);
        }

        clear();
        setState(EditState.DISPLAY, null);
    }

    /**
     * End editing the current installer
     */
    public void doReset() {
        if (editName != null) {
            imanager.addInstaller(editName, editInstaller);
        }

        clear();
        editName = null;
        editInstaller = null;

        setState(EditState.DISPLAY, "");
        select();
    }

    /**
     * Save the current installer to the list of installers
     */
    public void doSave() {
        String name = txtName.getText();
        siteEditor.save();
        Installer installer = siteEditor.getInstaller();
        imanager.addInstaller(name, installer);

        clear();
        editName = null;
        editInstaller = null;

        setState(EditState.DISPLAY, "");
        select();
    }

    /**
     * Set the various gui elements depending on the current edit mode
     */
    private void setState(EditState stateEditError, String message) {
        switch (stateEditError) {
        case DISPLAY:
            actions.findAction("Add").setEnabled(true);
            actions.findAction("Delete").setEnabled(true);
            actions.findAction("Edit").setEnabled(true);
            lstSite.setEnabled(true);

            actions.findAction("Reset").setEnabled(false);
            actions.findAction("Save").setEnabled(false);

            actions.findAction("Close").setEnabled(true);

            txtName.setEditable(false);
            cboType.setEnabled(false);

            if (siteEditor != null) {
                siteEditor.setEditable(false);
            }

            break;

        case EDIT_OK:
        case EDIT_ERROR:
            actions.findAction("Add").setEnabled(false);
            actions.findAction("Delete").setEnabled(false);
            actions.findAction("Edit").setEnabled(false);
            lstSite.setEnabled(false);

            actions.findAction("Reset").setEnabled(true);
            actions.findAction("Save").setEnabled(stateEditError == EditState.EDIT_OK);

            actions.findAction("Close").setEnabled(false);

            txtName.setEditable(true);
            cboType.setEnabled(true);

            if (siteEditor != null) {
                siteEditor.setEditable(true);
            }

            break;

        default:
            assert false : stateEditError;
        }

        if (message == null || message.trim().length() == 0) {
            lblMesg.setText(" ");
        } else {
            lblMesg.setText(message);
        }
    }

    /**
     * Set the display in the RHS to the given installer
     */
    private void display(String name, Installer installer) {
        txtName.setText(name);

        String type = imanager.getFactoryNameForInstaller(installer);
        userInitiated = false;
        cboType.setSelectedItem(type);
        userInitiated = true;

        setInstaller(installer);
    }

    /**
     * Clear the display in the RHS of any installers
     */
    private void clear() {
        txtName.setText("");
        setInstaller(null);
    }

    /**
     * Convenience method to allow us to change the type of the current
     * installer.
     * 
     * @param installer
     *            The new installer to introspect
     */
    private void setInstaller(Installer installer) {
        siteEditorPane.removeAll();
        siteEditor = null;
        if (installer != null) {
            siteEditor = SiteEditorFactory.createSiteEditor(installer);
            siteEditorPane.add((Component) siteEditor, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));
            GuiUtil.applyDefaultOrientation(siteEditorPane);
        }

        GuiUtil.refresh(this);
    }

    /**
     * Serialization support.
     * 
     * @param is
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private void readObject(ObjectInputStream is) throws IOException, ClassNotFoundException {
        // Broken but we don't serialize views
        imanager = null;
        editInstaller = null;
        actions = new ActionFactory(this);
        is.defaultReadObject();
    }


    /**
     * An EditState give the possible states that an editor can be in.
     */
    private enum EditState {
        /**
         * The state is viewing a site
         */
        DISPLAY,

        /**
         * The state is editing a site (syntactically valid)
         */
        EDIT_OK,

        /**
         * The state is editing a site (syntactically invalid)
         */
        EDIT_ERROR,
    }

    /**
     * The model that we are providing a view/controller for
     */
    private transient InstallManager imanager;

    /**
     * If we are editing an installer, we need to know it's original name in
     * case someone clicks cancel.
     */
    private String editName;

    /**
     * If we are editing an installer, we need to know it's original value in
     * case someone clicks cancel.
     */
    private transient Installer editInstaller;

    /**
     * Edits to the type combo box mean different things depending on whether it
     * was triggered by the user or the application.
     */
    private boolean userInitiated;

    /*
     * The ActionFactory holding the actions used by this
     * EditSite.
     */
    private transient ActionFactory actions;

    /*
     * GUI Components for the list of sites
     */
    private JList lstSite;

    /*
     * GUI Components for the site view/edit area
     */
    private JLabel lblMesg;
    private JTextField txtName;
    private JComboBox cboType;
    private JPanel siteEditorPane;
    private SiteEditor siteEditor;

    /*
     * Components for the dialog box including the button bar at the bottom.
     * These are separated in this way in case this component is reused in a
     * larger context.
     */
    protected JDialog dlgMain;
    private JButton btnClose;
    private JPanel pnlAction;

    /**
     * Serialization ID
     */
    private static final long serialVersionUID = 3256446910616057650L;
}
