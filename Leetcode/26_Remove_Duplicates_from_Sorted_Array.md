# Remove Duplicates from Sorted Array

## Problem Description

**Problem Link:** [Remove Duplicates from Sorted Array](https://leetcode.com/problems/remove-duplicates-from-sorted-array/)

Given an integer array `nums` sorted in **non-decreasing order**, remove the duplicates **in-place** such that each unique element appears only **once**. The **relative order** of the elements should be **kept the same**. Then return *the number of unique elements in* `nums`.

Consider the number of unique elements of `nums` to be `k`, to get accepted, you need to do the following things:

- Change the array `nums` such that the first `k` elements of `nums` contain the unique elements in the order they were present in `nums` initially. The remaining elements of `nums` are not important as well as the size of `nums`.
- Return `k`.

**Example 1:**
```
Input: nums = [1,1,2]
Output: 2, nums = [1,2,_]
Explanation: Your function should return k = 2, with the first two elements of nums being 1 and 2 respectively.
It does not matter what you leave beyond the returned k (hence they are underscores).
```

**Example 2:**
```
Input: nums = [0,0,1,1,1,2,2,3,3,4]
Output: 5, nums = [0,1,2,3,4,_,_,_,_,_]
Explanation: Your function should return k = 5, with the first five elements of nums being 0, 1, 2, 3, and 4 respectively.
It does not matter what values are set beyond the returned k.
```

**Constraints:**
- `1 <= nums.length <= 3 * 10^4`
- `-100 <= nums[i] <= 100`
- `nums` is sorted in **non-decreasing** order.

## Intuition/Main Idea

Since the array is sorted, duplicates are adjacent. We can use two pointers: one to track where to place the next unique element, and another to scan through the array.

**Core Algorithm:**
- Use a "write" pointer for the position of next unique element
- Use a "read" pointer to scan through array
- When we find a new unique element (different from previous), place it at write position
- Increment write pointer

**Why two pointers:** The write pointer maintains the position where unique elements should go, ensuring they're in order. The read pointer finds new unique elements by comparing with the last written element.

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|------------------------|-----------------------------------|
| Remove duplicates in-place | Two-pointer approach - Lines 6-14 |
| Maintain relative order | Write pointer tracking - Lines 7, 13 |
| Return count of unique elements | Write pointer value - Line 15 |
| Find unique elements | Comparison with last written - Line 9 |
| Place unique elements | Assignment - Line 10 |

## Final Java Code & Learning Pattern (Full Content)

```java
class Solution {
    public int removeDuplicates(int[] nums) {
        // Two-pointer approach
        // writeIndex: tracks position where next unique element should be placed
        // Starts at 1 because first element is always unique
        int writeIndex = 1;
        
        // Scan through array starting from index 1
        // Compare each element with the last written unique element
        for (int i = 1; i < nums.length; i++) {
            // If current element is different from last written unique element
            // This means we found a new unique element
            if (nums[i] != nums[writeIndex - 1]) {
                // Place new unique element at writeIndex
                nums[writeIndex] = nums[i];
                
                // Move write pointer forward
                // Next unique element will be placed here
                writeIndex++;
            }
            // If current element equals last written, it's a duplicate
            // Skip it by not incrementing writeIndex
        }
        
        // writeIndex represents the count of unique elements
        // It also points to the position after the last unique element
        return writeIndex;
    }
}
```

## Complexity Analysis

**Time Complexity:** $O(n)$ where $n$ is the length of the array. We traverse the array once.

**Space Complexity:** $O(1)$. We only use a constant amount of extra space for the pointers.

## Similar Problems

- [Remove Duplicates from Sorted Array II](https://leetcode.com/problems/remove-duplicates-from-sorted-array-ii/) - Allow at most 2 duplicates
- [Remove Element](https://leetcode.com/problems/remove-element/) - Similar in-place removal pattern
- [Move Zeroes](https://leetcode.com/problems/move-zeroes/) - Similar two-pointer technique

