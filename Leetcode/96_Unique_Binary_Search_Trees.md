# 96. Unique Binary Search Trees

[LeetCode Link](https://leetcode.com/problems/unique-binary-search-trees/)

## Problem Description
Given an integer `n`, return the number of structurally unique **BSTs (binary search trees)** that store values `1` to `n`.

### Examples

#### Example 1
- Input: `n = 3`
- Output: `5`

#### Example 2
- Input: `n = 1`
- Output: `1`

---

## Intuition/Main Idea
A BST built from values `1..n` is fully determined by which value we choose as the root.

If we choose value `rootValue` as the root:

- The left subtree must use values `1..rootValue-1` (there are `rootValue-1` values)
- The right subtree must use values `rootValue+1..n` (there are `n-rootValue` values)

Key multiplication idea:

- If there are `countLeft` ways to form the left subtree and `countRight` ways to form the right subtree,
  then combining them yields `countLeft * countRight` unique BSTs for this root.

So the total number of BSTs is the sum over all possible roots.

This is the classic **Catalan number** DP.

---

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|---|---|
| Count number of structurally unique BSTs for values `1..n` | DP state `countTrees(size)` or `dp[size]` |
| Choosing a root splits into left/right independent subproblems | Loop over `rootValue` / `leftSize` and use multiplication `leftWays * rightWays` |
| Efficient solution for up to `n = 19` | Memoization / bottom-up DP avoids exponential recursion |

---

## Final Java Code & Learning Pattern (Full Content)

### Top-Down (Memoized) DP

**Subproblem definition:**

- `countTrees(size)` = number of unique BSTs that can be formed using `size` distinct keys.

**State transition (simple words):**

- Pick a root position.
- The number of nodes on the left is `leftSize`.
- The number of nodes on the right is `rightSize = size - 1 - leftSize`.
- Total ways contributed by this root = `countTrees(leftSize) * countTrees(rightSize)`.

```java
import java.util.*;

class Solution {
    public int numTrees(int n) {
        // memo[size] stores countTrees(size).
        // Size is n+1 so we can directly index sizes 0..n.
        Integer[] memo = new Integer[n + 1];

        return countTrees(n, memo);
    }

    private int countTrees(int size, Integer[] memo) {
        if (size == 0 || size == 1) {
            return 1;
        }

        if (memo[size] != null) {
            return memo[size];
        }

        int totalWays = 0;

        // Choose how many nodes go to the left subtree.
        // leftSize ranges from 0 to size-1.
        for (int leftSize = 0; leftSize <= size - 1; leftSize++) {
            int rightSize = size - 1 - leftSize;

            int leftWays = countTrees(leftSize, memo);
            int rightWays = countTrees(rightSize, memo);

            totalWays += leftWays * rightWays;
        }

        memo[size] = totalWays;
        return totalWays;
    }
}
```

### Bottom-Up DP

Bottom-up uses the same subproblem definition (`dp[size]`) but iteratively fills from smaller sizes to larger sizes.

```java
import java.util.*;

class Solution {
    public int numTrees(int n) {
        // dp[size] = number of unique BSTs using 'size' nodes.
        // Size is n+1 so dp[0]..dp[n] are valid.
        int[] dp = new int[n + 1];

        dp[0] = 1;
        dp[1] = 1;

        for (int size = 2; size <= n; size++) {
            int totalWays = 0;

            for (int leftSize = 0; leftSize <= size - 1; leftSize++) {
                int rightSize = size - 1 - leftSize;
                totalWays += dp[leftSize] * dp[rightSize];
            }

            dp[size] = totalWays;
        }

        return dp[n];
    }
}
```

### Learning Pattern
- This is “choose a root, split into independent left/right subproblems”.
- Count problems on trees often turn into:
  - `answer = sum over splits (leftWays * rightWays)`
- When the actual values don’t matter (only counts), define DP by **size**.

---

## Complexity Analysis
- Time Complexity: $O(n^2)$
  - For each `size` from 1..n, we iterate over all splits.
- Space Complexity: $O(n)$
  - 1D memo / dp array.

---

## Dynamic Programming Problems

### Subproblem Definition
- `countTrees(size)` / `dp[size]` = number of unique BSTs possible with `size` nodes.

### State Transition (simple words)
- Choose root.
- Left subtree gets `leftSize` nodes, right subtree gets `rightSize` nodes.
- Ways for this split = `dp[leftSize] * dp[rightSize]`.
- Sum over all `leftSize`.

### Why the Top-Down Approach is Natural
- It matches the reasoning: “how many trees can I build with `size` nodes?”
- Memoization prevents recomputing the same `size` repeatedly.

### Why Bottom-Up Can Be Better in Practice
- Avoids recursion overhead.
- Simple loops; guaranteed stack-safe.

---

## Similar Problems
- [95. Unique Binary Search Trees II](https://leetcode.com/problems/unique-binary-search-trees-ii/) (generate the actual trees)
- [94. Binary Tree Inorder Traversal](https://leetcode.com/problems/binary-tree-inorder-traversal/) (BST traversal basics)
- [241. Different Ways to Add Parentheses](https://leetcode.com/problems/different-ways-to-add-parentheses/) (split + combine pattern)
