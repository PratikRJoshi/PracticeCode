package zeroOrder;

public class RangeSum2D {
    int[][] dp;
    public RangeSum2D(int[][] matrix) {
        if(matrix == null
                || matrix.length == 0
                || matrix[0].length == 0){

            return;
        }

        dp = new int[matrix.length + 1][matrix.length + 1];

        for(int i = 1; i < dp.length; i++){
            for(int j = 1; j < dp[i].length; j++){
                dp[i][j] = dp[i - 1][j] + dp[i][j - 1] - dp[i - 1][j - 1] + matrix[i - 1][j - 1];
            }
        }
    }

    public int sumRegion(int row1, int col1, int row2, int col2) {
        return dp[row2 + 1][col2 + 1] - dp[row1][col2 + 1] - dp[row2 + 1][col1] + dp[row1][col1];
    }

    public static void main(String[] args) {
//        int[][] matrix = new int[][]{{3, 0, 1, 4, 2},
//                                        {5, 6, 3, 2, 1},
//                                        {1, 2, 0, 1, 5},
//                                        {4, 1, 0, 1, 7},
//                                        {1, 0, 3, 0, 5}};
        int[][] matrix = new int[][]{{-4, -5}};

        RangeSum2D rangeSum2D = new RangeSum2D(matrix);
        int row1 = 0, col1 = 1, row2 = 0, col2 = 1;
        System.out.println(rangeSum2D.sumRegion(row1, col1, row2, col2));
    }
}
