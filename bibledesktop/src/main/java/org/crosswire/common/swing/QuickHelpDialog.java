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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.text.html.HTMLEditorKit;

import org.crosswire.bibledesktop.BDMsg;

/**
 * .
 * 
 * @see gnu.gpl.License The GNU General Public License for details.
 * @author Joe Walker
 */
public class QuickHelpDialog extends JDialog {
    /**
     * This is the default constructor
     */
    public QuickHelpDialog(Frame owner, String title, String helpText) {
        super(owner);

        initialize();

        txtHelp.setText(helpText);
        this.setTitle(title);
    }

    /**
     * This method initializes the GUI
     */
    private void initialize() {
        actions = new ActionFactory(this);

        txtHelp = new JEditorPane();
        txtHelp.setEditable(false);
        txtHelp.setEditorKit(new HTMLEditorKit());
        txtHelp.setMargin(new Insets(5, 5, 0, 5));
        txtHelp.addKeyListener(new KeyAdapter() {
            /* (non-Javadoc)
             * @see java.awt.event.KeyAdapter#keyTyped(java.awt.event.KeyEvent)
             */
            @Override
            public void keyTyped(KeyEvent ev) {
                close();
            }
        });

        JScrollPane scrHelp = new CWScrollPane(txtHelp);
        scrHelp.setBorder(null);

        // TRANSLATOR: This is the text on an "OK" button.
        JButton btnOK = new JButton(actions.addAction("OK", BDMsg.gettext("OK")));
        JPanel pnlOK = new JPanel();
        pnlOK.setLayout(new FlowLayout(FlowLayout.TRAILING));
        pnlOK.add(btnOK, null);
        pnlOK.setBackground(Color.WHITE);
        pnlOK.setOpaque(true);

        JPanel pnlHelp = new JPanel();
        pnlHelp.setLayout(new BorderLayout());
        pnlHelp.add(scrHelp, BorderLayout.CENTER);
        pnlHelp.add(pnlOK, BorderLayout.SOUTH);

        // TODO(joe): Make this more generic
        this.setSize(650, 200);
        this.setModal(true);
        this.setContentPane(pnlHelp);
        this.getRootPane().setDefaultButton(btnOK);
        this.addWindowListener(new WindowAdapter() {
            /* (non-Javadoc)
             * @see java.awt.event.WindowAdapter#windowClosing(java.awt.event.WindowEvent)
             */
            @Override
            public void windowClosing(WindowEvent ev) {
                close();
            }
        });
    }

    /* (non-Javadoc)
     * @see java.awt.Dialog#setVisible(boolean)
     */
    @Override
    public void setVisible(boolean visible) {
        if (visible) {
            GuiUtil.centerOnScreen(this);
        }

        super.setVisible(visible);
    }

    /**
     * Someone clicked OK
     */
    public void doOK() {
        close();
    }

    /**
     *
     */
    public final void close() {
        setVisible(false);
    }

    private transient ActionFactory actions;

    private JEditorPane txtHelp;

    /**
     * Serialization ID
     */
    private static final long serialVersionUID = 3690752899747557426L;
}
