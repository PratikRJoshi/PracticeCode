### 684. Redundant Connection
### Problem Link: [Redundant Connection](https://leetcode.com/problems/redundant-connection/)

### Intuition/Main Idea
This problem asks us to find an edge that can be removed to make a graph into a tree. In a tree with n nodes, there should be exactly n-1 edges. Since the input has n edges for n nodes, there is exactly one redundant edge. The key insight is to use a Union-Find (Disjoint Set) data structure to detect cycles. We process edges one by one, and when we find an edge that connects two nodes that are already in the same set (connected), that edge creates a cycle and is the redundant connection.

The problem also specifies that if there are multiple answers, we should return the edge that appears last in the input. This is automatically handled by processing edges in order.

### Code Mapping

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Process edges in order | `for (int[] edge : edges)` |
| Detect if nodes are already connected | `if (find(parent, edge[0]) == find(parent, edge[1]))` |
| Union operation to connect nodes | `union(parent, edge[0], edge[1])` |
| Return the redundant edge | `return edge;` |
| Initialize parent array | `int[] parent = new int[n + 1];` |

### Final Java Code & Learning Pattern

```java
// [Pattern: Union-Find (Disjoint Set)]
class Solution {
    public int[] findRedundantConnection(int[][] edges) {
        int n = edges.length;
        // Initialize parent array for Union-Find
        int[] parent = new int[n + 1];
        
        // Initially, each node is its own parent
        for (int i = 1; i <= n; i++) {
            parent[i] = i;
        }
        
        // Process each edge
        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            
            // If nodes are already in the same set, this edge creates a cycle
            if (find(parent, u) == find(parent, v)) {
                return edge; // This is the redundant connection
            }
            
            // Otherwise, union the two sets
            union(parent, u, v);
        }
        
        return new int[0]; // Should never reach here given the problem constraints
    }
    
    // Find operation with path compression
    private int find(int[] parent, int x) {
        if (parent[x] != x) {
            parent[x] = find(parent, parent[x]); // Path compression
        }
        return parent[x];
    }
    
    // Union operation
    private void union(int[] parent, int x, int y) {
        parent[find(parent, x)] = find(parent, y);
    }
}
```

### Alternative Implementation with Union by Rank

```java
// [Pattern: Union-Find with Union by Rank]
class Solution {
    public int[] findRedundantConnection(int[][] edges) {
        int n = edges.length;
        int[] parent = new int[n + 1];
        int[] rank = new int[n + 1];
        
        // Initialize each node as its own parent with rank 0
        for (int i = 1; i <= n; i++) {
            parent[i] = i;
            rank[i] = 0;
        }
        
        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            
            if (find(parent, u) == find(parent, v)) {
                return edge;
            }
            
            unionByRank(parent, rank, u, v);
        }
        
        return new int[0];
    }
    
    private int find(int[] parent, int x) {
        if (parent[x] != x) {
            parent[x] = find(parent, parent[x]);
        }
        return parent[x];
    }
    
    private void unionByRank(int[] parent, int[] rank, int x, int y) {
        int rootX = find(parent, x);
        int rootY = find(parent, y);
        
        if (rootX == rootY) return;
        
        if (rank[rootX] < rank[rootY]) {
            parent[rootX] = rootY;
        } else if (rank[rootX] > rank[rootY]) {
            parent[rootY] = rootX;
        } else {
            parent[rootY] = rootX;
            rank[rootX]++;
        }
    }
}
```

### Complexity Analysis
- **Time Complexity**: $O(n \times \alpha(n))$ where n is the number of nodes and $\alpha(n)$ is the inverse Ackermann function, which is nearly constant for all practical values of n. This is because each find and union operation takes nearly constant time with path compression and union by rank.
- **Space Complexity**: $O(n)$ for storing the parent array and, in the second implementation, the rank array.

### Similar Problems
1. **LeetCode 685: Redundant Connection II** - A more complex version with directed edges.
2. **LeetCode 261: Graph Valid Tree** - Determine if an undirected graph is a valid tree.
3. **LeetCode 323: Number of Connected Components in an Undirected Graph** - Count connected components using Union-Find.
4. **LeetCode 547: Number of Provinces** - Count connected components in a graph represented as an adjacency matrix.
5. **LeetCode 1319: Number of Operations to Make Network Connected** - Determine minimum operations to connect all computers.
6. **LeetCode 990: Satisfiability of Equality Equations** - Check if a set of equations is satisfiable.
7. **LeetCode 1202: Smallest String With Swaps** - Use Union-Find to group characters that can be swapped.
8. **LeetCode 128: Longest Consecutive Sequence** - Can be solved using Union-Find to group consecutive numbers.
