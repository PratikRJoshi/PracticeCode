# 323. Number of Connected Components in an Undirected Graph

## Problem Description

You have a graph of `n` nodes. You are given an integer `n` and an array `edges` where `edges[i] = [ui, vi]` indicates that there is an undirected edge between the nodes `ui` and `vi`.

Return the number of connected components in the graph.

### Examples:

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

## Intuition/Main Idea

To count the number of connected components in an undirected graph, we need to identify all groups of nodes that are connected to each other but disconnected from other groups.

There are two main approaches to solve this problem:

1. **Depth-First Search (DFS)**: We can use DFS to traverse each connected component and mark all visited nodes. Each time we start a DFS from an unvisited node, we've found a new connected component.

2. **Union-Find (Disjoint Set)**: We can use Union-Find to merge connected nodes and count the number of distinct sets at the end.

Both approaches are valid, but I'll focus on the Union-Find approach as it's a classic pattern for this type of problem.

## Code Mapping

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Count connected components | `int count = n;` and decrementing when merging components |
| Handle n nodes | `parent = new int[n];` and initializing each node as its own parent |
| Process undirected edges | `for (int[] edge : edges) { union(edge[0], edge[1]); }` |
| Return final count | `return count;` |

## Final Java Code & Learning Pattern (Full Content)

```java
class Solution {
    private int[] parent;
    private int count;
    
    public int countComponents(int n, int[][] edges) {
        // Initialize Union-Find data structure
        parent = new int[n];
        count = n; // Start with n components (each node is its own component)
        
        // Initialize each node as its own parent
        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }
        
        // Process each edge
        for (int[] edge : edges) {
            union(edge[0], edge[1]);
        }
        
        // Return the count of connected components
        return count;
    }
    
    // Find operation with path compression
    private int find(int x) {
        if (parent[x] != x) {
            parent[x] = find(parent[x]); // Path compression
        }
        return parent[x];
    }
    
    // Union operation
    private void union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);
        
        // If x and y are already in the same component, do nothing
        if (rootX == rootY) {
            return;
        }
        
        // Merge the two components
        parent[rootX] = rootY;
        
        // Decrement the count of connected components
        count--;
    }
}
```

## Alternative DFS Solution

```java
class Solution {
    public int countComponents(int n, int[][] edges) {
        // Build adjacency list
        List<List<Integer>> adjacencyList = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            adjacencyList.add(new ArrayList<>());
        }
        
        // Add edges to adjacency list (undirected)
        for (int[] edge : edges) {
            adjacencyList.get(edge[0]).add(edge[1]);
            adjacencyList.get(edge[1]).add(edge[0]);
        }
        
        // Track visited nodes
        boolean[] visited = new boolean[n];
        int count = 0;
        
        // Perform DFS for each unvisited node
        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                count++; // Found a new connected component
                dfs(adjacencyList, visited, i);
            }
        }
        
        return count;
    }
    
    private void dfs(List<List<Integer>> adjacencyList, boolean[] visited, int node) {
        visited[node] = true;
        
        for (int neighbor : adjacencyList.get(node)) {
            if (!visited[neighbor]) {
                dfs(adjacencyList, visited, neighbor);
            }
        }
    }
}
```

## Complexity Analysis

### Union-Find Approach:
- **Time Complexity**: $O(E \cdot \alpha(n))$ where $E$ is the number of edges and $\alpha(n)$ is the inverse Ackermann function, which is nearly constant for all practical purposes. Building the parent array takes $O(n)$ and processing each edge takes $O(\alpha(n))$.
- **Space Complexity**: $O(n)$ for the parent array.

### DFS Approach:
- **Time Complexity**: $O(n + E)$ where $n$ is the number of vertices and $E$ is the number of edges. We visit each vertex and edge once.
- **Space Complexity**: $O(n + E)$ for the adjacency list and $O(n)$ for the visited array and recursion stack, so overall $O(n + E)$.
