package zeroOrder;

import java.util.ArrayDeque;
import java.util.Deque;

public class SlidingWindowMax {
    private static int[] slidingWindowMax(int[] a, int k){
        int n = a.length;
        int[] result = new int[a.length];
        int resultIndex = 0;

        // deque<index>
        Deque<Integer> dq = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            // remove the out of window elements
            while (!dq.isEmpty() && dq.peek() < i - k + 1){
                dq.poll();
            }

            // remove all the invalid/small elements
            while (!dq.isEmpty() && a[dq.peekLast()] < a[i]){
                dq.pollLast();
            }

            dq.offer(i);
            if (i >= k - 1){
                result[resultIndex++] = a[dq.peek()];
            }
        }

        return result;
    }

    public static void main(String[] args) {
        int[] input = {1,3,-1,-3,5,3,6,7};
        int k = 3;
        int[] slidingWindowMax = slidingWindowMax(input, k);
        for (int s : slidingWindowMax) {
            System.out.print(s + " ");
        }
    }
}
