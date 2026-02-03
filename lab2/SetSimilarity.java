import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SetSimilarity {
    // Decide which algo to use
    public static <E> Map<Integer, Map<Integer, Double>> similarities(ArrayList<Set<E>> setList) {
        int n = setList.size();
        
        Map<E, List<Integer>> appearance = new HashMap<>();
        long totalElements = 0;
        
        // Calc dense work
        for (int i = 0; i < n; i++) {
            for (E element : setList.get(i)) {
                appearance.computeIfAbsent(element, k -> new ArrayList<>()).add(i);
                totalElements++;
            }
        }
        double avgSetSize = (double) totalElements / n; // calculate q
        long densePairCount = (long) n * (n - 1) / 2; // n choose 2
        double denseEstimate = densePairCount * avgSetSize;
        
        // Calc sparse work
        long sparsePairWork = 0;
        for (List<Integer> setIndices : appearance.values()) {
            int size = setIndices.size(); // "q"
            sparsePairWork += (long) size * (size - 1) / 2; // q choose 2
        }
        
        
        // Choose algorithm
        if (sparsePairWork < denseEstimate * 0.5) {
            return sparseAlgorithm(setList, appearance);
        } else {
            return denseAlgorithm(setList);
        }
    }
    
    // for dense
    private static <E> Map<Integer, Map<Integer, Double>> denseAlgorithm(ArrayList<Set<E>> setList) {
        Map<Integer, Map<Integer, Double>> similarities = new HashMap<>();
        int n = setList.size();
        
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                Set<E> a = setList.get(i);
                Set<E> b = setList.get(j);
                
                int sizeInter = 0;
                Set<E> smaller = a.size() <= b.size() ? a : b;
                Set<E> larger = a.size() <= b.size() ? b : a;
                
                for (E element : smaller) {
                    if (larger.contains(element)) {
                        sizeInter++;
                    }
                }
                
                int sizeA = a.size();
                int sizeB = b.size();
                int sizeUnion = sizeA + sizeB - sizeInter;
                
                double sim = (double) sizeInter / sizeUnion;
                
                if (sim > 0) {
                    similarities.computeIfAbsent(i, k -> new HashMap<>()).put(j, sim);
                }
            }
        }
        
        return similarities;
    }
    
    // for sparse
    private static <E> Map<Integer, Map<Integer, Double>> sparseAlgorithm(ArrayList<Set<E>> setList, Map<E, List<Integer>> appearance) {
        
        Map<Integer, Map<Integer, Double>> similarities = new HashMap<>();
        Map<Integer, Map<Integer, Integer>> intersections = new HashMap<>();
        
        // Count intersections using appearance
        for (List<Integer> setIndices : appearance.values()) {
            for (int i = 0; i < setIndices.size() - 1; i++) {
                for (int j = i + 1; j < setIndices.size(); j++) {
                    int a = setIndices.get(i);
                    int b = setIndices.get(j);
                    
                    intersections
                        .computeIfAbsent(a, k -> new HashMap<>())
                        .merge(b, 1, Integer::sum);
                }
            }
        }
        
        for (int i : intersections.keySet()) {
            Map<Integer, Integer> row = intersections.get(i);
            
            for (int j : row.keySet()) {
                int sizeInter = row.get(j);
                int sizeA = setList.get(i).size();
                int sizeB = setList.get(j).size();
                int sizeUnion = sizeA + sizeB - sizeInter;
                
                double sim = (double) sizeInter / sizeUnion;
                
                similarities.computeIfAbsent(i, k -> new HashMap<>()).put(j, sim);
            }
        }
        
        return similarities;
    }

    public static void main(String[] args) {
        ArrayList<Set<Integer>> test1 = new ArrayList<>(List.of(
            Set.of(1, 2, 3),
            Set.of(1, 2, 3),
            Set.of(1, 2, 4)
        ));
        System.out.println("Test 1: " + similarities(test1));
        
        ArrayList<Set<Integer>> test2 = new ArrayList<>(List.of(
            Set.of(1),
            Set.of(1, 2, 3)
        ));
        System.out.println("Test 2: " + similarities(test2));
        
        ArrayList<Set<Integer>> test3 = new ArrayList<>(List.of(
            Set.of(5),
            Set.of(1, 2, 3)
        ));
        System.out.println("Test 3 (disjoint): " + similarities(test3));
    }
}