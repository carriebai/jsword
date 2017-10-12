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

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.crosswire.bibledesktop.BDMsg;
import org.crosswire.common.config.Choice;
import org.crosswire.common.swing.ActionFactory;
import org.crosswire.common.swing.CWAction;

/**
 * A Filename selection.
 * 
 * @see gnu.gpl.License The GNU General Public License for details.
 * @author Joe Walker
 */
public class FileField extends JPanel implements Field {
    /**
     * Create a new FileField
     */
    public FileField() {
        ActionFactory actions = new ActionFactory(this);

        text = new JTextField();

        setLayout(new BorderLayout(10, 0));
        add(text, BorderLayout.CENTER);
        // TRANSLATOR: This is the text on a "Browse" button, that brings up a file selection dialog.
        CWAction action = actions.addAction("Browse", BDMsg.gettext("Browse"));
        // TRANSLATOR: This is the tooltip for a "Browse" button, that brings up a file selection dialog.
        action.setTooltip(BDMsg.gettext("Browse for a directory to select."));
        add(new JButton(action), BorderLayout.LINE_END);
    }

    /**
     * Open a browse dialog
     */
    public void doBrowse() {
        JFileChooser chooser = new JFileChooser(text.getText());
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        if (chooser.showOpenDialog(FileField.this) == JFileChooser.APPROVE_OPTION) {
            text.setText(chooser.getSelectedFile().getPath());
        }
    }

    /* (non-Javadoc)
     * @see org.crosswire.common.config.swing.Field#setChoice(org.crosswire.common.config.Choice)
     */
    @Override
    public void setChoice(Choice param) {
    }

    /* (non-Javadoc)
     * @see org.crosswire.common.config.swing.Field#getValue()
     */
    @Override
    public String getValue() {
        return text.getText();
    }

    /* (non-Javadoc)
     * @see org.crosswire.common.config.swing.Field#setValue(java.lang.String)
     */
    @Override
    public void setValue(String value) {
        text.setText(value);
    }

    /* (non-Javadoc)
     * @see org.crosswire.common.config.swing.Field#getComponent()
     */
    @Override
    public JComponent getComponent() {
        return this;
    }

    /**
     * The text field
     */
    protected JTextField text;

    /**
     * Serialization ID
     */
    private static final long serialVersionUID = 3258416148742484276L;
}
