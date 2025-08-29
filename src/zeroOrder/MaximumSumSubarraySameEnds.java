package zeroOrder;

import java.util.HashMap;
import java.util.Map;

public class MaximumSumSubarraySameEnds {
    private static int maxValue(int[] nums) {
        if (nums == null || nums.length == 0)
            return 0;

        Map<Integer, Integer> firstOccurrence = new HashMap<>();
        Map<Integer, Integer> lastOccurrence = new HashMap<>();

        for (int i : nums){
            firstOccurrence.put(i, 0);
            lastOccurrence.put(i, 0);
        }

        // build the prefix sum array
        int[] prefix = new int[nums.length];
        prefix[0] = nums[0];
        for (int i = 1; i < nums.length; i++){
            prefix[i] = prefix[ i - 1] + nums[i];

            if (firstOccurrence.get(nums[i]) == 0){
                firstOccurrence.put(nums[i], i);
            }

            lastOccurrence.put(nums[i], i);
        }

        int maxSum = 0;
        for (int i = 0; i < nums.length; i++){
            int first = firstOccurrence.get(nums[i]);
            int last = lastOccurrence.get(nums[i]);

            if (first != 0){
                maxSum = Math.max(maxSum, prefix[last] - prefix[first - 1]);
            }
        }

        return maxSum;
    }

    public static void main(String[] args) {
        int[] arr = new int[]{ 3, 2, 2, 3, 1 };
        System.out.print(maxValue(arr));
    }
}
