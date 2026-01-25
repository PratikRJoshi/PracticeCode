# 2064. Minimized Maximum of Products Distributed to Any Store

[LeetCode Link](https://leetcode.com/problems/minimized-maximum-of-products-distributed-to-any-store/)

## Problem Description
You are given an integer `n` (the number of retail stores) and an integer array `quantities` where `quantities[i]` represents the number of products of the `i`-th type.

You want to distribute all products to the stores such that:

- Each store can receive **products of at most one product type** (a store cannot mix types).
- A product type can be split across multiple stores.

Let `x` be the **maximum number of products assigned to any single store**. Your goal is to **minimize** `x`.

Return the minimum possible value of `x`.

### Examples

#### Example 1
- Input: `n = 6`, `quantities = [11, 6]`
- One optimal distribution:
  - For `11` items: split into `5, 5, 1` across 3 stores (max in those stores = 5)
  - For `6` items: split into `5, 1` across 2 stores (max in those stores = 5)
  - Total stores used = 5 (<= 6)
- Output: `5`

#### Example 2
- Input: `n = 7`, `quantities = [15, 10, 10]`
- If `x = 5`:
  - `15` needs `ceil(15/5)=3` stores
  - `10` needs `ceil(10/5)=2` stores
  - `10` needs `ceil(10/5)=2` stores
  - Total = 7 stores (fits)
- Output: `5`

---

## Intuition/Main Idea
This is a classic **"minimize the maximum"** problem, which usually indicates **Binary Search on the answer**.

### Key mapping from words -> math
If we guess a candidate answer `x` = "max products allowed in any store":

- For a product type with `q` items, if a store can hold at most `x` items (and cannot mix types), then the minimum number of stores needed for that type is:

`storesNeeded(q, x) = ceil(q / x)`

- Therefore, the total number of stores needed is:

`sum( ceil(quantities[i] / x) )`

A candidate `x` is **feasible** if:

`sum( ceil(quantities[i] / x) ) <= n`

### Why this feasibility check guarantees we won't run out of stores
The concern is: if we split a product type into multiple stores and stores cannot mix types, how do we know we have enough stores to place all items?

For a single product type with `q` items and a per-store cap `x`:

- Each store assigned to this type can contribute at most `x` capacity.
- If we use `k` stores for this type, the total capacity available for this type is at most `k * x`.
- To fit all `q` items, we must have `k * x >= q`, so the **minimum** valid `k` is:

`k = ceil(q / x)`

This is important: `ceil(q / x)` is not just "one possible" number of stores; it is the **best-case (minimum)** number of stores required for that product type given the max-per-store bound `x`.

Because stores cannot mix types, the minimum total stores required across all types is:

`sum( ceil(quantities[i] / x) )`

So:

- If `sum(...) <= n`, then we can assign each type exactly its required number of stores (and potentially leave some stores unused), so distribution is possible.
- If `sum(...) > n`, then even in the best case we still need more than `n` stores, so distribution is impossible for that `x`.

### Why binary search works
- If `x` is feasible, then any larger `x' > x` is also feasible (allowing more items per store never increases stores needed).
- This monotonic property lets us binary search the smallest feasible `x`.

### Binary Search specific decisions (as required by your rules)
- **Loop condition `<` vs `<=`**:
  - Use `while (lo < hi)` when you are searching for the *first true* in a monotonic predicate and you maintain the invariant:
    - `hi` is always feasible
    - `lo` is the smallest candidate not yet proven feasible
  - This avoids off-by-one mistakes and converges to the answer.

- **When to set `hi = mid` vs `hi = mid - 1`**:
  - When `mid` is feasible, we want to keep `mid` as a candidate answer (we are minimizing), so we do `hi = mid`.
  - When `mid` is not feasible, we must increase `x`, so we do `lo = mid + 1`.

- **Return value**:
  - With the above structure, the loop ends at `lo == hi`, and that value is the smallest feasible `x`, so we return `lo`.

---

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|---|---|
| Minimize the maximum products in any store | Binary search over `x` (`minimizedMaximum`) |
| Each store can have at most one product type | Feasibility uses `ceil(q/x)` per type; never mixes types |
| A product type can be split across many stores | `storesNeeded = (q + x - 1) / x` (ceiling division) |
| Must use at most `n` stores total | `totalStoresNeeded <= n` predicate |

---

## Final Java Code & Learning Pattern (Full Content)
```java
class Solution {
    public int minimizedMaximum(int n, int[] quantities) {
        // We are minimizing the maximum products per store.
        // The answer x is at least 1.
        int lo = 1;

        // Upper bound: if a single store got the entire largest product type,
        // max per store would be max(quantities). This is always feasible because
        // we can split product types across stores, so allowing this much per store
        // can only reduce stores needed.
        int hi = 0;
        for (int q : quantities) {
            hi = Math.max(hi, q);
        }

        // Binary search for the smallest feasible x.
        // Invariant: hi is feasible.
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;

            if (canDistribute(n, quantities, mid)) {
                // mid works, try to minimize further.
                hi = mid;
            } else {
                // mid too small => needs too many stores, increase x.
                lo = mid + 1;
            }
        }

        // lo == hi is the smallest feasible maximum.
        return lo;
    }

    private boolean canDistribute(int n, int[] quantities, int maxPerStore) {
        long storesNeeded = 0;

        for (int q : quantities) {
            // If maxPerStore is the cap for one store, and a store cannot mix types,
            // then this type alone needs ceil(q / maxPerStore) stores.
            // Ceiling division in integers: (q + maxPerStore - 1) / maxPerStore
            storesNeeded += (q + maxPerStore - 1) / maxPerStore;

            // Early exit: if we already need more than n stores, infeasible.
            if (storesNeeded > n) {
                return false;
            }
        }

        return storesNeeded <= n;
    }
}
```

### Learning Pattern
- This is **Binary Search on Answer**.
- Recognize it by:
  - Objective is "minimize the maximum" (or "maximize the minimum").
  - There is a monotonic feasibility test for a candidate answer.
- Core trick is turning the word constraint into a *counting function*:
  - "At most `x` per store" + "no mixing types" => per type stores = `ceil(q/x)`.

---

## Complexity Analysis
- Time Complexity: $O(m \cdot \log M)$
  - `m = quantities.length`
  - `M = max(quantities)`
  - Each binary search step scans all `m` types.
- Space Complexity: $O(1)$

---

## Similar Problems
- [875. Koko Eating Bananas](https://leetcode.com/problems/koko-eating-bananas/)
- [1283. Find the Smallest Divisor Given a Threshold](https://leetcode.com/problems/find-the-smallest-divisor-given-a-threshold/)
- [1011. Capacity To Ship Packages Within D Days](https://leetcode.com/problems/capacity-to-ship-packages-within-d-days/)
