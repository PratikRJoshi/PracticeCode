### 79. Word Search

Problem: https://leetcode.com/problems/word-search/description/

---

### Problem Description

Given an `m x n` grid of characters `board` and a string `word`, return `true` if `word` exists in the grid.

The word can be constructed from letters of sequentially adjacent cells, where adjacent cells are horizontally or vertically neighboring. The **same letter cell may not be used more than once**.

**Example 1:**
```
board = [["A","B","C","E"],
         ["S","F","C","S"],
         ["A","D","E","E"]]
word = "ABCCED"
Output: true
```

**Example 2:**
```
board = [["A","B","C","E"],
         ["S","F","C","S"],
         ["A","D","E","E"]]
word = "SEE"
Output: true
```

**Example 3:**
```
board = [["A","B","C","E"],
         ["S","F","C","S"],
         ["A","D","E","E"]]
word = "ABCB"
Output: false  (can't reuse B at [0][1])
```

---

### Intuition / Main Idea

#### Step 1 — Recognise what kind of search this is

We need to find a path of cells in a grid such that the characters along that path spell out the target word. Two properties make this a classic **backtracking** problem:

1. At every cell, we have up to 4 choices (up / right / down / left).
2. A cell **cannot be reused** within the same path — so we need to track which cells are already "on our current path."

#### Step 2 — Why backtracking?

Backtracking is essentially **DFS with an undo step**. We:
1. *Choose* a cell to extend the current path.
2. *Explore* deeper (recurse).
3. *Undo* (un-mark the cell) so the same cell is available for other paths tried from higher up in the recursion.

Without the undo step this would just be plain DFS, which would incorrectly forbid a cell forever once visited, even though it may be valid for a completely different starting path.

#### Step 3 — Building the algorithm incrementally

**Start simple:** For every cell in the grid, ask: "Can the word begin here?"
- If `board[i][j] == word[0]`, launch a DFS from `(i, j)`.

**Inside the DFS — what do we need to know?**
- *Where are we?* → `(row, col)`
- *How far have we matched?* → `wordIndex` (index into `word`)
- *Which cells are currently in use?* → `visited[row][col]`

**Base cases:**
- `wordIndex == word.length()` → matched all characters → return `true`.
- Out of bounds, or `board[row][col] != word[wordIndex]`, or cell already visited → dead end → return `false`.

**Recursive step:**
1. Mark `visited[row][col] = true` (cell is now part of the current path).
2. Try all 4 neighbours with `wordIndex + 1`.
3. If any neighbour returns `true`, propagate `true` upward.
4. Otherwise, **backtrack**: set `visited[row][col] = false` so future paths can reuse it.

#### Step 4 — Why a separate `visited` array?

The alternative is to overwrite `board[row][col]` with a sentinel (e.g., `'#'`) and restore it on backtrack. Both approaches work. Using a separate `boolean[][] visited` keeps the board **immutable**, which is:
- Safer (no risk of accidentally not restoring the board).
- Clearer to read — the visited state is explicitly separated from the board data.

The trade-off is $O(M \times N)$ extra space instead of $O(1)$, but the recursion stack is already $O(L)$, so this is acceptable.

#### Why does the algorithm always find the correct answer?

The outer double `for` loop tries **every cell** as a potential starting point. The inner DFS exhaustively explores every valid path from that start. The `visited` flag ensures we never reuse a cell in the same path. Together these two guarantees mean we will find the word if and only if a valid path exists.

---

### Code Mapping

| Problem Requirement | Java Code Section |
|---|---|
| Try every cell as a starting point | Outer `for` loops in `exist()` |
| Only start when first character matches | `if (board[i][j] == word.charAt(0))` guard before calling `backtrack` |
| Track cells used in the current path | `boolean[][] visited` array passed to `backtrack` |
| Base case: full word matched | `if (wordIndex == word.length()) return true` |
| Base case: out of bounds / wrong char / already visited | Three-part guard at the top of `backtrack` |
| Explore all 4 neighbours | `for (int[] direction : DIRECTIONS)` loop |
| Mark cell before recursing | `visited[row][col] = true` before recursive calls |
| Undo mark on backtrack | `visited[row][col] = false` after the loop returns `false` |

---

### Final Java Code

```java
class Solution {
    // Four possible movement directions: up, right, down, left
    private static final int[][] DIRECTIONS = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};

    public boolean exist(char[][] board, String word) {
        int rows = board.length;
        int cols = board[0].length;

        // visited[i][j] = true means cell (i,j) is already part of the current DFS path.
        // We allocate it once here and pass it down so all recursive calls share the same array.
        boolean[][] visited = new boolean[rows][cols];

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                // Only launch a DFS from cells whose character matches the first letter.
                // This is an early-exit optimisation: no point starting if word[0] doesn't match.
                if (board[row][col] == word.charAt(0) && backtrack(board, word, row, col, 0, visited)) {
                    return true;
                }
            }
        }

        // Exhausted all starting cells without finding the word.
        return false;
    }

    /**
     * DFS + backtracking: tries to match word[wordIndex..] starting at (row, col).
     *
     * @param board      The 2D character grid (never modified).
     * @param word       The target word.
     * @param row        Current row in the grid.
     * @param col        Current column in the grid.
     * @param wordIndex  Index of the character in word we are trying to match at this step.
     * @param visited    Shared boolean grid marking cells on the current DFS path.
     * @return           true if the remaining suffix word[wordIndex..] can be matched.
     */
    private boolean backtrack(char[][] board, String word, int row, int col, int wordIndex, boolean[][] visited) {

        // Base case: every character has been matched — the word exists!
        if (wordIndex == word.length()) {
            return true;
        }

        // Guard 1: boundary check — cell must be inside the grid.
        // Guard 2: character check — cell must match the current character we need.
        // Guard 3: visited check — cell must not already be on the current path.
        if (row < 0 || row >= board.length
                || col < 0 || col >= board[0].length
                || board[row][col] != word.charAt(wordIndex)
                || visited[row][col]) {
            return false;
        }

        // Mark this cell as part of the current path BEFORE recursing into neighbours.
        // This prevents any deeper call from re-using the same cell within this path.
        visited[row][col] = true;

        // Explore all four neighbours, trying to match the next character.
        for (int[] direction : DIRECTIONS) {
            int nextRow = row + direction[0];
            int nextCol = col + direction[1];

            if (backtrack(board, word, nextRow, nextCol, wordIndex + 1, visited)) {
                // A valid path was found — propagate success upward immediately.
                // NOTE: we intentionally do NOT unmark visited here because we are
                // returning true; no further backtracking is needed.
                return true;
            }
        }

        // BACKTRACK: no neighbour led to a valid path.
        // Unmark so that other DFS paths (starting from different cells) can use this cell.
        visited[row][col] = false;

        return false;
    }
}
```

---

### Complexity Analysis

- **Time Complexity**: $O(M \times N \times 3^L)$
    - $M \times N$ — we try every cell as a starting point.
    - $3^L$ — at each recursive step (depth up to $L$, the word length), we explore at most **3** new neighbours (not 4, because the cell we came from is already marked `visited` and will be rejected immediately). So the DFS tree has branching factor 3 and depth $L$.

- **Space Complexity**: $O(M \times N + L)$
    - $O(M \times N)$ for the `visited` boolean array.
    - $O(L)$ for the recursion call stack, which goes at most $L$ levels deep (one level per character matched).

---

### Similar Problems

- [212. Word Search II](https://leetcode.com/problems/word-search-ii/) — same DFS/backtracking on a grid, but now find *all* words from a list simultaneously (use a Trie to prune).
- [200. Number of Islands](https://leetcode.com/problems/number-of-islands/) — DFS/BFS on a grid, marking visited cells.
- [130. Surrounded Regions](https://leetcode.com/problems/surrounded-regions/) — DFS/BFS flood-fill on a grid with a visited marker.
