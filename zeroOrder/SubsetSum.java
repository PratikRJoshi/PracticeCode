package zeroOrder;

public class SubsetSum {
    public static boolean subsetSumBottomUp(int[] array, int sum){
        int n = array.length;
        boolean[][] dp = new boolean[n + 1][sum + 1];

        dp[0][0] = true;
        for (int i = 1; i < n + 1; i++){
            dp[i][0] = true;
        }
        for (int j = 1; j < sum + 1; j++){
            dp[0][j] = false;
        }

        for (int i = 1; i < n + 1; i++){
            for (int j = 1; j < sum + 1; j++){
                if (array[i - 1] <= j){
                    dp[i][j] = dp[i - 1][j - array[i - 1]] || dp[i - 1][j];
                } else {
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }
        return dp[n][sum];
    }

    public static void main(String[] args) {
        int[] array = {3, 34, 4, 12, 5, 2};
        int sum = 9;

        System.out.println(subsetSumBottomUp(array, sum));
    }
}
