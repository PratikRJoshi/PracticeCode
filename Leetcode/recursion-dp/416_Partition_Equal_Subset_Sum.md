# Partition Equal Subset Sum

## Problem Description

**Problem Link:** [Partition Equal Subset Sum](https://leetcode.com/problems/partition-equal-subset-sum/)

Given array `nums`, return `true` if it can be partitioned into two subsets with equal sum.

## Intuition/Main Idea

Total sum must be even. Then target is `sum / 2`.
Problem becomes: can we pick a subset with exact sum `target`?

### Subproblem definition
`canPick(index, remaining)` = can we form `remaining` using elements from `index` onward?

### State transition
- Skip current number: `canPick(index + 1, remaining)`
- Include current number (if possible): `canPick(index + 1, remaining - nums[index])`

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|-------------------------|-------------------------------------|
| Equal partition required | even-sum check `if (total % 2 != 0)` |
| 0/1 pick each element once | recursive/iterative transition to next index |
| Exact target subset sum | state dimension includes `remaining`/`target` |

## Final Java Code & Learning Pattern

### Top-Down / Memoized Version

```java
class Solution {
    public boolean canPartition(int[] nums) {
        int total = 0;
        for (int num : nums) {
            total += num;
        }
        if ((total & 1) == 1) {
            return false;
        }

        int target = total / 2;
        // Size: nums.length x (target+1) because states are index 0..n-1 and remaining 0..target.
        Boolean[][] memo = new Boolean[nums.length][target + 1];
        return dfs(0, target, nums, memo);
    }

    private boolean dfs(int index, int remaining, int[] nums, Boolean[][] memo) {
        if (remaining == 0) {
            return true;
        }
        if (index == nums.length || remaining < 0) {
            return false;
        }
        if (memo[index][remaining] != null) {
            return memo[index][remaining];
        }

        boolean skip = dfs(index + 1, remaining, nums, memo);
        boolean take = dfs(index + 1, remaining - nums[index], nums, memo);

        memo[index][remaining] = skip || take;
        return memo[index][remaining];
    }
}
```

### Bottom-Up Version

Bottom-up is often simpler for subset-sum and avoids recursion overhead.

```java
class Solution {
    public boolean canPartition(int[] nums) {
        int total = 0;
        for (int num : nums) {
            total += num;
        }
        if ((total & 1) == 1) {
            return false;
        }

        int target = total / 2;

        // Size target+1 because we track achievable sums from 0..target.
        boolean[] dp = new boolean[target + 1];
        dp[0] = true;

        for (int num : nums) {
            for (int sum = target; sum >= num; sum--) {
                dp[sum] = dp[sum] || dp[sum - num];
            }
        }

        return dp[target];
    }
}
```

## Complexity Analysis

- **Time Complexity:** $O(n \cdot target)$
- **Space Complexity:** Top-down $O(n \cdot target)$, bottom-up $O(target)$

## Similar Problems

1. [494. Target Sum](https://leetcode.com/problems/target-sum/)
2. [322. Coin Change](https://leetcode.com/problems/coin-change/)
