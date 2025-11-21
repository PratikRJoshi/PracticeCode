# Common Graph Algorithms

This document provides a comprehensive overview of common graph algorithms and their associated LeetCode problems.

## 1. Breadth-First Search (BFS)
- Used for finding shortest paths in unweighted graphs
- Level-order traversal of graphs
- Finding connected components

**LeetCode Problems:**
- **Easy:** 
  - [#733 Flood Fill](https://leetcode.com/problems/flood-fill/)
  - [#994 Rotting Oranges](https://leetcode.com/problems/rotting-oranges/)
  - [#1091 Shortest Path in Binary Matrix](https://leetcode.com/problems/shortest-path-in-binary-matrix/)
- **Medium:**
  - [#200 Number of Islands](https://leetcode.com/problems/number-of-islands/)
  - [#207 Course Schedule](https://leetcode.com/problems/course-schedule/)
  - [#210 Course Schedule II](https://leetcode.com/problems/course-schedule-ii/)
  - [#542 01 Matrix](https://leetcode.com/problems/01-matrix/)
  - [#1162 As Far from Land as Possible](https://leetcode.com/problems/as-far-from-land-as-possible/)
- **Hard:**
  - [#127 Word Ladder](https://leetcode.com/problems/word-ladder/)
  - [#815 Bus Routes](https://leetcode.com/problems/bus-routes/)
  - [#864 Shortest Path to Get All Keys](https://leetcode.com/problems/shortest-path-to-get-all-keys/)

## 2. Depth-First Search (DFS)
- Used for exploring paths, cycles, and connectivity
- Topological sorting
- Finding strongly connected components

**LeetCode Problems:**
- **Easy:**
  - [#100 Same Tree](https://leetcode.com/problems/same-tree/)
  - [#104 Maximum Depth of Binary Tree](https://leetcode.com/problems/maximum-depth-of-binary-tree/)
  - [#112 Path Sum](https://leetcode.com/problems/path-sum/)
- **Medium:**
  - [#130 Surrounded Regions](https://leetcode.com/problems/surrounded-regions/)
  - [#200 Number of Islands](https://leetcode.com/problems/number-of-islands/)
  - [#417 Pacific Atlantic Water Flow](https://leetcode.com/problems/pacific-atlantic-water-flow/)
  - [#695 Max Area of Island](https://leetcode.com/problems/max-area-of-island/)
  - [#797 All Paths From Source to Target](https://leetcode.com/problems/all-paths-from-source-to-target/)
- **Hard:**
  - [#329 Longest Increasing Path in a Matrix](https://leetcode.com/problems/longest-increasing-path-in-a-matrix/)
  - [#332 Reconstruct Itinerary](https://leetcode.com/problems/reconstruct-itinerary/)
  - [#1192 Critical Connections in a Network](https://leetcode.com/problems/critical-connections-in-a-network/)

## 3. Dijkstra's Algorithm
- Finds shortest paths from a source to all vertices
- Works with non-negative edge weights

**LeetCode Problems:**
- **Medium:**
  - [#743 Network Delay Time](https://leetcode.com/problems/network-delay-time/)
  - [#787 Cheapest Flights Within K Stops](https://leetcode.com/problems/cheapest-flights-within-k-stops/)
  - [#1334 Find the City With the Smallest Number of Neighbors at a Threshold Distance](https://leetcode.com/problems/find-the-city-with-the-smallest-number-of-neighbors-at-a-threshold-distance/)
- **Hard:**
  - [#499 The Maze III](https://leetcode.com/problems/the-maze-iii/)
  - [#505 The Maze II](https://leetcode.com/problems/the-maze-ii/)
  - [#1631 Path With Minimum Effort](https://leetcode.com/problems/path-with-minimum-effort/)
  - [#1786 Number of Restricted Paths From First to Last Node](https://leetcode.com/problems/number-of-restricted-paths-from-first-to-last-node/)

## 4. Bellman-Ford Algorithm
- Finds shortest paths from a source to all vertices
- Works with negative edge weights
- Can detect negative cycles

**LeetCode Problems:**
- **Medium:**
  - [#787 Cheapest Flights Within K Stops](https://leetcode.com/problems/cheapest-flights-within-k-stops/)
- **Hard:**
  - [#743 Network Delay Time](https://leetcode.com/problems/network-delay-time/)
  - [#787 Cheapest Flights Within K Stops](https://leetcode.com/problems/cheapest-flights-within-k-stops/)
  - [#1514 Path with Maximum Probability](https://leetcode.com/problems/path-with-maximum-probability/)

## 5. Floyd-Warshall Algorithm
- All-pairs shortest path algorithm
- Works with negative edge weights

**LeetCode Problems:**
- **Medium:**
  - [#399 Evaluate Division](https://leetcode.com/problems/evaluate-division/)
  - [#1334 Find the City With the Smallest Number of Neighbors at a Threshold Distance](https://leetcode.com/problems/find-the-city-with-the-smallest-number-of-neighbors-at-a-threshold-distance/)
- **Hard:**
  - [#1462 Course Schedule IV](https://leetcode.com/problems/course-schedule-iv/)
  - [#1617 Count Subtrees With Max Distance Between Cities](https://leetcode.com/problems/count-subtrees-with-max-distance-between-cities/)

## 6. Minimum Spanning Tree (Kruskal's & Prim's)
- Finds a subset of edges that forms a tree including every vertex
- Minimizes total edge weight

**LeetCode Problems:**
- **Medium:**
  - [#1135 Connecting Cities With Minimum Cost](https://leetcode.com/problems/connecting-cities-with-minimum-cost/)
  - [#1584 Min Cost to Connect All Points](https://leetcode.com/problems/min-cost-to-connect-all-points/)
- **Hard:**
  - [#1168 Optimize Water Distribution in a Village](https://leetcode.com/problems/optimize-water-distribution-in-a-village/)
  - [#1489 Find Critical and Pseudo-Critical Edges in Minimum Spanning Tree](https://leetcode.com/problems/find-critical-and-pseudo-critical-edges-in-minimum-spanning-tree/)

## 7. Topological Sort
- Linear ordering of vertices such that for every directed edge (u,v), u comes before v
- Only works on Directed Acyclic Graphs (DAGs)

**LeetCode Problems:**
- **Medium:**
  - [#207 Course Schedule](https://leetcode.com/problems/course-schedule/)
  - [#210 Course Schedule II](https://leetcode.com/problems/course-schedule-ii/)
  - [#269 Alien Dictionary](https://leetcode.com/problems/alien-dictionary/)
  - [#310 Minimum Height Trees](https://leetcode.com/problems/minimum-height-trees/)
  - [#802 Find Eventual Safe States](https://leetcode.com/problems/find-eventual-safe-states/)
- **Hard:**
  - [#329 Longest Increasing Path in a Matrix](https://leetcode.com/problems/longest-increasing-path-in-a-matrix/)
  - [#1203 Sort Items by Groups Respecting Dependencies](https://leetcode.com/problems/sort-items-by-groups-respecting-dependencies/)

## 8. Union-Find (Disjoint Set)
- Tracks a set of elements partitioned into disjoint subsets
- Used for finding connected components

**LeetCode Problems:**
- **Medium:**
  - [#200 Number of Islands](https://leetcode.com/problems/number-of-islands/)
  - [#547 Number of Provinces](https://leetcode.com/problems/number-of-provinces/)
  - [#684 Redundant Connection](https://leetcode.com/problems/redundant-connection/)
  - [#721 Accounts Merge](https://leetcode.com/problems/accounts-merge/)
  - [#990 Satisfiability of Equality Equations](https://leetcode.com/problems/satisfiability-of-equality-equations/)
- **Hard:**
  - [#128 Longest Consecutive Sequence](https://leetcode.com/problems/longest-consecutive-sequence/)
  - [#765 Couples Holding Hands](https://leetcode.com/problems/couples-holding-hands/)
  - [#1168 Optimize Water Distribution in a Village](https://leetcode.com/problems/optimize-water-distribution-in-a-village/)
  - [#1489 Find Critical and Pseudo-Critical Edges in Minimum Spanning Tree](https://leetcode.com/problems/find-critical-and-pseudo-critical-edges-in-minimum-spanning-tree/)

## 9. Strongly Connected Components (Tarjan's & Kosaraju's)
- Finds maximal strongly connected subgraphs
- A strongly connected component is a subgraph where every vertex is reachable from every other vertex

**LeetCode Problems:**
- **Medium:**
  - [#207 Course Schedule](https://leetcode.com/problems/course-schedule/)
  - [#802 Find Eventual Safe States](https://leetcode.com/problems/find-eventual-safe-states/)
- **Hard:**
  - [#1192 Critical Connections in a Network](https://leetcode.com/problems/critical-connections-in-a-network/)

## 10. Bipartite Graph Checking
- Determines if a graph can be colored using two colors such that no adjacent vertices have the same color

**LeetCode Problems:**
- **Medium:**
  - [#785 Is Graph Bipartite?](https://leetcode.com/problems/is-graph-bipartite/)
  - [#886 Possible Bipartition](https://leetcode.com/problems/possible-bipartition/)
- **Hard:**
  - [#1579 Remove Max Number of Edges to Keep Graph Fully Traversable](https://leetcode.com/problems/remove-max-number-of-edges-to-keep-graph-fully-traversable/)
