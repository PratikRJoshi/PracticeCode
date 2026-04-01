# Salesforce Field Service Management

## 1) Problem Description

Given an undirected weighted graph with HQ at node `0`, and up to `k <= 10` client nodes.

Find minimum travel time to:
1. start at `0`
2. visit all clients in any order
3. return to `0`

If impossible, return `-1`.

## 2) Intuition/Main Idea

This is TSP on a small set of important nodes, but distances come from a large sparse graph.

So solve in two phases:

1. **Shortest paths between important nodes**
   - Important nodes = `[0] + clients` (size `t = k + 1 <= 11`)
   - Run Dijkstra from each important node over full graph.
   - Build `t x t` distance matrix.

2. **Bitmask DP (TSP style)**
   - `dp[mask][last]` = minimum cost to start at HQ, visit client subset `mask`, and end at important node index `last`.
   - Transition by adding one unvisited client.
   - Final answer adds return edge to HQ.

### Why this intuition works

- Global graph complexity is handled by Dijkstra.
- Visit-order complexity is handled by small-state DP because `k <= 10`.

### How to derive it step by step

1. Build adjacency list.
2. Collect important nodes.
3. Run Dijkstra from each important node.
4. If any client unreachable from HQ, answer `-1`.
5. Run subset DP over client masks.
6. Close tour by returning to HQ.

## 3) Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
| --- | --- |
| @ShortestPathsInWeightedGraph | repeated Dijkstra from important nodes |
| @VisitAllClientsAnyOrder | bitmask DP over client subsets |
| @ReturnToHQ | final transition adds distance back to node `0` |
| @ImpossibleCaseReturnMinusOne | unreachable checks in distance matrix |

## 4) Final Java Code & Learning Pattern (Full Content)

```java
import java.util.*;

class Result {

    private static final long INF = Long.MAX_VALUE / 4;

    public static long getMinimumServiceTime(
            int connectionNodes,
            List<Integer> connectionFrom,
            List<Integer> connectionTo,
            List<Integer> connectionWeight,
            List<Integer> clients
    ) {
        List<List<long[]>> graph = new ArrayList<>();
        for (int i = 0; i < connectionNodes; i++) {
            graph.add(new ArrayList<>());
        }

        for (int i = 0; i < connectionFrom.size(); i++) {
            int u = connectionFrom.get(i);
            int v = connectionTo.get(i);
            int w = connectionWeight.get(i);
            graph.get(u).add(new long[]{v, w});
            graph.get(v).add(new long[]{u, w});
        }

        int k = clients.size();
        int t = k + 1;
        int[] important = new int[t];
        important[0] = 0;
        for (int i = 0; i < k; i++) {
            important[i + 1] = clients.get(i);
        }

        long[][] dist = new long[t][t];
        for (int i = 0; i < t; i++) {
            long[] dijkstraDist = dijkstra(important[i], graph);
            for (int j = 0; j < t; j++) {
                dist[i][j] = dijkstraDist[important[j]];
            }
        }

        for (int i = 1; i < t; i++) {
            if (dist[0][i] >= INF) {
                return -1;
            }
        }

        int fullMask = 1 << k;
        long[][] dp = new long[fullMask][t];
        for (int mask = 0; mask < fullMask; mask++) {
            Arrays.fill(dp[mask], INF);
        }
        dp[0][0] = 0;

        for (int mask = 0; mask < fullMask; mask++) {
            for (int last = 0; last < t; last++) {
                long current = dp[mask][last];
                if (current >= INF) {
                    continue;
                }

                for (int clientIndex = 0; clientIndex < k; clientIndex++) {
                    int bit = 1 << clientIndex;
                    if ((mask & bit) != 0) {
                        continue;
                    }

                    int nextNodeIndex = clientIndex + 1;
                    long edgeCost = dist[last][nextNodeIndex];
                    if (edgeCost >= INF) {
                        continue;
                    }

                    int nextMask = mask | bit;
                    dp[nextMask][nextNodeIndex] = Math.min(
                            dp[nextMask][nextNodeIndex],
                            current + edgeCost
                    );
                }
            }
        }

        long answer = INF;
        int allVisited = fullMask - 1;

        for (int last = 0; last < t; last++) {
            if (dp[allVisited][last] >= INF || dist[last][0] >= INF) {
                continue;
            }
            answer = Math.min(answer, dp[allVisited][last] + dist[last][0]);
        }

        return answer >= INF ? -1 : answer;
    }

    private static long[] dijkstra(int source, List<List<long[]>> graph) {
        int n = graph.size();
        long[] distance = new long[n];
        Arrays.fill(distance, INF);
        distance[source] = 0;

        PriorityQueue<long[]> priorityQueue = new PriorityQueue<>(Comparator.comparingLong(a -> a[1]));
        priorityQueue.offer(new long[]{source, 0});

        while (!priorityQueue.isEmpty()) {
            long[] current = priorityQueue.poll();
            int node = (int) current[0];
            long currentDistance = current[1];

            if (currentDistance != distance[node]) {
                continue;
            }

            for (long[] edge : graph.get(node)) {
                int next = (int) edge[0];
                long weight = edge[1];
                long candidate = currentDistance + weight;
                if (candidate < distance[next]) {
                    distance[next] = candidate;
                    priorityQueue.offer(new long[]{next, candidate});
                }
            }
        }

        return distance;
    }
}
```

Learning Pattern:
- For graph problems with small required-visit set, reduce to metric closure (pairwise shortest paths), then run subset DP.

## 5) Complexity Analysis

- Let `n` nodes, `m` edges, `k` clients (`k <= 10`).
- Dijkstra phase: `O((k+1) * (m log n))`
- DP phase: `O(2^k * k^2)`
- Space: `O(n + m + 2^k * k)`

## Similar Problems

- [LeetCode 847: Shortest Path Visiting All Nodes](https://leetcode.com/problems/shortest-path-visiting-all-nodes/) (state-space visit-all pattern)
- [LeetCode 1548: The Most Similar Path in a Graph](https://leetcode.com/problems/the-most-similar-path-in-a-graph/) (graph + DP flavor)