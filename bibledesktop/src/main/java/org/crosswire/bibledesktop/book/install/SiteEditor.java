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

import org.crosswire.jsword.book.install.Installer;

/**
 * A SiteEditor allows for the editing of a site.
 * 
 * @see gnu.gpl.License for license details.
 * @author DM Smith
 */
public interface SiteEditor {
    /**
     * Establish the installer for this SiteEditor.
     * 
     * @param newInstaller
     */
    void setInstaller(Installer newInstaller);

    /**
     * Get the installer associated with this SiteEditor
     * 
     * @return the installer
     */
    Installer getInstaller();

    /**
     * Change the Editable state of this SiteEditor.
     * 
     * @param editable
     */
    void setEditable(boolean editable);

    /**
     * Save the state of this SiteEditor.
     */
    void save();

    /**
     * Set the state of this SiteEditor to the last save.
     */
    void reset();
}
