package zeroOrder;

import java.util.Arrays;

public class TargetSum {
    int count = 0;
    public int findTargetSumWays1(int[] nums, int S) {
        int[][] memo = new int[nums.length][2001];
        for (int[] row : memo)
            Arrays.fill(row, Integer.MIN_VALUE);
        return calculate(nums, S, 0, 0, memo);
    }

    private int calculate(int[] nums, int S, int sum, int index, int[][] memo){
        if(index == nums.length){
            if(sum == S){
                return 1;
            } else {
                return 0;
            }
        } else {
            if (memo[index][sum + 1000] != Integer.MIN_VALUE)
                return memo[index][sum + 1000];

            int add = calculate(nums, S, sum + nums[index], index+1, memo);
            int subtract = calculate(nums, S, sum - nums[index], index+1, memo);
            memo[index][sum + 1000] = add + subtract;

            return memo[index][sum + 1000];
        }
    }

    public static void main(String[] args) {
//        int[] nums = {1, 1, 1, 1, 1};
        int[] nums = {1};
//        int S = 3;
        int S = 2;
        TargetSum targetSum = new TargetSum();
        int targetSumWays = targetSum.findTargetSumWays(nums, S);
        System.out.println(targetSumWays);
    }

    private int findTargetSumWays(int[] nums, int target) {
        if(nums == null || nums.length == 0)
            return 0;

        int sum = 0;
        for (int n : nums){
            sum += n;
        }

        if(sum < target || (sum + target) % 2 == 1) {
            return 0;
        }

        sum = (sum + target) / 2;
        // finding ways to get target sum is same as subset sum for (diff + sum) / 2;
        int[][] dp = new int[nums.length + 1][sum + 1];
        for (int i = 0; i < nums.length + 1; i++){
            dp[i][0] = 1;
        }

        for (int i = 1; i < nums.length + 1; i++) {
            for (int j = 0; j < sum + 1; j++) {
                if (nums[i - 1] <= j){
                    dp[i][j] = dp[i - 1][j] + dp[i - 1][j - nums[i - 1]];
                } else {
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }

        return dp[nums.length][sum];
    }
}
