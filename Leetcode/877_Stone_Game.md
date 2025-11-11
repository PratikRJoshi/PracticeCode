# Stone Game

## Problem Description

**Problem Link:** [Stone Game](https://leetcode.com/problems/stone-game/)

Alice and Bob play a game with piles of stones. There are an **even** number of piles arranged in a row, and each pile has a **positive** number of stones `piles[i]`.

The objective of the game is to end with the most stones. The **total** number of stones across all the piles is **odd**, so there are no ties.

Alice and Bob take turns, with **Alice starting first**. Each turn, a player takes the entire pile of stones from either the beginning or the end of the row. This continues until there are no more piles left, at which point the person with the most stones wins.

Assuming Alice and Bob play optimally, return `true` *if Alice wins the game, or `false` if Bob wins*.

**Example 1:**

```
Input: piles = [5,3,4,5]
Output: true
Explanation: 
Alice starts first, and can only take the first 5 or the last 5.
Say she takes the first 5, so piles become [3, 4, 5].
If Bob takes 3, then the board is [4, 5], and Alice takes 5 to win with 10 points.
If Bob takes the last 5, then the board is [3, 4], and Alice takes 4 to win with 9 points.
This demonstrated that taking the first 5 was a winning move for Alice, so we return true.
```

**Example 2:**

```
Input: piles = [3,7,2,3]
Output: true
```

**Constraints:**
- `2 <= piles.length <= 500`
- `piles.length` is **even**.
- `1 <= piles[i] <= 500`
- `sum(piles)` is **odd**.

## Intuition/Main Idea

This is a **minimax DP** problem, but there's a mathematical insight: **Alice always wins** when the number of piles is even and both players play optimally.

**Why Alice always wins:**
- With even number of piles, Alice can always choose to take from positions with indices of the same parity (all even or all odd).
- Since the sum is odd, one parity group has more stones than the other.
- Alice can always choose the better parity group.

**DP Approach:**
1. Use DP where `dp[i][j]` = maximum score difference Alice can achieve over Bob for subarray `piles[i..j]`.
2. Similar to Predict the Winner problem.

## Code Mapping

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Mathematical insight | Return statement - Line 3 |
| DP for score difference | DP array - Line 7 |
| Base case: single element | Base case - Line 10 |
| Try both ends | Choice calculation - Lines 13-14 |
| Maximize difference | Max calculation - Line 15 |
| Return result | Return statement - Line 18 |

## Final Java Code & Learning Pattern

### Mathematical Solution (Optimal)

```java
class Solution {
    public boolean stoneGame(int[] piles) {
        // Alice always wins with even number of piles
        return true;
    }
}
```

### DP Solution

```java
class Solution {
    public boolean stoneGame(int[] piles) {
        int n = piles.length;
        Integer[][] memo = new Integer[n][n];
        return maxDiff(piles, 0, n - 1, memo) > 0;
    }
    
    private int maxDiff(int[] piles, int i, int j, Integer[][] memo) {
        if (i == j) {
            return piles[i];
        }
        
        if (memo[i][j] != null) {
            return memo[i][j];
        }
        
        // Alice chooses piles[i] or piles[j], Bob gets the rest optimally
        int chooseLeft = piles[i] - maxDiff(piles, i + 1, j, memo);
        int chooseRight = piles[j] - maxDiff(piles, i, j - 1, memo);
        
        memo[i][j] = Math.max(chooseLeft, chooseRight);
        return memo[i][j];
    }
}
```

**Explanation of Key Code Sections:**

**Mathematical Solution:**
- With even number of piles, Alice can always force a win by choosing the parity group with more stones.

**DP Solution:**
- Same as Predict the Winner problem.
- Returns true if Alice's score difference is positive.

**Intuition behind generating subproblems:**
- **Subproblem:** "What is the maximum score difference Alice can achieve over Bob for subarray `[i..j]`?"
- **Why this works:** Both players play optimally. When Alice chooses a pile, Bob will choose optimally from the remaining, minimizing Alice's advantage.
- **Overlapping subproblems:** Multiple subarrays may share the same optimal differences.

## Complexity Analysis

- **Time Complexity:** 
  - Mathematical: $O(1)$.
  - DP: $O(n^2)$.

- **Space Complexity:** 
  - Mathematical: $O(1)$.
  - DP: $O(n^2)$.

## Similar Problems

Problems that can be solved using similar minimax DP patterns:

1. **877. Stone Game** (this problem) - Minimax DP (or mathematical)
2. **486. Predict the Winner** - Minimax DP
3. **1140. Stone Game II** - Minimax with constraints
4. **1406. Stone Game III** - Minimax variant
5. **1510. Stone Game IV** - Minimax with squares
6. **1563. Stone Game V** - Minimax with splitting
7. **1690. Stone Game VII** - Minimax variant
8. **1872. Stone Game VIII** - Minimax variant

