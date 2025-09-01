### 841. Keys and Rooms
Problem: https://leetcode.com/problems/keys-and-rooms/

---

## Problem → State mapping (how rules translate to code)

**Problem rules (short):**
- `n` rooms numbered `0` to `n-1`. Start in room `0`.
- Each room contains keys to other rooms (`rooms[i]` = list of keys in room `i`).
- Can only enter a room if you have its key.
- Return `true` if you can visit **all** rooms.

**Key idea:**
- This is a **graph traversal** problem: rooms are nodes, keys are directed edges.
- Use DFS from room `0` to mark all reachable rooms as visited.
- Check if all rooms were visited.

**State definition (DFS):**
- `dfs(room)` → marks current room as visited and recursively visits all rooms whose keys are found here.
- `visited[]` array tracks which rooms have been entered.

**Base case:**
- If current room already visited → return (avoid cycles).

**Transition:**
- For each key in current room, recursively visit that room if not already visited.

---

## Full Java code (DFS traversal)

```java
import java.util.*;

class Solution {
    public boolean canVisitAllRooms(List<List<Integer>> rooms) {
        boolean[] visited = new boolean[rooms.size()];
        
        dfs(0, rooms, visited);
        
        // Check if all rooms were visited
        for (boolean v : visited) {
            if (!v) {
                return false;
            }
        }
        
        return true;
    }
    
    private void dfs(int room, List<List<Integer>> rooms, boolean[] visited) {
        if (visited[room]) {
            return; // already visited, avoid cycles
        }
        
        visited[room] = true;
        
        // Visit all rooms whose keys are in current room
        for (int key : rooms.get(room)) {
            if (!visited[key]) {
                dfs(key, rooms, visited);
            }
        }
    }
}
```

---

## Mapping of requirements → specific lines in code

* **Start from room 0** → `dfs(0, rooms, visited)` call on line 7.
* **Track visited rooms** → `boolean[] visited` array and `visited[room] = true` on line 19.
* **Follow keys to other rooms** → loop through `rooms.get(room)` on line 22.
* **Avoid revisiting** → `if (visited[room])` check on line 16 and `if (!visited[key])` on line 23.
* **Check all rooms reachable** → final loop on lines 9-13 ensures every room was visited.

---

## Intuition / Core idea

Think of this as exploring a building where:
- You start with access to room 0
- Each room contains keys to other rooms
- You want to know if you can eventually access every room

The algorithm simulates this exploration:
1. **Start exploring** from room 0
2. **Collect keys** from current room and visit those rooms
3. **Mark rooms as visited** to avoid going in circles
4. **Check completeness** - if any room remains unvisited, return false

This is essentially a **connected components** problem in graph theory - we're checking if all nodes (rooms) are reachable from the starting node (room 0).

---

## Complexity

* **Time:** `O(n + k)` where `n` = number of rooms, `k` = total number of keys across all rooms. Each room and each key is processed at most once.
* **Space:** `O(n)` for the visited array + `O(n)` recursion stack in worst case (linear chain of rooms).

---

## Alternative approaches

* **BFS with Queue:** Replace DFS with iterative BFS using a queue - same time/space complexity.
* **Union-Find:** Overkill for this problem but theoretically possible.
* **Iterative DFS:** Use explicit stack instead of recursion to avoid stack overflow for very deep room chains.

---
