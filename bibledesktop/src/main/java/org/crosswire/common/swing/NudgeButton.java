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

import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * A nudge button set based on this dialog - even down to passing on edited
 * source.
 * 
 * @see gnu.gpl.License The GNU General Public License for details.
 * @author Joe Walker
 */
public class NudgeButton extends JPanel {
    /**
     *
     */
    public NudgeButton() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(up);
        add(down);
    }

    /**
     *
     */
    public void setUpEnabled(boolean active) {
        up.setEnabled(active);
    }

    /**
     *
     */
    public void setDownEnabled(boolean active) {
        down.setEnabled(active);
    }

    /**
     *
     */
    public boolean getUpEnabled() {
        return up.isEnabled();
    }

    /**
     *
     */
    public boolean getDownEnabled() {
        return down.isEnabled();
    }

    /**
     *
     */
    public void addUpActionListener(ActionListener al) {
        up.addActionListener(al);
    }

    /**
     *
     */
    public void removeUpActionListener(ActionListener al) {
        up.removeActionListener(al);
    }

    /**
     *
     */
    public void addDownActionListener(ActionListener al) {
        down.addActionListener(al);
    }

    /**
     *
     */
    public void removeDownActionListener(ActionListener al) {
        down.removeActionListener(al);
    }

    /**
     * The up button
     */
    private JButton up = new javax.swing.plaf.basic.BasicArrowButton(SwingConstants.NORTH);

    /**
     * The down button
     */
    private JButton down = new javax.swing.plaf.basic.BasicArrowButton(SwingConstants.SOUTH);

    /**
     * Serialization ID
     */
    private static final long serialVersionUID = 3257008748156499508L;
}
