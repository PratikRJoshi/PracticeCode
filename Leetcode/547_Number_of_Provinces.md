# 547. Number of Provinces

## Problem Description

[Number of Provinces](https://leetcode.com/problems/number-of-provinces/)

There are `n` cities. Some are connected (directly or transitively). A **province** is a group of directly or indirectly connected cities. You are given an `n x n` matrix `isConnected` where `isConnected[i][j] == 1` means city `i` and city `j` are directly connected. Return the total number of provinces.

### Example 1

Input: `isConnected = [[1,1,0],[1,1,0],[0,0,1]]`

Output: `2`

### Example 2

Input: `isConnected = [[1,0,0],[0,1,0],[0,0,1]]`

Output: `3`

### Constraints

- `1 <= n <= 200`, `isConnected[i][j]` is `0` or `1`, `isConnected[i][i] == 1`, symmetric.

## Intuition / Main Idea

A province is a **connected component**. Union-Find (Disjoint Set Union) merges connected cities under a common "captain" (root); the number of distinct captains is the number of provinces.

### Build the intuition step by step

1. Start with each city as its own captain: `parent[i] = i`.
2. For every connected pair `(i, j)`, `union` them — they share a captain.
3. Since the matrix is symmetric, only scan the **upper triangle** (`j = i + 1`).
4. Count cities that are their own captain (`find(i) == i`) — one per province.

### Why this works

Union-Find maintains the invariant that all members of a component point (eventually) to the same root. With **path compression** + **union by rank**, each operation is near-constant (`α(n)`). The roots are exactly the component representatives, so counting self-parents counts components.

## Code Mapping

| Problem Requirement (@) | Java Code Section |
|---|---|
| Merge directly connected cities | `if (isConnected[i][j] == 1) dsu.union(i, j);` |
| Avoid duplicate work (symmetry) | inner loop `j = i + 1` |
| Count provinces | `if (dsu.find(i) == i) provinces++;` |
| Near-O(1) ops | path compression in `find`, union by rank |

## Final Java Code & Learning Pattern

```java
// [Pattern: Union-Find (DSU) for connected components]
class Solution {
    public int findCircleNum(int[][] isConnected) {
        int n = isConnected.length;
        DSU dsu = new DSU(n);

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {   // symmetric -> upper triangle only
                if (isConnected[i][j] == 1) {
                    dsu.union(i, j);
                }
            }
        }

        int provinces = 0;
        for (int i = 0; i < n; i++) {
            if (dsu.find(i) == i) {             // self-captain => one province
                provinces++;
            }
        }
        return provinces;
    }

    private static class DSU {
        int[] parent, rank;

        DSU(int n) {
            parent = new int[n];
            rank = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;                  // each node is its own captain
                rank[i] = 1;
            }
        }

        int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]);    // path compression
            }
            return parent[x];
        }

        void union(int a, int b) {
            int ra = find(a), rb = find(b);
            if (ra == rb) return;
            if (rank[ra] < rank[rb]) {          // attach smaller tree under larger
                int t = ra; ra = rb; rb = t;
            }
            parent[rb] = ra;
            if (rank[ra] == rank[rb]) rank[ra]++;
        }
    }
}
```

### Why each part exists

- **`parent[i] = i` init** — every city starts as its own province.
- **Path compression** — flattens the tree on `find`, so future lookups are fast.
- **Union by rank** — keeps trees shallow; compare ranks **at the roots**, not arbitrary nodes.
- **`find(i) == i` count** — each self-pointing root is one province.

## Complexity Analysis

- **Time Complexity:** $O(n^2 \cdot \alpha(n)) \approx O(n^2)$ — scan the matrix once; DSU ops are near-constant.
- **Space Complexity:** $O(n)$ for `parent`/`rank`.

## Similar Problems

1. [LeetCode 1319. Number of Operations to Make Network Connected](https://leetcode.com/problems/number-of-operations-to-make-network-connected/) — count components, then spare-edge accounting.
2. [LeetCode 684. Redundant Connection](https://leetcode.com/problems/redundant-connection/) — DSU cycle detection.
3. [LeetCode 200. Number of Islands](https://leetcode.com/problems/number-of-islands/) — connected components via DFS/BFS or DSU.
