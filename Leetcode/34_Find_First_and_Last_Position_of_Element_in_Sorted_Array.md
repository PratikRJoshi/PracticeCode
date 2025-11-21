# [34. Find First and Last Position of Element in Sorted Array](https://leetcode.com/problems/find-first-and-last-position-of-element-in-sorted-array/)

Given an array of integers `nums` sorted in non-decreasing order, find the starting and ending position of a given `target` value.

If `target` is not found in the array, return `[-1, -1]`.

You must write an algorithm with `O(log n)` runtime complexity.

**Example 1:**

```
Input: nums = [5,7,7,8,8,10], target = 8
Output: [3,4]
```

**Example 2:**

```
Input: nums = [5,7,7,8,8,10], target = 6
Output: [-1,-1]
```

**Example 3:**

```
Input: nums = [], target = 0
Output: [-1,-1]
```

**Constraints:**

- `0 <= nums.length <= 10^5`
- `-10^9 <= nums[i] <= 10^9`
- `nums` is a non-decreasing array.
- `-10^9 <= target <= 10^9`

## Intuition/Main Idea:

Since the array is sorted and we need an $O(\log n)$ solution, binary search is the natural approach. However, standard binary search only finds one occurrence of the target. To find both the first and last positions, we need to perform two separate binary searches:

1. First binary search: Find the leftmost (first) occurrence of the target.
2. Second binary search: Find the rightmost (last) occurrence of the target.

For the first binary search, when we find the target, we don't immediately return. Instead, we continue searching in the left half to find an earlier occurrence.

For the second binary search, when we find the target, we continue searching in the right half to find a later occurrence.

## Code Mapping:

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Find the starting position | `int first = findFirstPosition(nums, target);` |
| Find the ending position | `int last = findLastPosition(nums, target);` |
| Return [-1, -1] if target not found | `if (first > last) return new int[]{-1, -1};` |
| O(log n) runtime complexity | Using binary search in both helper methods |

## Final Java Code & Learning Pattern:

```java
class Solution {
    public int[] searchRange(int[] nums, int target) {
        // Find the first and last positions of the target
        int first = findFirstPosition(nums, target);
        int last = findLastPosition(nums, target);
        
        // If target is not found (first > last), return [-1, -1]
        if (first > last) {
            return new int[]{-1, -1};
        }
        
        return new int[]{first, last};
    }
    
    // Helper method to find the first position of the target
    private int findFirstPosition(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        int result = -1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (nums[mid] == target) {
                // Found a match, but continue searching in the left half
                // to find the first occurrence
                result = mid;
                right = mid - 1;
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        
        return result;
    }
    
    // Helper method to find the last position of the target
    private int findLastPosition(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        int result = -1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (nums[mid] == target) {
                // Found a match, but continue searching in the right half
                // to find the last occurrence
                result = mid;
                left = mid + 1;
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        
        return result;
    }
}
```

This solution uses two modified binary searches to find the first and last positions of the target:

1. `findFirstPosition`: When we find the target, we update our result but continue searching in the left half to find any earlier occurrences.
2. `findLastPosition`: When we find the target, we update our result but continue searching in the right half to find any later occurrences.
3. If the target is not found in either search, the first position will be -1 and the last position will be -1, so we return [-1, -1].
4. If the target is found, we return the first and last positions.

## Binary Search Explanation:

### How to decide whether to use < or <= in the main loop condition:
- We use `left <= right` because we want to include the case where `left` and `right` point to the same element. This ensures we check every element in the range.

### How to decide if the pointers should be set to mid + 1 or mid - 1 or mid:
- When searching for the first position and we find a match, we set `right = mid - 1` to continue searching in the left half.
- When searching for the last position and we find a match, we set `left = mid + 1` to continue searching in the right half.
- When the current element is less than the target, we always set `left = mid + 1` because the target must be in the right half.
- When the current element is greater than the target, we always set `right = mid - 1` because the target must be in the left half.

### How to decide what would be the return value:
- We use a `result` variable to keep track of the most recent match we've found.
- If we never find a match, `result` remains -1.
- For the first position, we return the leftmost match.
- For the last position, we return the rightmost match.

## Complexity Analysis:

- **Time Complexity**: $O(\log n)$ where n is the length of the array. We perform two binary searches, each taking $O(\log n)$ time.
- **Space Complexity**: $O(1)$ as we only use a constant amount of extra space.

## Similar Problems:

1. [33. Search in Rotated Sorted Array](https://leetcode.com/problems/search-in-rotated-sorted-array/)
2. [35. Search Insert Position](https://leetcode.com/problems/search-insert-position/)
3. [278. First Bad Version](https://leetcode.com/problems/first-bad-version/)
4. [153. Find Minimum in Rotated Sorted Array](https://leetcode.com/problems/find-minimum-in-rotated-sorted-array/)
5. [162. Find Peak Element](https://leetcode.com/problems/find-peak-element/)
