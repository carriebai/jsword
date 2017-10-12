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

import javax.swing.AbstractListModel;

import org.crosswire.jsword.passage.Key;

/**
 * A simple implementation of ListModel that is backed by a SortedSet.
 * 
 * @see gnu.gpl.License for license details.
 * @author Joe Walker
 */
public class KeyListListModel extends AbstractListModel {
    /**
     * Constructor for ListListModel.
     */
    public KeyListListModel(Key keys) {
        this.keys = keys;
    }

    /* (non-Javadoc)
     * @see javax.swing.ListModel#getSize()
     */
    @Override
    public int getSize() {
        return keys != null ? keys.getCardinality() : 0;
    }

    /**
     * There must be a faster way of doing this?
     * 
     * @see javax.swing.ListModel#getElementAt(int)
     */
    @Override
    public Object getElementAt(int index) {
        return keys != null ? keys.get(index) : null;
    }

    private Key keys;

    /**
     * Serialization ID
     */
    private static final long serialVersionUID = 3546356240990286645L;
}
