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

import java.util.EventObject;

/**
 * 'thrown' to indicate that a View has a new title.
 * 
 * @see gnu.gpl.License The GNU General Public License for details.
 * @author Joe Walker
 */
public class TitleChangedEvent extends EventObject {
    /**
     *
     */
    public TitleChangedEvent(Object source, String title) {
        super(source);
        this.title = title;
    }

    /**
     * Accessor for the title
     */
    public String getTitle() {
        return title;
    }

    private String title;

    /**
     * Serialization ID
     */
    private static final long serialVersionUID = 3256444685672526641L;
}
