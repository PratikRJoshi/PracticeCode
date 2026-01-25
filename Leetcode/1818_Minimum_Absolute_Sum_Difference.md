# 1818. Minimum Absolute Sum Difference

[LeetCode Link](https://leetcode.com/problems/minimum-absolute-sum-difference/)

## Problem Description
You are given two integer arrays `nums1` and `nums2` of equal length `n`.

Define the **absolute sum difference** as:

`sum(|nums1[i] - nums2[i]|)` for all `i` from `0` to `n - 1`.

You are allowed to perform **at most one operation**:

- Replace **any one element** in `nums1` with **any element from `nums1`** (you may choose the same index / value, meaning you can also do “no-op”).

Return the **minimum possible absolute sum difference** after at most one operation.

Since the answer may be large, return it **modulo** `10^9 + 7`.

### Examples

#### Example 1
- Input: `nums1 = [1,7,5]`, `nums2 = [2,3,5]`
- Original sum: `|1-2| + |7-3| + |5-5| = 1 + 4 + 0 = 5`
- We can replace `7` with `5` (since `5` exists in `nums1`):
  - New sum: `|1-2| + |5-3| + |5-5| = 1 + 2 + 0 = 3`
- Output: `3`

#### Example 2
- Input: `nums1 = [2,4,6,8,10]`, `nums2 = [2,4,6,8,10]`
- Already equal, best is no-op.
- Output: `0`

---

## Intuition/Main Idea
We want to minimize:

`S = sum(|nums1[i] - nums2[i]|)`

We can change **at most one** `nums1[i]` to some value that already exists in `nums1`.

### Key observation
If we decide to replace at index `i`, only the `i`-th term changes:

- Original contribution: `diff = |nums1[i] - nums2[i]|`
- New contribution after replacement with value `v` (where `v` must be from `nums1`): `newDiff = |v - nums2[i]|`

So the total sum becomes:

`S' = S - diff + newDiff`

To minimize `S'`, we want to maximize the improvement:

`improvement = diff - newDiff`

### How to find best replacement quickly
For each `nums2[i]`, we want a value `v` from `nums1` that is as close as possible to `nums2[i]` (to minimize `|v - nums2[i]|`).

If we sort a copy of `nums1`, then for a target `t = nums2[i]`:

- The best `v` is either:
  - the first number `>= t` (lower bound), or
  - the number just before it (`< t`)

We can find those candidates using binary search in `O(log n)` per index.

### Binary Search details (since this solution uses it)
This is not “binary search on answer”, but we still rely on a standard **lower bound** binary search.

- **Loop condition `<` vs `<=`**:
  - For lower bound, we use `while (lo < hi)` to converge to the first index where `arr[idx] >= target`.
- **Pointer moves (`mid`, `mid+1`, etc.)**:
  - If `arr[mid] < target`, the lower bound must be to the right, so `lo = mid + 1`.
  - Otherwise, `mid` could still be the answer, so `hi = mid`.
- **Return value**:
  - Return `lo`, which is the first index with `arr[lo] >= target` (or `arr.length` if no such index exists).

---

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|---|---|
| Minimize `sum(|nums1[i] - nums2[i]|)` after at most one replacement | Compute `baseSum`, compute `maxImprove`, answer = `baseSum - maxImprove` |
| Replacement value must come from `nums1` | Use `sortedNums1` (sorted copy of `nums1`) as the allowed replacement set |
| Only one index replacement allowed | Track only the best single `maxImprove` across all indices |
| Return modulo `1e9+7` | Use `MOD` and return `(baseSum - maxImprove) % MOD` safely |

---

## Final Java Code & Learning Pattern (Full Content)
```java
import java.util.*;

class Solution {
    private static final int MOD = 1_000_000_007;

    public int minAbsoluteSumDiff(int[] nums1, int[] nums2) {
        int n = nums1.length;

        // sortedNums1 represents the set of allowed replacement values (must come from nums1).
        int[] sortedNums1 = Arrays.copyOf(nums1, n);
        Arrays.sort(sortedNums1);

        long baseSum = 0;
        int maxImprove = 0;

        for (int i = 0; i < n; i++) {
            int a = nums1[i];
            int b = nums2[i];

            int diff = Math.abs(a - b);
            baseSum += diff;

            // If we replace nums1[i] by some v from nums1, only this term changes.
            // We want v that minimizes |v - b|.
            int bestNewDiff = bestPossibleDiff(sortedNums1, b);

            // improvement = old contribution - new contribution.
            maxImprove = Math.max(maxImprove, diff - bestNewDiff);
        }

        long ans = (baseSum - maxImprove) % MOD;
        return (int) ans;
    }

    private int bestPossibleDiff(int[] sortedNums1, int target) {
        int idx = lowerBound(sortedNums1, target);

        int best = Integer.MAX_VALUE;

        // Candidate 1: first element >= target
        if (idx < sortedNums1.length) {
            best = Math.min(best, Math.abs(sortedNums1[idx] - target));
        }

        // Candidate 2: element just before lower bound (< target)
        if (idx - 1 >= 0) {
            best = Math.min(best, Math.abs(sortedNums1[idx - 1] - target));
        }

        return best;
    }

    // Returns the first index i such that arr[i] >= target.
    // If all elements are < target, returns arr.length.
    private int lowerBound(int[] arr, int target) {
        int lo = 0;
        int hi = arr.length;

        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;

            if (arr[mid] < target) {
                lo = mid + 1;
            } else {
                hi = mid;
            }
        }

        return lo;
    }
}
```

### Learning Pattern
- This is a **"one-change optimization"** pattern:
  - Compute the baseline objective.
  - For each index, compute the best improvement if you changed that index.
  - Take the single best improvement.
- The key accelerator is using **sorting + lower bound** to find the closest allowed replacement value in `O(log n)`.

---

## Complexity Analysis
- Time Complexity: $O(n \log n)$
  - Sorting `nums1`: $O(n \log n)$
  - For each index, lower bound search: $n \cdot O(\log n)$
- Space Complexity: $O(n)$
  - For the sorted copy of `nums1`.

---

## Similar Problems
- [16. 3Sum Closest](https://leetcode.com/problems/3sum-closest/) (closest value logic after sorting)
- [34. Find First and Last Position of Element in Sorted Array](https://leetcode.com/problems/find-first-and-last-position-of-element-in-sorted-array/) (lower bound style binary search)
