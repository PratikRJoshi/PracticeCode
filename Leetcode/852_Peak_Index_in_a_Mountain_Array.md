# 852. Peak Index in a Mountain Array

[LeetCode Link](https://leetcode.com/problems/peak-index-in-a-mountain-array/)

## Problem Description
You are given an integer array `arr` that is guaranteed to be a **mountain array**:

- There exists an index `peak` such that:
  - `arr[0] < arr[1] < ... < arr[peak]`
  - `arr[peak] > arr[peak + 1] > ... > arr[n - 1]`

Return the index `peak`.

### Examples

#### Example 1
- Input: `arr = [0,1,0]`
- Output: `1`

#### Example 2
- Input: `arr = [0,2,1,0]`
- Output: `1`

#### Example 3
- Input: `arr = [0,10,5,2]`
- Output: `1`

---

## Intuition/Main Idea
A mountain array strictly increases and then strictly decreases. That means:

- On the increasing side, the “slope” is upward: `arr[i] < arr[i + 1]`.
- On the decreasing side (including the peak), the “slope” is downward: `arr[i] > arr[i + 1]`.

So we can binary search for the boundary point where the slope changes from **up** to **down**.

Key idea:
- If `arr[mid] < arr[mid + 1]`, we are on the increasing slope, so the peak must be to the **right** of `mid`.
- Otherwise, we are on the decreasing slope or at the peak, so the peak is at `mid` or to the **left**.

This works because the predicate “we are on the increasing side” is monotonic over the array indices (true on the left part, false on the right part).

---

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|---|---|
| Return the index of the peak element | Return `left` after binary search finishes |
| Use mountain property (increasing then decreasing) | Compare `arr[mid]` with `arr[mid + 1]` to detect slope |
| Must be efficient | Binary search loop shrinks search space each iteration |

---

## Final Java Code & Learning Pattern (Full Content)
```java
class Solution {
    public int peakIndexInMountainArray(int[] arr) {
        int left = 0;
        int right = arr.length - 1;

        while (left < right) {
            int mid = left + (right - left) / 2;

            // mid + 1 is safe because mid < right whenever left < right
            if (arr[mid] < arr[mid + 1]) {
                // We are on the increasing slope, so peak is strictly to the right.
                left = mid + 1;
            } else {
                // We are on the decreasing slope (or at peak), so peak is at mid or to the left.
                right = mid;
            }
        }

        return left;
    }
}
```

### Learning Pattern
- Convert “find the peak” into “find where the slope changes”.
- Use binary search on indices by comparing `arr[mid]` and `arr[mid + 1]`.
- Template memory hook:
  - increasing slope (`arr[mid] < arr[mid+1]`) => `left = mid + 1`
  - decreasing slope => `right = mid`

---

## Complexity Analysis
- Time Complexity: $O(\log n)$
- Space Complexity: $O(1)$

---

## Binary Search Problems
- How to decide whether to use `<` or `<=` in the main loop condition:
  - Here we use `while (left < right)` because we maintain an invariant that the peak index is always inside the current closed interval `[left, right]`.
  - We stop when the interval collapses to a single index (`left == right`), which must be the peak.

- How to decide if the pointers should be set to `mid + 1` or `mid - 1` or `mid`:
  - We compare `arr[mid]` vs `arr[mid + 1]` to know which side of the peak we’re on.
  - If increasing (`arr[mid] < arr[mid + 1]`), peak cannot be `mid`, so we do `left = mid + 1`.
  - If decreasing, peak could be `mid`, so we keep it by doing `right = mid` (not `mid - 1`).

- How to decide what would be the return value:
  - When the loop ends, `left == right` and points to the only candidate index remaining, so we return `left`.

---

## Similar Problems
- [162. Find Peak Element](https://leetcode.com/problems/find-peak-element/) (peak via slope binary search)
- [34. Find First and Last Position of Element in Sorted Array](https://leetcode.com/problems/find-first-and-last-position-of-element-in-sorted-array/) (boundary finding with binary search)
