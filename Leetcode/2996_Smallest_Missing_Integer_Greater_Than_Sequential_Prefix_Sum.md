# 2996. Smallest Missing Integer Greater Than Sequential Prefix Sum

## Problem Description

[Smallest Missing Integer Greater Than Sequential Prefix Sum](https://leetcode.com/problems/smallest-missing-integer-greater-than-sequential-prefix-sum/)

A prefix `nums[0..i]` is **sequential** if every element is exactly one more than the previous (`nums[j] == nums[j-1] + 1` for all `1 <= j <= i`). A single element is trivially sequential. Let `s` be the sum of the **longest** sequential prefix. Return the smallest integer `x` such that `x >= s` and `x` is **not** present in `nums`.

### Example 1

Input: `nums = [1,2,3,2,5]`

Output: `6`

Explanation: longest sequential prefix is `[1,2,3]`, sum `6`. `6` is not in `nums`, so the answer is `6`.

### Example 2

Input: `nums = [3,4,5,1,12,14,13]`

Output: `15`

Explanation: longest sequential prefix is `[3,4,5]`, sum `12`. `12, 13, 14` are all present, so the answer is `15`.

### Constraints

- `1 <= nums.length <= 50`, `1 <= nums[i] <= 50`.

## Intuition / Main Idea

The word **"prefix"** is the whole trick: a prefix must start at index 0, so there is exactly **one** maximal sequential prefix. The instant the `+1` chain breaks, every longer prefix contains that broken pair and cannot be sequential — so we stop immediately. No max-tracking, no resume.

Then it's a simple "smallest missing value `>= s`" lookup, which a `HashSet` answers in O(1) per probe.

### Build the intuition step by step

1. Seed `sum = nums[0]` (length-1 prefix is always sequential).
2. Walk forward; while `nums[i] == nums[i-1] + 1`, add to `sum`. On the first break, stop.
3. Dump all values into a `HashSet`.
4. Increment `sum` until it is absent from the set — that's the answer.

### Why this works

A later `+1` run (not starting at index 0) is not a prefix, so it never qualifies even if longer. The final `while` advances at most `n + 1` times because only `n` distinct values can block consecutive integers.

## Code Mapping

| Problem Requirement (@) | Java Code Section |
|---|---|
| Longest sequential prefix sum | accumulate while `nums[i] == nums[i-1] + 1`, else `break` |
| Fast presence check | `HashSet<Integer>` of all values |
| Smallest missing `x >= s` | `while (set.contains(sum)) sum++;` |

## Final Java Code & Learning Pattern

```java
// [Pattern: Linear prefix scan + HashSet membership probe]
import java.util.HashSet;
import java.util.Set;

class Solution {
    public int missingInteger(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (int n : nums) set.add(n);

        int sum = nums[0];                 // length-1 prefix is trivially sequential
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] == nums[i - 1] + 1) {
                sum += nums[i];
            } else {
                break;                     // prefix broke -> no longer prefix can be sequential
            }
        }

        while (set.contains(sum)) sum++;   // smallest missing value >= sum
        return sum;
    }
}
```

### Why each part exists

- **`sum = nums[0]` seed** — a single element is always a valid sequential prefix.
- **`break` on chain break** — because "prefix" forces a start at index 0, there is nothing to gain by scanning further.
- **HashSet** — turns each "is `x` present?" check into O(1).
- **`while` increment** — finds the first integer `>= sum` not occupied.

## Complexity Analysis

- **Time Complexity:** $O(n)$ — one pass to build the set, one to sum the prefix, and the final `while` runs at most `n + 1` times.
- **Space Complexity:** $O(n)$ for the set.

## Similar Problems

1. [LeetCode 41. First Missing Positive](https://leetcode.com/problems/first-missing-positive/) — smallest missing positive (O(1) space via cyclic sort).
2. [LeetCode 448. Find All Numbers Disappeared in an Array](https://leetcode.com/problems/find-all-numbers-disappeared-in-an-array/) — all missing in `[1, n]`.
3. [LeetCode 268. Missing Number](https://leetcode.com/problems/missing-number/) — single missing in `[0, n]`.
