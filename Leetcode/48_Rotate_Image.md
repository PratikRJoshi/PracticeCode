### 48. Rotate Image
### Problem Link: [Rotate Image](https://leetcode.com/problems/rotate-image/)
### Intuition
This problem asks us to rotate an n×n matrix 90 degrees clockwise. The key constraint is that we need to do this in-place, without allocating another 2D matrix.

The key insight is that a 90-degree clockwise rotation can be achieved through two simpler operations:
1. Transpose the matrix (swap rows with columns)
2. Reverse each row

This approach is elegant and efficient, as it allows us to perform the rotation in-place with O(1) extra space.

### Java Reference Implementation
```java
class Solution {
    public void rotate(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) { // [R0] Handle edge cases
            return;
        }
        
        int n = matrix.length;
        
        // [R1] Step 1: Transpose the matrix (swap rows with columns)
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) { // [R2] Note: j starts from i to avoid double swapping
                // [R3] Swap matrix[i][j] with matrix[j][i]
                int temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }
        
        // [R4] Step 2: Reverse each row
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n / 2; j++) { // [R5] Only need to iterate through half the row
                // [R6] Swap matrix[i][j] with matrix[i][n-1-j]
                int temp = matrix[i][j];
                matrix[i][j] = matrix[i][n - 1 - j];
                matrix[i][n - 1 - j] = temp;
            }
        }
    }
}
```

### Alternative Implementation (Layer by Layer Rotation)
```java
class Solution {
    public void rotate(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return;
        }
        
        int n = matrix.length;
        
        // Rotate the matrix layer by layer
        for (int layer = 0; layer < n / 2; layer++) {
            int first = layer;
            int last = n - 1 - layer;
            
            for (int i = first; i < last; i++) {
                int offset = i - first;
                
                // Save top
                int top = matrix[first][i];
                
                // Left -> Top
                matrix[first][i] = matrix[last - offset][first];
                
                // Bottom -> Left
                matrix[last - offset][first] = matrix[last][last - offset];
                
                // Right -> Bottom
                matrix[last][last - offset] = matrix[i][last];
                
                // Top -> Right
                matrix[i][last] = top;
            }
        }
    }
}
```

### Understanding the Algorithm and Boundary Checks

1. **Transpose Operation:**
   - For a matrix transpose, we swap `matrix[i][j]` with `matrix[j][i]` for all `i` and `j`
   - To avoid swapping twice, we only perform the swap when `j >= i`
   - This effectively flips the matrix along its main diagonal

2. **Row Reversal:**
   - After transposing, we reverse each row by swapping `matrix[i][j]` with `matrix[i][n-1-j]`
   - We only need to iterate through half of each row (`j < n/2`) to avoid double swapping

3. **Mathematical Insight:**
   - If we denote the original position as `(i, j)` and the position after 90-degree rotation as `(i', j')`:
     - The relationship is: `i' = j` and `j' = n - 1 - i`
   - Transpose: `(i, j)` → `(j, i)`
   - Reverse rows: `(j, i)` → `(j, n - 1 - i)`
   - Combined: `(i, j)` → `(j, n - 1 - i)`, which is exactly the 90-degree rotation formula

4. **Layer by Layer Approach (Alternative):**
   - We can also think of the matrix as concentric layers or rings
   - For each layer, we rotate the four sides in a single pass
   - This approach directly applies the rotation formula but is more complex to implement

### Requirement → Code Mapping
- **R0 (Handle edge cases)**: `if (matrix == null || matrix.length == 0 || matrix[0].length == 0) { return; }` - Return for empty matrices
- **R1 (Transpose matrix)**: First step of the rotation process
- **R2 (Avoid double swapping)**: `for (int j = i; j < n; j++)` - Start j from i to avoid swapping elements twice
- **R3 (Swap elements)**: Swap matrix[i][j] with matrix[j][i] to transpose
- **R4 (Reverse rows)**: Second step of the rotation process
- **R5 (Half row iteration)**: `for (int j = 0; j < n / 2; j++)` - Only need to iterate through half the row
- **R6 (Swap elements)**: Swap matrix[i][j] with matrix[i][n-1-j] to reverse the row

### Complexity Analysis
- **Time Complexity**: O(n²)
  - Transpose operation: O(n²) - We visit each element in the matrix
  - Row reversal: O(n²) - We visit each element again
  - Overall: O(n²)

- **Space Complexity**: O(1)
  - We perform the rotation in-place without using additional data structures
  - We only use a constant amount of extra space for temporary variables

### Related Problems
- **Spiral Matrix** (Problem 54): Return all elements of a matrix in spiral order
- **Rotate Array** (Problem 189): Rotate an array to the right by k steps
- **Transpose Matrix** (Problem 867): Return the transpose of a matrix
