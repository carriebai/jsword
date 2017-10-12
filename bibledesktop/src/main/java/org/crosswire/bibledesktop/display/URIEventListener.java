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

import java.util.EventListener;

/**
 * Implement URIEventListener to receive URIEvents whenever someone activates an
 * URI.
 * 
 * @see gnu.gpl.License for license details.
 * @author DM Smith
 */
public interface URIEventListener extends EventListener {
    /**
     * This method is called to indicate that an URI can be processed.
     * 
     * @param ev
     *            Describes the URI
     */
    void activateURI(URIEvent ev);

    /**
     * This method is called to indicate that the mouse has entered the URI.
     * 
     * @param ev
     *            Describes the URI
     */
    void enterURI(URIEvent ev);

    /**
     * This method is called to indicate that the mouse has left the URI.
     * 
     * @param ev
     *            Describes the URI
     */
    void leaveURI(URIEvent ev);
}
