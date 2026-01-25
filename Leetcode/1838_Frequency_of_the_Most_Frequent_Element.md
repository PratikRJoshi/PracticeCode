# 1838. Frequency of the Most Frequent Element

[LeetCode Link](https://leetcode.com/problems/frequency-of-the-most-frequent-element/)

## Problem Description
You are given an integer array `nums` and an integer `k`.

In one operation, you can choose an index `i` and increment `nums[i]` by `1`.

Return the maximum possible frequency of an element after performing at most `k` operations.

### Examples

#### Example 1
- Input: `nums = [1,2,4]`, `k = 5`
- Explanation:
  - Make all elements equal to `4` using increments: `1 -> 4` costs 3, `2 -> 4` costs 2, total cost = 5.
  - Frequency of `4` becomes 3.
- Output: `3`

#### Example 2
- Input: `nums = [1,4,8,13]`, `k = 5`
- Explanation:
  - Best is to make `[4,8]` both become `8`: `4 -> 8` costs 4.
  - Then frequency of `8` is 2.
- Output: `2`

#### Example 3
- Input: `nums = [3,9,6]`, `k = 2`
- Explanation:
  - Sort to `[3,6,9]`. Best is make `3 -> 6` cost 3 (not possible), or make `6 -> 9` cost 3 (not possible).
  - So best frequency is 1.
- Output: `1`

---

## Intuition/Main Idea
We can only **increment** values, which suggests:

- If we want many elements to become the same value `X`, it is optimal to choose `X` as an existing element (after sorting), and only increment smaller elements up to `X`.

### Sort + Sliding Window
After sorting `nums`, suppose we look at a window `[l..r]` and try to make all elements in this window equal to `nums[r]`.

Cost to raise all elements to `nums[r]` is:

`cost = nums[r] * windowSize - sum(nums[l..r])`

### Why this formula is correct
Let `target = nums[r]`. To make every element in the window equal to `target`, we can only increment, so for each index `i` in `[l..r]` we must pay:

`target - nums[i]`

Total operations needed is:

`(target - nums[l]) + (target - nums[l+1]) + ... + (target - nums[r])`

Grouping terms:

`= target * windowSize - (nums[l] + nums[l+1] + ... + nums[r])`

Which is exactly:

`cost = target * windowSize - windowSum`

### Quick example
`nums = [1,2,4]`, `k = 5` (already sorted)

Take window `[l..r] = [0..2]` so `target = nums[2] = 4`:

- `windowSize = 3`
- `windowSum = 1 + 2 + 4 = 7`
- `cost = 4 * 3 - 7 = 12 - 7 = 5`

This matches the direct increment counting:

- `1 -> 4` costs 3
- `2 -> 4` costs 2
- `4 -> 4` costs 0

Total = 5.

Where:
- `windowSize = r - l + 1`
- `sum(nums[l..r])` can be maintained via a running prefix/window sum.

If `cost <= k`, then it is possible to make all elements in `[l..r]` equal to `nums[r]`, so we can achieve frequency `windowSize`.

If `cost > k`, the window is too expensive, so we shrink from the left (`l++`) until it becomes feasible.

This gives an `O(n)` sliding window after sorting.

---

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|---|---|
| Perform at most `k` increments | Feasibility check uses `cost <= k` |
| Only increments allowed | Window target is always `nums[r]` (raise smaller elements) |
| Maximize frequency of some value | Track `best = max(best, r - l + 1)` |
| Efficient constraints | Sort + sliding window with running sum |

---

## Final Java Code & Learning Pattern (Full Content)
```java
import java.util.*;

class Solution {
    public int maxFrequency(int[] nums, int k) {
        Arrays.sort(nums);

        long sumInWindow = 0;
        int start = 0;
        int maxFreq = 0;

        for (int end = 0; end < nums.length; end++) {
            // Expand window to include nums[end]
            sumInWindow += nums[end];

            // We want to make nums[start..end] all equal to nums[end].
            // Cost = nums[end] * windowSize - sum(nums[start..end])
            while ((long) nums[end] * (end - start + 1) - sumInWindow > k) {
                // Too expensive: shrink window from the left.
                sumInWindow -= nums[start];
                start++;
            }

            // Now the window is feasible.
            maxFreq = Math.max(maxFreq, end - start + 1);
        }

        return maxFreq;
    }
}
```

### Learning Pattern
- Sort to make it meaningful to “raise everyone to the current rightmost value”.
- Maintain a sliding window where it is feasible to equalize all elements to `nums[end]`.
- Use the cost formula:
  - `cost = target * windowSize - sumInWindow`

---

## Complexity Analysis
- Time Complexity: $O(n \log n)$ due to sorting.
- Space Complexity: $O(1)$ extra (ignoring sort implementation stack).

---

## Similar Problems
- [1004. Max Consecutive Ones III](https://leetcode.com/problems/max-consecutive-ones-iii/) (sliding window with a budget)
- [424. Longest Repeating Character Replacement](https://leetcode.com/problems/longest-repeating-character-replacement/) (sliding window with changes budget)
- [2269. Find the K-Beauty of a Number](https://leetcode.com/problems/find-the-k-beauty-of-a-number/) (window-based counting idea, different domain)
