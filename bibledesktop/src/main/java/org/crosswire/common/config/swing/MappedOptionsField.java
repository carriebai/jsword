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
 * © CrossWire Bible Society, 2007 - 2016
 */
package org.crosswire.common.config.swing;

import java.awt.Dimension;
import java.util.Map;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JComponent;

import org.crosswire.common.config.Choice;
import org.crosswire.common.config.MappedChoice;
import org.crosswire.common.swing.CWOtherMsg;
import org.crosswire.common.swing.GuiUtil;
import org.crosswire.common.swing.MapComboBoxModel;
import org.crosswire.common.swing.MapEntryRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Allow the user to choose from a combo box.
 * 
 * @see gnu.gpl.License The GNU General Public License for details.
 * @author DM Smith
 */
public class MappedOptionsField implements Field {
    /**
     * Create an empty MappedOptionsField
     */
    public MappedOptionsField() {
        combo = new JComboBox(new String[] {
                CWOtherMsg.lookupText("No Options Set")
        });
        // Set the preferred width. Note, the actual combo box will resize to
        // the width of it's container
        combo.setPreferredSize(new Dimension(100, combo.getPreferredSize().height));
        GuiUtil.applyDefaultOrientation(combo);
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
        if (!(param instanceof MappedChoice<?, ?>)) {
            throw new IllegalArgumentException("Illegal type for Choice. Not a MappedChoice. " + param.getKey());
        }
        MappedChoice<?, ?> mc = (MappedChoice<?, ?>) param;
        Map<?, ?> map = mc.getOptions();
        if (map == null) {
            throw new IllegalArgumentException("getOptions() returns null for option: " + param.getKey());
        }
        combo.setModel(new MapComboBoxModel(map));
        combo.setRenderer(new MapEntryRenderer());
    }

    /**
     * Return a string for use in the properties file
     * 
     * @return The current value
     */
    @Override
    public String getValue() {
        Object reply = combo.getSelectedItem();

        if (reply instanceof Map.Entry<?, ?>) {
            return ((Map.Entry<?, ?>) reply).getKey().toString();
        }
        return reply == null ? "" : reply.toString();
    }

    /**
     * Set the current value
     * 
     * @param value
     *            The new text
     */
    @Override
    public void setValue(String value) {
        ComboBoxModel model = combo.getModel();
        int size = model.getSize();
        for (int i = 0; i < size; i++) {
            Object match = model.getElementAt(i);
            if (match instanceof Map.Entry<?, ?>) {
                Map.Entry<?, ?> mapEntry = (Map.Entry<?, ?>) match;
                if (mapEntry.getKey().toString().equals(value) || mapEntry.getValue().toString().equals(value)) {
                    combo.setSelectedItem(mapEntry);
                    return;
                }
            }
        }

        // Equate null and empty string
        Object selected = combo.getSelectedItem();
        if (value.length() > 0 && selected != null) {
            log.warn("Checked for options without finding: '{}. Defaulting to first option: {}", value, selected);
        }
    }

    /**
     * Get the actual component that we can add to a Panel. (This can well be
     * this in an implementation).
     */
    @Override
    public JComponent getComponent() {
        return combo;
    }

    /**
     * The component that we are wrapping in a field
     */
    private JComboBox combo;

    /**
     * The log stream
     */
    private static final Logger log = LoggerFactory.getLogger(MappedOptionsField.class);
}
