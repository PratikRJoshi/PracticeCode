# Min Cost to Connect All Points

## Problem Description

**Problem Link:** [Min Cost to Connect All Points](https://leetcode.com/problems/min-cost-to-connect-all-points/)

You are given an array of `points` where `points[i] = [xi, yi]` represents a point on the **X-Y** plane and an integer `k`.

Return *the minimum cost to make all points connected*. All points are connected if there is exactly one simple path between any two points.

The cost is the **Manhattan distance** between two points: `|xi - xj| + |yi - yj|`.

**Example 1:**

```
Input: points = [[0,0],[2,2],[3,10],[5,2],[7,0]]
Output: 20
Explanation: We can connect the points as shown above to get the minimum total cost of 20.
Notice that there is a unique path between every pair of points.
```

**Example 2:**

```
Input: points = [[3,12],[-2,5],[-4,1]]
Output: 18
```

**Constraints:**
- `1 <= points.length <= 1000`
- `-10^6 <= xi, yi <= 10^6`
- All pairs `(xi, yi)` are distinct.

## Intuition/Main Idea

This is a **Minimum Spanning Tree (MST)** problem. We need to connect all points with minimum total cost, which is exactly what MST algorithms solve.

**Key Insight:** We can use **Kruskal's algorithm** (with Union-Find) or **Prim's algorithm** to find the MST. The cost between any two points is their Manhattan distance.

**Core Algorithm (Kruskal's):**
1. Calculate all possible edges (distances between all pairs of points).
2. Sort edges by weight (distance) in ascending order.
3. Use Union-Find to add edges one by one, skipping edges that would create cycles.
4. Stop when we have `n-1` edges (MST has `n-1` edges for `n` vertices).
5. Return the sum of edge weights.

**Why MST works:** A spanning tree connects all vertices with minimum total edge weight. Since we need exactly one path between any two points (tree property), MST is the optimal solution.

## Code Mapping

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Calculate all edges | Edge creation - Lines 8-14 |
| Sort edges by weight | Sorting - Line 16 |
| Union-Find data structure | UnionFind class - Lines 20-50 |
| Add edges to MST | Kruskal's loop - Lines 18-24 |
| Calculate Manhattan distance | Distance calculation - Line 12 |
| Return total cost | Return statement - Line 25 |

## Final Java Code & Learning Pattern

```java
import java.util.*;

class Solution {
    public int minCostConnectPoints(int[][] points) {
        int n = points.length;
        List<int[]> edges = new ArrayList<>();
        
        // Create all possible edges with their weights (Manhattan distance)
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int distance = Math.abs(points[i][0] - points[j][0]) + 
                              Math.abs(points[i][1] - points[j][1]);
                edges.add(new int[]{i, j, distance});
            }
        }
        
        // Sort edges by weight (distance)
        edges.sort((a, b) -> a[2] - b[2]);
        
        // Kruskal's algorithm: use Union-Find to build MST
        UnionFind uf = new UnionFind(n);
        int totalCost = 0;
        int edgesUsed = 0;
        
        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            int weight = edge[2];
            
            // If adding this edge doesn't create a cycle, add it to MST
            if (uf.union(u, v)) {
                totalCost += weight;
                edgesUsed++;
                // MST has n-1 edges
                if (edgesUsed == n - 1) {
                    break;
                }
            }
        }
        
        return totalCost;
    }
    
    // Union-Find data structure
    class UnionFind {
        int[] parent;
        int[] rank;
        
        public UnionFind(int n) {
            parent = new int[n];
            rank = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
            }
        }
        
        public int find(int x) {
            // Path compression
            if (parent[x] != x) {
                parent[x] = find(parent[x]);
            }
            return parent[x];
        }
        
        public boolean union(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);
            
            // If already in same component, adding edge creates cycle
            if (rootX == rootY) {
                return false;
            }
            
            // Union by rank
            if (rank[rootX] < rank[rootY]) {
                parent[rootX] = rootY;
            } else if (rank[rootX] > rank[rootY]) {
                parent[rootY] = rootX;
            } else {
                parent[rootY] = rootX;
                rank[rootX]++;
            }
            
            return true;
        }
    }
}
```

**Alternative Implementation (Prim's Algorithm):**

```java
import java.util.*;

class Solution {
    public int minCostConnectPoints(int[][] points) {
        int n = points.length;
        boolean[] visited = new boolean[n];
        // Min-heap: [distance, pointIndex]
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        
        // Start from point 0
        pq.offer(new int[]{0, 0});
        int totalCost = 0;
        int pointsConnected = 0;
        
        while (pointsConnected < n) {
            int[] current = pq.poll();
            int distance = current[0];
            int pointIndex = current[1];
            
            // Skip if already visited
            if (visited[pointIndex]) {
                continue;
            }
            
            visited[pointIndex] = true;
            totalCost += distance;
            pointsConnected++;
            
            // Add all unvisited neighbors to priority queue
            for (int i = 0; i < n; i++) {
                if (!visited[i]) {
                    int dist = Math.abs(points[pointIndex][0] - points[i][0]) +
                              Math.abs(points[pointIndex][1] - points[i][1]);
                    pq.offer(new int[]{dist, i});
                }
            }
        }
        
        return totalCost;
    }
}
```

**Explanation of Key Code Sections:**

1. **Edge Creation (Lines 8-14):** We create edges between all pairs of points. For each pair `(i, j)`, we calculate the Manhattan distance and store it as an edge.

2. **Sort Edges (Line 16):** We sort edges by weight in ascending order. This ensures we consider the shortest edges first in Kruskal's algorithm.

3. **Union-Find Initialization (Line 19):** We create a Union-Find data structure to track connected components and detect cycles.

4. **Kruskal's Algorithm (Lines 20-28):**
   - **Process Edges (Line 21):** Iterate through edges in sorted order (shortest first).
   - **Cycle Detection (Line 25):** Try to union the two endpoints. If they're already in the same component, adding this edge would create a cycle, so we skip it.
   - **Add to MST (Lines 26-27):** If no cycle, add the edge to MST and update total cost.
   - **Early Termination (Lines 28-30):** Stop when we have `n-1` edges (MST is complete).

5. **Union-Find Implementation (Lines 33-66):**
   - **Find with Path Compression (Lines 42-47):** Find the root of a component, compressing the path for efficiency.
   - **Union by Rank (Lines 49-64):** Merge two components, using rank to keep the tree balanced.

**Why Kruskal's works:**
- **Greedy choice:** Always add the shortest edge that doesn't create a cycle.
- **Optimal:** This greedy approach is proven to yield the minimum spanning tree.
- **Cycle detection:** Union-Find efficiently detects if adding an edge creates a cycle.

**Manhattan Distance:** `|x1 - x2| + |y1 - y2|` represents the distance when you can only move horizontally and vertically (like city blocks).

## Complexity Analysis

- **Time Complexity:** 
  - Kruskal's: $O(n^2 \log n)$ where $n$ is the number of points. We create $O(n^2)$ edges, sort them ($O(n^2 \log n)$), and process them with Union-Find ($O(n^2 \alpha(n))$ where $\alpha$ is the inverse Ackermann function, effectively constant).
  - Prim's: $O(n^2 \log n)$ due to priority queue operations.

- **Space Complexity:** $O(n^2)$ for storing all edges in Kruskal's, or $O(n)$ for Prim's.

## Similar Problems

Problems that can be solved using similar MST algorithms:

1. **1584. Min Cost to Connect All Points** (this problem) - MST with Manhattan distance
2. **1135. Connecting Cities With Minimum Cost** - MST problem
3. **1168. Optimize Water Distribution in a Village** - MST with virtual node
4. **1489. Find Critical and Pseudo-Critical Edges in Minimum Spanning Tree** - MST analysis
5. **684. Redundant Connection** - Find edge that creates cycle
6. **685. Redundant Connection II** - Directed graph variant
7. **1319. Number of Operations to Make Network Connected** - Connected components
8. **261. Graph Valid Tree** - Check if graph is tree
9. **323. Number of Connected Components in an Undirected Graph** - Union-Find
10. **1102. Path With Maximum Minimum Value** - Modified shortest path

