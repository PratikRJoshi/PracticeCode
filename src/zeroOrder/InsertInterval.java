package zeroOrder;

import java.util.ArrayList;
import java.util.List;

public class InsertInterval {
    public static int[][] insert(int[][] intervals, int[] newInterval) {
        List<int[]> result = new ArrayList<>();

        int index = 0;
        while(index < intervals.length && intervals[index][1] < newInterval[0]){
            result.add(intervals[index++]);
        }

        while (index < intervals.length && intervals[index][0] <= newInterval[1]){
            newInterval[0] = Math.min(intervals[index][0], newInterval[0]);
            newInterval[1] = Math.max(newInterval[1], intervals[index][1]);
            index++;
        }
        result.add(newInterval);

        while (index < intervals.length)
            result.add(intervals[index++]);

        return result.toArray(new int[result.size()][]);
    }


    public static void main(String[] args) {
        int[][] intervals = new int[][]{{1,3},{6,9}};
        int[] interval = {2,5};

        int[][] inserts = insert(intervals, interval);
        for (int[] insert : inserts){
            System.out.println(insert[0] + " " + insert[1]);
        }
    }
}
