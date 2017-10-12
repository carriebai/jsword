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
package org.crosswire.common.swing.desktop;

import java.awt.Insets;
import java.awt.LayoutManager;

import javax.swing.JPanel;
import javax.swing.UIManager;

import org.crosswire.common.swing.GuiUtil;

/**
 * A JPanel class where it's child components will paint on top of its border.
 * 
 * @see gnu.gpl.License The GNU General Public License for details.
 * @author Willie Thean
 */
public class TabbedPanePanel extends JPanel {
    public TabbedPanePanel() {
        super();
        init();
    }

    public TabbedPanePanel(boolean isDoubleBuffered) {
        super(isDoubleBuffered);
        init();
    }

    public TabbedPanePanel(LayoutManager layout) {
        super(layout);
        init();
    }

    public TabbedPanePanel(LayoutManager layout, boolean isDoubleBuffered) {
        super(layout, isDoubleBuffered);
        init();
    }

    private void init() {
        this.setBorder(UIManager.getBorder("TabbedPanePanel.border"));
        GuiUtil.applyDefaultOrientation(this);
    }

    /**
     * If we setBorder on this JPanel, the border width will be part of the
     * insets. We return an insets of 0 so the child components will paint on
     * top of the border.
     */
    @Override
    public Insets getInsets() {
        return new Insets(0, 0, 0, 0);
    }

    /**
     * Serialization ID
     */
    private static final long serialVersionUID = 5254437923545591019L;
}
