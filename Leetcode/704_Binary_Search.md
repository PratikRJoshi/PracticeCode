# Binary Search

## Problem Description

**Problem Link:** [Binary Search](https://leetcode.com/problems/binary-search/)

Given an array of integers `nums` which is sorted in **ascending order**, and an integer `target`, write a function to search `target` in `nums`. If `target` exists, then return its index. Otherwise, return `-1`.

You must write an algorithm with $O(\log n)$ runtime complexity.

**Example 1:**
```
Input: nums = [-1,0,3,5,9,12], target = 9
Output: 4
Explanation: 9 exists in nums and its index is 4
```

**Example 2:**
```
Input: nums = [-1,0,3,5,9,12], target = 2
Output: -1
Explanation: 2 does not exist in nums so return -1
```

**Constraints:**
- `1 <= nums.length <= 10^4`
- `-10^4 < nums[i], target < 10^4`
- All the integers in `nums` are **unique**.
- `nums` is sorted in **ascending order**.

## Intuition/Main Idea

Binary search is a classic divide-and-conquer algorithm that works on sorted arrays. The key idea is to repeatedly divide the search space in half by comparing the target with the middle element.

**Core Algorithm:**
1. Compare target with the middle element.
2. If target equals the middle element, return its index.
3. If target is smaller, search the left half.
4. If target is larger, search the right half.
5. Repeat until the element is found or the search space is exhausted.

**Why it works:** By eliminating half of the remaining elements at each step, we achieve O(log n) time complexity. This is much more efficient than linear search's O(n).

## Code Mapping

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Initialize search boundaries | `left` and `right` pointers - Lines 6-7 |
| Find middle element | Mid calculation - Line 10 |
| Compare target with middle | Comparison - Line 12 |
| Narrow search space | Update left/right pointers - Lines 14, 16 |
| Handle target found | Return index - Line 13 |
| Handle target not found | Return -1 - Line 19 |

## Final Java Code & Learning Pattern

```java
class Solution {
    public int search(int[] nums, int target) {
        // Initialize left and right boundaries
        int left = 0;
        int right = nums.length - 1;
        
        // Continue searching while there's a valid search space
        while (left <= right) {
            // Calculate middle index
            // Using (left + right) / 2 could overflow for large arrays
            // Using left + (right - left) / 2 prevents overflow
            int mid = left + (right - left) / 2;
            
            // Check if target is found at middle
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] < target) {
                // Target is in right half, exclude left half including mid
                left = mid + 1;
            } else {
                // Target is in left half, exclude right half including mid
                right = mid - 1;
            }
        }
        
        // Target not found
        return -1;
    }
}
```

**Explanation of Key Code Sections:**

1. **Boundary Initialization (Lines 6-7):** We set `left` to the start index (0) and `right` to the end index (length - 1). These define our current search space.

2. **Loop Condition (Line 9):** We use `left <= right` to continue searching while there's a valid search space. When `left > right`, we've exhausted all possibilities.

3. **Mid Calculation (Line 12):** We calculate the middle index. Using `left + (right - left) / 2` instead of `(left + right) / 2` prevents integer overflow when `left` and `right` are very large numbers.

4. **Target Comparison (Lines 14-18):**
   - **Found (Line 15):** If `nums[mid] == target`, we've found the target and return its index.
   - **Target in right half (Line 16-17):** If `nums[mid] < target`, the target must be in the right half. We update `left = mid + 1` to exclude the current mid and everything to its left.
   - **Target in left half (Line 18-19):** If `nums[mid] > target`, the target must be in the left half. We update `right = mid - 1` to exclude the current mid and everything to its right.

5. **Not Found (Line 22):** If we exit the loop without finding the target, return -1.

## Binary Search Decision Guide

### How to decide whether to use `<` or `<=` in the main loop condition:

**Use `left <= right` when:**
- You want to search the entire array including when `left == right`
- You're looking for an exact match
- This is the standard binary search pattern

**Use `left < right` when:**
- You want to stop when `left == right` and handle that case separately
- You're doing a variant like finding insertion point or boundary

**For this problem:** We use `left <= right` because we want to check every valid position, including when the search space narrows to a single element.

### How to decide if pointers should be set to `mid + 1` or `mid - 1` or `mid`:

**Standard pattern (this problem):**
- When `nums[mid] < target`: `left = mid + 1` (exclude mid, search right)
- When `nums[mid] > target`: `right = mid - 1` (exclude mid, search left)
- When `nums[mid] == target`: return mid

**Why `mid + 1` and `mid - 1`:**
- We've already checked `nums[mid]`, so we can safely exclude it
- This ensures the search space shrinks at each step
- Prevents infinite loops

**When to use `mid` instead:**
- In variants like "find first occurrence" or "find insertion point"
- When we want to keep `mid` in the search space for boundary detection

### How to decide what would be the return value:

**Standard binary search (this problem):**
- Return `mid` when `nums[mid] == target`
- Return `-1` when target not found (loop exits)

**Variants:**
- **Lower bound:** Return `left` (first position where target could be inserted)
- **Upper bound:** Return `right + 1` or `left`
- **Range search:** Return `[leftBound, rightBound]`

## Complexity Analysis

- **Time Complexity:** $O(\log n)$ where $n$ is the length of the array. At each step, we eliminate half of the remaining elements.

- **Space Complexity:** $O(1)$ as we only use a constant amount of extra space for variables.

## Similar Problems

Problems that can be solved using similar binary search patterns:

1. **704. Binary Search** (this problem) - Classic binary search
2. **35. Search Insert Position** - Binary search with insertion point
3. **34. Find First and Last Position of Element in Sorted Array** - Binary search variants
4. **33. Search in Rotated Sorted Array** - Binary search on rotated array
5. **81. Search in Rotated Sorted Array II** - With duplicates
6. **153. Find Minimum in Rotated Sorted Array** - Binary search for pivot
7. **162. Find Peak Element** - Binary search on unsorted array
8. **278. First Bad Version** - Binary search variant
9. **374. Guess Number Higher or Lower** - Binary search game
10. **69. Sqrt(x)** - Binary search for square root
11. **367. Valid Perfect Square** - Binary search for perfect square
12. **875. Koko Eating Bananas** - Binary search on answer space
13. **1011. Capacity To Ship Packages Within D Days** - Binary search on capacity
14. **1482. Minimum Number of Days to Make m Bouquets** - Binary search on days

