# Burst Balloons

## Problem Description

**Problem Link:** [Burst Balloons](https://leetcode.com/problems/burst-balloons/)

You are given `n` balloons, indexed from `0` to `n - 1`. Each balloon is painted with a number on it represented by an array `nums`. You are asked to burst all the balloons.

If you burst the `i`th balloon, you will get `nums[i - 1] * nums[i] * nums[i + 1]` coins. If `i - 1` or `i + 1` goes out of bounds of the array, then treat it as if there is a balloon with a `1` painted on it.

Return *the maximum coins you can collect by bursting the balloons wisely*.

**Example 1:**
```
Input: nums = [3,1,5,8]
Output: 167
Explanation:
nums = [3,1,5,8] --> [3,5,8] --> [3,8] --> [8] --> []
coins =  3*1*5    +   3*5*8   +  1*3*8  + 1*8*1 = 15 + 120 + 24 + 8 = 167
```

**Example 2:**
```
Input: nums = [1,5]
Output: 10
```

**Constraints:**
- `n == nums.length`
- `1 <= n <= 300`
- `0 <= nums[i] <= 100`

## Intuition/Main Idea

This is a **dynamic programming** problem that requires thinking about the problem in reverse. Instead of thinking "which balloon to burst first," we think "which balloon to burst last."

**Core Insight:**
- **Key Idea**: If we think about which balloon to burst **last** in a range `[i, j]`, we can divide the problem into two **independent** subproblems: `[i, k-1]` and `[k+1, j]`
- **Why "last" instead of "first"**: 
  - If we burst `k` **first**: The adjacent balloons change, making left and right subproblems **dependent** on each other
  - If we burst `k` **last**: The boundaries `i-1` and `j+1` remain constant, making left and right subproblems **independent**
- **Boundary Handling**: We add balloons with value 1 at both ends to simplify edge cases

**Why DP works:**
- **Overlapping subproblems**: Multiple ranges share the same subranges
- **Optimal substructure**: The optimal solution for a range depends on optimal solutions for smaller ranges
- **Interval DP pattern**: We process ranges from smallest to largest

## Code Mapping

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Add boundary balloons with value 1 | Array extension - Top-down: Lines 68-73, Bottom-up: Lines 117-122 |
| DP for range maximum coins | Memo/DP array allocation - Top-down: Line 76, Bottom-up: Line 125 |
| Base case: empty range | Base case check - Top-down: Line 83-85 |
| Try each balloon as last to burst | Loop through range - Top-down: Line 94, Bottom-up: Line 134 |
| Calculate coins for last balloon | Coin calculation - Top-down: Line 97, Bottom-up: Line 136 |
| Combine left and right subproblems | Recursive calls/DP lookup - Top-down: Lines 99-100, Bottom-up: Lines 138-139 |
| Return maximum coins | Return statement - Top-down: Line 78, Bottom-up: Line 146 |

## Final Java Code & Learning Pattern

### Intuition Behind Generating Subproblems (Top-Down Approach)

**Subproblem Definition:**
- `dp(left, right)` = maximum coins from bursting all balloons in range `[left, right]` (1-indexed after adding boundaries)

**Why this subproblem works:**
1. **Reverse thinking**: Instead of "which balloon to burst first" (creates dependencies), we think "which balloon to burst last" (creates independence)
2. **Independence**: When we fix balloon `k` as the last one to burst in range `[left, right]`:
   - Left subproblem `[left, k-1]` is independent (boundaries are `left-1` and `k`)
   - Right subproblem `[k+1, right]` is independent (boundaries are `k` and `right+1`)
   - When `k` is burst last, the boundaries that remain are `left-1` and `right+1` (the balloons outside the range)
3. **Coin calculation**: When `k` is burst last, coins = `balloons[left-1] * balloons[k] * balloons[right+1]` because these are the only balloons that remain adjacent to `k`

**Recursive relationship:**
- For range `[left, right]`, try each balloon `k` in `[left, right]` as the last one to burst:
  - Coins from bursting `k` last = `balloons[left-1] * balloons[k] * balloons[right+1]`
  - Total = coins from `k` + `dp(left, k-1)` + `dp(k+1, right)`
  - Take maximum over all choices of `k`

**Why memo array size is `(n+2) × (n+2)`:**
- After adding boundaries, we have `n+2` balloons total (original `n` + 2 boundaries)
- `left` and `right` range from `1` to `n` (the actual balloons, excluding boundaries at index 0 and n+1)
- However, we need indices `0` to `n+1` to handle boundary cases and base cases
- `memo[left][right]` stores result for range `[left, right]` where `left` and `right` can be `1..n`, but we allocate `n+2` to include boundary indices

**Why we add boundary balloons:**
- Simplifies edge case handling: when bursting a balloon at the edge, we multiply by 1 instead of checking bounds
- Makes the recurrence relation uniform: `balloons[left-1]` and `balloons[right+1]` always exist
- The boundaries at index 0 and `n+1` have value 1, matching the problem's rule for out-of-bounds

### Top-Down / Memoized Version

```java
class Solution {
    public int maxCoins(int[] nums) {
        int n = nums.length;
        
        // Add boundary balloons with value 1 at both ends
        // Size: n+2 because we add 1 balloon at start (index 0) and 1 at end (index n+1)
        // This simplifies boundary handling - when bursting edge balloons, we multiply by 1
        int[] balloons = new int[n + 2];
        balloons[0] = 1;           // Left boundary
        balloons[n + 1] = 1;        // Right boundary
        for (int i = 0; i < n; i++) {
            balloons[i + 1] = nums[i];  // Original balloons at indices 1 to n
        }
        
        // Memoization: memo[left][right] = max coins from bursting balloons in range [left, right]
        // Size: (n+2) × (n+2) because:
        // - left and right range from 1 to n (actual balloons, excluding boundaries)
        // - But we allocate n+2 to handle boundary indices and base cases
        // - The +2 accounts for indices 0 and n+1 (boundaries) which we use in calculations
        Integer[][] memo = new Integer[n + 2][n + 2];
        
        // Start with the full range [1, n] (all original balloons, excluding boundaries)
        return maxCoins(balloons, 1, n, memo);
    }
    
    // dp(left, right) = maximum coins from bursting all balloons in range [left, right]
    // We process from left to right (1-indexed after adding boundaries)
    private int maxCoins(int[] balloons, int left, int right, Integer[][] memo) {
        // Base case: no balloons in range (left > right)
        // This happens when we've exhausted a subrange
        if (left > right) {
            return 0;
        }
        
        // Check if we've already computed this subproblem
        if (memo[left][right] != null) {
            return memo[left][right];
        }
        
        int max = 0;
        
        // Try each balloon k in range [left, right] as the last one to burst
        for (int k = left; k <= right; k++) {
            // If k is burst last, the boundaries that remain are balloons[left-1] and balloons[right+1]
            // These are the balloons outside the range [left, right] that are still present
            // Coins = boundaries * k (the three balloons that are adjacent when k is burst)
            int coins = balloons[left - 1] * balloons[k] * balloons[right + 1];
            
            // Add coins from left subproblem [left, k-1]
            // This is independent because k hasn't been burst yet, so boundaries remain constant
            coins += maxCoins(balloons, left, k - 1, memo);
            
            // Add coins from right subproblem [k+1, right]
            // This is independent because k hasn't been burst yet, so boundaries remain constant
            coins += maxCoins(balloons, k + 1, right, memo);
            
            // Take maximum over all choices of k
            max = Math.max(max, coins);
        }
        
        // Store result in memo before returning
        memo[left][right] = max;
        return max;
    }
}
```

**Key Points:**
- **Processing direction**: We process ranges from left to right (1-indexed after adding boundaries)
- **Last balloon thinking**: We try each balloon as the last one to burst, making subproblems independent
- **Boundary handling**: Adding balloons with value 1 at both ends simplifies edge cases
- **Memoization**: We store results to avoid recalculating the same subproblems

### Common Conceptual Questions & Explanations

**Q1: Which line shows balloon k is burst?**

**Answer: No line explicitly shows k being burst!** 

The code calculates the **coins we would get IF k were burst last**, but it doesn't actually burst k. Line 142 calculates:
```java
int coins = balloons[left - 1] * balloons[k] * balloons[right + 1];
```

This is the coins from bursting k **when it's the last balloon in range [left, right]**, meaning all other balloons in that range have already been burst.

**Q2: How do I read the code to learn that k is indeed burst last and not before?**

**Key insight: The code structure implies the order!**

```java
// Lines 146 & 150: Solve subproblems FIRST
coins += maxCoins(balloons, left, k - 1, memo);    // Burst all balloons in [left, k-1]
coins += maxCoins(balloons, k + 1, right, memo);   // Burst all balloons in [k+1, right]

// Line 142: THEN calculate coins for k
int coins = balloons[left - 1] * balloons[k] * balloons[right + 1];
```

**Reading the logic:**
1. **First**: We recursively solve `[left, k-1]` → all balloons to the left of k are burst
2. **Then**: We recursively solve `[k+1, right]` → all balloons to the right of k are burst  
3. **Finally**: Only k remains in the range, so when we calculate `balloons[left-1] * balloons[k] * balloons[right+1]`, k is the last one

The **boundaries** `balloons[left-1]` and `balloons[right+1]` are still there because they're **outside** the range `[left, right]`.

**Conceptual vs Code Order:**

*Conceptual Order (what actually happens):*
1. Burst all balloons in `[left, k-1]` (left subproblem)
2. Burst all balloons in `[k+1, right]` (right subproblem)  
3. Now only k remains in the range, with boundaries `left-1` and `right+1`
4. Burst k last → get `balloons[left-1] * balloons[k] * balloons[right+1]` coins

*Code Order (for calculation efficiency):*
```java
// Calculate coins for k being last (Line 142)
int coins = balloons[left - 1] * balloons[k] * balloons[right + 1];

// Add coins from subproblems (Lines 146 & 150)  
coins += maxCoins(balloons, left, k - 1, memo);
coins += maxCoins(balloons, k + 1, right, memo);
```

**Why this code order works:**
- We can calculate the "k burst last" coins immediately because we know what the boundaries will be
- The subproblem results are independent of when we calculate the k coins
- We're just **adding up** the total: `coins_from_k + coins_from_left + coins_from_right`

**Visual Example:**

For `nums = [3,1,5,8]` with boundaries → `[1,3,1,5,8,1]`, let's say `k=2` (balloon with value 1) in range `[1,4]`:

*Conceptual execution:*
1. Burst all in `[1,1]` → burst balloon 3 → get some coins
2. Burst all in `[3,4]` → burst balloons 5,8 → get some coins  
3. Now array looks like `[1, _, 1, _, _, 1]` where only k=2 (value 1) remains
4. Burst k=2 last → get `1 * 1 * 1 = 1` coins

*Code calculation:*
```java
coins = balloons[0] * balloons[2] * balloons[5];  // 1 * 1 * 1 = 1
coins += maxCoins(..., 1, 1, ...);               // + coins from left
coins += maxCoins(..., 3, 4, ...);               // + coins from right
```

The key insight: **we're calculating the total coins, not simulating the actual bursting sequence**.

### Bottom-Up Version

```java
class Solution {
    public int maxCoins(int[] nums) {
        int n = nums.length;
        
        // Add boundary balloons with value 1 at both ends
        // Size: n+2 because we add 1 balloon at start (index 0) and 1 at end (index n+1)
        int[] balloons = new int[n + 2];
        balloons[0] = 1;
        balloons[n + 1] = 1;
        for (int i = 0; i < n; i++) {
            balloons[i + 1] = nums[i];
        }
        
        // DP: dp[i][j] = max coins from bursting all balloons in range [i, j]
        // Size: (n+2) × (n+2) because:
        // - i and j range from 1 to n (actual balloons, excluding boundaries)
        // - But we allocate n+2 to handle boundary indices used in calculations
        // - The +2 accounts for indices 0 and n+1 (boundaries)
        int[][] dp = new int[n + 2][n + 2];
        
        // Process ranges by length (from smallest to largest)
        // This ensures that when we calculate dp[i][j], the subproblems dp[i][k-1] and dp[k+1][j] are already computed
        for (int len = 1; len <= n; len++) {
            // For each possible starting position i
            for (int i = 1; i <= n - len + 1; i++) {
                int j = i + len - 1;  // Ending position for range of length len
                
                // Try each balloon k in range [i, j] as the last one to burst
                for (int k = i; k <= j; k++) {
                    // Coins from bursting k last = boundaries * k
                    // When k is burst last, balloons[i-1] and balloons[j+1] are the boundaries that remain
                    int coins = balloons[i - 1] * balloons[k] * balloons[j + 1];
                    
                    // Add coins from left subproblem [i, k-1]
                    // dp[i][k-1] is already computed because k-1 < j (we process by length)
                    coins += dp[i][k - 1];
                    
                    // Add coins from right subproblem [k+1, j]
                    // dp[k+1][j] is already computed because k+1 > i (we process by length)
                    coins += dp[k + 1][j];
                    
                    // Update maximum for range [i, j]
                    dp[i][j] = Math.max(dp[i][j], coins);
                }
            }
        }
        
        // Return maximum coins for the entire range [1, n] (all original balloons)
        return dp[1][n];
    }
}
```

**When Bottom-Up is Better Than Top-Down:**

1. **No recursion overhead**: Bottom-up avoids function call stack overhead
2. **Better cache locality**: Iterating through array sequentially is more cache-friendly
3. **Predictable memory access**: Sequential access pattern is easier for CPU to optimize
4. **No stack overflow risk**: For very large inputs, bottom-up avoids potential stack overflow from deep recursion
5. **Natural interval DP pattern**: Processing by length ensures dependencies are computed first

**Why we process by length:**
- When computing `dp[i][j]`, we need `dp[i][k-1]` and `dp[k+1][j]`
- Both subranges `[i, k-1]` and `[k+1, j]` have length less than `[i, j]`
- By processing from length 1 to length n, we ensure all smaller ranges are computed first
- This is the standard interval DP pattern

**Explanation of Key Code Sections:**

1. **Add Boundary Balloons (Lines 117-122):** We add balloons with value 1 at both ends. This simplifies boundary handling - when we burst a balloon at the edge, we multiply by 1 instead of checking bounds.

2. **DP Array Initialization (Line 125):** `dp[i][j]` represents the maximum coins we can get from bursting all balloons in the range `[i, j]` (1-indexed after adding boundaries). Size is `(n+2) × (n+2)` to handle boundary indices.

3. **Process by Length (Line 128):** We process ranges from smallest to largest. This ensures that when we calculate `dp[i][j]`, the subproblems `dp[i][k-1]` and `dp[k+1][j]` are already computed.

4. **Try Each Balloon as Last (Lines 134-142):** For each range `[i, j]`, we try each balloon `k` as the last one to burst:
   - **Coin Calculation (Line 136):** When `k` is burst last, the boundaries are `balloons[i-1]` and `balloons[j+1]`, so coins = `balloons[i-1] * balloons[k] * balloons[j+1]`.
   - **Combine Subproblems (Lines 138-139):** Add the maximum coins from the left range `[i, k-1]` and right range `[k+1, j]`.
   - **Update Maximum (Line 141):** Take the maximum over all choices of `k`.

5. **Return Result (Line 146):** `dp[1][n]` contains the maximum coins for the entire range (excluding boundaries).

## Complexity Analysis

- **Time Complexity:** $O(n^3)$ where $n$ is the number of balloons. 
  - Top-down: Each subproblem `(left, right)` is computed at most once, and there are $O(n^2)$ subproblems. For each subproblem, we try $O(n)$ balloons as the last one, giving $O(n^3)$ total.
  - Bottom-up: We have three nested loops: length ($n$), start position ($O(n)$), and middle position ($O(n)$), giving $O(n^3)$ total.

- **Space Complexity:** 
  - Top-down: $O(n^2)$ for the memoization array, plus $O(n)$ for recursion stack
  - Bottom-up: $O(n^2)$ for the DP array

## Similar Problems

Problems that can be solved using similar interval DP patterns:

1. **312. Burst Balloons** (this problem) - Interval DP with last element thinking
2. **516. Longest Palindromic Subsequence** - Interval DP
3. **1039. Minimum Score Triangulation of Polygon** - Interval DP
4. **1547. Minimum Cost to Cut a Stick** - Interval DP
5. **1000. Minimum Cost to Merge Stones** - Interval DP with constraints
6. **1130. Minimum Cost Tree From Leaf Values** - Interval DP
7. **375. Guess Number Higher or Lower II** - Interval DP
8. **486. Predict the Winner** - Interval DP game theory
9. **664. Strange Printer** - Interval DP
10. **87. Scramble String** - Interval DP variant
