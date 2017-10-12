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
package org.crosswire.bibledesktop.book.install;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

import org.crosswire.jsword.book.install.InstallManager;
import org.crosswire.jsword.book.install.InstallerEvent;
import org.crosswire.jsword.book.install.InstallerListener;

/**
 * A ListModel for a JList that uses the list of Installers given by the
 * InstallManager.
 * 
 * @see gnu.gpl.License for license details.
 * @author Joe Walker
 */
public class InstallManagerComboBoxModel extends AbstractListModel implements ComboBoxModel {
    /**
     * Simple ctor
     */
    public InstallManagerComboBoxModel(InstallManager imanager) {
        this.imanager = imanager;

        update(null);
        selection = getElementAt(0);

        imanager.addInstallerListener(new CustomInstallerListener());
    }

    /* (non-Javadoc)
     * @see javax.swing.ComboBoxModel#getSelectedItem()
     */
    @Override
    public Object getSelectedItem() {
        return selection;
    }

    /* (non-Javadoc)
     * @see javax.swing.ComboBoxModel#setSelectedItem(java.lang.Object)
     */
    @Override
    public void setSelectedItem(Object selection) {
        this.selection = selection;
    }

    /* (non-Javadoc)
     * @see javax.swing.ListModel#getSize()
     */
    @Override
    public int getSize() {
        return names.size();
    }

    /* (non-Javadoc)
     * @see javax.swing.ListModel#getElementAt(int)
     */
    @Override
    public final Object getElementAt(int index) {
        return names.get(index);
    }

    /**
     * Listens to the InstallManager for Installer changes
     */
    class CustomInstallerListener implements InstallerListener {
        /* (non-Javadoc)
         * @see org.crosswire.jsword.book.install.InstallerListener#installerAdded(org.crosswire.jsword.book.install.InstallerEvent)
         */
        @Override
        public void installerAdded(InstallerEvent ev) {
            update(ev);
        }

        /* (non-Javadoc)
         * @see org.crosswire.jsword.book.install.InstallerListener#installerRemoved(org.crosswire.jsword.book.install.InstallerEvent)
         */
        @Override
        public void installerRemoved(InstallerEvent ev) {
            update(ev);
        }
    }

    /**
     * Simple way to avoid eclipse private/protected warning
     */
    protected final void update(InstallerEvent ev) {
        int oldmax = names.size();

        names.clear();
        names.addAll(imanager.getInstallers().keySet());
        Collections.sort(names);

        if (ev != null) {
            fireContentsChanged(ev.getSource(), 0, oldmax);
        }
    }

    /**
     * Serialization support.
     * 
     * @param is
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private void readObject(ObjectInputStream is) throws IOException, ClassNotFoundException {
        // Broken but we don't serialize views
        imanager = null;
        is.defaultReadObject();
    }

    /**
     * The currently selected object
     */
    private Object selection;

    /**
     * A cache of the names in the Install Manager
     */
    private List<String> names = new ArrayList<String>();

    /**
     * The install manager that we are representing
     */
    private transient InstallManager imanager;

    /**
     * Serialization ID
     */
    private static final long serialVersionUID = 3256725082729756980L;
}
