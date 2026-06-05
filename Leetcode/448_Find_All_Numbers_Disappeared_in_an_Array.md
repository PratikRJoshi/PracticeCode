# 448. Find All Numbers Disappeared in an Array

## Problem Description

[Find All Numbers Disappeared in an Array](https://leetcode.com/problems/find-all-numbers-disappeared-in-an-array/)

Given an array `nums` of `n` integers where each value is in the range `[1, n]`, return all integers in `[1, n]` that do **not** appear in `nums`.

### Example 1

Input: `nums = [4,3,2,7,8,2,3,1]`

Output: `[5,6]`

### Example 2

Input: `nums = [1,1]`

Output: `[2]`

### Constraints

- `n == nums.length`, `1 <= n <= 10^5`, `1 <= nums[i] <= n`.

## Intuition / Main Idea

Same shape as **First Missing Positive (#41)**: values live in `[1, n]`, so place value `v` at index `v - 1` via **cyclic sort**. After sorting, every index `i` where `nums[i] != i + 1` corresponds to a missing number `i + 1`.

### Build the intuition step by step

1. Because values are guaranteed in `[1, n]`, the two **bounds guards are unnecessary** — only the duplicate guard remains.
2. Cyclic-sort each value to its home index.
3. Scan once and collect `i + 1` for every mismatched slot.

### Why this works

The array doubles as a hash table: a present value claims its home index. Any index left holding the wrong tenant marks a value that never appeared.

## Code Mapping

| Problem Requirement (@) | Java Code Section |
|---|---|
| Avoid infinite loop on duplicates | `nums[i] != nums[nums[i] - 1]` |
| Place value at home index | freeze `target = nums[i]-1`, then swap |
| Collect all missing numbers | every `i` with `nums[i] != i + 1` → add `i + 1` |

## Final Java Code & Learning Pattern

```java
// [Pattern: Cyclic sort (index-as-hash, in-place)]
import java.util.ArrayList;
import java.util.List;

class Solution {
    public List<Integer> findDisappearedNumbers(int[] nums) {
        List<Integer> result = new ArrayList<>();
        int n = nums.length;

        for (int i = 0; i < n; i++) {
            // values are guaranteed in [1, n], so only the duplicate guard is needed
            while (nums[i] != i + 1 && nums[i] != nums[nums[i] - 1]) {
                int target = nums[i] - 1;   // freeze BEFORE swapping
                int temp = nums[i];
                nums[i] = nums[target];
                nums[target] = temp;
            }
        }

        for (int i = 0; i < n; i++) {
            if (nums[i] != i + 1) result.add(i + 1);
        }
        return result;
    }
}
```

### Why each part exists

- **Dropped bounds guards** — constraints guarantee `1 <= nums[i] <= n`, so `nums[nums[i]-1]` is always in range.
- **Frozen `target`** — `nums[i]` changes after the first write; reusing it corrupts the swap.
- **Duplicate guard** — `[1,1]`-style inputs would otherwise spin forever.
- **Collect loop** — every unmatched home index is a disappeared number.

## Complexity Analysis

- **Time Complexity:** $O(n)$ — each swap finalizes one element; two linear passes.
- **Space Complexity:** $O(1)$ extra (the output list is not counted toward auxiliary space).

## Similar Problems

1. [LeetCode 41. First Missing Positive](https://leetcode.com/problems/first-missing-positive/) — return only the first missing positive.
2. [LeetCode 442. Find All Duplicates in an Array](https://leetcode.com/problems/find-all-duplicates-in-an-array/) — same cyclic sort, report repeats.
3. [LeetCode 268. Missing Number](https://leetcode.com/problems/missing-number/) — single missing value via XOR/sum or cyclic sort.
