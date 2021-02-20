package zeroOrder;

import java.util.TreeSet;

public class MaxSubarraySumModK {
    private static int maxSubarray(int[] nums, int m) {
        TreeSet<Integer> set = new TreeSet<>();

        int prefixSum = 0, maxSum = 0;
        set.add(0);

        for (int i = 0; i < nums.length; i++) {
            prefixSum = (prefixSum + nums[i]) % m;
            maxSum = Math.max(maxSum, prefixSum);

            if (!set.isEmpty()){
                int lowerBoundPrefixSum = set.ceiling(prefixSum + 1);

                if (lowerBoundPrefixSum != set.size()){
                    maxSum = Math.max(maxSum, prefixSum - lowerBoundPrefixSum + m);
                }
            }

            set.add(prefixSum);
        }

        return maxSum;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{3, 3, 9, 9, 5};
        int m = 7;

        System.out.println(maxSubarray(nums, m));
    }
}
