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
package org.crosswire.common.config.swing;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.crosswire.bibledesktop.BDMsg;
import org.crosswire.common.swing.ActionFactory;
import org.crosswire.common.swing.EdgeBorder;

/**
 * A pane that contains ok, cancel and apply buttons.
 * 
 * @see gnu.gpl.License The GNU General Public License for details.
 * @author Joe Walker
 */
public class ButtonPane extends JPanel {

    /**
     * Simple ctor
     */
    public ButtonPane(ButtonPaneListener li) {
        this.li = li;
        init();
    }

    /**
     * GUI init.
     */
    private void init() {
        actions = new ActionFactory(this);

        // PENDING: find some way to do default buttons
        // dialog.getRootPane().setDefaultButton(ok);

        // A panel so we can right justify
        JPanel buttons = new JPanel();

        buttons.setLayout(new GridLayout(1, 2, 10, 10));
        buttons.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        // TRANSLATOR: This is the text on an "OK" button
        buttons.add(new JButton(actions.addAction("OK", BDMsg.gettext("OK"))));
        // TRANSLATOR: This is the text on a "Cancel" button
        buttons.add(new JButton(actions.addAction("Cancel", BDMsg.gettext("Cancel"))));
        // TRANSLATOR: This is the text on an "Apply" button
        buttons.add(new JButton(actions.addAction("Apply", BDMsg.gettext("Apply"))));

        this.setBorder(new EdgeBorder(SwingConstants.NORTH));
        this.setLayout(new BorderLayout(10, 10));
        this.add(buttons, BorderLayout.LINE_END);
    }

    /**
     * Do the OK action
     * 
     * @param ev
     */
    public void doOK(ActionEvent ev) {
        li.okPressed(ev);
    }

    /**
     * Do the Cancel action
     * 
     * @param ev
     */
    public void doCancel(ActionEvent ev) {
        li.cancelPressed(ev);
    }

    /**
     * Do the Apply action
     * 
     * @param ev
     */
    public void doApply(ActionEvent ev) {
        li.applyPressed(ev);
    }

    /**
     * The action factory for the buttons
     */
    private transient ActionFactory actions;

    /**
     * PENDING: turn this into a [add|remove]ButtonPaneListener thing
     */
    protected ButtonPaneListener li;

    /**
     * Serialization ID
     */
    private static final long serialVersionUID = 3257847701248031033L;
}
