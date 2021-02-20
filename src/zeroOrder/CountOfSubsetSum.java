package zeroOrder;

public class CountOfSubsetSum {
    public static int countSubsetSum(int[] array, int sum){
        if (array == null || array.length == 0)
            return 0;

        int[][] dp = new int[array.length + 1][sum + 1];
        dp[0][0] = 1; // empty array, 0 sum

        for (int j = 1; j < array.length + 1; j++){
            dp[j][0] = 1;
        }

        for (int i = 1; i < array.length + 1; i++){
            for (int j = 1; j < sum + 1; j++){
                if (array[i - 1] <= j){
                    dp[i][j] = dp[i - 1][j] // did not choose the current element
                                + dp[i - 1][j - array[i - 1]];
                } else {
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }

        return dp[array.length][sum];
    }

    public static void main(String[] args) {
        int[] array = {2,3,5,8,10};
        int sum = 10;

        System.out.println(countSubsetSum(array, sum));
    }
}
