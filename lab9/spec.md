# Program 9: Pretty Printing

In a file `PrettyPrint.java` containing a `public class PrettyPrint`, implement a function

`static List<Integer> splitWords(int[] lengths, int L)`

that solves the problem described in Lab 9 in which we are interested in formatting the words of given `lengths` using a maximum line length of `L` and minimizing the sum of the squares of the line slacks.

The input to this function consists of two parameters.

1. The first is an integer array `lengths` of length $n$; `length[i]` represents the number of characters in the $i$th word of the input. Note that these numbers don't include any whitespace that may have been in between the original words (as that is irrelevant in our problem). For example, if the input sequence of words is:
    
    ```
    Call me Ishmael.  Some years ago, never mind how long
    ```
    
    then the input array `lengths = {4, 2, 8, 4, 5, 4, 5, 4, 3, 4}`.
    
2. The second is an integer `L` that gives the maximum line length of any line in the output.

The output of this function should be a list of indices corresponding to the last word on each line, in order of the lines, in an optimal solution. So, if the optimal pretty-printing for the above sequence is determined to be

```
Call me Ishmael.
Some years ago,
never mind how long
```

then the list returned should be $2,5,9$ because the last word on the first line is the third word of the input sequence (its length was at index 2 in the input, because of zero-based indexing), the last word in the second line is the sixth word of the input sequence, and so on.

**If a word in the input is longer than** `L`, then the function should return `null`, as no pretty-printing with that margin is possible.

## Submission

Upload your `PrettyPrint.java` source code file, containing the `splitWords` function described above, and any helper files, to Gradescope.

You are allowed, and encouraged, to add a `main` function to `PrettyPrint.java` (or additional functions) for testing; Gradescope will make multiple calls to `splitWords` but will not call `main`.