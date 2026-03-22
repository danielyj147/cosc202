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

`DiskManager` is the abstract class that you will subclass for different heuristic implementations. `Manager1` is an example of such a subclass.

# The Assignment

Create three files, `Manager2.java`, `Manager3.java`, and `Manager4.java`, each of which is a subclass of `DiskManager`, for example:

```java
public class Manager2 extends DiskManager {

	// instance variables go here
	
	/* constructor
	
	   must take an integer capacity
	   and should call super(capacity) to do the basic DiskManager stuff	
	*/
	public Manager2(int capacity) {
		super(capacity);
		// any other initialization goes here
	}

  /* file assignment
  
     heuristic logic goes here
  */
	@Override
	public void assignFile(int fileID, int size) {
		// your implementation goes here
	}
}
```

Each subclass implements one of Heuristics 2–4. You can use the example of `Manager1` (included in the starter files) as a guide, although you will have to change the instance variables to include the relevant data structures for keeping track of disks efficiently for the different heuristics.

Here is how a `DiskManager` object will get used / tested:

1. It gets constructed with an integer `capacity` argument, that indicates the capacity of the disks. `super(capacity)` will take care of initializing a disk array and other internal management.
2. Any other initialization can happen in the constructor of `Manager`*x.*
    
    For example, `Manager1` opens a new disk and stores it in an instance variable `current` because Heuristic 1 says to start with a single open disk.
    
3. Then, there will be a sequence of file-storage requests. Your code will not get the entire sequence directly. Instead, outside of your code (for example, in the tester that constructs the `DiskManager` object), there will be multiple calls to `assignFile`, such that each call corresponds to one incremental file-storage request. Your `assignFile` code should implement the heuristic’s strategy to make a single new file assignment to a disk and update any relevant data structures.
    
    For example, `Manager1` tests if the file size provided to `assignFile` will fit on the current disk: if it does, it assigns it to that disk; if it doesn’t, it closes that disk, opens a new one, and assigns the file to the new one, remembering it as `current` going forward.
    
4. At the end of the sequence of requests, we can examine which files are on which disk to determine if your implementation matched the corresponding heuristic.
5. By running a test with many files, we can test the efficiency of the heuristic.

## Heuristic 2

Create a file `Manager2.java` that contains a `public class Manager2 extends DiskManager`. You can duplicate and modify `Manager1.java` if it’s easier.

`Manager2`'s `assignFile` function should find the most full open disk that can fit the given file. The most efficient way to implement this is to store the open disks in a BST in which disks are ordered by free space, and we binary search for the disk that has the lowest amount of free space that is greater than or equal to the size of the file.

Because this uses standard BST operations, you can use the built-in Java BST. Because we are binary searching for a file size, it’s easiest if the keys of the BST are integers representing free space, and the values of the BST are the disks with that amount of free space. (You’ll have to account for the possibility of multiple disks having the same amount of free space with this setup.) You’ll likely use the following methods:

- [`TreeMap` constructor](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/TreeMap.html#%3Cinit%3E())
- [`TreeMap.put`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/TreeMap.html#put(K,V)) to add an entry to the BST
- [`TreeMap.ceilingEntry`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/TreeMap.html#ceilingEntry(K)) or [`TreeMap.ceilingKey`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/TreeMap.html#ceilingKey(K)) to binary search for the best disk to fit the file.
- [`TreeMap.remove`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/TreeMap.html#remove(java.lang.Object)) to remove an entry from the BST (to avoid clutter, given that free space amounts will be changing every time a file is assigned)

## Heuristic 3

Create a file `Manager3.java` that contains a `public class Manager3 extends DiskManager`. You can duplicate and modify `Manager1.java` if it’s easier.

`Manager3`'s `assignFile` function should find the least full open disk that can fit the given file. The most efficient way to implement this is to store the open disks in a min-heap in which disks are ordered by free space.

Java’s built-in [`PriorityQueue`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/PriorityQueue.html) implements a minimum heap. As such, you can directly use it on `Disk` objects, as long as you indicate that disks should be ordered by the amount of used space. For this, you want to use the [`PriorityQueue(Comparator<? super E> comparator)`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/PriorityQueue.html#%3Cinit%3E(java.util.Comparator)) version of the constructor that takes a [`Comparator`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Comparator.html) object.

There are multiple ways to provide a correct comparator:

- The most old-school, backwards-compatible way is to write helper class that `implements Comparator<Disk>` containing a `compare` method. That method that takes two disks and returns which should come first in the heap ordering. The method can look at the used space of the two disks to decide. Then, provide a new instance of this helper class as the argument to the `PriorityQueue` constructor.
- Or, you can declare and provide the comparator inline as an anonymous inner class.
- Or, you can use lambda functions with one of the Comparator class’s static methods to indicate that the comparison should be done by getting the used space of the disks and naturally comparing those.

Once the heap is set up with the correct ordering, similar to above, you can use standard methods for the implementation:

- [peek](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/PriorityQueue.html#peek()) to check the least full disk
- [poll](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/PriorityQueue.html#poll()) to remove the least full disk
- [add](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/PriorityQueue.html#add(E)) to add back in the disk (with a changed free space) or add in a new disk

## Heuristic 4

Create a file `Manager4.java` that contains a `public class Manager4 extends DiskManager`. You can duplicate and modify `Manager1.java` if it’s easier.

`Manager4`'s `assignFile` function should find the oldest open disk that can fit the given file. The most efficient way to implement this is to use a BST ordered by disk age that stores, at each node, the maximum free space of any disk in that node’s subtree.

A standard BST from the Java library will not suffice for this, so you’ll have to use your own. As a starting point, you can use `DiskTree.java`, which implements a left-leaning red-black BST. The code was directly adapted from the textbook. It stores `Disk` objects and orders them by disk ID (which is assumed to reflect the disk’s age).

You’ll have to add an instance variable to `DiskTree.Node` that can store that max free space. You’ll have to modify the BST code to keep that variable updated across changes to the tree, including additions, removals, and rotations (rebalancing).

You’ll then have to add a modified binary-search method that can use those max free space variables to efficiently find the oldest disk that can fit a given file size.

<aside>
💡

*Hint:* The original [textbook red-black tree implementation](https://algs4.cs.princeton.edu/33balanced/RedBlackBST.java.html) has a similar variable that it uses for order statistics, namely, `size`, which stores the number of nodes in the subtree. You can see how the `size` variables are kept up-to-date as a template for how to maintain the max free space variables, and the order methods (`rank` and `select`) for examples of how those are used for alternate searches.

</aside>

Once your modified tree is capable of the modified binary search, you can use it in `Manager4` to implement `assignFile` according to this heuristic’s rule.

# Testing and Submission

You can test the heuristics separately and locally to see how they are working. Consider adding a `main` method to each `Manager`*x* class that will call `assignFile` a number of times. `DiskManager` has a `toString` method that will reveal the contents of disks, so you can simply print the manager object and it will show you all disks and what is stored on them.

Submit your `Manager2.java`, `Manager3.java`, `Manager4.java`, your modified `DiskTree.java`, and any helper files (for example, if you wrote a comparator helper class in a separate file) to Gradescope.

*Note:* It is possible to omit one or more of the heuristics in your submission, and the tests should run on the ones that you did submit.