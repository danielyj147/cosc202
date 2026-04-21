package lab10;
import java.util.*;
public class Consistency {

    public static Set<IntervalInfo> checkStatements(Collection<Statement> statements) {
        Map<String, Set<String>> adj = new HashMap<>();
        Map<String, Integer> inDegree = new HashMap<>();
        Set<Integer> seen = new HashSet<>();

        for (Statement st : statements) {
            int f = st.firstInterval();
            int s = st.secondInterval();
            String fs = f + "s", fe = f + "e", ss = s + "s", se = s + "e";

            seen.add(f);
            seen.add(s);

            // Ensure all nodes exist in the graph
            for (String node : new String[]{fs, fe, ss, se}) {
                adj.computeIfAbsent(node, k -> new HashSet<>());
                inDegree.putIfAbsent(node, 0);
            }

            // Every interval: start < end
            addEdge(adj, inDegree, fs, fe);
            addEdge(adj, inDegree, ss, se);

            if (st.isOverlap()) {
                // X starts before Y ends AND Y starts before X ends
                addEdge(adj, inDegree, fs, se);
                addEdge(adj, inDegree, ss, fe);
            } else {
                // X ends before Y starts
                addEdge(adj, inDegree, fe, ss);
            }
        }

        // DFS
        Queue<String> queue = new LinkedList<>();
        for (var entry : inDegree.entrySet()) {
            if (entry.getValue() == 0) queue.add(entry.getKey());
        }

        Map<String, Integer> timeOf = new HashMap<>();
        int time = 1;
        while (!queue.isEmpty()) {
            String node = queue.poll();
            timeOf.put(node, time++);
            for (String neighbor : adj.getOrDefault(node, Collections.emptySet())) {
                int deg = inDegree.merge(neighbor, -1, Integer::sum);
                if (deg == 0) queue.add(neighbor);
            }
        }

        if (timeOf.size() != inDegree.size()) return null;

        // Build result
        Set<IntervalInfo> result = new HashSet<>();
        for (int id : seen) {
            IntervalInfo info = new IntervalInfo(id);
            info.setStart(timeOf.get(id + "s"));
            info.setEnd(timeOf.get(id + "e"));
            result.add(info);
        }
        return result;
    }

    private static void addEdge(Map<String, Set<String>> adj, Map<String, Integer> inDegree, String from, String to) {
        if (adj.computeIfAbsent(from, k -> new HashSet<>()).add(to)) {
            inDegree.merge(to, 1, Integer::sum);
        }
    }

}