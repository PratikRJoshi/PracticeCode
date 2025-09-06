### LeetCode 1462: Course Schedule IV
Problem: https://leetcode.com/problems/course-schedule-iv/

## Problem Statement

There are a total of `numCourses` courses you have to take, labeled from `0` to `numCourses - 1`. You are given an array `prerequisites` where `prerequisites[i] = [ai, bi]` indicates that you must take course `bi` first if you want to take course `ai`.

You are also given an array `queries` where `queries[j] = [uj, vj]`. For the `j`th query, you should answer `true` if course `uj` is a prerequisite of course `vj`, and `false` otherwise.

Return a `boolean` array `answer`, where `answer[j]` is the answer to the `j`th query.

---

## Intuition and Main Idea

This problem asks whether a course `u` is an indirect prerequisite for another course `v`. In graph terms, this means: **"Is there a path from node `u` to node `v` in the dependency graph?"**

The number of queries can be large, so running a separate graph traversal (like BFS or DFS) for each query would likely be too slow. A single BFS/DFS takes `O(V + E)` time (where `V` is courses, `E` is prerequisites). With `Q` queries, the total time would be `O(Q * (V + E))`, which is inefficient.

A better strategy is to **pre-compute the reachability for all pairs of courses**. We can create a 2D matrix, say `isPrerequisite[i][j]`, which will store `true` if course `i` is a prerequisite for course `j` (directly or indirectly).

Two common algorithms for this all-pairs reachability problem are:
1.  **Floyd-Warshall Algorithm**: A dynamic programming approach that iteratively considers each node as an intermediate point in paths between other nodes. Its simplicity and elegance make it a great choice here.
2.  **Multiple BFS/DFS Runs**: Run a separate traversal starting from every single course `i` to find all the courses `j` that are reachable from it. This is also efficient and can be more intuitive.

We will implement the Floyd-Warshall approach.

---

## Algorithm: Floyd-Warshall for Transitive Closure

The Floyd-Warshall algorithm can be adapted to find the **transitive closure** of a graph, which is exactly what we need: a complete map of who is a prerequisite for whom.

1.  **Initialization:**
    *   Create a 2D boolean matrix `isPrerequisite` of size `numCourses x numCourses`, and initialize all entries to `false`.
    *   For each direct prerequisite `[u, v]` in the input, set `isPrerequisite[u][v] = true`.

2.  **Dynamic Programming Iteration:**
    *   Iterate through every course `k` from `0` to `numCourses - 1`. We will consider `k` as a potential intermediate course in a prerequisite chain.
    *   Inside this loop, iterate through every possible pair of courses `(i, j)`.
    *   The core logic is: Course `i` is a prerequisite for `j` if `i` is a prerequisite for `k` AND `k` is a prerequisite for `j`.
    *   Update the matrix with this rule: `isPrerequisite[i][j] = isPrerequisite[i][j] || (isPrerequisite[i][k] && isPrerequisite[k][j]);`
    *   After iterating through all `k`, the `isPrerequisite` matrix will contain all direct and indirect prerequisite relationships.

3.  **Process Queries:**
    *   Iterate through the `queries` array.
    *   For each query `[u, v]`, the answer is simply the value of `isPrerequisite[u][v]`. This is now an `O(1)` lookup.

---

## Thinking Through the `k` Loop (Floyd-Warshall Intuition)

The role of the outermost `k` loop can feel a bit like magic at first, but it's based on a very clever and systematic way of building up a solution. Here's the thought process:

### The Goal: Finding All Paths

Our goal is to figure out if a path exists from any course `i` to any other course `j`.
- A **direct path** is easy: `i -> j`. We know this from the `prerequisites` list.
- An **indirect path** involves one or more intermediate courses: `i -> a -> b -> ... -> j`.

The challenge is to find *all* these indirect paths efficiently.

### The "Aha!" Moment: Building Paths Incrementally

Instead of trying to find paths of any length all at once, we can build them up step-by-step. This is a classic dynamic programming approach.

**Step 0: The Base Case (Paths of length 1)**
We start with the simplest possible paths: direct connections. We create a grid `isPrerequisite[i][j]` and mark it `true` only if there's a direct edge from `i` to `j`.

**Step 1: Introducing an Intermediate Node**
Now, let's ask a new question: "Can we find any new paths if we are allowed to pass through **course 0**?"

For any two courses `i` and `j`, a path from `i` to `j` can now exist in two ways:
1.  The direct path we already knew about (`i -> j`).
2.  A new, indirect path that goes through course 0 (`i -> 0 -> j`).

We can update our knowledge for every single pair `(i, j)` with this new rule:
`isPrerequisite[i][j] = isPrerequisite[i][j] OR (isPrerequisite[i][0] AND isPrerequisite[0][j])`

After we do this for all `i` and `j`, our grid is smarter. It now knows about all direct paths and all indirect paths that use **only course 0** as an intermediate stop.

### The General Idea of `k`

The variable `k` in the Floyd-Warshall algorithm represents this **"intermediate node that we are currently introducing"**.

The outermost loop `for (int k = 0; k < numCourses; k++)` is systematically introducing one new node at a time and giving our `isPrerequisite` grid a chance to "level up" its knowledge.

When the `k` loop is running for `k = 10`, it's essentially asking:
> "For every pair of nodes `(i, j)`, let's check if we can create a new path from `i` to `j` by going through node `10`. The path from `i` to `10` and the path from `10` to `j` can use any of the intermediate nodes we've already processed (`0` through `9`)."

By the time the `k` loop finishes, we have considered every single node as a potential intermediate point in a path between any two other nodes. As a result, the `isPrerequisite` grid contains the complete answer for all possible paths.

---

## Java Implementation

```java
import java.util.ArrayList;
import java.util.List;

class Solution {
    public List<Boolean> checkIfPrerequisite(int numCourses, int[][] prerequisites, int[][] queries) {
        // 1. Initialization
        boolean[][] isPrerequisite = new boolean[numCourses][numCourses];
        for (int[] p : prerequisites) {
            isPrerequisite[p[0]][p[1]] = true;
        }

        // 2. Floyd-Warshall to find transitive closure
        for (int k = 0; k < numCourses; k++) {
            for (int i = 0; i < numCourses; i++) {
                for (int j = 0; j < numCourses; j++) {
                    // Is `i` a prereq for `j`? 
                    // Either it was already, OR we can go from `i` to `k` and then `k` to `j`.
                    isPrerequisite[i][j] = isPrerequisite[i][j] || (isPrerequisite[i][k] && isPrerequisite[k][j]);
                }
            }
        }

        // 3. Process Queries
        List<Boolean> result = new ArrayList<>();
        for (int[] q : queries) {
            result.add(isPrerequisite[q[0]][q[1]]);
        }

        return result;
    }
}
```
---

## Complexity Analysis

Let `V` be `numCourses`, `E` be `prerequisites.length`, and `Q` be `queries.length`.

*   **Time Complexity: O(V³ + Q)**
    *   **Initialization:** Populating the initial `isPrerequisite` matrix takes O(E) time.
    *   **Floyd-Warshall Algorithm:** The three nested loops run `V` times each, leading to a time complexity of **O(V³)**. This is the dominant part of the pre-computation.
    *   **Query Processing:** Each of the `Q` queries is answered in O(1) time. This takes O(Q) time in total.
    *   The overall time complexity is **O(V³ + Q)**.

*   **Space Complexity: O(V²)**
    *   The primary space cost is the `isPrerequisite` matrix, which requires **O(V²)** space to store the reachability information for all pairs of courses.
