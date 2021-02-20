package zeroOrder;

public class RodCutting {

    public static int maxProfitToCutRod(int[] price){
        if (price == null || price.length == 0)
            return 0;

        int[][] dp = new int[price.length + 1][price.length + 1]; // size, rod length

        for (int i = 1; i < price.length + 1; i++) {
            for (int j = 1; j < price.length + 1; j++) {
                if (price[i - 1] <= j){
                    dp[i][j] = Math.max(price[i - 1] + dp[i][j - price[i - 1]], // rod is cut at this length
                            dp[i - 1][j]); // rod is NOT cut at this length
                } else {
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }

        return dp[price.length][price.length];
    }

    public static void main(String[] args) {
        int[] price = new int[]{1, 5, 8, 9, 10, 17, 17, 20};
        System.out.println(maxProfitToCutRod(price));
    }
}
