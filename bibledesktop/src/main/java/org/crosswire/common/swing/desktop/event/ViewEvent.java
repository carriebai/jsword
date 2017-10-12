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
package org.crosswire.common.swing.desktop.event;

import java.awt.Component;
import java.util.EventObject;

/**
 * An event that describes that something happened to a view.
 * 
 * @see gnu.gpl.License The GNU General Public License for details.
 * @author DM Smith
 */
public final class ViewEvent extends EventObject {
    /**
     * Default ctor to set the event source
     */
    public ViewEvent(Component view) {
        super(view);
    }

    /**
     * Serialization ID
     */
    private static final long serialVersionUID = 3258689927188590898L;
}
