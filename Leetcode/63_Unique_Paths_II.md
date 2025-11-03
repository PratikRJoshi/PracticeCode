### 63. Unique Paths II
### Problem Link: [Unique Paths II](https://leetcode.com/problems/unique-paths-ii/)
### Intuition
This problem is a variation of the classic "Unique Paths" problem with the added complexity of obstacles. We can solve it using dynamic programming. The key insight is that the number of ways to reach a cell is the sum of the number of ways to reach the cell above it and the cell to its left, unless the cell contains an obstacle, in which case there are 0 ways to reach it.

### Java Reference Implementation
```java
class Solution {
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        if (obstacleGrid == null || obstacleGrid.length == 0 || obstacleGrid[0].length == 0) {
            return 0;
        }
        
        int m = obstacleGrid.length;
        int n = obstacleGrid[0].length;
        
        // If the starting cell has an obstacle, there's no way to reach the end
        if (obstacleGrid[0][0] == 1) {
            return 0;
        }
        
        // dp[i][j] represents the number of unique paths to reach cell (i,j)
        int[][] dp = new int[m][n];
        
        // Initialize the first cell
        dp[0][0] = 1;
        
        // Initialize the first column
        for (int i = 1; i < m; i++) {
            dp[i][0] = (obstacleGrid[i][0] == 0 && dp[i-1][0] == 1) ? 1 : 0;
        }
        
        // Initialize the first row
        for (int j = 1; j < n; j++) {
            dp[0][j] = (obstacleGrid[0][j] == 0 && dp[0][j-1] == 1) ? 1 : 0;
        }
        
        // Fill the dp table
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (obstacleGrid[i][j] == 0) {
                    dp[i][j] = dp[i-1][j] + dp[i][j-1];
                } else {
                    dp[i][j] = 0;
                }
            }
        }
        
        return dp[m-1][n-1];
    }
}
```

### Optimized Implementation (Space-Optimized)
```java
class Solution {
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        int m = obstacleGrid.length;
        int n = obstacleGrid[0].length;
        
        // Use a 1D array to optimize space
        int[] dp = new int[n];
        dp[0] = 1;
        
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (obstacleGrid[i][j] == 1) {
                    dp[j] = 0;
                } else if (j > 0) {
                    dp[j] += dp[j-1];
                }
            }
        }
        
        return dp[n-1];
    }
}
```

### Requirement → Code Mapping
- **R0 (Handle edge cases)**: `if (obstacleGrid == null || obstacleGrid.length == 0 || obstacleGrid[0].length == 0) { return 0; }`
- **R1 (Initialize DP table)**: Create a 2D array `dp` where `dp[i][j]` represents the number of unique paths to cell (i,j)
- **R2 (Initialize first row and column)**: Set values based on obstacles in the first row and column
- **R3 (Fill DP table)**: `dp[i][j] = dp[i-1][j] + dp[i][j-1]` if there's no obstacle, otherwise `dp[i][j] = 0`
- **R4 (Return final result)**: `return dp[m-1][n-1]`

### Complexity
- **Time Complexity**: O(m*n) - We need to fill in the entire DP table
- **Space Complexity**: O(m*n) for the 2D DP table, or O(n) for the space-optimized version
