# Program 7: Cloud Archive

# Overview

You will implement Heuristics 2–4 from [Lab 7](https://www.notion.so/283b03384ad2495481ee2df353351ba2?pvs=21). Each heuristic will be implemented in a different Java class, and all implementations are subclasses of an abstract class `DiskManager` .

# Starter Files and Objects

You can access the starter files from Moodle:

- `Disk.java` defines a disk object. You should not modify this file. Methods include:
    - A constructor, which takes an integer capacity and automatically assigns an ID (so that each disk opened gets the next integer ID)
    - Accessor methods, that return a disk’s ID, capacity, used space, and free space
    - An `assign` method that takes a file ID and size, and adds that file to the disk, changing its used & free space as a result
    - A `close` method (although disks auto-close when they reach capacity)
- `DiskManager.java` is an abstract class for the cloud archive. You should not modify this file,. Its existing methods include:
    - A constructor, that takes a disk capacity as an argument
    - `open`, which adds a new empty disk to the archive
    - Informational methods that return the number of disks in the archive, the capacity of the disks, and a `toString` method that can be used for debugging
    - An abstract method `assignFile` that will be overridden by subclasses that implement the different heuristics to assign files to disks.
- `Manager1.java` is an implementation of Heuristic 1. It is an example of a subclass of `DiskManager` that keeps track of a single current, open disk. Its `assignFile` method will assign the given file to the current disk if it will fit, and will open a new disk (closing the current one) for it if it will not fit.
- `DiskTree.java` is an implementation of a left-leaning red-black BST (code is adapted from your textbook) that can be modified to implement Heuristic 4.

For your work, you will create three new subclasses of `DiskManager`, one for each of Heuristics 2–4. You can use the example of `Manager1` as a guide, although you will have to change the instance variables to include the relevant data structures for keeping track of disks efficiently for the different heuristics.

# Heuristic 2

Create a file `Manager2.java` that contains a `public class Manager2 extends DiskManager`. You can duplicate and modify `Manager1.java` if it’s easier.

`Manager2`'s `assignFile` function should find the most full open disk that can fit the given file. The most efficient way to implement this is to store the open disks in a BST in which disks are ordered by free space, and we binary search for the disk that has the lowest amount of free space that is greater than or equal to the size of the file.

Because this uses standard BST operations, you can use the built-in [`TreeSet`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/TreeSet.html) in Java for this. You’ll have to use the following methods:

- [`TreeSet` constructor](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/TreeSet.html#%3Cinit%3E(java.util.Comparator)): Use the version that takes a `Comparator`, because that will allow you to order the disks based on their free size.
    - You’ll have to write a helper class that `implements Comparator<Disk>` containing a `compare` method that takes two disks and returns which should come first in the BST ordering. The method can look at the free space of the two disks to decide.
    - You can provide a new instance of that helper class as the argument to the `TreeSet` constructor.
- [`TreeSet.add`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/TreeSet.html#add(E)) to add new disks to the BST.
- [`TreeSet.ceiling`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/TreeSet.html#ceiling(E)) to binary search for the best disk to fit the file.
- [`TreeSet.remove`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/TreeSet.html#remove(java.lang.Object)) to remove a disk from the BST.
    - Once we find the disk to assign the file, we should remove the disk, assign the file (which will change its free space), and then put it back into the BST if it’s not full.

# Heuristic 3

Create a file `Manager3.java` that contains a `public class Manager3 extends DiskManager`. You can duplicate and modify `Manager1.java` if it’s easier.

`Manager3`'s `assignFile` function should find the least full open disk that can fit the given file. The most efficient way to implement this is to store the open disks in a min-heap in which disks are ordered by free space.

Java’s built-in [`PriorityQueue`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/PriorityQueue.html) implements a minimum heap. As such, you can directly use it on `Disk` objects, providing the very same helper class from Heuristic 2’s implementation for the `Comparator` that orders disks by free space.

Similar to above, you can use standard methods for the implementation:

- [peek](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/PriorityQueue.html#peek()) to check the least full disk
- [poll](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/PriorityQueue.html#poll()) to remove the least full disk
- [add](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/PriorityQueue.html#add(E)) to add back in the disk (with a changed free space) or add in a new disk

# Heuristic 4

Create a file `Manager4.java` that contains a `public class Manager4 extends DiskManager`. You can duplicate and modify `Manager1.java` if it’s easier.

`Manager4`'s `assignFile` function should find the oldest open disk that can fit the given file. The most efficient way to implement this is to use a BST ordered by disk age that stores, at each node, the maximum free space of any disk in that node’s subtree.

A standard BST from the Java library will not suffice for this, so you’ll have to use your own. As a starting point, you can use `DiskTree.java`, which implements a left-leaning red-black BST. The code was directly adapted from the textbook. It stores `Disk` objects and orders them by disk ID (which is assumed to reflect the disk’s age).

You’ll have to add an instance variable to `DiskTree.Node` that can store that max free space. You’ll have to modify the BST code to keep that variable updated across changes to the tree, including additions, removals, and rotations (rebalancing).

You’ll then have to add a modified binary-search method that can use those max free space variables to efficiently find the oldest disk that can fit a given file size.

Once your modified tree is capable of the modified binary search, you can use it in `Manager4` to implement `assignFile` according to this heuristic’s rule.