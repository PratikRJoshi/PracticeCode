### LeetCode 210: Course Schedule II
Problem: https://leetcode.com/problems/course-schedule-ii/.    

## Problem Statement

There are a total of `numCourses` courses you have to take, labeled from `0` to `numCourses - 1`. You are given an array `prerequisites` where `prerequisites[i] = [ai, bi]` indicates that you must take course `bi` first if you want to take course `ai`.

Return the ordering of courses you should take to finish all courses. If there are many valid answers, return any of them. If it is impossible to finish all courses, return an empty array.

---

## Intuition and Main Idea

This problem is a direct extension of LeetCode 207 (Course Schedule). In that problem, we only needed to determine *if* it was possible to finish all courses. Here, we need to return the actual *order* in which to take them.

The underlying problem is the same: we need to find a **topological sort** of the course dependency graph. If a topological sort exists, it gives us a valid sequence of courses. If one doesn't exist (because the graph has a cycle), then it's impossible to finish them all.

The algorithm we used for LC207 (Kahn's Algorithm) is perfectly suited for this. As we process courses from the queue (i.e., courses whose prerequisites have been met), we can simply add them to a result list. The order in which they are added to this list is a valid topological ordering.

---

## Algorithm: Topological Sort (Kahn's Algorithm using BFS)

The algorithm is nearly identical to the one for Course Schedule I.

1.  **Graph Representation & In-Degree Calculation:**
    *   Build an **adjacency list** to represent the graph (`bi -> ai`).
    *   Calculate the **in-degree** for each course.

2.  **Initialization:**
    *   Create a queue and add all courses with an in-degree of `0`.
    *   Create a list or array, `topologicalOrder`, to store the result.

3.  **Processing:**
    *   While the queue is not empty:
        *   Dequeue a course, `currentCourse`.
        *   Add `currentCourse` to the `topologicalOrder` list.
        *   For each neighbor of `currentCourse`:
            *   Decrement the neighbor's in-degree.
            *   If the neighbor's in-degree becomes `0`, add it to the queue.

4.  **Conclusion:**
    *   After the loop, check if the size of the `topologicalOrder` list is equal to `numCourses`. 
    *   If it is, we have successfully found an ordering for all courses. Return the `topologicalOrder` list.
    *   If it's not, it means there was a cycle, and some courses were never processed. Return an empty array as required.

---

## Java Implementation

```java
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

class Solution {
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        // 1. Graph Representation & In-Degree Calculation
        List<List<Integer>> adj = new ArrayList<>(numCourses);
        for (int i = 0; i < numCourses; i++) {
            adj.add(new ArrayList<>());
        }

        int[] inDegree = new int[numCourses];
        for (int[] prereq : prerequisites) {
            int course = prereq[0];
            int pre = prereq[1];
            adj.get(pre).add(course); // Edge from pre -> course
            inDegree[course]++;
        }

        // 2. Initialization
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < numCourses; i++) {
            if (inDegree[i] == 0) {
                queue.offer(i);
            }
        }

        // 3. Processing
        int[] topologicalOrder = new int[numCourses];
        int i = 0; // Pointer for the result array

        while (!queue.isEmpty()) {
            int currentCourse = queue.poll();
            topologicalOrder[i++] = currentCourse;

            for (int neighbor : adj.get(currentCourse)) {
                inDegree[neighbor]--;
                if (inDegree[neighbor] == 0) {
                    queue.offer(neighbor);
                }
            }
        }

        // 4. Conclusion
        // If we took all courses, i will be equal to numCourses
        if (i == numCourses) {
            return topologicalOrder;
        } else {
            // Otherwise, there was a cycle, and it's impossible
            return new int[0];
        }
    }
}
```

---

## Complexity Analysis

The complexity analysis is identical to that of Course Schedule I.

Let `V` be the number of courses (`numCourses`) and `E` be the number of prerequisites.

*   **Time Complexity: O(V + E)**
    *   Building the graph and in-degree array: O(E)
    *   Initializing the queue: O(V)
    *   BFS traversal: Each vertex and edge is processed once, which is O(V + E).
    *   Total: **O(V + E)**.

*   **Space Complexity: O(V + E)**
    *   Adjacency List: O(E)
    *   In-Degree Array: O(V)
    *   Queue: O(V) in the worst case.
    *   Result Array: O(V)
    *   Total: **O(V + E)**.

```
