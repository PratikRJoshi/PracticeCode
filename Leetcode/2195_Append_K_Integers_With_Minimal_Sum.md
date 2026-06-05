# 2195. Append K Integers With Minimal Sum

## Problem Description

[Append K Integers With Minimal Sum](https://leetcode.com/problems/append-k-integers-with-minimal-sum/)

Append exactly `k` **unique positive** integers that do **not** already appear in `nums`, chosen so the sum of the appended integers is minimized. Return that sum.

### Example 1

Input: `nums = [1,4,25,10,25]`, `k = 2`

Output: `5`

Explanation: the two smallest missing positives are `2` and `3`; their sum is `5`.

### Example 2

Input: `nums = [5,6]`, `k = 6`

Output: `25`

Explanation: append `1,2,3,4,7,8` → sum `25`.

### Constraints

- `1 <= nums.length <= 10^5`, `1 <= nums[i] <= 10^9`, `1 <= k <= 10^8`.

## Intuition / Main Idea

We want the `k` smallest positive integers not present in `nums`. With `k` up to 10⁸ we can't enumerate them one by one — use the **Gauss closed form** `k(k+1)/2` for "sum of the first k positives," then repair it for the values that `nums` already occupies.

### Build the intuition step by step

1. If `nums` were empty, the answer is `1 + 2 + ... + k = k(k+1)/2`.
2. Each occupied value `v` inside our chosen window `[1, boundary]` must be swapped out for the next free integer beyond the boundary.
3. Sort + dedup `nums`; for each `v ≤ boundary`: `sum += (boundary + 1) - v; boundary++`.
4. Once `v > boundary`, no later (sorted) value can intrude → stop.

### Why this works

Swapping an occupied `v` for `boundary + 1` keeps exactly `k` numbers chosen while removing the conflict and adding the smallest still-available integer — locally optimal at every step, hence globally minimal.

## Code Mapping

| Problem Requirement (@) | Java Code Section |
|---|---|
| Sum of first k positives | `long sum = (long) k * (k + 1) / 2;` |
| Track largest taken so far | `long boundary = k;` |
| Replace occupied value | `sum += (boundary + 1) - v; boundary++;` |
| Stop when out of window | `else break;` |

## Final Java Code & Learning Pattern

```java
// [Pattern: Greedy + arithmetic-series sum with shifting boundary]
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Solution {
    public long minimalKSum(int[] nums, int k) {
        Arrays.sort(nums);

        List<Integer> unique = new ArrayList<>();
        unique.add(nums[0]);
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] != nums[i - 1]) unique.add(nums[i]);
        }

        long sum = (long) k * (k + 1) / 2;   // cast a single operand BEFORE the multiply
        long boundary = k;

        for (int v : unique) {
            if (v <= boundary) {
                sum += (boundary + 1) - v;   // swap occupied v for next free integer
                boundary++;
            } else {
                break;                       // sorted: nothing after can be in window
            }
        }
        return sum;
    }
}
```

### Why each part exists

- **Gauss formula** — avoids an O(k) loop (k up to 10⁸).
- **`(long) k * (k + 1) / 2`** — overflow guard: `k(k+1)` ≈ 10¹⁶ overflows `int`. The cast must hit one operand *before* the multiply; `(long)(k*(k+1))` would overflow in `int` first.
- **Dedup** — a repeated `nums` value must not shift the boundary twice.
- **`break`** — sorted order means later values are all beyond the window.

## Complexity Analysis

- **Time Complexity:** $O(n \log n)$ — dominated by the sort; the greedy pass is O(n).
- **Space Complexity:** $O(n)$ for the dedup list (reducible to O(1) with in-place dedup).

## Similar Problems

1. [LeetCode 2598. Smallest Missing Non-negative Integer After Operations](https://leetcode.com/problems/smallest-missing-non-negative-integer-after-operations/) — greedy over residues.
2. [LeetCode 2996. Smallest Missing Integer Greater Than Sequential Prefix Sum](https://leetcode.com/problems/smallest-missing-integer-greater-than-sequential-prefix-sum/) — smallest missing ≥ a threshold.
3. [LeetCode 41. First Missing Positive](https://leetcode.com/problems/first-missing-positive/) — smallest missing positive in O(1) space.
