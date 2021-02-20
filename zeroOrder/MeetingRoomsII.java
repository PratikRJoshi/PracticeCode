package zeroOrder;

import java.util.Map;
import java.util.TreeMap;

public class MeetingRoomsII {
    public int minMeetingRooms(int[][] intervals) {
        Map<Integer, Integer> map = new TreeMap<>();
        for (int[] itl : intervals) {
            map.put(itl[0], map.getOrDefault(itl[0], 0) + 1);
            map.put(itl[1], map.getOrDefault(itl[1], 0) - 1);
        }
        int room = 0, k = 0;
        for (int v : map.values()) {
            room += v;
            k = Math.max(k, room);
        }

        return k;
    }

    public static void main(String[] args) {
//        int[][] intervals = new int[][]{{9,10},{4,8},{4,17}};
        int[][] intervals = new int[][]{{9,10},{5,8},{4,17}};
//        int[][] intervals = new int[][]{{7, 10}, {2, 4}};
        MeetingRoomsII meetingRoomsII = new MeetingRoomsII();
        int minMeetingRooms = meetingRoomsII.minMeetingRooms(intervals);
        System.out.println(minMeetingRooms);
    }
}
