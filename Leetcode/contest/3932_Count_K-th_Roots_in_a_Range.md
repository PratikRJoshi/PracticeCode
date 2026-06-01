# 3932. Count K-th Roots in a Range

> Weekly Contest 502, Q2. The statement below is reconstructed from the accepted solution (official contest text is login-gated).

## Problem Description

[Count K-th Roots in a Range](https://leetcode.com/problems/count-k-th-roots-in-a-range/)

Given integers `l`, `r`, and `k`, count the number of integers `x` such that `l <= x^k <= r`. Equivalently, count the **perfect k-th powers** that fall within `[l, r]`.

### Example 1

Input: `l = 4, r = 20, k = 2`

Output: `3`

Explanation: `2^2=4`, `3^2=9`, `4^2=16` lie in `[4, 20]` (`5^2=25` is too big).

### Example 2

Input: `l = 1, r = 1000000000, k = 1`

Output: `1000000000`

Explanation: With `k = 1`, every integer in `[l, r]` is its own first power.

### Constraints

- `0 <= l <= r <= 10^9` (representative).
- `1 <= k`.

## Intuition / Main Idea

Counting perfect k-th powers in a range. The decisive insight is **which variable to iterate**: iterate the candidate root `x`, not the range `[l, r]`, because the number of valid roots is only about `r^{1/k}`.

### Build the intuition step by step

1. We want integers `x` with `l <= x^k <= r`.
2. Iterating every value in `[l, r]` and testing each for being a perfect power is `O(r - l)` — up to `10^9`, far too slow.
3. Instead iterate `x` upward from `0`, compute `x^k`, and stop once `x^k > r`. The loop runs only `O(r^{1/k})` times.
4. Count each `x` whose `x^k` lands in `[l, r]`.

### Pitfalls that shaped the solution

- **Integer division:** in Java `1 / k` is `0` for any `k >= 2`; the early `Math.pow(i, 1/k)` attempt silently computed `i^0 = 1`. Fractional exponents need `1.0 / k`.
- **Floating-point imprecision:** `Math.pow(64, 0.5)` can return `7.9999...`, so truncation misses perfect powers. The robust fix is to **avoid `Math.pow` entirely** and compute `x^k` with exact `long` multiplication.
- **`k == 1`:** every integer in `[l, r]` qualifies — return `r - l + 1` directly (also avoids an `O(r-l)` loop that would TLE).
- **`x = 0`:** `0^k = 0` must be counted when `l <= 0`, so the loop starts at `x = 0`.

## Code Mapping

| Problem Requirement (@) | Java Code Section |
|---|---|
| Count integers `x` with `l <= x^k <= r` | `if (sq >= l && sq <= r) result++;` |
| Iterate the small search space (roots) | `int x = 0; while (sq <= r) { ...; x++; sq = power(x, k); }` |
| Exact `x^k` (no float error) | `power(x, k)` via repeated `long` multiplication |
| `k == 1` shortcut | `if (k == 1) return r - l + 1;` |

## Final Java Code & Learning Pattern

```java
// [Pattern: iterate candidate roots (small search space) + exact integer power]
class Solution {
    public int countKthRoots(int l, int r, int k) {
        if (k == 1) {
            return r - l + 1;          // every integer in [l, r] is its own 1st power
        }

        int result = 0;
        int x = 0;                     // include 0 so 0^k = 0 is counted when l <= 0
        long sq = power(x, k);
        while (sq <= r) {
            if (sq >= l) {
                result++;
            }
            x++;
            sq = power(x, k);
        }
        return result;
    }

    private long power(int x, int k) {
        long result = 1;
        for (int i = 0; i < k; i++) {
            result *= x;               // exact; avoids Math.pow precision loss
        }
        return result;
    }
}
```

### Why each part exists

- **`if (k == 1) return r - l + 1`** — avoids both a wrong-answer (`x^1 = x`) and a TLE from looping over the whole range.
- **`x` starts at `0`** — handles `0^k = 0` when the range includes `0`.
- **`power` with `long` multiplication** — exact integer arithmetic; `Math.pow` returns a `double` and loses precision near perfect powers.
- **`while (sq <= r)`** — stop as soon as the k-th power exceeds `r`; only ~`r^{1/k}` iterations.

## Complexity Analysis

- **Time Complexity:** $O(r^{1/k} \cdot k)$ — about `r^{1/k}` candidate roots, each costing `k` multiplications. For `k == 1`, $O(1)$.
- **Space Complexity:** $O(1)$.

## Similar Problems

1. [LeetCode 69. Sqrt(x)](https://leetcode.com/problems/sqrtx/) — integer square root without floating point.
2. [LeetCode 367. Valid Perfect Square](https://leetcode.com/problems/valid-perfect-square/) — perfect-power test, exact arithmetic.
3. [LeetCode 633. Sum of Square Numbers](https://leetcode.com/problems/sum-of-square-numbers/) — iterate candidate roots over a bounded range.
