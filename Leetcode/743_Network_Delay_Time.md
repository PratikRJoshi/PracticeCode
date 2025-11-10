### 743. Network Delay Time
### Problem Link: [Network Delay Time](https://leetcode.com/problems/network-delay-time/)

### Intuition/Main Idea
This problem asks us to find the minimum time it takes for a signal to reach all nodes in a network, starting from a given source node. If some nodes cannot be reached, we return -1.

This is a classic **Single-Source Shortest Path** problem on a weighted, directed graph. Since all edge weights (times) are positive, **Dijkstra's algorithm** is the ideal choice. The key insight is that the time it takes for all nodes to receive the signal is determined by the node that takes the longest time to receive it.

Dijkstra's algorithm works by greedily selecting the node with the smallest known distance from the source and relaxing all of its outgoing edges. We use a priority queue to efficiently select the next node to process. Once we've processed all reachable nodes, the answer is the maximum time among all nodes. If any node is unreachable (its time is still infinity), we return -1.

### Code Mapping

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Build the graph representation | `Map<Integer, List<int[]>> graph = new HashMap<>();` |
| Track minimum time to reach each node | `int[] minTimeToReachNode = new int[n + 1];` |
| Process nodes in order of increasing time | `PriorityQueue<int[]> priorityQueue = new PriorityQueue<>((a, b) -> a[1] - b[1]);` |
| Update time if a shorter path is found | `if (newTime < minTimeToReachNode[neighborNode]) { minTimeToReachNode[neighborNode] = newTime; }` |
| Find the maximum time among all nodes | `int maxTime = 0; for (int i = 1; i <= n; i++) { maxTime = Math.max(maxTime, time); }` |
| Handle unreachable nodes | `if (time == Integer.MAX_VALUE) { return -1; }` |

### Final Java Code & Learning Pattern

```java
// [Pattern: Dijkstra's Algorithm for Single-Source Shortest Path]
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
        minTimeToReachNode[k] = 0;  // Time to reach the source node is 0

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

            // If this node has no outgoing edges, continue
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

### Complexity Analysis
- **Time Complexity**: $O(E \log V)$ where $E$ is the number of edges (times.length) and $V$ is the number of vertices (n).
  - Building the graph takes $O(E)$ time.
  - Each edge relaxation involves a potential priority queue operation, which takes $O(\log V)$ time.
  - In the worst case, we might process each edge once, giving us $O(E \log V)$ overall.

- **Space Complexity**: $O(V + E)$
  - The graph representation requires $O(E)$ space to store all edges.
  - The distance array and priority queue can take up to $O(V)$ space.

### Similar Problems
1. **LeetCode 787: Cheapest Flights Within K Stops** - Similar shortest path problem with an additional constraint on the number of stops.
2. **LeetCode 1631: Path With Minimum Effort** - Find a path with the minimum maximum absolute difference in heights.
3. **LeetCode 1514: Path with Maximum Probability** - Find the path with the maximum probability of success.
4. **LeetCode 1334: Find the City With the Smallest Number of Neighbors at a Threshold Distance** - Uses shortest path algorithms to find cities within a distance threshold.
5. **LeetCode 1368: Minimum Cost to Make at Least One Valid Path in a Grid** - Find the minimum cost to make a valid path in a grid.
6. **LeetCode 1462: Course Schedule IV** - Determine if a course is a prerequisite of another course.
7. **LeetCode 399: Evaluate Division** - Evaluate expressions involving variables and division operations.
8. **LeetCode 1976: Number of ways to Arrive at destination** - Count the number of ways to reach a destination with the minimum possible cost.
