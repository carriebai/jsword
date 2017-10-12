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
package org.crosswire.common.swing.desktop;

import org.crosswire.bibledesktop.BDMsg;


/**
 * Types of ViewLayouts. Currently there are two types of desktop layouts:
 * <ul>
 * <li>TDI - tabbed document interface.</li>
 * <li>MDI - multiple document interface (sub-windows)</li>
 * </ul>
 * 
 * @see gnu.gpl.License The GNU General Public License for details.
 * @author Joe Walker
 * @author DM Smith
 */
public enum LayoutType {
    /**
     * Tabbed View
     */
    TDI {
        @Override
        public AbstractViewLayout createLayout() {
            return new TDIViewLayout();
        }

        @Override
        public String toString() {
            // TRANSLATOR: This is the name of one of two different ways to present Bible Views.
            // These show up in Options/Preferences.
            return BDMsg.gettext("Tabbed Document Interface");
        }
    },

    /**
     * Multiple Document View
     */
    MDI {
        @Override
        public AbstractViewLayout createLayout() {
            return new MDIViewLayout();
        }

        @Override
        public String toString() {
            // TRANSLATOR: This is the name of one of two different ways to present Bible Views.
            // These show up in Options/Preferences.
            return BDMsg.gettext("Multiple Document Interface");
        }
    };

    /**
     * Return the layout
     * 
     * @return the layout
     */
    public AbstractViewLayout getLayout() {
        // In order to get the proper LAF it needs to be created after the LAF
        // is set
        // So we delay it until it is actually needed.
        if (layout == null) {
            layout = createLayout();
        }
        return layout;
    }

    /**
     * Create the appropriate kind of view layout
     * 
     * @return the created view layout
     */
    public abstract AbstractViewLayout createLayout();

    /**
     * Get an integer representation for this LayoutType
     */
    public int toInteger() {
        return ordinal();
    }

    /**
     * Lookup method to convert from a String
     */
    public static LayoutType fromString(String name) {
        for (LayoutType v : values()) {
            if (v.name().equalsIgnoreCase(name)) {
                return v;
            }
        }

        // cannot get here
        assert false;
        return null;
    }

    /**
     * Lookup method by ordinal value
     */
    public static LayoutType fromInteger(int ordinal) {
        for (LayoutType v : values()) {
            if (v.ordinal() == ordinal) {
                return v;
            }
        }

        // cannot get here
        assert false;
        return null;
    }

    /**
     * The actual layout
     */
    private transient AbstractViewLayout layout;
}
