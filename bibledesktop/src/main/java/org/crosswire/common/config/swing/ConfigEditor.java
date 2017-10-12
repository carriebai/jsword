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
package org.crosswire.common.config.swing;

import java.awt.Component;
import java.awt.event.ActionListener;

import org.crosswire.common.config.Config;

/**
 * An abstraction of a Configuration Editor.
 * 
 * @see gnu.gpl.License The GNU General Public License for details.
 * @author Joe Walker
 */
public interface ConfigEditor {
    /**
     * The equivalent of a constructor, create a Config base with the set of
     * Fields that it will display.
     * 
     * @param config
     *            The configurable settings
     */
    void construct(Config config);

    /**
     * Create a dialog to house a TreeConfig component using the default set of
     * Fields
     * 
     * @param parent
     *            A component to use to find a frame to use as a dialog parent
     */
    void showDialog(Component parent, ActionListener al);
}
