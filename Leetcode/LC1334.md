### 1334. Find the City With the Smallest Number of Neighbors at a Threshold Distance
Problem: https://leetcode.com/problems/find-the-city-with-the-smallest-number-of-neighbors-at-a-threshold-distance/

---

### Main Idea: Floyd-Warshall for All-Pairs Shortest Path

To solve this problem, we first need to find the shortest travel distance between **every pair of cities**. This is a classic **All-Pairs Shortest Path (APSP)** problem. The Floyd-Warshall algorithm is a perfect fit for this, especially with the given constraints (`n <= 100`).

1.  **Initialize Distances:** We start with a 2D `distances` matrix. The distance from a city to itself is 0. The distance between directly connected cities is their edge weight. All other pairs are initialized to infinity.
2.  **Iterate Through Intermediate Nodes:** The core of the algorithm is a triple loop. We iterate through every possible intermediate city `k`. For every pair of cities `i` and `j`, we check if going through `k` creates a shorter path (`distances[i][k] + distances[k][j]`). If it does, we update `distances[i][j]`.
3.  **Count Reachable Cities:** After the algorithm finishes, the `distances` matrix contains the shortest path between every city pair. We can then iterate through each city `i`, count how many other cities `j` have `distances[i][j] <= distanceThreshold`.
4.  **Find the Result:** We keep track of the city with the minimum reachable count, breaking ties by choosing the city with the greater index.

---

### Requirement → Code Mapping

| Tag | Requirement | Where Implemented |
|---|---|---|
| R1 | Initialize a matrix with all path distances | `long[][] distances = new long[n][n];` loop |
| R2 | Run Floyd-Warshall to find all-pairs shortest paths | The triple `for` loop over `k`, `i`, and `j` |
| R3 | For each city, count neighbors within the threshold | The nested loop after Floyd-Warshall |
| R4 | Keep track of the city with the minimum count | `if (reachableCities <= minReachable) { ... }` |
| R5 | Break ties by choosing the city with the larger index | `resultCity = i;` (updates on `<=` condition) |

---

### Floyd-Warshall Solution

```java
import java.util.Arrays;

class Solution {
    public int findTheCity(int n, int[][] edges, int distanceThreshold) {
        // R1: Initialize distances matrix
        // Use long to avoid overflow during addition
        long[][] distances = new long[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(distances[i], Integer.MAX_VALUE);
            distances[i][i] = 0;
        }

        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            int weight = edge[2];
            distances[u][v] = weight;
            distances[v][u] = weight;
        }

        // R2: Floyd-Warshall algorithm
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    // Update distance if path through k is shorter
                    if (distances[i][k] + distances[k][j] < distances[i][j]) {
                        distances[i][j] = distances[i][k] + distances[k][j];
                    }
                }
            }
        }

        int minReachable = n;
        int resultCity = -1;

        // R3: Count reachable cities for each city
        for (int i = 0; i < n; i++) {
            int reachableCities = 0;
            for (int j = 0; j < n; j++) {
                if (i != j && distances[i][j] <= distanceThreshold) {
                    reachableCities++;
                }
            }

            // R4 & R5: Find city with min count, breaking ties with larger index
            if (reachableCities <= minReachable) {
                minReachable = reachableCities;
                resultCity = i;
            }
        }

        return resultCity;
    }
}
```

---

### Complexity Analysis
*   **Time Complexity: `O(n^3)`**
    *   The dominant part of the algorithm is the triple nested loop of the Floyd-Warshall algorithm.

*   **Space Complexity: `O(n^2)`**
    *   We use a 2D array of size `n x n` to store the distances between all pairs of cities.

---

### Data Structure Choice: Adjacency Matrix vs. Adjacency List

For the **Floyd-Warshall algorithm**, using a 2D array (an **Adjacency Matrix**) is the ideal choice, not a `Map` (an **Adjacency List**).

*   **Why Adjacency Matrix is Better Here:** The core of the algorithm, `distances[i][j] = distances[i][k] + distances[k][j]`, requires instant `O(1)` lookup of the distance between any two arbitrary nodes. A matrix provides this directly.
*   **Why Adjacency List is Worse Here:** An adjacency list is designed to quickly find the *direct neighbors* of a node. It does not provide an efficient way to get the distance between two non-adjacent nodes, which is essential for Floyd-Warshall.

| Algorithm | Best Data Structure | Reason |
| :--- | :--- | :--- |
| **Floyd-Warshall** | **Adjacency Matrix** | Needs `O(1)` access to distances between any two nodes. |
| **BFS / DFS / Dijkstra's** | **Adjacency List** | Needs to efficiently iterate over a single node's neighbors. |