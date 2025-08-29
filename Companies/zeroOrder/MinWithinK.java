package zeroOrder;

import java.util.PriorityQueue;

public class MinWithinK {
    private int[] getMinWithinK(int[] input, int within) {
        PriorityQueue<Integer> pq = new PriorityQueue<>(within);
        pq.offer(Integer.MAX_VALUE);
        int[] result = new int[input.length];


        for (int i = 0; i < input.length; i++) {
            result[i] = pq.peek();

            while (pq.size() > within) {
                pq.poll();
            }
            pq.offer(input[i]);
        }

        return result;
    }

    public static void main(String[] args) {
        int[] input = {5, 1, 2, 3, 1, 0, 7};
        int within = 3;
        MinWithinK minWithinK = new MinWithinK();
        int[] withinK = minWithinK.getMinWithinK(input, within);
        for (int i : withinK) {
            System.out.print(i + "\t");
        }
    }
}
