### 871. Minimum Number of Refueling Stops
Problem: https://leetcode.com/problems/minimum-number-of-refueling-stops/


---

Below is a compact mapping from **problem requirements → code** followed by a top-down DFS + memo Java solution that mirrors the pattern used in `LC741`, `LC123`, `LC122`, and `LC1277`.

## Problem → State mapping (how rules translate to code)

**Problem rules (short):**
- Goal distance `target`, initial fuel `startFuel`.
- `stations[i] = [pos, fuel]` sorted by `pos`.
- At a station you may refuel **once**, gaining `fuel` units.
- Minimise **number of refuelling stops** to reach `target`; return `-1` if impossible.

**Key idea (DP view):**
- Let **`k` = number of refuels used so far**.  
  For each `k (0…n)` track the **farthest distance** we can reach.
- Classic iterative solution does `dp[k] = farthest`.  We convert this to **DFS + memo** so that the code skeleton matches previous problems.

**State definition in code:**
- `dfs(idx, k)` → farthest distance reachable after processing the **first `idx` stations** (indices `0 … idx-1`) making exactly `k` refuels.
    - `idx ∈ [0,n]` (`idx==0` ⇒ no station processed).
    - `k ∈ [0,idx]`.
- Answer: smallest `k` such that `dfs(n, k) ≥ target`.

**Base cases:**
- `idx == 0`:
  - If `k == 0` ⇒ we haven’t visited any station, so distance = `startFuel`.
  - If `k > 0` ⇒ impossible ⇒ return sentinel `NEG`.

**Transition (two choices at station `idx-1`):**
1. **Skip** refuelling: distance = `dfs(idx-1, k)`.
2. **Refuel** at station `idx-1` (allowed only if previously reached the station and `k>0`):
   - previous distance `prev = dfs(idx-1, k-1)` must be `≥ pos`.
   - new distance = `prev + fuel`.
- Take `max()` of the two distances.

**Sentinel value**
- `NEG = Long.MIN_VALUE / 4` (very negative **long** that never beats a valid distance).

---

## Full Java code (DFS + memo)

```java
import java.util.*;

class Solution {
    private static final long NEG = Long.MIN_VALUE / 4; // impossible sentinel

    public int minRefuelStops(int target, int startFuel, int[][] stations) {
        int n = stations.length;
        Long[][] memo = new Long[n + 1][n + 1]; // memo[idx][k]

        for (int k = 0; k <= n; k++) {
            long dist = dfs(n, k, startFuel, stations, memo);
            if (dist >= target) return k; // smallest k that reaches target
        }
        return -1; // never reachable
    }

    // dfs(idx, k) -> farthest distance after first idx stations using k refuels
    private long dfs(int idx, int k, int startFuel, int[][] st, Long[][] memo) {
        if (idx == 0) {
            return (k == 0) ? startFuel : NEG;
        }
        if (memo[idx][k] != null) return memo[idx][k];

        // Option 1: skip station idx-1
        long best = dfs(idx - 1, k, startFuel, st, memo);

        // Option 2: refuel at station idx-1 if possible
        if (k > 0) {
            long prev = dfs(idx - 1, k - 1, startFuel, st, memo);
            if (prev >= st[idx - 1][0]) { // can reach this station
                best = Math.max(best, prev + st[idx - 1][1]);
            }
        }

        return memo[idx][k] = best;
    }
}
```

---

## Mapping of requirements → specific lines in code

* **Impossible sentinel** → `private static final long NEG …` (line 4) mirrors `NEG_INF` in `LC741`.
* **State definition `dfs(idx,k)`** → method signature line 14.
* **Base case (`idx==0`)** → lines 15-17.
* **Skip vs Refuel choices** → lines 20-27 (two branches just like *buy/skip*, *sell/skip* in stock problems).
* **Reachability guard** (`prev >= position`) → line 24 enforces *“can only refuel at stations you reached”* rule.
* **Memoization** → `memo[idx][k]` check & store lines 13 and 27.
* **Answer extraction** → for-loop lines 9-11 finds minimal `k`, akin to post-processing in `LC741` (`Math.max(0, ans)`).

---

## Complexity

* **Time:** There are `(n+1)² / 2 = O(n²)` states `(idx,k)`; each computes `O(1)` work ⇒ `O(n²)`.
* **Space:** Memo table `O(n²)` plus recursion depth `O(n)`.

---

## Notes / Pattern reuse

- The two-branch recursion (*refuel vs skip*) is structurally identical to *buy vs skip* (`LC122`) or *sell vs hold* (`LC123`).
- Using a large negative sentinel lets us `max()` states safely, exactly like `LC741`.
- Iterative heap solution is faster (`O(n log n)`), but this DFS version is purposely crafted to rehearse the memo pattern you’re practicing.

---

## Simplified Sweep Version (cleaner code)

Many prefer a *reach-based sweep* that keeps the code short and removes the second `while` loop:

```java
import java.util.*;

class Solution {
    public int minRefuelStops(int target, int startFuel, int[][] stations) {
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        long fuel = startFuel;          // distance we can still travel
        int idx = 0;                   // next station index
        int stops = 0;                 // refuels made

        // Continue until we can reach the target
        while (fuel < target) {
            // Add all stations that are now reachable with current fuel
            while (idx < stations.length && stations[idx][0] <= fuel) {
                maxHeap.offer(stations[idx][1]);
                idx++;
            }

            // No reachable station ⇒ stuck
            if (maxHeap.isEmpty()) return -1;

            fuel += maxHeap.poll(); // refuel at the best past station
            stops++;
        }
        return stops;
    }
}
```

### Why it’s simpler
* **Single loop:** We loop until `fuel ≥ target`; stations are processed on-the-fly.
* **No sentinel / final loop:** The target is implicitly handled by the `while (fuel < target)` guard.
* **Descriptive names**: `maxHeap`, `fuel`, `idx`, `stops`.
* **Overflow-safe**: `fuel` is `long`.

### Invariant (unchanged)
*Heap always holds fuels of 
**every station whose position ≤ current fuel and not yet used**.  This is the same invariant as before but maintained with less code.

### Complexity
Same as original: `O(n log n)` time, `O(n)` space.
