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
 * © CrossWire Bible Society, 2007 - 2016
 */
package org.crosswire.bibledesktop.book;

import java.util.EventListener;

/**
 * Implement BookSelectListener to receive notification that the list of
 * selected books has been updated.
 * 
 * @see gnu.gpl.License for license details.
 * @author DM Smith
 */
public interface BookSelectListener extends EventListener {
    /**
     * Notify that the list of Books has changed.
     * 
     * @param ev
     *            Describes the change
     */
    void booksChosen(BookSelectEvent ev);
}
