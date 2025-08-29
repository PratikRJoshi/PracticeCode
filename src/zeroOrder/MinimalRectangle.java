package zeroOrder;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MinimalRectangle {
    private int minAreaRect(int[][] points) {
        Map<Integer, Set<Integer>> map = new HashMap<>();

        for (int[] p : points) {
            if (!map.containsKey(p[0])) {
                map.put(p[0], new HashSet<Integer>());
            }
            map.get(p[0]).add(p[1]);
        }

        int min = Integer.MAX_VALUE;
        for (int[] p : points) {
            for (int[] q : points) {
                if (p[0] == q[0] || p[1] == q[1])
                    continue;
                else if (map.get(p[0]).contains(q[1]) && map.get(q[0]).contains(p[1])) {
                    min = Math.min(min, Math.abs(p[0] - q[0]) * Math.abs(p[1] - q[1]));
                }
            }
        }

        return (min == Integer.MAX_VALUE ? 0 : min);
    }

    public static void main(String[] args) {
        int[][] points = {{1,1},{1,3},{3,1},{3,3},{2,2}};
        MinimalRectangle minimalRectangle = new MinimalRectangle();
        int minAreaRect = minimalRectangle.minAreaRect(points);
        System.out.println(minAreaRect);
    }
}
