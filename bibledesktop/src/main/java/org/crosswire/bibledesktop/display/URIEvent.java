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

import java.util.EventObject;

/**
 * A URIEvent happens whenever a user selects a URI.
 * 
 * @see gnu.gpl.License for license details.
 * @author DM Smith
 */
public class URIEvent extends EventObject {
    /**
     * For when a command has been made
     * 
     * @param source
     *            The thing that started this off
     */
    public URIEvent(Object source, String scheme, String uri) {
        super(source);

        this.scheme = scheme;
        this.uri = uri;
    }

    /**
     * @return Returns the scheme.
     */
    public String getScheme() {
        return scheme;
    }

    /**
     * @return Returns the uri.
     */
    public String getURI() {
        return uri;
    }

    private String scheme;
    private String uri;

    /**
     * Serialization ID
     */
    private static final long serialVersionUID = 3978710575457187634L;
}
