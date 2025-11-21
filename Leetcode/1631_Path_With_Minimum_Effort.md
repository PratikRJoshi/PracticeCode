# Path With Minimum Effort

## Problem Description

**Problem Link:** [Path With Minimum Effort](https://leetcode.com/problems/path-with-minimum-effort/)

You are a hiker preparing for an upcoming hike. You are given `heights`, a 2D array of size `rows x columns`, where `heights[i][j]` represents the height of cell `(i, j)`.

A hiker starts at the top-left cell, `(0, 0)` and wants to reach the bottom-right cell, `(rows-1, columns-1)`. You can move **up, down, left, or right**.

The **effort** of a path is the **maximum absolute difference** in heights between two consecutive cells of the path.

Return *the minimum effort required to travel from the top-left cell to the bottom-right cell*.

**Example 1:**
```
Input: heights = [[1,2,2],[3,8,2],[5,3,5]]
Output: 2
Explanation: The path of [1,3,5,3,5] has a maximum absolute difference of 2 in consecutive cells.
```

**Constraints:**
- `rows == heights.length`
- `columns == heights[i].length`
- `1 <= rows, columns <= 100`
- `1 <= heights[i][j] <= 10^6`

## Intuition/Main Idea

This is a shortest path problem where edge weights are absolute differences. We need to minimize the maximum edge weight along the path.

**Core Algorithm:**
- Use Dijkstra's algorithm with modified priority
- Priority is maximum effort so far (not total)
- Track minimum effort to reach each cell
- Return effort to reach bottom-right

**Why Dijkstra:** We need shortest path with edge weights. Modified Dijkstra minimizes maximum edge weight along path.

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|------------------------|-----------------------------------|
| Find minimum effort path | Dijkstra - Lines 8-35 |
| Track maximum effort | Priority queue - Lines 6, 20 |
| Explore neighbors | 4-directional moves - Lines 25-34 |

## Final Java Code & Learning Pattern (Full Content)

```java
class Solution {
    public int minimumEffortPath(int[][] heights) {
        int rows = heights.length;
        int cols = heights[0].length;
        
        // Priority queue: [effort, row, col]
        // Effort is maximum absolute difference so far
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        pq.offer(new int[]{0, 0, 0});
        
        // Track minimum effort to reach each cell
        int[][] efforts = new int[rows][cols];
        for (int[] row : efforts) {
            Arrays.fill(row, Integer.MAX_VALUE);
        }
        efforts[0][0] = 0;
        
        int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        
        while (!pq.isEmpty()) {
            int[] current = pq.poll();
            int effort = current[0];
            int row = current[1];
            int col = current[2];
            
            // If reached destination
            if (row == rows - 1 && col == cols - 1) {
                return effort;
            }
            
            // Explore neighbors
            for (int[] dir : directions) {
                int newRow = row + dir[0];
                int newCol = col + dir[1];
                
                if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols) {
                    // Calculate effort for this move
                    int newEffort = Math.max(effort, 
                        Math.abs(heights[row][col] - heights[newRow][newCol]));
                    
                    // If found better path, update
                    if (newEffort < efforts[newRow][newCol]) {
                        efforts[newRow][newCol] = newEffort;
                        pq.offer(new int[]{newEffort, newRow, newCol});
                    }
                }
            }
        }
        
        return efforts[rows - 1][cols - 1];
    }
}
```

## Complexity Analysis

**Time Complexity:** $O(rows \times cols \times \log(rows \times cols))$ for Dijkstra with heap.

**Space Complexity:** $O(rows \times cols)$ for efforts array and heap.

## Similar Problems

- [Network Delay Time](https://leetcode.com/problems/network-delay-time/) - Dijkstra's algorithm
- [Cheapest Flights Within K Stops](https://leetcode.com/problems/cheapest-flights-within-k-stops/) - Shortest path with constraints
- [Swim in Rising Water](https://leetcode.com/problems/swim-in-rising-water/) - Similar path finding

