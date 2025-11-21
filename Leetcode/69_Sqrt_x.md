# Sqrt(x)

## Problem Description

**Problem Link:** [Sqrt(x)](https://leetcode.com/problems/sqrtx/)

Given a non-negative integer `x`, return *the square root of* `x` *rounded down to the nearest integer*. The returned integer should be **non-negative** as well.

You **must not use** any built-in exponent function or operator.

- For example, do not use `pow(x, 0.5)` in c++ or `x ** 0.5` in python.

**Example 1:**
```
Input: x = 4
Output: 2
Explanation: The square root of 4 is 2, so we return 2.
```

**Example 2:**
```
Input: x = 8
Output: 2
Explanation: The square root of 8 is 2.82842..., and since we round it down to the nearest integer, 2 is returned.
```

**Constraints:**
- `0 <= x <= 2^31 - 1`

## Intuition/Main Idea

We can use binary search on the answer space. The square root of x is between 0 and x.

**Core Algorithm:**
- Binary search in range [0, x]
- For each mid, check if mid*mid <= x
- Find the largest mid such that mid*mid <= x

**Why binary search:** Instead of linear search, we can binary search the answer space. This reduces time from $O(\sqrt{x})$ to $O(\log x)$.

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|------------------------|-----------------------------------|
| Binary search on answer | While loop - Line 6 |
| Check mid*mid <= x | Condition - Line 9 |
| Find largest valid mid | Left pointer tracking - Lines 10-13 |
| Avoid overflow | Use long - Line 8 |

## Final Java Code & Learning Pattern (Full Content)

```java
class Solution {
    public int mySqrt(int x) {
        if (x < 2) return x;
        
        int left = 1;
        int right = x;
        
        // Binary search for square root
        // We use < instead of <= to avoid infinite loop
        // When left == right, we've found the answer
        while (left < right) {
            // Use long to avoid overflow
            int mid = left + (right - left) / 2;
            long square = (long) mid * mid;
            
            // If mid*mid <= x, mid could be answer, search right half
            // We don't exclude mid, so right = mid (not mid - 1)
            if (square <= x) {
                left = mid + 1;
            }
            // If mid*mid > x, mid is too large, search left half
            // We exclude mid, so right = mid - 1
            else {
                right = mid;
            }
        }
        
        // After loop, left points to first number whose square > x
        // So left - 1 is the square root
        return left - 1;
    }
}
```

## Binary Search Decision Guide

**How to decide whether to use < or <= in the main loop condition:**
- Use `<` here because we want to stop when left and right converge
- When `left == right`, we've narrowed down to the answer

**How to decide if pointers should be set to mid + 1 or mid - 1 or mid:**
- When `square <= x`, mid could be valid, but we want larger, so `left = mid + 1`
- When `square > x`, mid is invalid, so `right = mid` (exclude mid)

**How to decide what would be the return value:**
- After loop, `left` points to first number whose square > x
- So `left - 1` is the largest number whose square <= x (the square root)

## Complexity Analysis

**Time Complexity:** $O(\log x)$. Binary search halves the search space each iteration.

**Space Complexity:** $O(1)$. We only use a constant amount of extra space.

## Similar Problems

- [Search Insert Position](https://leetcode.com/problems/search-insert-position/) - Similar binary search pattern
- [Pow(x, n)](https://leetcode.com/problems/powx-n/) - Exponentiation
- [Find Peak Element](https://leetcode.com/problems/find-peak-element/) - Binary search variant

