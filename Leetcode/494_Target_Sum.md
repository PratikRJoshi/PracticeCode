# Target Sum

## Problem Description

**Problem Link:** [Target Sum](https://leetcode.com/problems/target-sum/)

You are given an integer array `nums` and an integer `target`.

You want to build an **expression** out of `nums` by adding one of the symbols `'+'` and `'-'` before each integer in `nums` and then concatenate all the integers.

For example, if `nums = [2, 1]`, you can add a `'+'` before `2` and a `'-'` before `1` and concatenate them to build the expression `"+2-1"`.

Return *the number of different **expressions** that you can build, which evaluates to `target`*.

**Example 1:**
```
Input: nums = [1,1,1,1,1], target = 3
Output: 5
Explanation: There are 5 ways to assign symbols to make the sum of nums be target 3.
-1 + 1 + 1 + 1 + 1 = 3
+1 - 1 + 1 + 1 + 1 = 3
+1 + 1 - 1 + 1 + 1 = 3
+1 + 1 + 1 - 1 + 1 = 3
+1 + 1 + 1 + 1 - 1 = 3
```

**Example 2:**
```
Input: nums = [1], target = 1
Output: 1
```

**Constraints:**
- `1 <= nums.length <= 20`
- `0 <= nums[i] <= 1000`
- `0 <= sum(nums[i]) <= 1000`
- `-1000 <= target <= 1000`

## Intuition/Main Idea

This problem can be transformed into a **subset sum** problem. If we assign `+` to some numbers (set P) and `-` to others (set N), then:
- `sum(P) - sum(N) = target`
- `sum(P) + sum(N) = totalSum`
- Adding: `2 × sum(P) = target + totalSum`
- Therefore: `sum(P) = (target + totalSum) / 2`

So we need to find the number of ways to form a subset with sum `(target + totalSum) / 2`.

**Core Algorithm:**
1. Calculate total sum. Check if `(target + totalSum)` is even and non-negative.
2. Use dynamic programming to count ways to form sum `(target + totalSum) / 2`.
3. `dp[i]` = number of ways to form sum `i`.

**Why DP works:** This is a counting variant of the subset sum problem. We need to count the number of ways, not just check existence.

## Code Mapping

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Calculate total sum | Sum calculation - Lines 6-8 |
| Validate feasibility | Validation check - Lines 10-13 |
| Calculate target subset sum | Target calculation - Line 15 |
| Count ways to form sum | DP array - Line 17 |
| Base case: 1 way to form sum 0 | `dp[0] = 1` - Line 18 |
| Process each number | For loop - Line 21 |
| Update count for each sum | Inner loop - Line 23 |
| Return number of ways | Return statement - Line 27 |

## Final Java Code & Learning Pattern

### Top-Down / Memoized Version

```java
class Solution {
    public int findTargetSumWays(int[] nums, int target) {
        int totalSum = 0;
        for (int num : nums) {
            totalSum += num;
        }
        
        // Check if (target + totalSum) is even and non-negative
        if ((target + totalSum) % 2 != 0 || target + totalSum < 0) {
            return 0;
        }
        
        int subsetSum = (target + totalSum) / 2;
        int n = nums.length;
        // Memoization: memo[index][sum] = ways to form sum using first index elements
        Integer[][] memo = new Integer[n][subsetSum + 1];
        
        return countWays(nums, n - 1, subsetSum, memo);
    }
    
    private int countWays(int[] nums, int index, int sum, Integer[][] memo) {
        // Base case: sum 0 can be formed in 1 way (empty subset)
        if (sum == 0 && index < 0) {
            return 1;
        }
        
        // Base case: no more elements or negative sum
        if (index < 0 || sum < 0) {
            return 0;
        }
        
        // Check memoization
        if (memo[index][sum] != null) {
            return memo[index][sum];
        }
        
        // Two choices: include nums[index] or exclude it
        int include = countWays(nums, index - 1, sum - nums[index], memo);
        int exclude = countWays(nums, index - 1, sum, memo);
        
        memo[index][sum] = include + exclude;
        return memo[index][sum];
    }
}
```

### Bottom-Up Version

```java
class Solution {
    public int findTargetSumWays(int[] nums, int target) {
        int totalSum = 0;
        for (int num : nums) {
            totalSum += num;
        }
        
        // Transform: find ways to form subset with sum = (target + totalSum) / 2
        // Check feasibility
        if ((target + totalSum) % 2 != 0 || target + totalSum < 0) {
            return 0;
        }
        
        int subsetSum = (target + totalSum) / 2;
        
        // DP array: dp[i] = number of ways to form sum i
        int[] dp = new int[subsetSum + 1];
        dp[0] = 1;  // Base case: 1 way to form sum 0 (empty subset)
        
        // Process each number
        for (int num : nums) {
            // Iterate backwards to avoid using same number twice
            for (int j = subsetSum; j >= num; j--) {
                // Ways to form sum j = ways without num + ways with num
                dp[j] += dp[j - num];
            }
        }
        
        return dp[subsetSum];
    }
}
```

**Explanation of Key Code Sections:**

1. **Sum Calculation (Lines 6-8):** Calculate the total sum of all numbers in the array.

2. **Feasibility Check (Lines 10-13):** 
   - Check if `(target + totalSum)` is even (required for integer division)
   - Check if `target + totalSum >= 0` (subset sum must be non-negative)
   - If either fails, return 0 (no solution possible)

3. **Target Subset Sum (Line 15):** Calculate the required subset sum: `(target + totalSum) / 2`. This is the sum of numbers we assign `+` signs to.

4. **DP Array Initialization (Lines 17-18):** 
   - `dp[i]` represents the number of ways to form sum `i`
   - `dp[0] = 1` because there's exactly 1 way to form sum 0 (empty subset)

5. **Process Each Number (Line 21):** For each number, update the DP array to count new ways to form sums.

6. **Update DP Backwards (Lines 23-26):** 
   - Iterate backwards to avoid using the same number multiple times
   - For each sum `j >= num`, add the count from `dp[j - num]` to `dp[j]`
   - This counts: ways to form `j` without `num` (already in `dp[j]`) + ways to form `j` with `num` (`dp[j - num]`)

7. **Return Result (Line 27):** `dp[subsetSum]` contains the number of ways to form the target subset sum, which equals the number of expressions evaluating to `target`.

**Intuition behind generating subproblems:**
- **Subproblem:** "How many ways can we form sum `j` using some subset of the first `i` numbers?"
- **Why this works:** To form sum `j` with first `i` numbers, we can either:
  - Exclude `nums[i]`: count ways to form `j` with first `i-1` numbers
  - Include `nums[i]`: count ways to form `j - nums[i]` with first `i-1` numbers
- **Counting:** We sum both possibilities: `dp[j] = dp[j] + dp[j - num]`

**Transformation explanation:**
- Original: `sum(P) - sum(N) = target` where P has `+` signs, N has `-` signs
- Also: `sum(P) + sum(N) = totalSum`
- Adding: `2 × sum(P) = target + totalSum`
- Therefore: `sum(P) = (target + totalSum) / 2`
- So we count ways to form subset with sum `(target + totalSum) / 2`

**Top-down vs Bottom-up comparison:**
- **Top-down (memoized):** More intuitive, recursive. Time: O(n × subsetSum), Space: O(n × subsetSum) for memo + stack.
- **Bottom-up (iterative):** More efficient, no recursion. Time: O(n × subsetSum), Space: O(subsetSum).
- **When bottom-up is better:** Better for this problem due to no recursion overhead, better cache locality, and space efficiency.

## Complexity Analysis

- **Time Complexity:** $O(n \times subsetSum)$ where $n$ is the array length and $subsetSum = (target + totalSum) / 2$. We process each number and for each, update all sums from `subsetSum` down to `num`.

- **Space Complexity:** 
  - Bottom-up optimized: $O(subsetSum)$ for the DP array
  - Top-down memoized: $O(n \times subsetSum)$ for memo array + recursion stack

## Similar Problems

Problems that can be solved using similar counting DP or subset sum patterns:

1. **494. Target Sum** (this problem) - Counting subset sum with transformation
2. **416. Partition Equal Subset Sum** - Existence check for subset sum
3. **518. Coin Change 2** - Counting ways (unbounded knapsack)
4. **377. Combination Sum IV** - Counting ways with repetition
5. **39. Combination Sum** - Find all combinations
6. **40. Combination Sum II** - With duplicates
7. **216. Combination Sum III** - With size constraint
8. **1049. Last Stone Weight II** - Similar partition problem
9. **1155. Number of Dice Rolls With Target Sum** - Counting with constraints
10. **879. Profitable Schemes** - 2D counting DP

