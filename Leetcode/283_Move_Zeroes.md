# Move Zeroes

## Problem Description

**Problem Link:** [Move Zeroes](https://leetcode.com/problems/move-zeroes/)

Given an integer array `nums`, move all `0`'s to the end of it while maintaining the relative order of the non-zero elements.

Note that you must do this **in-place** without making a copy of the array.

**Example 1:**
```
Input: nums = [0,1,0,3,12]
Output: [1,3,12,0,0]
```

**Example 2:**
```
Input: nums = [0]
Output: [0]
```

**Constraints:**
- `1 <= nums.length <= 10^4`
- `-2^31 <= nums[i] <= 2^31 - 1`

## Intuition/Main Idea

We need to move all zeros to the end while preserving the order of non-zero elements. The key insight is to use two pointers: one to track the position where the next non-zero should be placed, and another to scan through the array.

**Core Algorithm:**
- Use a "write" pointer to track where next non-zero should go
- Use a "read" pointer to scan through array
- When we find a non-zero, swap it with the write position (or just copy if write < read)
- Increment write pointer after placing non-zero

**Why two pointers:** We need to separate the "scanning" (finding non-zeros) from "writing" (placing them in correct positions). The write pointer maintains the position where non-zeros should go, ensuring order is preserved.

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|------------------------|-----------------------------------|
| Move zeros to end | Two-pointer approach - Lines 6-13 |
| Maintain relative order | Write pointer tracking - Lines 7, 12 |
| In-place operation | Direct array modification - Line 11 |
| Find non-zero elements | Scan pointer - Line 8 |
| Place non-zeros in order | Write pointer increment - Line 12 |

## Final Java Code & Learning Pattern (Full Content)

```java
class Solution {
    public void moveZeroes(int[] nums) {
        // Two-pointer approach
        // writeIndex: tracks where next non-zero element should be placed
        // This maintains the relative order of non-zero elements
        int writeIndex = 0;
        
        // Scan through the entire array
        // When we find a non-zero, place it at writeIndex
        for (int i = 0; i < nums.length; i++) {
            // If current element is non-zero
            if (nums[i] != 0) {
                // Place it at writeIndex
                // If writeIndex < i, we're moving non-zero forward (swapping with zero)
                // If writeIndex == i, element is already in correct position
                nums[writeIndex] = nums[i];
                
                // If we moved an element forward, set original position to zero
                // This handles the case where writeIndex < i
                if (writeIndex != i) {
                    nums[i] = 0;
                }
                
                // Move write pointer forward
                // Next non-zero will be placed at this position
                writeIndex++;
            }
            // If current element is zero, just continue scanning
            // writeIndex stays at current position, waiting for next non-zero
        }
    }
}
```

## Complexity Analysis

**Time Complexity:** $O(n)$ where $n$ is the length of the array. We traverse the array once.

**Space Complexity:** $O(1)$. We only use a constant amount of extra space for the pointers.

## Similar Problems

- [Remove Element](https://leetcode.com/problems/remove-element/) - Similar two-pointer removal pattern
- [Remove Duplicates from Sorted Array](https://leetcode.com/problems/remove-duplicates-from-sorted-array/) - Similar in-place modification
- [Sort Colors](https://leetcode.com/problems/sort-colors/) - Three-pointer variant

