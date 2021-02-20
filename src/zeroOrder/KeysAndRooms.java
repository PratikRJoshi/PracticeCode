package zeroOrder;

import java.util.*;

public class KeysAndRooms {
    public boolean canVisitAllRooms(List<List<Integer>> rooms) {
        if (rooms == null || rooms.size() == 0)
            return false;
        boolean[] visited = new boolean[rooms.size()];

        Queue<Integer> queue = new LinkedList<>(rooms.get(0));
        visited[0] = true;
        while (!queue.isEmpty()) {
            int room = queue.remove();
            if (!visited[room]) {
                visited[room] = true;
                queue.addAll(rooms.get(room));
            }
        }

        for (boolean b : visited){
            if (!b)
                return false;
        }
        return true;
    }

    public static void main(String[] args) {
        int[][] roomArray = {{1},{2},{3},{}};
//        int[][] roomArray = {{1, 3},{3, 0 , 1},{2},{0}};
        List<List<Integer>> rooms = new ArrayList<>();
        for (int[] room : roomArray) {
            List<Integer> temp = new ArrayList<>();
            for (int r : room)
                temp.add(r);
            rooms.add(temp);
        }

        KeysAndRooms keysAndRooms = new KeysAndRooms();
        boolean result = keysAndRooms.canVisitAllRooms(rooms);
        System.out.println(result);
    }
}
