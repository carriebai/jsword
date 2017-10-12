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
package org.crosswire.bibledesktop.book.install;

import java.util.Set;

import javax.swing.tree.DefaultMutableTreeNode;

import org.crosswire.jsword.book.Book;
import org.crosswire.jsword.book.BookSet;

/**
 * A Node for a book in a tree. It may be a property of a BookMetaData or the
 * BookMetaData itself.
 * 
 * @see gnu.gpl.License for license details.
 * @author DM Smith
 */
public class BookNode extends DefaultMutableTreeNode {

    public BookNode(Object node, BookSet books, int level, Object... grouping) {
        setUserObject(node);
        if (level < grouping.length) {
            String key = (String) grouping[level];
            Set<Object> group = books.getGroup(key);
            for (Object value : group) {
                BookSet subBooks = books.filter(key, value);
                add(new BookNode(value, subBooks, level + 1, grouping));
            }
        } else if (books != null) {
            for (Book book : books) {
                add(new BookNode(book, null, level + 1, grouping));
            }
        }
    }

    /**
     * Serialization ID
     */
    private static final long serialVersionUID = 3256442525387602231L;
}
