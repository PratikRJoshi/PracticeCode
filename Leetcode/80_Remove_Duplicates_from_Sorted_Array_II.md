# Remove Duplicates from Sorted Array II

## Problem Description

**Problem Link:** [Remove Duplicates from Sorted Array II](https://leetcode.com/problems/remove-duplicates-from-sorted-array-ii/)

Given an integer array `nums` sorted in **non-decreasing order**, remove some duplicates **in-place** such that each unique element appears **at most twice**. The **relative order** of the elements should be **kept the same**.

Since it is impossible to change the length of the array in some languages, you must instead have the result be placed in the **first part** of the array `nums`. More formally, if there are `k` elements after removing the duplicates, then the first `k` elements of `nums` should hold the final result. It does not matter what you leave beyond the first `k` elements.

Return `k` *after placing the final result in the first* `k` *slots of* `nums`.

Do **not** allocate extra space for another array. You must do this by **modifying the input array in-place** with O(1) extra memory.

**Example 1:**
```
Input: nums = [1,1,1,2,2,3]
Output: 5, nums = [1,1,2,2,3,_]
Explanation: Your function should return k = 5, with the first five elements of nums being 1, 1, 2, 2, and 3 respectively.
It does not matter what you leave beyond the returned k (hence they are underscores).
```

**Example 2:**
```
Input: nums = [0,0,1,1,1,1,2,3,3]
Output: 7, nums = [0,0,1,1,2,3,3,_,_]
Explanation: Your function should return k = 7, with the first seven elements of nums being 0, 0, 1, 1, 2, 3, and 3 respectively.
It does not matter what values are set beyond the returned k.
```

**Constraints:**
- `1 <= nums.length <= 3 * 10^4`
- `-10^4 <= nums[i] <= 10^4`
- `nums` is sorted in **non-decreasing** order.

## Intuition/Main Idea

This is similar to the previous problem, but we allow each element to appear at most twice. We need to check if the current element is the same as the element two positions before the write pointer.

**Core Algorithm:**
- Use a write pointer starting at index 2 (first two elements are always valid)
- For each element, check if it's different from the element two positions before write pointer
- If different (or same but count < 2), place it at write position
- This ensures at most two occurrences of each element

**Why check two positions back:** If `nums[i] == nums[writeIndex-2]`, then placing `nums[i]` at `writeIndex` would create three consecutive same elements. By checking two positions back, we ensure at most two occurrences.

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|------------------------|-----------------------------------|
| Allow at most 2 duplicates | Check two positions back - Line 9 |
| Remove extra duplicates in-place | Two-pointer approach - Lines 6-14 |
| Maintain relative order | Write pointer tracking - Lines 7, 13 |
| Return count | Write pointer value - Line 15 |

## Final Java Code & Learning Pattern (Full Content)

```java
class Solution {
    public int removeDuplicates(int[] nums) {
        // Two-pointer approach
        // writeIndex: tracks position where next valid element should be placed
        // Starts at 2 because first two elements are always valid (can have at most 2)
        int writeIndex = 2;
        
        // Scan through array starting from index 2
        // We compare with element two positions before writeIndex
        for (int i = 2; i < nums.length; i++) {
            // Check if current element is different from element two positions back
            // If nums[i] == nums[writeIndex-2], placing nums[i] would create third occurrence
            // So we only place if nums[i] != nums[writeIndex-2]
            if (nums[i] != nums[writeIndex - 2]) {
                // Current element can be placed (either new element or third+ occurrence skipped)
                nums[writeIndex] = nums[i];
                
                // Move write pointer forward
                writeIndex++;
            }
            // If nums[i] == nums[writeIndex-2], skip this element
            // This ensures at most 2 occurrences of each element
        }
        
        // writeIndex represents the count of valid elements
        return writeIndex;
    }
}
```

## Complexity Analysis

**Time Complexity:** $O(n)$ where $n$ is the length of the array. We traverse the array once.

**Space Complexity:** $O(1)$. We only use a constant amount of extra space for the pointers.

## Similar Problems

- [Remove Duplicates from Sorted Array](https://leetcode.com/problems/remove-duplicates-from-sorted-array/) - Allow only 1 occurrence
- [Remove Element](https://leetcode.com/problems/remove-element/) - Similar in-place removal
- [Move Zeroes](https://leetcode.com/problems/move-zeroes/) - Similar two-pointer pattern

