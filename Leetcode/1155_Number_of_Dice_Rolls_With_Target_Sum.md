# Number of Dice Rolls With Target Sum

## Problem Description

**Problem Link:** [Number of Dice Rolls With Target Sum](https://leetcode.com/problems/number-of-dice-rolls-with-target-sum/)

You have `n` dice, each with `k` faces numbered from `1` to `k`.

Given three integers `n`, `k`, and `target`, return *the number of possible ways (out of the `k^n` total ways) to roll the dice, so the sum of the face-up numbers equals `target`*.

Since the answer may be too large, return it **modulo** $10^9 + 7$.

**Example 1:**

```
Input: n = 1, k = 6, target = 3
Output: 1
Explanation: You throw one die with 6 faces.
There is only one way to get a sum of 3.
```

**Example 2:**

```
Input: n = 1, k = 2, target = 3
Output: 0
Explanation: You throw one die with 2 faces.
There is no way to get a sum of 3.
```

**Example 3:**

```
Input: n = 2, k = 6, target = 7
Output: 6
Explanation: You throw two dice, each with 6 faces.
There are 6 ways to get a sum of 7: (1,6), (2,5), (3,4), (4,3), (5,2), (6,1).
```

**Constraints:**
- `1 <= n, k <= 30`
- `1 <= target <= 1000`

## Intuition/Main Idea

This is a **dynamic programming** problem. We need to count ways to get target sum using `n` dice.

**Core Algorithm:**
1. Use DP where `dp[dice][sum]` = number of ways to get `sum` using `dice` dice.
2. Base case: `dp[0][0] = 1` (1 way to get sum 0 with 0 dice).
3. For each die and sum: `dp[dice][sum] = sum(dp[dice-1][sum-face])` for all faces `1..k`.
4. Return `dp[n][target]`.

**Why DP works:** The problem has overlapping subproblems - counting ways to get sums with given dice is needed multiple times. We can build the solution bottom-up.

## Code Mapping

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| DP for ways | DP array - Line 6 |
| Base case | Base case - Line 7 |
| Process each die | Dice loop - Line 9 |
| Process each sum | Sum loop - Line 10 |
| Try each face | Face loop - Line 12 |
| Update ways | DP update - Line 13 |
| Return result | Return statement - Line 16 |

## Final Java Code & Learning Pattern

### Top-Down / Memoized Version

```java
class Solution {
    private static final int MOD = 1000000007;
    
    public int numRollsToTarget(int n, int k, int target) {
        Integer[][] memo = new Integer[n + 1][target + 1];
        return ways(n, k, target, memo);
    }
    
    private int ways(int dice, int faces, int target, Integer[][] memo) {
        // Base case: no dice left
        if (dice == 0) {
            return target == 0 ? 1 : 0;
        }
        
        // Base case: impossible
        if (target < 0 || target > dice * faces) {
            return 0;
        }
        
        if (memo[dice][target] != null) {
            return memo[dice][target];
        }
        
        long count = 0;
        for (int face = 1; face <= faces; face++) {
            count = (count + ways(dice - 1, faces, target - face, memo)) % MOD;
        }
        
        memo[dice][target] = (int) count;
        return (int) count;
    }
}
```

### Bottom-Up Version

```java
class Solution {
    private static final int MOD = 1000000007;
    
    public int numRollsToTarget(int n, int k, int target) {
        // DP: dp[dice][sum] = number of ways to get sum using dice dice
        int[][] dp = new int[n + 1][target + 1];
        dp[0][0] = 1; // Base case: 1 way to get sum 0 with 0 dice
        
        for (int dice = 1; dice <= n; dice++) {
            for (int sum = 1; sum <= target; sum++) {
                for (int face = 1; face <= k && face <= sum; face++) {
                    dp[dice][sum] = (dp[dice][sum] + dp[dice - 1][sum - face]) % MOD;
                }
            }
        }
        
        return dp[n][target];
    }
}
```

**Space-Optimized Version:**

```java
class Solution {
    private static final int MOD = 1000000007;
    
    public int numRollsToTarget(int n, int k, int target) {
        int[] dp = new int[target + 1];
        dp[0] = 1;
        
        for (int dice = 1; dice <= n; dice++) {
            int[] next = new int[target + 1];
            for (int sum = 1; sum <= target; sum++) {
                for (int face = 1; face <= k && face <= sum; face++) {
                    next[sum] = (next[sum] + dp[sum - face]) % MOD;
                }
            }
            dp = next;
        }
        
        return dp[target];
    }
}
```

**Explanation of Key Code Sections:**

1. **Base Case (Line 7):** `dp[0][0] = 1` - 1 way to get sum 0 with 0 dice.

2. **Process Dice (Lines 9-14):** For each die and sum:
   - **Try Faces (Line 12):** For each face value `1..k`:
   - **Update Ways (Line 13):** Add ways from previous die with `sum - face`.

3. **Return Result (Line 16):** Return `dp[n][target]`.

**Intuition behind generating subproblems:**
- **Subproblem:** "How many ways to get sum `s` using `d` dice?"
- **Why this works:** To get sum `s` with `d` dice, we roll one die (with value `face`), then get sum `s-face` with `d-1` dice.
- **Overlapping subproblems:** Multiple dice and sums may share the same optimal subproblems.

**Example walkthrough for `n=2, k=6, target=7`:**
- dp[0][0]=1
- dice=1: dp[1][1..6]=1 each
- dice=2: dp[2][7] = dp[1][1]+dp[1][2]+...+dp[1][6] = 6 ✓

## Complexity Analysis

- **Time Complexity:** $O(n \times target \times k)$ where we process each die, sum, and face.

- **Space Complexity:** $O(n \times target)$ for DP. Can be optimized to $O(target)$.

## Similar Problems

Problems that can be solved using similar DP patterns:

1. **1155. Number of Dice Rolls With Target Sum** (this problem) - DP counting
2. **518. Coin Change 2** - Count ways DP
3. **377. Combination Sum IV** - Count permutations
4. **322. Coin Change** - Minimum coins DP
5. **279. Perfect Squares** - Unbounded knapsack
6. **70. Climbing Stairs** - Similar DP pattern
7. **746. Min Cost Climbing Stairs** - DP with costs
8. **198. House Robber** - Similar DP pattern

