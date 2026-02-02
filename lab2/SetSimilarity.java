import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SetSimilarity {
    
    /**
     * 
     * @param setList list of sets
     * @return similariteis b/t sets
     */
    public static <E> Map<Integer, Map<Integer, Double>> similarities(ArrayList<Set<E>> setList){
        Map<Integer, Map<Integer, Double>> sims = new HashMap<>();
        int setsSize = setList.size();

        for(int i = 0; i < setsSize - 1; i++){
            for(int j = i + 1; j < setsSize; j++){ 
                Set<E> a = setList.get(i);
                Set<E> b = setList.get(j);

                Set<E> intersection = new HashSet<E>(a);
                intersection.retainAll(b);

                int intersectionSize = intersection.size();
                int aSize = a.size();
                int bSize = b.size();

                int unionSize = aSize + bSize - intersectionSize;

                double sim = (double) intersectionSize / unionSize;

                // Put sim to [i][j]
                sims.computeIfAbsent(i, k -> new HashMap<>()).put(j, sim);
            }
        }

        return sims;
    }

    public static void main(String[] args) {
        // TEST
        ArrayList<ArrayList<Set<Integer>>> cases = new ArrayList<>(List.of(
            new ArrayList<>(List.of(
                Set.of(1, 2, 3),
                Set.of(1, 2, 3),
                Set.of(1, 2, 4)
            )), 
            new ArrayList<>(List.of(
                Set.of(1),
                Set.of(1, 2, 3)
            )),
            new ArrayList<>(List.of(
                Set.of(5),
                Set.of(1, 2, 3)
            )),
            new ArrayList<>(List.of(
            )),
            new ArrayList<>(List.of(
                Set.of(1)
            ))
        ));
    
        for(int i = 0; i < cases.size(); i++){
            System.out.println(similarities(cases.get(i)).toString());
        }
    }
}