# Minimum Cost For Tickets

## Problem Description

**Problem Link:** [Minimum Cost For Tickets](https://leetcode.com/problems/minimum-cost-for-tickets/)

You have planned some train traveling one year in advance. The days of the year in which you will travel are given as an integer array `days`. Each day is an integer from `1` to `365`.

Train tickets are sold in **three different ways**:

- a **1-day** pass is sold for `costs[0]` dollars,
- a **7-day** pass is sold for `costs[1]` dollars, and
- a **30-day** pass is sold for `costs[2]` dollars.

The passes allow that many days of consecutive travel.

For example, if we get a **7-day** pass on day `2`, then we can travel for `7` days: `2`, `3`, `4`, `5`, `6`, `7`, and `8`.

Return *the minimum number of dollars you need to travel every day in the given list of `days`*.

**Example 1:**
```
Input: days = [1,4,6,7,8,20], costs = [2,7,15]
Output: 11
Explanation: For example, here is one way to buy tickets that gives you 11:
On day 1, you bought a 1-day pass which costs costs[0] = $2 and covered day 1.
On day 4, you bought a 7-day pass which costs costs[1] = $7 and covered days 4, 5, 6, 7, and 8.
On day 20, you bought a 1-day pass which costs costs[0] = $2 and covered day 20.
In total you spent $11 and covered all the days of your travel.
```

**Example 2:**
```
Input: days = [1,2,3,4,5,6,7,8,9,10,30,31], costs = [2,7,15]
Output: 17
Explanation: For example, here is one way to buy tickets that gives you 17:
On day 1, you bought a 30-day pass which costs costs[2] = $15 and covered days 1 through 30.
On day 31, you bought a 1-day pass which costs costs[0] = $2 and covered day 31.
In total you spent $17 and covered all the days of your travel.
```

**Constraints:**
- `1 <= days.length <= 365`
- `1 <= days[i] <= 365`
- `days` is in strictly increasing order.
- `costs.length == 3`
- `1 <= costs[0] < costs[1] < costs[2] <= 1000`

## Intuition/Main Idea

This is a **dynamic programming** problem. For each travel day, we decide which ticket to buy (1-day, 7-day, or 30-day) to minimize cost.

**Core Algorithm:**
1. Use DP where `dp[i]` = minimum cost to cover all travel days up to day `i`.
2. For each travel day, try buying each type of ticket.
3. If we buy a ticket on day `i`, it covers days `[i, i+duration-1]`.
4. Find the minimum cost among all options.

**Why DP works:** The problem has overlapping subproblems - covering days up to a certain point might be needed multiple times. We can build the solution bottom-up.

## Code Mapping

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Track travel days | Set for O(1) lookup - Lines 7-9 |
| DP for minimum cost | DP array - Line 11 |
| Process each day | For loop - Line 13 |
| Skip non-travel days | If check - Line 14 |
| Try each ticket type | For loop - Line 17 |
| Calculate cost | Cost calculation - Lines 18-22 |
| Update minimum | DP update - Line 23 |
| Return result | Return statement - Line 25 |

## Final Java Code & Learning Pattern

### Top-Down / Memoized Version

```java
import java.util.*;

class Solution {
    public int mincostTickets(int[] days, int[] costs) {
        Set<Integer> travelDays = new HashSet<>();
        for (int day : days) {
            travelDays.add(day);
        }
        
        int maxDay = days[days.length - 1];
        Integer[] memo = new Integer[maxDay + 1];
        return minCost(maxDay, travelDays, costs, memo);
    }
    
    private int minCost(int day, Set<Integer> travelDays, int[] costs, Integer[] memo) {
        if (day < 1) {
            return 0;
        }
        
        if (memo[day] != null) {
            return memo[day];
        }
        
        if (!travelDays.contains(day)) {
            // Not a travel day, cost same as previous day
            memo[day] = minCost(day - 1, travelDays, costs, memo);
            return memo[day];
        }
        
        // Try each ticket type
        int min = Integer.MAX_VALUE;
        
        // 1-day pass
        min = Math.min(min, costs[0] + minCost(day - 1, travelDays, costs, memo));
        
        // 7-day pass
        min = Math.min(min, costs[1] + minCost(day - 7, travelDays, costs, memo));
        
        // 30-day pass
        min = Math.min(min, costs[2] + minCost(day - 30, travelDays, costs, memo));
        
        memo[day] = min;
        return min;
    }
}
```

### Bottom-Up Version

```java
import java.util.*;

class Solution {
    public int mincostTickets(int[] days, int[] costs) {
        // Track which days are travel days
        Set<Integer> travelDays = new HashSet<>();
        for (int day : days) {
            travelDays.add(day);
        }
        
        int maxDay = days[days.length - 1];
        // DP: dp[i] = minimum cost to cover all travel days up to day i
        int[] dp = new int[maxDay + 1];
        
        for (int i = 1; i <= maxDay; i++) {
            if (!travelDays.contains(i)) {
                // Not a travel day, cost same as previous day
                dp[i] = dp[i - 1];
            } else {
                // Try each ticket type
                int min = Integer.MAX_VALUE;
                
                // 1-day pass: covers day i
                min = Math.min(min, costs[0] + dp[i - 1]);
                
                // 7-day pass: covers days i-6 to i
                min = Math.min(min, costs[1] + dp[Math.max(0, i - 7)]);
                
                // 30-day pass: covers days i-29 to i
                min = Math.min(min, costs[2] + dp[Math.max(0, i - 30)]);
                
                dp[i] = min;
            }
        }
        
        return dp[maxDay];
    }
}
```

**Explanation of Key Code Sections:**

1. **Track Travel Days (Lines 7-9):** Use a HashSet to quickly check if a day is a travel day.

2. **DP Array (Line 11):** `dp[i]` represents the minimum cost to cover all travel days up to day `i`.

3. **Process Each Day (Lines 13-25):** 
   - **Non-Travel Day (Lines 14-16):** If day `i` is not a travel day, cost is same as previous day.
   - **Travel Day (Lines 17-23):** Try each ticket type:
     - **1-day pass:** Cost is `costs[0] + dp[i-1]`
     - **7-day pass:** Cost is `costs[1] + dp[i-7]` (covers 7 days ending at `i`)
     - **30-day pass:** Cost is `costs[2] + dp[i-30]` (covers 30 days ending at `i`)

4. **Return Result (Line 25):** `dp[maxDay]` contains the minimum cost.

**Intuition behind generating subproblems:**
- **Subproblem:** "What is the minimum cost to cover all travel days up to day `i`?"
- **Why this works:** To cover travel days up to day `i`, we can buy a ticket that covers day `i` and optimally cover the days before the ticket's coverage starts.
- **Overlapping subproblems:** Multiple days may share the same optimal subproblems.

**Example walkthrough for `days = [1,4,6,7,8,20], costs = [2,7,15]`:**
- dp[0] = 0
- dp[1] = min(2+dp[0], 7+dp[0], 15+dp[0]) = min(2,7,15) = 2
- dp[2] = dp[1] = 2 (not travel day)
- dp[3] = dp[2] = 2
- dp[4] = min(2+dp[3], 7+dp[0], 15+dp[0]) = min(4,7,15) = 4
- Continue...
- dp[20] = min(2+dp[19], 7+dp[13], 15+dp[0]) = 11 ✓

## Complexity Analysis

- **Time Complexity:** $O(maxDay)$ where `maxDay` is the last travel day. We process each day once.

- **Space Complexity:** $O(maxDay)$ for the DP array and travel days set.

## Similar Problems

Problems that can be solved using similar DP patterns:

1. **983. Minimum Cost For Tickets** (this problem) - DP with multiple choices
2. **322. Coin Change** - Unbounded knapsack
3. **518. Coin Change 2** - Count ways
4. **279. Perfect Squares** - Unbounded with squares
5. **377. Combination Sum IV** - Count permutations
6. **70. Climbing Stairs** - Similar DP pattern
7. **746. Min Cost Climbing Stairs** - DP with costs
8. **198. House Robber** - Similar decision pattern

