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

import java.awt.Component;

import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

import org.crosswire.jsword.passage.Key;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A specialization of DefaultTreeCellRenderer that knows how to get names from
 * Keys.
 * 
 * @see gnu.gpl.License for license details.
 * @author Joe Walker
 */
public class KeyTreeCellRenderer extends DefaultTreeCellRenderer {
    /* (non-Javadoc)
     * @see javax.swing.tree.TreeCellRenderer#getTreeCellRendererComponent(javax.swing.JTree, java.lang.Object, boolean, boolean, boolean, int, boolean)
     */
    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean isselected, boolean expanded, boolean leaf, int row, boolean focus) {
        super.getTreeCellRendererComponent(tree, value, isselected, expanded, leaf, row, focus);

        if (value instanceof KeyTreeNode) {
            KeyTreeNode keytn = (KeyTreeNode) value;
            Key key = keytn.getKey();
            if (key != null) {
                setText(key.getName());
            }
        } else { // if (value != null)
            log.warn("value is not a key: {}", value.getClass().getName());
        }

        return this;
    }

    /**
     * The log stream
     */
    private static final Logger log = LoggerFactory.getLogger(KeyTreeCellRenderer.class);

    /**
     * Serialization ID
     */
    private static final long serialVersionUID = 3545232531516765241L;
}
