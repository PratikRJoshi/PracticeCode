### 74. Search a 2D Matrix
### Problem Link: [Search a 2D Matrix](https://leetcode.com/problems/search-a-2d-matrix/)

### Problem Description
You are given an `m x n` integer matrix `matrix` with the following two properties:
- Each row is sorted in non-decreasing order.
- The first integer of each row is greater than the last integer of the previous row.

Given an integer `target`, return `true` if `target` is in `matrix` or `false` otherwise.

You must write a solution in `O(log(m * n))` time complexity.

**Example 1:**
```
Input: matrix = [[1,3,5,7],[10,11,16,20],[23,30,34,60]], target = 3
Output: true
```

**Example 2:**
```
Input: matrix = [[1,3,5,7],[10,11,16,20],[23,30,34,60]], target = 13
Output: false
```

**Constraints:**
- `m == matrix.length`
- `n == matrix[i].length`
- `1 <= m, n <= 100`
- `-10^4 <= matrix[i][j], target <= 10^4`

---

### Intuition / Main Idea

#### Why the Intuition Works

The key insight is that this 2D matrix, when "flattened" row by row, forms a **single sorted array**. This is because:
1. Each row is sorted left to right.
2. The first element of each row is greater than the last element of the previous row.

So the matrix:
```
[1,  3,  5,  7 ]
[10, 11, 16, 20]
[23, 30, 34, 60]
```

Is logically equivalent to the sorted array:
```
[1, 3, 5, 7, 10, 11, 16, 20, 23, 30, 34, 60]
```

Since we have a sorted sequence, we can apply **binary search** in O(log(m*n)) time.

#### How to Derive It Step by Step

**Step 1: Recognize the sorted property**
- Read the problem constraints carefully. The two properties guarantee a globally sorted order.

**Step 2: Map 1D index to 2D coordinates**
- If we treat the matrix as a 1D array of length `m * n`, we can use a single index `mid` from `0` to `m*n - 1`.
- To convert `mid` to 2D coordinates:
  - `row = mid / numCols`
  - `col = mid % numCols`

**Step 3: Apply standard binary search**
- Initialize `left = 0`, `right = m * n - 1`
- While `left <= right`:
  - Compute `mid`, convert to 2D, get `matrix[row][col]`
  - If equal to target → return `true`
  - If less than target → search right half (`left = mid + 1`)
  - If greater than target → search left half (`right = mid - 1`)

**Step 4: Return false if not found**

---

### Binary Search Decision Points

#### 1. How to decide whether to use `<` or `<=` in the main loop condition?

Use `left <= right` because:
- We are searching for a **specific target value**.
- When `left == right`, there's still one element to check.
- Using `<` would skip checking the last remaining element.

#### 2. How to decide if pointers should be set to `mid + 1`, `mid - 1`, or `mid`?

- **`left = mid + 1`**: When `matrix[row][col] < target`, the current `mid` is too small. We've already checked it, so exclude it.
- **`right = mid - 1`**: When `matrix[row][col] > target`, the current `mid` is too large. We've already checked it, so exclude it.
- We never set pointers to `mid` because we always check `mid` before adjusting, so there's no need to re-check it.

#### 3. How to decide what the return value should be?

- Return `true` immediately when `matrix[row][col] == target`.
- Return `false` after the loop ends (target not found).

---

### Requirement → Code Mapping

| Problem Requirement | Java Code Section |
|---------------------|-------------------|
| Handle edge cases (null/empty matrix) | `if (matrix == null \|\| matrix.length == 0 \|\| matrix[0].length == 0)` |
| Treat matrix as 1D sorted array | `left = 0`, `right = numRows * numCols - 1` |
| Convert 1D index to 2D coordinates | `row = mid / numCols`, `col = mid % numCols` |
| Standard binary search comparison | `if (matrix[row][col] == target)` |
| Search right half if too small | `left = mid + 1` |
| Search left half if too large | `right = mid - 1` |
| Return false if not found | `return false` at end |

---

### Java Reference Implementation

```java
class Solution {
    public boolean searchMatrix(int[][] matrix, int target) {
        // [R0] Handle edge cases: null or empty matrix
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return false;
        }
        
        int numRows = matrix.length;
        int numCols = matrix[0].length;
        
        // [R1] Treat the 2D matrix as a 1D sorted array
        // The "flattened" array has indices from 0 to (numRows * numCols - 1)
        int left = 0;
        int right = numRows * numCols - 1;
        
        // [R2] Standard binary search with left <= right
        // We use <= because when left == right, there's still one element to check
        while (left <= right) {
            // [R3] Calculate mid index (avoids integer overflow)
            int mid = left + (right - left) / 2;
            
            // [R4] Convert 1D index to 2D coordinates
            // row = mid / numCols (which row does this index fall into?)
            // col = mid % numCols (which column within that row?)
            int row = mid / numCols;
            int col = mid % numCols;
            
            int currentValue = matrix[row][col];
            
            // [R5] Standard binary search comparisons
            if (currentValue == target) {
                // Found the target
                return true;
            } else if (currentValue < target) {
                // Current value is too small, search in the right half
                // We use mid + 1 because we've already checked mid
                left = mid + 1;
            } else {
                // Current value is too large, search in the left half
                // We use mid - 1 because we've already checked mid
                right = mid - 1;
            }
        }
        
        // [R6] Target not found in the matrix
        return false;
    }
}
```

---

### Complexity Analysis

- **Time Complexity**: O(log(m × n))
  - We perform binary search over `m × n` elements.
  - Each iteration halves the search space.

- **Space Complexity**: O(1)
  - We only use a constant number of variables (`left`, `right`, `mid`, `row`, `col`).

---

### Alternative Approach: Two Binary Searches

Instead of treating the matrix as a 1D array, you can:
1. Binary search to find the correct row (where `row[0] <= target <= row[n-1]`)
2. Binary search within that row to find the target

This is also O(log m + log n) = O(log(m × n)), but slightly more complex to implement.

---

### Related Problems

- **[240. Search a 2D Matrix II](https://leetcode.com/problems/search-a-2d-matrix-ii/)**: Similar but rows and columns are sorted independently (not globally sorted). Requires a different approach (start from top-right or bottom-left corner).
- **[704. Binary Search](https://leetcode.com/problems/binary-search/)**: Basic binary search on a 1D array.
- **[33. Search in Rotated Sorted Array](https://leetcode.com/problems/search-in-rotated-sorted-array/)**: Binary search with a twist (rotated array).

---

### Why Problem 240 is Different

In **Search a 2D Matrix II** (Problem 240):
- Each row is sorted left to right.
- Each column is sorted top to bottom.
- **But** the first element of a row is NOT necessarily greater than the last element of the previous row.

Example:
```
[1,  4,  7, 11]
[2,  5,  8, 12]
[3,  6,  9, 16]
```

Here, `2 > 1` but `2` is in the next row. The matrix cannot be "flattened" into a single sorted array. This requires a different O(m + n) approach starting from a corner.
