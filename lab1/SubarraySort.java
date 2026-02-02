import java.util.Arrays;

public class SubarraySort {
    public static void findUnsortedRange(int[] input, int[] output) {
        int a = -1;
        int b = -1;

        // Step 1: find max a
        for (int i = 0; i < input.length - 1; i++) {
            if (input[i] > input[i + 1]) {
                a = i;
                break;
            }
        }

        // Already sorted
        if (a == -1) {
            output[0] = 0;
            output[1] = 0;
            return;
        }

        // Step 2: find min b
        for (int i = input.length - 1; i > 0; i--) {
            if (input[i] < input[i - 1]) {
                b = i;
                break;
            }
        }

        // Find min and max in range [a, b]
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;
        for (int i = a; i <= b; i++) {
            max = Math.max(max, input[i]);
            min = Math.min(min, input[i]);
        }

        // Step 3: Extend `a` leftward
        for (int i = 0; i < a; i++) {
            if (input[i] > min) {
                a = i;
                break;
            }
        }

        // Step 4: Extend `b` rightward
        for (int i = input.length - 1; i > b; i--) {
            if (input[i] < max) {
                b = i;
                break;
            }
        }

        output[0] = a;
        output[1] = b;
    }

    public static void main(String[] args) {
        // TESTS        
        int[][] inputs = {
            {1, 2, 4, 7, 10, 11, 7, 12, 6, 7, 16, 18, 19}, // unsorted middle
            {1, 2, 3, 4, 5}, // sorted
            {5, 4, 3, 2, 1}, // reversed 
            {1, 2, 3, 5, 4}, // right 2
            {2, 1, 3, 4, 5}, // left 2
            {1, 2, 3, 4, 0}, // last
            {10, 1, 2, 3, 4}, // first
            {2, 1}, // 2 elements
            {1, 2}, // sorted 2 elements
            {1}, // 1 element
            {5, 5, 5, 5, 5}, // same numbers
            {3, 5, 6, 7, 2, 8, 9}, // expand left
            {1, 2, 8, 4, 5, 6, 7}, // expand right
        };
        int[][] answers = {
            {3, 9},
            {0, 0},
            {0, 4},
            {3, 4},
            {0, 1},
            {0, 4},
            {0, 4},
            {0, 1},
            {0, 0},
            {0, 0},
            {0, 0},
            {0, 4},
            {2, 6},
        };

        for (int i = 0; i < inputs.length; i++) {
            int[] input = inputs[i];
            int[] output = {0, 0};

            findUnsortedRange(input, output);

            boolean passed = Arrays.equals(answers[i], output);
            String result = passed ? "Passed" : "Failed";
            
            System.out.println("Test " + i + ": " + result + 
                "\n* Input: " + Arrays.toString(input) + 
                "\n* Output: " + Arrays.toString(output) + 
                "\n* Expected Output: " + Arrays.toString(answers[i]) + 
                "\n");
            
            if (!passed){
                // Break if test fails
                break;
            }
        }
    }
}