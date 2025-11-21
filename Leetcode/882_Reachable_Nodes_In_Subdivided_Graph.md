# Reachable Nodes In Subdivided Graph

## Problem Description

**Problem Link:** [Reachable Nodes In Subdivided Graph](https://leetcode.com/problems/reachable-nodes-in-subdivided-graph/)

You are given an undirected graph (the **original graph**) with `n` nodes labeled from `0` to `n - 1`. You decide to **subdivide** each edge in the graph into a chain of nodes, with the number of new nodes varying between each edge.

The graph is given as a 2D array of `edges` where `edges[i] = [ui, vi, cnti]` indicates that there is an edge between nodes `ui` and `vi` in the original graph, and `cnti` is the total number of new nodes that you will **subdivide** the edge into. Note that `cnti == 0` means you will not subdivide the edge.

**Subdividing** an edge means adding new nodes on the edge, turning the original edge into a path. For example, if you have an edge `[0, 1]` with `cnti = 2`, and you subdivide it, you will have `0 -> n -> n+1 -> 1`.

After subdividing all edges in the original graph, you will get a new graph with `n + sum(cnti)` nodes.

You are also given an integer `maxMoves` and `startNode`. You want to know how many nodes you can reach from `startNode` in the new graph in **at most** `maxMoves` moves.

Return *the number of nodes reachable from* `startNode` *in the new graph*.

**Example 1:**
```
Input: n = 3, edges = [[0,1,10],[0,2,1],[1,2,2]], maxMoves = 6, startNode = 0
Output: 13
```

**Constraints:**
- `0 <= edges.length <= min(n * (n - 1) / 2, 10^4)`
- `edges[i].length == 3`

## Intuition/Main Idea

We need to count reachable nodes in subdivided graph within maxMoves. Use Dijkstra to find shortest distances, then count nodes reachable within maxMoves.

**Core Algorithm:**
- Use Dijkstra to find shortest distance from startNode to all original nodes
- For each edge, count subdivided nodes reachable from both ends
- Sum original nodes + subdivided nodes reachable

**Why Dijkstra:** We need shortest distances to determine which nodes are reachable within maxMoves.

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|------------------------|-----------------------------------|
| Find reachable nodes | Dijkstra + counting - Lines 8-50 |
| Count subdivided nodes | Edge traversal - Lines 35-48 |
| Track distances | Distance array - Lines 10-12 |

## Final Java Code & Learning Pattern (Full Content)

```java
class Solution {
    public int reachableNodes(int[][] edges, int maxMoves, int n) {
        // Build adjacency list
        List<int[]>[] graph = new List[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int[] edge : edges) {
            graph[edge[0]].add(new int[]{edge[1], edge[2] + 1});
            graph[edge[1]].add(new int[]{edge[0], edge[2] + 1});
        }
        
        // Dijkstra to find shortest distances
        int[] dist = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[0] = 0;
        
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[1] - b[1]);
        pq.offer(new int[]{0, 0});
        
        while (!pq.isEmpty()) {
            int[] current = pq.poll();
            int node = current[0];
            int moves = current[1];
            
            if (moves > dist[node]) continue;
            
            for (int[] neighbor : graph[node]) {
                int next = neighbor[0];
                int weight = neighbor[1];
                int newMoves = moves + weight;
                
                if (newMoves < dist[next]) {
                    dist[next] = newMoves;
                    pq.offer(new int[]{next, newMoves});
                }
            }
        }
        
        // Count reachable nodes
        int result = 0;
        
        // Count original nodes reachable
        for (int d : dist) {
            if (d <= maxMoves) {
                result++;
            }
        }
        
        // Count subdivided nodes on edges
        for (int[] edge : edges) {
            int u = edge[0], v = edge[1], cnt = edge[2];
            int fromU = Math.max(0, maxMoves - dist[u]);
            int fromV = Math.max(0, maxMoves - dist[v]);
            result += Math.min(cnt, fromU + fromV);
        }
        
        return result;
    }
}
```

## Complexity Analysis

**Time Complexity:** $O(E \log V)$ where $E$ is edges and $V$ is vertices for Dijkstra.

**Space Complexity:** $O(V + E)$ for graph and distance array.

## Similar Problems

- [Network Delay Time](https://leetcode.com/problems/network-delay-time/) - Dijkstra's algorithm
- [Path With Minimum Effort](https://leetcode.com/problems/path-with-minimum-effort/) - Similar shortest path
- [Cheapest Flights Within K Stops](https://leetcode.com/problems/cheapest-flights-within-k-stops/) - Shortest path with constraints

