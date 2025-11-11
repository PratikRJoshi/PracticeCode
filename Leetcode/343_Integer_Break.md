# Integer Break

## Problem Description

**Problem Link:** [Integer Break](https://leetcode.com/problems/integer-break/)

Given an integer `n`, break it into the sum of `k` **positive integers**, where `k >= 2`, and maximize the product of those integers.

Return *the maximum product you can get*.

**Example 1:**
```
Input: n = 2
Output: 1
Explanation: 2 = 1 + 1, 1 × 1 = 1.
```

**Example 2:**
```
Input: n = 10
Output: 36
Explanation: 10 = 3 + 3 + 4, 3 × 3 × 4 = 36.
```

**Constraints:**
- `2 <= n <= 58`

## Intuition/Main Idea

This is a **dynamic programming** problem. The key insight is that for optimal breaking, we should use as many 3's as possible, since 3 gives the best product-to-sum ratio.

**Core Algorithm:**
1. Use DP where `dp[i]` = maximum product from breaking `i`.
2. For each `i`, try breaking it as `j + (i-j)` for all `1 <= j < i`.
3. Take the maximum: `dp[i] = max(j * (i-j), j * dp[i-j])`.

**Mathematical Insight:** For `n > 4`, it's optimal to break into 3's. For `n = 4`, breaking as `2+2` gives same product as `3+1` but uses more parts.

## Code Mapping

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Base cases | Base case handling - Lines 7-9 |
| DP array | DP initialization - Line 11 |
| Try all breaks | Nested loops - Lines 13-17 |
| Calculate product | Product calculation - Line 15 |
| Return result | Return statement - Line 18 |

## Final Java Code & Learning Pattern

### Top-Down / Memoized Version

```java
class Solution {
    public int integerBreak(int n) {
        if (n == 2) return 1;
        if (n == 3) return 2;
        
        Integer[] memo = new Integer[n + 1];
        return breakInteger(n, memo);
    }
    
    private int breakInteger(int n, Integer[] memo) {
        // Base case: don't break if n <= 3
        if (n <= 3) {
            return n;
        }
        
        if (memo[n] != null) {
            return memo[n];
        }
        
        int maxProduct = 0;
        for (int i = 1; i < n; i++) {
            // Option 1: Break into i and (n-i), don't break further
            // Option 2: Break into i and break (n-i) further
            maxProduct = Math.max(maxProduct, 
                Math.max(i * (n - i), i * breakInteger(n - i, memo)));
        }
        
        memo[n] = maxProduct;
        return maxProduct;
    }
}
```

### Bottom-Up Version

```java
class Solution {
    public int integerBreak(int n) {
        // Base cases
        if (n == 2) return 1;
        if (n == 3) return 2;
        
        // DP: dp[i] = maximum product from breaking i
        int[] dp = new int[n + 1];
        
        // Base cases: for i <= 3, don't break (but we handle n=2,3 separately)
        dp[1] = 1;
        dp[2] = 2;
        dp[3] = 3;
        
        for (int i = 4; i <= n; i++) {
            int maxProduct = 0;
            for (int j = 1; j < i; j++) {
                // Break as j + (i-j)
                // Can break (i-j) further or not
                maxProduct = Math.max(maxProduct, j * dp[i - j]);
            }
            dp[i] = maxProduct;
        }
        
        return dp[n];
    }
}
```

### Mathematical Approach (Optimal)

```java
class Solution {
    public int integerBreak(int n) {
        if (n == 2) return 1;
        if (n == 3) return 2;
        
        int product = 1;
        while (n > 4) {
            product *= 3;
            n -= 3;
        }
        product *= n;
        
        return product;
    }
}
```

**Explanation of Key Code Sections:**

**DP Approach:**

1. **Base Cases (Lines 7-9):** Handle `n = 2` and `n = 3` separately.

2. **DP Initialization (Lines 11-14):** For `i <= 3`, the optimal is not to break further (but we handle n=2,3 as base cases).

3. **DP Transition (Lines 16-21):** For each `i >= 4`, try all possible breaks `j + (i-j)`:
   - Break `(i-j)` further: `j * dp[i-j]`
   - Don't break `(i-j)`: `j * (i-j)` (handled by dp[i-j] when i-j <= 3)

**Mathematical Approach:**

1. **Greedy with 3's (Lines 7-11):** While `n > 4`, break off 3's and multiply.
2. **Handle Remainder (Line 12):** Multiply by the remaining `n` (which will be 2, 3, or 4).

**Why 3 is optimal:**
- For `n = 6`: `3*3 = 9 > 2*2*2 = 8 > 4*2 = 8`
- For `n = 7`: `3*4 = 12 > 3*2*2 = 12 = 2*2*3 = 12`
- Generally, 3 gives the best product-to-sum ratio.

**Intuition behind generating subproblems:**
- **Subproblem:** "What is the maximum product from breaking integer `i`?"
- **Why this works:** To break `i`, we can break it as `j + (i-j)` for any `1 <= j < i`. We can then break `(i-j)` further or not.
- **Overlapping subproblems:** Multiple numbers may break into the same subproblems.

**Example walkthrough for `n = 10`:**
- DP: dp[1]=1, dp[2]=2, dp[3]=3
- dp[4] = max(1*3, 2*2, 3*1) = max(3, 4, 3) = 4
- dp[5] = max(1*4, 2*3, 3*2, 4*1) = max(4, 6, 6, 4) = 6
- Continue...
- Mathematical: 10 = 3+3+4 → 3*3*4 = 36 ✓

## Complexity Analysis

- **Time Complexity:** 
  - DP: $O(n^2)$ for nested loops.
  - Mathematical: $O(n)$ for the while loop.

- **Space Complexity:** 
  - DP: $O(n)$ for the DP array.
  - Mathematical: $O(1)$.

## Similar Problems

Problems that can be solved using similar DP or mathematical patterns:

1. **343. Integer Break** (this problem) - DP or mathematical
2. **279. Perfect Squares** - DP with squares
3. **322. Coin Change** - Unbounded knapsack
4. **518. Coin Change 2** - Count ways
5. **377. Combination Sum IV** - Count combinations
6. **70. Climbing Stairs** - Similar DP pattern
7. **509. Fibonacci Number** - Base DP pattern
8. **746. Min Cost Climbing Stairs** - DP with costs

