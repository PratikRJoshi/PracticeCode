### 62. Unique Paths
### Problem Link: [Unique Paths](https://leetcode.com/problems/unique-paths/)
### Intuition
This problem asks us to find the number of unique paths from the top-left corner to the bottom-right corner of a grid, where we can only move right or down. This is a classic dynamic programming problem.

The key insight is that the number of ways to reach a cell (i, j) is the sum of the number of ways to reach the cell above it (i-1, j) and the cell to its left (i, j-1). This is because we can only come from above or from the left.

We can use a 2D array to store the number of ways to reach each cell, or optimize further to use a 1D array since we only need the previous row's information.

### Java Reference Implementation
```java
class Solution {
    public int uniquePaths(int m, int n) {
        if (m <= 0 || n <= 0) { // [R0] Handle invalid inputs
            return 0;
        }
        
        // [R1] Create a 2D DP array where dp[i][j] represents the number of unique paths to cell (i, j)
        int[][] dp = new int[m][n];
        
        // [R2] Initialize the first row and first column to 1
        // There's only one way to reach any cell in the first row or first column
        for (int i = 0; i < m; i++) {
            dp[i][0] = 1;
        }
        
        for (int j = 0; j < n; j++) {
            dp[0][j] = 1;
        }
        
        // [R3] Fill the DP array
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                // [R4] The number of ways to reach cell (i, j) is the sum of ways to reach the cell above and to the left
                dp[i][j] = dp[i-1][j] + dp[i][j-1];
            }
        }
        
        return dp[m-1][n-1]; // [R5] Return the number of unique paths to the bottom-right corner
    }
}
```

### Alternative Implementation (Space-Optimized)
```java
class Solution {
    public int uniquePaths(int m, int n) {
        if (m <= 0 || n <= 0) {
            return 0;
        }
        
        // Use a 1D array to store the current row
        int[] dp = new int[n];
        
        // Initialize all values to 1 (first row)
        Arrays.fill(dp, 1);
        
        // Update the dp array row by row
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                // dp[j] represents the number of ways to reach the cell above
                // dp[j-1] represents the number of ways to reach the cell to the left
                dp[j] += dp[j-1];
            }
        }
        
        return dp[n-1];
    }
}
```

### Alternative Implementation (Mathematical Combination)
```java
class Solution {
    public int uniquePaths(int m, int n) {
        if (m <= 0 || n <= 0) {
            return 0;
        }
        
        // Total steps needed: (m-1) down steps + (n-1) right steps = (m+n-2) total steps
        // We need to choose which (m-1) steps out of (m+n-2) will be down steps
        // This is a combination problem: C(m+n-2, m-1)
        
        int N = m + n - 2; // Total steps
        int k = Math.min(m - 1, n - 1); // Choose the smaller one to minimize calculations
        
        long result = 1;
        
        // Calculate C(N, k) = N! / (k! * (N-k)!)
        // To avoid overflow, we calculate it as: (N * (N-1) * ... * (N-k+1)) / (1 * 2 * ... * k)
        for (int i = 1; i <= k; i++) {
            result = result * (N - k + i) / i;
        }
        
        return (int) result;
    }
}
```

### Understanding the Algorithm and Boundary Checks

1. **Dynamic Programming Approach:**
   - We use a 2D array `dp` where `dp[i][j]` represents the number of unique paths to reach cell (i, j)
   - Base case: There's only one way to reach any cell in the first row or first column (by moving only right or only down)
   - Recurrence relation: `dp[i][j] = dp[i-1][j] + dp[i][j-1]`
   - This builds up the solution incrementally from the top-left to the bottom-right

2. **Space-Optimized Approach:**
   - We can optimize the space complexity by using a 1D array
   - Since we only need information from the previous row and the current row, we can reuse the same array
   - As we process each row, `dp[j]` represents the number of ways to reach the cell above, and `dp[j-1]` represents the number of ways to reach the cell to the left

3. **Mathematical Approach:**
   - This problem can also be solved using combinatorics
   - To reach the bottom-right corner, we need to make exactly (m-1) down moves and (n-1) right moves
   - The total number of moves is (m+n-2)
   - We need to choose which (m-1) positions out of (m+n-2) will be down moves
   - This is equivalent to calculating the combination C(m+n-2, m-1)

4. **Edge Cases:**
   - Invalid grid dimensions (m <= 0 or n <= 0): Return 0
   - 1x1 grid: Return 1 (already at the destination)
   - 1xn or mx1 grid: Return 1 (only one possible path)

### Requirement → Code Mapping
- **R0 (Handle invalid inputs)**: `if (m <= 0 || n <= 0) { return 0; }` - Return 0 for invalid grid dimensions
- **R1 (Create DP array)**: `int[][] dp = new int[m][n];` - Create a 2D array to store the number of paths
- **R2 (Initialize base cases)**: Set the first row and first column to 1
- **R3 (Fill DP array)**: Process each cell to build up the solution
- **R4 (Apply recurrence relation)**: `dp[i][j] = dp[i-1][j] + dp[i][j-1];` - Calculate the number of paths to each cell
- **R5 (Return result)**: `return dp[m-1][n-1];` - Return the number of paths to the bottom-right corner

### Complexity Analysis
- **Time Complexity**: O(m×n)
  - We need to fill in the entire DP array
  - For each cell, we perform constant-time operations
  - Overall: O(m×n)

- **Space Complexity**: 
  - 2D DP approach: O(m×n) - We use a 2D array of size m×n
  - 1D DP approach: O(n) - We use a 1D array of size n
  - Mathematical approach: O(1) - We use only a constant amount of extra space

### Related Problems
- **Unique Paths II** (Problem 63): Similar but with obstacles in the grid
- **Minimum Path Sum** (Problem 64): Find the path with the minimum sum
- **Dungeon Game** (Problem 174): More complex path finding with additional constraints
