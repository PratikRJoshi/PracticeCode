# [1929. Concatenation of Array](https://leetcode.com/problems/concatenation-of-array/)

Given an integer array `nums` of length `n`, you want to create an array `ans` of length `2n` where `ans[i] == nums[i]` and `ans[i + n] == nums[i]` for `0 <= i < n` (0-indexed).

Specifically, `ans` is the concatenation of two `nums` arrays.

Return the array `ans`.

**Example 1:**

```
Input: nums = [1,2,1]
Output: [1,2,1,1,2,1]
Explanation: The array ans is formed as follows:
- ans = [nums[0],nums[1],nums[2],nums[0],nums[1],nums[2]]
- ans = [1,2,1,1,2,1]
```

**Example 2:**

```
Input: nums = [1,3,2,1]
Output: [1,3,2,1,1,3,2,1]
Explanation: The array ans is formed as follows:
- ans = [nums[0],nums[1],nums[2],nums[3],nums[0],nums[1],nums[2],nums[3]]
- ans = [1,3,2,1,1,3,2,1]
```

**Constraints:**

- `n == nums.length`
- `1 <= n <= 1000`
- `1 <= nums[i] <= 1000`

## Intuition/Main Idea:

The problem asks us to create a new array that is the concatenation of the input array with itself. This is a straightforward problem where we need to:
1. Create a new array of size 2n
2. Copy the elements of nums to the first half of the new array
3. Copy the elements of nums to the second half of the new array

We can solve this in a single pass by iterating through the original array and placing each element at both index i and index i+n in the result array.

## Code Mapping:

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Create an array of length 2n | `int[] ans = new int[nums.length * 2];` |
| ans[i] == nums[i] for 0 <= i < n | `ans[i] = nums[i];` |
| ans[i + n] == nums[i] for 0 <= i < n | `ans[i + nums.length] = nums[i];` |

## Final Java Code & Learning Pattern:

```java
class Solution {
    public int[] getConcatenation(int[] nums) {
        // Create a new array with twice the length of the input array
        int[] ans = new int[nums.length * 2];
        
        // Iterate through the original array
        for (int i = 0; i < nums.length; i++) {
            // Copy each element to both the first half and second half of the new array
            ans[i] = nums[i];                 // First copy at index i
            ans[i + nums.length] = nums[i];   // Second copy at index i + n
        }
        
        return ans;
    }
}
```

This solution is straightforward:
1. We create a new array `ans` with twice the length of the input array `nums`.
2. We iterate through the original array and for each element at index `i`:
   - We place it at index `i` in the new array
   - We also place it at index `i + nums.length` in the new array
3. Finally, we return the new array which contains the concatenation of `nums` with itself.

## Complexity Analysis:

- **Time Complexity**: $O(n)$ where n is the length of the input array. We iterate through the array once.
- **Space Complexity**: $O(n)$ for the output array, which has a size of 2n.

## Similar Problems:

1. [1470. Shuffle the Array](https://leetcode.com/problems/shuffle-the-array/)
2. [2574. Left and Right Sum Differences](https://leetcode.com/problems/left-and-right-sum-differences/)
3. [1480. Running Sum of 1d Array](https://leetcode.com/problems/running-sum-of-1d-array/)
4. [2011. Final Value of Variable After Performing Operations](https://leetcode.com/problems/final-value-of-variable-after-performing-operations/)
