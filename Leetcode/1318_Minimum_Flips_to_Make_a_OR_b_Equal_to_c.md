# Minimum Flips to Make a OR b Equal to c

## Problem Description

**Problem Link:** [Minimum Flips to Make a OR b Equal to c](https://leetcode.com/problems/minimum-flips-to-make-a-or-b-equal-to-c/)

Given 3 positives numbers `a`, `b` and `c`. Return the minimum flips required in some bits of `a` and `b` to make ( `a` OR `b` == `c` ). (bitwise OR operation).
Flip operation consists of change **any** single bit `1` to `0` or change the bit `0` to `1` in their binary representation.

**Example 1:**
```
Input: a = 2, b = 6, c = 5
Output: 3
Explanation: After flips a = 1 , b = 4 , c = 5 such that (a OR b == c)
```

**Constraints:**
- `1 <= a <= 10^9`
- `1 <= b <= 10^9`
- `1 <= c <= 10^9`

## Intuition/Main Idea

We need to make (a OR b) == c by flipping minimum bits. Check each bit position.

**Core Algorithm:**
- For each bit position, check what's needed
- If c has bit 1: at least one of a or b must have bit 1
- If c has bit 0: both a and b must have bit 0
- Count flips needed

**Why bit-by-bit:** Each bit position is independent. We can optimize each position separately.

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|------------------------|-----------------------------------|
| Count minimum flips | Bit-by-bit check - Lines 6-20 |
| Check each bit | Bit extraction - Lines 9-11 |
| Calculate flips | Conditional logic - Lines 13-18 |

## Final Java Code & Learning Pattern (Full Content)

```java
class Solution {
    public int minFlips(int a, int b, int c) {
        int flips = 0;
        
        // Check each bit position
        for (int i = 0; i < 32; i++) {
            int bitA = (a >> i) & 1;
            int bitB = (b >> i) & 1;
            int bitC = (c >> i) & 1;
            
            if (bitC == 1) {
                // c has bit 1: need at least one of a or b to have bit 1
                if (bitA == 0 && bitB == 0) {
                    flips++; // Flip one bit
                }
            } else {
                // c has bit 0: both a and b must have bit 0
                if (bitA == 1) flips++;
                if (bitB == 1) flips++;
            }
        }
        
        return flips;
    }
}
```

## Complexity Analysis

**Time Complexity:** $O(32) = O(1)$. We check 32 bits.

**Space Complexity:** $O(1)$. We only use a constant amount of extra space.

## Similar Problems

- [Single Number II](https://leetcode.com/problems/single-number-ii/) - Bit manipulation
- [Minimum Bit Flips to Convert Number](https://leetcode.com/problems/minimum-bit-flips-to-convert-number/) - Similar bit flipping
- [Number of 1 Bits](https://leetcode.com/problems/number-of-1-bits/) - Bit counting

