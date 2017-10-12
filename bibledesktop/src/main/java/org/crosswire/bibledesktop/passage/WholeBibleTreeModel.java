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

import javax.swing.tree.DefaultTreeModel;

/**
 * The PassageTreeModel class implements TreeModel using various custom
 * TreeNodes, and simply extending DefaultTreeModel.
 * 
 * @see gnu.gpl.License for license details.
 * @author Joe Walker
 * @see DefaultTreeModel
 */
public class WholeBibleTreeModel extends DefaultTreeModel {
    /**
     * Basic constructor.
     */
    public WholeBibleTreeModel() {
        super(WholeBibleTreeNode.getRootNode());
    }

    /**
     * Serialization ID
     */
    private static final long serialVersionUID = 3688785881985004853L;
}
