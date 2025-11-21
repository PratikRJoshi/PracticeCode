# Single Number II

## Problem Description

**Problem Link:** [Single Number II](https://leetcode.com/problems/single-number-ii/)

Given an integer array `nums` where every element appears **three times** except for one, which appears **exactly once**. Find the single element and return it.

You must implement a solution with a linear runtime complexity and use only constant extra space.

**Example 1:**
```
Input: nums = [2,2,3,2]
Output: 3
```

**Example 2:**
```
Input: nums = [0,1,0,1,0,1,99]
Output: 99
```

**Constraints:**
- `1 <= nums.length <= 3 * 10^4`
- `-2^31 <= nums[i] <= 2^31 - 1`
- Each element in `nums` appears exactly **three times** except for one element which appears **once**.

## Intuition/Main Idea

We need to find the number that appears once while others appear three times. We can use bit manipulation.

**Core Algorithm:**
- Count bits at each position across all numbers
- For each bit position, count % 3 gives the bit of the single number
- Reconstruct the single number from its bits

**Why bit counting:** If a number appears 3 times, its bits contribute 3 to each position. The single number's bits contribute 1, so count % 3 extracts them.

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|------------------------|-----------------------------------|
| Find single number | Bit counting - Lines 6-15 |
| Count bits | Bit extraction - Lines 9-11 |
| Reconstruct number | Bit setting - Lines 13-14 |

## Final Java Code & Learning Pattern (Full Content)

```java
class Solution {
    public int singleNumber(int[] nums) {
        int result = 0;
        
        // Check each bit position (32 bits for int)
        for (int i = 0; i < 32; i++) {
            int count = 0;
            
            // Count how many numbers have bit i set
            for (int num : nums) {
                if ((num >> i & 1) == 1) {
                    count++;
                }
            }
            
            // If count % 3 == 1, this bit belongs to single number
            if (count % 3 == 1) {
                result |= (1 << i);
            }
        }
        
        return result;
    }
}
```

## Complexity Analysis

**Time Complexity:** $O(32 \times n) = O(n)$ where $n$ is array length. We check 32 bits for each number.

**Space Complexity:** $O(1)$. We only use a constant amount of extra space.

## Similar Problems

- [Single Number](https://leetcode.com/problems/single-number/) - XOR solution
- [Single Number III](https://leetcode.com/problems/single-number-iii/) - Two single numbers
- [Missing Number](https://leetcode.com/problems/missing-number/) - Bit manipulation

