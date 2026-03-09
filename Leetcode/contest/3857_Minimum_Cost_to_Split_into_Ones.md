# Minimum Cost to Split into Ones

## Problem Description

**Problem Link:** [Minimum Cost to Split into Ones](https://leetcode.com/problems/minimum-cost-to-split-into-ones/)

You are given an integer `n`.

In one operation, you may split an integer `x` into two positive integers `a` and `b` such that `a + b = x`.

The cost of this split is `a * b`.

Return the minimum total cost required to split `n` into `n` ones.

**Example 1:**
```
Input: n = 3
Output: 3
Explanation:
3 -> 1 + 2 (cost 2)
2 -> 1 + 1 (cost 1)
Total = 3
```

**Example 2:**
```
Input: n = 4
Output: 6
Explanation:
One optimal way: 4 -> 2 + 2 (cost 4), then each 2 -> 1 + 1 (cost 1 + 1)
Total = 6
```

**Constraints:**
- `1 <= n <= 500`

## Intuition/Main Idea

At first glance, this looks like a DP partition problem: for each `x`, try all splits `a + b = x`.

But there is a stronger insight: **the total cost is actually fixed no matter how you split**.

### Build the intuition step by step

1. Let `cost(x)` be the minimum total cost to split `x` into ones.
2. If we split `x` into `a` and `b`, total cost from this decision is:
   - immediate split cost: `a * b`
   - plus cost to split `a` into ones
   - plus cost to split `b` into ones
3. Suppose a candidate closed-form is:
   - `F(x) = x * (x - 1) / 2`
4. Check whether one split preserves this value:
   - `a*b + F(a) + F(b)`
   - `= a*b + a(a-1)/2 + b(b-1)/2`
   - `= (a+b)(a+b-1)/2`
   - `= F(x)` since `a+b=x`

So every split sequence gives the same total: `n*(n-1)/2`.

### Why the intuition works

The quantity `x*(x-1)/2` acts like an invariant potential:
- `F(1) = 0` (already one, no cost)
- Every split exactly redistributes potential without loss/gain.

Therefore minimum cost equals this value (and in fact all valid strategies have this same cost).

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|-------------------------|-------------------------------------|
| Return minimum total split cost | `return n * (n - 1) / 2;` |
| Handle smallest input `n = 1` | Formula naturally gives `0` |
| Avoid overflow habits | Use `long` during multiplication and cast at end |

## Final Java Code & Learning Pattern

```java
// [Pattern: Math / Invariant]
class Solution {
    public int minimumCost(int n) {
        // Total cost is always n * (n - 1) / 2, independent of split order.
        // Use long multiplication to be safe in general.
        long totalCost = (long) n * (n - 1) / 2;
        return (int) totalCost;
    }
}
```

## Complexity Analysis

- **Time Complexity:** $O(1)$
- **Space Complexity:** $O(1)$

## Similar Problems

1. [650. 2 Keys Keyboard](https://leetcode.com/problems/2-keys-keyboard/) - Also uses operation-cost reasoning to avoid brute force simulation.
2. [279. Perfect Squares](https://leetcode.com/problems/perfect-squares/) - A classic “split into parts” optimization problem, usually solved with DP.
3. [343. Integer Break](https://leetcode.com/problems/integer-break/) - Partitioning an integer with objective optimization.
