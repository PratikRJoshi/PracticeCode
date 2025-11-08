### 36. Valid Sudoku
### Problem Link: [Valid Sudoku](https://leetcode.com/problems/valid-sudoku/)
### Intuition
This problem asks us to determine if a partially filled 9x9 Sudoku board is valid. A valid Sudoku board must satisfy three conditions:
1. Each row must contain unique digits (1-9)
2. Each column must contain unique digits (1-9)
3. Each of the nine 3x3 sub-boxes must contain unique digits (1-9)

The key insight is to check all three conditions in a single pass through the board. We can use HashSets to keep track of the digits we've seen in each row, column, and sub-box.

### Java Reference Implementation
```java
class Solution {
    public boolean isValidSudoku(char[][] board) {
        // [R0] Create sets to track digits in each row, column, and sub-box
        Set<Character>[] rows = new HashSet[9];
        Set<Character>[] cols = new HashSet[9];
        Set<Character>[] boxes = new HashSet[9];
        
        // [R1] Initialize the sets
        for (int i = 0; i < 9; i++) {
            rows[i] = new HashSet<>();
            cols[i] = new HashSet<>();
            boxes[i] = new HashSet<>();
        }
        
        // [R2] Check all cells in the board
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                char c = board[i][j];
                
                // [R3] Skip empty cells
                if (c == '.') {
                    continue;
                }
                
                // [R4] Calculate the box index (0-8)
                int boxIndex = (i / 3) * 3 + j / 3;
                
                // [R5] Check if the digit is already in the row, column, or box
                if (rows[i].contains(c) || cols[j].contains(c) || boxes[boxIndex].contains(c)) {
                    return false; // [R6] Invalid Sudoku
                }
                
                // [R7] Add the digit to the sets
                rows[i].add(c);
                cols[j].add(c);
                boxes[boxIndex].add(c);
            }
        }
        
        return true; // [R8] Valid Sudoku
    }
}
```

### Alternative Implementation (Using Bit Manipulation)
```java
class Solution {
    public boolean isValidSudoku(char[][] board) {
        // Use bit manipulation to track digits (1-9) in each row, column, and box
        int[] rows = new int[9];
        int[] cols = new int[9];
        int[] boxes = new int[9];
        
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                char c = board[i][j];
                
                if (c == '.') {
                    continue;
                }
                
                int digit = c - '0';
                int bitMask = 1 << digit;
                int boxIndex = (i / 3) * 3 + j / 3;
                
                // Check if the digit is already set in the row, column, or box
                if ((rows[i] & bitMask) > 0 || (cols[j] & bitMask) > 0 || (boxes[boxIndex] & bitMask) > 0) {
                    return false;
                }
                
                // Set the bit for this digit
                rows[i] |= bitMask;
                cols[j] |= bitMask;
                boxes[boxIndex] |= bitMask;
            }
        }
        
        return true;
    }
}
```

### Understanding the Algorithm and Data Structure

1. **Tracking Seen Digits:**
   - We use three arrays of sets to track the digits we've seen in each row, column, and sub-box
   - For each cell, we check if the digit already exists in its row, column, or sub-box
   - If it does, the Sudoku is invalid
   - If not, we add the digit to the appropriate sets

2. **Sub-box Index Calculation:**
   - We need to map each cell (i, j) to one of the nine 3x3 sub-boxes
   - The formula `(i / 3) * 3 + j / 3` gives us the box index (0-8)
   - This maps cells to boxes as follows:
     - Box 0: (0,0) to (2,2)
     - Box 1: (0,3) to (2,5)
     - Box 2: (0,6) to (2,8)
     - Box 3: (3,0) to (5,2)
     - And so on...

3. **Bit Manipulation Approach:**
   - Instead of using sets, we can use integers to represent the presence of digits
   - Each bit position corresponds to a digit (1-9)
   - We use bitwise operations to check and update the presence of digits
   - This is more space-efficient but slightly less readable

4. **Single Pass Efficiency:**
   - We check all three conditions in a single pass through the board
   - This gives us O(1) time complexity for each cell check
   - Overall, we achieve O(n²) time complexity where n is the board size (9 in this case)

### Requirement → Code Mapping
- **R0 (Create tracking sets)**: `Set<Character>[] rows/cols/boxes` - Create sets to track digits
- **R1 (Initialize sets)**: Initialize all sets as empty HashSets
- **R2 (Check all cells)**: Iterate through each cell in the board
- **R3 (Skip empty cells)**: `if (c == '.') { continue; }` - Skip cells that don't have a digit
- **R4 (Calculate box index)**: `(i / 3) * 3 + j / 3` - Map cell to its 3x3 sub-box
- **R5 (Check for duplicates)**: Check if the digit already exists in its row, column, or box
- **R6 (Invalid case)**: Return false if a duplicate is found
- **R7 (Update sets)**: Add the digit to the appropriate sets
- **R8 (Valid case)**: Return true if no duplicates are found

### Complexity Analysis
- **Time Complexity**: O(1)
  - The board size is fixed at 9x9
  - We visit each cell exactly once
  - Each check and update operation takes O(1) time
  - Overall: O(9²) = O(81) = O(1)

- **Space Complexity**: O(1)
  - We use a fixed amount of extra space regardless of input size
  - HashSet approach: 9 sets for rows + 9 sets for columns + 9 sets for boxes = 27 sets
  - Bit manipulation approach: 9 integers for rows + 9 for columns + 9 for boxes = 27 integers

### Related Problems
- **Sudoku Solver** (Problem 37): Solve a partially filled Sudoku board
- **N-Queens** (Problem 51): Another grid-based validation problem
- **Valid Tic-Tac-Toe State** (Problem 794): Validate the state of a game board
