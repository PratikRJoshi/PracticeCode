# Predict the Winner

## Problem Description

**Problem Link:** [Predict the Winner](https://leetcode.com/problems/predict-the-winner/)

You are given an integer array `nums`. Two players are playing a game with this array: player 1 and player 2.

Player 1 and player 2 take turns, with **player 1 starting first**. Both players start the game with a score of `0`. At each turn, the player takes one of the numbers from either end of the array (i.e., `nums[0]` or `nums[nums.length - 1]`), removes it from the array, and adds it to their score. The game continues until all numbers have been chosen.

Return `true` *if Player 1 can win the game*. If both players play optimally, return `false` otherwise.

**Example 1:**

```
Input: nums = [1,5,2]
Output: false
Explanation: Initially, player 1 can choose between 1 and 2. 
If he chooses 2 (or 1), then player 2 can choose from 1 (or 2) and 5. If player 2 chooses 5, then player 1 will be left with 1 (or 2). 
So, final score of player 1 is 1 + 2 = 3, and player 2 is 5. 
Hence, player 1 will never be the winner and you need to return false.
```

**Example 2:**

```
Input: nums = [1,5,233,7]
Output: true
Explanation: Player 1 first chooses 1. Then player 2 has to choose between 5 and 7. No matter which number player 2 choose, player 1 can choose 233.
Finally, player 1 has more score (234) than player 2 (12), so you need to return True representing player1 can win.
```

**Constraints:**
- `1 <= nums.length <= 20`
- `0 <= nums[i] <= 10^7`

## Intuition/Main Idea

This is a **minimax DP** problem. We need to determine if player 1 can win when both players play optimally.

**Core Algorithm:**
1. Use DP where `dp[i][j]` = maximum score difference player 1 can achieve over player 2 for subarray `nums[i..j]`.
2. If `i == j`, return `nums[i]`.
3. Player 1 can choose `nums[i]` or `nums[j]`:
   - Choose `nums[i]`: difference = `nums[i] - dp[i+1][j]`
   - Choose `nums[j]`: difference = `nums[j] - dp[i][j-1]`
4. Take maximum: `dp[i][j] = max(nums[i] - dp[i+1][j], nums[j] - dp[i][j-1])`.
5. Player 1 wins if `dp[0][n-1] >= 0`.

**Why minimax works:** Both players play optimally, so player 1 maximizes the difference while player 2 minimizes it (by maximizing their own score, which minimizes player 1's advantage).

## Code Mapping

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| DP for score difference | DP array - Line 6 |
| Base case: single element | Base case - Line 9 |
| Try both ends | Choice calculation - Lines 12-13 |
| Maximize difference | Max calculation - Line 14 |
| Return result | Return statement - Line 17 |

## Final Java Code & Learning Pattern

### Top-Down / Memoized Version

```java
class Solution {
    public boolean PredictTheWinner(int[] nums) {
        int n = nums.length;
        Integer[][] memo = new Integer[n][n];
        return maxDiff(nums, 0, n - 1, memo) >= 0;
    }
    
    private int maxDiff(int[] nums, int i, int j, Integer[][] memo) {
        if (i == j) {
            return nums[i];
        }
        
        if (memo[i][j] != null) {
            return memo[i][j];
        }
        
        // Player 1 chooses nums[i] or nums[j], player 2 gets the rest optimally
        int chooseLeft = nums[i] - maxDiff(nums, i + 1, j, memo);
        int chooseRight = nums[j] - maxDiff(nums, i, j - 1, memo);
        
        memo[i][j] = Math.max(chooseLeft, chooseRight);
        return memo[i][j];
    }
}
```

### Bottom-Up Version

```java
class Solution {
    public boolean PredictTheWinner(int[] nums) {
        int n = nums.length;
        
        // DP: dp[i][j] = maximum score difference player 1 can achieve over player 2
        int[][] dp = new int[n][n];
        
        // Base case: single element
        for (int i = 0; i < n; i++) {
            dp[i][i] = nums[i];
        }
        
        // Process intervals by length
        for (int len = 2; len <= n; len++) {
            for (int i = 0; i <= n - len; i++) {
                int j = i + len - 1;
                // Player 1 chooses nums[i] or nums[j]
                dp[i][j] = Math.max(
                    nums[i] - dp[i + 1][j],
                    nums[j] - dp[i][j - 1]
                );
            }
        }
        
        return dp[0][n - 1] >= 0;
    }
}
```

**Explanation of Key Code Sections:**

1. **Base Case (Line 9):** If `i == j`, only one element, player 1 gets it: `dp[i][i] = nums[i]`.

2. **DP Transition (Lines 12-14):** For subarray `[i..j]`:
   - **Choose Left (Line 12):** Player 1 takes `nums[i]`, player 2 gets optimal difference for `[i+1..j]`, so difference = `nums[i] - dp[i+1][j]`.
   - **Choose Right (Line 13):** Player 1 takes `nums[j]`, player 2 gets optimal difference for `[i..j-1]`, so difference = `nums[j] - dp[i][j-1]`.
   - **Take Maximum (Line 14):** Player 1 chooses the option that maximizes their advantage.

**Intuition behind generating subproblems:**
- **Subproblem:** "What is the maximum score difference player 1 can achieve over player 2 for subarray `[i..j]`?"
- **Why this works:** Both players play optimally. When player 1 chooses an element, player 2 will choose optimally from the remaining, minimizing player 1's advantage. The difference formula accounts for this.
- **Overlapping subproblems:** Multiple subarrays may share the same optimal differences.

**Top-down vs Bottom-up comparison:**
- **Top-down (memoized):** More intuitive, recursive. Time: O(n²), Space: O(n²).
- **Bottom-up (iterative):** More efficient, no recursion. Time: O(n²), Space: O(n²).
- **When bottom-up is better:** Better for this problem due to no recursion overhead.

## Complexity Analysis

- **Time Complexity:** $O(n^2)$ where $n$ is the array length. We process each subarray once.

- **Space Complexity:** $O(n^2)$ for the DP array.

## Similar Problems

Problems that can be solved using similar minimax DP patterns:

1. **486. Predict the Winner** (this problem) - Minimax DP
2. **877. Stone Game** - Similar minimax DP
3. **1140. Stone Game II** - Minimax with constraints
4. **1406. Stone Game III** - Minimax variant
5. **1510. Stone Game IV** - Minimax with squares
6. **1563. Stone Game V** - Minimax with splitting
7. **1690. Stone Game VII** - Minimax variant
8. **1872. Stone Game VIII** - Minimax variant

