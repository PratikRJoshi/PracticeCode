package zeroOrder;

public class MinSubsetDifference {
    public static int minSubsetDifference(int[] array){
        if (array == null || array.length == 0)
            return 0;

        int range = 0;
        for (int i : array){
            range += i;
        }

        boolean[][] dp = new boolean[array.length + 1][range + 1];
        dp[0][0] = true;

        for (int i = 0; i < array.length + 1; i++){
            dp[i][0] = true;
        }

        for (int i = 1; i < array.length + 1; i++){
            for (int j = 1; j < range + 1; j++){
                if (array[i - 1] <= j){
                    dp[i][j] = dp[i - 1][j] || dp[i - 1][j - array[i - 1]];
                } else {
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }

        int min = Integer.MAX_VALUE;
        for (int i = 0; i < range / 2; i++){
            if (dp[dp.length - 1][i]){
                min = Math.min(min, range - 2 * i);
            }
        }
        return min;
    }

    public static void main(String[] args) {
        int[] array = {1, 2, 7};

        System.out.println(minSubsetDifference(array));
    }
}
