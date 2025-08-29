package zeroOrder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

public class KSmallestPairs {
    public static List<List<Integer>> kSmallestPairs(int[] nums1, int[] nums2, int k) {
        PriorityQueue<List<Integer>> pq = new PriorityQueue<>((a, b) -> ((a.get(0) + a.get(1)) - (b.get(0) + b.get(1))));
        List<List<Integer>> result = new ArrayList<>();
        if(nums1 == null || nums1.length == 0 || nums2 == null || nums2.length == 0 || k == 0)
            return result;

        for(int i = 0; i < nums1.length  && i < k; i++){
            pq.offer(Arrays.asList(nums1[i], nums2[0], 0));
        }

        while(!pq.isEmpty() && k > 0){
            k--;
            List<Integer> current = pq.poll();
            result.add(Arrays.asList(current.get(0), current.get(1)));
            if(current.get(2) == nums2.length - 1) {
                continue;   // we have exhausted elements in nums2, so just continue with remaining nums1 elements
            }
            pq.offer(Arrays.asList(current.get(0), nums2[current.get(2) + 1], current.get(2) + 1));
        }
        return result;
    }

    public static void main(String[] args) {
        int[] nums1 = {1,7,11};
        int[] nums2 = {2,4,6};
        int k = 3;
        List<List<Integer>> result = kSmallestPairs(nums1, nums2, k);
        System.out.println(result);

    }
}
