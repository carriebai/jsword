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
package org.crosswire.bibledesktop.display.basic;

import java.awt.event.MouseListener;

import javax.swing.JEditorPane;
import javax.swing.text.html.HTMLEditorKit;

/**
 *
 *
 * @see gnu.gpl.License for license details.
 * @author Yingjie Lan
 */
public class LazyHTMLEditorKit extends HTMLEditorKit {
    /**
     * Called when the kit is being installed into the a JEditorPane.
     * 
     * @param c
     *            the JEditorPane
     */
    @Override
    public void install(JEditorPane c) {
        super.install(c);
        MouseListener[] mls = c.getMouseListeners();
        for (int i = 0; i < mls.length; i++) {
            if (mls[i] instanceof HTMLEditorKit.LinkController) {
                if (linkCtrl == null) {
                    linkCtrl = (HTMLEditorKit.LinkController) mls[i];
                } else {
                    throw new RuntimeException("Multiple Link Controllers!");
                }
            }
        }
        c.removeMouseMotionListener(linkCtrl);
    }

    /**
     * Called when the kit is being removed from the JEditorPane. This is used
     * to unregister any listeners that were attached.
     * 
     * @param c
     *            the JEditorPane
     */
    @Override
    public void deinstall(JEditorPane c) {
        c.addMouseMotionListener(linkCtrl);
        super.deinstall(c);
        linkCtrl = null;
    }

    public HTMLEditorKit.LinkController getLinkCtrl() {
        return linkCtrl;
    }

    private HTMLEditorKit.LinkController linkCtrl;

    /**
     * Serialization ID
     */
    private static final long serialVersionUID = 4673477549981614993L;
}
