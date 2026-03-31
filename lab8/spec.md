# Program 8: Meeting Room Assignments

# Input

You are given:

- An integer $R$, corresponding to the number of rooms you have available to assign.
- Two array lists of integers, `start` and `finish`, both of length $n$, corresponding to the start and finish times of the $n$ meeting requests. In particular, a meeting request $i$ (where $0 \leq i < n$) has a start time of `start[i]` and a finish time of `finish[i]`.

# Output

Return a map $M$ of integer-integer key-value pairs:

- The keys are the indices $0,1,\ldots,n-1$ corresponding to the meeting-request indices used for start and finish times in the input.
- The value $M[i]$ for meeting $i$ is the room assigned to the meeting, where $0 \leq M[i] < R$ is the room number.

The room assignment should meet the following conditions:

- No two overlapping (in time) meetings should be assigned to the same room. (However, a meeting that ends at some time $t$ and another meeting that begins at that time $t$ do *not* overlap; they just have a time gap of zero between them, and both can be booked in the same room.)
- The number of distinct values in the map should be minimized, meaning that we use as few rooms as possible to complete the assignment.

Alternatively, return `null` if *no* assignment using $R$ rooms is possible.

# Specification

In a file `Scheduler.java` implement the function

```java
public static HashMap<Integer, Integer> assignRooms(
	int R,                        /* number of rooms */
	ArrayList<Integer> start,     /* meeting start times indexed by ID */
	ArrayList<Integer> finish     /* meeting finish times indexed by ID */
) {
	// TODO: Your implementation here
}
```