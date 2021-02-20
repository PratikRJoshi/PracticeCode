package zeroOrder;

import java.util.Arrays;

public class NonOverlappingIntervals {
    private static int eraseOverlapIntervals(int[][] intervals) {
        if (intervals == null || intervals.length == 0) {
            return 0;
        }
        Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
        int count = 0;
        int[] prev = intervals[0];
        for (int i = 1; i < intervals.length; ++i) {
            if (intervals[i][0] < prev[1]) {
                prev[1] = Math.min(prev[1], intervals[i][1]);
                count++;
            } else {
                prev = intervals[i];
            }
        }
        return count;
    }

    public static void main(String[] args) {
        int[][] input = {{1,2}, {2,3}, {3,4}, {1,3}};
        int result = eraseOverlapIntervals(input);
        System.out.println(result);
    }
}
