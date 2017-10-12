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

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

/**
 * Customizations to Windows LF for tabs.
 * 
 * @see gnu.gpl.License The GNU General Public License for details.
 * @author Willie Thean
 */
public class WindowsLFCustoms extends AbstractLFCustoms {
    /**
     * Default constructor.
     */
    public WindowsLFCustoms() {
        super();
    }

    /**
     * Install Windows platform specific UI defaults.
     */
    @Override
    protected void initPlatformUIDefaults() {
        Border tabbedPanePanelBorder = null;
        Color standardBorderColor = null;
        Object windowsScrollPaneborder = UIManager.get("ScrollPane.border");
        if (windowsScrollPaneborder != null) {
            if (windowsScrollPaneborder instanceof LineBorder) {
                standardBorderColor = ((LineBorder) windowsScrollPaneborder).getLineColor();
                tabbedPanePanelBorder = new LineBorder(standardBorderColor);
            } else {
                tabbedPanePanelBorder = BorderFactory.createEmptyBorder(1, 1, 1, 1);
            }
        }

        Border panelSelectBorder = BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(1, 1, 0, 1, standardBorderColor), BorderFactory
                .createEmptyBorder(5, 5, 5, 5));

        Object[] windowsUIDefaults = new Object[] {
                "BibleViewPane.TabbedPaneUI", WindowsBorderlessTabbedPaneUI.createUI(null),
                "TabbedPanePanel.border", tabbedPanePanelBorder,
                "StandardBorder.color", standardBorderColor,
                "SelectPanel.border", panelSelectBorder
        };

        UIManager.getDefaults().putDefaults(windowsUIDefaults);
    }
}
