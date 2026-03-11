# Unique Binary Search Trees

## Problem Description

**Problem Link:** [Unique Binary Search Trees](https://leetcode.com/problems/unique-binary-search-trees/)

Given integer `n`, return number of structurally unique BSTs storing values `1..n`.

## Intuition/Main Idea

Catalan DP counting.

Pick each value as root. Then:
- left subtree uses smaller values
- right subtree uses larger values
- independent combinations multiply

### Subproblem definition
`count(nodes)` = number of unique BSTs that can be built using `nodes` values.

### State transition
For each root position `r` in `[1..nodes]`:
- left size = `r - 1`
- right size = `nodes - r`

`count(nodes) += count(leftSize) * count(rightSize)`

Base:
- `count(0) = 1` (empty tree)
- `count(1) = 1`

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|-------------------------|-------------------------------------|
| Count unique BST structures | DP over number of nodes |
| Root splits tree into left/right sizes | loop with `leftSize` and `rightSize` |
| Multiply independent subtree choices | `leftWays * rightWays` |

## Final Java Code & Learning Pattern

### Top-Down / Memoized Version

```java
class Solution {
    public int numTrees(int n) {
        // Size n+1 because states are node counts 0..n.
        Integer[] memo = new Integer[n + 1];
        return dfs(n, memo);
    }

    private int dfs(int nodes, Integer[] memo) {
        if (nodes <= 1) {
            return 1;
        }
        if (memo[nodes] != null) {
            return memo[nodes];
        }

        int ways = 0;
        for (int root = 1; root <= nodes; root++) {
            int leftWays = dfs(root - 1, memo);
            int rightWays = dfs(nodes - root, memo);
            ways += leftWays * rightWays;
        }

        memo[nodes] = ways;
        return ways;
    }
}
```

### Bottom-Up Version

Bottom-up makes Catalan buildup explicit from smaller node counts.

```java
class Solution {
    public int numTrees(int n) {
        // Size n+1 because dp[k] stores answer for k nodes.
        int[] dp = new int[n + 1];
        dp[0] = 1;
        dp[1] = 1;

        for (int nodes = 2; nodes <= n; nodes++) {
            int ways = 0;
            for (int root = 1; root <= nodes; root++) {
                int leftSize = root - 1;
                int rightSize = nodes - root;
                ways += dp[leftSize] * dp[rightSize];
            }
            dp[nodes] = ways;
        }

        return dp[n];
    }
}
```

## Complexity Analysis

- **Time Complexity:** $O(n^2)$
- **Space Complexity:** $O(n)$

## Similar Problems

1. [95. Unique Binary Search Trees II](https://leetcode.com/problems/unique-binary-search-trees-ii/)
2. [241. Different Ways to Add Parentheses](https://leetcode.com/problems/different-ways-to-add-parentheses/)
