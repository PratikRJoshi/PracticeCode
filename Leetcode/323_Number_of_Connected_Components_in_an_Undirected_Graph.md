# Number of Connected Components in an Undirected Graph

## Problem Description

**Problem Link:** [Number of Connected Components in an Undirected Graph](https://leetcode.com/problems/number-of-connected-components-in-an-undirected-graph/)

You have a graph of `n` nodes. You are given an integer `n` and an array `edges` where `edges[i] = [ai, bi]` indicates that there is an undirected edge between nodes `ai` and `bi` in the graph.

Return *the number of connected components in the graph*.

**Example 1:**

```
Input: n = 5, edges = [[0,1],[1,2],[3,4]]
Output: 2
```

**Example 2:**

```
Input: n = 5, edges = [[0,1],[1,2],[2,3],[3,4]]
Output: 1
```

**Constraints:**
- `1 <= n <= 2000`
- `1 <= edges.length <= 5000`
- `edges[i].length == 2`
- `0 <= ai, bi < n`
- `ai != bi`
- There are no repeated edges.

## Intuition/Main Idea

This problem requires counting the number of connected components in an undirected graph. We can use **Union-Find** or **DFS/BFS** to solve it.

**Core Algorithm (Union-Find):**
1. Initialize Union-Find with `n` components (each node is its own component initially).
2. For each edge, union the two nodes.
3. Count the number of distinct root components.

**Core Algorithm (DFS):**
1. Build adjacency list.
2. Use DFS to mark all nodes in a component as visited.
3. Count how many times we start a new DFS (each start = one component).

**Why Union-Find works:** Union-Find naturally groups nodes into components. After processing all edges, the number of distinct roots equals the number of components.

## Code Mapping

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Initialize components | UnionFind initialization - Line 6 |
| Process edges | For loop - Lines 8-11 |
| Union nodes | Union operation - Line 10 |
| Count components | Component count - Line 13 |
| Return result | Return statement - Line 14 |

## Final Java Code & Learning Pattern

### Union-Find Approach

```java
class Solution {
    public int countComponents(int n, int[][] edges) {
        UnionFind uf = new UnionFind(n);
        
        // Union all connected nodes
        for (int[] edge : edges) {
            uf.union(edge[0], edge[1]);
        }
        
        // Return number of distinct components
        return uf.getComponents();
    }
    
    class UnionFind {
        private int[] parent;
        private int[] rank;
        private int components;
        
        public UnionFind(int n) {
            parent = new int[n];
            rank = new int[n];
            components = n;  // Initially, each node is its own component
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
        
        public void union(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);
            
            // If already in same component, no change
            if (rootX == rootY) {
                return;
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
            
            // Decrease component count when merging
            components--;
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
    public int countComponents(int n, int[][] edges) {
        // Build adjacency list
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }
        
        for (int[] edge : edges) {
            graph.get(edge[0]).add(edge[1]);
            graph.get(edge[1]).add(edge[0]);
        }
        
        // Count connected components using DFS
        boolean[] visited = new boolean[n];
        int components = 0;
        
        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                // Start DFS from unvisited node (new component)
                dfs(graph, i, visited);
                components++;
            }
        }
        
        return components;
    }
    
    private void dfs(List<List<Integer>> graph, int node, boolean[] visited) {
        visited[node] = true;
        
        // Visit all neighbors
        for (int neighbor : graph.get(node)) {
            if (!visited[neighbor]) {
                dfs(graph, neighbor, visited);
            }
        }
    }
}
```

**Explanation of Key Code Sections:**

**Union-Find Approach:**

1. **Initialization (Line 6):** Create Union-Find with `n` components (each node starts as its own component).

2. **Process Edges (Lines 8-11):** For each edge, union the two nodes. This merges their components.

3. **Count Components (Line 13):** The `components` variable tracks how many distinct components remain.

4. **Union Operation (Lines 35-50):** When merging two components, decrement the component count.

**DFS Approach:**

1. **Build Graph (Lines 6-13):** Create an adjacency list representation.

2. **DFS Traversal (Lines 15-23):** For each unvisited node, start a DFS. Each DFS call marks all nodes in that component as visited.

3. **Count Components (Line 22):** Each time we start a new DFS (find an unvisited node), we've found a new component.

**Why both approaches work:**
- **Union-Find:** Efficiently groups nodes and tracks component count.
- **DFS:** Systematically explores each component and counts how many times we start exploration.

**Example walkthrough for `n = 5, edges = [[0,1],[1,2],[3,4]]`:**
- Union-Find: Start with 5 components
  - Union(0,1): 4 components {0,1}, {2}, {3}, {4}
  - Union(1,2): 3 components {0,1,2}, {3}, {4}
  - Union(3,4): 2 components {0,1,2}, {3,4}
- Result: 2 components

## Complexity Analysis

- **Time Complexity:** 
  - Union-Find: $O(n \times \alpha(n))$ where $\alpha$ is the inverse Ackermann function (effectively constant).
  - DFS: $O(n + m)$ where $m$ is the number of edges.

- **Space Complexity:** $O(n)$ for the Union-Find data structure or adjacency list and visited array.

## Similar Problems

Problems that can be solved using similar Union-Find or graph traversal patterns:

1. **323. Number of Connected Components in an Undirected Graph** (this problem) - Union-Find or DFS
2. **261. Graph Valid Tree** - Check if graph is a tree
3. **547. Number of Provinces** - Similar connected components
4. **684. Redundant Connection** - Union-Find to find cycle
5. **685. Redundant Connection II** - Directed graph variant
6. **200. Number of Islands** - DFS/BFS on grid
7. **130. Surrounded Regions** - DFS from boundaries
8. **695. Max Area of Island** - DFS to count area
9. **133. Clone Graph** - BFS/DFS to clone
10. **207. Course Schedule** - Detect cycle in directed graph

