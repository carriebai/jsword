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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

/**
 * A ComboBoxModel for selecting entries from a map.
 * 
 * @see gnu.gpl.License for license details.
 * @author DM Smith
 */
public class MapComboBoxModel extends AbstractListModel implements ComboBoxModel {
    /**
     * Simple ctor for an entry from a map.
     */
    public MapComboBoxModel(Map<?, ?> map) {
        list = new ArrayList<Object>(map.entrySet());
    }

    /* (non-Javadoc)
     * @see javax.swing.ComboBoxModel#setSelectedItem(java.lang.Object)
     */
    @Override
    public void setSelectedItem(Object newSelection) {
        selected = newSelection;
        fireContentsChanged(this, -1, -1);
    }

    /* (non-Javadoc)
     * @see javax.swing.ComboBoxModel#getSelectedItem()
     */
    @Override
    public Object getSelectedItem() {
        return selected;
    }

    /* (non-Javadoc)
     * @see javax.swing.ListModel#getSize()
     */
    @Override
    public int getSize() {
        return list.size();
    }

    /* (non-Javadoc)
     * @see javax.swing.ListModel#getElementAt(int)
     */
    @Override
    public Object getElementAt(int index) {
        return list.get(index);
    }

    private List<?> list;

    /**
     * What is currently selected?
     */
    private transient Object selected;

    /**
     * Serialization ID
     */
    private static final long serialVersionUID = 4830813224135775043L;
}
