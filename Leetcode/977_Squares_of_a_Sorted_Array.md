# Squares of a Sorted Array

## Problem Description

**Problem Link:** [Squares of a Sorted Array](https://leetcode.com/problems/squares-of-a-sorted-array/)

Given an integer array `nums` sorted in **non-decreasing** order, return an array of **the squares of each number** sorted in non-decreasing order.

**Example 1:**
```
Input: nums = [-4,-1,0,3,10]
Output: [0,1,9,16,100]
Explanation: After squaring, the array becomes [16,1,0,9,100].
After sorting, it becomes [0,1,9,16,100].
```

**Example 2:**
```
Input: nums = [-7,-3,2,3,11]
Output: [4,9,9,49,121]
```

**Constraints:**
- `1 <= nums.length <= 10^4`
- `-10^4 <= nums[i] <= 10^4`
- `nums` is sorted in **non-decreasing** order.

## Intuition/Main Idea

The key insight is that after squaring, the largest squares come from either end of the array (most negative or most positive). We can use two pointers starting from both ends and fill the result array from right to left.

**Core Algorithm:**
- Use two pointers: one at start (for negative numbers), one at end (for positive numbers)
- Compare absolute values (or squares) of elements at both pointers
- Place the larger square at the end of result array
- Move the pointer that contributed the larger square
- Fill result array from right to left

**Why two pointers from ends:** After squaring, the array has a "V" shape - smallest squares are in the middle (near zero), largest are at the ends. By comparing ends and filling backwards, we can build the sorted result in one pass.

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|------------------------|-----------------------------------|
| Square each number | Square calculation - Lines 11, 13 |
| Sort squares | Two-pointer merge - Lines 6-18 |
| Compare from ends | Pointer comparison - Line 10 |
| Fill result backwards | Right-to-left filling - Line 15 |
| Handle negative numbers | Left pointer - Line 7 |
| Handle positive numbers | Right pointer - Line 8 |

## Final Java Code & Learning Pattern (Full Content)

```java
class Solution {
    public int[] sortedSquares(int[] nums) {
        int n = nums.length;
        // Result array to store sorted squares
        int[] result = new int[n];
        
        // Two pointers: left for negative numbers, right for positive numbers
        // After squaring, largest values come from the ends
        int left = 0;
        int right = n - 1;
        
        // Fill result array from right to left (largest to smallest)
        // We start from the end because we know the largest squares are at the ends
        for (int i = n - 1; i >= 0; i--) {
            // Calculate squares
            int leftSquare = nums[left] * nums[left];
            int rightSquare = nums[right] * nums[right];
            
            // Compare squares: larger square goes to current position
            // If left square is larger (or equal), use it and move left pointer right
            if (leftSquare >= rightSquare) {
                result[i] = leftSquare;
                left++;
            } else {
                // Right square is larger, use it and move right pointer left
                result[i] = rightSquare;
                right--;
            }
        }
        
        return result;
    }
}
```

## Complexity Analysis

**Time Complexity:** $O(n)$ where $n$ is the length of the array. We traverse the array once with two pointers.

**Space Complexity:** $O(n)$ for the result array. This is required for the output.

## Similar Problems

- [Merge Sorted Array](https://leetcode.com/problems/merge-sorted-array/) - Similar two-pointer merge pattern
- [Two Sum II](https://leetcode.com/problems/two-sum-ii-input-array-is-sorted/) - Two pointers on sorted array
- [3Sum](https://leetcode.com/problems/3sum/) - Uses similar two-pointer technique

