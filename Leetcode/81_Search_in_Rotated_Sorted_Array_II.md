# Search in Rotated Sorted Array II

## Problem Description

**Problem Link:** [Search in Rotated Sorted Array II](https://leetcode.com/problems/search-in-rotated-sorted-array-ii/)

There is an integer array `nums` sorted in non-decreasing order (not necessarily with **distinct** values).

Before being passed to your function, `nums` is **rotated** at an unknown pivot index `k` (0 <= k < nums.length) such that the resulting array is `[nums[k], nums[k+1], ..., nums[n-1], nums[0], nums[1], ..., nums[k-1]]` (0-indexed). For example, `[0,1,2,4,4,4,5,6,6,7]` might be rotated at pivot index `5` and become `[4,5,6,6,7,0,1,2,4,4]`.

Given the array `nums` **after the rotation** and an integer `target`, return `true` *if* `target` *is in* `nums`*, or* `false` *if it is not in* `nums`*.*

You must decrease the overall operation steps as much as possible.

**Example 1:**
```
Input: nums = [2,5,6,0,0,1,2], target = 0
Output: true
```

**Example 2:**
```
Input: nums = [2,5,6,0,0,1,2], target = 3
Output: false
```

**Constraints:**
- `1 <= nums.length <= 5000`
- `-10^4 <= nums[i] <= 10^4`
- `nums` is guaranteed to be rotated at some pivot.
- `-10^4 <= target <= 10^4`

## Intuition/Main Idea

This is similar to Search in Rotated Sorted Array, but with duplicates. The challenge is handling cases where `nums[left] == nums[mid] == nums[right]`, which makes it unclear which half is sorted.

**Core Algorithm:**
- Binary search with special handling for duplicates
- When `nums[left] == nums[mid] == nums[right]`, we can't determine which half is sorted
- In this case, shrink search space by moving both pointers
- Otherwise, proceed as in the non-duplicate version

**Why special handling:** With duplicates, when `nums[left] == nums[mid]`, we can't tell if left half is sorted or right half is sorted. We must shrink the search space.

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|------------------------|-----------------------------------|
| Handle duplicates | Duplicate check - Lines 12-15 |
| Binary search | While loop - Line 7 |
| Check sorted half | Sorted half check - Lines 17-25 |
| Shrink search space | Pointer updates - Line 14 |

## Final Java Code & Learning Pattern (Full Content)

```java
class Solution {
    public boolean search(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        
        // Binary search with duplicate handling
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            // If target found, return true
            if (nums[mid] == target) {
                return true;
            }
            
            // Handle case where we can't determine sorted half
            // When nums[left] == nums[mid] == nums[right], shrink search space
            if (nums[left] == nums[mid] && nums[mid] == nums[right]) {
                left++;
                right--;
            }
            // Check if left half is sorted
            else if (nums[left] <= nums[mid]) {
                // Left half is sorted
                // Check if target is in left half
                if (nums[left] <= target && target < nums[mid]) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            }
            // Right half is sorted
            else {
                // Check if target is in right half
                if (nums[mid] < target && target <= nums[right]) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
        }
        
        return false;
    }
}
```

## Binary Search Decision Guide

**How to decide whether to use < or <= in the main loop condition:**
- Use `<=` to check all elements, including when left == right

**How to decide if pointers should be set to mid + 1 or mid - 1 or mid:**
- When we can determine which half to search, use `mid + 1` or `mid - 1`
- When we can't determine (duplicates), shrink by moving both pointers inward

**How to decide what would be the return value:**
- Return `true` if `nums[mid] == target`
- Return `false` if search completes without finding target

## Complexity Analysis

**Time Complexity:** $O(n)$ in worst case (all elements same), $O(\log n)$ average case. When duplicates force us to shrink both sides, we may need to check many elements.

**Space Complexity:** $O(1)$. We only use a constant amount of extra space.

## Similar Problems

- [Search in Rotated Sorted Array](https://leetcode.com/problems/search-in-rotated-sorted-array/) - Without duplicates
- [Find Minimum in Rotated Sorted Array](https://leetcode.com/problems/find-minimum-in-rotated-sorted-array/) - Find pivot
- [Find Peak Element](https://leetcode.com/problems/find-peak-element/) - Similar binary search pattern

