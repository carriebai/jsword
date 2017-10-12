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
package org.crosswire.bibledesktop.passage;

import java.util.EventObject;

import org.crosswire.jsword.passage.Key;

/**
 * A KeyChangeEvent happens whenever a user changes a key.
 * 
 * @see gnu.gpl.License for license details.
 * @author DM Smith
 */
public class KeyChangeEvent extends EventObject {
    /**
     * For when a key has been changed
     * 
     * @param source
     *            The thing that started this off
     * @param key
     *            The selected Key
     */
    public KeyChangeEvent(Object source, Key key) {
        super(source);

        this.key = key;
    }

    /**
     * Get the key with its changes
     * 
     * @return the changed key
     */
    public Key getKey() {
        return key;
    }

    /**
     * The new key
     */
    private Key key;

    /**
     * Serialization ID
     */
    private static final long serialVersionUID = 3256725091319362361L;
}
