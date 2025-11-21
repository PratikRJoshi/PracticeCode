# Rotate Array

## Problem Description

**Problem Link:** [Rotate Array](https://leetcode.com/problems/rotate-array/)

Given an integer array `nums`, rotate the array to the right by `k` steps, where `k` is non-negative.

**Example 1:**
```
Input: nums = [1,2,3,4,5,6,7], k = 3
Output: [5,6,7,1,2,3,4]
Explanation:
rotate 1 steps to the right: [7,1,2,3,4,5,6]
rotate 2 steps to the right: [6,7,1,2,3,4,5]
rotate 3 steps to the right: [5,6,7,1,2,3,4]
```

**Example 2:**
```
Input: nums = [-1,-100,3,99], k = 2
Output: [3,99,-1,-100]
Explanation: 
rotate 1 steps to the right: [99,-1,-100,3]
rotate 2 steps to the right: [3,99,-1,-100]
```

**Constraints:**
- `1 <= nums.length <= 10^5`
- `-2^31 <= nums[i] <= 2^31 - 1`
- `0 <= k <= 10^5`

## Intuition/Main Idea

Rotating an array right by `k` positions means the last `k` elements move to the front. There are multiple approaches:

**Approach 1: Reverse three times**
- Reverse entire array
- Reverse first `k` elements
- Reverse remaining `n-k` elements

**Approach 2: Cyclic replacement**
- Move elements in cycles, replacing each element with the one `k` positions ahead

**Why reverse approach:** It's elegant and in-place. The reverse operations naturally achieve the rotation effect. For example, rotating `[1,2,3,4,5]` right by 2: reverse all → `[5,4,3,2,1]`, reverse first 2 → `[4,5,3,2,1]`, reverse rest → `[4,5,1,2,3]`.

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|------------------------|-----------------------------------|
| Rotate array right by k | Three reverse operations - Lines 6-11 |
| Handle k > array length | Modulo operation - Line 5 |
| Reverse entire array | First reverse - Line 8 |
| Reverse first k elements | Second reverse - Line 9 |
| Reverse remaining elements | Third reverse - Line 10 |

## Final Java Code & Learning Pattern (Full Content)

```java
class Solution {
    public void rotate(int[] nums, int k) {
        int n = nums.length;
        // Handle case where k >= n
        // Rotating by n positions brings array back to original
        // So we only need to rotate by k % n positions
        k = k % n;
        
        // Three-step reverse approach
        // Step 1: Reverse entire array
        // This brings last k elements to front (but in reverse order)
        reverse(nums, 0, n - 1);
        
        // Step 2: Reverse first k elements
        // This corrects the order of elements that moved to front
        reverse(nums, 0, k - 1);
        
        // Step 3: Reverse remaining n-k elements
        // This corrects the order of elements that stayed in back
        reverse(nums, k, n - 1);
    }
    
    // Helper method to reverse array between indices left and right (inclusive)
    private void reverse(int[] nums, int left, int right) {
        // Two-pointer swap approach
        while (left < right) {
            // Swap elements at left and right positions
            int temp = nums[left];
            nums[left] = nums[right];
            nums[right] = temp;
            
            // Move pointers towards center
            left++;
            right--;
        }
    }
}
```

## Complexity Analysis

**Time Complexity:** $O(n)$ where $n$ is the length of the array. We reverse the array three times, each taking $O(n)$ time.

**Space Complexity:** $O(1)$. We only use a constant amount of extra space for the temporary variable in the swap operation.

## Similar Problems

- [Rotate List](https://leetcode.com/problems/rotate-list/) - Similar rotation concept on linked list
- [Reverse String](https://leetcode.com/problems/reverse-string/) - Uses similar reverse technique
- [Reverse Words in a String](https://leetcode.com/problems/reverse-words-in-a-string/) - Multiple reverse operations

