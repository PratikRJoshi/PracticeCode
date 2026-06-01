# 1319. Number of Operations to Make Network Connected

## Problem Description

[Number of Operations to Make Network Connected](https://leetcode.com/problems/number-of-operations-to-make-network-connected/)

There are `n` computers numbered `0` to `n - 1` connected by ethernet cables `connections`, where `connections[i] = [a, b]` connects computers `a` and `b`. Any computer can reach any other directly or indirectly through the network.

You can extract a cable between two directly connected computers and place it between any pair of disconnected computers. Return the **minimum** number of such moves to make all computers connected, or `-1` if it is impossible.

### Example 1

Input: `n = 4, connections = [[0,1],[0,2],[1,2]]`

Output: `1`

### Example 2

Input: `n = 6, connections = [[0,1],[0,2],[0,3],[1,2],[1,3]]`

Output: `2`

### Example 3

Input: `n = 6, connections = [[0,1],[0,2],[0,3],[1,2]]`

Output: `-1`

### Constraints

- `1 <= n <= 10^5`, `1 <= connections.length <= min(n*(n-1)/2, 10^5)`, no duplicate connections, no self-loops.

## Intuition / Main Idea

Connecting `n` computers requires at least `n - 1` cables. If there are fewer cables than that, it's impossible. Otherwise, the answer is purely about **how many components** exist: merging `c` components into one needs exactly `c - 1` rewires, and there are always enough spare (cycle) cables to do it.

### Build the intuition step by step

1. **Feasibility check:** if `connections.length < n - 1`, return `-1` — not enough cable to ever connect `n` nodes.
2. Union all given connections with DSU.
3. Count the resulting components (roots).
4. Answer = `components - 1`: each move merges two components into one.

### Why this works

Any spanning structure of `n` nodes needs `n - 1` edges. If we have at least that many, every "extra" edge inside a component is a cycle edge we can freely relocate. To unify `c` components we need `c - 1` relocations, and the early `n - 1` check guarantees enough spares exist.

## Code Mapping

| Problem Requirement (@) | Java Code Section |
|---|---|
| Impossible if too few cables | `if (connections.length < n - 1) return -1;` |
| Merge connected computers | `for (int[] c : connections) dsu.union(c[0], c[1]);` |
| Count components (incl. isolated nodes) | loop all `i` in `[0, n)`, count `find(i) == i` |
| Moves needed | `return components - 1;` |

## Final Java Code & Learning Pattern

```java
// [Pattern: Union-Find component counting + spare-edge accounting]
class Solution {
    public int makeConnected(int n, int[][] connections) {
        if (connections.length < n - 1) {
            return -1;                          // not enough cable for a spanning tree
        }

        DSU dsu = new DSU(n);
        for (int[] c : connections) {
            dsu.union(c[0], c[1]);              // union handles same-root via early return
        }

        int components = 0;
        for (int i = 0; i < n; i++) {           // must scan ALL nodes (isolated ones too)
            if (dsu.find(i) == i) {
                components++;
            }
        }
        return components - 1;                  // c components -> c - 1 merges
    }

    private static class DSU {
        int[] parent, rank;

        DSU(int n) {
            parent = new int[n];
            rank = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
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
            if (ra == rb) return;               // already connected; this is a spare edge
            if (rank[ra] < rank[rb]) {
                int t = ra; ra = rb; rb = t;
            }
            parent[rb] = ra;
            if (rank[ra] == rank[rb]) rank[ra]++;
        }
    }
}
```

### Why each part exists

- **Early `n - 1` check** — the only way the task is impossible; also guarantees enough spare edges afterward.
- **`union` early return on same root** — spare/cycle edges are silently ignored; no explicit `find(u) == find(v)` needed in the caller.
- **Scan all `n` nodes when counting** — isolated computers never appear in `connections`; missing them would undercount components.
- **`components - 1`** — each relocation reduces component count by exactly one.

## Complexity Analysis

- **Time Complexity:** $O((n + m) \cdot \alpha(n)) \approx O(n + m)$, where `m = connections.length`.
- **Space Complexity:** $O(n)$.

## Similar Problems

1. [LeetCode 547. Number of Provinces](https://leetcode.com/problems/number-of-provinces/) — pure component counting with DSU.
2. [LeetCode 684. Redundant Connection](https://leetcode.com/problems/redundant-connection/) — detect the spare (cycle) edge with DSU.
3. [LeetCode 261. Graph Valid Tree](https://leetcode.com/problems/graph-valid-tree/) — exactly `n - 1` edges and fully connected.
