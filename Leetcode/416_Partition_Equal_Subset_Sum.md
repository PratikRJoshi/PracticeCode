# Partition Equal Subset Sum

## Problem Description

**Problem Link:** [Partition Equal Subset Sum](https://leetcode.com/problems/partition-equal-subset-sum/)

Given a **non-empty** array `nums` containing **only positive integers**, find if the array can be partitioned into two subsets such that the sum of elements in both subsets is equal.

**Example 1:**
```
Input: nums = [1,5,11,5]
Output: true
Explanation: The array can be partitioned as [1, 5, 5] and [11].
```

**Example 2:**
```
Input: nums = [1,2,3,5]
Output: false
Explanation: The array cannot be partitioned into equal sum subsets.
```

**Constraints:**
- `1 <= nums.length <= 200`
- `1 <= nums[i] <= 100`

## Intuition/Main Idea

This is a **0/1 Knapsack** problem in disguise. If we can partition the array into two equal-sum subsets, then:
- Total sum must be even (otherwise impossible)
- Each subset must sum to `totalSum / 2`
- We need to find if there's a subset that sums to `totalSum / 2`

**Core Algorithm:**
1. Calculate total sum. If odd, return false.
2. Use dynamic programming to check if we can form a subset with sum `target = totalSum / 2`.
3. `dp[i][j]` represents whether we can form sum `j` using first `i` elements.
4. Transition: `dp[i][j] = dp[i-1][j] || dp[i-1][j-nums[i]]`

**Why DP works:** This is a classic subset sum problem with overlapping subproblems. We can build the solution bottom-up by checking all possible sums.

## Code Mapping

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Check if total sum is even | Sum calculation and check - Lines 6-10 |
| Calculate target sum | Target = totalSum / 2 - Line 12 |
| Track if sum is achievable | DP array - Line 14 |
| Base case: sum 0 is always achievable | `dp[0] = true` - Line 15 |
| Process each number | For loop - Line 17 |
| Update DP for each possible sum | Inner loop - Line 19 |
| Check if target sum is achievable | Return statement - Line 24 |

## Final Java Code & Learning Pattern

### Top-Down / Memoized Version

```java
class Solution {
    public boolean canPartition(int[] nums) {
        int totalSum = 0;
        for (int num : nums) {
            totalSum += num;
        }
        
        // If total sum is odd, cannot partition into equal subsets
        if (totalSum % 2 != 0) {
            return false;
        }
        
        int target = totalSum / 2;
        int n = nums.length;
        // Memoization: memo[i][sum] = can we form sum using elements starting from index i
        Boolean[][] memo = new Boolean[n][target + 1];
        
        return canFormSum(nums, 0, target, memo);
    }
    
    private boolean canFormSum(int[] nums, int index, int sum, Boolean[][] memo) {
        // Base case: sum 0 is always achievable (empty subset)
        if (sum == 0) {
            return true;
        }
        
        // Base case: reached end of array or negative sum
        if (index == nums.length || sum < 0) {
            return false;
        }
        
        // Check memoization
        if (memo[index][sum] != null) {
            return memo[index][sum];
        }
        
        // Two choices: include nums[index] or exclude it
        boolean include = canFormSum(nums, index + 1, sum - nums[index], memo);
        boolean exclude = canFormSum(nums, index + 1, sum, memo);
        
        memo[index][sum] = include || exclude;
        return memo[index][sum];
    }
}
```

### Bottom-Up Version

```java
class Solution {
    public boolean canPartition(int[] nums) {
        int totalSum = 0;
        for (int num : nums) {
            totalSum += num;
        }
        
        // If total sum is odd, cannot partition into equal subsets
        if (totalSum % 2 != 0) {
            return false;
        }
        
        int target = totalSum / 2;
        int n = nums.length;
        
        // DP array: dp[i] = can we form sum i using some subset
        boolean[] dp = new boolean[target + 1];
        dp[0] = true;  // Base case: sum 0 is always achievable
        
        // Process each number
        for (int num : nums) {
            // Iterate backwards to avoid using same number twice
            for (int j = target; j >= num; j--) {
                // Can form sum j either by:
                // 1. Not including num (dp[j] already true)
                // 2. Including num (dp[j - num] must be true)
                dp[j] = dp[j] || dp[j - num];
            }
        }
        
        return dp[target];
    }
}
```

**Explanation of Key Code Sections:**

1. **Sum Calculation and Validation (Lines 6-10):** We calculate the total sum. If it's odd, we can't partition into two equal-sum subsets, so we return false immediately.

2. **Target Calculation (Line 12):** The target sum for each subset is `totalSum / 2`. If we can form a subset with this sum, the remaining elements automatically form the other subset with the same sum.

3. **DP Array Initialization (Line 14):** `dp[i]` represents whether we can form sum `i` using some subset of the numbers we've processed so far. `dp[0] = true` because we can always form sum 0 with an empty subset.

4. **Process Each Number (Line 17):** For each number in the array, we update our DP array to reflect the new possibilities.

5. **Update DP Backwards (Lines 19-23):** We iterate backwards from `target` to `num`:
   - **Why backwards?** To ensure we don't use the same number twice. If we iterate forwards, `dp[j - num]` might have already been updated with the current `num`, leading to using it multiple times.
   - **Transition:** `dp[j] = dp[j] || dp[j - num]` means we can form sum `j` either by not including `num` (if `dp[j]` was already true) or by including `num` (if we can form `j - num`).

6. **Return Result (Line 24):** After processing all numbers, `dp[target]` tells us if we can form the target sum.

**Intuition behind generating subproblems:**
- **Subproblem:** "Can we form sum `j` using some subset of the first `i` numbers?"
- **Why this works:** To form sum `j` with first `i` numbers, we either:
  - Don't include `nums[i]`: need to form `j` with first `i-1` numbers
  - Include `nums[i]`: need to form `j - nums[i]` with first `i-1` numbers
- **Overlapping subproblems:** Multiple paths may lead to the same sum, so we memoize results.

**Top-down vs Bottom-up comparison:**
- **Top-down (memoized):** More intuitive, recursive approach. Time: O(n × target), Space: O(n × target) for memo + recursion stack.
- **Bottom-up (iterative):** More efficient, no recursion overhead. Time: O(n × target), Space: O(target) with space optimization.
- **When bottom-up is better:** For this problem, bottom-up is better due to:
  - No recursion overhead
  - Better cache locality
  - Space optimization possible (1D array instead of 2D)
  - No risk of stack overflow

## Complexity Analysis

- **Time Complexity:** $O(n \times target)$ where $n$ is the array length and $target = totalSum / 2$. We process each number and for each, check all possible sums up to target.

- **Space Complexity:** 
  - Bottom-up optimized: $O(target)$ for the DP array
  - Top-down memoized: $O(n \times target)$ for memo array + recursion stack

## Similar Problems

Problems that can be solved using similar 0/1 Knapsack DP patterns:

1. **416. Partition Equal Subset Sum** (this problem) - Subset sum problem
2. **494. Target Sum** - Similar with + and - signs
3. **518. Coin Change 2** - Unbounded knapsack variant
4. **322. Coin Change** - Minimum coins for target sum
5. **279. Perfect Squares** - Minimum squares for target
6. **377. Combination Sum IV** - Count ways to form target
7. **1049. Last Stone Weight II** - Similar partition problem
8. **474. Ones and Zeroes** - 2D knapsack
9. **879. Profitable Schemes** - 2D knapsack with constraints
10. **1155. Number of Dice Rolls With Target Sum** - Counting ways
