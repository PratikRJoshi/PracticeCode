package zeroOrder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class MergeIntervals {
    private static int[][] merge(int[][] intervals) {
        if(intervals.length <= 1)
            return intervals;

//        Arrays.sort(intervals, (i1, i2) -> Integer.compare(i1[0], i2[0]));
        Arrays.sort(intervals, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return 0;
            }
        });

        List<int[]> result = new ArrayList<>();
        int[] newinterval = intervals[0];
        result.add(newinterval);

        for(int[] interval : intervals) {
            if(interval[0] <= newinterval[1]){
                newinterval[1] = Math.max(newinterval[1], interval[1]);
            } else {
                newinterval = interval;
                result.add(newinterval);
            }
        }

        return result.toArray(new int[result.size()][]);
    }

    public static void main(String[] args) {
        int[][] input = {{1,3},{2,6},{8,10},{15,18}};

        int[][] result = merge(input);
        System.out.println(Arrays.deepToString(result));
    }
}
