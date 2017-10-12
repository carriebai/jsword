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

/**
 * Another way to get a hold of missing exceptions.
 * 
 * @see gnu.gpl.License The GNU General Public License for details.
 * @author Joe Walker
 */
public class CatchingThreadGroup extends ThreadGroup {
    /**
     * Simple ctor that names the threadgroup
     * 
     * @param name
     *            The name for this group
     */
    public CatchingThreadGroup(String name) {
        super(name);
    }

    /**
     * Simple ctor that names the threadgroup, and provides a parent group
     * 
     * @param group
     *            The parent ThreadGroup
     * @param name
     *            The name for this group
     */
    public CatchingThreadGroup(ThreadGroup group, String name) {
        super(group, name);
    }

    /* (non-Javadoc)
     * @see java.lang.ThreadGroup#uncaughtException(java.lang.Thread, java.lang.Throwable)
     */
    @Override
    public void uncaughtException(Thread t, Throwable ex) {
        ex.printStackTrace(System.err);
        ExceptionPane.showExceptionDialog(null, ex);
    }
}
