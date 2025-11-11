# Last Stone Weight II

## Problem Description

**Problem Link:** [Last Stone Weight II](https://leetcode.com/problems/last-stone-weight-ii/)

You are given an array of integers `stones` where `stones[i]` is the weight of the `i`th stone.

We are playing a game with the stones. On each turn, we choose any two stones and smash them together. Suppose the stones have weights `x` and `y` with `x <= y`. The result of this smash is:

- If `x == y`, both stones are totally destroyed;
- If `x != y`, the stone of weight `x` is totally destroyed, and the stone of weight `y` has new weight `y - x`.

At the end of the game, there is **at most one** stone left.

Return *the smallest possible weight of the left stone*. If there are no stones left, return `0`.

**Example 1:**
```
Input: stones = [2,7,4,1,8,1]
Output: 1
Explanation:
We can combine 2 and 4 to get 2, so the array converts to [2,7,1,8,1] then,
we can combine 7 and 8 to get 1, so the array converts to [2,1,1,1] then,
we can combine 2 and 1 to get 1, so the array converts to [1,1,1] then,
we can combine 1 and 1 to get 0, so the array converts to [1] then that's the optimal value.
```

**Example 2:**
```
Input: stones = [31,26,33,21,40]
Output: 5
```

**Constraints:**
- `1 <= stones.length <= 30`
- `1 <= stones[i] <= 100`

## Intuition/Main Idea

This problem can be transformed into a **partition problem**. The key insight is that we want to partition stones into two groups such that the difference between their sums is minimized.

**Core Algorithm:**
1. Calculate total sum of stones.
2. Use DP to find if we can form a subset with sum closest to `totalSum / 2`.
3. The answer is `totalSum - 2 * subsetSum`, where `subsetSum` is the sum of one partition.

**Why this works:** If we partition stones into two groups with sums `S1` and `S2`, the final stone weight is `|S1 - S2|`. To minimize this, we want `S1` and `S2` as close as possible, ideally both near `totalSum / 2`.

## Code Mapping

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Calculate total sum | Sum calculation - Lines 6-8 |
| Calculate target | Target = totalSum / 2 - Line 10 |
| DP for subset sum | DP array - Line 12 |
| Base case | dp[0] = true - Line 13 |
| Process stones | For loop - Line 16 |
| Update DP | Backward iteration - Lines 18-21 |
| Find closest sum | For loop - Lines 23-26 |
| Return result | Return statement - Line 27 |

## Final Java Code & Learning Pattern

### Top-Down / Memoized Version

```java
class Solution {
    public int lastStoneWeightII(int[] stones) {
        int totalSum = 0;
        for (int stone : stones) {
            totalSum += stone;
        }
        
        int target = totalSum / 2;
        int n = stones.length;
        Boolean[][] memo = new Boolean[n][target + 1];
        
        // Find the largest sum <= target that we can form
        int closestSum = findClosestSum(stones, n - 1, target, memo);
        
        return totalSum - 2 * closestSum;
    }
    
    private int findClosestSum(int[] stones, int index, int sum, Boolean[][] memo) {
        if (sum == 0 || index < 0) {
            return 0;
        }
        
        if (memo[index][sum] != null) {
            // Return the sum if we can form it
            return memo[index][sum] ? sum : 0;
        }
        
        int result = 0;
        if (stones[index] <= sum) {
            int include = stones[index] + findClosestSum(stones, index - 1, sum - stones[index], memo);
            int exclude = findClosestSum(stones, index - 1, sum, memo);
            result = Math.max(include, exclude);
        } else {
            result = findClosestSum(stones, index - 1, sum, memo);
        }
        
        memo[index][sum] = (result > 0);
        return result;
    }
}
```

### Bottom-Up Version

```java
class Solution {
    public int lastStoneWeightII(int[] stones) {
        int totalSum = 0;
        for (int stone : stones) {
            totalSum += stone;
        }
        
        int target = totalSum / 2;
        
        // DP: dp[i] = can we form sum i using some subset
        boolean[] dp = new boolean[target + 1];
        dp[0] = true;
        
        // Process each stone
        for (int stone : stones) {
            for (int j = target; j >= stone; j--) {
                dp[j] = dp[j] || dp[j - stone];
            }
        }
        
        // Find the largest sum <= target that we can form
        for (int i = target; i >= 0; i--) {
            if (dp[i]) {
                return totalSum - 2 * i;
            }
        }
        
        return totalSum;
    }
}
```

**Explanation of Key Code Sections:**

1. **Calculate Total Sum (Lines 6-8):** Sum all stone weights.

2. **Target Calculation (Line 10):** Target is `totalSum / 2`. We want to find a subset with sum as close to this as possible.

3. **DP Array (Line 12):** `dp[i]` represents whether we can form sum `i` using some subset of stones.

4. **Process Stones (Lines 16-21):** For each stone, update DP backwards to avoid using the same stone twice.

5. **Find Closest Sum (Lines 23-26):** Find the largest sum `<= target` that we can form. This gives us the partition closest to `totalSum / 2`.

6. **Return Result (Line 27):** Return `totalSum - 2 * closestSum`, which is the difference between the two partitions.

**Intuition behind generating subproblems:**
- **Subproblem:** "Can we form sum `j` using some subset of stones?"
- **Why this works:** To minimize `|S1 - S2|`, we want `S1` and `S2` as close as possible. Since `S1 + S2 = totalSum`, if `S1 ≈ totalSum/2`, then `S2 ≈ totalSum/2`, minimizing the difference.
- **Overlapping subproblems:** Multiple subsets may have the same sum.

**Example walkthrough for `stones = [2,7,4,1,8,1]`:**
- Total sum = 23, target = 11
- Can form sums: 0,1,2,3,4,5,6,7,8,9,10,11
- Closest to 11: 11 (can form with subset like [2,4,1,1,3] wait, let me recalculate)
- Actually: Can form 11 with [2,4,1,1,3]? No, that's 11. Let me think...
- Subsets: [2,7,1,1]=11, [4,7]=11, etc.
- Result: 23 - 2*11 = 1 ✓

## Complexity Analysis

- **Time Complexity:** $O(n \times target)$ where $n$ is the number of stones and $target = totalSum / 2$. We process each stone and check sums up to target.

- **Space Complexity:** $O(target)$ for the DP array. Can be optimized from 2D to 1D.

## Similar Problems

Problems that can be solved using similar partition DP patterns:

1. **1049. Last Stone Weight II** (this problem) - Partition to minimize difference
2. **416. Partition Equal Subset Sum** - Partition into equal sums
3. **494. Target Sum** - Partition with target difference
4. **698. Partition to K Equal Sum Subsets** - K-way partition
5. **473. Matchsticks to Square** - 4-way partition
6. **2305. Fair Distribution of Cookies** - Partition with constraints
7. **2035. Partition Array Into Two Arrays to Minimize Sum Difference** - Minimize difference

