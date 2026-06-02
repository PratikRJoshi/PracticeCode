# 1981. Minimize the Difference Between Target and Chosen Elements

## Problem Description

[Minimize the Difference Between Target and Chosen Elements](https://leetcode.com/problems/minimize-the-difference-between-target-and-chosen-elements/)

You are given an `m x n` integer matrix `mat` and an integer `target`. Choose **exactly one** integer from **each row** so that the absolute difference between `target` and the **sum** of the chosen integers is minimized. Return that minimum absolute difference.

### Example 1

Input: `mat = [[1,2,3],[4,5,6],[7,8,9]]`, `target = 13`

Output: `0`

Explanation: Choose `1`, `5`, `7` → sum `13`, difference `0`.

### Example 2

Input: `mat = [[1],[2],[3]]`, `target = 100`

Output: `94`

Explanation: Only one choice: `1 + 2 + 3 = 6`, difference `94`.

### Constraints

- `m == mat.length`, `n == mat[i].length`, `1 <= m, n <= 70`.
- `1 <= mat[i][j] <= 70`, `1 <= target <= 800`.

## Intuition / Main Idea

Picking one element per row to hit a target sum is a subset-sum-flavored DP. The exhaustive search is `n^m`, but most branches **converge on the same running sum**, and from a given sum the remaining work is identical. That redundancy is exactly what memoization removes.

### Build the intuition step by step

1. After processing some rows, the only thing that affects the rest of the computation is **which row we're on** and the **running sum so far** — not the specific path taken.
2. So define the state `(row, sum)` and memoize on it.
3. `solve(row, sum)` = the minimum achievable `abs(target - finalSum)` if we still have rows `row..m-1` to choose from, having accumulated `sum`.
4. Base case `row == m`: every row contributed, so the answer for this leaf is `abs(target - sum)`.
5. Recurrence: try each column `j` in the current row and recurse: `min over j of solve(row+1, sum + mat[row][j])`.

### Why this works

The sum is bounded: `maxSum = m * maxValue ≤ 70 * 70 = 4900`. So there are only `O(m * maxSum)` distinct states. Memoization computes each once; every repeat is an `O(1)` cache hit. The exponential `n^m` collapses to polynomial.

### Complexity reasoning for memoized DP (the takeaway)

> **Time = (number of distinct states) × (work per state, excluding memoized recursive calls).**

Here: states `= m * maxSum`, work per state `= O(n)` (the column loop). So time `= O(m * n * maxSum)`.

## Code Mapping

| Problem Requirement (@) | Java Code Section |
|---|---|
| One element per row | recurse `row + 1` after adding `mat[row][j]` |
| Minimize `abs(target - sum)` | base `return Math.abs(target - sum)`; `Math.min` over choices |
| Avoid recomputation | `Integer[m][maxSum+1]` memo, `null` = uncomputed |
| Bounded sum range | `maxSum = 70 * 70 = 4900`, array size `4901` |

## Final Java Code & Learning Pattern

```java
// [Pattern: Top-down DP / memoized search over (row, running-sum)]
class Solution {
    public int minimizeTheDifference(int[][] mat, int target) {
        int m = mat.length;
        // maxSum = m * maxValue <= 70 * 70 = 4900; size 4901 for indices 0..4900.
        Integer[][] memo = new Integer[m][4901];
        return solve(mat, target, 0, 0, memo);
    }

    private int solve(int[][] mat, int target, int row, int sum, Integer[][] memo) {
        if (row == mat.length) {
            return Math.abs(target - sum);      // all rows chosen
        }
        if (memo[row][sum] != null) {
            return memo[row][sum];              // cache hit
        }

        int minDiff = Integer.MAX_VALUE;        // NOT 0: every diff is >= 0
        for (int j = 0; j < mat[0].length; j++) {
            int diff = solve(mat, target, row + 1, sum + mat[row][j], memo);
            minDiff = Math.min(minDiff, diff);
        }

        memo[row][sum] = minDiff;
        return minDiff;
    }
}
```

### Why each part exists

- **Base case at `row == m`** — pushing it one step past the last row keeps the recurrence uniform; no special-casing the final row.
- **`memo[row][sum]`** — the cache that turns `n^m` into `O(m * n * maxSum)`.
- **`minDiff = Integer.MAX_VALUE`** — initializing to `0` would make `Math.min(diff, 0)` always return `0`, since differences are non-negative.
- **Array width `4901`** — sums span `0..4900` inclusive (off-by-one: size = max index + 1).

## Dynamic Programming Notes

- **Subproblem definition:** `solve(row, sum)` = min `abs(target - final sum)` reachable from `row` onward given accumulated `sum`.
- **State transition (plain words):** "best I can do standing at this row with this sum = the best over each element I could pick here, then continue to the next row."
- **Top-down chosen** because the recurrence reads naturally as "choose, recurse, take the min"; the memo array sized `m × (maxSum+1)` reflects exactly the state space.
- **Optional optimizations:** clamp `sum` toward `target` (since `target ≤ 800` but `maxSum ≈ 4900`) to shrink states; or a bottom-up `boolean[maxSum+1]` reachable-sums set for `O(maxSum)` space, then scan for the value closest to `target`.

## Complexity Analysis

- **Time Complexity:** $O(m \cdot n \cdot \text{maxSum})$ where `maxSum = m \cdot \max(mat) \le 4900`.
- **Space Complexity:** $O(m \cdot \text{maxSum})$ for the memo, plus `O(m)` recursion stack.

## Similar Problems

1. [LeetCode 494. Target Sum](https://leetcode.com/problems/target-sum/) — assign +/- to reach a target; DP over reachable sums.
2. [LeetCode 416. Partition Equal Subset Sum](https://leetcode.com/problems/partition-equal-subset-sum/) — boolean reachable-sums subset DP.
3. [LeetCode 322. Coin Change](https://leetcode.com/problems/coin-change/) — DP over a bounded value axis with min objective.
