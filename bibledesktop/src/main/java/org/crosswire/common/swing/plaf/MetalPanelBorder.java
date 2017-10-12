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

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import javax.swing.border.AbstractBorder;
import javax.swing.plaf.UIResource;
import javax.swing.plaf.metal.MetalLookAndFeel;

/**
 * A class that provides a border that matches MetalBorders.ScrollPaneBorder.
 * 
 * @see gnu.gpl.License The GNU General Public License for details.
 * @author Willie Thean
 */
public final class MetalPanelBorder extends AbstractBorder implements UIResource {
    public static final int TOP = 1;
    public static final int LEFT = 2;
    public static final int BOTTOM = 4;
    public static final int RIGHT = 8;

    /**
     * Default constructor.
     */
    public MetalPanelBorder() {
        insets = new Insets(insetTop, insetLeft, insetBottom, insetRight);
    }

    /**
     * Create a MetalPanelBorder instance where the border visbility (top, left,
     * bottom and right border) is controlled by the bit mask
     * <CODE>borderFlags</CODE>.
     * 
     * @param borderFlags
     *            Match flags, a bit mask that may include TOP, LEFT, BOTTOM,
     *            and RIGHT
     */
    public MetalPanelBorder(int borderFlags) {
        flags = 0 | borderFlags;

        if ((flags & TOP) != TOP) {
            insetTop = 0;
        }

        if ((flags & LEFT) != LEFT) {
            insetLeft = 0;
        }

        if ((flags & BOTTOM) != BOTTOM) {
            insetBottom = 0;
        }

        if ((flags & RIGHT) != RIGHT) {
            insetRight = 0;
        }

        insets = new Insets(insetTop, insetLeft, insetBottom, insetRight);
    }

    /* (non-Javadoc)
     * @see javax.swing.border.AbstractBorder#paintBorder(java.awt.Component, java.awt.Graphics, int, int, int, int)
     */
    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
        g.translate(x, y);

        if ((flags & TOP) == TOP) {
            g.setColor(MetalLookAndFeel.getControlDarkShadow());
            g.drawLine(0, 0, w - 2, 0);
        }

        if ((flags & LEFT) == LEFT) {
            g.drawLine(0, 0, 0, h - 2);
        }

        if ((flags & BOTTOM) == BOTTOM) {
            g.drawLine(0, h - 2, w - 2, h - 2);
            g.setColor(MetalLookAndFeel.getControlHighlight());
            g.drawLine(1, h - 1, w - 1, h - 1);
        }

        if ((flags & RIGHT) == RIGHT) {
            g.setColor(MetalLookAndFeel.getControlDarkShadow());
            g.drawLine(w - 2, h - 2, w - 2, 0);
            g.setColor(MetalLookAndFeel.getControlHighlight());
            g.drawLine(w - 1, h - 1, w - 1, 1);
        }

        g.translate(-x, -y);
    }

    /* (non-Javadoc)
     * @see javax.swing.border.AbstractBorder#getBorderInsets(java.awt.Component)
     */
    @Override
    public Insets getBorderInsets(Component c) {
        return insets;
    }

    private int insetTop = 1;
    private int insetLeft = 1;
    private int insetBottom = 2;
    private int insetRight = 2;

    private int flags;

    private Insets insets;

    /**
     * Serialization ID
     */
    private static final long serialVersionUID = 7929433986066846750L;
}
