### 261. Graph Valid Tree
Problem: https://leetcode.com/problems/graph-valid-tree/

#### Problem Description

You have a graph of `n` nodes labeled from `0` to `n-1`. You are given an integer `n` and an array `edges` where `edges[i] = [ai, bi]` indicates that there is an undirected edge between nodes `ai` and `bi` in the graph.

Return `true` if the edges of the given graph make up a valid tree, and `false` otherwise.

A graph is a valid tree if:
1. It is fully connected (all nodes can be reached from any other node)
2. It has no cycles (no loops in the graph)

#### Example 1:
**Input:** n = 5, edges = [[0,1],[0,2],[0,3],[1,4]]  
**Output:** true

#### Example 2:
**Input:** n = 5, edges = [[0,1],[1,2],[2,3],[1,3],[1,4]]  
**Output:** false

---

### Main Idea & Intuition

To determine if a graph is a valid tree, we need to check two conditions:

1. **The graph is fully connected** - There is exactly one connected component
2. **The graph has no cycles** - There are no loops

There are several approaches to solve this problem:

1. **Union-Find (Disjoint Set)**: Check if adding each edge creates a cycle
2. **Depth-First Search (DFS)**: Check for connectivity and cycles
3. **Breadth-First Search (BFS)**: Similar to DFS, but using a queue

A key insight is that for a graph with n nodes to be a tree, it must have exactly n-1 edges. This is a necessary but not sufficient condition (we still need to check connectivity).

### Solution 1: Union-Find Approach

```java
class Solution {
    public boolean validTree(int n, int[][] edges) {
        // For a graph to be a valid tree:
        // 1. It must have exactly n-1 edges
        // 2. It must be fully connected (no disjoint components)
        
        // Check edge count
        if (edges.length != n - 1) {
            return false;
        }
        
        // Initialize union-find data structure
        int[] parent = new int[n];
        
        // Initially, each node is its own parent
        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }
        
        // Process each edge
        for (int[] edge : edges) {
            int x = find(parent, edge[0]);
            int y = find(parent, edge[1]);
            
            // If nodes are already in the same set, we have a cycle
            if (x == y) {
                return false;
            }
            
            // Union the two sets
            parent[x] = y;
        }
        
        // If we've processed all edges without finding a cycle,
        // and we have exactly n-1 edges, the graph is a valid tree
        return true;
    }
    
    // Find operation with path compression
    private int find(int[] parent, int x) {
        if (parent[x] != x) {
            parent[x] = find(parent, parent[x]);
        }
        return parent[x];
    }
}
```

### Solution 2: DFS Approach

```java
class Solution {
    public boolean validTree(int n, int[][] edges) {
        // For a graph to be a valid tree:
        // 1. It must have exactly n-1 edges
        // 2. It must be fully connected
        
        // Check edge count
        if (edges.length != n - 1) {
            return false;
        }
        
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
        
        // Use DFS to check if the graph is fully connected
        boolean[] visited = new boolean[n];
        dfs(adjacencyList, visited, 0);
        
        // Check if all nodes were visited
        for (boolean v : visited) {
            if (!v) {
                return false;
            }
        }
        
        // If we have n-1 edges and all nodes are connected, it's a valid tree
        return true;
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

### Solution 3: BFS Approach

```java
class Solution {
    public boolean validTree(int n, int[][] edges) {
        // For a graph to be a valid tree:
        // 1. It must have exactly n-1 edges
        // 2. It must be fully connected
        
        // Check edge count
        if (edges.length != n - 1) {
            return false;
        }
        
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
        
        // Use BFS to check if the graph is fully connected
        boolean[] visited = new boolean[n];
        Queue<Integer> queue = new LinkedList<>();
        
        // Start BFS from node 0
        queue.offer(0);
        visited[0] = true;
        
        while (!queue.isEmpty()) {
            int node = queue.poll();
            
            for (int neighbor : adjacencyList.get(node)) {
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    queue.offer(neighbor);
                }
            }
        }
        
        // Check if all nodes were visited
        for (boolean v : visited) {
            if (!v) {
                return false;
            }
        }
        
        // If we have n-1 edges and all nodes are connected, it's a valid tree
        return true;
    }
}
```

### Understanding the Solutions

#### Union-Find Approach:
1. We first check if the graph has exactly n-1 edges (necessary for a tree)
2. We use Union-Find to detect cycles:
   - If two nodes are already in the same set, adding an edge between them creates a cycle
   - Otherwise, we union the two sets
3. If we process all edges without finding a cycle, the graph is a valid tree

#### DFS/BFS Approach:
1. We first check if the graph has exactly n-1 edges
2. We build an adjacency list to represent the graph
3. We use DFS or BFS to traverse the graph from any node (e.g., node 0)
4. After traversal, we check if all nodes were visited:
   - If all nodes were visited, the graph is fully connected
   - If some nodes were not visited, the graph has disconnected components
5. If the graph has n-1 edges and is fully connected, it's a valid tree

### Key Insights

1. **Tree Properties**: A tree with n nodes must have exactly n-1 edges
2. **Cycle Detection**: Union-Find is efficient for detecting cycles in undirected graphs
3. **Connectivity Check**: DFS/BFS can verify if all nodes are reachable
4. **Early Termination**: Checking the edge count first allows for quick rejection

### Complexity Analysis

#### Union-Find Approach:
- **Time Complexity**: `O(E * α(n))` where E is the number of edges and α(n) is the inverse Ackermann function (nearly constant)
- **Space Complexity**: `O(n)` for the parent array

#### DFS/BFS Approach:
- **Time Complexity**: `O(n + E)` where n is the number of nodes and E is the number of edges
- **Space Complexity**: `O(n + E)` for the adjacency list, visited array, and recursion stack/queue

### Which Approach to Choose?

- **Union-Find**: More intuitive for cycle detection and simpler to implement
- **DFS/BFS**: More versatile and can be extended to solve other graph problems
- Both approaches are efficient, but Union-Find might be slightly faster for this specific problem
