### 207. Course Schedule
Problem: https://leetcode.com/problems/course-schedule/.

# LeetCode 207: Course Schedule

## Problem Statement

There are a total of `numCourses` courses you have to take, labeled from `0` to `numCourses - 1`. You are given an array `prerequisites` where `prerequisites[i] = [ai, bi]` indicates that you must take course `bi` first if you want to take course `ai`.

For example, the pair `[0, 1]` indicates that to take course `0` you have to first take course `1`.

Return `true` if you can finish all courses. Otherwise, return `false`.

---

## Intuition and Main Idea

This problem can be modeled as a directed graph problem. Each course is a **node** in the graph, and a prerequisite relationship `[ai, bi]` represents a directed **edge** from `bi` to `ai` (i.e., `bi -> ai`). This means course `bi` must be completed before `ai`.

The question "can you finish all courses?" translates to: **"Is there a cycle in this directed graph?"**

If there is a cycle (e.g., `A -> B -> C -> A`), it's impossible to determine a starting point among these courses, and thus you can never complete them all. If the graph is a Directed Acyclic Graph (DAG), you can always find a valid order to take the courses. This ordering is called a **topological sort**.

The core idea is to perform a topological sort. If a valid topological sort includes all the courses, it means there are no cycles, and we can finish all courses.

---

## Algorithm: Topological Sort (Kahn's Algorithm using BFS)

This is a standard and intuitive algorithm for finding a topological sort. It mimics how you would actually take courses.

1.  **Graph Representation & In-Degree Calculation:**
    *   We first need to represent the graph. An **adjacency list** is a good choice, where we map each course to a list of courses that depend on it.
    *   We also need to count the number of prerequisites for each course. This is called the **in-degree**. We'll use an array, say `inDegree`, where `inDegree[i]` stores the number of prerequisites for course `i`.

2.  **Initialization:**
    *   Create a queue and add all courses with an in-degree of `0`. These are the courses with no prerequisites, so we can take them right away.

3.  **Processing:**
    *   While the queue is not empty, dequeue a course. Let's call it `currentCourse`.
    *   Increment a counter for the number of courses we have successfully "taken".
    *   For each neighbor of `currentCourse` (i.e., each course that has `currentCourse` as a prerequisite):
        *   Decrement the neighbor's in-degree by 1 (since we've completed one of its prerequisites).
        *   If the neighbor's in-degree becomes `0`, it means all its prerequisites are now met. Add this neighbor to the queue.

4.  **Conclusion:**
    *   After the loop finishes, if the count of courses taken is equal to `numCourses`, it means we were able to process every course. This is only possible if there were no cycles. Return `true`.
    *   If the count is less than `numCourses`, it means some courses were never processed because their in-degree never became `0`. This happens when they are part of a cycle. Return `false`.

---

## Java Implementation

```java
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

class Solution {
    public boolean canFinish(int numCourses, int[][] prerequisites) {
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
        int coursesTaken = 0;
        while (!queue.isEmpty()) {
            int currentCourse = queue.poll();
            coursesTaken++;

            for (int neighbor : adj.get(currentCourse)) {
                inDegree[neighbor]--;
                if (inDegree[neighbor] == 0) {
                    queue.offer(neighbor);
                }
            }
        }

        // 4. Conclusion
        return coursesTaken == numCourses;
    }
}
```

---

## Complexity Analysis

Let `V` be the number of courses (`numCourses`) and `E` be the number of prerequisites.

*   **Time Complexity: O(V + E)**
    *   **Graph Building:** We iterate through all `E` prerequisites to build the adjacency list and the `inDegree` array. This takes O(E) time.
    *   **Queue Initialization:** We iterate through all `V` courses to find the initial set of courses with an in-degree of 0. This takes O(V) time.
    *   **BFS Traversal:** Each course (vertex) is enqueued and dequeued exactly once. When we process a course, we look at all its outgoing edges. Over the entire algorithm, each edge is processed exactly once. This takes O(V + E) time.
    *   The total time complexity is O(E) + O(V) + O(V + E) = **O(V + E)**.

*   **Space Complexity: O(V + E)**
    *   **Adjacency List:** The space required to store the graph is O(E) because we store one entry for each prerequisite relationship.
    *   **In-Degree Array:** This requires O(V) space.
    *   **Queue:** In the worst case (a long chain of dependencies), the queue might hold up to O(V) courses.
    *   The total space complexity is dominated by the storage for the graph, making it **O(V + E)**.
