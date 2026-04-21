import java.util.*;
public class Consistency {

    public static Set<IntervalInfo> checkStatements(Collection<Statement> statements) {
        Map<String, Set<String>> adj = new HashMap<>();
        Set<Integer> seen = new HashSet<>();

        for (Statement st : statements) {
            int f = st.firstInterval();
            int s = st.secondInterval();
            String fs = f + "s", fe = f + "e", ss = s + "s", se = s + "e";

            seen.add(f);
            seen.add(s);

            for (String node : new String[]{fs, fe, ss, se})
                adj.computeIfAbsent(node, k -> new HashSet<>());

            // Every interval start < end
            addEdge(adj, fs, fe);
            addEdge(adj, ss, se);

            if (st.isOverlap()) {
                addEdge(adj, fs, se);
                addEdge(adj, ss, fe);
            } else {
                addEdge(adj, fe, ss);
            }
        }

        Map<String, Integer> state = new HashMap<>(); // 0=unvisited, 1=in-progress, 2=done
        Deque<String> order = new ArrayDeque<>();

        for (String node : adj.keySet()) {
            if (state.getOrDefault(node, 0) == 0) {
                if (!dfs(node, adj, state, order)) return null;
            }
        }

        // Assign times
        Map<String, Integer> timeOf = new HashMap<>();
        int time = 1;
        while (!order.isEmpty())
            timeOf.put(order.pop(), time++);

        Set<IntervalInfo> result = new HashSet<>();
        for (int id : seen) {
            IntervalInfo info = new IntervalInfo(id);
            info.setStart(timeOf.get(id + "s"));
            info.setEnd(timeOf.get(id + "e"));
            result.add(info);
        }
        return result;
    }

    private static boolean dfs(String node, Map<String, Set<String>> adj,
                               Map<String, Integer> state, Deque<String> order) {
        state.put(node, 1);
        for (String neighbor : adj.getOrDefault(node, Collections.emptySet())) {
            int s = state.getOrDefault(neighbor, 0);
            if (s == 1) return false; // back edge = cycle
            if (s == 0 && !dfs(neighbor, adj, state, order)) return false;
        }
        state.put(node, 2);
        order.push(node);
        return true;
    }

    private static void addEdge(Map<String, Set<String>> adj, String from, String to) {
        adj.computeIfAbsent(from, k -> new HashSet<>()).add(to);
    }

}
