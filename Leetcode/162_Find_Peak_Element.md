# Find Peak Element

## Problem Description

**Problem Link:** [Find Peak Element](https://leetcode.com/problems/find-peak-element/)

A **peak element** is an element that is strictly greater than its neighbors.

Given a **0-indexed** integer array `nums`, find a peak element, and return its index. If the array contains multiple peaks, return the index to **any of the peaks**.

You may imagine that `nums[-1] = nums[n] = -∞`. In other words, an element is always considered to be strictly greater than a neighbor that is outside the array.

You must write an algorithm that runs in $O(\log n)$ time complexity.

**Example 1:**
```
Input: nums = [1,2,3,1]
Output: 2
Explanation: 3 is a peak element because your function can return either index number 2 where the peak element is 3, or index number 0 where 1 is a peak element.
```

**Example 2:**
```
Input: nums = [1,2,1,3,5,6,4]
Output: 5
Explanation: Your function can return either index number 1 where the peak element is 2, or index number 5 where the peak element is 6.
```

**Constraints:**
- `1 <= nums.length <= 1000`
- `-2^31 <= nums[i] <= 2^31 - 1`
- For all valid `i`, `nums[i] != nums[i + 1]`

## Intuition/Main Idea

We can use binary search to find a peak. The key insight is that if `nums[mid] < nums[mid+1]`, there must be a peak on the right side (because we go up, and eventually must come down or hit boundary). Similarly, if `nums[mid] < nums[mid-1]`, there's a peak on the left.

**Core Algorithm:**
- Compare `nums[mid]` with `nums[mid+1]`
- If `nums[mid] < nums[mid+1]`, peak is on right, search right
- Otherwise, peak is on left (or mid is peak), search left

**Why binary search:** Even though array isn't sorted, we can eliminate half the search space based on the slope. If we're going up, peak is ahead; if going down, peak is behind.

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|------------------------|-----------------------------------|
| Binary search for peak | While loop - Line 6 |
| Compare with neighbor | Comparison - Line 10 |
| Search right if ascending | Right search - Lines 11-12 |
| Search left if descending | Left search - Line 14 |

## Final Java Code & Learning Pattern (Full Content)

```java
class Solution {
    public int findPeakElement(int[] nums) {
        int left = 0;
        int right = nums.length - 1;
        
        // Binary search for peak element
        // We use < instead of <= because we check mid+1
        while (left < right) {
            int mid = left + (right - left) / 2;
            
            // Compare mid with right neighbor
            // If nums[mid] < nums[mid+1], we're on ascending slope
            // Peak must be on the right side
            if (nums[mid] < nums[mid + 1]) {
                left = mid + 1;
            }
            // Otherwise, we're on descending slope or at peak
            // Peak is on the left side (or mid itself)
            else {
                right = mid;
            }
        }
        
        // After loop, left == right, and it's a peak
        return left;
    }
}
```

## Binary Search Decision Guide

**How to decide whether to use < or <= in the main loop condition:**
- Use `<` because we access `mid+1`, so we need `left < right` to ensure `mid+1` is valid
- When `left == right`, we've found the peak

**How to decide if pointers should be set to mid + 1 or mid - 1 or mid:**
- When ascending (`nums[mid] < nums[mid+1]`), peak is definitely on right, so `left = mid + 1`
- When descending, peak could be mid or left, so `right = mid` (include mid)

**How to decide what would be the return value:**
- After loop, `left == right` and it's guaranteed to be a peak
- This works because we always move towards the direction with a peak

## Complexity Analysis

**Time Complexity:** $O(\log n)$ where $n$ is the length of the array. Binary search halves the search space each iteration.

**Space Complexity:** $O(1)$. We only use a constant amount of extra space.

## Similar Problems

- [Search in Rotated Sorted Array](https://leetcode.com/problems/search-in-rotated-sorted-array/) - Similar binary search pattern
- [Mountain Array](https://leetcode.com/problems/peak-index-in-a-mountain-array/) - Similar peak finding
- [Binary Search](https://leetcode.com/problems/binary-search/) - Basic binary search

