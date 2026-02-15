# Longest Strictly Increasing Subsequence With Non-Zero Bitwise AND

## Problem Description

**Problem Link:** [Longest Strictly Increasing Subsequence With Non-Zero Bitwise AND](https://leetcode.com/problems/longest-strictly-increasing-subsequence-with-non-zero-bitwise-and/)

You are given an integer array `nums`.

Return the length of the longest strictly increasing subsequence in `nums` whose bitwise AND is non-zero. If no such subsequence exists, return `0`.

A subsequence keeps the original relative order of elements, but you may delete any elements.

**Example 1:**
```
Input: nums = [5,4,7]
Output: 2
Explanation:
One longest strictly increasing subsequence is [5, 7].
Bitwise AND: 5 AND 7 = 5 (non-zero).
```

**Example 2:**
```
Input: nums = [2,3,6]
Output: 3
Explanation:
The longest strictly increasing subsequence is [2, 3, 6].
Bitwise AND: 2 AND 3 AND 6 = 2 (non-zero).
```

**Example 3:**
```
Input: nums = [0,1]
Output: 1
Explanation:
One longest strictly increasing subsequence is [1].
Bitwise AND: 1 (non-zero).
```

## Intuition/Main Idea

### Key observation: AND is non-zero means “there exists a bit that survives”

A bitwise AND becomes non-zero **only if there is at least one bit position** (say bit `b`) such that:

- every number in the chosen subsequence has bit `b` set to `1`.

If that happens, then the AND of the whole subsequence will still have bit `b = 1`, so the final AND is non-zero.

So the problem becomes:

- pick a bit `b`
- keep only the numbers that have bit `b` set
- find the LIS length in that filtered sequence
- take the maximum over all bits

### Why this works

- If a subsequence has AND > 0, then at least one bit is common to all of its elements.
  - Choose that common bit `b`.
  - Every element of that subsequence is included in the “bit `b` set” filtered sequence.
  - Therefore, the LIS length among numbers with bit `b` set is at least that subsequence length.

- Conversely, any increasing subsequence chosen entirely from numbers that share a bit `b` will have AND with that bit set, hence AND > 0.

So the answer is:

- `max_b LIS(filtered_by_bit_b)`

### Computing LIS efficiently (Patience sorting)

For each bit `b`, we compute LIS length in `O(n log n)` using the classic tails-array method:

- `tails[len]` = the smallest possible ending value of an increasing subsequence of length `len + 1`
- for each value `x`, binary search the first position `pos` where `tails[pos] >= x` and replace it
- the size of `tails` at the end is the LIS length (strictly increasing because we use `>=` in the lower bound)

Number of bits is small (at most 31 for typical constraints), so total complexity is about `31 * n log n`.

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|------------------------|-----------------------------------|
| Strictly increasing subsequence | Patience-sorting LIS with lower bound (lines 31-60) |
| Bitwise AND of chosen subsequence is non-zero | Iterate each bit and filter by `(num & (1<<bit)) != 0` (lines 10-29) |
| Return 0 if no such subsequence exists | If no bit yields any element, max stays 0 (lines 7-29) |

## Final Java Code & Learning Pattern (Full Content)

```java
import java.util.Arrays;

class Solution {
    public int longestIncreasingSubsequenceWithNonZeroAnd(int[] nums) {
        int bestAnswer = 0;

        // We only need to try each bit position.
        // If values can fit in int, bits 0..30 are enough for non-negative nums.
        for (int bit = 0; bit <= 30; bit++) {
            int lisForThisBit = lisLengthConsideringOnlyNumbersWithBit(nums, bit);
            bestAnswer = Math.max(bestAnswer, lisForThisBit);
        }

        return bestAnswer;
    }

    private int lisLengthConsideringOnlyNumbersWithBit(int[] nums, int bit) {
        // tails[i] will store the smallest ending value of an increasing subsequence of length i+1.
        int[] tails = new int[nums.length];
        int size = 0;

        int mask = 1 << bit;

        for (int value : nums) {
            // Only keep values that have this bit set.
            if ((value & mask) == 0) {
                continue;
            }

            // Find the insertion point (lower bound) for value in tails[0..size).
            // Using first index with tails[idx] >= value enforces STRICT increasing.
            int pos = Arrays.binarySearch(tails, 0, size, value);
            if (pos < 0) {
                pos = -pos - 1;
            }

            tails[pos] = value;

            if (pos == size) {
                size++;
            }
        }

        return size;
    }
}
```

## Complexity Analysis

**Time Complexity:** $O(B \cdot n \log n)$ where `B` is the number of bits we try (at most 31).

**Space Complexity:** $O(n)$ for the `tails` array.

## Similar Problems

- [Longest Increasing Subsequence](https://leetcode.com/problems/longest-increasing-subsequence/) - same LIS technique
- [Reverse Bits](https://leetcode.com/problems/reverse-bits/) - bit-level thinking practice
- [Bitwise AND of Numbers Range](https://leetcode.com/problems/bitwise-and-of-numbers-range/) - reasoning about AND behavior across sets
