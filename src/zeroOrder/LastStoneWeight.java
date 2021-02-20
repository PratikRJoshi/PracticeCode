package zeroOrder;

import java.util.Collections;
import java.util.PriorityQueue;

public class LastStoneWeight {
    public static int lastStoneWeight(int[] stones) {
        if (stones == null || stones.length == 0)
            return 0;


        PriorityQueue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder());
        for (int i : stones){
            pq.offer(i);
        }

        while (pq.size() > 1){
            int firstHighest = pq.poll();
            int secondHighest = pq.poll();

            int difference = firstHighest - secondHighest;
            pq.offer(difference);
        }

        return (pq.size() == 1) ? pq.poll() : 0;
    }

    public static void main(String[] args) {
        int[] stones = new int[]{2,7,4,1,8};
        int lastStoneWeight = lastStoneWeight(stones);
        System.out.println(lastStoneWeight);
    }
}
