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
package org.crosswire.common.swing;

import java.util.Enumeration;

import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.FontUIResource;
import javax.swing.plaf.metal.MetalLookAndFeel;

import org.crosswire.common.swing.plaf.MetalLFCustoms;
import org.crosswire.common.swing.plaf.OtherLFCustoms;
import org.crosswire.common.swing.plaf.WindowsLFCustoms;
import org.crosswire.common.util.ClassUtil;

/**
 * LookAndFeelUtil declares the Choices and actions needed to dynamically change
 * the look and feel (PLAF) and to add new PLAFs without needing to restart.
 * 
 * @see gnu.gpl.License The GNU General Public License for details.
 * @author Joe Walker
 * @author Mark Goodwin
 * @author DM Smith
 * @author Willie Thean
 */
public final class LookAndFeelUtil {
    /**
     * Prevent instantiation
     */
    private LookAndFeelUtil() {
    }

    /**
     * Establish the system look and feel
     */
    public static void initialize() {
        // Calling any method in this package will force the
        // static initializer to be called.
    }

    /**
     * The Options customization
     */
    public static Class<?> getLookAndFeel() {
        if (currentLAF == null) {
            return defaultLAF;
        }
        return currentLAF;
    }

    /**
     * Set the look and feel to a new class.
     */
    public static void setLookAndFeel(Class<LookAndFeel> newLaFClass) throws InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
        LookAndFeel laf = newLaFClass.newInstance();

        // newLaFClass is null if the user enters a bogus value
        if (currentLAF != null && !currentLAF.equals(newLaFClass)) {
            CWOptionPane.showMessageDialog(null, CWOtherMsg.lookupText("The Look and Feel will change on the next startup."));
        } else {
            UIManager.setLookAndFeel(laf);
        }

        currentLAF = newLaFClass;
    }

    /**
     * Accessor for the stylesheet we are transforming using
     */
    public static String getFont() {
        return font;
    }

    /**
     * Converts the font spec to something useful.
     */
    public static FontUIResource toFontUIResource() {
        return new FontUIResource(GuiConvert.string2Font(LookAndFeelUtil.font));
    }

    /**
     * Accessor for the stylesheet we are transforming using
     */
    public static void setFont(String font) {
        LookAndFeelUtil.font = font;
        setUIFont(toFontUIResource());
    }

    /**
     * Set the default font for all Swing components. E.g.
     * <code>setUIFont(new FontUIResource("Serif", Font.ITALIC, 12));</code> <br>
     * Note: a single resources can be changed with:
     * <code>UIManager.put("Label.font", new Font("Serif", Font.ITALIC, 12));</code>
     * 
     * @param f
     *            the font to use
     */
    public static void setUIFont(FontUIResource f) {
        Enumeration<Object> keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);

            if (value instanceof FontUIResource) {
                // System.err.println(key + " = " + value);
                UIManager.put(key, f);
            }
        }
    }

    /**
     * The current PLAF
     */
    private static Class<LookAndFeel> currentLAF;

    /**
     * The default PLAF (and the default value)
     */
    private static Class<?> defaultLAF;

    /**
     * The font to be used for the application
     */
    private static String font = "Dialog-PLAIN-12";

    /**
     * Setup the default PLAF
     */
    static {
        defaultLAF = MetalLookAndFeel.class;
        String systemLAF = UIManager.getSystemLookAndFeelClassName();
        try {
            // Note: GTK looks good under Java 1.5, but is broken.
            // Motif still does not look good.
            // systemLAF.indexOf("GTKLookAndFeel") != -1 ||
            if (systemLAF.indexOf("WindowsLookAndFeel") != -1 || systemLAF.indexOf("AquaLookAndfeel") != -1) {
                UIManager.setLookAndFeel(systemLAF);
                // MetalLookAndFeel.setCurrentTheme(new DefaultMetalTheme());
                // UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
                // UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
                // UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
                defaultLAF = ClassUtil.forName(systemLAF);
            }
        } catch (ClassNotFoundException e) {
            assert false;
        } catch (InstantiationException e) {
            assert false;
        } catch (IllegalAccessException e) {
            assert false;
        } catch (UnsupportedLookAndFeelException e) {
            assert false;
        }

        customizeBDLookandFeel();
    }

    private static void customizeBDLookandFeel() {
        String currentLF = UIManager.getLookAndFeel().getClass().getName();

        if (currentLF.indexOf("MetalLookAndFeel") != -1) {
            new MetalLFCustoms().initUIDefaults();
        } else if (currentLF.indexOf("WindowsLookAndFeel") != -1) {
            new WindowsLFCustoms().initUIDefaults();
        } else {
            new OtherLFCustoms().initUIDefaults();
        }
    }
}
