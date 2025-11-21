# Minimum Size Subarray Sum

## Problem Description

**Problem Link:** [Minimum Size Subarray Sum](https://leetcode.com/problems/minimum-size-subarray-sum/)

Given an array of positive integers `nums` and a positive integer `target`, return the minimal length of a **subarray** whose sum is greater than or equal to `target`. If there is no such subarray, return `0` instead.

**Example 1:**
```
Input: target = 7, nums = [2,3,1,2,4,3]
Output: 2
Explanation: The subarray [4,3] has the minimal length under the problem constraint.
```

**Example 2:**
```
Input: target = 4, nums = [1,4,4]
Output: 1
```

**Example 3:**
```
Input: target = 11, nums = [1,1,1,1,1,1,1,1]
Output: 0
```

**Constraints:**
- `1 <= target <= 10^9`
- `1 <= nums.length <= 10^5`
- `1 <= nums[i] <= 10^4`

## Intuition/Main Idea

This is a classic sliding window problem. We maintain a window and expand/contract it to find the minimum subarray with sum >= target.

**Core Algorithm:**
- Use two pointers: left and right
- Expand window by moving right pointer, adding elements to sum
- When sum >= target, try to shrink window from left to minimize length
- Track minimum length seen

**Why sliding window:** We need to find contiguous subarrays. The sliding window efficiently explores all valid subarrays by expanding and contracting, avoiding redundant calculations.

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|------------------------|-----------------------------------|
| Find minimum subarray length | Sliding window - Lines 6-20 |
| Sum >= target | Sum tracking - Lines 7, 10, 15 |
| Expand window | Right pointer - Line 9 |
| Shrink window | Left pointer - Line 14 |
| Track minimum length | minLen variable - Lines 5, 17 |

## Final Java Code & Learning Pattern (Full Content)

```java
class Solution {
    public int minSubArrayLen(int target, int[] nums) {
        int n = nums.length;
        int minLen = Integer.MAX_VALUE;
        
        // Sliding window: left and right pointers
        int left = 0;
        int sum = 0;
        
        // Expand window by moving right pointer
        for (int right = 0; right < n; right++) {
            // Add current element to sum
            sum += nums[right];
            
            // When sum >= target, try to shrink window from left
            // This minimizes the subarray length while maintaining sum >= target
            while (sum >= target) {
                // Update minimum length
                // Current window length is (right - left + 1)
                minLen = Math.min(minLen, right - left + 1);
                
                // Remove leftmost element and shrink window
                sum -= nums[left];
                left++;
            }
            // If sum < target, continue expanding window
        }
        
        // Return 0 if no valid subarray found, otherwise return minimum length
        return minLen == Integer.MAX_VALUE ? 0 : minLen;
    }
}
```

## Complexity Analysis

**Time Complexity:** $O(n)$ where $n$ is the length of the array. Each element is visited at most twice (once by right pointer, once by left pointer).

**Space Complexity:** $O(1)$. We only use a constant amount of extra space.

## Similar Problems

- [Minimum Window Substring](https://leetcode.com/problems/minimum-window-substring/) - Similar sliding window pattern
- [Longest Substring Without Repeating Characters](https://leetcode.com/problems/longest-substring-without-repeating-characters/) - Sliding window variant
- [Fruit Into Baskets](https://leetcode.com/problems/fruit-into-baskets/) - Similar window technique

