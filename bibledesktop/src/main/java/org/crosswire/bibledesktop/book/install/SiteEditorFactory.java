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

import java.util.Map;
import java.util.MissingResourceException;

import org.crosswire.common.config.swing.FieldMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.crosswire.common.util.PluginUtil;
import org.crosswire.jsword.book.install.Installer;

/**
 * Factory for SiteEditors.
 * 
 * @see gnu.gpl.License for license details.
 * @author DM Smith
 */
public final class SiteEditorFactory {
    /**
     * Prevent Use
     */
    private SiteEditorFactory() {
    }

    /**
     * Create a new SiteEditor
     */
    public static SiteEditor createSiteEditor(Installer installer) {
        try {
            Class<SiteEditor> clazz = map.get(installer.getType());
            SiteEditor editor = null;
            if (clazz != null) {
                editor = clazz.newInstance();
                editor.setInstaller(installer);
            } else {
                log.warn("SiteEditor type ({}) unregistered.", installer.getType());
            }
            return editor;
        } catch (MissingResourceException e) {
            assert false : e;
        } catch (InstantiationException e) {
            assert false : e;
        } catch (IllegalAccessException e) {
            assert false : e;
        }
        return null;
    }
    /**
     * The configuration table
     */
    private static Map<String, Class<SiteEditor>> map;

    /**
     * Default map configuration
     */
    static {
        map = PluginUtil.getImplementorsMap(SiteEditor.class);
    }

    /**
     * The log stream
     */
    private static final Logger log = LoggerFactory.getLogger(FieldMap.class);
}
