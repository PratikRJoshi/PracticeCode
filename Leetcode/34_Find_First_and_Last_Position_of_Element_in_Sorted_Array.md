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
| Return [-1, -1] if target not found | `if (first == -1) return new int[]{-1, -1};` |
| O(log n) runtime complexity | Using insertion-point binary search in both helper methods |

## Final Java Code & Learning Pattern:

```java
class Solution {
    public int[] searchRange(int[] nums, int target) {
        int first = findFirstPosition(nums, target);
        if (first == -1) {
            return new int[]{-1, -1};
        }

        int last = findLastPosition(nums, target);
        return new int[]{first, last};
    }

    // First index i where nums[i] == target (or -1 if not found)
    private int findFirstPosition(int[] nums, int target) {
        int left = 0;
        int right = nums.length;

        while (left < right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }

        if (left == nums.length || nums[left] != target) {
            return -1;
        }
        return left;
    }

    // Last index i where nums[i] == target (assumes target exists)
    private int findLastPosition(int[] nums, int target) {
        int left = 0;
        int right = nums.length;

        while (left < right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] <= target) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }

        return left - 1;
    }
}
```

This solution uses an insertion-point binary search pattern:

1. `findFirstPosition` converges to the first index with `nums[i] >= target`, then validates equality.
2. `findLastPosition` converges to the first index with `nums[i] > target`, then subtracts 1 to get the last `== target`.
3. If `findFirstPosition` returns `-1`, the target does not exist and we return `[-1, -1]`.

## Binary Search Explanation:

### How to decide whether to use < or <= in the main loop condition:
- In this approach we use `left < right` because `right` is exclusive (`right = nums.length`).
- The loop maintains an insertion-point invariant, and terminates when the search range becomes empty (`left == right`).

### How to decide if the pointers should be set to mid + 1 or mid - 1 or mid:
- In `findFirstPosition`, when `nums[mid] < target`, the first `>= target` must be to the right, so `left = mid + 1`.
- Otherwise `nums[mid] >= target`, so `mid` could still be the first valid position, and we keep it by setting `right = mid`.
- In `findLastPosition`, when `nums[mid] <= target`, the first `> target` must be to the right, so `left = mid + 1`.
- Otherwise `nums[mid] > target`, so `mid` could still be the first `> target`, and we keep it by setting `right = mid`.

### How to decide what would be the return value:
- `findFirstPosition` returns `left` after the loop, but only if it is in bounds and `nums[left] == target`, otherwise `-1`.
- `findLastPosition` returns `left - 1` because `left` ends at the first index where `nums[left] > target`.

## Complexity Analysis:

- **Time Complexity**: $O(\log n)$ where n is the length of the array. We perform two binary searches, each taking $O(\log n)$ time.
- **Space Complexity**: $O(1)$ as we only use a constant amount of extra space.

## Similar Problems:

1. [33. Search in Rotated Sorted Array](https://leetcode.com/problems/search-in-rotated-sorted-array/)
2. [35. Search Insert Position](https://leetcode.com/problems/search-insert-position/)
3. [278. First Bad Version](https://leetcode.com/problems/first-bad-version/)
4. [153. Find Minimum in Rotated Sorted Array](https://leetcode.com/problems/find-minimum-in-rotated-sorted-array/)
5. [162. Find Peak Element](https://leetcode.com/problems/find-peak-element/)
