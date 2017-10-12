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

import java.awt.Component;

import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

import org.crosswire.bibledesktop.BDMsg;
import org.crosswire.jsword.book.Book;
import org.crosswire.jsword.book.BookCategory;

/**
 * Provides appropriate icons for books.
 * 
 * @see gnu.gpl.License for license details.
 * @author DM Smith
 */
public class BookTreeCellRenderer extends DefaultTreeCellRenderer {

    /* (non-Javadoc)
     * @see javax.swing.tree.DefaultTreeCellRenderer#getTreeCellRendererComponent(javax.swing.JTree, java.lang.Object, boolean, boolean, boolean, int, boolean)
     */
    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean focus) {
        super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
        String tooltip = null;
        if (leaf && value instanceof BookNode) {
            Object obj = ((BookNode) value).getUserObject();
            if (obj instanceof Book) {
                Book book = (Book) obj;
                setText(book.getName() + " (" + book.getInitials() + ")");
                setLeafIcon(BookIcon.getIcon(book));

                if (book.isQuestionable()) {
                    tooltip = BookCategory.QUESTIONABLE.toString();
                }

                if (!book.isSupported()) {
                    // TRANSLATOR: The book is not supported by JSword
                    tooltip = BDMsg.gettext("Unsupported");
                } else if (book.isLocked()) {
                    // TRANSLATOR: The book is enciphered and needs to be unlocked
                    tooltip = BDMsg.gettext("Locked");
                }
            }
        }

        setToolTipText(tooltip);
        return this;
    }

    /**
     * Serialization ID
     */
    private static final long serialVersionUID = -942626483282049048L;
}
