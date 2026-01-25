# Find the Smallest Divisor Given a Threshold

## Problem Description

**Problem Link:** [Find the Smallest Divisor Given a Threshold](https://leetcode.com/problems/find-the-smallest-divisor-given-a-threshold/)

Given an array of integers `nums` and an integer `threshold`, we want to choose a **positive integer divisor** `d` such that:

- For every `nums[i]`, compute `ceil(nums[i] / d)`
- Sum all these values

Return the **smallest** divisor `d` such that the sum is **less than or equal to** `threshold`.

**Example 1:**
```
Input: nums = [1,2,5,9], threshold = 6
Output: 5
Explanation:
For d = 5 => ceil(1/5)+ceil(2/5)+ceil(5/5)+ceil(9/5)
          = 1 + 1 + 1 + 2 = 5 <= 6
For d = 4 => 1 + 1 + 2 + 3 = 7 > 6
So the smallest valid divisor is 5.
```

**Example 2:**
```
Input: nums = [44,22,33,11,1], threshold = 5
Output: 44
```

**Constraints:**
- `1 <= nums.length <= 5 * 10^4`
- `1 <= nums[i] <= 10^6`
- `nums.length <= threshold <= 10^6`

## Intuition/Main Idea

This is a **binary search on answer space** problem.

Define a function `isValid(d)`:
- Compute `sum = Σ ceil(nums[i] / d)`
- Return `true` if `sum <= threshold`, else `false`

Key monotonic observation:
- If a divisor `d` is **valid**, then any **larger** divisor is also valid (because dividing by a larger number makes each `ceil(nums[i]/d)` smaller or equal).
- If a divisor `d` is **invalid**, then any **smaller** divisor is also invalid.

So we can binary search the smallest `d` such that `isValid(d)` is `true`.

Search bounds:
- Minimum divisor is `1`
- Maximum divisor is `max(nums)` (because with `d = max(nums)`, each term is at most `1`, so the sum is `<= nums.length` and constraints guarantee `threshold >= nums.length`)

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|-------------------------|------------------------------------|
| Find smallest divisor `d` such that `Σ ceil(nums[i]/d) <= threshold` | Binary search loop (`while (left < right)`) and validation check (`isValid`) |
| Use `ceil(nums[i] / d)` efficiently without floating-point | `sum += (num + d - 1) / d;` inside `isValid` |
| Work within input constraints up to `5 * 10^4` elements | `O(n log max(nums))` approach with one pass per mid |

## Final Java Code & Learning Pattern (Full Content)

```java
class Solution {
    public int smallestDivisor(int[] nums, int threshold) {
        // We binary-search the answer (the divisor).
        // The smallest divisor is 1.
        // The largest divisor we ever need to consider is max(nums).
        int left = 1;
        int right = getMax(nums);

        // Binary Search on Answer Space:
        // Maintain invariant:
        // - All divisors < left are invalid
        // - All divisors >= right are potentially valid
        // We shrink [left, right] until they converge to the smallest valid divisor.
        while (left < right) {
            int mid = left + (right - left) / 2;

            // If mid works, we try to find a smaller divisor (go left).
            // Note: we keep mid in the search space by doing right = mid,
            // because mid itself could still be the minimum valid divisor.
            if (isValid(nums, threshold, mid)) {
                right = mid;
            } else {
                // If mid doesn't work, we need a larger divisor.
                // We can exclude mid (and everything smaller) by doing left = mid + 1.
                left = mid + 1;
            }
        }

        // At the end, left == right and points to the smallest divisor that is valid.
        return left;
    }

    private boolean isValid(int[] nums, int threshold, int divisor) {
        // Compute Σ ceil(nums[i] / divisor)
        // Using integer math:
        // ceil(a / b) = (a + b - 1) / b
       // OR 
        // ceil(a / b) = (long) Math.ceil((double) a / b)
        long sum = 0;

        for (int num : nums) {
            sum += (num + divisor - 1) / divisor;

            // Early exit: if we already exceeded threshold, no need to continue.
            if (sum > threshold) {
                return false;
            }
        }

        return sum <= threshold;
    }

    private int getMax(int[] nums) {
        int max = 0;
        for (int x : nums) {
            max = Math.max(max, x);
        }
        return max;
    }
}
```

## Binary Search Problems Explanation

### How to decide whether to use `<` or `<=` in the main loop condition

We use `while (left < right)` when we are searching for the **minimum** value that satisfies a condition (a “first true” / lower-bound style binary search).

- With `left < right`, we stop exactly when both pointers converge.
- When `left == right`, there is exactly one candidate left, and it is the smallest valid answer by invariant.

### How to decide if the pointers should be set to `mid + 1` or `mid - 1` or `mid`

This depends on whether `mid` could still be the answer.

- When `isValid(mid)` is `true`:
  - `mid` is a **valid candidate** and might be the minimum.
  - So we must keep it in the search space: `right = mid`.

- When `isValid(mid)` is `false`:
  - `mid` is **not** a valid answer.
  - Because of monotonicity, all divisors `< mid` are also invalid.
  - So we exclude `mid` and everything smaller: `left = mid + 1`.

### How to decide what would be the return value

For this “minimum valid value” pattern:
- Return `left` (or `right`) after the loop ends, because `left == right` and it points to the smallest divisor that is valid.

## Complexity Analysis

- **Time Complexity:** $O(n \log M)$ where `n = nums.length` and `M = max(nums)`. Each validation is a single pass over the array, and binary search takes `log M` steps.
- **Space Complexity:** $O(1)$ extra space.

## Similar Problems

- [875. Koko Eating Bananas](https://leetcode.com/problems/koko-eating-bananas/) - binary search on minimum speed
- [1011. Capacity To Ship Packages Within D Days](https://leetcode.com/problems/capacity-to-ship-packages-within-d-days/) - binary search on minimum capacity
- [1482. Minimum Number of Days to Make m Bouquets](https://leetcode.com/problems/minimum-number-of-days-to-make-m-bouquets/) - binary search on minimum days
