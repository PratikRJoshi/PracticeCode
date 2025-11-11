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

This is a **dynamic programming** problem that requires thinking about the problem in reverse. Instead of thinking "which balloon to burst first," think "which balloon to burst last."

**Key Insight:** If we think about which balloon to burst **last** in a range `[i, j]`, we can divide the problem into two independent subproblems: `[i, k-1]` and `[k+1, j]`.

**Core Algorithm:**
1. Add boundary balloons with value 1 at both ends.
2. Use DP where `dp[i][j]` = maximum coins from bursting all balloons in range `[i, j]`.
3. For each range, try each balloon `k` as the last one to burst.
4. Recurrence: `dp[i][j] = max(dp[i][j], nums[i-1]*nums[k]*nums[j+1] + dp[i][k-1] + dp[k+1][j])`

**Why this works:** By fixing the last balloon to burst, the left and right subproblems become independent. The coins from bursting `k` last is `nums[i-1] * nums[k] * nums[j+1]` because `i-1` and `j+1` are the boundaries that remain.

## Code Mapping

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Add boundary balloons | Array extension - Lines 6-9 |
| DP for range maximum coins | 2D DP array - Line 11 |
| Process ranges by length | Length loop - Line 13 |
| Try each balloon as last | Middle loop - Line 15 |
| Calculate coins for last balloon | Coin calculation - Line 18 |
| Combine subproblems | DP transition - Line 19 |
| Return maximum coins | Return statement - Line 23 |

## Final Java Code & Learning Pattern

### Top-Down / Memoized Version

```java
class Solution {
    public int maxCoins(int[] nums) {
        int n = nums.length;
        // Add boundary balloons with value 1
        int[] balloons = new int[n + 2];
        balloons[0] = 1;
        balloons[n + 1] = 1;
        for (int i = 0; i < n; i++) {
            balloons[i + 1] = nums[i];
        }
        
        // Memoization: memo[i][j] = max coins from bursting balloons in range [i, j]
        Integer[][] memo = new Integer[n + 2][n + 2];
        
        return maxCoins(balloons, 1, n, memo);
    }
    
    private int maxCoins(int[] balloons, int left, int right, Integer[][] memo) {
        // Base case: no balloons in range
        if (left > right) {
            return 0;
        }
        
        // Check memoization
        if (memo[left][right] != null) {
            return memo[left][right];
        }
        
        int max = 0;
        // Try each balloon in range as the last one to burst
        for (int k = left; k <= right; k++) {
            // If k is burst last, coins = balloons[left-1] * balloons[k] * balloons[right+1]
            // because left-1 and right+1 are the boundaries that remain
            int coins = balloons[left - 1] * balloons[k] * balloons[right + 1];
            // Add coins from left and right subproblems
            coins += maxCoins(balloons, left, k - 1, memo);
            coins += maxCoins(balloons, k + 1, right, memo);
            max = Math.max(max, coins);
        }
        
        memo[left][right] = max;
        return max;
    }
}
```

### Bottom-Up Version

```java
class Solution {
    public int maxCoins(int[] nums) {
        int n = nums.length;
        // Add boundary balloons with value 1
        int[] balloons = new int[n + 2];
        balloons[0] = 1;
        balloons[n + 1] = 1;
        for (int i = 0; i < n; i++) {
            balloons[i + 1] = nums[i];
        }
        
        // DP: dp[i][j] = max coins from bursting all balloons in range [i, j]
        int[][] dp = new int[n + 2][n + 2];
        
        // Process ranges by length (from smallest to largest)
        for (int len = 1; len <= n; len++) {
            // For each possible starting position
            for (int i = 1; i <= n - len + 1; i++) {
                int j = i + len - 1;
                
                // Try each balloon k in range [i, j] as the last one to burst
                for (int k = i; k <= j; k++) {
                    // Coins from bursting k last = boundaries * k
                    int coins = balloons[i - 1] * balloons[k] * balloons[j + 1];
                    // Add coins from left and right subproblems
                    coins += dp[i][k - 1];
                    coins += dp[k + 1][j];
                    // Update maximum
                    dp[i][j] = Math.max(dp[i][j], coins);
                }
            }
        }
        
        return dp[1][n];
    }
}
```

**Explanation of Key Code Sections:**

1. **Add Boundary Balloons (Lines 6-9):** We add balloons with value 1 at both ends. This simplifies the boundary handling - when we burst a balloon at the edge, we multiply by 1.

2. **DP Array Initialization (Line 11):** `dp[i][j]` represents the maximum coins we can get from bursting all balloons in the range `[i, j]` (1-indexed after adding boundaries).

3. **Process by Length (Line 13):** We process ranges from smallest to largest. This ensures that when we calculate `dp[i][j]`, the subproblems `dp[i][k-1]` and `dp[k+1][j]` are already computed.

4. **Try Each Balloon as Last (Lines 15-22):** For each range `[i, j]`, we try each balloon `k` as the last one to burst:
   - **Coin Calculation (Line 18):** When `k` is burst last, the boundaries are `balloons[i-1]` and `balloons[j+1]`, so coins = `balloons[i-1] * balloons[k] * balloons[j+1]`.
   - **Combine Subproblems (Lines 19-20):** Add the maximum coins from the left range `[i, k-1]` and right range `[k+1, j]`.
   - **Update Maximum (Line 21):** Take the maximum over all choices of `k`.

5. **Return Result (Line 23):** `dp[1][n]` contains the maximum coins for the entire range (excluding boundaries).

**Intuition behind generating subproblems:**
- **Subproblem:** "What is the maximum coins from bursting all balloons in range `[i, j]`?"
- **Why this works:** Instead of thinking which balloon to burst first (which creates dependent subproblems), we think which balloon to burst last. When we fix the last balloon `k`:
  - Left subproblem `[i, k-1]` is independent
  - Right subproblem `[k+1, j]` is independent
  - The coins from bursting `k` is `boundaries * k` because `i-1` and `j+1` are the only balloons remaining
- **Overlapping subproblems:** Multiple ranges share the same subranges, so memoization helps.

**Why think "last" instead of "first":**
- If we think "first": When we burst balloon `k` first, the adjacent balloons change, making subproblems dependent.
- If we think "last": When we burst `k` last, the boundaries `i-1` and `j+1` remain constant, making subproblems independent.

**Top-down vs Bottom-up comparison:**
- **Top-down (memoized):** More intuitive, recursive. Time: O(n³), Space: O(n²) for memo + recursion stack.
- **Bottom-up (iterative):** More efficient, no recursion. Time: O(n³), Space: O(n²).
- **When bottom-up is better:** Better for this problem due to no recursion overhead and better cache locality. The triple nested loop is straightforward.

## Complexity Analysis

- **Time Complexity:** $O(n^3)$ where $n$ is the number of balloons. We have three nested loops: length (n), start position (n), and middle position (n).

- **Space Complexity:** $O(n^2)$ for the DP array. Top-down also uses $O(n^2)$ for memoization plus recursion stack.

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

