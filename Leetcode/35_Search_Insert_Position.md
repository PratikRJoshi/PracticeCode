# Search Insert Position

## Problem Description

**Problem Link:** [Search Insert Position](https://leetcode.com/problems/search-insert-position/)

Given a sorted array of distinct integers and a target value, return the index if the target is found. If not, return the index where it would be if it were inserted in order.

You must write an algorithm with $O(\log n)$ runtime complexity.

**Example 1:**
```
Input: nums = [1,3,5,6], target = 5
Output: 2
```

**Example 2:**
```
Input: nums = [1,3,5,6], target = 2
Output: 1
```

**Example 3:**
```
Input: nums = [1,3,5,6], target = 7
Output: 4
```

**Constraints:**
- `1 <= nums.length <= 10^4`
- `-10^4 <= nums[i] <= 10^4`
- `nums` contains **distinct** values sorted in **ascending** order.
- `-10^4 <= target <= 10^4`

## Intuition/Main Idea

This is a binary search problem. We need to find the insertion position, which is the first position where the element is >= target.

**Core Algorithm:**
- Use binary search to find insertion position
- If target found, return index
- If not found, return left pointer (where it should be inserted)

**Why binary search:** The array is sorted, so we can use binary search to find the position in $O(\log n)$ time.

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|------------------------|-----------------------------------|
| Binary search | While loop - Line 6 |
| Find insertion position | Left pointer - Line 15 |
| Handle target found | Equality check - Line 10 |
| Handle target not found | Return left - Line 15 |

## Final Java Code & Learning Pattern (Full Content)

```java
class Solution {
    public int searchInsert(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        
        // Binary search for insertion position
        // We use <= to ensure we check all elements
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            // If target found, return index
            if (nums[mid] == target) {
                return mid;
            }
            // If target is smaller, search left half
            // We set right = mid - 1 because mid is not the answer
            else if (nums[mid] > target) {
                right = mid - 1;
            }
            // If target is larger, search right half
            // We set left = mid + 1 because mid is not the answer
            else {
                left = mid + 1;
            }
        }
        
        // If target not found, left points to insertion position
        // After loop, left > right, and left is where target should be inserted
        return left;
    }
}
```

## Binary Search Decision Guide

**How to decide whether to use < or <= in the main loop condition:**
- Use `<=` when we need to check all elements and the search space can be reduced to a single element
- Use `<` when we want to stop when left and right are adjacent

**How to decide if pointers should be set to mid + 1 or mid - 1 or mid:**
- If `nums[mid]` is definitely not the answer, use `mid + 1` or `mid - 1`
- If `nums[mid]` could still be the answer, use `mid`

**How to decide what would be the return value:**
- If searching for exact match: return `mid` when found, or `-1`/`left` when not found
- If searching for insertion position: return `left` (points to where element should be inserted)

## Complexity Analysis

**Time Complexity:** $O(\log n)$ where $n$ is the length of the array. Binary search halves the search space each iteration.

**Space Complexity:** $O(1)$. We only use a constant amount of extra space.

## Similar Problems

- [Binary Search](https://leetcode.com/problems/binary-search/) - Basic binary search
- [Find First and Last Position](https://leetcode.com/problems/find-first-and-last-position-of-element-in-sorted-array/) - Binary search variants
- [Sqrt(x)](https://leetcode.com/problems/sqrtx/) - Binary search on answer space

