# Reconstruct Itinerary

## Problem Description

**Problem Link:** [Reconstruct Itinerary](https://leetcode.com/problems/reconstruct-itinerary/)

You are given a list of airline `tickets` where `tickets[i] = [fromi, toi]` represent the departure and arrival airports of one flight. Reconstruct the itinerary in order and return it.

**What does "reconstruct" mean here?**
You are not creating a brand new itinerary. You are given a scrambled pile of tickets (each just `[from, to]`) with no information about which was used first or last. The task is to figure out the order in which all flights were taken and return that sequence of airports. Think of it like being handed a shuffled stack of boarding passes and being asked: "In what order did this person actually fly?"

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

Think of each ticket as a directed edge:
- `from` airport -> `to` airport

So the problem becomes:
1. Start from `JFK`
2. Use every edge exactly once
3. If multiple answers exist, return lexicographically smallest

This is an **Eulerian path** style problem. A standard way to solve it is **Hierholzer's algorithm**.

Simple idea:
- Always take the smallest possible next airport (lexicographic order)
- Remove that ticket immediately (so each ticket is used once)
- When you reach an airport with no outgoing tickets left, add that airport to answer
- At the end, reverse the answer

Why reverse? Because in DFS we add airports during backtracking (from end to start).

## Code Mapping

| Problem Requirement (@) | Java Code Section |
|-------------------------|------------------|
| @Start from JFK | `dfs("JFK", graph, result)` |
| @Use all tickets once | `destinations.poll()` removes one ticket/edge exactly once |
| @If multiple valid itineraries, choose smallest lexical order | `PriorityQueue<String>` for each source airport |
| @Build route in correct order | add airport on backtrack, then `Collections.reverse(result)` |

## Final Java Code & Learning Pattern

```java
import java.util.*;

class Solution {
    public List<String> findItinerary(List<List<String>> tickets) {
        // graph[from] = min-heap of destinations from 'from'
        // Min-heap ensures we always pick lexicographically smallest next airport.
        Map<String, PriorityQueue<String>> graph = new HashMap<>();

        for (List<String> ticket : tickets) {
            String from = ticket.get(0);
            String to = ticket.get(1);
            graph.putIfAbsent(from, new PriorityQueue<>());
            graph.get(from).offer(to);
        }

        List<String> result = new ArrayList<>();
        dfs("JFK", graph, result);

        // Airports are added in reverse travel order during backtracking.
        Collections.reverse(result);
        return result;
    }

    private void dfs(String airport, Map<String, PriorityQueue<String>> graph, List<String> result) {
        PriorityQueue<String> destinations = graph.get(airport);

        // While there are unused tickets leaving this airport,
        // use the lexicographically smallest one first.
        while (destinations != null && !destinations.isEmpty()) {
            String nextAirport = destinations.poll(); // consume this ticket exactly once
            dfs(nextAirport, graph, result);
        }

        // No outgoing ticket left -> fix this airport's position in route.
        result.add(airport);
    }
}
```

## Proof That This Code Meets the Problem Requirements

### 1) It starts from `JFK`
The traversal is started only by:
`dfs("JFK", graph, result)`.
So route always begins from `JFK` after final reversal.

### 2) It uses every ticket once and only once
Each ticket is stored as one entry in a priority queue.
When used, we call `poll()` on that queue, which removes exactly one entry.
No removed ticket is ever added back.
So each ticket can be used at most once.
Since DFS keeps consuming outgoing tickets until none are left everywhere reachable, and problem guarantees at least one valid itinerary, all tickets are consumed.

### 3) Returned route is valid (continuous path)
Whenever DFS moves from `airport` to `nextAirport`, it does so via an actual ticket (`poll()`ed edge).
So consecutive airports in the final route correspond to real tickets.

### 4) Lexicographically smallest valid itinerary is produced
At each airport, next destinations are taken from a min-heap, so smallest available destination is always chosen first.
Hierholzer backtracking handles dead-ends correctly while preserving this smallest-choice order per node.
Therefore among all valid itineraries, the constructed one is lexicographically smallest.

### 5) Why reversing is necessary and correct
An airport is added after all its outgoing edges are used.
So additions happen from end of journey back toward start.
Reversing converts this postorder list into the forward itinerary.

## Complexity Analysis

- **Time Complexity:** $O(E \log E)$ where $E$ is number of tickets.
  - Each ticket is inserted and removed from a priority queue once.
- **Space Complexity:** $O(E)$ for adjacency structure and recursion/result storage.

## Similar Problems

1. **2097. Valid Arrangement of Pairs** - Eulerian path pattern
2. **753. Cracking the Safe** - Eulerian circuit idea
3. **207. Course Schedule** - graph traversal/topological reasoning
4. **210. Course Schedule II** - ordered graph traversal

