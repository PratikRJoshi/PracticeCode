# 442. Find All Duplicates in an Array

## Problem Description

[Find All Duplicates in an Array](https://leetcode.com/problems/find-all-duplicates-in-an-array/)

Given an integer array `nums` of length `n` where all values are in `[1, n]` and each appears **once or twice**, return all values that appear **twice**. Must run in O(n) time and use O(1) extra space (excluding the output).

### Example 1

Input: `nums = [4,3,2,7,8,2,3,1]`

Output: `[2,3]`

### Example 2

Input: `nums = [1,1,2]`

Output: `[1]`

### Constraints

- `n == nums.length`, `1 <= n <= 10^5`, `1 <= nums[i] <= n`, each element appears once or twice.

## Intuition / Main Idea

Same **cyclic sort** machinery as First Missing Positive (#41) and Find All Disappeared (#448): place value `v` at index `v - 1`. The only change is the read-out.

After sorting, at a mismatched index `i`, the value **squatting there** (`nums[i]`) is a duplicate — its real home `nums[i]-1` is already occupied by an identical twin, so it had nowhere to go.

### Build the intuition step by step

1. Cyclic-sort: escort each value to its home index `v - 1`.
2. The duplicate guard (`nums[i] != nums[nums[i]-1]`) is exactly what *strands* a second copy in a wrong slot.
3. Second pass: every index `i` with `nums[i] != i + 1` contributes `nums[i]` (the stranded duplicate) to the result.

### Why this works

A value that found its home leaves `nums[v-1] == v`. A second copy of `v` can never claim that home (the guard blocks the swap), so it ends up parked in some wrong slot — and reading that slot's value gives the duplicate directly.

## Code Mapping

| Problem Requirement (@) | Java Code Section |
|---|---|
| Place value at home index | freeze `target = nums[i]-1`, then swap |
| Strand the second copy | duplicate guard `nums[i] != nums[nums[i]-1]` |
| Collect duplicates | mismatched slot → add `nums[i]` (not `i+1`) |

## Final Java Code & Learning Pattern

```java
// [Pattern: Cyclic sort (index-as-hash, in-place) — read mismatched VALUE]
import java.util.ArrayList;
import java.util.List;

class Solution {
    public List<Integer> findDuplicates(int[] nums) {
        List<Integer> result = new ArrayList<>();

        for (int i = 0; i < nums.length; i++) {
            while (nums[i] != i + 1 && nums[i] != nums[nums[i] - 1]) {
                int target = nums[i] - 1;   // freeze BEFORE swapping
                int temp = nums[i];
                nums[i] = nums[target];
                nums[target] = temp;
            }
        }

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != i + 1) result.add(nums[i]);   // squatter is the duplicate
        }
        return result;
    }
}
```

### Why each part exists

- **Cyclic-sort loop** — identical to #448; sends each value to its home unless a twin already lives there.
- **Duplicate guard** — both prevents the infinite loop *and* leaves the second copy stranded for collection.
- **`result.add(nums[i])`** — the key difference from #448: we want the misplaced value, not the empty home's index.

## Complexity Analysis

- **Time Complexity:** $O(n)$ — each swap finalizes one element; two linear passes.
- **Space Complexity:** $O(1)$ extra (output list not counted).

## Similar Problems

1. [LeetCode 448. Find All Numbers Disappeared in an Array](https://leetcode.com/problems/find-all-numbers-disappeared-in-an-array/) — same loop, collect `i+1` for missing.
2. [LeetCode 41. First Missing Positive](https://leetcode.com/problems/first-missing-positive/) — smallest missing positive.
3. [LeetCode 287. Find the Duplicate Number](https://leetcode.com/problems/find-the-duplicate-number/) — single duplicate, Floyd's cycle detection (read-only array).
