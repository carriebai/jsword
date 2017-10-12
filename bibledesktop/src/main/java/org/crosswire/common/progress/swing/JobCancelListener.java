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
 * © CrossWire Bible Society, 2008 - 2016
 */
package org.crosswire.common.progress.swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.crosswire.common.progress.Progress;

/**
 * Listen for cancel events and do the cancel.
 * 
 * @see gnu.gpl.License The GNU General Public License for details.
 * @author DM Smith
 */
final class JobCancelListener implements ActionListener {
    /**
     * @param theJob
     */
    JobCancelListener(Progress theJob) {
        job = theJob;
    }

    /* (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed(ActionEvent ev) {
        job.cancel();
    }

    private Progress job;
}
