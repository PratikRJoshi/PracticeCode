### 54. Spiral Matrix
### Problem Link: [Spiral Matrix](https://leetcode.com/problems/spiral-matrix/)
### Intuition
This problem asks us to return all elements of a matrix in spiral order. The key insight is to traverse the matrix layer by layer, going around the perimeter and gradually moving inward. We need to keep track of our boundaries and direction as we traverse.

The approach is to define four boundaries (top, right, bottom, left) and traverse the matrix in a clockwise spiral pattern:
1. Traverse from left to right along the top row
2. Traverse from top to bottom along the rightmost column
3. Traverse from right to left along the bottom row
4. Traverse from bottom to top along the leftmost column

After completing one full spiral, we update our boundaries and continue until we've visited all elements.

### Java Reference Implementation
```java
class Solution {
    public List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> result = new ArrayList<>(); // [R0] Initialize result list
        
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) { // [R1] Handle edge cases
            return result;
        }
        
        int rows = matrix.length;
        int cols = matrix[0].length;
        
        // [R2] Define the four boundaries
        int top = 0;
        int right = cols - 1;
        int bottom = rows - 1;
        int left = 0;
        
        // [R3] Traverse in spiral order until all elements are visited
        while (top <= bottom && left <= right) {
            // [R4] Traverse from left to right along the top row
            for (int j = left; j <= right; j++) {
                result.add(matrix[top][j]);
            }
            top++; // [R5] Move top boundary down
            
            // [R6] Traverse from top to bottom along the rightmost column
            for (int i = top; i <= bottom; i++) {
                result.add(matrix[i][right]);
            }
            right--; // [R7] Move right boundary left
            
            // [R8] Check if there are more rows to traverse
            if (top <= bottom) {
                // [R9] Traverse from right to left along the bottom row
                for (int j = right; j >= left; j--) {
                    result.add(matrix[bottom][j]);
                }
                bottom--; // [R10] Move bottom boundary up
            }
            
            // [R11] Check if there are more columns to traverse
            if (left <= right) {
                // [R12] Traverse from bottom to top along the leftmost column
                for (int i = bottom; i >= top; i--) {
                    result.add(matrix[i][left]);
                }
                left++; // [R13] Move left boundary right
            }
        }
        
        return result; // [R14] Return the spiral order traversal
    }
}
```

### Understanding the Algorithm and Boundary Checks

1. **Boundary Definition and Movement:**
   - We define four boundaries: `top`, `right`, `bottom`, and `left`
   - After traversing each side, we update the corresponding boundary:
     - After traversing the top row, we increment `top`
     - After traversing the rightmost column, we decrement `right`
     - After traversing the bottom row, we decrement `bottom`
     - After traversing the leftmost column, we increment `left`

2. **Conditional Checks (`top <= bottom && left <= right`):**
   - This condition ensures we still have elements to traverse
   - When the boundaries cross, we've processed all elements

3. **Additional Checks for Bottom and Left Traversals:**
   - Before traversing the bottom row, we check `if (top <= bottom)`
   - Before traversing the leftmost column, we check `if (left <= right)`
   - These checks are necessary for non-square matrices to avoid duplicate traversals
   - For example, in a 1×n matrix, after traversing the top row, we shouldn't traverse the bottom row

4. **Edge Cases:**
   - Empty matrix: Return an empty list
   - Single element matrix: Return that element
   - Single row matrix: Traverse only left to right
   - Single column matrix: Traverse only top to bottom

### Requirement → Code Mapping
- **R0 (Initialize result)**: `List<Integer> result = new ArrayList<>();` - Create a list to store the spiral order
- **R1 (Handle edge cases)**: `if (matrix == null || matrix.length == 0 || matrix[0].length == 0) { return result; }` - Return empty list for empty matrix
- **R2 (Define boundaries)**: Set up the four boundaries for spiral traversal
- **R3 (Traverse in spiral)**: `while (top <= bottom && left <= right)` - Continue until all elements are visited
- **R4-R13 (Traverse sides)**: Traverse each side of the current layer and update boundaries
- **R14 (Return result)**: `return result;` - Return the spiral order traversal

### Complexity Analysis
- **Time Complexity**: O(m×n)
  - We visit each element in the matrix exactly once
  - For an m×n matrix, the time complexity is O(m×n)

- **Space Complexity**: O(m×n)
  - We store all elements in the result list
  - For an m×n matrix, the space complexity is O(m×n)
  - If we don't count the output as part of the space complexity, then it's O(1)

### Related Problems
- **Spiral Matrix II** (Problem 59): Generate a matrix filled with elements from 1 to n² in spiral order
- **Rotate Image** (Problem 48): Rotate a matrix 90 degrees clockwise
- **Diagonal Traverse** (Problem 498): Traverse a matrix diagonally
