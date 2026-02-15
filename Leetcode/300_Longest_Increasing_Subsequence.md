# Longest Increasing Subsequence

## Problem Description

**Problem Link:** [Longest Increasing Subsequence](https://leetcode.com/problems/longest-increasing-subsequence/)

Given an integer array `nums`, return the length of the **longest strictly increasing subsequence**.

A **subsequence** is a sequence that can be derived from the array by deleting some (or none) of the elements **without changing the relative order** of the remaining elements.

**Example 1:**
```
Input: nums = [10,9,2,5,3,7,101,18]
Output: 4
Explanation: One LIS is [2,3,7,101], length = 4.
```

**Example 2:**
```
Input: nums = [0,1,0,3,2,3]
Output: 4
Explanation: One LIS is [0,1,2,3], length = 4.
```

**Example 3:**
```
Input: nums = [7,7,7,7,7,7,7]
Output: 1
Explanation: Strictly increasing means duplicates cannot be used back-to-back.
```

**Constraints:**
- `1 <= nums.length <= 2500`
- `-10^4 <= nums[i] <= 10^4`

## Intuition/Main Idea

### Build the intuition (mentor-style)

When you look at `nums[i]`, there are only two possibilities:

- You **do not** use `nums[i]` in the LIS.
- You **do** use `nums[i]` in the LIS, and then it must be the **last** element of some increasing subsequence.

So a very natural “DP anchor” is:

- “What is the best increasing subsequence that ends at index `i`?”

### DP subproblem definition

Let:

- `dp[i]` = length of the **longest strictly increasing subsequence that ends at index `i`**.

How do we compute `dp[i]`?

- `nums[i]` can always start a subsequence by itself, so minimum is 1.
- To extend a previous subsequence, we can only come from an index `j < i` where `nums[j] < nums[i]`.

So the transition is:

- `dp[i] = 1 + max(dp[j])` over all `j < i` with `nums[j] < nums[i]`
- If no such `j` exists, then `dp[i] = 1`.

Finally, the answer is:

- `max(dp[i])` over all indices `i`.

### Why this intuition works

This works because:

- Any increasing subsequence has a **last element**.
- If the last element is `nums[i]`, then the rest of that subsequence must end at some earlier index `j` with a smaller value.

So by solving “best ending at `i`” for every `i`, we cover all possible subsequences.

### Dynamic Programming Problems: Top-down then Bottom-up

We’ll write the same idea in two DP styles:

#### Top-down / memoized

- Subproblem: `lisEndingAt(i)` returns the LIS length ending at `i`.
- It depends on `lisEndingAt(j)` for `j < i`.
- Memoization avoids recomputing `lisEndingAt(i)`.

#### Bottom-up

- We compute `dp[i]` from left to right.
- This is usually simpler in interviews for LIS because indices are naturally ordered.

### (Optional) Faster pattern: `O(n log n)` via “tails”

There is also a classic optimization using binary search:

- Keep an array `tails[len]` = the **smallest possible tail value** of an increasing subsequence of length `len + 1`.
- For each number, binary search its position in `tails`.

This gives `O(n log n)` time. But the DP solution is the most straightforward to derive and is enough for the constraints here.

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|------------------------|-----------------------------------|
| Strictly increasing subsequence | Compare `nums[j] < nums[i]` before extending (both DP styles) (lines 30-66) |
| Return length of LIS (not the subsequence) | Track and return global maximum length (lines 22-27, 55-66) |
| Constraints up to `n = 2500` | Use `O(n^2)` DP which is feasible (`~6.25M` comparisons) (overall) |
| Dynamic Programming requirement | Top-down memoized then bottom-up implementation (lines 9-27 and 29-66) |

## Final Java Code & Learning Pattern (Full Content)

```java
import java.util.Arrays;

class Solution {
    public int lengthOfLIS(int[] nums) {
        // Bottom-up is the simplest to execute and explain in one pass.
        // (Top-down version is also included below as a learning pattern.)
        return lengthOfLISBottomUp(nums);
    }

    // ----------------------------
    // Top-down / memoized version
    // ----------------------------
    // Subproblem: lisEndingAt(i) = length of LIS ending exactly at index i.
    // Transition: lisEndingAt(i) = 1 + max(lisEndingAt(j)) for all j < i with nums[j] < nums[i].
    // Memo array size = n because we memoize one answer per index.
    private int lengthOfLISTopDown(int[] nums) {
        int n = nums.length;
        int[] memo = new int[n];
        Arrays.fill(memo, -1);

        int best = 1;
        for (int i = 0; i < n; i++) {
            best = Math.max(best, lisEndingAt(nums, i, memo));
        }

        return best;
    }

    private int lisEndingAt(int[] nums, int i, int[] memo) {
        if (memo[i] != -1) {
            return memo[i];
        }

        // Base: the subsequence [nums[i]] has length 1.
        int bestEndingHere = 1;

        // Try to extend any valid subsequence ending at j into i.
        for (int j = 0; j < i; j++) {
            if (nums[j] < nums[i]) {
                bestEndingHere = Math.max(bestEndingHere, 1 + lisEndingAt(nums, j, memo));
            }
        }

        memo[i] = bestEndingHere;
        return bestEndingHere;
    }

    // ----------------------------
    // Bottom-up version
    // ----------------------------
    // dp[i] = length of LIS ending at i.
    // dp size = n because we store one LIS-ending-length per index.
    private int lengthOfLISBottomUp(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n];
        Arrays.fill(dp, 1);

        int best = 1;

        // We fill dp left-to-right because dp[i] depends only on dp[0..i-1].
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                // Strictly increasing means nums[j] must be strictly smaller than nums[i].
                if (nums[j] < nums[i]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            best = Math.max(best, dp[i]);
        }

        return best;
    }
}
```

## Complexity Analysis

**Time Complexity:** $O(n^2)$

- Both top-down and bottom-up DP do (in the worst case) a nested loop over pairs `(j, i)` with `j < i`.

**Space Complexity:** $O(n)$

- `dp[]` or `memo[]` arrays store one value per index.

## Similar Problems

- [Russian Doll Envelopes](https://leetcode.com/problems/russian-doll-envelopes/) - LIS after sorting + tie handling
- [Maximum Length of Pair Chain](https://leetcode.com/problems/maximum-length-of-pair-chain/) - LIS-like DP/greedy pattern
- [Number of Longest Increasing Subsequences](https://leetcode.com/problems/number-of-longest-increasing-subsequences/) - LIS with counting
- [Longest Continuous Increasing Subsequence](https://leetcode.com/problems/longest-continuous-increasing-subsequence/) - contiguous variant (not subsequence)
- [Longest String Chain](https://leetcode.com/problems/longest-string-chain/) - LIS pattern on strings via DP
