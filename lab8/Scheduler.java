package lab8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Stack;


public class Scheduler {
	public static HashMap<Integer, Integer> assignRooms(
		int R,                        /* number of rooms */
		ArrayList<Integer> starts,     /* meeting start times indexed by ID */
		ArrayList<Integer> ends     /* meeting finish times indexed by ID */
	) {
        HashMap<Integer, Integer> map = new HashMap<>(); // meeting # - room #
        int lastRoom = 0; 
        Stack<Integer> availableRooms = new Stack<Integer>();

        int[][] events = new int[starts.size() + ends.size()][3]; // "3": (time, type, idx)

        // n
        for (int i = 0; i < starts.size(); i++){
            events[i * 2] = new int[]{starts.get(i), 1, i}; // (time, start?, idx)
            events[i * 2 + 1] = new int[]{ends.get(i), 0, i};
        }

        // n log n
        // sort by start.
        // if same, sort by end ==> processes immediate end-start correctly
        Arrays.sort(events, (a,b) -> a[0] != b[0] ? a[0] - b[0] : a[1] - b[1]);

        for (int i = 0; i < events.length; i++){
            // int time = events[i][0];
            int isStart = events[i][1];
            int idx = events[i][2]; 

            if (isStart == 1){
                if (availableRooms.size() == 0){
                    lastRoom += 1;
                    if (lastRoom > R) return null; // checking allowed room num
                    map.put(idx, lastRoom);
                } else {
                    int roomNumber = availableRooms.pop();
                    map.put(idx, roomNumber);
                }
            } 

            if (isStart == 0){
                map.remove(idx);
                availableRooms.add(idx);
            }
        }
        
		return map;
	}
}
