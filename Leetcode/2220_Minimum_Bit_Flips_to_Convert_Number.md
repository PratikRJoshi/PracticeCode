# Minimum Bit Flips to Convert Number

## Problem Description

**Problem Link:** [Minimum Bit Flips to Convert Number](https://leetcode.com/problems/minimum-bit-flips-to-convert-number/)

A **bit flip** of a number `x` is choosing a bit in the binary representation of `x` and flipping it from either `0` to `1` or `1` to `0`.

- For example, for `x = 7` (binary representation `111`), we can choose the middle bit and flip it so it becomes `101` which is `5` in decimal.

Given two integers `start` and `goal`, return *the **minimum** number of **bit flips** to convert* `start` *to* `goal`.

**Example 1:**
```
Input: start = 10, goal = 7
Output: 3
Explanation: The binary representation of 10 and 7 are 1010 and 0111 respectively. We can convert 10 to 7 in 3 steps:
- Flip the first bit from the right: 1010 -> 1011.
- Flip the second bit from the right: 1011 -> 1111.
- Flip the third bit from the right: 1111 -> 0111.
```

**Constraints:**
- `0 <= start, goal <= 10^9`

## Intuition/Main Idea

We need to count how many bits differ between start and goal. This is the Hamming distance.

**Core Algorithm:**
- XOR start and goal
- Count number of set bits in the result
- Each set bit represents a bit that needs to be flipped

**Why XOR:** XOR gives 1 where bits differ. Counting set bits in XOR result gives the number of differing bits, which equals minimum flips needed.

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|------------------------|-----------------------------------|
| Count bit differences | XOR operation - Line 5 |
| Count set bits | Bit counting - Lines 6-9 |

## Final Java Code & Learning Pattern (Full Content)

```java
class Solution {
    public int minBitFlips(int start, int goal) {
        // XOR gives 1 where bits differ
        int diff = start ^ goal;
        
        // Count number of set bits (bits that differ)
        int flips = 0;
        while (diff > 0) {
            flips += diff & 1; // Check if least significant bit is set
            diff >>= 1; // Right shift to check next bit
        }
        
        return flips;
    }
}
```

## Complexity Analysis

**Time Complexity:** $O(\log(max(start, goal)))$. We process bits until diff becomes 0.

**Space Complexity:** $O(1)$. We only use a constant amount of extra space.

## Similar Problems

- [Hamming Distance](https://leetcode.com/problems/hamming-distance/) - Same problem
- [Number of 1 Bits](https://leetcode.com/problems/number-of-1-bits/) - Count set bits
- [Single Number](https://leetcode.com/problems/single-number/) - XOR operation

