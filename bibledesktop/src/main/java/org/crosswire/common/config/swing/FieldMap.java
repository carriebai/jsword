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

import java.util.Map;

import org.crosswire.common.config.Choice;
import org.crosswire.common.config.MultipleChoice;
import org.crosswire.common.util.PluginUtil;
import org.crosswire.common.util.Reporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class provides mapping between Choice types and Fields. There is an
 * argument that this class should be a properties file however the practical
 * advantages of compile time type-checking and make simplicity, outweigh the
 * possible re-use gains of a properties file.
 * 
 * @see gnu.gpl.License The GNU General Public License for details.
 * @author Joe Walker
 */
public final class FieldMap {
    /**
     * Prevent instantiation
     */
    private FieldMap() {
    }

    /**
     * Get a field from a string
     * 
     * @param type
     *            the configuration type
     * @return The best Field that matches
     */
    public static Field getField(Choice type) {
        Field field = null;
        Exception ex = null;
        try {
            // We need to treat instances of MultipleChoice differently
            // because they are always OptionsFields whatever their underlying
            // type is.
            if (type instanceof MultipleChoice) {
                field = new OptionsField();
            } else {
                Class<Field> clazz = map.get(type.getType());
                if (clazz != null) {
                    field = clazz.newInstance();
                } else {
                    log.warn("field type ({}) unregistered.", type);
                    field = new TextField();
                }
            }
            field.setChoice(type);
        } catch (InstantiationException e) {
            ex = e;
        } catch (IllegalAccessException e) {
            ex = e;
        }

        if (ex != null) {
            log.warn("field type ({}) initialization failed:", type, ex);
            Reporter.informUser(type, ex);

            log.warn("field type ({}) unregistered.", type);
            field = new TextField();
            field.setChoice(type);
        }

        return field;
    }

    /**
     * The configuration table
     */
    private static Map<String, Class<Field>> map;

    /**
     * Default map configuration
     */
    static {
        map = PluginUtil.getImplementorsMap(Field.class);
    }

    /**
     * The log stream
     */
    private static final Logger log = LoggerFactory.getLogger(FieldMap.class);
}
