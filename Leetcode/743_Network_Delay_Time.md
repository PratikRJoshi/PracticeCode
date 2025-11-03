### 743. Network Delay Time
Problem: https://leetcode.com/problems/network-delay-time/

---

### Main Idea: Dijkstra's Single-Source Shortest Path

This problem asks for the minimum time required for a signal to propagate from a starting node `k` to all other nodes in a network. This is a classic **single-source shortest path** problem on a weighted, directed graph.

1.  **Model:** The network is a graph where nodes are routers and edges have weights equal to the travel time.
2.  **Algorithm Choice:** Since edge weights are positive, **Dijkstra's algorithm** is the ideal choice. It efficiently finds the shortest path from a source to all other nodes.
3.  **Strategy:**
    *   We use a `dist` array to keep track of the shortest time found so far from `k` to every other node, initialized to infinity.
    *   A **Priority Queue** is used to always process the node that is currently closest to the source `k`. This greedy approach ensures we find the shortest paths.
    *   We start with node `k` (distance 0). We iteratively pull the closest node from the queue and "relax" its neighbors: if we find a shorter path to a neighbor through the current node, we update its distance and add it to the queue.
4.  **Final Result:** After the algorithm runs, the answer is the **maximum time** in our `dist` array. If any node is unreachable (its distance is still infinity), we return -1.

---

### Requirement → Code Mapping

| Tag | Requirement | Where Implemented |
|---|---|---|
| R1 | Model the weighted, directed graph | `graph.get(u).add(new int[]{v, w});` |
| R2 | Track shortest distance from `k` to all nodes | `int[] dist = new int[n + 1];` |
| R3 | Use a priority queue to always explore the closest node first | `PriorityQueue<int[]> pq = new PriorityQueue<>(...);` |
| R4 | Update a neighbor's distance if a shorter path is found (Relaxation) | `if (dist[u] + w < dist[v]) { ... }` |
| R5 | Find the time to reach the farthest node | `int maxDelay = 0; ... maxDelay = Math.max(maxDelay, dist[i]);` |
| R6 | Handle unreachable nodes | `if (dist[i] == Integer.MAX_VALUE) return -1;` |

---

### Dijkstra's Algorithm Solution (Clearer Variable Names)

```java
import java.util.*;

class Solution {
    public int networkDelayTime(int[][] times, int n, int k) {
        // Build the graph: Map<source, List<[destination, time]>>
        Map<Integer, List<int[]>> graph = new HashMap<>();
        for (int[] edge : times) {
            int source = edge[0];
            int destination = edge[1];
            int time = edge[2];
            graph.computeIfAbsent(source, key -> new ArrayList<>()).add(new int[]{destination, time});
        }

        // Stores the minimum time from startNode k to every other node
        int[] minTimeToReachNode = new int[n + 1];
        Arrays.fill(minTimeToReachNode, Integer.MAX_VALUE);
        minTimeToReachNode[k] = 0;

        // Priority Queue stores {node, time_from_k}, sorted by time
        PriorityQueue<int[]> priorityQueue = new PriorityQueue<>((a, b) -> a[1] - b[1]);
        priorityQueue.offer(new int[]{k, 0});

        while (!priorityQueue.isEmpty()) {
            int[] currentNodeInfo = priorityQueue.poll();
            int currentNode = currentNodeInfo[0];
            int timeToCurrentNode = currentNodeInfo[1];

            // If we've already found a shorter path to this node, skip
            if (timeToCurrentNode > minTimeToReachNode[currentNode]) {
                continue;
            }

            if (!graph.containsKey(currentNode)) {
                continue;
            }

            // For each neighbor, check if we can find a shorter path
            for (int[] neighborEdge : graph.get(currentNode)) {
                int neighborNode = neighborEdge[0];
                int timeToNeighbor = neighborEdge[1];
                
                int newTime = timeToCurrentNode + timeToNeighbor;

                if (newTime < minTimeToReachNode[neighborNode]) {
                    // Found a shorter path, update it
                    minTimeToReachNode[neighborNode] = newTime;
                    priorityQueue.offer(new int[]{neighborNode, newTime});
                }
            }
        }

        // The result is the time it takes to reach the FARTHEST node
        int maxTime = 0;
        for (int i = 1; i <= n; i++) {
            int time = minTimeToReachNode[i];
            // If any node is unreachable, its time will be infinity
            if (time == Integer.MAX_VALUE) {
                return -1;
            }
            maxTime = Math.max(maxTime, time);
        }

        return maxTime;
    }
}
```

---

### Complexity Analysis
*   **Time Complexity: `O(E log V)`**, where `V` is `n` (nodes) and `E` is `times.length` (edges).
    *   Building the graph takes `O(E)`.
    *   Each node is added to the priority queue at most once. Each edge relaxation involves a potential priority queue operation, which takes `O(log V)` time. In total, this gives `O(E log V)`.

*   **Space Complexity: `O(V + E)`**
    *   The `graph` map stores all edges, taking `O(E)` space.
    *   The `dist` array and the priority queue can take up to `O(V)` space.
