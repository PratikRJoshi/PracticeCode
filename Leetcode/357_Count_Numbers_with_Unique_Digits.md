# Count Numbers with Unique Digits

## Problem Description

**Problem Link:** [Count Numbers with Unique Digits](https://leetcode.com/problems/count-numbers-with-unique-digits/)

Given an integer `n`, return *the count of all numbers with unique digits, `x`, where `0 <= x < 10^n`*.

**Example 1:**

```
Input: n = 2
Output: 91
Explanation: The answer should be the total numbers in the range of 0 ≤ x < 100, excluding 11,22,33,44,55,66,77,88,99
```

**Example 2:**

```
Input: n = 0
Output: 1
```

**Constraints:**
- `0 <= n <= 8`

## Intuition/Main Idea

This is a **combinatorics** problem. We need to count numbers with unique digits.

**Core Algorithm:**
1. For numbers with `k` digits (1 ≤ k ≤ n):
   - First digit: 9 choices (1-9, can't be 0)
   - Remaining digits: 9, 8, 7, ... choices (can't repeat previous digits)
   - Count = 9 × 9 × 8 × ... × (11-k)
2. Sum counts for all k from 1 to n, plus 1 for 0.

**Why combinatorics works:** We count valid numbers digit by digit, ensuring no repetition. The formula follows from the constraint that digits must be unique.

## Code Mapping

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Base case: n=0 | Base case - Line 5 |
| Count for each length | Length loop - Line 8 |
| Calculate choices | Choices calculation - Lines 10-13 |
| Sum counts | Sum accumulation - Line 14 |
| Return result | Return statement - Line 16 |

## Final Java Code & Learning Pattern

```java
class Solution {
    public int countNumbersWithUniqueDigits(int n) {
        if (n == 0) {
            return 1;
        }
        
        int total = 10; // Count for 1-digit numbers (0-9)
        
        for (int k = 2; k <= n; k++) {
            int count = 9; // First digit: 9 choices (1-9)
            int choices = 9; // Remaining digits: 9, 8, 7, ...
            
            for (int i = 1; i < k; i++) {
                count *= choices;
                choices--;
            }
            
            total += count;
        }
        
        return total;
    }
}
```

**Explanation of Key Code Sections:**

1. **Base Case (Lines 5-7):** If `n = 0`, only number is 0, return 1.

2. **Count for Each Length (Lines 9-15):** For numbers with `k` digits:
   - **First Digit (Line 10):** 9 choices (1-9, can't be 0).
   - **Remaining Digits (Lines 11-14):** For each remaining position, multiply by available choices (decreasing from 9).

3. **Sum Counts (Line 14):** Add count for current length to total.

**Why this works:**
- For 1-digit: 10 numbers (0-9).
- For k-digit (k ≥ 2): First digit has 9 choices (1-9), next has 9 choices (0-9 except first), next has 8, etc.
- Formula: `9 × 9 × 8 × ... × (11-k)` for k-digit numbers.

**Example:**
- n=2: total = 10 (1-digit) + 9×9 (2-digit) = 10 + 81 = 91 ✓

## Complexity Analysis

- **Time Complexity:** $O(n^2)$ where we iterate for each length and calculate choices.

- **Space Complexity:** $O(1)$ extra space.

## Similar Problems

Problems that can be solved using similar combinatorics patterns:

1. **357. Count Numbers with Unique Digits** (this problem) - Combinatorics counting
2. **902. Numbers At Most N Given Digit Set** - Digit DP
3. **1012. Numbers With Repeated Digits** - Complement counting
4. **233. Number of Digit One** - Digit counting
5. **788. Rotated Digits** - Digit DP
6. **600. Non-negative Integers without Consecutive Ones** - Digit DP
7. **1088. Confusing Number II** - Digit DP
8. **1397. Find All Good Strings** - String DP

