# Reconstruct Itinerary

## Problem Description

**Problem Link:** [Reconstruct Itinerary](https://leetcode.com/problems/reconstruct-itinerary/)

You are given a list of airline `tickets` where `tickets[i] = [fromi, toi]` represent the departure and arrival airports of one flight. Reconstruct the itinerary in order and return it.

All of the tickets belong to a man who departs from `"JFK"`, thus, the itinerary must begin with `"JFK"`. If there are multiple valid itineraries, you should return the itinerary that has the smallest lexical order when read as a single string.

- For example, the itinerary `["JFK", "LGA"]` has a smaller lexical order than `["JFK", "LGB"]`.

You may assume all tickets form at least one valid itinerary. You must use all the tickets once and only once.

**Example 1:**

```
Input: tickets = [["MUC","LHR"],["JFK","MUC"],["SFO","SJC"],["LHR","SFO"]]
Output: ["JFK","MUC","LHR","SFO","SJC"]
```

**Example 2:**

```
Input: tickets = [["JFK","SFO"],["JFK","ATL"],["SFO","ATL"],["ATL","JFK"],["ATL","SFO"]]
Output: ["JFK","ATL","JFK","SFO","ATL","SFO"]
Explanation: Another possible reconstruction is ["JFK","SFO","ATL","JFK","ATL","SFO"] but it is larger in lexical order.
```

**Constraints:**
- `1 <= tickets.length <= 300`
- `tickets[i].length == 2`
- `fromi.length == 3`
- `toi.length == 3`
- `fromi` and `toi` consist of uppercase English letters.
- `fromi != toi`

## Intuition/Main Idea

This is an **Eulerian Path** problem. We need to find a path that uses all edges exactly once, starting from "JFK", with lexicographically smallest order.

**Core Algorithm (Hierholzer's Algorithm):**
1. Build a graph with adjacency lists, sorted lexicographically.
2. Use DFS to traverse the graph.
3. When a node has no more outgoing edges, add it to the result.
4. Reverse the result to get the correct order.

**Why this works:** Hierholzer's algorithm finds an Eulerian path by greedily following edges and backtracking when stuck. By processing edges in lexicographic order and adding nodes when they have no outgoing edges, we ensure the lexicographically smallest path.

## Code Mapping

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Build graph with sorted edges | Graph construction - Lines 7-13 |
| DFS traversal | DFS method - Lines 15-25 |
| Process edges in order | While loop - Lines 19-22 |
| Add node when stuck | Result addition - Line 23 |
| Reverse result | Collections.reverse - Line 26 |
| Return itinerary | Return statement - Line 27 |

## Final Java Code & Learning Pattern

```java
import java.util.*;

class Solution {
    public List<String> findItinerary(List<List<String>> tickets) {
        // Build graph: airport -> priority queue of destinations (sorted)
        Map<String, PriorityQueue<String>> graph = new HashMap<>();
        
        for (List<String> ticket : tickets) {
            String from = ticket.get(0);
            String to = ticket.get(1);
            graph.putIfAbsent(from, new PriorityQueue<>());
            graph.get(from).offer(to);
        }
        
        List<String> result = new ArrayList<>();
        dfs("JFK", graph, result);
        
        // Reverse because we add nodes when backtracking
        Collections.reverse(result);
        return result;
    }
    
    private void dfs(String airport, Map<String, PriorityQueue<String>> graph, List<String> result) {
        // Process all outgoing edges in lexicographic order
        PriorityQueue<String> destinations = graph.get(airport);
        
        while (destinations != null && !destinations.isEmpty()) {
            // Greedily choose lexicographically smallest destination
            String next = destinations.poll();
            dfs(next, graph, result);
        }
        
        // Add current airport when no more outgoing edges (backtracking)
        result.add(airport);
    }
}
```

**Explanation of Key Code Sections:**

1. **Build Graph (Lines 7-13):** Create a map where each airport maps to a priority queue of destinations. Priority queues automatically maintain lexicographic order.

2. **DFS Traversal (Lines 15-25):**
   - **Process Edges (Lines 19-22):** While there are outgoing edges, greedily choose the lexicographically smallest destination and recurse.
   - **Add Node (Line 23):** When a node has no more outgoing edges, add it to the result. This happens during backtracking.

3. **Reverse Result (Line 26):** Since we add nodes when backtracking (from end to start), we reverse to get the correct order.

**Why Hierholzer's algorithm works:**
- **Greedy choice:** Always choose lexicographically smallest edge ensures optimal result.
- **Backtracking:** When stuck (no outgoing edges), we've completed a cycle. Adding the node and backtracking allows us to continue.
- **Eulerian path:** The algorithm finds a path that uses all edges exactly once.

**Why we reverse:**
- We add nodes when backtracking (when they have no outgoing edges).
- This means we add the end of the path first, then work backwards.
- Reversing gives us the correct forward order.

**Example walkthrough for `tickets = [["JFK","SFO"],["JFK","ATL"],["SFO","ATL"],["ATL","JFK"],["ATL","SFO"]]`:**
- Graph: JFK -> [ATL, SFO], ATL -> [JFK, SFO], SFO -> [ATL]
- Start at JFK: Choose ATL (lexicographically smaller than SFO)
  - ATL: Choose JFK
    - JFK: Choose SFO (ATL already used)
      - SFO: Choose ATL
        - ATL: Choose SFO
          - SFO: No more edges → add SFO
        - ATL: No more edges → add ATL
      - SFO: No more edges → add SFO
    - JFK: No more edges → add JFK
  - ATL: No more edges → add ATL
- JFK: No more edges → add JFK
- Result: [SFO, ATL, SFO, JFK, ATL, JFK]
- Reversed: [JFK, ATL, JFK, SFO, ATL, SFO] ✓

## Complexity Analysis

- **Time Complexity:** $O(E \log E)$ where $E$ is the number of tickets. We process each edge once, and priority queue operations take $O(\log E)$.

- **Space Complexity:** $O(E)$ for the graph and recursion stack.

## Similar Problems

Problems that can be solved using similar graph traversal patterns:

1. **332. Reconstruct Itinerary** (this problem) - Eulerian path with Hierholzer's
2. **753. Cracking the Safe** - Eulerian circuit
3. **2097. Valid Arrangement of Pairs** - Similar Eulerian path
4. **207. Course Schedule** - Topological sort
5. **210. Course Schedule II** - Topological sort with ordering
6. **269. Alien Dictionary** - Topological sort
7. **310. Minimum Height Trees** - Graph center
8. **399. Evaluate Division** - Graph DFS
9. **684. Redundant Connection** - Union-Find
10. **685. Redundant Connection II** - Union-Find with directed graph

