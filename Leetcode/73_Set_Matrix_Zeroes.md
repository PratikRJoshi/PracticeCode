### 73. Set Matrix Zeroes
### Problem Link: [Set Matrix Zeroes](https://leetcode.com/problems/set-matrix-zeroes/)
### Intuition
This problem asks us to modify a matrix such that if an element is 0, its entire row and column are set to 0. The key challenge is to do this in-place without using extra space.

The naive approach would be to use a separate matrix to track which cells become 0, but we can optimize this by using the first row and first column of the matrix itself as markers. We first check if the first row and column originally contain any zeros (we'll need to zero them out separately at the end). Then, we use the first row and column to mark which rows and columns need to be zeroed out.

### Java Reference Implementation
```java
class Solution {
    public void setZeroes(int[][] matrix) {
        if(matrix == null || matrix.length == 0 || matrix[0].length == 0){ // [R0] Handle edge cases
            return;
        }

        int rows = matrix.length;
        int cols = matrix[0].length;

        boolean firstRowHasZero = false; // [R1] Flag to track if first row has zero
        boolean firstColHasZero = false; // [R1] Flag to track if first column has zero

        // [R2] Check if first row has zero
        for(int i = 0; i < cols; i++){
            if(matrix[0][i] == 0){
                firstRowHasZero = true;
                break;
            }
        }

        // [R2] Check if first column has zero
        for(int i = 0; i < rows; i++){
            if(matrix[i][0] == 0){
                firstColHasZero = true;
                break;
            }
        }

        // [R3] Use first row and column as markers for zeros
        for(int i = 1; i < rows; i++){
            for(int j = 1; j < cols; j++){
                if(matrix[i][j] == 0){
                    matrix[i][0] = 0; // Mark this row
                    matrix[0][j] = 0; // Mark this column
                }
            }
        }

        // [R4] Set zeros in the matrix based on markers
        for(int i = 1; i < rows; i++){
            for(int j = 1; j < cols; j++){
                if(matrix[i][0] == 0 || matrix[0][j] == 0){
                    matrix[i][j] = 0;
                }
            }
        }

        // [R5] Set zeros in the first row if needed
        if(firstRowHasZero){
            for(int i = 0; i < cols; i++){
                matrix[0][i] = 0;
            }
        }

        // [R5] Set zeros in the first column if needed
        if(firstColHasZero){
            for(int i = 0; i < rows; i++){
                matrix[i][0] = 0;
            }
        }
    }
}
```
### Requirement → Code Mapping
- **R0 (Handle edge cases)**: `if(matrix == null || matrix.length == 0 || matrix[0].length == 0) { return; }` - Return for empty matrices
- **R1 (Track first row/column)**: `boolean firstRowHasZero = false; boolean firstColHasZero = false;` - Special flags for first row and column
- **R2 (Check first row/column)**: Two separate loops to check if first row and column contain zeros
- **R3 (Mark zeros)**: Use first row and column as markers for which rows and columns need to be zeroed
- **R4 (Apply zeros)**: Set elements to zero based on the markers in the first row and column
- **R5 (Handle first row/column)**: Set first row and column to zero if they originally contained zeros

### Alternative Approaches
1. **Using extra space**: O(m+n) space complexity, use two separate arrays to track which rows and columns need to be zeroed.
2. **Using a special marker**: Replace zeros with a special value during the first pass, then replace all special values with zeros.

### Complexity Analysis
- **Time Complexity**: O(m×n) - We need to iterate through the entire matrix multiple times
- **Space Complexity**: O(1) - We use a constant amount of extra space (just two boolean variables)