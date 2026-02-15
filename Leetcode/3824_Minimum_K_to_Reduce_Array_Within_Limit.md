# Minimum K to Reduce Array Within Limit

## Problem Description

**Problem Link:** [Minimum K to Reduce Array Within Limit](https://leetcode.com/problems/minimum-k-to-reduce-array-within-limit/)

You are given a positive integer array `nums`.

For a positive integer `k`, define `nonPositive(nums, k)` as the minimum number of operations needed to make every element of `nums` non-positive.

In one operation, you can choose an index `i` and reduce `nums[i]` by `k`.

Return the minimum value of `k` such that:

- `nonPositive(nums, k) <= k^2`

(Your examples indicate the “limit” is `k^2`. If your statement uses a different limit, tell me and the solution pattern stays the same.)

**Example 1:**
```
Input: nums = [3,7,5]
Output: 3
Explanation:
When k = 3, nonPositive(nums, k) = 6 <= 3^2.
- 3 needs 1 operation: 3 - 3 = 0
- 7 needs 3 operations: 7 - 3 - 3 - 3 = -2
- 5 needs 2 operations: 5 - 3 - 3 = -1
Total operations = 1 + 3 + 2 = 6
```

**Example 2:**
```
Input: nums = [1]
Output: 1
Explanation:
When k = 1, nonPositive(nums, k) = 1 <= 1^2.
```

**Constraints:**
- `1 <= nums.length <= 10^5`
- `1 <= nums[i] <= 10^5`

## Intuition/Main Idea

### Step 1: Compute `nonPositive(nums, k)` for a fixed `k`

Think about a single number `x = nums[i]`.

- In 1 operation, we can subtract `k` once, so `x` becomes `x - k`.
- In 2 operations, `x` becomes `x - 2k`.
- After `t` operations, `x` becomes `x - t * k`.

We want `x` to become **0 or negative**, so we need:

- `x - t * k <= 0`

Rearrange it:

- `t * k >= x`

So `t` is basically “how many `k`-sized chunks fit into `x`”.

Because `t` must be a whole number of operations, we round up:

- `t = ceil(x / k)`

That means total operations for the whole array is:

- `nonPositive(nums, k) = sum( ceil(nums[i] / k) )`

In integer math (so we can code it without floating point):

- `ceil(x / k) = (x + k - 1) / k`

### Step 2: Why we can binary search on `k`

We want the minimum `k` such that:

- `sum(ceil(nums[i]/k)) <= k^2`

As `k` increases:

- The left side `sum(ceil(nums[i]/k))` can only go **down or stay the same**.
  - Bigger `k` means each operation removes more, so you need the same or fewer operations.
- The right side `k^2` always goes **up** as `k` grows.

So the predicate:

- `isValid(k) := (operationsNeeded(k) <= k^2)`

is **monotonic**:

- false, false, ..., false, true, true, ...

This “all false then all true” shape is exactly what binary search needs.

### Step 3: Bounds for binary search

- Minimum `k` is `1`.
- A safe maximum is `max(nums)` because:
  - if `k = max(nums)`, each element needs at most 1 operation, so operationsNeeded <= n
  - and `k^2` is huge enough (especially since `k` is large)

So search range:

- `low = 1`, `high = max(nums)`

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|------------------------|-----------------------------------|
| Minimum `k` such that `nonPositive(nums, k) <= k^2` | Binary search on `k` with monotonic predicate (lines 19-49) |
| `nonPositive(nums, k)` is minimum operations | Per element operations `ceil(x / k)` and sum (lines 27-41) |
| Large `n` up to `1e5` | O(n log max(nums)) approach (overall) |
| Avoid overflow | Use `long` for operation sum and `k^2` (lines 24-41) |

## Final Java Code & Learning Pattern (Full Content)

```java
class Solution {
    public int minimumK(int[] nums) {
        int maxValue = 0;
        for (int value : nums) {
            maxValue = Math.max(maxValue, value);
        }

        int low = 1;
        int high = maxValue;

        // Binary search for the smallest k that satisfies:
        // operationsNeeded(k) <= k^2
        while (low < high) {
            int midK = low + (high - low) / 2;

            if (isValid(nums, midK)) {
                // midK works, so try smaller k
                high = midK;
            } else {
                // midK doesn't work, need a larger k
                low = midK + 1;
            }
        }

        return low;
    }

    private boolean isValid(int[] nums, int k) {
        long maxAllowedOperations = 1L * k * k;
        long operationsNeeded = 0;

        for (int value : nums) {
            // Minimum ops to make value <= 0 by subtracting k each time:
            // ceil(value / k) = (value + k - 1) / k
            operationsNeeded += (value + (long) k - 1) / k;

            // Early stop: once we exceed k^2, we already know it's invalid.
            if (operationsNeeded > maxAllowedOperations) {
                return false;
            }
        }

        return operationsNeeded <= maxAllowedOperations;
    }
}
```

## Complexity Analysis

**Time Complexity:** $O(n \log M)$ where:
- `n = nums.length`
- `M = max(nums)`

We do a binary search over `k` (`log M` steps), and each check scans the array once.

**Space Complexity:** $O(1)$ (binary search + counters)

## Similar Problems

- [Koko Eating Bananas](https://leetcode.com/problems/koko-eating-bananas/) - binary search on an answer `k` with a monotonic feasibility check
- [Minimum Number of Days to Make m Bouquets](https://leetcode.com/problems/minimum-number-of-days-to-make-m-bouquets/) - another classic “binary search the answer”
- [Split Array Largest Sum](https://leetcode.com/problems/split-array-largest-sum/) - feasibility predicate + binary search
