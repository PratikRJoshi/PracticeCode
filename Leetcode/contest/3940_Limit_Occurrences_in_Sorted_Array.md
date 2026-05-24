# 3940. Limit Occurrences in Sorted Array

## Problem Description

Given a **sorted** integer array `nums` and an integer `k`, return an array such that each distinct element appears at most `k` times, while preserving the relative order of the elements.

If a distinct element appears at least `k` times, it must appear exactly `k` times in the resulting array.

### Example 1

Input: `nums = [1,1,1,2,2,3], k = 2`

Output: `[1,1,2,2,3]`

Explanation: `1` is capped at 2 occurrences; `2` and `3` are kept as-is.

### Example 2

Input: `nums = [1,2,3], k = 1`

Output: `[1,2,3]`

Explanation: All elements are already distinct.

### Constraints

- `1 <= nums.length <= 100`
- `1 <= nums[i] <= 100`
- `nums` is sorted in non-decreasing order.
- `1 <= k <= nums.length`

### Follow-up

Solve it in-place using `O(1)` extra space.

## Intuition / Main Idea

The array is **sorted**, so all occurrences of any distinct value sit in one contiguous block. We can scan once and decide per element whether to keep it, without any map.

### Build the intuition step by step

1. Walk left-to-right with a single read pointer (`index`) and a single write pointer (`result`).
2. Track the current value (`num`) and how many times we've already kept it (`count`).
3. For each element `x = nums[index]`:
   - **If `x != num`:** we've entered a new distinct block — reset `num = x` and `count = 0`.
   - **If `count < k`:** keep `x` (write it to `nums[result]`, advance `result`, increment `count`).
4. After the scan, the first `result` elements of `nums` are the answer.

### Why this works

Sortedness guarantees runs of identical values are contiguous, so a single counter is enough — no hash map needed. The two `if`s are **independent**: the first updates the tracker on transitions, the second decides whether to keep. Combining them with `if/else` (the natural-looking first attempt) loses elements at the boundary where `count` hits `k` *and* a new value arrives in the same step.

## Code Mapping

| Problem Requirement | Java Code Section |
|---------------------|-------------------|
| Detect when a new distinct value begins | `if (nums[index] != num) { num = nums[index]; count = 0; }` |
| Keep at most `k` of each value | `if (count < k) { nums[result++] = nums[index]; count++; }` |
| Preserve relative order | Single forward scan, write pointer never overtakes read pointer |
| In-place output (O(1) extra space) | Overwrite `nums` in place, then `Arrays.copyOf(nums, result)` to size the result |

## Final Java Code & Learning Pattern

```java
// [Pattern: Two Pointers / Read-Write In-Place Compaction]
import java.util.Arrays;

class Solution {
    public int[] limitOccurrences(int[] nums, int k) {
        int num = nums[0];
        int count = 0;
        int index = 0;
        int result = 0;

        while (index < nums.length) {
            // Detect a new distinct block.
            if (nums[index] != num) {
                num = nums[index];
                count = 0;
            }
            // Decide whether to keep the current element.
            if (count < k) {
                nums[result++] = nums[index];
                count++;
            }
            index++;
        }

        return Arrays.copyOf(nums, result);
    }
}
```

## Complexity Analysis

- **Time Complexity:** $O(n)$ — single pass over `nums`.
- **Space Complexity:** $O(1)$ extra space (in-place write; the returned array is required output, not counted).

## Common Pitfalls

- **Hardcoding `2`** instead of using `k` while iterating.
- **Forgetting to advance the write pointer** — overwriting index 0 repeatedly.
- **Using `if/else`** so the "new value detected" branch eats the element instead of placing it. The two checks must be independent.
- **Returning the full `nums`** without truncating — trailing junk from the original tail leaks through. Use `Arrays.copyOf(nums, result)`.

## Similar Problems

1. [LeetCode 26. Remove Duplicates from Sorted Array](https://leetcode.com/problems/remove-duplicates-from-sorted-array/) — the `k = 1` special case of this problem.
2. [LeetCode 80. Remove Duplicates from Sorted Array II](https://leetcode.com/problems/remove-duplicates-from-sorted-array-ii/) — the `k = 2` special case.
3. [LeetCode 27. Remove Element](https://leetcode.com/problems/remove-element/) — same read/write pointer compaction idiom.
