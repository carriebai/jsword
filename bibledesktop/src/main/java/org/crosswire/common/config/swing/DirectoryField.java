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

import java.awt.FileDialog;

import javax.swing.JFileChooser;

import org.crosswire.common.swing.GuiUtil;
import org.crosswire.common.util.OSType;

/**
 * A Directory selection.
 * 
 * @see gnu.gpl.License The GNU General Public License for details.
 * @author Joe Walker
 */
public class DirectoryField extends FileField {
    /* (non-Javadoc)
     * @see org.crosswire.common.config.swing.FileField#doBrowse()
     */
    @Override
    public void doBrowse() {
        if (OSType.MAC.equals(OSType.getOSType())) {
            FileDialog chooser = new FileDialog(GuiUtil.getFrame(getParent()), text.getText());
            String prop = "apple.awt.fileDialogForDirectories";
            System.setProperty(prop, "true");
            chooser.setVisible(true);
            System.setProperty(prop, "false");
            String dir = chooser.getFile();
            if (dir != null) {
                text.setText(dir);
            }
        } else {
            JFileChooser chooser = new JFileChooser(text.getText());
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            if (chooser.showOpenDialog(DirectoryField.this) == JFileChooser.APPROVE_OPTION) {
                text.setText(chooser.getSelectedFile().getPath());
            }
        }
    }

    /**
     * Serialization ID
     */
    private static final long serialVersionUID = 3905239018106075189L;
}
