### 785. Is Graph Bipartite?
Problem: https://leetcode.com/problems/is-graph-bipartite/

### Main Idea: Bipartite Graph Coloring

This problem is the textbook definition of checking if a graph is **bipartite**. A graph is bipartite if its nodes can be divided into two disjoint sets such that every edge connects a node in one set to a node in the other.

This is equivalent to a **2-coloring problem**:
1.  **Model:** The input `graph` is already our adjacency list.
2.  **Goal:** Assign one of two "colors" (e.g., Group 1 and Group 2) to every node, such that no two adjacent nodes have the same color.
3.  **Strategy (BFS):** We traverse the graph, assigning alternating colors. We start with a node, color it `A`, and add its neighbors to a queue to be colored `B`. If we ever find a neighbor that is already colored with the *same* color as the current node, we have a conflict.

This is the exact same logic as LeetCode 886: Possible Bipartition.

### Requirement → Code Mapping

| Tag | Requirement | Where Implemented |
|---|---|---|
| R1 | Use the provided adjacency list | `for (int neighbor : graph[node])` |
| R2 | Track which group/color each node is in | `int[] colors = new int[n];` |
| R3 | Handle multiple disconnected components | `for (int i = 0; i < n; i++) { if (colors[i] == 0) ... }` |
| R4 | Assign a node to the opposite group of its neighbor | `colors[neighbor] = -colors[node];` |
| R5 | Detect a conflict where two neighbors must be in the same group | `if (colors[neighbor] == colors[node]) return false;` |

### BFS Solution for Is Graph Bipartite?

```java
import java.util.LinkedList;
import java.util.Queue;

class Solution {
    public boolean isBipartite(int[][] graph) {
        int n = graph.length;
        // R2: colors[i] = 0 (uncolored), 1 (group A), -1 (group B)
        int[] colors = new int[n];

        // R3: Loop through all nodes to handle disconnected components
        for (int i = 0; i < n; i++) {
            if (colors[i] == 0) { // If uncolored, start a new BFS
                Queue<Integer> queue = new LinkedList<>();
                queue.offer(i);
                colors[i] = 1; // Assign to group A

                while (!queue.isEmpty()) {
                    int node = queue.poll();
                    // R1: Use the pre-built adjacency list
                    for (int neighbor : graph[node]) {
                        if (colors[neighbor] == 0) {
                            // R4: Assign neighbor to the opposite group
                            colors[neighbor] = -colors[node];
                            queue.offer(neighbor);
                        } else if (colors[neighbor] == colors[node]) {
                            // R5: Conflict found
                            return false;
                        }
                    }
                }
            }
        }

        return true; // No conflicts found
    }
}
```
### Complexity Analysis
*   **Time Complexity: `O(V + E)`**, where `V` is `n` (nodes) and `E` is the number of edge entries.
    *   The BFS traversal visits each node and edge exactly once.

*   **Space Complexity: `O(V)`**
    *   The `colors` array and the `queue` take `O(V)` space. The graph itself is part of the input, so it's not counted as extra space.
