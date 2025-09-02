### 886. Possible Bipartition
Problem: https://leetcode.com/problems/possible-bipartition/

### Main Idea: Bipartite Graph Coloring

This problem is a classic test of whether a graph is **bipartite**. A graph is bipartite if you can divide its nodes into two independent sets, such that no two nodes within the same set are connected by an edge. 

We can rephrase this as a **2-coloring problem**:
1.  **Model:** People are nodes, and `dislikes` are edges.
2.  **Goal:** Assign one of two "colors" (e.g., Group 1 and Group 2) to every person, such that no two people who dislike each other have the same color.
3.  **Strategy (BFS):** We traverse the graph, assigning alternating colors. We start with a node, color it `A`, and add its neighbors to a queue to be colored `B`. Then we process the `B` nodes, coloring their neighbors `A`, and so on. If we ever find a neighbor that is already colored with the *same* color as the current node, we have a conflict, and the graph is not bipartite.

### General Template: Bipartition Check (BFS)

```java
// 1. Build the graph (adjacency list)
// 2. Create a 'colors' array to store the color of each node (0=uncolored, 1=color A, -1=color B)

// 3. Loop through all nodes to handle disconnected components
for (int i = 1; i <= n; i++) {
    if (colors[i] == 0) { // If uncolored, start a new BFS
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(i);
        colors[i] = 1; // Start with color A

        while (!queue.isEmpty()) {
            int node = queue.poll();
            for (int neighbor : graph.get(node)) {
                if (colors[neighbor] == 0) { // If neighbor is uncolored
                    colors[neighbor] = -colors[node]; // Assign opposite color
                    queue.offer(neighbor);
                } else if (colors[neighbor] == colors[node]) {
                    return false; // Conflict: neighbor has same color
                }
            }
        }
    }
}
return true; // No conflicts found
```

### Requirement → Code Mapping

| Tag | Requirement | Where Implemented |
|---|---|---|
| R1 | Model people and dislikes as a graph | `graph.get(d[0]).add(d[1]);` (Adjacency List) |
| R2 | Track which group/color each person is in | `int[] colors = new int[n + 1];` |
| R3 | Handle multiple disconnected groups of people | `for (int i = 1; i <= n; i++) { if (colors[i] == 0) ... }` |
| R4 | Assign a person to the opposite group of their enemy | `colors[neighbor] = -colors[node];` |
| R5 | Detect a conflict where two enemies must be in the same group | `if (colors[neighbor] == colors[node]) return false;` |

### BFS Solution for Possible Bipartition

```java
import java.util.*;

class Solution {
    public boolean possibleBipartition(int n, int[][] dislikes) {
        // R1: Build the graph
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            graph.add(new ArrayList<>());
        }
        for (int[] d : dislikes) {
            graph.get(d[0]).add(d[1]);
            graph.get(d[1]).add(d[0]);
        }

        // R2: colors[i] = 0 (uncolored), 1 (group A), -1 (group B)
        int[] colors = new int[n + 1];

        // R3: Loop through all people to handle disconnected components
        for (int i = 1; i <= n; i++) {
            if (colors[i] == 0) { // Not yet colored
                Queue<Integer> queue = new LinkedList<>();
                queue.offer(i);
                colors[i] = 1; // Assign to group A

                while (!queue.isEmpty()) {
                    int node = queue.poll();
                    for (int neighbor : graph.get(node)) {
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
*   **Time Complexity: `O(V + E)`**, where `V` is `n` (people) and `E` is `dislikes.length`.
    *   We build the graph which takes `O(E)`.
    *   The BFS traversal visits each node and edge exactly once, which is `O(V + E)`.

*   **Space Complexity: `O(V + E)`**
    *   The `graph` adjacency list stores `2 * E` entries, so `O(E)`.
    *   The `colors` array and the `queue` take `O(V)` space.
