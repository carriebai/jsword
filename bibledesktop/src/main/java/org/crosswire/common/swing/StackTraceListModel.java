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

import javax.swing.ListModel;
import javax.swing.event.ListDataListener;

import org.crosswire.common.util.StackTrace;

/**
 * To iterate over the full function names.
 * 
 * @see gnu.gpl.License The GNU General Public License for details.
 * @author Joe Walker
 * @see javax.swing.ListModel
 * @see javax.swing.JList
 */
public class StackTraceListModel implements ListModel {
    /**
     * @param st
     */
    public StackTraceListModel(StackTrace st) {
        this.st = st;
    }

    /**
     * Returns the length of the list
     */
    @Override
    public int getSize() {
        return st.countStackElements();
    }

    /**
     * Returns the value at the specified index
     */
    @Override
    public Object getElementAt(int index) {
        return st.getFullFunctionName(index);
    }

    /**
     * Ignore this because the trace will not change
     */
    @Override
    public void addListDataListener(ListDataListener li) {
    }

    /**
     * Ignore this because the trace will not change
     */
    @Override
    public void removeListDataListener(ListDataListener li) {
    }

    private final StackTrace st;
}
