# 3Sum Closest

## Problem Description

**Problem Link:** [3Sum Closest](https://leetcode.com/problems/3sum-closest/)

Given an integer array `nums` of length `n` and an integer `target`, find three integers in `nums` such that the sum is closest to `target`.

Return *the sum of the three integers*.

You may assume that each input would have exactly one solution.

**Example 1:**
```
Input: nums = [-1,2,1,-4], target = 1
Output: 2
Explanation: The sum that is closest to the target is 2. (-1 + 2 + 1 = 2).
```

**Example 2:**
```
Input: nums = [0,0,0], target = 1
Output: 0
```

**Constraints:**
- `3 <= nums.length <= 500`
- `-1000 <= nums[i] <= 1000`
- `-10^4 <= target <= 10^4`

## Intuition/Main Idea

This is similar to 3Sum, but instead of finding exact sum, we find the sum closest to target. We use sorting + two pointers.

**Core Algorithm:**
- Sort the array
- Fix one element, use two pointers for the other two
- For each fixed element, move pointers to minimize difference from target
- Track the closest sum seen so far

**Why sorting + two pointers:** After sorting, we can use two pointers to efficiently find pairs that sum closest to `target - fixed`. Moving pointers based on whether current sum is less than or greater than target allows us to explore the solution space efficiently.

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|------------------------|-----------------------------------|
| Find sum closest to target | Two-pointer search - Lines 8-25 |
| Sort array | Arrays.sort - Line 6 |
| Fix one element | Outer loop - Line 7 |
| Use two pointers | Left and right pointers - Lines 9-10 |
| Track closest sum | closestSum variable - Lines 5, 18-19 |
| Minimize difference | Absolute difference comparison - Line 17 |

## Final Java Code & Learning Pattern (Full Content)

```java
class Solution {
    public int threeSumClosest(int[] nums, int target) {
        // Sort array to enable two-pointer technique
        Arrays.sort(nums);
        
        // Track the closest sum to target
        // Initialize with sum of first three elements
        int closestSum = nums[0] + nums[1] + nums[2];
        
        // Fix one element at a time
        for (int i = 0; i < nums.length - 2; i++) {
            // Two pointers: left starts after fixed element, right at end
            int left = i + 1;
            int right = nums.length - 1;
            
            // Use two pointers to find pair that minimizes difference from target
            while (left < right) {
                // Calculate current sum of three elements
                int currentSum = nums[i] + nums[left] + nums[right];
                
                // Update closestSum if current sum is closer to target
                // We compare absolute differences
                if (Math.abs(currentSum - target) < Math.abs(closestSum - target)) {
                    closestSum = currentSum;
                }
                
                // Move pointers based on comparison with target
                // If current sum is less than target, we need larger sum (move left right)
                if (currentSum < target) {
                    left++;
                } else if (currentSum > target) {
                    // If current sum is greater than target, we need smaller sum (move right left)
                    right--;
                } else {
                    // If current sum equals target, we found exact match
                    // Return immediately as this is the closest possible
                    return currentSum;
                }
            }
        }
        
        return closestSum;
    }
}
```

## Complexity Analysis

**Time Complexity:** $O(n^2)$ where $n$ is the length of the array. Sorting takes $O(n \log n)$, and for each fixed element, we do a two-pointer scan in $O(n)$ time.

**Space Complexity:** $O(1)$ excluding the space for sorting. We only use a constant amount of extra space.

## Similar Problems

- [3Sum](https://leetcode.com/problems/3sum/) - Find exact sum equal to zero
- [3Sum Smaller](https://leetcode.com/problems/3sum-smaller/) - Count triplets with sum less than target
- [Two Sum](https://leetcode.com/problems/two-sum/) - Simpler two-pointer variant

