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
package org.crosswire.common.swing.plaf;

/**
 * Contains base UI defaults for all platforms.
 * 
 * @see gnu.gpl.License The GNU General Public License for details.
 * @author Willie Thean
 */
public abstract class AbstractLFCustoms {
    /**
     * Constructor.
     */
    public AbstractLFCustoms() {
    }

    /**
     * Calling this method installs base and platform specfic UI defaults.
     */
    public void initUIDefaults() {
        initBaseUIDefaults();
        initPlatformUIDefaults();
    }

    /**
     * Init UI Defaults value applicable to all platforms.
     */
    private void initBaseUIDefaults() {
        // Specify defaults applicable to all platforms here
    }

    /**
     * This method does nothing. Subclass should override this to install
     * platform specific UI defaults.
     */
    protected abstract void initPlatformUIDefaults();
}
