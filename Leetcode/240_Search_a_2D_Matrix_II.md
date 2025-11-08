### 240. Search a 2D Matrix II
### Problem Link: [Search a 2D Matrix II](https://leetcode.com/problems/search-a-2d-matrix-ii/)
### Intuition
This problem presents a 2D matrix where:
1. Integers in each row are sorted from left to right
2. Integers in each column are sorted from top to bottom

Unlike LeetCode 74, there's no guarantee that the first element of a row is greater than the last element of the previous row. This means we cannot treat the matrix as a single sorted array.

The key insight is to start from a strategic position (top-right or bottom-left corner) where we can eliminate either a row or a column with each comparison, effectively reducing the search space.

### Java Reference Implementation (Search Space Reduction)
```java
class Solution {
    public boolean searchMatrix(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return false;
        }
        
        int rows = matrix.length;
        int cols = matrix[0].length;
        
        // Start from the top-right corner
        int row = 0;
        int col = cols - 1;
        
        while (row < rows && col >= 0) {
            if (matrix[row][col] == target) {
                return true;
            } else if (matrix[row][col] > target) {
                // The current column has values greater than target, move left
                col--;
            } else {
                // The current row has values smaller than target, move down
                row++;
            }
        }
        
        return false;
    }
}
```

### Alternative Implementation (Starting from Bottom-Left)
```java
class Solution {
    public boolean searchMatrix(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return false;
        }
        
        int rows = matrix.length;
        int cols = matrix[0].length;
        
        // Start from the bottom-left corner
        int row = rows - 1;
        int col = 0;
        
        while (row >= 0 && col < cols) {
            if (matrix[row][col] == target) {
                return true;
            } else if (matrix[row][col] > target) {
                // The current row has values greater than target, move up
                row--;
            } else {
                // The current column has values smaller than target, move right
                col++;
            }
        }
        
        return false;
    }
}
```

### Requirement → Code Mapping
- **R0 (Handle edge cases)**: `if (matrix == null || matrix.length == 0 || matrix[0].length == 0) { return false; }`
- **R1 (Strategic starting point)**: Start from top-right or bottom-left corner where we can make decisions to eliminate rows or columns
- **R2 (Eliminate search space)**: If current value > target, eliminate column (move left); if current value < target, eliminate row (move down)
- **R3 (Check for target)**: Return true immediately when target is found

### Complexity
- **Time Complexity**: O(m + n) - In the worst case, we might need to traverse m rows and n columns
- **Space Complexity**: O(1) - Constant extra space

### Comparison with LeetCode 74 (Search a 2D Matrix)

| Aspect | LeetCode 74 | LeetCode 240 |
|--------|------------|-------------|
| **Matrix Properties** | 1. Rows sorted left to right<br>2. First element of each row > last element of previous row | 1. Rows sorted left to right<br>2. Columns sorted top to bottom |
| **Can treat as 1D array?** | Yes | No |
| **Optimal Algorithm** | Binary Search | Search Space Reduction |
| **Time Complexity** | O(log(m*n)) | O(m + n) |
| **Starting Position** | N/A (binary search) | Top-right or bottom-left corner |

**Key Insight**: The additional constraint in LeetCode 74 (each row's first element > previous row's last element) allows us to treat the matrix as a single sorted array, enabling binary search. Without this constraint in LeetCode 240, we need a different approach that takes advantage of the 2D sorting properties.

**Algorithm Choice**: For LeetCode 74, binary search is optimal. For LeetCode 240, the search space reduction technique starting from a corner is optimal. Using the LeetCode 240 approach for LeetCode 74 would work but would be less efficient (O(m+n) vs O(log(m*n))).
