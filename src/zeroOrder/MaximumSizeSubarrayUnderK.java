package zeroOrder;

import java.util.Arrays;

public class MaximumSizeSubarrayUnderK {
    private static int maxSize(int[] nums, int k)
    {

        // Initialize prefix sum array as 0.
        int prefixsum[] = new int[nums.length + 1];
        Arrays.fill(prefixsum, 0);

        // Finding prefix sum of the array.
        for (int i = 0; i < nums.length; i++)
            prefixsum[i + 1] = prefixsum[i] + nums[i];

        return binarySearch(prefixsum, nums.length, k);
    }

    private static int binarySearch(int[] prefixsum, int n, int k) {
        int left = 1, right = n;
        int maxSize = -1;

        while (left <= right){
            int mid = left + (right - left) / 2;
            int i;
            for (i = mid; i <= n; i++){
                if (prefixsum[i] - prefixsum[i - mid] > k)
                    break;
            }
            if (i == n + 1){
                maxSize = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return maxSize;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{1, 2, 10, 4};
        int k = 14;
        System.out.println(maxSize(nums, k));
    }

}
