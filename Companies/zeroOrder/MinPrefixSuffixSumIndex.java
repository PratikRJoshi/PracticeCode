package zeroOrder;

public class MinPrefixSuffixSumIndex {
    private static int indexMinSum(int nums[]) {
        if (nums == null || nums.length == 0)
            return -1;
        int minIndex = 0, minVal = Integer.MAX_VALUE;

        // prefixSum[i] + suffixSum[i] = sumOfArrayElements + array[i]
        // since sumOfArrayElements is constant, LHS will be min for the min value of index
        for (int i = 0; i < nums.length; i++){
            if (nums[i] < minVal){
                minVal = nums[i];
                minIndex = i;
            }
        }
        return minIndex + 1;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{6, 8, 2, 3, 5};
        System.out.println(indexMinSum(nums));
    }
}
