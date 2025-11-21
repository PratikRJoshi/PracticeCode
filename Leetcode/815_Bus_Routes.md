# Bus Routes

## Problem Description

**Problem Link:** [Bus Routes](https://leetcode.com/problems/bus-routes/)

You are given an array `routes` representing bus routes where `routes[i]` is a bus route that the `i`th bus repeats forever.

- For example, if `routes[0] = [1, 5, 7]`, this means that the `0`th bus travels in the sequence `1 -> 5 -> 7 -> 1 -> 5 -> 7 -> 1 -> ...` forever.

You will start at bus stop `source` (You are not on any bus initially), and you want to go to bus stop `target`. You can travel between bus stops only by buses.

Return *the least number of buses you must take to reach* `target`*. Return* `-1` *if it is not possible*.

**Example 1:**
```
Input: routes = [[1,2,7],[3,6,7]], source = 1, target = 6
Output: 2
Explanation: The best strategy is take the first bus to the stop 7, then take the second bus to the stop 6.
```

**Constraints:**
- `1 <= routes.length <= 500`
- `1 <= routes[i].length <= 10^5`
- All the values of `routes[i]` are **unique**.

## Intuition/Main Idea

We need minimum buses to go from source to target. This is a BFS problem where nodes are bus stops and edges represent bus routes.

**Core Algorithm:**
- Build graph: stop → list of buses that visit it
- Use BFS starting from source
- Track buses taken (not stops)
- For each stop, try all buses that visit it
- Mark stops visited by each bus

**Why BFS:** BFS finds shortest path. We want minimum buses, so BFS on bus routes (not stops) is appropriate.

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|------------------------|-----------------------------------|
| Find minimum buses | BFS - Lines 10-35 |
| Build stop-to-buses map | Graph building - Lines 12-18 |
| Track buses taken | Level-based BFS - Lines 20-21 |
| Avoid revisiting | Visited buses set - Lines 9, 28 |

## Final Java Code & Learning Pattern (Full Content)

```java
class Solution {
    public int numBusesToDestination(int[][] routes, int source, int target) {
        if (source == target) {
            return 0;
        }
        
        // Map: stop -> list of bus indices that visit this stop
        Map<Integer, List<Integer>> stopToBuses = new HashMap<>();
        
        for (int i = 0; i < routes.length; i++) {
            for (int stop : routes[i]) {
                stopToBuses.computeIfAbsent(stop, k -> new ArrayList<>()).add(i);
            }
        }
        
        Queue<Integer> queue = new LinkedList<>();
        Set<Integer> visitedBuses = new HashSet<>();
        Set<Integer> visitedStops = new HashSet<>();
        
        // Start from source: add all buses that visit source
        for (int bus : stopToBuses.getOrDefault(source, new ArrayList<>())) {
            queue.offer(bus);
            visitedBuses.add(bus);
        }
        visitedStops.add(source);
        
        int buses = 1;
        
        while (!queue.isEmpty()) {
            int size = queue.size();
            
            for (int i = 0; i < size; i++) {
                int bus = queue.poll();
                
                // Check all stops on this bus route
                for (int stop : routes[bus]) {
                    if (stop == target) {
                        return buses;
                    }
                    
                    // Try all buses that visit this stop
                    if (!visitedStops.contains(stop)) {
                        visitedStops.add(stop);
                        for (int nextBus : stopToBuses.get(stop)) {
                            if (!visitedBuses.contains(nextBus)) {
                                visitedBuses.add(nextBus);
                                queue.offer(nextBus);
                            }
                        }
                    }
                }
            }
            
            buses++;
        }
        
        return -1;
    }
}
```

## Complexity Analysis

**Time Complexity:** $O(B * S)$ where $B$ is number of buses and $S$ is average stops per bus.

**Space Complexity:** $O(B * S)$ for the graph and visited sets.

## Similar Problems

- [Word Ladder](https://leetcode.com/problems/word-ladder/) - Similar BFS pattern
- [Open the Lock](https://leetcode.com/problems/open-the-lock/) - BFS shortest path
- [Snakes and Ladders](https://leetcode.com/problems/snakes-and-ladders/) - Similar graph traversal

