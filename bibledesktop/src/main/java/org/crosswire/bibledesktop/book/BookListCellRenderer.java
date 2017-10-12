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
package org.crosswire.bibledesktop.book;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

import org.crosswire.bibledesktop.BDMsg;
import org.crosswire.bibledesktop.book.install.BookIcon;
import org.crosswire.common.swing.GuiUtil;
import org.crosswire.jsword.book.Book;

/**
 * A custom list view that paints icons alongside the words, with a ToolTip of
 * the name of the Book.
 * 
 * @see gnu.gpl.License for license details.
 * @author Joe Walker
 * @author DM Smith
 */
public class BookListCellRenderer extends DefaultListCellRenderer {
    /**
     * Constructs a default renderer object for an item in a list, using full
     * names.
     */
    public BookListCellRenderer() {
        this(false);
    }

    /**
     * Constructs a renderer object for an item in a list, using abbreviated
     * names if desired.
     * 
     * @param abbreviated
     *            use the initials in the list.
     */
    public BookListCellRenderer(boolean abbreviated) {
        super();
        this.abbreviated = abbreviated;
        GuiUtil.applyDefaultOrientation(this);
    }

    /**
     * @return the abbreviated
     */
    public boolean isAbbreviated() {
        return abbreviated;
    }

    /**
     * @param newAbbreviated
     *            the abbreviated to set
     */
    public void setAbbreviated(boolean newAbbreviated) {
        this.abbreviated = newAbbreviated;
    }

    /* (non-Javadoc)
     * @see javax.swing.DefaultListCellRenderer#getListCellRendererComponent(javax.swing.JList, java.lang.Object, int, boolean, boolean)
     */
    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean selected, boolean focus) {
        // Do the default rendering
        Component comp = super.getListCellRendererComponent(list, value, index, selected, focus);

        // Do our rendering
        setToolTipText(null);

        if (value == null) {
            // TRANSLATOR: This is the replacement text for a blank book name in a list.
            setText(BDMsg.gettext("None"));
            setEnabled(false);
        }

        // Hack to allow us to use PROTOTYPE_BOOK_NAME as a prototype value
        if (value instanceof Book) {
            Book book = (Book) value;
            String name = book.getName();

            setText(abbreviated ? book.getInitials() : name);
            setToolTipText(name);
            setIcon(BookIcon.getIcon(book));
        }

        return comp;
    }

    /**
     * If true then the initials of a book are shown, otherwise the full name.
     */
    private boolean abbreviated;

    /**
     * Make sure that book names are not too wide
     */
    public static final String PROTOTYPE_BOOK_NAME = "0123456789";

    /**
     * Serialization ID
     */
    private static final long serialVersionUID = 3978138859576308017L;
}
