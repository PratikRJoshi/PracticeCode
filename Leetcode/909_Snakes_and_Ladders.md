# Snakes and Ladders

## Problem Description

**Problem Link:** [Snakes and Ladders](https://leetcode.com/problems/snakes-and-ladders/)

You are given an `n x n` integer matrix `board` where the cells are labeled from `1` to `n^2` in a [Boustrophedon style](https://en.wikipedia.org/wiki/Boustrophedon) starting from the bottom left of the board (i.e. `board[n - 1][0]`) and alternating direction each row.

You start on square `1` of the board. In each move, from square `curr`, do the following:

- Choose a destination square `next` with a label in the range `[curr + 1, min(curr + 6, n^2)]`.
- This choice simulates the result of a standard **6-sided die roll**: i.e., there are always at most 6 destinations, regardless of the size of the board.
- If `next` has a snake or ladder, you **must** move to the destination of that snake or ladder. Otherwise, you move to `next`.
- The game ends when you reach the square `n^2`.

Return *the least number of moves required to reach the square* `n^2`*. If it is not possible to reach the square, return* `-1`.

**Example 1:**
```
Input: board = [[-1,-1,-1,-1,-1,-1],[-1,-1,-1,-1,-1,-1],[-1,-1,-1,-1,-1,-1],[-1,35,-1,-1,13,-1],[-1,-1,-1,-1,-1,-1],[-1,15,-1,-1,-1,-1]]
Output: 4
```

**Constraints:**
- `n == board.length == board[i].length`
- `2 <= n <= 20`
- `board[i][j]` is either `-1` or in the range `[1, n^2]`.

## Intuition/Main Idea

This is a shortest path problem on a graph. We need to find minimum moves to reach n².

**Core Algorithm:**
- Use BFS to find shortest path
- Convert cell number to board coordinates (Boustrophedon pattern)
- For each cell, try moves 1-6, follow snake/ladder if exists
- Track visited cells and moves count

**Why BFS:** BFS finds shortest path in unweighted graph. First time we reach n² is guaranteed minimum moves.

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|------------------------|-----------------------------------|
| Find minimum moves | BFS - Lines 8-35 |
| Convert number to coordinates | Helper function - Lines 37-48 |
| Handle snakes/ladders | Check board value - Lines 20-23 |
| Track moves | Level-based BFS - Lines 12-14 |

## Final Java Code & Learning Pattern (Full Content)

```java
class Solution {
    public int snakesAndLadders(int[][] board) {
        int n = board.length;
        int target = n * n;
        
        Queue<Integer> queue = new LinkedList<>();
        boolean[] visited = new boolean[target + 1];
        
        queue.offer(1);
        visited[1] = true;
        int moves = 0;
        
        while (!queue.isEmpty()) {
            int size = queue.size();
            
            for (int i = 0; i < size; i++) {
                int curr = queue.poll();
                
                if (curr == target) {
                    return moves;
                }
                
                // Try all possible dice rolls (1-6)
                for (int dice = 1; dice <= 6 && curr + dice <= target; dice++) {
                    int next = curr + dice;
                    int[] coord = numToCoord(next, n);
                    int row = coord[0], col = coord[1];
                    
                    // If snake or ladder, move to destination
                    if (board[row][col] != -1) {
                        next = board[row][col];
                    }
                    
                    // If not visited, add to queue
                    if (!visited[next]) {
                        visited[next] = true;
                        queue.offer(next);
                    }
                }
            }
            
            moves++;
        }
        
        return -1;
    }
    
    // Convert cell number to board coordinates (Boustrophedon pattern)
    private int[] numToCoord(int num, int n) {
        int row = (num - 1) / n;
        int col = (num - 1) % n;
        
        // Reverse direction for odd rows (from bottom)
        if (row % 2 == 1) {
            col = n - 1 - col;
        }
        
        // Rows are counted from bottom
        row = n - 1 - row;
        
        return new int[]{row, col};
    }
}
```

## Complexity Analysis

**Time Complexity:** $O(n^2)$ where $n$ is board size. We visit each cell at most once.

**Space Complexity:** $O(n^2)$ for visited array and queue.

## Similar Problems

- [Word Ladder](https://leetcode.com/problems/word-ladder/) - Similar BFS shortest path
- [Jump Game II](https://leetcode.com/problems/jump-game-ii/) - Minimum jumps
- [Shortest Path in Binary Matrix](https://leetcode.com/problems/shortest-path-in-binary-matrix/) - BFS shortest path

