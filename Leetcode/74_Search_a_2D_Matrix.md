### 74. Search a 2D Matrix
### Problem Link: [Search a 2D Matrix](https://leetcode.com/problems/search-a-2d-matrix/)
### Intuition
This problem presents a sorted 2D matrix where:
1. Integers in each row are sorted from left to right
2. The first integer of each row is greater than the last integer of the previous row

With these properties, we can treat the entire 2D matrix as a sorted 1D array and perform a binary search. Alternatively, we can first identify the row that might contain the target (using the first element of each row), and then perform a binary search within that row.

### Java Reference Implementation (Treating as 1D array)
```java
class Solution {
    public boolean searchMatrix(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return false;
        }
        
        int m = matrix.length;
        int n = matrix[0].length;
        
        // Treat the 2D matrix as a 1D sorted array
        int left = 0;
        int right = m * n - 1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            // Convert 1D index to 2D coordinates
            int row = mid / n;
            int col = mid % n;
            
            if (matrix[row][col] == target) {
                return true;
            } else if (matrix[row][col] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        
        return false;
    }
}
```

### Alternative Implementation (Two Binary Searches)
```java
class Solution {
    public boolean searchMatrix(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return false;
        }
        
        int m = matrix.length;
        int n = matrix[0].length;
        
        // First binary search to find the row
        int top = 0;
        int bottom = m - 1;
        int row = -1;
        
        while (top <= bottom) {
            int mid = top + (bottom - top) / 2;
            
            if (matrix[mid][0] <= target && target <= matrix[mid][n - 1]) {
                row = mid;
                break;
            } else if (matrix[mid][0] > target) {
                bottom = mid - 1;
            } else {
                top = mid + 1;
            }
        }
        
        // If no suitable row found
        if (row == -1) {
            return false;
        }
        
        // Second binary search within the row
        int left = 0;
        int right = n - 1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (matrix[row][mid] == target) {
                return true;
            } else if (matrix[row][mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        
        return false;
    }
}
```

### Requirement → Code Mapping
- **R0 (Handle edge cases)**: `if (matrix == null || matrix.length == 0 || matrix[0].length == 0) { return false; }`
- **R1 (Treat as 1D array)**: Convert 2D coordinates to 1D index using `row = mid / n` and `col = mid % n`
- **R2 (Find target row)**: Use binary search to find the row that might contain the target
- **R3 (Search within row)**: Use binary search within the identified row to find the target

### Complexity
- **Time Complexity**: O(log(m*n)) - Binary search on a virtual array of size m*n
- **Space Complexity**: O(1) - Constant extra space
