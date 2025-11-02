### 79. Word Search
Problem: https://leetcode.com/problems/word-search/description/

---

### Main Idea & Intuition

The problem asks us to find if a given word exists in a 2D grid of characters. The key insight is to use a backtracking approach to explore all possible paths in the grid.

#### Core Concept: Backtracking

1. **Explore all paths**: Try every possible path starting from each cell
2. **Backtrack when invalid**: If a path is invalid, backtrack and try a different path
3. **Mark visited cells**: Use a boolean matrix or modify the original board to keep track of visited cells to avoid cycles

#### Understanding the Backtracking Process

1. **Base case**: If we've found all characters of the word, return true
2. **Explore neighbors**: Try moving in all four directions (up, down, left, right)
3. **Backtrack**: If a path is invalid, backtrack and try a different path
4. **Mark visited**: Mark cells as visited to avoid cycles
5. **Unmark visited**: Unmark cells when backtracking to explore other paths

### Code Implementation with Detailed Comments

```java
class Solution {
    // Four possible movement directions: up, right, down, left
    private static final int[][] DIRECTIONS = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    
    public boolean exist(char[][] board, String word) {
        int rows = board.length;
        int cols = board[0].length;
        
        // Try each cell as a starting point
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                // If the first character matches, start backtracking from this cell
                if (board[i][j] == word.charAt(0) && backtrack(board, word, i, j, 0)) {
                    return true;
                }
            }
        }
        
        // No valid path found
        return false;
    }
    
    /**
     * Backtracking function to explore all possible paths
     * 
     * @param board  The 2D grid of characters
     * @param word   The word we're searching for
     * @param row    Current row position
     * @param col    Current column position
     * @param index  Current index in the word we're matching
     * @return       True if we can find the remaining part of the word starting from this position
     */
    private boolean backtrack(char[][] board, String word, int row, int col, int index) {
        // Base case 1: We've matched all characters in the word
        if (index == word.length()) {
            return true;
        }
        
        // Base case 2: Out of bounds or character doesn't match
        if (row < 0 || row >= board.length || col < 0 || col >= board[0].length || 
            board[row][col] != word.charAt(index)) {
            return false;
        }
        
        // Mark the current cell as visited by replacing with a special character
        // This is more efficient than using a separate visited matrix
        char originalChar = board[row][col];
        board[row][col] = '#'; // Use any character that won't appear in the input
        
        // Try all four directions (up, right, down, left)
        for (int[] direction : DIRECTIONS) {
            int newRow = row + direction[0];
            int newCol = col + direction[1];
            
            // Recursive call to explore this path
            if (backtrack(board, word, newRow, newCol, index + 1)) {
                // If we found a valid path, propagate the success back up
                return true;
            }
        }
        
        // Backtrack: restore the original character for other paths to explore
        board[row][col] = originalChar;
        
        // No valid path found from this position
        return false;
    }
}
```

### Complexity Analysis

*   **Time Complexity**: `O(N * 3^L)` where:
    *   `N` is the number of cells in the board (rows × columns)
    *   `L` is the length of the word
    *   For each starting position, we potentially explore 4 directions for the first character, but only 3 directions for subsequent characters (since we can't go back to where we came from)
    *   More precisely, we can think of this as a 4-way branching decision tree of depth L, but since we mark cells as visited, we effectively have only 3 choices at each step after the first

*   **Space Complexity**:
    *   **Recursion Stack**: `O(L)` where L is the length of the word. This represents the maximum depth of the recursion.
    *   **Board Modification**: `O(1)` additional space since we modify the board in-place to mark visited cells instead of using a separate visited matrix.
    *   If we had used a separate visited matrix, the space complexity would have been `O(N)` where N is the number of cells in the board.

*   **Optimization Notes**:
    *   The in-place modification of the board to mark visited cells is a space optimization that avoids the need for a separate visited matrix.
    *   Early termination when the first character doesn't match saves unnecessary backtracking.
    *   Checking boundary conditions and character matching before recursive calls prevents unnecessary stack frames.
        I'll update the time and space complexity section in
        Leetcode/LC79.md
        with a more detailed analysis. Let me try again with a complete edit.
