### 51. N-Queens
### Problem Link: [N-Queens](https://leetcode.com/problems/n-queens/)
### Intuition
The N-Queens puzzle is the problem of placing N queens on an N×N chessboard such that no two queens threaten each other. In chess, a queen can attack horizontally, vertically, and diagonally. The challenge is to find all possible configurations where no two queens can attack each other.

This is a classic backtracking problem. The key insight is to place queens column by column, and for each column, try placing a queen in each row. If a placement is valid (doesn't conflict with previously placed queens), we proceed to the next column. If we reach a point where we can't place a queen in any row of the current column, we backtrack and try a different position for the previous queen.

### Java Reference Implementation
```java
import java.util.ArrayList;
import java.util.List;

class Solution {
    public List<List<String>> solveNQueens(int n) {
        List<List<String>> result = new ArrayList<>();
        char[][] board = new char[n][n];
        
        // Initialize the board with empty cells
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                board[i][j] = '.';
            }
        }
        
        backtrack(board, 0, result);
        return result;
    }
    
    private void backtrack(char[][] board, int col, List<List<String>> result) {
        // Base case: If all queens are placed, add the solution to the result
        if (col == board.length) {
            result.add(constructSolution(board));
            return;
        }
        
        // Try placing a queen in each row of the current column
        for (int row = 0; row < board.length; row++) {
            if (isSafe(board, row, col)) {
                // Place the queen
                board[row][col] = 'Q';
                
                // Recursively place queens in the next column
                backtrack(board, col + 1, result);
                
                // Backtrack: remove the queen to try other positions
                board[row][col] = '.';
            }
        }
    }
    
    private boolean isSafe(char[][] board, int row, int col) {
        int n = board.length;
        
        // Check if there's a queen in the same row to the left
        for (int i = 0; i < col; i++) {
            if (board[row][i] == 'Q') {
                return false;
            }
        }
        
        // Check upper diagonal on the left
        for (int i = row, j = col; i >= 0 && j >= 0; i--, j--) {
            if (board[i][j] == 'Q') {
                return false;
            }
        }
        
        // Check lower diagonal on the left
        for (int i = row, j = col; i < n && j >= 0; i++, j--) {
            if (board[i][j] == 'Q') {
                return false;
            }
        }
        
        return true;
    }
    
    private List<String> constructSolution(char[][] board) {
        List<String> solution = new ArrayList<>();
        for (char[] row : board) {
            solution.add(new String(row));
        }
        return solution;
    }
}
```

### Alternative Implementation (Using Sets for Conflict Checking)
```java
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class Solution {
    public List<List<String>> solveNQueens(int n) {
        List<List<String>> result = new ArrayList<>();
        char[][] board = new char[n][n];
        
        // Initialize the board with empty cells
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                board[i][j] = '.';
            }
        }
        
        // Sets to keep track of occupied columns and diagonals
        Set<Integer> cols = new HashSet<>();
        Set<Integer> posDiag = new HashSet<>(); // row + col
        Set<Integer> negDiag = new HashSet<>(); // row - col
        
        backtrack(board, 0, cols, posDiag, negDiag, result);
        return result;
    }
    
    private void backtrack(char[][] board, int row, Set<Integer> cols, Set<Integer> posDiag, Set<Integer> negDiag, List<List<String>> result) {
        int n = board.length;
        
        // Base case: If all queens are placed, add the solution to the result
        if (row == n) {
            result.add(constructSolution(board));
            return;
        }
        
        // Try placing a queen in each column of the current row
        for (int col = 0; col < n; col++) {
            int posD = row + col;
            int negD = row - col;
            
            // Check if the current position conflicts with any queen
            if (cols.contains(col) || posDiag.contains(posD) || negDiag.contains(negD)) {
                continue;
            }
            
            // Place the queen
            board[row][col] = 'Q';
            cols.add(col);
            posDiag.add(posD);
            negDiag.add(negD);
            
            // Recursively place queens in the next row
            backtrack(board, row + 1, cols, posDiag, negDiag, result);
            
            // Backtrack: remove the queen to try other positions
            board[row][col] = '.';
            cols.remove(col);
            posDiag.remove(posD);
            negDiag.remove(negD);
        }
    }
    
    private List<String> constructSolution(char[][] board) {
        List<String> solution = new ArrayList<>();
        for (char[] row : board) {
            solution.add(new String(row));
        }
        return solution;
    }
}
```

### Requirement → Code Mapping
- **R0 (Board representation)**: Use a 2D array to represent the chessboard
- **R1 (Backtracking)**: Use recursion to try different queen placements and backtrack when needed
- **R2 (Safety check)**: Check if a position is safe by verifying no queens are in the same row, column, or diagonals
- **R3 (Solution construction)**: Convert the board representation to the required string format
- **R4 (Optimization)**: Use sets to efficiently track occupied columns and diagonals

### Example Walkthrough
For n = 4:

1. Start with an empty board:
   ```
   ....
   ....
   ....
   ....
   ```

2. Try placing queens column by column:
   - Place first queen at (0, 0):
     ```
     Q...
     ....
     ....
     ....
     ```
   - Cannot place second queen in column 1 (all rows conflict)
   - Backtrack and try placing first queen at (1, 0):
     ```
     ....
     Q...
     ....
     ....
     ```
   - Place second queen at (3, 1):
     ```
     ....
     Q...
     ....
     .Q..
     ```
   - And so on...

3. Eventually find solutions:
   ```
   .Q..    ..Q.
   ...Q    Q...
   Q...    ...Q
   ..Q.    .Q..
   ```

### Complexity Analysis
- **Time Complexity**: O(N!), where N is the size of the board. There are N choices for the first queen, N-1 for the second, and so on.
- **Space Complexity**: O(N²) for the board representation and O(N) for the recursion stack.
