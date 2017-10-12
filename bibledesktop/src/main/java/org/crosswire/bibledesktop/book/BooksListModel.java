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

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.event.ListDataListener;

import org.crosswire.jsword.book.Book;
import org.crosswire.jsword.book.BookFilter;
import org.crosswire.jsword.book.BookList;
import org.crosswire.jsword.book.Books;
import org.crosswire.jsword.book.BooksEvent;
import org.crosswire.jsword.book.BooksListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * BooksListModel creates a Swing ListModel from the available Bibles. I would
 * normally implement BooksListener in an inner class however doing that would
 * stop me calling fireInterval*() in AbstractListModel because that is a
 * protected method and the inner class is neither in the same package or a sub
 * class.
 * 
 * @see gnu.gpl.License for license details.
 * @author Joe Walker
 */
public class BooksListModel extends AbstractListModel {
    /**
     * Basic constructor
     */
    public BooksListModel() {
        this(null, null);
    }

    /**
     * Basic constructor
     */
    public BooksListModel(BookFilter filter) {
        this(filter, Books.installed(), null);
    }

    /**
     * Basic constructor, redefining ordering.
     */
    public BooksListModel(BookFilter filter, Comparator<Book> comp) {
        this(filter, Books.installed(), comp);
    }

    /**
     * Basic constructor for a filtered list of books, ordered as requested.
     */
    public BooksListModel(BookFilter filter, BookList bookList, Comparator<Book> comparator) {
        this.filter = filter;
        this.bookList = bookList;
        this.comparator = comparator;

        cacheData();
    }

    /* (non-Javadoc)
     * @see javax.swing.ListModel#getSize()
     */
    @Override
    public synchronized int getSize() {
        return books.size();
    }

    /* (non-Javadoc)
     * @see javax.swing.ListModel#getElementAt(int)
     */
    @Override
    public synchronized Object getElementAt(int index) {
        // PARANOIA(joe): this check shouldn't be needed
        if (index > books.size()) {
            log.error("trying to get book at {} when there are only {} known books.", Integer.toString(index), Integer.toString(books.size()));
            return null;
        }

        return books.get(index);
    }

    /**
     * Returns the index-position of the specified object in the list.
     * 
     * @param test
     *            the object to find
     * @return an int representing the index position, where 0 is the first
     *         position
     */
    public synchronized int getIndexOf(Object test) {
        return books.indexOf(test);
    }

    /**
     * @param filter
     */
    public void setFilter(BookFilter filter) {
        synchronized (this) {
            this.filter = filter;
        }
        cacheData();

        fireContentsChanged(this, 0, getSize());
    }

    /* (non-Javadoc)
     * @see javax.swing.ListModel#addListDataListener(javax.swing.event.ListDataListener)
     */
    @Override
    public void addListDataListener(ListDataListener li) {
        if (listenerList.getListenerCount() == 0) {
            bookList.addBooksListener(listener);
        }

        super.addListDataListener(li);
    }

    /* (non-Javadoc)
     * @see javax.swing.ListModel#removeListDataListener(javax.swing.event.ListDataListener)
     */
    @Override
    public void removeListDataListener(ListDataListener li) {
        super.removeListDataListener(li);

        if (listenerList.getListenerCount() == 0) {
            bookList.removeBooksListener(listener);
        }
    }

    /**
     * Setup the data-stores of the current Bibles and drivers
     */
    protected final synchronized void cacheData() {
        books = new ArrayList<Book>();
        books.addAll(bookList.getBooks(filter));
        Collections.sort(books, comparator);
    }

    /**
     * So we can get a handle on what Bibles there are
     */
    class CustomListDataListener implements BooksListener {
        /* (non-Javadoc)
         * @see org.crosswire.jsword.book.BooksListener#bookAdded(org.crosswire.jsword.book.BooksEvent)
         */
        @Override
        public void bookAdded(BooksEvent ev) {
            int oldsize = getSize();
            cacheData();
            fireContentsChanged(ev.getSource(), 0, oldsize);
        }

        /* (non-Javadoc)
         * @see org.crosswire.jsword.book.BooksListener#bookRemoved(org.crosswire.jsword.book.BooksEvent)
         */
        @Override
        public void bookRemoved(BooksEvent ev) {
            int oldsize = getSize();
            cacheData();
            fireContentsChanged(ev.getSource(), 0, oldsize);
        }
    }

    /* (non-Javadoc)
     * @see javax.swing.AbstractListModel#fireContentsChanged(java.lang.Object, int, int)
     */
    @Override
    protected void fireContentsChanged(Object source, int index0, int index1) {
        super.fireContentsChanged(source, index0, index1);
    }

    /**
     * Serialization support.
     * 
     * @param is
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private void readObject(ObjectInputStream is) throws IOException, ClassNotFoundException {
        listener = new CustomListDataListener();
        filter = null;
        // This is not quite right. Probably should write out the Book initials
        // and read them in here.
        // But at this time we don't serialize views.
        bookList = Books.installed();
        books = new ArrayList<Book>();

        is.defaultReadObject();
    }

    /**
     * The list of books in this tree
     */
    private transient BookList bookList;

    /**
     * The filter used to choose Bibles
     */
    private transient BookFilter filter;

    /**
     * The listener
     */
    private transient CustomListDataListener listener = new CustomListDataListener();

    /**
     * The array of versions. All methods that access this variable have been
     * marked synchronized to ensure that one thread can't update the list of
     * books while another is trying to create a JList based on this class.
     */
    protected transient List<Book> books;

    /**
     * The sort algorithm to use.
     */
    protected transient Comparator<Book> comparator;

    /**
     * The log stream
     */
    private static final Logger log = LoggerFactory.getLogger(BooksListModel.class);

    /**
     * Serialization ID
     */
    private static final long serialVersionUID = 3257568408165036595L;
}
