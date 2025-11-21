# Maximum Sum Circular Subarray

## Problem Description

**Problem Link:** [Maximum Sum Circular Subarray](https://leetcode.com/problems/maximum-sum-circular-subarray/)

Given a **circular integer array** `nums` of length `n`, return *the maximum possible sum of a non-empty **subarray** of* `nums`.

A **circular array** means the end of the array connects to the beginning of the array. Formally, the next element of `nums[i]` is `nums[(i + 1) % n]` and the previous element of `nums[i]` is `nums[(i - 1 + n) % n]`.

A **subarray** may only include each element of the fixed buffer `nums` at most once. Formally, for a subarray `nums[i], nums[i+1], ..., nums[j]`, there does not exist `i <= k1, k2 <= j` with `k1 % n == k2 % n`.

**Example 1:**
```
Input: nums = [1,-2,3,-2]
Output: 3
Explanation: Subarray [3] has maximum sum 3.
```

**Example 2:**
```
Input: nums = [5,-3,5]
Output: 10
Explanation: Subarray [5,5] has maximum sum 10.
```

**Constraints:**
- `n == nums.length`
- `1 <= n <= 3 * 10^4`
- `-3 * 10^4 <= nums[i] <= 3 * 10^4`

## Intuition/Main Idea

In a circular array, maximum subarray can be:
1. Normal maximum subarray (not wrapping around)
2. Wrapping around: total sum - minimum subarray

**Core Algorithm:**
- Find maximum subarray sum (Kadane's algorithm)
- Find minimum subarray sum
- Answer = max(maxSum, totalSum - minSum)
- Handle edge case: if all negative, return maxSum

**Why this works:** If maximum wraps around, it means we're excluding the minimum subarray. So we take total - minimum.

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|------------------------|-----------------------------------|
| Find max circular sum | Kadane + wrap-around - Lines 6-30 |
| Normal maximum | Kadane's algorithm - Lines 8-13 |
| Wrapping maximum | Total - minimum - Lines 15-20, 25 |

## Final Java Code & Learning Pattern (Full Content)

```java
class Solution {
    public int maxSubarraySumCircular(int[] nums) {
        int n = nums.length;
        
        // Kadane's algorithm for maximum subarray
        int maxSum = nums[0];
        int currentMax = nums[0];
        
        // Kadane's algorithm for minimum subarray
        int minSum = nums[0];
        int currentMin = nums[0];
        int totalSum = nums[0];
        
        for (int i = 1; i < n; i++) {
            totalSum += nums[i];
            
            // Maximum subarray (Kadane's)
            currentMax = Math.max(nums[i], currentMax + nums[i]);
            maxSum = Math.max(maxSum, currentMax);
            
            // Minimum subarray (for wrap-around case)
            currentMin = Math.min(nums[i], currentMin + nums[i]);
            minSum = Math.min(minSum, currentMin);
        }
        
        // Maximum circular sum is either:
        // 1. Normal maximum subarray (not wrapping)
        // 2. Total sum - minimum subarray (wrapping around)
        // Edge case: if all negative, totalSum - minSum = 0, so use maxSum
        if (maxSum < 0) {
            return maxSum; // All negative
        }
        
        return Math.max(maxSum, totalSum - minSum);
    }
}
```

## Complexity Analysis

**Time Complexity:** $O(n)$ where $n$ is array length. Single pass through array.

**Space Complexity:** $O(1)$. We only use a constant amount of extra space.

## Similar Problems

- [Maximum Subarray](https://leetcode.com/problems/maximum-subarray/) - Kadane's algorithm
- [Best Time to Buy and Sell Stock](https://leetcode.com/problems/best-time-to-buy-and-sell-stock/) - Similar optimization
- [Product of Array Except Self](https://leetcode.com/problems/product-of-array-except-self/) - Array manipulation

