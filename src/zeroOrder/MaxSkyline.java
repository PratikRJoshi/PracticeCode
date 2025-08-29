package zeroOrder;

public class MaxSkyline {
    public int maxIncreaseKeepingSkyline(int[][] grid) {
        int[] leftRightMax = new int[grid.length];
        int[] topBottomMax = new int[grid.length];

        for (int i = 0; i < grid.length; i++) {
            int max = Integer.MIN_VALUE;
            for (int j : grid[i]) {
                max = Math.max(max, j);
            }
            leftRightMax[i] = max;
        }

        for (int j = 0; j < grid[0].length; j++){
            int max = Integer.MIN_VALUE;
            for (int i = 0; i < grid.length; i++) {
                max = Math.max(max, grid[i][j]);
            }
            topBottomMax[j] = max;
        }

        int maxIncreaseBy = 0;

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                int min = Math.min(leftRightMax[i], topBottomMax[j]);
                maxIncreaseBy += Math.abs(min - grid[i][j]);
            }
        }

        return maxIncreaseBy;
    }

    public static void main(String[] args) {
        int[][] array = {{3,0,8,4},{2,4,5,7},{9,2,6,3},{0,3,1,0}};
        MaxSkyline maxSkyline = new MaxSkyline();
        int result = maxSkyline.maxIncreaseKeepingSkyline(array);
        System.out.println(result);
    }
}
