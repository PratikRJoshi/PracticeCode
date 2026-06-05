# 41. First Missing Positive

## Problem Description

[First Missing Positive](https://leetcode.com/problems/first-missing-positive/)

Given an unsorted integer array `nums`, return the smallest positive integer that is **not** present. The algorithm must run in `O(n)` time and use `O(1)` auxiliary space.

### Example 1

Input: `nums = [1,2,0]`

Output: `3`

### Example 2

Input: `nums = [3,4,-1,1]`

Output: `2`

### Example 3

Input: `nums = [7,8,9,11,12]`

Output: `1`

### Constraints

- `1 <= nums.length <= 10^5`, `-2^31 <= nums[i] <= 2^31 - 1`.

## Intuition / Main Idea

**Pigeonhole bound:** an array of size `n` holds at most `n` distinct positive integers, so the answer must lie in `[1, n+1]`. That lets us ignore huge/negative values and treat the array itself as a hash table.

**Cyclic sort (index-as-hash):** place value `v` at index `v - 1`. After this, a correct slot reads `nums[i] == i + 1`. The first index that violates this is the missing positive; if none do, the answer is `n + 1`.

### Build the intuition step by step

1. Only values in `[1, n]` matter — everything else (≤0 or >n) can never affect the answer.
2. Repeatedly swap each in-range value to its home index `v - 1`.
3. Use a `while` (not `if`): after a swap, the value that landed in `nums[i]` may itself need placing.
4. Scan once: first `i` with `nums[i] != i + 1` → return `i + 1`. None → return `n + 1`.

### Why this works

Each successful swap puts at least one value in its permanent home, so total swaps ≤ n → the nested `while` is amortized O(n). No extra structure is needed; the array encodes membership by position.

## Code Mapping

| Problem Requirement (@) | Java Code Section |
|---|---|
| Ignore out-of-range values | `nums[i] > 0 && nums[i] <= n` guards |
| Avoid infinite loop on duplicates | `nums[i] != nums[nums[i] - 1]` |
| Place value at home index | freeze `j = nums[i]-1`, then swap |
| Find the missing positive | first `i` with `nums[i] != i + 1` |

## Final Java Code & Learning Pattern

```java
// [Pattern: Cyclic sort (index-as-hash, in-place)]
class Solution {
    public int firstMissingPositive(int[] nums) {
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            // bounds guards MUST precede the nums[nums[i]-1] dereference (&& short-circuit)
            while (nums[i] > 0 && nums[i] <= n && nums[i] != nums[nums[i] - 1]) {
                int j = nums[i] - 1;   // freeze target BEFORE swapping (moving-target trap)
                int temp = nums[i];
                nums[i] = nums[j];
                nums[j] = temp;
            }
        }

        for (int i = 0; i < n; i++) {
            if (nums[i] != i + 1) return i + 1;
        }
        return n + 1;
    }
}
```

### Why each part exists

- **Bounds guards first** — Java evaluates `&&` left to right; a negative/oversized `nums[i]` would throw inside `nums[nums[i]-1]` if checked after.
- **Frozen `j`** — `nums[i]` changes mid-swap; reusing it for the second write corrupts the array.
- **Duplicate guard** — without `nums[i] != nums[nums[i]-1]`, inputs like `[2,2]` loop forever.
- **Final scan** — the first home that holds the wrong tenant is the smallest missing positive.

## Complexity Analysis

- **Time Complexity:** $O(n)$ — each swap finalizes one element, so at most `n` swaps plus two linear passes.
- **Space Complexity:** $O(1)$ — in-place; no auxiliary structures.

## Similar Problems

1. [LeetCode 448. Find All Numbers Disappeared in an Array](https://leetcode.com/problems/find-all-numbers-disappeared-in-an-array/) — same cyclic sort, collect all mismatches.
2. [LeetCode 442. Find All Duplicates in an Array](https://leetcode.com/problems/find-all-duplicates-in-an-array/) — cyclic sort, report repeated values.
3. [LeetCode 268. Missing Number](https://leetcode.com/problems/missing-number/) — single missing in `[0, n]`.
