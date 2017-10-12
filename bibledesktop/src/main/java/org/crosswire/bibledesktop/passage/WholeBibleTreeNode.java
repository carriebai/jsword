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
package org.crosswire.bibledesktop.passage;

import java.util.Enumeration;

import javax.swing.tree.TreeNode;

import org.crosswire.bibledesktop.BDMsg;
import org.crosswire.common.icu.NumberShaper;
import org.crosswire.jsword.passage.Verse;
import org.crosswire.jsword.passage.VerseRange;
import org.crosswire.jsword.versification.BibleBook;
import org.crosswire.jsword.versification.Versification;
import org.crosswire.jsword.versification.system.Versifications;

/**
 * A PassageTreeNode extends TreeNode to Model a Passage.
 * 
 * @see gnu.gpl.License for license details.
 * @author Joe Walker
 */
public final class WholeBibleTreeNode implements TreeNode {
    /**
     * The start point for all WholeBibleTreeNodes.
     */
    public static WholeBibleTreeNode getRootNode() {
        // AV11N(DMS): Is this right?
        return new WholeBibleTreeNode(null, Versifications.instance().getVersification("KJV").getAllVerses(), Level.BIBLE);
    }

    /**
     * We could do some caching here if needs be.
     */
    protected static WholeBibleTreeNode getNode(TreeNode parent, BibleBook b, int c, int v) {
        // AV11N(DMS): Is this right?
        Versification v11n = Versifications.instance().getVersification("KJV");
        Verse start = null;
        Verse end = null;
        Level thislevel = Level.BOOK;

        if (b == null) {
            assert false : "BibleBook is null";
        } else if (c == -1) {
            thislevel = Level.BOOK;
            int ec = v11n.getLastChapter(b);
            int ev = v11n.getLastVerse(b, ec);
            start = new Verse(v11n, b, 0, 0);
            end = new Verse(v11n, b, ec, ev);
        } else if (v == -1) {
            thislevel = Level.CHAPTER;
            int ev = v11n.getLastVerse(b, c);
            start = new Verse(v11n, b, c, 0);
            end = new Verse(v11n, b, c, ev);
        } else {
            thislevel = Level.VERSE;
            start = new Verse(v11n, b, c, v);
            end = start;
        }

        VerseRange rng = new VerseRange(v11n, start, end);
        return new WholeBibleTreeNode(parent, rng, thislevel);
    }

    /**
     * This constructor is for when we are really a BookTreeNode
     */
    private WholeBibleTreeNode(TreeNode parent, VerseRange range, Level level) {
        if (parent != null) {
            this.parent = parent;
        } else {
            this.parent = this;
        }

        this.range = range;
        this.level = level;
        shaper = new NumberShaper();
    }

    /**
     * The current Passage number
     */
    public VerseRange getVerseRange() {
        return range;
    }

    /**
     * @see javax.swing.tree.TreeNode#getParent()
     */
    @Override
    public TreeNode getParent() {
        return parent;
    }

    /**
     * @see javax.swing.tree.TreeNode#getAllowsChildren()
     */
    @Override
    public boolean getAllowsChildren() {
        return level != Level.VERSE;
    }

    /**
     * @see javax.swing.tree.TreeNode#isLeaf()
     */
    @Override
    public boolean isLeaf() {
        return level == Level.VERSE;
    }

    /**
     * How we appear in the Tree
     */
    @Override
    public String toString() {
        switch (level) {
        case BIBLE:
            // TRANSLATOR: The top level of the tree of Bible books, chapters and verses.
            return BDMsg.gettext("The Bible");

        case BOOK:
            Verse start = range.getStart();
            return start.getVersification().getPreferredName(start.getBook());

        case CHAPTER:
            return shaper.shape(Integer.toString(range.getStart().getChapter()));

        case VERSE:
            return shaper.shape(Integer.toString(range.getStart().getVerse()));

        default:
            // TRANSLATOR: Unexpected error condition.
            return BDMsg.gettext("Error");
        }
    }

    /**
     * Returns the child <code>TreeNode</code> at index i
     */
    @Override
    public TreeNode getChildAt(int i) {
        switch (level) {
        case BIBLE:
            return WholeBibleTreeNode.getNode(this, v11n.getBook(i), -1, -1);

        case BOOK:
            return WholeBibleTreeNode.getNode(this, range.getStart().getBook(), i, -1);

        case CHAPTER:
            return WholeBibleTreeNode.getNode(this, range.getStart().getBook(), range.getStart().getChapter(), i);

        default:
            return null;
        }
    }

    /**
     * Returns the number of children <code>TreeNode</code>s the receiver
     * contains.
     */
    @Override
    public int getChildCount() {
        switch (level) {
        case BIBLE:
            return v11n.getBookCount();

        case BOOK:
            return v11n.getLastChapter(range.getStart().getBook()) + 1;

        case CHAPTER:
            return v11n.getLastVerse(range.getStart().getBook(), range.getStart().getChapter()) + 1;

        default:
            return 0;
        }
    }

    /**
     * Returns the index of <code>node</code> in the receivers children. If the
     * receiver does not contain <code>node</code>, 0 will be returned.
     */
    @Override
    public int getIndex(TreeNode node) {
        if (!(node instanceof WholeBibleTreeNode)) {
            return 0;
        }

        WholeBibleTreeNode vnode = (WholeBibleTreeNode) node;

        switch (level) {
        case BIBLE:
            // AV11N(DMS): need to get ordinal from the BibleBookList
            return vnode.getVerseRange().getStart().getBook().ordinal();

        case BOOK:
            return vnode.getVerseRange().getStart().getChapter();

        case CHAPTER:
            return vnode.getVerseRange().getStart().getVerse();

        default:
            return 0;
        }
    }

    /**
     * @see javax.swing.tree.TreeNode#children()
     */
    @Override
    public Enumeration<TreeNode> children() {
        return new WholeBibleEnumeration(this);
    }

    /**
     * Iterate over the Books
     */
    private class WholeBibleEnumeration implements Enumeration<TreeNode> {
        WholeBibleEnumeration(WholeBibleTreeNode treeNode) {
            this.treeNode = treeNode;
        }
        @Override
        public boolean hasMoreElements() {
            return count < treeNode.getChildCount();
        }

        @Override
        public TreeNode nextElement() {
            count++;
            return treeNode.getChildAt(count);
        }

        private WholeBibleTreeNode treeNode;
        private int count;
    }

    /**
     * Levels at which this node stands.
     */
    private enum Level {
        BIBLE,
        BOOK,
        CHAPTER,
        VERSE,
    }

    // AV11N(DMS): Is this right?
    private Versification v11n = Versifications.instance().getVersification("KJV");

    /** Change the number representation as needed */
    private NumberShaper shaper;

    /** The range that this node refers to */
    private VerseRange range;

    /** Our parent tree node */
    private TreeNode parent;

    /** The level of this node one of: LEVEL_[BIBLE|BOOK|CHAPTER|VERSE] */
    private Level level;
}
