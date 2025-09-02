### LC802. Find Eventual Safe States
Problem: https://leetcode.com/problems/find-eventual-safe-states/description/

### Main Idea: Topological Sort on a Reversed Graph

A node is **safe** if all paths starting from it lead to a **terminal node** (a node with no outgoing edges). Nodes in a cycle are unsafe.

The problem is equivalent to finding all nodes that cannot reach a cycle. We can solve this by reversing the problem:

1.  **Reverse the Graph:** Instead of `A -> B`, we'll think `B -> A`. Now, we're looking for nodes that can be reached *from* the original terminal nodes.
2.  **Identify Starting Points:** The original terminal nodes (out-degree of 0) become the starting points in our reversed graph (they now have an in-degree of 0).
3.  **Run Topological Sort:** We perform a topological sort (Kahn's algorithm) on the reversed graph. Any node that can be included in the sort is guaranteed to have a path to a terminal node in the original graph, making it **safe**.

### Requirement → Code Mapping

| Tag | Requirement | Where Implemented |
|---|---|---|
| R1 | Model paths leading *away* from a node | `outDegree[i] = graph[i].length;` |
| R2 | Model paths leading *into* a node | `revGraph.get(neighbor).add(i);` (Graph reversal) |
| R3 | Identify initial safe (terminal) nodes | `if (outDegree[i] == 0) queue.offer(i);` |
| R4 | Iteratively find new safe nodes | The main `while (!queue.isEmpty())` loop |
| R5 | Collect and sort the final list of safe nodes | `safeNodes.add(curr);` and `Collections.sort(safeNodes);` |

### Topological Sort Solution

```java
import java.util.*;

class Solution {
    public List<Integer> eventualSafeNodes(int[][] graph) {
        int n = graph.length;
        // R1: Track out-degrees for the original graph
        int[] outDegree = new int[n];
        // R2: Build the reversed graph
        List<List<Integer>> revGraph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            revGraph.add(new ArrayList<>());
        }

        for (int i = 0; i < n; i++) {
            outDegree[i] = graph[i].length;
            for (int neighbor : graph[i]) {
                revGraph.get(neighbor).add(i);
            }
        }

        Queue<Integer> queue = new LinkedList<>();
        // R3: Add all terminal nodes (initial safe nodes) to the queue
        for (int i = 0; i < n; i++) {
            if (outDegree[i] == 0) {
                queue.offer(i);
            }
        }

        List<Integer> safeNodes = new ArrayList<>();
        // R4: Perform topological sort on the reversed graph
        while (!queue.isEmpty()) {
            int curr = queue.poll();
            safeNodes.add(curr);

            // Check nodes that had an edge to `curr` in the original graph
            for (int neighborInRev : revGraph.get(curr)) {
                outDegree[neighborInRev]--;
                if (outDegree[neighborInRev] == 0) {
                    queue.offer(neighborInRev);
                }
            }
        }

        // R5: Sort the result before returning
        Collections.sort(safeNodes);
        return safeNodes;
    }
}
```

### Graph Representation: `Map` vs. `List<List<>>`

For this problem, where nodes are labeled `0` to `n-1`, a `List<List<Integer>>` is **more efficient**.

*   **`List<List<Integer>>`:** Faster (`O(1)` index-based access) and uses less memory. It's the ideal choice for dense, zero-based integer nodes.
*   **`Map<Integer, List<Integer>>`:** More flexible and necessary for sparse or non-integer node labels. However, it's slightly slower (due to hashing) and uses more memory. It offers no real advantage here.

While the `Map` version works perfectly, the `List` version is the more optimal and conventional approach for this specific problem context.

### Complexity Analysis
*   **Time Complexity: `O(V + E)`**, where `V` is the number of nodes and `E` is the number of edges.
    *   Building the reversed graph and out-degree array takes `O(V + E)`.
    *   The topological sort visits each node and edge once, which is `O(V + E)`.
    *   Sorting the final list takes `O(V log V)`.
    *   Overall complexity is dominated by `O(V + E + V log V)`.

*   **Space Complexity: `O(V + E)`**
    *   The `revGraph` adjacency list stores all edges, taking `O(E)` space.
    *   The `outDegree` array and the `queue` take `O(V)` space.