# Topological Ordering of DAG

## Problem Description
You are given a directed acyclic graph (DAG) with N nodes labeled 1..N.

Each directed edge u v represents a dependency from u to v, i.e., an edge u → v.

Your task is to compute any valid topological ordering of all nodes: a permutation of 1..N such that for every edge u → v, node u appears before node v in the ordering.

If there are multiple valid orders, you may output any of them.

The graph is guaranteed to be a DAG (it has no directed cycles).



Example

N = 4, M = 3

Edges:

1 3

2 3

3 4

Here, 1 and 2 are independent starting nodes, and both must appear before 3, which must appear before 4.

Valid topological orders include:

1 2 3 4

2 1 3 4

Both are valid because:

- 1 appears before 3

- 2 appears before 3

- 3 appears before 4

and there is no dependency between 1 and 2.



Function Description

Complete the function topologicalSort in the editor below.

The function must compute any valid topological ordering of a directed acyclic graph (DAG) with N nodes labeled 1..N.



Function Parameters:

    int N: The number of nodes in the graph, labeled from 1 to N.

int edges[M][2]: A 2D array where each row represents a directed edge [u, v], meaning there is an edge u → v and v depends on u.



Returns

    int[N]: An array containing a valid topological ordering of all N nodes.



Constraints

1 ≤ N ≤ 2 * 10^5
0 ≤ M ≤ 2 * 10^5
1 ≤ u, v ≤ N
u ≠ v
The graph is guaranteed to be a DAG.


Input Format for Custom Testing


Input to the function will be provided as follows:

N: the number of nodes in the graph, labeled from 1 to N.

edges: a 2D array of size M x 2, where each row [u, v] represents a directed edge u → v.

The graph is guaranteed to be a directed acyclic graph (DAG).



Sample Case 0
Sample Input 0

5 4
1 2
1 3
3 4
2 5
Sample Output 0

1 3 2 4 5
Explanation

The edges are:

1 → 2

1 → 3

3 → 4

2 → 5

A valid topological ordering must satisfy:

1 appears before 2 and 3

3 appears before 4

2 appears before 5

The output 1 3 2 4 5 satisfies all of these constraints.

Other answers such as 1 2 3 4 5 would also be valid.

Sample Case 1
Sample Input 1

4 3
1 3
2 3
3 4
Sample Output 1

1 2 3 4
Explanation

The edges are:

1 → 3  
2 → 3  
3 → 4
A valid topological ordering must satisfy:

1 appears before 3

2 appears before 3

3 appears before 4

The output 1 2 3 4 satisfies all of these constraints.

Other answers such as 2 1 3 4 would also be valid.

## Intuition/Main Idea:
There are two common algorithms to find a topological sort:
1. Kahn's Algorithm (BFS-based): Remove nodes with no incoming edges iteratively
2. DFS-based algorithm: Use a modified DFS to build the ordering in reverse

For this solution, we'll use Kahn's algorithm because it's intuitive and efficient:
1. Calculate the in-degree (number of incoming edges) for each node
2. Start with nodes that have an in-degree of 0 (no dependencies)
3. Remove these nodes and their outgoing edges from the graph
4. This reduces the in-degree of their neighbors
5. Repeat until all nodes are processed

## Code Mapping:

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Build adjacency list | `List<List<Integer>> graph = new ArrayList<>(N + 1)` |
| Calculate in-degrees | `int[] inDegree = new int[N + 1]` |
| Process nodes with no dependencies | `Queue<Integer> queue = new LinkedList<>()` |
| Build topological order | `int[] result = new int[N]` |

## Final Java Code & Learning Pattern:

```java
import java.util.*;

class Solution {
    public static int[] topologicalSort(int N, int[][] edges) {
        // Create adjacency list representation of the graph
        List<List<Integer>> graph = new ArrayList<>(N + 1);
        for (int i = 0; i <= N; i++) {
            graph.add(new ArrayList<>());
        }
        
        // Calculate in-degree for each node
        int[] inDegree = new int[N + 1];
        
        // Build the graph
        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            graph.get(u).add(v);
            inDegree[v]++;
        }
        
        // Queue for nodes with no incoming edges
        Queue<Integer> queue = new LinkedList<>();
        
        // Add all nodes with in-degree 0 to the queue
        for (int i = 1; i <= N; i++) {
            if (inDegree[i] == 0) {
                queue.add(i);
            }
        }
        
        // Array to store the topological ordering
        int[] result = new int[N];
        int index = 0;
        
        // Process nodes in topological order
        while (!queue.isEmpty()) {
            int node = queue.poll();
            result[index++] = node;
            
            // Reduce in-degree of all neighbors
            for (int neighbor : graph.get(node)) {
                inDegree[neighbor]--;
                
                // If in-degree becomes 0, add to queue
                if (inDegree[neighbor] == 0) {
                    queue.add(neighbor);
                }
            }
        }
        
        return result;
    }
    
    // Main method for testing
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] line = scanner.nextLine().split(" ");
        int N = Integer.parseInt(line[0]);
        int M = Integer.parseInt(line[1]);
        
        int[][] edges = new int[M][2];
        for (int i = 0; i < M; i++) {
            line = scanner.nextLine().split(" ");
            edges[i][0] = Integer.parseInt(line[0]);
            edges[i][1] = Integer.parseInt(line[1]);
        }
        
        int[] result = topologicalSort(N, edges);
        
        // Print the result
        for (int i = 0; i < N; i++) {
            System.out.print(result[i] + (i < N - 1 ? " " : ""));
        }
        
        scanner.close();
    }
}
```

## Complexity Analysis:

**Time Complexity**: $O(N + M)$, where N is the number of nodes and M is the number of edges. We process each node and edge exactly once.

**Space Complexity**: $O(N + M)$ for storing the graph as an adjacency list, the in-degree array, and the queue.

## Similar Problems:
- [LeetCode 207: Course Schedule](https://leetcode.com/problems/course-schedule/) - Detecting cycles in a directed graph
- [LeetCode 210: Course Schedule II](https://leetcode.com/problems/course-schedule-ii/) - Finding a topological ordering if one exists
- [LeetCode 269: Alien Dictionary](https://leetcode.com/problems/alien-dictionary/) - Building a graph from character order constraints and finding a topological sort
