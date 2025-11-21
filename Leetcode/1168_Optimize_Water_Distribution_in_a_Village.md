# Optimize Water Distribution in a Village

## Problem Description

**Problem Link:** [Optimize Water Distribution in a Village](https://leetcode.com/problems/optimize-water-distribution-in-a-village/)

There are `n` houses in a village. We want to supply water for all the houses by building wells and laying pipes.

For each house `i`, we can either build a well inside it directly with cost `wells[i - 1]` (note the `-1` due to **0-indexing**), or pipe in water from another well to it. The costs to lay pipes between houses are given by the array `pipes` where each `pipes[j] = [house1j, house2j, costj]` represents the cost to connect house1j and house2j together using a pipe.

Return *the minimum total cost to supply water to all houses*.

**Example 1:**
```
Input: n = 3, wells = [1,2,2], pipes = [[1,2,1],[2,3,1]]
Output: 3
Explanation: The best strategy is to build a well in the first house with cost 1 and connect the other houses to it with cost 2 so the total cost is 3.
```

**Constraints:**
- `2 <= n <= 10^4`
- `wells.length == n`
- `0 <= wells[i] <= 10^5`
- `1 <= pipes.length <= 10^4`

## Intuition/Main Idea

This is a minimum spanning tree (MST) problem. We need to connect all houses with minimum cost, where we can either build a well or connect via pipes.

**Core Algorithm:**
- Treat building a well as connecting to a virtual node (node 0) with cost = well cost
- Use Kruskal's algorithm (Union-Find) to find MST
- Connect all nodes with minimum total cost

**Why MST:** We need to connect all houses with minimum cost. This is exactly MST problem.

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|------------------------|-----------------------------------|
| Find minimum cost | Kruskal's MST - Lines 8-45 |
| Handle wells | Virtual node edges - Lines 12-15 |
| Union-Find | Find and Union - Lines 47-62 |

## Final Java Code & Learning Pattern (Full Content)

```java
class Solution {
    public int minCostToSupplyWater(int n, int[] wells, int[][] pipes) {
        // Create edges: treat wells as edges from virtual node 0
        List<int[]> edges = new ArrayList<>();
        
        // Add well costs as edges from node 0
        for (int i = 0; i < n; i++) {
            edges.add(new int[]{0, i + 1, wells[i]});
        }
        
        // Add pipe edges
        for (int[] pipe : pipes) {
            edges.add(pipe);
        }
        
        // Sort edges by cost
        edges.sort((a, b) -> a[2] - b[2]);
        
        // Union-Find for MST
        int[] parent = new int[n + 1];
        for (int i = 0; i <= n; i++) {
            parent[i] = i;
        }
        
        int totalCost = 0;
        int edgesUsed = 0;
        
        // Kruskal's algorithm
        for (int[] edge : edges) {
            int u = edge[0], v = edge[1], cost = edge[2];
            
            int rootU = find(u, parent);
            int rootV = find(v, parent);
            
            // If not connected, add edge to MST
            if (rootU != rootV) {
                parent[rootU] = rootV;
                totalCost += cost;
                edgesUsed++;
                
                // MST has n edges for n+1 nodes (including virtual node)
                if (edgesUsed == n) {
                    break;
                }
            }
        }
        
        return totalCost;
    }
    
    private int find(int x, int[] parent) {
        if (parent[x] != x) {
            parent[x] = find(parent[x], parent); // Path compression
        }
        return parent[x];
    }
}
```

## Complexity Analysis

**Time Complexity:** $O(E \log E)$ where $E$ is total edges (wells + pipes) for sorting.

**Space Complexity:** $O(n)$ for Union-Find.

## Similar Problems

- [Connecting Cities With Minimum Cost](https://leetcode.com/problems/connecting-cities-with-minimum-cost/) - Similar MST problem
- [Min Cost to Connect All Points](https://leetcode.com/problems/min-cost-to-connect-all-points/) - MST with points
- [Accounts Merge](https://leetcode.com/problems/accounts-merge/) - Union-Find application

