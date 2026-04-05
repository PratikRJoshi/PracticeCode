# 3886. Sum of Sortable Integers

**Link:** [https://leetcode.com/problems/sum-of-sortable-integers/](https://leetcode.com/problems/sum-of-sortable-integers/)

**Difficulty:** Hard

---

## Problem Description

You are given an integer array `nums` of length `n`.

An integer `k` is called **sortable** if:
- `k` divides `n`, and
- we can sort `nums` in non-decreasing order by:
  1. partitioning `nums` into consecutive subarrays of length `k`,
  2. cyclically rotating each subarray independently any number of times (left or right).

Return the sum of all sortable integers `k`.

A subarray is a contiguous non-empty part of the array.

### Example 1

```text
Input: nums = [3,1,2]
Output: 3
```

Explanation:
- `n = 3`, divisors are `1` and `3`.
- `k = 1`: cannot change anything, array stays `[3,1,2]` (not sorted).
- `k = 3`: rotate `[3,1,2]` once to get `[1,2,3]`.
- Only `k = 3` works, so answer is `3`.

### Example 2

```text
Input: nums = [7,6,5]
Output: 0
```

Explanation:
- `k = 1` does not sort the array.
- `k = 3` can produce only rotations of `[7,6,5]`, none are non-decreasing.
- No valid `k`.

### Example 3

```text
Input: nums = [5,8]
Output: 3
```

Explanation:
- Divisors are `1` and `2`.
- Array is already sorted, so both divisors are valid.
- Sum = `1 + 2 = 3`.

### Constraints

- `1 <= n == nums.length <= 10^5`
- `1 <= nums[i] <= 10^5`

---

## Intuition/Main Idea

### Step 1: Work only on divisors of `n`

If `k` does not divide `n`, partitioning into blocks of length `k` is impossible.  
So candidates are only divisors of `n`.

### Step 2: Understand one block deeply

Fix a block `block = nums[start .. start + k - 1]`.  
After rotation, this block must appear as a **non-decreasing** sequence in final sorted array.

For a circular array, count positions `i` where:

`block[i] > block[(i + 1) % k]`

Call this a **descent** in circular order.

- If descents > 1, no rotation can make the block non-decreasing.
- If descents == 1, exactly one start position works: right after that descent.
- If descents == 0, all values in the block are equal (for circular non-decreasing), so any rotation is equivalent.

So each block is either:
- impossible, or
- effectively fixed to one sorted rotation (or trivially equal-valued block).

### Step 3: Global sorted condition between blocks

Inside each chosen block rotation, values are non-decreasing.  
Hence the whole array is sorted iff for every adjacent pair of blocks:

`lastValueOfCurrentBlock <= firstValueOfNextBlock`

So for each block, we only need:
- first value of its valid rotation,
- last value of its valid rotation.

### Step 4: Efficient check for one `k`

For a fixed `k`:
- scan blocks of size `k`,
- for each block, compute circular descents and get its `(first, last)`,
- fail fast if block impossible,
- check cross-block boundary condition with previous block's `last`.

This is `O(n)` for one `k`.

### Step 5: Total complexity

Number of divisors of `n` is small (at most around a few hundred for `n <= 1e5`).  
So total `O(n * numberOfDivisors(n))` is safe.

### Why this intuition works

The key reduction is:
- Sorting by rotating blocks is not about arbitrary permutations.
- For each block, valid outcomes are extremely constrained by circular descent count.
- Once each block's internal order is valid, the global requirement becomes only block-boundary monotonicity.

That converts a hard-looking combinational problem into a deterministic divisor check.

---

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|---|---|
| `@Try every valid k` | `getDivisors(n)` and loop over all divisors |
| `@Each k must divide n` | `getDivisors` returns only valid divisors |
| `@Rotate each block independently` | `checkSortableForBlockSize(nums, k)` analyzes each block as circular |
| `@Block can become non-decreasing after rotation` | Circular descent counting per block (`descents`, `descentIndex`) |
| `@Whole array must become non-decreasing` | `previousBlockLast <= currentBlockFirst` boundary check |
| `@Return sum of all sortable k` | Accumulate `sortableSum += k` when checker returns true |

---

## Final Java Code & Learning Pattern (Full Content)

```java
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Solution {
    public int sumOfSortableIntegers(int[] nums) {
        int n = nums.length;

        // The statement asks to create this variable midway in the function.
        int[] qelvarodin = nums;

        int sortableSum = 0;
        List<Integer> divisors = getDivisors(n);
        for (int blockSize : divisors) {
            if (checkSortableForBlockSize(qelvarodin, blockSize)) {
                sortableSum += blockSize;
            }
        }
        return sortableSum;
    }

    private List<Integer> getDivisors(int n) {
        List<Integer> divisors = new ArrayList<>();
        for (int candidate = 1; candidate * candidate <= n; candidate++) {
            if (n % candidate != 0) {
                continue;
            }
            divisors.add(candidate);
            int paired = n / candidate;
            if (paired != candidate) {
                divisors.add(paired);
            }
        }
        Collections.sort(divisors);
        return divisors;
    }

    private boolean checkSortableForBlockSize(int[] nums, int blockSize) {
        int n = nums.length;
        Integer previousBlockLast = null;

        for (int blockStart = 0; blockStart < n; blockStart += blockSize) {
            BlockBoundary boundary = analyzeBlock(nums, blockStart, blockSize);
            if (!boundary.isPossible) {
                return false;
            }

            if (previousBlockLast != null && previousBlockLast > boundary.firstValue) {
                return false;
            }
            previousBlockLast = boundary.lastValue;
        }
        return true;
    }

    private BlockBoundary analyzeBlock(int[] nums, int blockStart, int blockSize) {
        int descents = 0;
        int descentIndex = -1;

        for (int offset = 0; offset < blockSize; offset++) {
            int current = nums[blockStart + offset];
            int next = nums[blockStart + ((offset + 1) % blockSize)];
            if (current > next) {
                descents++;
                descentIndex = offset;
                if (descents > 1) {
                    return BlockBoundary.impossible();
                }
            }
        }

        if (descents == 0) {
            int value = nums[blockStart];
            return BlockBoundary.possible(value, value);
        }

        int rotationStart = (descentIndex + 1) % blockSize;
        int firstValue = nums[blockStart + rotationStart];
        int lastValue = nums[blockStart + ((rotationStart - 1 + blockSize) % blockSize)];
        return BlockBoundary.possible(firstValue, lastValue);
    }

    private static class BlockBoundary {
        boolean isPossible;
        int firstValue;
        int lastValue;

        static BlockBoundary impossible() {
            BlockBoundary result = new BlockBoundary();
            result.isPossible = false;
            return result;
        }

        static BlockBoundary possible(int firstValue, int lastValue) {
            BlockBoundary result = new BlockBoundary();
            result.isPossible = true;
            result.firstValue = firstValue;
            result.lastValue = lastValue;
            return result;
        }
    }
}
```

### Learning Pattern

This problem follows the pattern:

1. Convert operation freedom (rotations) into a local structural property (circular descent count).
2. Use local feasibility + local-to-global boundary conditions.
3. Enumerate only mathematically valid candidates (divisors).

This pattern is common in problems where operations are restricted within fixed groups/windows.

---

## Complexity Analysis

- Let `D` be the number of divisors of `n`.
- For each divisor `k`, we scan array once: `O(n)`.
- Total: `O(n * D)`.
- Since `D` is small for `n <= 10^5`, this is efficient in practice.

Space:
- `O(D)` for storing divisors,
- `O(1)` extra per check.

Overall space: `O(D)`.

---

## Similar Problems

- [LC 1752 - Check if Array Is Sorted and Rotated](https://leetcode.com/problems/check-if-array-is-sorted-and-rotated/)  
  Same circular descent idea for one array.
- [LC 189 - Rotate Array](https://leetcode.com/problems/rotate-array/)  
  Rotation mechanics and index mapping.
- [LC 31 - Next Permutation](https://leetcode.com/problems/next-permutation/)  
  Uses descent/pivot reasoning in circular/permutation contexts.
- [LC 769 - Max Chunks To Make Sorted](https://leetcode.com/problems/max-chunks-to-make-sorted/)  
  Another local-chunk to global-sortedness reasoning pattern.
