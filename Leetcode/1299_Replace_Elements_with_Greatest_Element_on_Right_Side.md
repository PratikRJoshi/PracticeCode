# Replace Elements with Greatest Element on Right Side

## Problem Description

**Problem Link:** [Replace Elements with Greatest Element on Right Side](https://leetcode.com/problems/replace-elements-with-greatest-element-on-right-side/)

Given an array `arr`, replace every element in that array with the greatest element among the elements to its right, and replace the last element with `-1`.

After doing so, return the array.

**Example 1:**
```
Input: arr = [17,18,5,4,6,1]
Output: [18,6,6,6,1,-1]
Explanation: 
- index 0 --> the greatest element to the right of index 0 is index 1 (18).
- index 1 --> the greatest element to the right of index 1 is index 4 (6).
- index 2 --> the greatest element to the right of index 2 is index 4 (6).
- index 3 --> the greatest element to the right of index 3 is index 4 (6).
- index 4 --> the greatest element to the right of index 4 is index 5 (1).
- index 5 --> there are no elements to the right of index 5, so we put -1.
```

**Example 2:**
```
Input: arr = [400]
Output: [-1]
Explanation: There are no elements to the right of index 0.
```

**Constraints:**
- `1 <= arr.length <= 10^4`
- `1 <= arr[i] <= 10^5`

## Intuition/Main Idea

The key insight is to process the array from right to left. When we traverse backwards, we can track the maximum element seen so far and use it to replace the current element.

**Core Algorithm:**
- Start from the rightmost element
- Keep track of the maximum element encountered
- Replace current element with the maximum
- Update maximum for next iteration
- Last element becomes -1

**Why right-to-left:** When going right-to-left, we've already seen all elements to the right. We can maintain a running maximum efficiently. Going left-to-right would require finding max for each position separately, which is less efficient.

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|------------------------|-----------------------------------|
| Replace each element with max to right | Right-to-left traversal - Lines 6-11 |
| Track maximum element | `max` variable - Lines 7, 10 |
| Replace current element | Assignment - Line 8 |
| Last element becomes -1 | Initial max value - Line 7 |
| Update maximum | Max update - Line 10 |

## Final Java Code & Learning Pattern (Full Content)

```java
class Solution {
    public int[] replaceElements(int[] arr) {
        // Start from the rightmost element
        // We'll traverse backwards to efficiently track maximum
        int n = arr.length;
        
        // Initialize max to -1 (since last element should be -1)
        // This handles the last element case automatically
        int max = -1;
        
        // Traverse from right to left
        // At each position, we've already seen all elements to the right
        for (int i = n - 1; i >= 0; i--) {
            // Store current element before replacing
            // We need the original value to update max
            int current = arr[i];
            
            // Replace current element with max seen so far
            // This is the greatest element to the right of current position
            arr[i] = max;
            
            // Update max to include current element
            // For next iteration (leftward), this becomes the max to the right
            max = Math.max(max, current);
        }
        
        return arr;
    }
}
```

## Complexity Analysis

**Time Complexity:** $O(n)$ where $n$ is the length of the array. We traverse the array once from right to left.

**Space Complexity:** $O(1)$ excluding the output array. We only use a constant amount of extra space for the `max` variable.

## Similar Problems

- [Trapping Rain Water](https://leetcode.com/problems/trapping-rain-water/) - Uses similar right-to-left max tracking
- [Product of Array Except Self](https://leetcode.com/problems/product-of-array-except-self/) - Similar pattern with left and right passes
- [Next Greater Element](https://leetcode.com/problems/next-greater-element-i/) - Finding next greater element using stack

