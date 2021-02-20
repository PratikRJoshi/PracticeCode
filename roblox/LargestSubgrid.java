package roblox;

public class LargestSubgrid {
    int largestSubgrid(int[][] grid, int maxSum) {
        int n = grid.length;
        int[][] sum = new int[grid.length][grid.length];
        int max = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == 0 && j == 0) {
                    sum[0][0] = grid[0][0];
                } else if (i == 0) {
                    sum[0][j] = sum[0][j - 1] + grid[0][j];
                } else if (j == 0) {
                    sum[i][0] = sum[i - 1][0] + grid[i][0];
                } else {
                    sum[i][j] = sum[i - 1][j] + sum[i][j - 1] + grid[i][j] - sum[i - 1][j - 1];
                }
                max = Math.max(max, grid[i][j]);
            }
        }

        int left = 0, right = n;
        while (left < right) {
            int mid = left + (right - left + 1) / 2;
            int result = 0;
            for (int i = mid - 1; i < n; i++) {
                for (int j = mid - 1; j < n; j++) {
                    int total = sum[i][j];
                    if (i >= mid) {
                        total -= sum[i - mid][j];
                    }
                    if (j >= mid) {
                        total -= sum[i][j - mid];
                    }
                    if (i >= mid && j >= mid) {
                        total += sum[i - mid][j - mid];
                    }
                    result = Math.max(result, total);
                }
            }
            if (maxSum >= result) {
                left = mid;
            } else {
                right = mid - 1;
            }
        }
        return right;
    }
}
