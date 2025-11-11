# Perfect Squares

## Problem Description

**Problem Link:** [Perfect Squares](https://leetcode.com/problems/perfect-squares/)

Given an integer `n`, return *the least number of perfect square numbers that sum to `n`*.

A **perfect square** is an integer that is the square of an integer; in other words, it is the product of some integer with itself. For example, `1`, `4`, `9`, and `16` are perfect squares while `3` and `11` are not.

**Example 1:**
```
Input: n = 12
Output: 3
Explanation: 12 = 4 + 4 + 4.
```

**Example 2:**
```
Input: n = 13
Output: 2
Explanation: 13 = 4 + 9.
```

**Constraints:**
- `1 <= n <= 10^4`

## Intuition/Main Idea

This is an **unbounded knapsack** problem where we want to find the minimum number of perfect squares that sum to `n`.

**Core Algorithm:**
1. Generate all perfect squares up to `n`.
2. Use DP where `dp[i]` = minimum number of perfect squares to form sum `i`.
3. For each sum `i`, try each perfect square `sq` where `sq <= i`.
4. `dp[i] = min(dp[i], 1 + dp[i - sq])`.

**Why DP works:** To form sum `i`, we can use any perfect square `sq <= i`, then form the remaining sum `i - sq` optimally. This has overlapping subproblems.

## Code Mapping

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Generate perfect squares | Square generation - Lines 7-10 |
| DP for minimum count | DP array - Line 12 |
| Initialize with maximum | Arrays.fill - Line 13 |
| Base case | dp[0] = 0 - Line 14 |
| Process each sum | Outer loop - Line 16 |
| Try each square | Inner loop - Line 17 |
| Update minimum | DP update - Line 19 |
| Return result | Return statement - Line 22 |

## Final Java Code & Learning Pattern

### Top-Down / Memoized Version

```java
class Solution {
    public int numSquares(int n) {
        Integer[] memo = new Integer[n + 1];
        return minSquares(n, memo);
    }
    
    private int minSquares(int n, Integer[] memo) {
        if (n == 0) {
            return 0;
        }
        
        if (memo[n] != null) {
            return memo[n];
        }
        
        int min = Integer.MAX_VALUE;
        for (int i = 1; i * i <= n; i++) {
            int square = i * i;
            min = Math.min(min, 1 + minSquares(n - square, memo));
        }
        
        memo[n] = min;
        return min;
    }
}
```

### Bottom-Up Version

```java
import java.util.*;

class Solution {
    public int numSquares(int n) {
        // Generate all perfect squares up to n
        List<Integer> squares = new ArrayList<>();
        for (int i = 1; i * i <= n; i++) {
            squares.add(i * i);
        }
        
        // DP: dp[i] = minimum number of perfect squares to form sum i
        int[] dp = new int[n + 1];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;  // Base case: 0 squares to form sum 0
        
        // Process each sum from 1 to n
        for (int i = 1; i <= n; i++) {
            for (int square : squares) {
                if (square <= i && dp[i - square] != Integer.MAX_VALUE) {
                    dp[i] = Math.min(dp[i], 1 + dp[i - square]);
                }
            }
        }
        
        return dp[n];
    }
}
```

**Space-Optimized Version (No Squares List):**

```java
class Solution {
    public int numSquares(int n) {
        int[] dp = new int[n + 1];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;
        
        for (int i = 1; i <= n; i++) {
            // Try all perfect squares up to i
            for (int j = 1; j * j <= i; j++) {
                int square = j * j;
                dp[i] = Math.min(dp[i], 1 + dp[i - square]);
            }
        }
        
        return dp[n];
    }
}
```

**Explanation of Key Code Sections:**

1. **Generate Squares (Lines 7-10):** Generate all perfect squares up to `n`. Alternatively, we can generate them on-the-fly in the inner loop.

2. **DP Initialization (Lines 12-14):** Initialize `dp` with maximum values, except `dp[0] = 0` (0 squares needed for sum 0).

3. **DP Transition (Lines 16-20):** For each sum `i`:
   - Try each perfect square `square <= i`.
   - Update `dp[i] = min(dp[i], 1 + dp[i - square])`.

4. **Return Result (Line 22):** `dp[n]` contains the minimum number of perfect squares.

**Intuition behind generating subproblems:**
- **Subproblem:** "What is the minimum number of perfect squares to form sum `i`?"
- **Why this works:** To form sum `i`, we can use any perfect square `sq <= i`, then form the remaining sum `i - sq` optimally.
- **Overlapping subproblems:** Multiple sums may use the same subproblems.

**Example walkthrough for `n = 12`:**
- Squares: [1, 4, 9]
- dp[0] = 0
- dp[1] = 1 (use 1)
- dp[2] = 2 (use 1+1)
- dp[3] = 3 (use 1+1+1)
- dp[4] = 1 (use 4)
- dp[5] = 2 (use 4+1)
- ...
- dp[12] = 3 (use 4+4+4) ✓

## Complexity Analysis

- **Time Complexity:** $O(n \times \sqrt{n})$ where we process $n$ sums and try up to $\sqrt{n}$ perfect squares for each.

- **Space Complexity:** $O(n)$ for the DP array.

## Similar Problems

Problems that can be solved using similar unbounded knapsack patterns:

1. **279. Perfect Squares** (this problem) - Unbounded knapsack with squares
2. **322. Coin Change** - Unbounded knapsack (minimum coins)
3. **518. Coin Change 2** - Unbounded knapsack (count ways)
4. **377. Combination Sum IV** - Unbounded knapsack (count permutations)
5. **343. Integer Break** - Unbounded breaking
6. **983. Minimum Cost For Tickets** - Unbounded with ticket types
7. **70. Climbing Stairs** - Similar DP pattern
8. **746. Min Cost Climbing Stairs** - DP with costs

