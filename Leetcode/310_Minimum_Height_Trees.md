# Minimum Height Trees

## Problem Description

**Problem Link:** [Minimum Height Trees](https://leetcode.com/problems/minimum-height-trees/)

A tree is an undirected graph in which any two vertices are connected by exactly one path. In other words, any connected graph without simple cycles is a tree.

Given a tree of `n` nodes labelled from `0` to `n - 1`, and an array of `n - 1` `edges` where `edges[i] = [ai, bi]` indicates that there is an undirected edge between nodes `ai` and `bi` in the tree.

You can choose any node of the tree as the root. When you select a node `x` as the root, the result tree has height `h`. Among all possible rooted trees, those with minimum height (min `h`) are called **minimum height trees** (MHTs).

Return *a list of all **MHTs'** root labels*. You can return the answer in **any order**.

The **height** of a rooted tree is the number of edges on the longest downward path between the root and a leaf.

**Example 1:**
```
Input: n = 4, edges = [[1,0],[1,2],[1,3]]
Output: [1]
Explanation: As shown, the height of the tree when root is 1 is 1, which is minimum.
```

**Constraints:**
- `1 <= n <= 2 * 10^4`
- `edges.length == n - 1`
- `0 <= ai, bi < n`

## Intuition/Main Idea

The MHT roots are the nodes at the center of the tree (at most 2 nodes). We can find them by repeatedly removing leaf nodes until 1-2 nodes remain.

**Core Algorithm:**
- Use topological sort approach
- Repeatedly remove leaf nodes (degree 1)
- Continue until 1-2 nodes remain
- These are the MHT roots

**Why leaf removal:** The center nodes have the minimum height. Removing leaves iteratively converges to the center.

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|------------------------|-----------------------------------|
| Find MHT roots | Leaf removal - Lines 8-30 |
| Remove leaves iteratively | BFS with degree tracking - Lines 15-28 |
| Track degrees | Degree array - Lines 10-13 |

## Final Java Code & Learning Pattern (Full Content)

```java
class Solution {
    public List<Integer> findMinHeightTrees(int n, int[][] edges) {
        if (n == 1) {
            return Arrays.asList(0);
        }
        
        // Build adjacency list and degree array
        List<List<Integer>> graph = new ArrayList<>();
        int[] degree = new int[n];
        
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }
        
        for (int[] edge : edges) {
            graph.get(edge[0]).add(edge[1]);
            graph.get(edge[1]).add(edge[0]);
            degree[edge[0]]++;
            degree[edge[1]]++;
        }
        
        // Queue for leaf nodes (degree == 1)
        Queue<Integer> leaves = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            if (degree[i] == 1) {
                leaves.offer(i);
            }
        }
        
        // Remove leaves iteratively until 1-2 nodes remain
        int remainingNodes = n;
        while (remainingNodes > 2) {
            int leavesCount = leaves.size();
            remainingNodes -= leavesCount;
            
            // Remove current leaves
            for (int i = 0; i < leavesCount; i++) {
                int leaf = leaves.poll();
                
                // Update neighbors
                for (int neighbor : graph.get(leaf)) {
                    degree[neighbor]--;
                    if (degree[neighbor] == 1) {
                        leaves.offer(neighbor);
                    }
                }
            }
        }
        
        // Remaining nodes are MHT roots
        return new ArrayList<>(leaves);
    }
}
```

## Complexity Analysis

**Time Complexity:** $O(n)$ where $n$ is the number of nodes. We visit each node and edge once.

**Space Complexity:** $O(n)$ for the graph and queue.

## Similar Problems

- [Course Schedule](https://leetcode.com/problems/course-schedule/) - Topological sort
- [Alien Dictionary](https://leetcode.com/problems/alien-dictionary/) - Topological ordering
- [Network Delay Time](https://leetcode.com/problems/network-delay-time/) - Graph traversal

