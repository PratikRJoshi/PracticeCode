# Min Cost Climbing Stairs

## Problem Description

**Problem Link:** [Min Cost Climbing Stairs](https://leetcode.com/problems/min-cost-climbing-stairs/)

You are given an integer array `cost` where `cost[i]` is the cost of `i`th step on a staircase. Once you pay the cost, you can either climb one or two steps.

You can either start from the step with index `0`, or the step with index `1`.

Return *the minimum cost to reach the top of the floor*.

**Example 1:**
```
Input: cost = [10,15,20]
Output: 15
Explanation: You will start at index 1.
- Pay 15 and climb two steps to reach the top.
The total cost is 15.
```

**Example 2:**
```
Input: cost = [1,100,1,1,1,100,1,1,100,1]
Output: 6
Explanation: You will start at index 0.
- Pay 1 and climb two steps to reach index 2.
- Pay 1 and climb two steps to reach index 4.
- Pay 1 and climb two steps to reach index 6.
- Pay 1 and climb one step to reach index 7.
- Pay 1 and climb two steps to reach index 9.
- Pay 1 and climb one step to reach the top.
The total cost is 6.
```

**Constraints:**
- `2 <= cost.length <= 1000`
- `0 <= cost[i] <= 999`

## Intuition/Main Idea

This is a classic **dynamic programming** problem. The key insight is that to reach step `i`, we can come from either step `i-1` or step `i-2`. We want to choose the path with minimum cost.

**Core Algorithm:**
1. For each step `i`, the minimum cost to reach it is: `min(cost[i-1] + cost to reach i-1, cost[i-2] + cost to reach i-2)`
2. We can start from index 0 or 1, so the base cases are: `dp[0] = cost[0]`, `dp[1] = cost[1]`
3. The top of the floor is beyond the last step, so we need to reach either the last step or second-to-last step.

**Why DP works:** This problem has overlapping subproblems - the cost to reach step `i` depends on the cost to reach previous steps. We can build the solution bottom-up by calculating the minimum cost for each step.

## Code Mapping

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Track minimum cost to reach each step | DP array - Line 5 |
| Base case: starting at index 0 | `dp[0] = cost[0]` - Line 7 |
| Base case: starting at index 1 | `dp[1] = cost[1]` - Line 8 |
| Calculate minimum cost for each step | For loop - Line 11 |
| Choose minimum between one or two steps | `Math.min()` - Line 13 |
| Return minimum cost to reach top | Return statement - Line 16 |

## Final Java Code & Learning Pattern

### Top-Down / Memoized Version

```java
class Solution {
    public int minCostClimbingStairs(int[] cost) {
        int n = cost.length;
        // Memoization array to store computed results
        Integer[] memo = new Integer[n];
        // We can start from index 0 or 1, and top is beyond last step
        // So we need min cost to reach either last or second-to-last step
        return Math.min(minCost(cost, n - 1, memo), minCost(cost, n - 2, memo));
    }
    
    private int minCost(int[] cost, int i, Integer[] memo) {
        // Base cases: starting positions have their own costs
        if (i < 0) return 0;
        if (i == 0 || i == 1) return cost[i];
        
        // Check if already computed
        if (memo[i] != null) return memo[i];
        
        // Recurrence: min cost to reach i is cost[i] + min of reaching i-1 or i-2
        memo[i] = cost[i] + Math.min(
            minCost(cost, i - 1, memo),
            minCost(cost, i - 2, memo)
        );
        
        return memo[i];
    }
}
```

### Bottom-Up Version

```java
class Solution {
    public int minCostClimbingStairs(int[] cost) {
        int n = cost.length;
        // DP array: dp[i] represents minimum cost to reach step i
        int[] dp = new int[n];
        
        // Base cases: starting positions
        dp[0] = cost[0];
        dp[1] = cost[1];
        
        // Fill DP array: for each step, choose minimum cost path
        for (int i = 2; i < n; i++) {
            // To reach step i, we can come from i-1 or i-2
            // Choose the path with minimum total cost
            dp[i] = cost[i] + Math.min(dp[i - 1], dp[i - 2]);
        }
        
        // Top is beyond last step, so we can reach from last or second-to-last step
        return Math.min(dp[n - 1], dp[n - 2]);
    }
}
```

### Space-Optimized Bottom-Up Version

```java
class Solution {
    public int minCostClimbingStairs(int[] cost) {
        int n = cost.length;
        // We only need the last two values, so use variables instead of array
        int prev2 = cost[0];  // Cost to reach step 0
        int prev1 = cost[1];  // Cost to reach step 1
        
        // Calculate minimum cost for each step
        for (int i = 2; i < n; i++) {
            // Current cost is cost[i] + minimum of previous two steps
            int current = cost[i] + Math.min(prev1, prev2);
            // Update for next iteration
            prev2 = prev1;
            prev1 = current;
        }
        
        // Return minimum of last two steps (can reach top from either)
        return Math.min(prev1, prev2);
    }
}
```

**Explanation of Key Code Sections:**

1. **DP Array Initialization (Line 5):** We create an array to store the minimum cost to reach each step. `dp[i]` represents the minimum cost to reach step `i`.

2. **Base Cases (Lines 7-8):** 
   - `dp[0] = cost[0]`: If we start at step 0, we pay `cost[0]`
   - `dp[1] = cost[1]`: If we start at step 1, we pay `cost[1]`

3. **DP Transition (Lines 11-13):** For each step `i >= 2`:
   - We can reach step `i` from either step `i-1` or step `i-2`
   - The cost is `cost[i]` plus the minimum cost to reach the previous step
   - `dp[i] = cost[i] + min(dp[i-1], dp[i-2])`

4. **Final Answer (Line 16):** The top is beyond the last step, so we can reach it from either the last step (`dp[n-1]`) or the second-to-last step (`dp[n-2]`). We return the minimum of these two.

**Intuition behind generating subproblems:**
- **Subproblem:** "What is the minimum cost to reach step `i`?"
- **Why this works:** To reach step `i`, we must have come from step `i-1` or `i-2`. The optimal solution to reach `i` uses the optimal solution to reach `i-1` or `i-2`.
- **Overlapping subproblems:** Multiple paths may lead to the same step, so we memoize results to avoid recomputation.

**Top-down vs Bottom-up comparison:**
- **Top-down (memoized):** More intuitive, recursive approach. Time complexity: O(n), Space: O(n) for recursion stack + memo.
- **Bottom-up (iterative):** More efficient, no recursion overhead. Time complexity: O(n), Space: O(n) for DP array, or O(1) if optimized.
- **When bottom-up is better:** For this problem, bottom-up is slightly better due to no recursion overhead, but the difference is minimal. Bottom-up is generally preferred for production code due to better cache locality and no stack overflow risk.

## Complexity Analysis

- **Time Complexity:** $O(n)$ where $n$ is the length of the cost array. We process each step exactly once.

- **Space Complexity:** 
  - Bottom-up with array: $O(n)$ for the DP array
  - Bottom-up optimized: $O(1)$ using only two variables
  - Top-down memoized: $O(n)$ for memo array and recursion stack

## Similar Problems

Problems that can be solved using similar dynamic programming patterns:

1. **746. Min Cost Climbing Stairs** (this problem) - DP with two previous states
2. **70. Climbing Stairs** - Similar structure without cost
3. **198. House Robber** - DP with skip pattern
4. **213. House Robber II** - Circular variant
5. **337. House Robber III** - Tree variant
6. **509. Fibonacci Number** - Classic DP with two previous states
7. **1137. N-th Tribonacci Number** - DP with three previous states
8. **322. Coin Change** - DP with multiple choices
9. **518. Coin Change 2** - DP with combination counting
10. **279. Perfect Squares** - DP with multiple previous states

