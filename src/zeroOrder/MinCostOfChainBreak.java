package zeroOrder;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Random;

public class MinCostOfChainBreak {
    private static int minCost(int[] A){
        if (A == null || A.length == 0)
            return 0;

        int min = Integer.MAX_VALUE;
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 1; i < A.length - 1; i++){
            map.put(A[i], i);
        }

        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for (int i : map.keySet()){
            pq.offer(i);
        }

        int lowestVal = pq.poll();
        int lowestIndex = map.get(lowestVal);

        while (!pq.isEmpty()){
            if (map.get(pq.peek()) - lowestIndex > 1){
                min = lowestVal + pq.poll();
                break;
            } else {
                pq.poll();
            }
        }

        return min;
    }

    public static void main(String[] args) {
        System.out.println(minCost(new int[]{5, 2, 4, 6, 3, 7})); // 5
        System.out.println(minCost(new int[]{8,6,1,5,7,9,11})); // 8

        Random random = new Random();
        int n = 8;
        int[] array = new int[n];

        for (int i = 0; i < n; i++) {
            array[i] = Math.abs(random.nextInt(1_00_000));
        }

        System.out.println("input array: \n" + Arrays.toString(array));
        System.out.println("min cost = " + minCost(array));

    }
}
