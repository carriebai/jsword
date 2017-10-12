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

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

import org.crosswire.common.util.Convert;

/**
 * TableModel using a Map internally. Note that an AbstractTableModel (this is-a
 * AbstractTableModel) reports changes to the data to the table itself. However
 * since a Map does not have a addChangeListener interface we can't do the same
 * - SO if you change the Map while we are displaying it then don't expect the
 * changes to be automatically reflected in the JTable.
 * 
 * @see gnu.gpl.License The GNU General Public License for details.
 * @author Joe Walker
 */
public class MapTableModel extends AbstractTableModel {
    /**
     * Create an internal store from a 2D array
     */
    public MapTableModel() {
        this(null);
    }

    /**
     * Create an internal store from a 2D array
     * 
     * @param map
     *            The table to model
     */
    public MapTableModel(Map map) {
        list = new ArrayList<StringPair>();
        setMap(map);
    }

    /**
     * Change the map that we report on
     * 
     * @param map
     *            The map we are getting our data from
     */
    public final void setMap(Map<Object, Object> map) {
        this.map = map;
        list.clear();
        if (map != null) {
            Iterator<Map.Entry<Object, Object>> iter = map.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry<Object, Object> me = iter.next();
                Object k = me.getKey();
                Object v = me.getValue();
                if (k == null || v == null) {
                    // perhaps log a warning?
                    continue;
                }
                String key = k.toString().trim();
                String value = v.toString().trim();
                if (key.length() == 0) {
                    // perhaps log a warning?
                    continue;
                }
                list.add(new StringPair(key, value));
            }
        }
        fireTableDataChanged();
    }

    /**
     * @param key
     * @param aValue
     */
    public void add(String key, String aValue) {
        String value = aValue;
        if (value == null) {
            value = "";
        }

        if (key == null || key.length() == 0) {
            return;
        }

        if (map.containsKey(key) && value.equals(map.get(key))) {
            return;
        }

        map.put(key, value);
        setMap(map);
    }

    /**
     * @param key
     */
    public void remove(String key) {
        if (map != null) {
            map.remove(key);
            setMap(map);
        }
    }

    /**
     * @param oldkey
     * @param newkey
     * @param newvalue
     */
    public void update(String oldkey, String newkey, String newvalue) {
        if (map != null) {
            if (!oldkey.equals(newkey)) {
                map.remove(oldkey);
            }

            add(newkey, newvalue);
        }
    }

    /**
     * Return a string version of the current value
     * 
     * @return The current value
     */
    public String getValue() {
        return Convert.map2String(map);
    }

    /**
     * How many Cols are there in this store
     * 
     * @return The number of columns
     */
    @Override
    public int getColumnCount() {
        if (list == null) {
            return 0;
        }
        return 2;
    }

    /**
     * How many Rows are there in this store
     * 
     * @return the number of row in the TableModel = elements in the map
     */
    @Override
    public int getRowCount() {
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    /**
     * Return the Object at row, col
     * 
     * @param row
     *            The element in the list
     * @param col
     *            1=keys, 2=values
     * @return The key/value of the given element
     */
    @Override
    public Object getValueAt(int row, int col) {
        if (list == null) {
            return null;
        }

        StringPair entry = list.get(row);
        if (col == 0) {
            return entry.getKey();
        }
        return entry.getValue();
    }

    /**
     * Get the default class
     * 
     * @param col
     *            1=keys, 2=values
     * @return String.class
     */
    @Override
    public Class<String> getColumnClass(int col) {
        return String.class;
    }

    /**
     * The name of the of the <code>col</code>th column
     * 
     * @param col
     *            The column index
     * @return The column name
     */
    @Override
    public String getColumnName(int col) {
        return colNames[col];
    }

    /**
     * The name of the of the <code>col</code>th column
     * 
     * @param col
     *            The column index
     * @param name
     *            The column name
     */
    public void setColumnName(int col, String name) {
        colNames[col] = name;
    }

    /**
     * Serialization support.
     * 
     * @param is
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private void readObject(ObjectInputStream is) throws IOException, ClassNotFoundException {
        list = new ArrayList<StringPair>();
        is.defaultReadObject();
    }

    /**
     * A simple holder of a key/value pair of Strings.
     */
    private static final class StringPair implements Serializable {
        /**
         * @param k
         *            The non-null key.
         * @param v
         *            The non-null value.
         */
        StringPair(String k, String v) {
            key = k;
            value = v;
        }

        /**
         * @return Returns the key.
         */
        public String getKey() {
            return key;
        }

        /**
         * @return Returns the value.
         */
        public String getValue() {
            return value;
        }

        /**
         * <code>key</code> is the string representation of a Map entry key
         */
        private String key;

        /**
         * <code>value</code> is the string representation of a Map entry value
         */
        private String value;

        /**
         * Serialization ID
         */
        private static final long serialVersionUID = 1730905297956834949L;
    }

    /**
     * The List that is a copy of the list. A list is used for direct access
     * performance.
     */
    private transient List<StringPair> list;

    /**
     * The backing map
     */
    private Map<Object, Object> map;

    /**
     * The default column names
     */
    private String[] colNames = new String[] {
            CWOtherMsg.lookupText("Keys"), CWOtherMsg.lookupText("Values"),
    };

    /**
     * Serialization ID
     */
    private static final long serialVersionUID = 3546365041277352241L;
}
