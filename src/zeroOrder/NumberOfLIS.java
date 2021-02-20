package zeroOrder;

import java.util.Arrays;

public class NumberOfLIS {
    public int findNumberOfLIS(int[] nums) {
        if(nums == null || nums.length == 0)
            return 0;
        int[] dp = new int[nums.length];
        Arrays.fill(dp, 1);

        for(int i = 1; i < nums.length; i++){
            for(int j = 0; j < i; j++){
                if(nums[i] > nums[j] && dp[j] + 1 > dp[i]) {
                    dp[i] = dp[j] + 1;
                }
            }
        }
        int max = 1, count = 1;
        for(int i = 0; i < dp.length; i++){
            if(dp[i] > max){
                max = dp[i];
                count = 1;
            } else if(dp[i] == max){
                count++;
            }
        }

        return count;
    }

    public static void main(String[] args) {
        int[] nums = {1,3,5,4,7};
        NumberOfLIS numberOfLIS = new NumberOfLIS();
        int numberOfLIS1 = numberOfLIS.findNumberOfLIS(nums);
        System.out.println(numberOfLIS1);
    }
}
