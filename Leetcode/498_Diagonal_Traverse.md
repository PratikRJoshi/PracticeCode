### 498. Diagonal Traverse
### Problem Link: [Diagonal Traverse](https://leetcode.com/problems/diagonal-traverse/)
### Intuition
This problem asks us to traverse a matrix in a zigzag diagonal pattern. The key insight is that for each diagonal, the sum of row and column indices (i+j) is constant. For example, the first diagonal has i+j=0, the second has i+j=1, and so on. Additionally, the direction of traversal alternates: even-sum diagonals are traversed upward (decreasing row, increasing column), and odd-sum diagonals are traversed downward (increasing row, decreasing column).

### Java Reference Implementation
```java
class Solution {
    public int[] findDiagonalOrder(int[][] mat) {
        if (mat == null || mat.length == 0) {
            return new int[0];
        }
        
        int m = mat.length;
        int n = mat[0].length;
        int[] result = new int[m * n];
        int row = 0, col = 0;
        int direction = 1; // 1 for up, -1 for down
        
        for (int i = 0; i < m * n; i++) {
            result[i] = mat[row][col];
            
            // Move to the next element based on the current direction
            if (direction == 1) {
                // Moving upward (decreasing row, increasing column)
                if (col == n - 1) {
                    // Reached the rightmost column, go down
                    row++;
                    direction = -1;
                } else if (row == 0) {
                    // Reached the topmost row, go right
                    col++;
                    direction = -1;
                } else {
                    // Continue moving upward
                    row--;
                    col++;
                }
            } else {
                // Moving downward (increasing row, decreasing column)
                if (row == m - 1) {
                    // Reached the bottommost row, go right
                    col++;
                    direction = 1;
                } else if (col == 0) {
                    // Reached the leftmost column, go down
                    row++;
                    direction = 1;
                } else {
                    // Continue moving downward
                    row++;
                    col--;
                }
            }
        }
        
        return result;
    }
}
```

### Alternative Implementation (Using Sum of Indices)
```java
class Solution {
    public int[] findDiagonalOrder(int[][] mat) {
        if (mat == null || mat.length == 0) {
            return new int[0];
        }
        
        int m = mat.length;
        int n = mat[0].length;
        int[] result = new int[m * n];
        int index = 0;
        
        // For each diagonal (sum of indices from 0 to m+n-2)
        for (int sum = 0; sum < m + n - 1; sum++) {
            // For even sum diagonals, traverse upward
            if (sum % 2 == 0) {
                // Start from the lowest possible row and highest possible column
                int row = Math.min(sum, m - 1);
                int col = sum - row;
                
                // Traverse upward until we reach the top or right edge
                while (row >= 0 && col < n) {
                    result[index++] = mat[row--][col++];
                }
            } else {
                // For odd sum diagonals, traverse downward
                // Start from the lowest possible column and highest possible row
                int col = Math.min(sum, n - 1);
                int row = sum - col;
                
                // Traverse downward until we reach the bottom or left edge
                while (col >= 0 && row < m) {
                    result[index++] = mat[row++][col--];
                }
            }
        }
        
        return result;
    }
}
```

### Requirement → Code Mapping
- **R0 (Handle edge cases)**: `if (mat == null || mat.length == 0) { return new int[0]; }` - Return empty array for edge cases
- **R1 (Traverse diagonals)**: Use the sum of indices (i+j) to identify diagonals
- **R2 (Alternate direction)**: Even-sum diagonals go upward, odd-sum diagonals go downward
- **R3 (Handle boundaries)**: Change direction when reaching the edges of the matrix
- **R4 (Fill result array)**: Store elements in the result array in the order of traversal

### Complexity Analysis
- **Time Complexity**: O(m*n) - We visit each element in the matrix exactly once
- **Space Complexity**: O(m*n) - We store all elements in the result array
