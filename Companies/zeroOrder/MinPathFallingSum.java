package zeroOrder;

public class MinPathFallingSum {
    public int minFallingPathSum(int[][] A) {
        int[][] dp = new int[A.length][A.length];

        for (int i = 0; i < A.length; i++) {
            dp[0][i] = A[0][i];
        }

        for (int i = 1; i < dp.length; i++) {
            for (int j = 0; j < dp[i].length; j++) {
                int topLeft = (j - 1) >= 0 ? dp[i-1][j - 1] : Integer.MAX_VALUE;
                int topRight = (j + 1) <= dp[i].length - 1 ? dp[i- 1][j + 1] : Integer.MAX_VALUE;
                dp[i][j] = Math.min(dp[i - 1][j], Math.min(topLeft, topRight)) + A[i][j];
            }
        }

        int min = Integer.MAX_VALUE;
        for (int col = 0; col < dp[0].length; col++){
            min = Math.min(min, dp[A.length - 1][col]);
        }

        return min;
    }

    public static void main(String[] args) {
        int[][] input = {{1,2,3},{4,5,6},{7,8,9}};
        MinPathFallingSum minPathFallingSum = new MinPathFallingSum();
        int fallingPathSum = minPathFallingSum.minFallingPathSum(input);
        System.out.println(fallingPathSum);
    }
}
