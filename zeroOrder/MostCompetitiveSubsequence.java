package zeroOrder;

import java.util.Arrays;
import java.util.PriorityQueue;

public class MostCompetitiveSubsequence {

    public static int[] mostCompetitive(int[] nums, int k) {
        int[] result = new int[k];
        if(nums == null || nums.length == 0 || k == 0)
            return result;

        PriorityQueue<Pair> pq = new PriorityQueue<>((p1, p2) -> p1.key == p2.key ? p1.value - p2.value : p1.key - p2.key);

        for(int i = 0; i < nums.length; i++){
            pq.offer(new Pair(nums[i], i));
        }

        int index = 0, prevIndex = -1;
        while (k > 0 && pq.size() >= k){
            if (pq.peek().value > prevIndex){
                result[index++] = pq.peek().key;
                prevIndex = pq.poll().value;
                k--;
            } else {
                pq.poll();
            }
        }

        return result;
    }

    public static void main(String[] args) {
        int[] array = new int[]{3,5,2,6};
        int k = 2;
        int[] ints = mostCompetitive(array, k);
        System.out.println(Arrays.toString(ints));

        array = new int[]{2,4,3,3,5,4,9,6};
        k = 4;
        ints = mostCompetitive(array, k);
        System.out.println(Arrays.toString(ints));

        array = new int[]{71,18,52,29,55,73,24,42,66,8,80,2};
        k = 3;
        ints = mostCompetitive(array, k);
        System.out.println(Arrays.toString(ints));
    }
}