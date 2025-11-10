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
        
        // Initialize all prices to "infinity" except the source
        Arrays.fill(prices, Integer.MAX_VALUE);
        prices[src] = 0;  // It costs 0 to stay at the source

        // Why k+1 iterations? Because k stops means k+1 flights
        // Each iteration represents the max number of flights we can take
        for (int i = 0; i <= k; i++) {
            // Why do we need a temp array? To avoid using updated values within the same iteration
            // This ensures we only use results from the previous iteration (with at most i-1 flights)
            int[] tempPrices = Arrays.copyOf(prices, n);

            // For each flight, try to relax the path to the destination
            for (int[] flight : flights) {
                int from = flight[0];
                int to = flight[1];
                int price = flight[2];

                // Why check if prices[from] is MAX_VALUE? Because if we can't reach the 'from' city,
                // we can't use this flight. This avoids integer overflow when adding price.
                if (prices[from] == Integer.MAX_VALUE) {
                    continue;
                }

                // Why compare with tempPrices[to]? Because we want to keep the minimum price
                // found so far for each destination, considering at most i flights
                if (prices[from] + price < tempPrices[to]) {
                    tempPrices[to] = prices[from] + price;
                }
            }
            
            // Update prices for the next iteration
            // This represents the cheapest way to reach each city using at most i flights
            prices = tempPrices;
        }

        // If we can't reach the destination within k stops, return -1
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
        // Build the graph as an adjacency list for efficient neighbor lookup
        // Map<from_city, List<[to_city, price]>>
        Map<Integer, List<int[]>> graph = new HashMap<>();
        for (int[] flight : flights) {
            graph.computeIfAbsent(flight[0], key -> new ArrayList<>()).add(new int[]{flight[1], flight[2]});
        }

        // Priority Queue sorts by cost (first element of the array)
        // Why sort by cost? Because we want to explore cheaper paths first
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        
        // Start with source city, 0 cost, and 0 stops
        pq.offer(new int[]{0, src, 0});  // [cost, city, stops]

        // Track minimum stops to reach each city to avoid processing suboptimal paths
        // Why track min stops? To prune paths that use too many stops to reach a city
        int[] minStops = new int[n];
        Arrays.fill(minStops, Integer.MAX_VALUE);

        while (!pq.isEmpty()) {
            int[] current = pq.poll();
            int cost = current[0];
            int city = current[1];
            int stops = current[2];

            // Why check stops > k+1? Because k stops means k+1 flights maximum
            // Why check stops >= minStops[city]? If we've reached this city with fewer stops before,
            // this path won't lead to a better solution (we've already explored from here with fewer stops)
            if (stops > k + 1 || stops >= minStops[city]) {
                continue;
            }
            
            // Update the minimum stops needed to reach this city
            minStops[city] = stops;

            // If we've reached the destination, return the cost
            // Why can we return immediately? Because the priority queue ensures this is the cheapest path
            // to the destination that satisfies our stops constraint
            if (city == dst) {
                return cost;
            }

            // If this city has no outgoing flights, skip
            if (!graph.containsKey(city)) {
                continue;
            }

            // Explore all neighbors of the current city
            for (int[] neighbor : graph.get(city)) {
                int neighborCity = neighbor[0];
                int price = neighbor[1];
                
                // Add neighbor to queue with updated cost and stops
                // Why stops+1? Each flight counts as one additional stop
                pq.offer(new int[]{cost + price, neighborCity, stops + 1});
            }
        }

        // If we've exhausted all possible paths and haven't reached the destination, return -1
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

### Key Insights and Intuition

1. **Why Bellman-Ford works well for this problem:**
   - The iterative nature of Bellman-Ford naturally maps to the "at most k stops" constraint
   - Each iteration i finds the cheapest paths using at most i flights
   - Using a temporary array prevents us from using updated values within the same iteration

2. **Why standard Dijkstra's algorithm doesn't work:**
   - Standard Dijkstra's only optimizes for total cost, not number of stops
   - It might find a cheaper path that uses too many stops
   - Once a node is processed in standard Dijkstra's, it's never revisited

3. **Why our modified Dijkstra's works:**
   - We include stops as part of our state in the priority queue
   - We may visit the same city multiple times with different numbers of stops
   - The minStops array helps prune paths that won't lead to better solutions

4. **Choosing between the approaches:**
   - For small k values, Bellman-Ford is often faster and simpler
   - For large k values and sparse graphs, the modified Dijkstra's may perform better
   - Bellman-Ford has more predictable performance regardless of graph structure
