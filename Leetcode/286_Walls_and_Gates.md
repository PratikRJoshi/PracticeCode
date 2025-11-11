# Walls and Gates

## Problem Description

**Problem Link:** [Walls and Gates](https://leetcode.com/problems/walls-and-gates/)

You are given an `m x n` grid `rooms` initialized with these three possible values:

- `-1` A wall or an obstacle.
- `0` A gate.
- `INF` Infinity means an empty room. We use the value `2^31 - 1 = 2147483647` to represent `INF` as you may assume that the distance to a gate is less than `2147483647`.

Fill each empty room with the distance to its nearest gate. If it is impossible to reach a gate, it should remain `INF`.

**Example 1:**

```
Input: rooms = [[2147483647,-1,0,2147483647],[2147483647,2147483647,2147483647,-1],[2147483647,-1,2147483647,-1],[0,-1,2147483647,2147483647]]
Output: [[3,-1,0,1],[2,2,1,-1],[1,-1,2,-1],[0,-1,3,4]]
```

**Example 2:**

```
Input: rooms = [[-1]]
Output: [[-1]]
```

**Example 3:**

```
Input: rooms = [[2147483647]]
Output: [[2147483647]]
```

**Constraints:**
- `m == rooms.length`
- `n == rooms[i].length`
- `1 <= m, n <= 250`
- `rooms[i][j]` is `-1`, `0`, or `2^31 - 1`.

## Intuition/Main Idea

This is a **multi-source BFS** problem. We need to find the shortest distance from each empty room to the nearest gate.

**Key Insight:** Instead of running BFS from each empty room (which would be inefficient), we run BFS from all gates simultaneously. This way, when we first reach an empty room, we've found the shortest distance to the nearest gate.

**Core Algorithm:**
1. Find all gates (cells with value 0) and add them to a queue.
2. Use BFS starting from all gates simultaneously.
3. For each cell, update its distance if we find a shorter path.
4. Only process cells that are empty rooms (INF) and haven't been visited with a shorter distance.

**Why multi-source BFS works:** By starting from all gates at once, the first time we reach an empty room via BFS guarantees it's the shortest distance to the nearest gate. This is because BFS explores level by level (distance by distance).

## Code Mapping

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Find all gates | Initial queue population - Lines 8-14 |
| Multi-source BFS | Queue and BFS loop - Lines 16-35 |
| Explore four directions | Direction array and loop - Lines 6, 25-34 |
| Update room distance | Distance update - Line 30 |
| Skip walls and gates | Boundary and value checks - Lines 27-29 |
| Process empty rooms | INF check - Line 27 |

## Final Java Code & Learning Pattern

```java
import java.util.LinkedList;
import java.util.Queue;

class Solution {
    private static final int[][] DIRECTIONS = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
    private static final int INF = 2147483647;
    
    public void wallsAndGates(int[][] rooms) {
        int m = rooms.length;
        int n = rooms[0].length;
        Queue<int[]> queue = new LinkedList<>();
        
        // Find all gates and add them to queue
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (rooms[i][j] == 0) {
                    queue.offer(new int[]{i, j});
                }
            }
        }
        
        // Multi-source BFS: process all gates simultaneously
        int distance = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            distance++;
            
            // Process all cells at current distance level
            for (int i = 0; i < size; i++) {
                int[] cell = queue.poll();
                int row = cell[0];
                int col = cell[1];
                
                // Explore four directions
                for (int[] dir : DIRECTIONS) {
                    int newRow = row + dir[0];
                    int newCol = col + dir[1];
                    
                    // Check bounds and if cell is an empty room
                    if (newRow >= 0 && newRow < m && newCol >= 0 && newCol < n &&
                        rooms[newRow][newCol] == INF) {
                        // Update distance and add to queue
                        rooms[newRow][newCol] = distance;
                        queue.offer(new int[]{newRow, newCol});
                    }
                }
            }
        }
    }
}
```

**Explanation of Key Code Sections:**

1. **Direction Array (Line 6):** We define the four possible directions: right, down, left, up.

2. **Find All Gates (Lines 10-14):** We iterate through the entire grid and add all gates (cells with value 0) to the queue. These are our starting points for BFS.

3. **Multi-Source BFS (Lines 16-35):**
   - **Level-by-Level Processing (Lines 18-19):** We process all cells at the current distance level before moving to the next level. This ensures we find the shortest distance.
   - **Distance Increment (Line 19):** After processing all cells at distance `d`, the next level will be at distance `d+1`.
   - **Explore Neighbors (Lines 25-34):** For each cell, we explore its four neighbors:
     - **Boundary Check (Line 27):** Ensure the new cell is within grid bounds.
     - **Empty Room Check (Line 28):** Only process cells that are empty rooms (INF). We skip walls (-1) and gates (0).
     - **Update Distance (Line 30):** Set the distance to the current level.
     - **Add to Queue (Line 31):** Add the cell to the queue for the next level.

**Why multi-source BFS is efficient:**
- **Single BFS run:** Instead of running BFS from each empty room (O(m²n²)), we run one BFS from all gates (O(mn)).
- **Guaranteed shortest:** BFS explores level by level, so the first time we reach an empty room is via the shortest path.
- **Optimal:** Time complexity is optimal for this problem.

**Why we check `rooms[newRow][newCol] == INF`:**
- We only update empty rooms that haven't been visited yet.
- Once a room is updated, it's no longer INF, so we won't process it again.
- This ensures each room is updated exactly once with the shortest distance.

**Example walkthrough:**
- Initial: Gates at (0,2) and (3,0), all other rooms are INF
- Level 1: Process gates, update neighbors to distance 1
- Level 2: Process distance-1 cells, update their neighbors to distance 2
- Continue until all reachable rooms are updated

## Complexity Analysis

- **Time Complexity:** $O(m \times n)$ where $m$ and $n$ are the dimensions of the grid. Each cell is visited at most once.

- **Space Complexity:** $O(m \times n)$ for the queue in the worst case when all cells are gates.

## Similar Problems

Problems that can be solved using similar BFS patterns:

1. **286. Walls and Gates** (this problem) - Multi-source BFS
2. **200. Number of Islands** - BFS/DFS on grid
3. **130. Surrounded Regions** - BFS from boundaries
4. **994. Rotting Oranges** - Multi-source BFS
5. **542. 01 Matrix** - Multi-source BFS
6. **1162. As Far from Land as Possible** - Multi-source BFS
7. **317. Shortest Distance from All Buildings** - BFS from multiple sources
8. **1091. Shortest Path in Binary Matrix** - BFS shortest path
9. **1293. Shortest Path in a Grid with Obstacles Elimination** - BFS with state
10. **1926. Nearest Exit from Entrance in Maze** - BFS shortest path

