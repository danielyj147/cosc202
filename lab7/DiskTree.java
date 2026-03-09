/******************************************************************************
 *
 *  A left-leaning red-black BST that holds a collection of Disk objects, arranged by their IDs (age).
 *  This is adapted from Section 3.3 of Algorithms, 4th Edition by Robert Sedgewick and Kevin Wayne.
 *
 ******************************************************************************/

import java.util.NoSuchElementException;

public class DiskTree {

    private static final boolean RED   = true;
    private static final boolean BLACK = false;

    private Node root;     // root of the BST
    private int size;     // number of disks in the tree

    // BST helper node data type
    private class Node {
        private Disk disk;         // disk object (ID is key)
        private Node left, right;  // links to left and right subtrees
        private boolean color;     // color of parent link
        private int max;

        public Node(Disk disk, boolean color) {
            this.disk = disk;
            this.color = color;
            this.max = disk.getFree();
        }
    }

    /**
     * Initializes an empty symbol table.
     */
    public DiskTree() {
    }

   /***************************************************************************
    *  Node helper methods.
    ***************************************************************************/
    // is node x red; false if x is null ?
    private boolean isRed(Node x) {
        if (x == null) return false;
        return x.color == RED;
    }

   /**
     * Is this symbol table empty?
     * @return {@code true} if this symbol table is empty and {@code false} otherwise
     */
    public boolean isEmpty() {
        return root == null;
    }


   /***************************************************************************
    *  Standard BST search.
    ***************************************************************************/

    /**
     * Returns the disk for a given ID if present in the tree using standard BST search.
     * @param ID the disk ID
     * @return the disk if the ID is in the symbol table
     *     and {@code null} if the ID is not in the symbol table
     */
    public Disk get(int ID) {
        return get(root, ID);
    }

    // value associated with the given key in subtree rooted at x; null if no such key
    private Disk get(Node x, int ID) {
        while (x != null) {
            int cmp = Integer.compare(ID, x.disk.getID());
            if      (cmp < 0) x = x.left;
            else if (cmp > 0) x = x.right;
            else              return x.disk;
        }
        return null;
    }

    /**
     * Does this symbol table contain the given key?
     * @param key the key
     * @return {@code true} if the tree contains a disk with the given ID and
     *     {@code false} otherwise
     */
    public boolean contains(int ID) {
        return get(ID) != null;
    }

    /**
     * Return the size of the tree.
     * @return the current number of nodes (number of disks)
     */
    public int size() {
        return size;
    }

   /***************************************************************************
    *  Red-black tree insertion.
    ***************************************************************************/

    /**
     * Inserts the given Disk object into the tree.
     *
     * @param disk the disk object to insert
     * @throws IllegalArgumentException if {@code disk} is {@code null}
     */
    public void put(Disk disk) {
        if (disk == null) throw new IllegalArgumentException("argument to put() is null");

        root = put(root, disk);
        root.color = BLACK;
        size++;
    }

    // insert the disk in the subtree rooted at h
    private Node put(Node h, Disk disk) {
        if (h == null) return new Node(disk, RED);

        int cmp = Integer.compare(disk.getID(), h.disk.getID());

        if (cmp < 0){ 
            h.left  = put(h.left,  disk);
        } else if (cmp > 0) {
            h.right = put(h.right, disk);
        }
        else {
            h.disk   = disk;
        }
        
        // fix-up any right-leaning links
        if (isRed(h.right) && !isRed(h.left))      h = rotateLeft(h);
        if (isRed(h.left)  &&  isRed(h.left.left)) h = rotateRight(h);
        if (isRed(h.left)  &&  isRed(h.right))     flipColors(h);

        h.max = h.disk.getFree();
        if (h.left != null)  h.max = Math.max(h.max, h.left.max);
        if (h.right != null) h.max = Math.max(h.max, h.right.max);

        return h;
    }

   /***************************************************************************
    *  Red-black tree deletion.
    ***************************************************************************/

    /**
     * Removes the disk with the given ID from the tree if the ID is present.
     *
     * @param  ID the disk ID
     */
    public void delete(int ID) {
        if (!contains(ID)) return;

        // if both children of root are black, set root to red
        if (!isRed(root.left) && !isRed(root.right))
            root.color = RED;

        root = delete(root, ID);
        if (!isEmpty()) root.color = BLACK;
        size--;
    }

    // delete the given key from the subtree rooted at h
    private Node delete(Node h, int ID) {
        if (ID < h.disk.getID()) {
            if (!isRed(h.left) && !isRed(h.left.left))
                h = moveRedLeft(h);
            h.left = delete(h.left, ID);
        }
        else {
            if (isRed(h.left))
                h = rotateRight(h);
            if (ID == h.disk.getID() && (h.right == null))
                return null;
            if (!isRed(h.right) && !isRed(h.right.left))
                h = moveRedRight(h);
            if (ID == h.disk.getID()) {
                Node x = min(h.right);
                h.disk = x.disk;
                h.right = deleteMin(h.right);
            }
            else h.right = delete(h.right, ID);
        }
        return balance(h);
    }

    // delete the disk with the minimum key rooted at h
    private Node deleteMin(Node h) {
        if (h.left == null)
            return null;

        if (!isRed(h.left) && !isRed(h.left.left))
            h = moveRedLeft(h);

        h.left = deleteMin(h.left);
        return balance(h);
    }

   /***************************************************************************
    *  Red-black tree helper functions.
    ***************************************************************************/

    // make a left-leaning link lean to the right
    private Node rotateRight(Node h) {
        assert (h != null) && isRed(h.left);
        Node x = h.left;
        h.left = x.right;
        x.right = h;
        x.color = h.color;
        h.color = RED;

        // recalibrating h max
        h.max = h.disk.getFree();
        if (h.left != null)  h.max = Math.max(h.max, h.left.max);
        if (h.right != null)  h.max = Math.max(h.max, h.right.max);

        // recalibrating x max
        x.max = x.disk.getFree();
        if (x.left != null)  x.max = Math.max(x.max, x.left.max);
        if (x.right != null)  x.max = Math.max(x.max, x.right.max);

        return x;
    }

    // make a right-leaning link lean to the left
    private Node rotateLeft(Node h) {
        assert (h != null) && isRed(h.right);
        Node x = h.right;
        h.right = x.left;
        x.left = h;
        x.color = h.color;
        h.color = RED;

        // recalibrating h max
        h.max = h.disk.getFree();
        if (h.left != null)  h.max = Math.max(h.max, h.left.max);
        if (h.right != null)  h.max = Math.max(h.max, h.right.max);

        // recalibrating x max
        x.max = x.disk.getFree();
        if (x.left != null)  x.max = Math.max(x.max, x.left.max);
        if (x.right != null)  x.max = Math.max(x.max, x.right.max);

        return x;
    }

    // flip the colors of a node and its two children
    private void flipColors(Node h) {
        h.color = !h.color;
        h.left.color = !h.left.color;
        h.right.color = !h.right.color;
    }

    // Assuming that h is red and both h.left and h.left.left
    // are black, make h.left or one of its children red.
    private Node moveRedLeft(Node h) {
        flipColors(h);
        if (isRed(h.right.left)) {
            h.right = rotateRight(h.right);
            h = rotateLeft(h);
            flipColors(h);
        }
        return h;
    }

    // Assuming that h is red and both h.right and h.right.left
    // are black, make h.right or one of its children red.
    private Node moveRedRight(Node h) {
        flipColors(h);
        if (isRed(h.left.left)) {
            h = rotateRight(h);
            flipColors(h);
        }
        return h;
    }

    // restore red-black tree invariant
    private Node balance(Node h) {
        if (isRed(h.right) && !isRed(h.left))    h = rotateLeft(h);
        if (isRed(h.left) && isRed(h.left.left)) h = rotateRight(h);
        if (isRed(h.left) && isRed(h.right))     flipColors(h);

        // for delete ==> when a child is gone, needs recalibrating 
        h.max = h.disk.getFree();
        if (h.left != null)  h.max = Math.max(h.max, h.left.max);
        if (h.right != null) h.max = Math.max(h.max, h.right.max);

        return h;
    }


   /***************************************************************************
    *  Utility functions.
    ***************************************************************************/

    /**
     * Returns the height of the BST.
     * @return the height of the BST (a 0-node tree has height 0)
     */
    public int height() {
        return height(root);
    }
    private int height(Node x) {
        if (x == null) return 0;
        return 1 + Math.max(height(x.left), height(x.right));
    }

   /***************************************************************************
    *  Ordered symbol table methods.
    ***************************************************************************/

    /**
     * Returns the disk with smallest ID in the tree.
     * @return the smallest-ID (oldest) disk object in the tree
     * @throws NoSuchElementException if the symbol table is empty
     */
    public Disk min() {
        if (isEmpty()) throw new NoSuchElementException("calls min() with empty symbol table");
        return min(root).disk;
    }

    // the smallest key in subtree rooted at x; null if no such key
    private Node min(Node x) {
        if (x.left == null) return x;
        else                return min(x.left);
    }

    /**
     * Returns the disk with the largest ID in the tree.
     * @return the largest-ID (newest) disk object in the tree
     * @throws NoSuchElementException if the symbol table is empty
     */
    public Disk max() {
        if (isEmpty()) throw new NoSuchElementException("calls max() with empty symbol table");
        return max(root).disk;
    }

    // the largest key in the subtree rooted at x; null if no such key
    private Node max(Node x) {
        // assert x != null;
        if (x.right == null) return x;
        else                 return max(x.right);
    }


   /***************************************************************************
    *  Check integrity of red-black tree data structure.
    ***************************************************************************/
    public boolean check() {
        boolean result = true;

        result = result && isBST();
        if (!result)            System.err.println("Not in symmetric order");
        result = result && is23();
        if (!result)            System.err.println("Not a 2-3 tree");
        result = result && isBalanced();
        if (!result)            System.err.println("Not balanced");

        return result;
    }

    // does this binary tree satisfy symmetric order?
    // Note: this test also ensures that data structure is a binary tree since order is strict
    private boolean isBST() {
        return isBST(root, null, null);
    }

    // is the tree rooted at x a BST with all disk IDs strictly between min and max
    // (if min or max is null, treat as empty constraint)
    // Credit: elegant solution due to Bob Dondero
    private boolean isBST(Node x, Integer min, Integer max) {
        if (x == null) return true;
        int key = x.disk.getID();
        if (min != null && key <= min) return false;
        if (max != null && key >= max) return false;
        return isBST(x.left, min, key) && isBST(x.right, key, max);
    }

    // Does the tree have no red right links, and at most one (left)
    // red links in a row on any path?
    private boolean is23() { return is23(root); }
    private boolean is23(Node x) {
        if (x == null) return true;
        if (isRed(x.right)) return false;
        if (x != root && isRed(x) && isRed(x.left))
            return false;
        return is23(x.left) && is23(x.right);
    }

    // do all paths from root to leaf have same number of black edges?
    private boolean isBalanced() {
        int black = 0;     // number of black links on path from root to min
        Node x = root;
        while (x != null) {
            if (!isRed(x)) black++;
            x = x.left;
        }
        return isBalanced(root, black);
    }

    // does every path from the root to a leaf have the given number of black links?
    private boolean isBalanced(Node x, int black) {
        if (x == null) return black == 0;
        if (!isRed(x)) black--;
        return isBalanced(x.left, black) && isBalanced(x.right, black);
    }

    /***************************************************************************
    *  Max Biinary Tree Search Function
    ***************************************************************************/

    /**
     * 
     * @param size
     * @return oldest disk w/ enough space OR null if there is no such disk
     */
    public Disk getOldest(int size){
        Node node = root;
        
        while (node != null){
            if (node.left != null && node.left.max >= size){ // check older
                node = node.left;
            } else if (node.disk.getFree() >= size) { // check self
                return node.disk;
            } else { // check younger
                node = node.right;
            }
        }
        return null;
    }
}
