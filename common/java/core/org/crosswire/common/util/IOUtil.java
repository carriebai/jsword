package org.crosswire.common.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * .
 *
 * <p><table border='1' cellPadding='3' cellSpacing='0'>
 * <tr><td bgColor='white' class='TableRowColor'><font size='-7'>
 *
 * Distribution Licence:<br />
 * JSword is free software; you can redistribute it
 * and/or modify it under the terms of the GNU General Public License,
 * version 2 as published by the Free Software Foundation.<br />
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br />
 * The License is available on the internet
 * <a href='http://www.gnu.org/copyleft/gpl.html'>here</a>, or by writing to:
 * Free Software Foundation, Inc., 59 Temple Place - Suite 330, Boston,
 * MA 02111-1307, USA<br />
 * The copyright to this program is held by it's authors.
 * </font></td></tr></table>
 * @see gnu.gpl.Licence
 * @author Joe Walker [joe at eireneh dot com]
 * @version $Id$
 */
public class IOUtil
{
    /**
     * Prevent instansiation
     */
    private IOUtil()
    {
    }

    /**
     * Unpack a zip file to a given directory. Honor the paths
     * as given in the zip file.
     * @param file The zip file to download
     * @param destdir The directory to unpack up
     * @throws IOException If there is an file error
     */
    public static void unpackZip(File file, File destdir) throws IOException
    {
        // unpack the zip.
        byte[] dbuf = new byte[4096];
        ZipFile zf = new ZipFile(file);
        Enumeration entries = zf.entries();
        while (entries.hasMoreElements())
        {
            ZipEntry entry = (ZipEntry) entries.nextElement();
            String entrypath = entry.getName();
            File entryFile = new File(destdir, entrypath);
            File parentDir = entryFile.getParentFile();
            // Is it already a directory ?
            if (!parentDir.isDirectory())
            {
                parentDir.mkdirs();

                // Did that work?
                if (!parentDir.isDirectory())
                {
                    throw new MalformedURLException(Msg.CREATE_DIR_FAIL.toString(parentDir.toString()));
                }
            }

            URL child = NetUtil.getURL(entryFile);

            OutputStream dataOut = NetUtil.getOutputStream(child);
            InputStream dataIn = zf.getInputStream(entry);

            while (true)
            {
                int count = dataIn.read(dbuf);
                if (count == -1)
                {
                    break;
                }
                dataOut.write(dbuf, 0, count);
            }

            dataOut.close();
        }
    }

    /**
     * Close the stream whatever without complaining
     * @param out The stream to close
     */
    public static void close(OutputStream out)
    {
        if (null != out)
        {
            try
            {
                out.close();
            }
            catch (IOException ex)
            {
                log.error("close", ex); //$NON-NLS-1$
            }
        }
    }

    /**
     * Close the stream whatever without complaining
     * @param in The stream to close
     */
    public static void close(InputStream in)
    {
        if (null != in)
        {
            try
            {
                in.close();
            }
            catch (IOException ex)
            {
                log.error("close", ex); //$NON-NLS-1$
            }
        }
    }

    /**
     * The log stream
     */
    private static final Logger log = Logger.getLogger(IOUtil.class);
}