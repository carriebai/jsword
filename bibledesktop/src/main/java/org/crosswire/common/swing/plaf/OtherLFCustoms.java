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

import javax.swing.BorderFactory;
import javax.swing.UIManager;
import javax.swing.border.Border;

/**
 * Customizations for other LF other than Windows and Metal.
 * 
 * @see gnu.gpl.License The GNU General Public License for details.
 * @author Willie Thean
 */
public class OtherLFCustoms extends AbstractLFCustoms {
    /**
     * Default constructor.
     */
    public OtherLFCustoms() {
        super();
    }

    /**
     * Install other platform specific UI defaults that we're not aware of.
     */
    @Override
    protected void initPlatformUIDefaults() {
        Border tabbedPanePanelBorder = BorderFactory.createEtchedBorder();
        Border panelSelectBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);

        Object[] otherUIDefaults = new Object[] {
                "TabbedPanePanel.border", tabbedPanePanelBorder,
                "SelectPanel.border", panelSelectBorder
        };

        UIManager.getDefaults().putDefaults(otherUIDefaults);
    }
}
