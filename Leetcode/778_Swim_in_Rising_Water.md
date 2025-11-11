# Swim in Rising Water

## Problem Description

**Problem Link:** [Swim in Rising Water](https://leetcode.com/problems/swim-in-rising-water/)

You are given an `n x n` integer matrix `grid` where each value `grid[i][j]` represents the elevation at that point `(i, j)`.

The rain starts to fall. At time `t`, the depth of the water everywhere is `t`. You can swim from a square to another 4-directionally adjacent square if and only if the elevation of both squares individually are at most `t`. You can swim infinite distance in zero time. Of course, you must stay within the boundaries of the grid during your swim.

Return *the least time until you can reach the bottom right square `(n - 1, n - 1)` if you start at the top left square `(0, 0)`*.

**Example 1:**

```
Input: grid = [[0,2],[1,3]]
Output: 3
Explanation:
At time t = 0, you are in the location (0, 0).
You cannot go anywhere because 4-directionally adjacent neighbors have a higher elevation.
At time t = 3, you can swim anywhere in the grid.
```

**Example 2:**

```
Input: grid = [[0,1,2,3,4],[24,23,22,21,5],[12,13,14,15,16],[11,17,18,19,20],[10,9,8,7,6]]
Output: 16
Explanation: The path with least maximum elevation is shown.
```

**Constraints:**
- `n == grid.length`
- `n == grid[i].length`
- `1 <= n <= 50`
- `0 <= grid[i][j] < n^2`
- Each value `grid[i][j]` is **unique**.

## Intuition/Main Idea

This problem can be solved using **Dijkstra's algorithm** or **binary search + BFS/DFS**. The key insight is that we need to find the minimum time (which equals the maximum elevation along the path) to reach the destination.

**Core Algorithm (Dijkstra's):**
1. Use a priority queue (min-heap) to always process the cell with the minimum elevation/time first.
2. Start from `(0, 0)` with time/elevation `grid[0][0]`.
3. For each cell, explore neighbors that are reachable (their elevation <= current max elevation along path).
4. Update the maximum elevation needed to reach each cell.
5. When we reach `(n-1, n-1)`, return the maximum elevation along the path.

**Why Dijkstra's works:** We want the path with the minimum maximum elevation. Dijkstra's algorithm naturally finds the shortest path in terms of edge weights, and by using max elevation as the "cost", we find the path with minimum maximum elevation.

## Code Mapping

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Priority queue for Dijkstra's | PriorityQueue - Line 7 |
| Track visited cells | Visited array - Line 8 |
| Start from top-left | Initial offer - Lines 11-12 |
| Process minimum elevation cell | While loop - Line 14 |
| Check if reached destination | Destination check - Lines 17-19 |
| Explore four directions | Direction loop - Lines 22-32 |
| Update path maximum elevation | Max calculation - Line 27 |
| Add to priority queue | Offer operation - Line 30 |

## Final Java Code & Learning Pattern

```java
import java.util.PriorityQueue;

class Solution {
    private static final int[][] DIRECTIONS = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
    
    public int swimInWater(int[][] grid) {
        int n = grid.length;
        // Priority queue: [row, col, maxElevation]
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[2] - b[2]);
        boolean[][] visited = new boolean[n][n];
        
        // Start from top-left corner
        pq.offer(new int[]{0, 0, grid[0][0]});
        visited[0][0] = true;
        
        while (!pq.isEmpty()) {
            int[] current = pq.poll();
            int row = current[0];
            int col = current[1];
            int maxElevation = current[2];
            
            // If reached destination, return the maximum elevation along path
            if (row == n - 1 && col == n - 1) {
                return maxElevation;
            }
            
            // Explore four directions
            for (int[] dir : DIRECTIONS) {
                int newRow = row + dir[0];
                int newCol = col + dir[1];
                
                // Check bounds and if not visited
                if (newRow >= 0 && newRow < n && newCol >= 0 && newCol < n &&
                    !visited[newRow][newCol]) {
                    visited[newRow][newCol] = true;
                    // Maximum elevation along path to this cell
                    int newMaxElevation = Math.max(maxElevation, grid[newRow][newCol]);
                    pq.offer(new int[]{newRow, newCol, newMaxElevation});
                }
            }
        }
        
        return -1;  // Should not reach here
    }
}
```

**Explanation of Key Code Sections:**

1. **Priority Queue Setup (Line 7):** We use a min-heap priority queue that orders cells by their maximum elevation along the path. This ensures we always process the cell with the minimum "cost" first.

2. **Initialization (Lines 11-12):** We start from the top-left corner `(0, 0)` with initial elevation `grid[0][0]` and mark it as visited.

3. **Dijkstra's Main Loop (Lines 14-32):**
   - **Extract Minimum (Line 15):** Get the cell with the minimum maximum elevation.
   - **Destination Check (Lines 17-19):** If we've reached the destination, return the maximum elevation along this path. This is guaranteed to be optimal due to Dijkstra's property.
   - **Explore Neighbors (Lines 22-31):** For each valid neighbor:
     - Check bounds and if not visited.
     - Calculate the new maximum elevation: `max(currentMax, neighborElevation)`.
     - Add to priority queue for processing.

4. **Why this finds optimal path:** Dijkstra's algorithm guarantees that when we first reach the destination, we've found the path with the minimum maximum elevation. This is because we always process cells in order of increasing "cost" (maximum elevation).

**Alternative Approach (Binary Search + BFS):**

```java
class Solution {
    private static final int[][] DIRECTIONS = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
    
    public int swimInWater(int[][] grid) {
        int n = grid.length;
        int left = grid[0][0];
        int right = n * n - 1;
        
        // Binary search on the answer (time/elevation)
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (canReach(grid, mid)) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        
        return left;
    }
    
    // BFS to check if we can reach destination with max elevation = time
    private boolean canReach(int[][] grid, int time) {
        int n = grid.length;
        boolean[][] visited = new boolean[n][n];
        Queue<int[]> queue = new LinkedList<>();
        
        if (grid[0][0] > time) return false;
        
        queue.offer(new int[]{0, 0});
        visited[0][0] = true;
        
        while (!queue.isEmpty()) {
            int[] cell = queue.poll();
            int row = cell[0];
            int col = cell[1];
            
            if (row == n - 1 && col == n - 1) {
                return true;
            }
            
            for (int[] dir : DIRECTIONS) {
                int newRow = row + dir[0];
                int newCol = col + dir[1];
                
                if (newRow >= 0 && newRow < n && newCol >= 0 && newCol < n &&
                    !visited[newRow][newCol] && grid[newRow][newCol] <= time) {
                    visited[newRow][newCol] = true;
                    queue.offer(new int[]{newRow, newCol});
                }
            }
        }
        
        return false;
    }
}
```

**Comparison:**
- **Dijkstra's:** More direct, O(n² log n) time. Finds the answer in one pass.
- **Binary Search + BFS:** O(n² log(maxElevation)) time. May require multiple BFS runs but each BFS is simpler.

## Complexity Analysis

- **Time Complexity:** $O(n^2 \log n)$ where $n$ is the grid size. We process at most $n^2$ cells, each with $O(\log n)$ priority queue operations.

- **Space Complexity:** $O(n^2)$ for the priority queue and visited array.

## Similar Problems

Problems that can be solved using similar Dijkstra's or path-finding patterns:

1. **778. Swim in Rising Water** (this problem) - Dijkstra's with max elevation
2. **1631. Path With Minimum Effort** - Similar path with minimum maximum
3. **1102. Path With Maximum Minimum Value** - Path with maximum minimum
4. **743. Network Delay Time** - Dijkstra's shortest path
5. **787. Cheapest Flights Within K Stops** - Dijkstra's with constraints
6. **1514. Path with Maximum Probability** - Dijkstra's with probabilities
7. **1293. Shortest Path in a Grid with Obstacles Elimination** - BFS with state
8. **407. Trapping Rain Water II** - Priority queue for water level
9. **499. The Maze III** - Dijkstra's with path reconstruction
10. **505. The Maze II** - Shortest path in maze

