# Spiral Matrix II

## Problem Description

**Problem Link:** [Spiral Matrix II](https://leetcode.com/problems/spiral-matrix-ii/)

Given a positive integer `n`, generate an `n x n` matrix filled with elements from `1` to `n^2` in spiral order.

**Example 1:**
```
Input: n = 3
Output: [[1,2,3],[8,9,4],[7,6,5]]
```

**Example 2:**
```
Input: n = 1
Output: [[1]]
```

**Constraints:**
- `1 <= n <= 20`

## Intuition/Main Idea

We need to fill matrix in spiral order: right → down → left → up, repeating.

**Core Algorithm:**
- Use boundaries: top, bottom, left, right
- Fill right, then down, then left, then up
- Shrink boundaries after each direction
- Continue until all cells filled

**Why boundaries:** Boundaries track the current spiral layer. We fill one layer at a time, moving boundaries inward.

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|------------------------|-----------------------------------|
| Generate spiral matrix | Boundary-based filling - Lines 8-35 |
| Fill right | Right direction - Lines 12-15 |
| Fill down | Down direction - Lines 17-20 |
| Fill left | Left direction - Lines 22-25 |
| Fill up | Up direction - Lines 27-30 |
| Shrink boundaries | Boundary updates - Lines 16, 21, 26, 31 |

## Final Java Code & Learning Pattern (Full Content)

```java
class Solution {
    public int[][] generateMatrix(int n) {
        int[][] matrix = new int[n][n];
        
        int top = 0, bottom = n - 1;
        int left = 0, right = n - 1;
        int num = 1;
        
        while (num <= n * n) {
            // Fill top row: left to right
            for (int i = left; i <= right; i++) {
                matrix[top][i] = num++;
            }
            top++;
            
            // Fill right column: top to bottom
            for (int i = top; i <= bottom; i++) {
                matrix[i][right] = num++;
            }
            right--;
            
            // Fill bottom row: right to left
            for (int i = right; i >= left; i--) {
                matrix[bottom][i] = num++;
            }
            bottom--;
            
            // Fill left column: bottom to top
            for (int i = bottom; i >= top; i--) {
                matrix[i][left] = num++;
            }
            left++;
        }
        
        return matrix;
    }
}
```

## Complexity Analysis

**Time Complexity:** $O(n^2)$ where $n$ is matrix size. We fill each cell once.

**Space Complexity:** $O(n^2)$ for the output matrix.

## Similar Problems

- [Spiral Matrix](https://leetcode.com/problems/spiral-matrix/) - Read spiral order
- [Rotate Image](https://leetcode.com/problems/rotate-image/) - Matrix manipulation
- [Set Matrix Zeroes](https://leetcode.com/problems/set-matrix-zeroes/) - Matrix operations

