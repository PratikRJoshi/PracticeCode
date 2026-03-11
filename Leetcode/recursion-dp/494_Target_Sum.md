# Target Sum

## Problem Description

**Problem Link:** [Target Sum](https://leetcode.com/problems/target-sum/)

Given `nums` and `target`, place `+` or `-` before each number and count number of expressions evaluating to `target`.

## Intuition/Main Idea

Transform to subset sum.
Let positives subset be `P`, negatives subset be `N`:
- `P - N = target`
- `P + N = total`
=> `2P = total + target`
So count subsets with sum `(total + target)/2`.

### Subproblem definition
`ways(index, sum)` = number of ways to form `sum` using first `index` numbers.

### State transition
`ways(i, s) = ways(i-1, s) + ways(i-1, s - nums[i-1])`.

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|-------------------------|-------------------------------------|
| Assign + or - to each element | transformed to subset-count equation |
| Return number of valid expressions | DP stores counts not booleans |
| Invalid transform cases | parity and bound check before DP |

## Final Java Code & Learning Pattern

### Top-Down / Memoized Version

```java
class Solution {
    public int findTargetSumWays(int[] nums, int target) {
        int total = 0;
        for (int num : nums) {
            total += num;
        }

        if (Math.abs(target) > total || ((total + target) & 1) == 1) {
            return 0;
        }

        int subsetTarget = (total + target) / 2;
        // Size nums.length x (subsetTarget+1) for (index, remaining) states.
        Integer[][] memo = new Integer[nums.length][subsetTarget + 1];
        return dfs(nums.length - 1, subsetTarget, nums, memo);
    }

    private int dfs(int index, int remaining, int[] nums, Integer[][] memo) {
        if (remaining == 0) {
            return 1;
        }
        if (index < 0 || remaining < 0) {
            return 0;
        }
        if (memo[index][remaining] != null) {
            return memo[index][remaining];
        }

        int skip = dfs(index - 1, remaining, nums, memo);
        int take = dfs(index - 1, remaining - nums[index], nums, memo);

        memo[index][remaining] = skip + take;
        return memo[index][remaining];
    }
}
```

### Bottom-Up Version

Bottom-up is usually cleaner for counting subset combinations.

```java
class Solution {
    public int findTargetSumWays(int[] nums, int target) {
        int total = 0;
        for (int num : nums) {
            total += num;
        }

        if (Math.abs(target) > total || ((total + target) & 1) == 1) {
            return 0;
        }

        int subsetTarget = (total + target) / 2;

        // Size subsetTarget+1 because we count ways for every sum 0..subsetTarget.
        int[] dp = new int[subsetTarget + 1];
        dp[0] = 1;

        for (int num : nums) {
            for (int sum = subsetTarget; sum >= num; sum--) {
                dp[sum] += dp[sum - num];
            }
        }

        return dp[subsetTarget];
    }
}
```

## Complexity Analysis

- **Time Complexity:** $O(n \cdot subsetTarget)$
- **Space Complexity:** Top-down $O(n \cdot subsetTarget)$, bottom-up $O(subsetTarget)$

## Similar Problems

1. [416. Partition Equal Subset Sum](https://leetcode.com/problems/partition-equal-subset-sum/)
2. [518. Coin Change II](https://leetcode.com/problems/coin-change-ii/)
