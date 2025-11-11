# Super Egg Drop

## Problem Description

**Problem Link:** [Super Egg Drop](https://leetcode.com/problems/super-egg-drop/)

You are given `k` identical eggs and you have access to a building with `n` floors labeled from `1` to `n`.

You know that there exists a floor `f` where `0 <= f <= n` such that any egg dropped at a floor higher than `f` will break, and any egg dropped at or below floor `f` will not break.

Each move, you may take an unbroken egg and drop it from any floor `x` (where `1 <= x <= n`). If the egg breaks, you can no longer use it. However, if the egg does not break, you may **reuse** it in future moves.

Return *the **minimum number of moves** that you need to determine **with certainty** what the value of `f` is*.

**Example 1:**

```
Input: k = 1, n = 2
Output: 2
Explanation: 
Drop the egg from floor 1. If it breaks, we know that f = 0.
Otherwise, drop the egg from floor 2. If it breaks, we know that f = 1.
If it does not break, then we know f = 2.
Hence, we need at minimum 2 moves to determine with certainty what the value of f is.
```

**Example 2:**

```
Input: k = 2, n = 6
Output: 3
```

**Constraints:**
- `1 <= k <= 100`
- `1 <= n <= 10^4`

## Intuition/Main Idea

This is a **dynamic programming** problem. We need to find the minimum number of moves to determine the critical floor.

**Core Algorithm:**
1. Use DP where `dp[moves][eggs]` = maximum floors we can test with `moves` moves and `eggs` eggs.
2. For each move, try dropping from each floor: `dp[moves][eggs] = 1 + dp[moves-1][eggs-1] + dp[moves-1][eggs]`.
3. Find the minimum `moves` such that `dp[moves][k] >= n`.

**Alternative approach:** Instead of `dp[moves][eggs]`, we can use `dp[eggs][floors]` = minimum moves needed, but this is less efficient.

**Why DP works:** The problem has overlapping subproblems - testing floors with given moves and eggs is needed multiple times. We can build the solution bottom-up.

## Code Mapping

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| DP for floors tested | DP array - Line 6 |
| Process moves | Moves loop - Line 8 |
| Process eggs | Eggs loop - Line 9 |
| Calculate floors | DP transition - Line 11 |
| Check if enough floors | Condition check - Line 13 |
| Return moves | Return statement - Line 15 |

## Final Java Code & Learning Pattern

### Top-Down / Memoized Version

```java
class Solution {
    public int superEggDrop(int k, int n) {
        Integer[][] memo = new Integer[k + 1][n + 1];
        return minMoves(k, n, memo);
    }
    
    private int minMoves(int eggs, int floors, Integer[][] memo) {
        if (floors == 0 || floors == 1) {
            return floors;
        }
        if (eggs == 1) {
            return floors;
        }
        
        if (memo[eggs][floors] != null) {
            return memo[eggs][floors];
        }
        
        int min = Integer.MAX_VALUE;
        for (int floor = 1; floor <= floors; floor++) {
            int breaks = minMoves(eggs - 1, floor - 1, memo);
            int notBreaks = minMoves(eggs, floors - floor, memo);
            min = Math.min(min, 1 + Math.max(breaks, notBreaks));
        }
        
        memo[eggs][floors] = min;
        return min;
    }
}
```

### Bottom-Up Version (Optimal - Floors Tested)

```java
class Solution {
    public int superEggDrop(int k, int n) {
        // DP: dp[moves][eggs] = maximum floors we can test
        int[][] dp = new int[n + 1][k + 1];
        int moves = 0;
        
        while (dp[moves][k] < n) {
            moves++;
            for (int eggs = 1; eggs <= k; eggs++) {
                // If egg breaks: test floors below (dp[moves-1][eggs-1])
                // If egg doesn't break: test floors above (dp[moves-1][eggs])
                dp[moves][eggs] = 1 + dp[moves - 1][eggs - 1] + dp[moves - 1][eggs];
            }
        }
        
        return moves;
    }
}
```

**Explanation of Key Code Sections:**

1. **DP Array (Line 6):** `dp[moves][eggs]` = maximum floors we can test with `moves` moves and `eggs` eggs.

2. **Process Moves (Lines 8-13):** For each move:
   - Try with each number of eggs.
   - **DP Transition (Line 11):** If we drop from a floor:
     - Egg breaks: we can test floors below → `dp[moves-1][eggs-1]`
     - Egg doesn't break: we can test floors above → `dp[moves-1][eggs]`
     - Total: `1 + dp[moves-1][eggs-1] + dp[moves-1][eggs]`

3. **Check Condition (Line 9):** Continue until we can test at least `n` floors.

**Intuition behind generating subproblems:**
- **Subproblem:** "What is the maximum floors we can test with `moves` moves and `eggs` eggs?"
- **Why this works:** To test floors, we drop from a floor. If it breaks, we test below; if not, we test above. The maximum floors we can test is the sum of both cases plus the current floor.
- **Overlapping subproblems:** Multiple combinations of moves and eggs may test the same number of floors.

**Top-down vs Bottom-up comparison:**
- **Top-down (memoized):** More intuitive but slower due to recursion. Time: O(k×n²), Space: O(k×n).
- **Bottom-up (iterative):** More efficient, especially with the floors-tested approach. Time: O(k×moves), Space: O(k×moves) where moves ≤ n.
- **When bottom-up is better:** Much better for this problem due to better time complexity and no recursion overhead.

## Complexity Analysis

- **Time Complexity:** 
  - Top-down: $O(k \times n^2)$.
  - Bottom-up: $O(k \times moves)$ where moves ≤ n, typically much better.

- **Space Complexity:** 
  - Top-down: $O(k \times n)$.
  - Bottom-up: $O(k \times moves)$.

## Similar Problems

Problems that can be solved using similar DP patterns:

1. **887. Super Egg Drop** (this problem) - DP with optimization
2. **312. Burst Balloons** - Interval DP
3. **1039. Minimum Score Triangulation of Polygon** - Interval DP
4. **1547. Minimum Cost to Cut a Stick** - Interval DP
5. **1000. Minimum Cost to Merge Stones** - Interval DP
6. **1884. Egg Drop With 2 Eggs and N Floors** - Simpler variant
7. **975. Odd Even Jump** - DP with optimization
8. **871. Minimum Number of Refueling Stops** - DP with optimization

