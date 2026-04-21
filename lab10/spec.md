# Lab/Program 10: Interval Consistency

# Problem Statement

You are given a series of statements about time intervals. Each statement is in one of the following two forms:

- $X$ ends before $Y$ starts
- $X$ and $Y$ overlap

Thus, each statement tells you something about the relationship between two arbitrary intervals $X$ and $Y$.

Crucially, you don't have the start and end times of the intervals. You should assume that every interval has nonzero length.

You want to check if all the statements are logically consistent, that is, if it's possible to create a set of start and end times to assign to each interval such that all the statements are simultaneously true.

# Examples

## 1. Consistent statements

Given the following statements:

- $A$ overlaps with $B$
- $B$ overlaps with $C$
- $A$ ends before $C$ starts
- $C$ ends before $D$ starts
- $D$ overlaps with $E$
- $E$ ends before $F$ starts

We can assign the following times to the intervals to demonstrate that the statements are consistent:

- $A = (1, 3)$
- $B = (2, 5)$
- $C = (4, 6)$
- $D = (7, 9)$
- $E = (8, 10)$
- $F = (11, 12)$

## 2. Inconsistent statements

If we are given the following statements:

- $A$ overlaps with $B$
- $B$ ends before $C$ starts
- $C$ overlaps with $D$
- $D$ ends before $A$ starts

We can detect they are inconsistent: $A$ and $B$ overlap; $C$ and $D$ overlap; but, one of the first group finishes before one of the second group, and one of the second group finishes before one of the first.

# Lab Writeup

During lab, work in small groups to develop an efficient algorithm that takes a collection of statements as described above and tries to assign consistent start and end times to each interval. As output, your algorithm should either return a (start, end) pair *for every interval* to demonstrate consistency, or it should report that no consistent time assignment is possible.

Give a description of your algorithm and analyze its complexity. Be sure to get your answer checked by the TAs / instructor to get feedback as you are working and to be sure you have the correct approach. As a record of your work, submit your document link and source to Moodle.

# Program Specification

Note that there are three starter source-code files for the main implementation and helper classes available on Moodle: `Statement.java` and `IntervalInfo.java` are helper classes (the first is used for the input to the algorithm; the second will be used for output from the algorithm); `Consistency.java` has the entry point for the algorithm.

In `Consistency.java`, implement the following function:

```java
public static Set<IntervalInfo> checkStatements(Collection<Statement> statements)
```

## Input

The input to the function is a collection of statements, each of which is represented by a `Statement` object. This class is declared in the starter file `Statement.java` and has the following accessor methods:

- `int firstInterval()`: returns an integer corresponding to the identifier of the first interval mentioned in the statement
- `int secondInterval()`: returns an integer corresponding to the identifier of the second interval mentioned in the statement
- `boolean isOverlap()`: returns `true` if the intervals in the statement overlap, and `false` if the first interval ends before the second one starts

### Examples

For example, if for an object `s` of type `Statement`, we get that:

- `s.firstInterval()` returns 5
- `s.secondInterval()` returns 8
- `s.isOverlap()` returns `true`

then `s` represents the statement, "5 overlaps with 8." On the other hand, if for an object `t`, we get that:

- `t.firstInterval()` returns 2
- `t.secondInterval()` returns 10
- `t.isOverlap()` returns `false`

then `t` represents the statement, "2 ends before 10 starts."

These statements are of the same form as described above, where integers are used as identifiers of intervals for simplicity.

### Accessing the Statements

The input is a `Collection`, which means you can iterate over the elements using a for-each loop, for example:

```java
for (Statement s : statements) {
	if (s.isOverlap()) { ... }
	else { ... }
}
```

### Notes

Do not make any assumptions about the range of interval identifiers; in particular, if there are $n$ statements, it *does not* mean that the intervals in the statement have numbers with any relationship to $n$, and it *does not* mean that the intervals are numbered $0, 1, \ldots, k < n+1$.

## Output

The return value should be:

- if the statements are consistent, a set whose elements are objects of type `IntervalInfo`, which is defined in the starter file `IntervalInfo.java`
- if the statements are inconsistent, the value `null`

If the statements are consistent, the return value should represent an example assignment of start and end times to the intervals contained in the input statements; that example assignment demonstrates that the statements are indeed consistent. In any such assignment, the start time of an interval should be strictly less than that interval's end time.

An object of type `IntervalInfo` has a constructor that takes an interval identifier. It has two mutator and three accessor methods:

- `void setStart(int startTime)` sets the start time to the specified integer
- `void setEnd(int endTime)` sets the end time to the specified integer
- `int getStart()` returns the currently stored start time
- `int getEnd()` returns the current stored end time
- `int getIdentifer()` returns the interval identifier provided at construction

Note that the interval identifier of an object is not mutable and must be set at the time the object is constructed.

### Example

The following code snippet creates an object representing an example start and end time for an interval whose identifier is 9:

```java
IntervalInfo i = new IntervalInfo(9);
i.setStart(2);
i.setEnd(4);
```

### Notes

For a non-`null` return, there should be an `IntervalInfo` object for *every* interval identifier that appears in a statement in the input.

The `Set` type for the return collection implies that there should only be one `IntervalInfo` object per interval mentioned in the input. The `equals` and `hashCode` methods of `IntervalInfo` compare interval identifiers only, which ensures that `Set`s of `IntervalInfo` objects contain no duplicates.

# Submission

Submit your `Consistency.java` source code file containing an implementation of the `checkStatements` function as described above, along with any helper files for other classes.

The `IntervalInfo.java` and `Statement.java` files do not need to be uploaded. *If you upload them, they will be overwritten with the starter-code versions of those files.* You should not need to make any changes to those files.

Gradescope will call your `checkStatements` function multiple times using different input collections of `Statement`s and evaluate the return value, either `null` or a proper assignment of times to demonstrate consistency.