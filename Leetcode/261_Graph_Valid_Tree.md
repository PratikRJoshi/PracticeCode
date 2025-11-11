# Graph Valid Tree

## Problem Description

**Problem Link:** [Graph Valid Tree](https://leetcode.com/problems/graph-valid-tree/)

You have a graph of `n` nodes labeled from `0` to `n - 1`. You are given an integer `n` and an array `edges` where `edges[i] = [ai, bi]` indicates that there is an undirected edge between nodes `ai` and `bi` in the graph.

Return `true` *if the edges of the given graph make up a valid tree, and `false` otherwise*.

**Example 1:**

```
Input: n = 5, edges = [[0,1],[0,2],[0,3],[1,4]]
Output: true
```

**Example 2:**

```
Input: n = 5, edges = [[0,1],[1,2],[2,3],[1,3],[1,4]]
Output: false
```

**Constraints:**
- `1 <= n <= 2000`
- `0 <= edges.length <= 5000`
- `edges[i].length == 2`
- `0 <= ai, bi < n`
- `ai != bi`
- There are no self-loops or repeated edges.

## Intuition/Main Idea

A graph is a valid tree if and only if:
1. It has exactly `n - 1` edges (a tree with `n` nodes has `n - 1` edges).
2. All nodes are connected (the graph is connected).

We can use **Union-Find (Disjoint Set Union)** or **DFS/BFS** to check connectivity and detect cycles.

**Core Algorithm (Union-Find):**
1. Check if number of edges equals `n - 1`.
2. Use Union-Find to connect all edges.
3. If we try to connect two nodes that are already in the same component, there's a cycle → not a tree.
4. After processing all edges, check if all nodes are in the same component.

**Why Union-Find works:** Union-Find efficiently detects cycles and tracks connectivity. If we find a cycle or if the graph is not connected, it's not a valid tree.

## Code Mapping

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Check edge count | Edge count check - Lines 7-9 |
| Initialize Union-Find | UnionFind class - Lines 12-45 |
| Process edges | For loop - Lines 11-16 |
| Detect cycles | Union return value - Line 14 |
| Check connectivity | Component count check - Lines 17-19 |
| Return result | Return statement - Line 20 |

## Final Java Code & Learning Pattern

### Union-Find Approach

```java
class Solution {
    public boolean validTree(int n, int[][] edges) {
        // A tree with n nodes must have exactly n-1 edges
        if (edges.length != n - 1) {
            return false;
        }
        
        // Use Union-Find to detect cycles and check connectivity
        UnionFind uf = new UnionFind(n);
        
        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            
            // If adding this edge creates a cycle, it's not a tree
            if (!uf.union(u, v)) {
                return false;
            }
        }
        
        // Check if all nodes are connected (should have 1 component)
        return uf.getComponents() == 1;
    }
    
    class UnionFind {
        private int[] parent;
        private int[] rank;
        private int components;
        
        public UnionFind(int n) {
            parent = new int[n];
            rank = new int[n];
            components = n;
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
            
            components--;
            return true;
        }
        
        public int getComponents() {
            return components;
        }
    }
}
```

### DFS Approach

```java
import java.util.*;

class Solution {
    public boolean validTree(int n, int[][] edges) {
        // A tree with n nodes must have exactly n-1 edges
        if (edges.length != n - 1) {
            return false;
        }
        
        // Build adjacency list
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }
        
        for (int[] edge : edges) {
            graph.get(edge[0]).add(edge[1]);
            graph.get(edge[1]).add(edge[0]);
        }
        
        // Check connectivity and cycles using DFS
        boolean[] visited = new boolean[n];
        
        // If DFS finds cycle or graph is not connected, return false
        if (hasCycle(graph, 0, -1, visited)) {
            return false;
        }
        
        // Check if all nodes are visited (connected)
        for (boolean v : visited) {
            if (!v) {
                return false;
            }
        }
        
        return true;
    }
    
    private boolean hasCycle(List<List<Integer>> graph, int node, int parent, boolean[] visited) {
        visited[node] = true;
        
        for (int neighbor : graph.get(node)) {
            // Skip the parent node (undirected graph)
            if (neighbor == parent) {
                continue;
            }
            
            // If neighbor is visited and not parent, there's a cycle
            if (visited[neighbor]) {
                return true;
            }
            
            // Recursively check neighbors
            if (hasCycle(graph, neighbor, node, visited)) {
                return true;
            }
        }
        
        return false;
    }
}
```

**Explanation of Key Code Sections:**

**Union-Find Approach:**

1. **Edge Count Check (Lines 7-9):** A tree with `n` nodes must have exactly `n-1` edges. If not, it's either not connected (too few edges) or has cycles (too many edges).

2. **Process Edges (Lines 11-16):** For each edge, try to union the two nodes. If they're already in the same component, adding this edge creates a cycle → not a tree.

3. **Connectivity Check (Lines 17-19):** After processing all edges, check if all nodes are in one component. If `components > 1`, the graph is not connected.

**DFS Approach:**

1. **Build Graph (Lines 10-18):** Create an adjacency list representation of the graph.

2. **Cycle Detection (Lines 20-22):** Use DFS to detect cycles. In an undirected graph, a cycle exists if we visit a node that's not the parent.

3. **Connectivity Check (Lines 24-28):** Check if all nodes were visited. If not, the graph is not connected.

**Why both conditions are necessary:**
- **n-1 edges:** Necessary but not sufficient (could be disconnected).
- **Connected:** Necessary but not sufficient (could have cycles).
- **Both together:** Sufficient for a valid tree.

**Example walkthrough:**
- `n = 5, edges = [[0,1],[0,2],[0,3],[1,4]]`: 4 edges = 5-1 ✓, no cycles ✓, connected ✓ → valid tree
- `n = 5, edges = [[0,1],[1,2],[2,3],[1,3],[1,4]]`: 5 edges ≠ 4 ✗ → not a tree (too many edges = cycle)

## Complexity Analysis

- **Time Complexity:** 
  - Union-Find: $O(n \times \alpha(n))$ where $\alpha$ is the inverse Ackermann function (effectively constant).
  - DFS: $O(n + m)$ where $m$ is the number of edges.

- **Space Complexity:** $O(n)$ for the Union-Find data structure or adjacency list and visited array.

## Similar Problems

Problems that can be solved using similar Union-Find or graph traversal patterns:

1. **261. Graph Valid Tree** (this problem) - Union-Find or DFS
2. **323. Number of Connected Components in an Undirected Graph** - Union-Find
3. **684. Redundant Connection** - Union-Find to find cycle edge
4. **685. Redundant Connection II** - Union-Find with directed graph
5. **547. Number of Provinces** - Union-Find or DFS
6. **200. Number of Islands** - DFS/BFS on grid
7. **130. Surrounded Regions** - DFS from boundaries
8. **695. Max Area of Island** - DFS to count area
9. **133. Clone Graph** - BFS/DFS to clone
10. **207. Course Schedule** - Detect cycle in directed graph
