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

import java.awt.FlowLayout;

import javax.swing.ButtonGroup;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import org.crosswire.bibledesktop.BDMsg;
import org.crosswire.common.config.Choice;
import org.crosswire.common.swing.ActionFactory;
import org.crosswire.common.util.Convert;

/**
 * Allow the user to choose from True/False.
 * 
 * @see gnu.gpl.License The GNU General Public License for details.
 * @author Joe Walker
 */
public class BooleanField extends JPanel implements Field {
    /**
     * Give the values list (true/false) to the ComboBox
     */
    public BooleanField() {
        ActionFactory actions = new ActionFactory(this);

        // TRANSLATOR: This is the text on a "Yes" button
        on = new JRadioButton(actions.addAction("Yes", BDMsg.gettext("Yes")));
        // TRANSLATOR: This is the text on a "No" button
        off = new JRadioButton(actions.addAction("No", BDMsg.gettext("No")));

        ButtonGroup group = new ButtonGroup();
        group.add(on);
        group.add(off);

        setLayout(new FlowLayout(FlowLayout.LEADING, 0, 0));
        add(on);
        add(off);
    }

    /**
     * Some fields will need some extra info to display properly like the
     * options in an options field. FieldMap calls this method with options
     * provided by the choice.
     * 
     * @param param
     *            The options provided by the Choice
     */
    @Override
    public void setChoice(Choice param) {
    }

    /**
     * Return a string for use in the properties file
     * 
     * @return The current value
     */
    @Override
    public String getValue() {
        return Convert.boolean2String(on.isSelected());
    }

    /**
     * Set the current value
     * 
     * @param value
     *            The new text
     */
    @Override
    public void setValue(String value) {
        on.setSelected(Convert.string2Boolean(value));
        off.setSelected(!Convert.string2Boolean(value));
    }

    public void doYes() {
        on.setSelected(true);
    }

    public void doNo() {
        off.setSelected(true);
    }

    /**
     * Get the actual component that we can add to a Panel. (This can well be
     * this in an implementation).
     */
    @Override
    public JComponent getComponent() {
        return this;
    }

    /**
     * The 'on' button
     */
    private JRadioButton on;

    /**
     * The 'off' button
     */
    private JRadioButton off;

    /**
     * Serialization ID
     */
    private static final long serialVersionUID = 3617291237934053686L;
}
