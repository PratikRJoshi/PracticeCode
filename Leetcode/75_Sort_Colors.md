# [75. Sort Colors](https://leetcode.com/problems/sort-colors/)

Given an array `nums` with `n` objects colored red, white, or blue, sort them **in-place** so that objects of the same color are adjacent, with the colors in the order red, white, and blue.

We will use the integers `0`, `1`, and `2` to represent the color red, white, and blue, respectively.

You must solve this problem without using the library's sort function.

**Example 1:**

```
Input: nums = [2,0,2,1,1,0]
Output: [0,0,1,1,2,2]
```

**Example 2:**

```
Input: nums = [2,0,1]
Output: [0,1,2]
```

**Constraints:**

- `n == nums.length`
- `1 <= n <= 300`
- `nums[i]` is either `0`, `1`, or `2`.

**Follow up:** Could you come up with a one-pass algorithm using only constant extra space?

## Intuition/Main Idea:

This problem is also known as the Dutch National Flag problem, proposed by Edsger W. Dijkstra. The goal is to sort an array containing only 0s, 1s, and 2s in a single pass with constant extra space.

There are several approaches to solve this problem:

1. **Counting Sort**: Count the occurrences of 0s, 1s, and 2s, then overwrite the array.
2. **Three Pointers (Dutch National Flag Algorithm)**: Use three pointers to partition the array into three sections.

The Dutch National Flag Algorithm is particularly elegant for this problem:
- We use three pointers: `low`, `mid`, and `high`.
- Elements to the left of `low` are 0s.
- Elements between `low` and `mid` are 1s.
- Elements to the right of `high` are 2s.
- Elements between `mid` and `high` are yet to be processed.

As we process each element:
- If it's 0, we swap it with the element at `low` and increment both `low` and `mid`.
- If it's 1, we leave it in place and increment `mid`.
- If it's 2, we swap it with the element at `high` and decrement `high`.

## Code Mapping:

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Sort in-place | The entire solution sorts in-place without extra arrays |
| Colors in order red (0), white (1), blue (2) | `while (mid <= high) { ... }` implements this ordering |
| One-pass algorithm | The solution processes each element only once |
| Constant extra space | Only a few pointers are used, regardless of array size |

## Final Java Code & Learning Pattern:

```java
class Solution {
    public void sortColors(int[] nums) {
        // Initialize the three pointers
        int low = 0;        // Boundary for 0s (red)
        int mid = 0;        // Current element being processed
        int high = nums.length - 1;  // Boundary for 2s (blue)
        
        // Process until all elements are sorted
        while (mid <= high) {
            switch (nums[mid]) {
                case 0:  // Red
                    // Swap with the low pointer and move both pointers
                    swap(nums, low, mid);
                    low++;
                    mid++;
                    break;
                    
                case 1:  // White
                    // Already in the correct position, just move the mid pointer
                    mid++;
                    break;
                    
                case 2:  // Blue
                    // Swap with the high pointer and move only the high pointer
                    swap(nums, mid, high);
                    high--;
                    break;
            }
        }
    }
    
    // Helper method to swap elements
    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
```

This solution implements the Dutch National Flag algorithm:

1. We initialize three pointers:
   - `low`: Points to the position where the next 0 should be placed.
   - `mid`: Points to the current element being processed.
   - `high`: Points to the position where the next 2 should be placed.

2. We process each element based on its value:
   - If it's 0 (red), we swap it with the element at `low`, then increment both `low` and `mid`.
   - If it's 1 (white), it's already in the correct position, so we just increment `mid`.
   - If it's 2 (blue), we swap it with the element at `high`, then decrement `high`. We don't increment `mid` because the swapped element still needs to be processed.

3. We continue until `mid` exceeds `high`, at which point all elements have been processed.

The key insight is that at any point during the algorithm:
- All elements to the left of `low` are 0s.
- All elements between `low` and `mid` are 1s.
- All elements between `mid` and `high` are yet to be processed.
- All elements to the right of `high` are 2s.

## Alternative Implementations:

### Counting Sort Approach:

```java
class Solution {
    public void sortColors(int[] nums) {
        // Count the occurrences of each color
        int count0 = 0;
        int count1 = 0;
        int count2 = 0;
        
        for (int num : nums) {
            if (num == 0) count0++;
            else if (num == 1) count1++;
            else count2++;
        }
        
        // Overwrite the array with the correct number of each color
        int index = 0;
        
        // Fill with 0s
        while (count0 > 0) {
            nums[index++] = 0;
            count0--;
        }
        
        // Fill with 1s
        while (count1 > 0) {
            nums[index++] = 1;
            count1--;
        }
        
        // Fill with 2s
        while (count2 > 0) {
            nums[index++] = 2;
            count2--;
        }
    }
}
```

This approach counts the occurrences of each color, then overwrites the array with the correct number of each color. It requires two passes through the array but is simpler to understand.

## Complexity Analysis:

- **Time Complexity**: O(n) where n is the length of the array. We process each element exactly once.
- **Space Complexity**: O(1) as we only use a constant amount of extra space regardless of the input size.

## Similar Problems:

1. [283. Move Zeroes](https://leetcode.com/problems/move-zeroes/)
2. [215. Kth Largest Element in an Array](https://leetcode.com/problems/kth-largest-element-in-an-array/)
3. [148. Sort List](https://leetcode.com/problems/sort-list/)
4. [912. Sort an Array](https://leetcode.com/problems/sort-an-array/)
5. [324. Wiggle Sort II](https://leetcode.com/problems/wiggle-sort-ii/)
