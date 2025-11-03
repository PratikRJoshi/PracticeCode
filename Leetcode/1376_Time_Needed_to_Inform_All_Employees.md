### 1376. Time Needed to Inform All Employees
Problem: https://leetcode.com/problems/time-needed-to-inform-all-employees/

---

#### Reference solution (DFS)
```java
import java.util.*;

class Solution {
    public int numOfMinutes(int n, int headID, int[] manager, int[] informTime) {
        Map<Integer, List<Integer>> graph = new HashMap<>();
        for (int i = 0; i < manager.length; i++) {
            int boss = manager[i];
            graph.computeIfAbsent(boss, k -> new ArrayList<>()).add(i);
        }
        return dfs(graph, informTime, headID);
    }

    private int dfs(Map<Integer, List<Integer>> graph, int[] informTime, int cur) {
        int max = 0;                       // longest delay among sub-trees
        if (!graph.containsKey(cur))       // leaf → no sub-ordinates
            return max;
        for (int next : graph.get(cur))    // explore all direct reports
            max = Math.max(max, dfs(graph, informTime, next));
        return max + informTime[cur];      // add current manager’s delay **after** recursion
    }
}
```

---

#### Why add `informTime[cur]` **after** the recursion? (A story)

Imagine a CEO needs to spread news. The total time is determined by the **slowest path** from the CEO to the last employee.

**Scenario:**
- **CEO:** Takes **10 mins** to talk.
  - **Manager A:** Takes **2 mins** to talk.
  - **Manager B:** Takes **7 mins** to talk.

**How time flows:**
1.  The CEO talks to both managers at the same time. This takes **10 mins**.
2.  Now, two branches happen in parallel:
    - **Path A:** Manager A talks to their team. Total time for this path: `10 (CEO) + 2 (Manager A) = 12 mins`.
    - **Path B:** Manager B talks to their team. Total time for this path: `10 (CEO) + 7 (Manager B) = 17 mins`.

The news isn't fully spread until the slowest path is complete. The final time is `max(12, 17) = 17`.

**How the code mirrors this:**
`dfs(CEO)` does the following:
1.  It calls `dfs()` on its children (Manager A and B) to find out how long their branches take *after* the CEO is done talking. It gets back `2` and `7`.
2.  It finds the `max` of those children's times: `max(2, 7) = 7`. This identifies the bottleneck.
3.  It adds its *own* talking time to that bottleneck: `7 + 10 = 17`.

We add `informTime[cur]` **after** the loop because the manager's delay happens only *after* the slowest subordinate path has been identified.

---

#### Complexity Analysis
* **Time Complexity: `O(n)`**
  - We iterate through all `n` employees once to build the adjacency list (graph).
  - The DFS traversal visits each employee exactly once.

* **Space Complexity: `O(n)`**
  - The adjacency list requires `O(n)` space to store the hierarchy.
  - The recursion stack for DFS can go as deep as `n` in the worst case (a single long chain of command).
