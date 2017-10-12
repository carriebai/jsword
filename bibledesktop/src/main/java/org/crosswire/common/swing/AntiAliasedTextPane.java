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
package org.crosswire.common.swing;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JTextPane;

/**
 * An extension of JTextPane that does Anti-Aliasing.
 * JDK15(joe): we will need to take a bit of care not clashing with J2SE5 AA
 * 
 * @see gnu.gpl.License The GNU General Public License for details.
 * @author Joe Walker
 */
public class AntiAliasedTextPane extends JTextPane {
    /* (non-Javadoc)
     * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
     */
    @Override
    public void paintComponent(Graphics g) {
        if (g instanceof Graphics2D) {
            Graphics2D g2 = (Graphics2D) g;

            if (antiAliasing) {
                g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            }
        }
        super.paintComponent(g);
    }

    /**
     * @return Returns the anti aliasing status.
     */
    public static boolean isAntiAliasing() {
        return antiAliasing;
    }

    /**
     * @param antiAliasing
     *            The new anti aliasing status.
     */
    public static void setAntiAliasing(boolean antiAliasing) {
        AntiAliasedTextPane.antiAliasing = antiAliasing;
        // Set it system wide for the next run
        System.setProperty("swing.aatext", Boolean.toString(antiAliasing));

    }

    /**
     * Do we anti-alias the text box?
     */
    private static boolean antiAliasing;

    /**
     * Serialization ID
     */
    private static final long serialVersionUID = 3256728398477734965L;
}
