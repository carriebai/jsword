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
package org.crosswire.bibledesktop.display;

import java.awt.Component;
import java.beans.PropertyChangeEvent;

import org.crosswire.bibledesktop.passage.KeyChangeListener;
import org.crosswire.jsword.book.Book;
import org.crosswire.jsword.passage.Key;

/**
 * An implementation of BookDataDisplay that simply proxies all requests to an
 * underlying BookDataDisplay.
 * <p>
 * Useful for chaining a few BookDataDisplays together to add functionality
 * component by component.
 * </p>
 * 
 * @see gnu.gpl.License for license details.
 * @author Joe Walker
 */
public class ProxyBookDataDisplay implements BookDataDisplay {
    /**
     * Setup the proxy
     */
    public ProxyBookDataDisplay(BookDataDisplay proxy) {
        this.proxy = proxy;
    }

    /**
     * Accessor for the proxy
     * 
     * @return Returns the proxy.
     */
    protected BookDataDisplay getProxy() {
        return proxy;
    }

    /* (non-Javadoc)
     * @see org.crosswire.bibledesktop.display.BookDataDisplay#addKeyChangeListener(org.crosswire.bibledesktop.passage.KeyChangeListener)
     */
    @Override
    public void addKeyChangeListener(KeyChangeListener listener) {
        proxy.addKeyChangeListener(listener);
    }

    /* (non-Javadoc)
     * @see org.crosswire.bibledesktop.display.BookDataDisplay#removeKeyChangeListener(org.crosswire.bibledesktop.passage.KeyChangeListener)
     */
    @Override
    public void removeKeyChangeListener(KeyChangeListener listener) {
        proxy.removeKeyChangeListener(listener);
    }

    /* (non-Javadoc)
     * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        proxy.propertyChange(evt);
    }

    /* (non-Javadoc)
     * @see org.crosswire.bibledesktop.display.BookDataDisplay#addURIEventListener(org.crosswire.bibledesktop.display.URIEventListener)
     */
    @Override
    public void addURIEventListener(URIEventListener listener) {
        proxy.addURIEventListener(listener);
    }

    /* (non-Javadoc)
     * @see org.crosswire.bibledesktop.display.BookDataDisplay#removeURIEventListener(org.crosswire.bibledesktop.display.URIEventListener)
     */
    @Override
    public void removeURIEventListener(URIEventListener listener) {
        proxy.removeURIEventListener(listener);
    }

    /* (non-Javadoc)
     * @see org.crosswire.bibledesktop.display.BookDataDisplay#copy()
     */
    @Override
    public void copy() {
        proxy.copy();
    }

    /* (non-Javadoc)
     * @see org.crosswire.bibledesktop.display.BookDataDisplay#getComponent()
     */
    @Override
    public Component getComponent() {
        return proxy.getComponent();
    }

    /* (non-Javadoc)
     * @see org.crosswire.bibledesktop.display.BookDataDisplay#clearBookData()
     */
    @Override
    public void clearBookData() {
        setBookData(null, null);
    }

    /* (non-Javadoc)
     * @see org.crosswire.bibledesktop.display.BookDataDisplay#setBookData(org.crosswire.jsword.book.Book[], org.crosswire.jsword.passage.Key)
     */
    @Override
    public void setBookData(Book[] books, Key key) {
        proxy.setBookData(books, key);
    }

    /* (non-Javadoc)
     * @see org.crosswire.bibledesktop.display.BookDataDisplay#setCompareBooks(boolean)
     */
    @Override
    public void setCompareBooks(boolean compare) {
        proxy.setCompareBooks(compare);
    }

    /* (non-Javadoc)
     * @see org.crosswire.bibledesktop.display.BookDataDisplay#refresh()
     */
    @Override
    public void refresh() {
        proxy.refresh();
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return proxy.toString();
    }

    /* (non-Javadoc)
     * @see org.crosswire.bibledesktop.display.BookDataDisplay#getKey()
     */
    @Override
    public Key getKey() {
        return getProxy().getKey();
    }

    /* (non-Javadoc)
     * @see org.crosswire.bibledesktop.display.BookDataDisplay#getBooks()
     */
    @Override
    public Book[] getBooks() {
        return getProxy().getBooks();
    }

    /* (non-Javadoc)
     * @see org.crosswire.bibledesktop.display.BookDataDisplay#getFirstBook()
     */
    @Override
    public Book getFirstBook() {
        return getProxy().getFirstBook();
    }

    /**
     * The component to which we proxy
     */
    private BookDataDisplay proxy;
}
