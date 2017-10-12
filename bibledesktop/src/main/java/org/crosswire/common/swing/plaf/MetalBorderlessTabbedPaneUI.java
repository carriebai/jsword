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

import java.awt.Insets;

import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.metal.MetalTabbedPaneUI;

/**
 * Provides customization to MetalLF Tabbed panes.
 * 
 * @see gnu.gpl.License The GNU General Public License for details.
 * @author Willie Thean
 */
public class MetalBorderlessTabbedPaneUI extends MetalTabbedPaneUI {
    public static ComponentUI createUI(JComponent x) {
        return new MetalBorderlessTabbedPaneUI();
    }

    /**
     * Return a new Insets(0, 0, 0, 0). <CODE>tabPlacement</CODE>. is ignored.
     * 
     * @param tabPlacement
     *            ignored
     * @return a new Insets(0, 0, 0, 0)
     */
    @Override
    protected Insets getContentBorderInsets(int tabPlacement) {
        return new Insets(0, 0, 0, 0);
    }

}
