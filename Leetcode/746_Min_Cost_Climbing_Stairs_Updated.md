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

This is a classic **dynamic programming** problem. The key insight is that from any step, we can either climb one or two steps forward. We want to find the minimum cost path to reach the top.

**Core Algorithm:**
1. For each step `i`, we need to decide whether to take one step or two steps from there.
2. The minimum cost to reach the top from step `i` is: `cost[i] + min(cost to reach top from i+1, cost to reach top from i+2)`
3. We can start from index 0 or 1, so we need to find the minimum of these two starting positions.

**Why DP works:** This problem has overlapping subproblems - the cost to reach the top from step `i` will be calculated multiple times in different paths. We can use memoization to avoid redundant calculations.

## Code Mapping

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Track minimum cost from each step to top | Memoization array - Line 5 |
| Choose between starting at index 0 or 1 | Return statement - Line 7 |
| Calculate minimum cost from each position | Recursive function - Lines 10-24 |
| Choose between climbing one or two steps | Math.min() - Lines 20-23 |
| Base case: reaching or passing the top | Base case check - Lines 13-14 |

## Final Java Code & Learning Pattern

### Top-Down / Memoized Version (Starting from Index 0)

```java
class Solution {
    public int minCostClimbingStairs(int[] cost) {
        int n = cost.length;
        // Memoization array to store computed results
        // Size is n because we need to store results for indices 0 to n-1
        Integer[] memo = new Integer[n];
        // We can start from index 0 or 1, so return the minimum
        return Math.min(minCostToTop(cost, 0, memo), minCostToTop(cost, 1, memo));
    }
    
    private int minCostToTop(int[] cost, int index, Integer[] memo) {
        int n = cost.length;
        
        // Base cases: if we've reached or passed the top
        if (index >= n) {
            return 0;
        }
        
        // Check if already computed
        if (memo[index] != null) {
            return memo[index];
        }
        
        // Recurrence: cost at current step + min cost of taking 1 or 2 steps forward
        memo[index] = cost[index] + Math.min(
            minCostToTop(cost, index + 1, memo),
            minCostToTop(cost, index + 2, memo)
        );
        
        return memo[index];
    }
}
```

### Bottom-Up Version

```java
class Solution {
    public int minCostClimbingStairs(int[] cost) {
        int n = cost.length;
        // DP array: dp[i] represents minimum cost to reach the top from step i
        // Size is n+1 to include the top position (n) as well
        int[] dp = new int[n + 1];
        
        // Base case: cost to reach top from the top is 0
        dp[n] = 0;
        
        // Fill DP array from right to left (top to bottom)
        for (int i = n - 1; i >= 0; i--) {
            // To reach top from step i, we can go to i+1 or i+2
            // Choose the path with minimum total cost
            dp[i] = cost[i] + Math.min(dp[i + 1], (i + 2 <= n) ? dp[i + 2] : 0);
        }
        
        // Return minimum cost starting from step 0 or step 1
        return Math.min(dp[0], dp[1]);
    }
}
```

### Space-Optimized Bottom-Up Version

```java
class Solution {
    public int minCostClimbingStairs(int[] cost) {
        int n = cost.length;
        // We only need the next two values, so use variables instead of array
        int next1 = 0;  // Cost to reach top from position n (the top)
        int next2 = 0;  // Initially doesn't matter, will be updated
        
        // Calculate minimum cost for each step from right to left
        for (int i = n - 1; i >= 0; i--) {
            // Current cost is cost[i] + minimum of next two steps
            int current = cost[i] + Math.min(next1, next2);
            // Update for next iteration
            next2 = next1;
            next1 = current;
        }
        
        // Return minimum of starting from step 0 or step 1
        // next1 is now the cost starting from step 0
        // next2 is now the cost starting from step 1
        return Math.min(next1, next2);
    }
}
```

**Explanation of Key Code Sections:**

1. **Memoization Array (Line 5):** 
   - We create an array to store the minimum cost to reach the top from each step.
   - Size is n because we only need to store results for valid step indices (0 to n-1).

2. **Base Case (Lines 13-14):** 
   - If we've reached or passed the top (index >= n), the cost is 0 since we're already there.
   - This is different from the bottom-up approach where we work backwards.

3. **Recurrence Relation (Lines 20-23):** 
   - For each step, we have two choices: climb 1 step or climb 2 steps.
   - The minimum cost is the cost of the current step plus the minimum cost of the next possible positions.
   - `cost[index] + min(minCostToTop(index+1), minCostToTop(index+2))`

4. **Starting Position (Line 7):** 
   - We can start from either step 0 or step 1, so we calculate both and return the minimum.

**Intuition behind generating subproblems:**
- **Subproblem:** "What is the minimum cost to reach the top from step `i`?"
- **Why this works:** From step `i`, we can either go to step `i+1` or `i+2`. The optimal solution from `i` uses the optimal solution from `i+1` or `i+2`.
- **Overlapping subproblems:** Multiple paths may require calculating the cost from the same step, so we memoize results.

**Top-down vs Bottom-up comparison:**
- **Top-down (memoized):** More intuitive for this forward-looking approach. Time complexity: O(n), Space: O(n) for recursion stack + memo.
- **Bottom-up (iterative):** More efficient, no recursion overhead. Time complexity: O(n), Space: O(n) for DP array, or O(1) if optimized.
- **When bottom-up is better:** For this problem, the bottom-up approach is slightly more efficient due to no recursion overhead and better cache locality. The space-optimized version reduces space complexity to O(1).

## Complexity Analysis

- **Time Complexity:** $O(n)$ where $n$ is the length of the cost array. We process each step exactly once.

- **Space Complexity:** 
  - Top-down memoized: $O(n)$ for memo array and recursion stack
  - Bottom-up with array: $O(n)$ for the DP array
  - Bottom-up optimized: $O(1)$ using only two variables

## Similar Problems

Problems that can be solved using similar dynamic programming patterns:

1. **746. Min Cost Climbing Stairs** (this problem) - DP with forward steps
2. **70. Climbing Stairs** - Similar structure without cost
3. **198. House Robber** - DP with skip pattern
4. **213. House Robber II** - Circular variant
5. **337. House Robber III** - Tree variant
6. **509. Fibonacci Number** - Classic DP with two previous states
7. **1137. N-th Tribonacci Number** - DP with three previous states
8. **322. Coin Change** - DP with multiple choices
9. **518. Coin Change 2** - DP with combination counting
10. **279. Perfect Squares** - DP with multiple previous states
