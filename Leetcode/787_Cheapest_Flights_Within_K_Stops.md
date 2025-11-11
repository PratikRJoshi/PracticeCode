### 787. Cheapest Flights Within K Stops
Problem: https://leetcode.com/problems/cheapest-flights-within-k-stops/

---

### Problem Analysis

This is a shortest path problem on a weighted, directed graph, but with an important additional constraint: the path can have **at most `k` stops**. This constraint means we can't use a standard Dijkstra's algorithm, as it might find a path that is cheaper but has too many stops. We need an algorithm that can account for the number of edges in the path.

Two excellent approaches are a modified **Bellman-Ford** algorithm and a modified **Dijkstra's** algorithm.

---

### Approach 1: Modified Bellman-Ford

#### Intuition
The Bellman-Ford algorithm is a natural fit because of its iterative nature. After 1 iteration, it finds the cheapest paths using at most 1 flight. After `i` iterations, it finds the cheapest paths with at most `i` flights. This aligns perfectly with the problem's constraint of `k` stops (which means `k+1` flights).

We run a loop `k+1` times. In each iteration, we calculate a new set of prices based on the prices from the *previous* iteration, ensuring we don't use information from the current iteration prematurely.

#### Solution
```java
import java.util.Arrays;

class SolutionBellmanFord {
    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
        // prices[i] stores the cheapest price to reach city i from src
        int[] prices = new int[n];
        Arrays.fill(prices, Integer.MAX_VALUE);
        prices[src] = 0;

        // We run the relaxation loop k+1 times (for k stops)
        for (int i = 0; i <= k; i++) {
            // Use a temp array to store new prices for this iteration
            int[] tempPrices = Arrays.copyOf(prices, n);

            for (int[] flight : flights) {
                int from = flight[0];
                int to = flight[1];
                int price = flight[2];

                if (prices[from] == Integer.MAX_VALUE) {
                    continue;
                }

                // Relax the edge: check if flying from 'from' is cheaper
                if (prices[from] + price < tempPrices[to]) {
                    tempPrices[to] = prices[from] + price;
                }
            }
            // Update prices with the results of the current iteration
            prices = tempPrices;
        }

        return prices[dst] == Integer.MAX_VALUE ? -1 : prices[dst];
    }
}
```

#### Complexity Analysis
*   **Time Complexity: `O(k * E)`**, where `E` is the number of flights.
*   **Space Complexity: `O(n)`**, for the `prices` arrays.

---

### Approach 2: Dijkstra's with State (BFS-style)

#### Intuition
We can adapt Dijkstra's algorithm to solve this problem. The key is to modify the state we store in the priority queue. Instead of just tracking `[city, cost]`, we must also track the number of stops: `[cost, city, stops]`.

The priority queue will always explore the path that is currently the **cheapest**. When we extract a path from the queue, we only explore its neighbors if the number of stops is within our limit (`<= k`).

#### Solution
```java
import java.util.*;

class SolutionDijkstra {
    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
        // Build the graph
        Map<Integer, List<int[]>> graph = new HashMap<>();
        for (int[] flight : flights) {
            graph.computeIfAbsent(flight[0], key -> new ArrayList<>()).add(new int[]{flight[1], flight[2]});
        }

        // Priority Queue stores {cost, city, stops}
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        pq.offer(new int[]{0, src, 0});

        // Track min stops to reach a city to prune search space
        int[] minStops = new int[n];
        Arrays.fill(minStops, Integer.MAX_VALUE);

        while (!pq.isEmpty()) {
            int[] current = pq.poll();
            int cost = current[0];
            int city = current[1];
            int stops = current[2];

            // Prune path if it has too many stops or is already suboptimal
            if (stops > k + 1 || stops >= minStops[city]) {
                continue;
            }
            minStops[city] = stops;

            if (city == dst) {
                return cost;
            }

            if (!graph.containsKey(city)) {
                continue;
            }

            for (int[] neighbor : graph.get(city)) {
                int neighborCity = neighbor[0];
                int price = neighbor[1];
                pq.offer(new int[]{cost + price, neighborCity, stops + 1});
            }
        }

        return -1;
    }
}
```

#### Complexity Analysis
*   **Time Complexity: `O(E log(E))` or `O(E log(V))`**. The size of the priority queue can grow large, as we may add multiple paths to the same node (with different stop counts).
*   **Space Complexity: `O(V + E)`**, for the graph, priority queue, and stops array.

---

### Comparison of Approaches

| Approach | Time Complexity | When to Use |
| :--- | :--- | :--- |
| **Bellman-Ford** | `O(k * E)` | Simpler to implement. More efficient when `k` is small. |
| **Dijkstra's** | `O(E log E)` | More complex. More efficient when `k` is large and the graph is sparse. |
