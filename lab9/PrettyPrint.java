package lab9;

import java.util.ArrayList;
import java.util.List;

public class PrettyPrint {
    /**
     * 
     * @param lengths word lengths
     * @param L max line len
     * @return indices of each line's last word w/ min toal of slack squared.
     */
    static List<Integer> splitWords(int[] lengths, int L) {
        int n = lengths.length;
        int[] opt = new int[n];
        int[] parent = new int[n];
        List<Integer> ends = new ArrayList<>();

        if (lengths[0] > L) return null;

        opt[0] = (L - lengths[0]) * (L - lengths[0]);
        parent[0] = -1;

        for (int i = 1; i < n; i++) {
            if (lengths[i] > L) return null;

            int lineLen = lengths[i];
            int best = Integer.MAX_VALUE; 
            int bestStart = i;

            // j = number of words on the current line, ending at i
            // opt(i-j) + S^2(i-j ~ i)
            for (int j = 1; j <= i + 1 && lineLen <= L; j++) {
                int prev = (i - j == -1) ? 0 : opt[i - j]; // to preserve parent
                int slack = L - lineLen;
                int cand = prev + slack * slack;
                if (cand < best) {
                    best = cand;
                    bestStart = i - j + 1;
                }
                if (i - j >= 0) lineLen += 1 + lengths[i - j];
            }

            opt[i] = best;
            parent[i] = bestStart - 1;
        }

        // moving up to get parents.
        for (int k = n - 1; k >= 0; k = parent[k]) ends.add(k);
        java.util.Collections.reverse(ends); // reverse order
        return ends;
    }

    public static void main(String[] args) {
        int[] lengths1 = {1,1};
        int[] lengths2 = {4, 2, 8, 4, 5, 4, 5, 4, 3, 4};
        int[] lengths3 = {1,9};
        System.err.println(splitWords(lengths1, 1));
        System.err.println(splitWords(lengths2, 19));
        System.err.println(splitWords(lengths3, 8));
    }
}
