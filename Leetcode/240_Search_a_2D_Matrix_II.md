### 240. Search a 2D Matrix II
### Problem Link: [Search a 2D Matrix II](https://leetcode.com/problems/search-a-2d-matrix-ii/)

### Intuition/Main Idea
This problem asks us to search for a target value in a 2D matrix with the following properties:
- Integers in each row are sorted in ascending order from left to right
- Integers in each column are sorted in ascending order from top to bottom

The key insight is to start from a corner of the matrix where we can make decisions to eliminate either a row or a column based on the comparison with the target. The optimal starting point is the top-right corner (or alternatively, the bottom-left corner).

Starting from the top-right corner:
- If the current element is greater than the target, we can eliminate the entire column (move left)
- If the current element is less than the target, we can eliminate the entire row (move down)
- If the current element equals the target, we've found it

This approach works because of the sorted property of the matrix. When we move left, we decrease the value; when we move down, we increase the value. This allows us to efficiently narrow down the search space.

### Code Mapping

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Start from top-right corner | `int row = 0, col = matrix[0].length - 1;` |
| Eliminate column if too large | `if (matrix[row][col] > target) { col--; }` |
| Eliminate row if too small | `else if (matrix[row][col] < target) { row++; }` |
| Return true if target found | `else { return true; }` |

### Final Java Code & Learning Pattern

```java
// [Pattern: Search Space Reduction]
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
        
        // Continue until we go out of bounds
        while (row < rows && col >= 0) {
            if (matrix[row][col] > target) {
                // Current value is too large, eliminate this column
                col--;
            } else if (matrix[row][col] < target) {
                // Current value is too small, eliminate this row
                row++;
            } else {
                // Found the target
                return true;
            }
        }
        
        // Target not found
        return false;
    }
}
```

### Alternative Implementation (Binary Search Approach)

```java
// [Pattern: Binary Search in Each Row]
class Solution {
    public boolean searchMatrix(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return false;
        }
        
        int rows = matrix.length;
        
        // Perform binary search on each row
        for (int i = 0; i < rows; i++) {
            if (binarySearch(matrix[i], target)) {
                return true;
            }
        }
        
        return false;
    }
    
    private boolean binarySearch(int[] row, int target) {
        int left = 0;
        int right = row.length - 1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (row[mid] == target) {
                return true;
            } else if (row[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        
        return false;
    }
}
```

### Complexity Analysis
- **Time Complexity**: $O(m + n)$ for the search space reduction approach, where m is the number of rows and n is the number of columns. In the worst case, we traverse at most m rows and n columns.
- **Space Complexity**: $O(1)$ as we only use a constant amount of extra space.

For the binary search approach:
- **Time Complexity**: $O(m \log n)$ where we perform binary search on each of the m rows, and each binary search takes $O(\log n)$ time.
- **Space Complexity**: $O(1)$ as we only use a constant amount of extra space.

### Binary Search Problems Explanation
For the binary search approach:
- **Using < vs <=**: We use `left <= right` in the binary search because we want to include the case where `left == right`, which is a valid index to check.
- **Setting pointers**: We set `left = mid + 1` when the target is greater than the middle element because we know the target cannot be at or before the middle. Similarly, we set `right = mid - 1` when the target is less than the middle element.
- **Return value**: We return `true` as soon as we find the target, and `false` if we've searched all rows without finding it.

### Similar Problems
1. **LeetCode 74: Search a 2D Matrix** - Similar problem but with stronger sorting constraints.
2. **LeetCode 378: Kth Smallest Element in a Sorted Matrix** - Find the kth smallest element in a sorted matrix.
3. **LeetCode 1351: Count Negative Numbers in a Sorted Matrix** - Count negative numbers in a sorted matrix.
4. **LeetCode 1428: Leftmost Column with at Least a One** - Find the leftmost column with a 1 in a binary matrix.
5. **LeetCode 1237: Find Positive Integer Solution for a Given Equation** - Find all pairs that satisfy an equation.
6. **LeetCode 668: Kth Smallest Number in Multiplication Table** - Find the kth smallest number in a multiplication table.
7. **LeetCode 1011: Capacity To Ship Packages Within D Days** - Find minimum capacity to ship packages within D days.
8. **LeetCode 1060: Missing Element in Sorted Array** - Find the kth missing element in a sorted array.
