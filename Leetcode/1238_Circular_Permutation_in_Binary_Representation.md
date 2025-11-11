# Circular Permutation in Binary Representation

## Problem Description

**Problem Link:** [Circular Permutation in Binary Representation](https://leetcode.com/problems/circular-permutation-in-binary-representation/)

Given 2 integers `n` and `start`. Your task is return **any** permutation `p` of `(0, 1, 2, ..., 2^n -1)` such that :

- `p[0] = start`
- `p[i]` and `p[i+1]` differ by only one bit in their binary representation.
- `p[0]` and `p[2^n - 1]` must also differ by only one bit.

**Example 1:**

```
Input: n = 2, start = 3
Output: [3,2,0,1]
Explanation: The binary representation of the permutation is (11,10,00,01).
All the adjacent element differ by one bit. Another valid permutation is [3,1,0,2]
```

**Example 2:**

```
Input: n = 3, start = 2
Output: [2,6,7,5,4,0,1,3]
Explanation: The binary representation is (010,110,111,101,100,000,001,011).
```

**Constraints:**
- `1 <= n <= 16`
- `0 <= start < 2^n`

## Intuition/Main Idea

This is a **Gray code** problem. We need to generate a circular Gray code sequence starting from `start`.

**Core Algorithm:**
1. Generate standard Gray code sequence.
2. Find position of `start` in the sequence.
3. Rotate sequence to start from `start`.

**Why Gray code works:** Gray code ensures consecutive numbers differ by exactly one bit. By rotating, we can start from any position while maintaining the property.

## Code Mapping

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Generate Gray code | Gray code generation - Lines 6-12 |
| Find start position | Position search - Lines 14-18 |
| Rotate sequence | Rotation - Lines 20-23 |
| Return result | Return statement - Line 24 |

## Final Java Code & Learning Pattern

```java
import java.util.*;

class Solution {
    public List<Integer> circularPermutation(int n, int start) {
        // Generate Gray code sequence
        List<Integer> grayCode = new ArrayList<>();
        for (int i = 0; i < (1 << n); i++) {
            grayCode.add(i ^ (i >> 1)); // Gray code formula
        }
        
        // Find start position
        int startIndex = 0;
        for (int i = 0; i < grayCode.size(); i++) {
            if (grayCode.get(i) == start) {
                startIndex = i;
                break;
            }
        }
        
        // Rotate to start from start
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < grayCode.size(); i++) {
            result.add(grayCode.get((startIndex + i) % grayCode.size()));
        }
        
        return result;
    }
}
```

**Explanation of Key Code Sections:**

1. **Generate Gray Code (Lines 6-12):** Use formula `i ^ (i >> 1)` to generate Gray code sequence.

2. **Find Start (Lines 14-18):** Find the index of `start` in the Gray code sequence.

3. **Rotate (Lines 20-23):** Rotate the sequence to start from `start`.

**Why Gray code:**
- **Property:** Consecutive numbers differ by exactly one bit.
- **Circular:** First and last also differ by one bit.
- **Rotation:** Starting from any position maintains the property.

**Example walkthrough for `n = 2, start = 3`:**
- Gray code: [0,1,3,2] (binary: 00,01,11,10)
- Start index: 2 (value 3)
- Rotate: [3,2,0,1] ✓

## Complexity Analysis

- **Time Complexity:** $O(2^n)$ to generate Gray code and find start.

- **Space Complexity:** $O(2^n)$ for the Gray code sequence.

## Similar Problems

Problems that can be solved using similar patterns:

1. **1238. Circular Permutation in Binary Representation** (this problem) - Gray code
2. **89. Gray Code** - Generate Gray code
3. **1980. Find Unique Binary String** - Binary string generation
4. **784. Letter Case Permutation** - String permutation
5. **1415. The k-th Lexicographical String of All Happy Strings of Length n** - String generation
6. **17. Letter Combinations of a Phone Number** - String combinations
7. **22. Generate Parentheses** - String generation
8. **46. Permutations** - Generate permutations

