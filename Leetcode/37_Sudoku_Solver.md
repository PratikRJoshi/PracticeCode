# Sudoku Solver

## Problem Description

**Problem Link:** [Sudoku Solver](https://leetcode.com/problems/sudoku-solver/)

Write a program to solve a Sudoku puzzle by filling the empty cells.

A sudoku solution must satisfy **all of the following rules**:

1. Each of the digits `1-9` must occur exactly once in each row.
2. Each of the digits `1-9` must occur exactly once in each column.
3. Each of the digits `1-9` must occur exactly once in each of the 9 `3x3` sub-boxes of the grid.

The `'.'` character indicates empty cells.

**Example 1:**

```
Input: board = [["5","3",".",".","7",".",".",".","."],["6",".",".","1","9","5",".",".","."],[".","9","8",".",".",".",".","6","."],["8",".",".",".","6",".",".",".","3"],["4",".",".","8",".","3",".",".","1"],["7",".",".",".","2",".",".",".","6"],[".","6",".",".",".",".","2","8","."],[".",".",".","4","1","9",".",".","5"],[".",".",".",".","8",".",".","7","9"]]
Output: [["5","3","4","6","7","8","9","1","2"],["6","7","2","1","9","5","3","4","8"],["1","9","8","3","4","2","5","6","7"],["8","5","9","7","6","1","4","2","3"],["4","2","6","8","5","3","7","9","1"],["7","1","3","9","2","4","8","5","6"],["9","6","1","5","3","7","2","8","4"],["2","8","7","4","1","9","6","3","5"],["3","4","5","2","8","6","1","7","9"]]
```

**Constraints:**
- `board.length == 9`
- `board[i].length == 9`
- `board[i][j]` is a digit or `'.'`.
- It is **guaranteed** that the input board has only one solution.

## Intuition/Main Idea

This is a **backtracking** problem. We need to fill empty cells with valid digits.

**Core Algorithm:**
1. Find the next empty cell.
2. Try digits 1-9.
3. Check if digit is valid (not in row, column, or 3x3 box).
4. If valid, place digit and recurse.
5. If recursion fails, backtrack and try next digit.

**Why backtracking works:** We systematically try all valid digits for each empty cell. When we reach a dead end, we backtrack and try the next possibility.

## Code Mapping

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Find empty cell | Find empty - Lines 6-12 |
| Try each digit | Digit loop - Line 15 |
| Check validity | IsValid method - Lines 20-35 |
| Place digit | Place operation - Line 17 |
| Recurse | Recursive call - Line 18 |
| Backtrack | Remove operation - Line 19 |
| Return result | Return statement - Line 21 |

## Final Java Code & Learning Pattern

```java
class Solution {
    public void solveSudoku(char[][] board) {
        solve(board);
    }
    
    private boolean solve(char[][] board) {
        // Find next empty cell
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] == '.') {
                    // Try each digit 1-9
                    for (char digit = '1'; digit <= '9'; digit++) {
                        if (isValid(board, i, j, digit)) {
                            board[i][j] = digit;
                            if (solve(board)) {
                                return true;
                            }
                            board[i][j] = '.'; // Backtrack
                        }
                    }
                    return false; // No valid digit found
                }
            }
        }
        return true; // All cells filled
    }
    
    private boolean isValid(char[][] board, int row, int col, char digit) {
        // Check row
        for (int j = 0; j < 9; j++) {
            if (board[row][j] == digit) {
                return false;
            }
        }
        
        // Check column
        for (int i = 0; i < 9; i++) {
            if (board[i][col] == digit) {
                return false;
            }
        }
        
        // Check 3x3 box
        int boxRow = (row / 3) * 3;
        int boxCol = (col / 3) * 3;
        for (int i = boxRow; i < boxRow + 3; i++) {
            for (int j = boxCol; j < boxCol + 3; j++) {
                if (board[i][j] == digit) {
                    return false;
                }
            }
        }
        
        return true;
    }
}
```

**Explanation of Key Code Sections:**

1. **Find Empty Cell (Lines 6-12):** Find the next empty cell (marked with '.').

2. **Try Digits (Lines 15-19):** For each digit 1-9:
   - **Check Validity (Line 16):** Ensure digit is valid.
   - **Place (Line 17):** Place digit in cell.
   - **Recurse (Line 18):** Try to solve rest of puzzle.
   - **Backtrack (Line 19):** If recursion fails, remove digit.

3. **IsValid (Lines 20-35):** Check if digit is valid:
   - Not in same row.
   - Not in same column.
   - Not in same 3x3 box.

**Why backtracking:**
- We need to explore all possibilities systematically.
- When a path fails, we backtrack and try the next digit.
- This ensures we find the solution if it exists.

**Example walkthrough:**
- Find first empty cell at (0,2).
- Try '1': invalid (in row 0).
- Try '2': invalid (in column 2).
- Try '4': valid → place → recurse.
- Continue until puzzle solved or backtrack needed.

## Complexity Analysis

- **Time Complexity:** $O(9^m)$ where $m$ is the number of empty cells. In worst case, we try 9 digits for each empty cell.

- **Space Complexity:** $O(1)$ extra space (recursion stack is $O(m)$).

## Similar Problems

Problems that can be solved using similar backtracking patterns:

1. **37. Sudoku Solver** (this problem) - Backtracking puzzle solving
2. **51. N-Queens** - Backtracking placement
3. **52. N-Queens II** - Count solutions
4. **79. Word Search** - Backtracking search
5. **212. Word Search II** - Multiple word search
6. **980. Unique Paths III** - Backtracking paths
7. **1219. Path with Maximum Gold** - Backtracking with optimization
8. **526. Beautiful Arrangement** - Backtracking placement

