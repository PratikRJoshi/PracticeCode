# Combination Sum IV

## Problem Description

**Problem Link:** [Combination Sum IV](https://leetcode.com/problems/combination-sum-iv/)

Given an array of **distinct** integers `nums` and a target integer `target`, return *the number of possible combinations that add up to `target`*.

The test cases are generated so that the answer can fit in a **32-bit integer**.

**Example 1:**
```
Input: nums = [1,2,3], target = 4
Output: 7
Explanation:
The possible combination ways are:
(1, 1, 1, 1)
(1, 1, 2)
(1, 2, 1)
(1, 3)
(2, 1, 1)
(2, 2)
(3, 1)
Note that different sequences are counted as different combinations.
```

**Example 2:**
```
Input: nums = [9], target = 3
Output: 0
```

**Constraints:**
- `1 <= nums.length <= 200`
- `1 <= nums[i] <= 1000`
- All the elements of `nums` are **unique**.
- `1 <= target <= 1000`

## Intuition/Main Idea

This is an **unbounded knapsack** problem where we count the number of ways to form the target sum. Note that different orders are counted as different combinations (it's actually counting permutations).

**Core Algorithm:**
1. Use DP where `dp[i]` = number of ways to form sum `i`.
2. For each sum `i`, try each number in `nums`.
3. If `nums[j] <= i`, add `dp[i - nums[j]]` to `dp[i]`.
4. Process sums from 1 to target.

**Why this works:** To form sum `i`, we can use any number `nums[j]` if `nums[j] <= i`, and then form the remaining sum `i - nums[j]`. Since order matters, we count all permutations.

## Code Mapping

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Base case: sum 0 | dp[0] = 1 - Line 7 |
| DP array for counts | DP array - Line 6 |
| Process each sum | Outer loop - Line 9 |
| Try each number | Inner loop - Line 10 |
| Count ways | DP update - Line 12 |
| Return result | Return statement - Line 15 |

## Final Java Code & Learning Pattern

### Top-Down / Memoized Version

```java
class Solution {
    public int combinationSum4(int[] nums, int target) {
        Integer[] memo = new Integer[target + 1];
        return countWays(nums, target, memo);
    }
    
    private int countWays(int[] nums, int target, Integer[] memo) {
        // Base case: sum 0 can be formed in 1 way (empty combination)
        if (target == 0) {
            return 1;
        }
        
        if (target < 0) {
            return 0;
        }
        
        if (memo[target] != null) {
            return memo[target];
        }
        
        int ways = 0;
        for (int num : nums) {
            if (num <= target) {
                ways += countWays(nums, target - num, memo);
            }
        }
        
        memo[target] = ways;
        return ways;
    }
}
```

### Bottom-Up Version

```java
class Solution {
    public int combinationSum4(int[] nums, int target) {
        // DP: dp[i] = number of ways to form sum i
        int[] dp = new int[target + 1];
        dp[0] = 1;  // Base case: 1 way to form sum 0 (empty combination)
        
        // Process each sum from 1 to target
        for (int i = 1; i <= target; i++) {
            // Try each number in nums
            for (int num : nums) {
                if (num <= i) {
                    // Add ways to form (i - num)
                    dp[i] += dp[i - num];
                }
            }
        }
        
        return dp[target];
    }
}
```

**Explanation of Key Code Sections:**

1. **Base Case (Line 7):** `dp[0] = 1` because there's exactly 1 way to form sum 0 (empty combination).

2. **Process Sums (Line 9):** Process each sum from 1 to target in order.

3. **Try Each Number (Lines 10-14):** For each sum `i`, try each number `num` in `nums`:
   - If `num <= i`, we can use `num` to form sum `i`.
   - Add the number of ways to form `i - num` to `dp[i]`.

4. **Why order matters:** By processing sums in order and trying all numbers for each sum, we count all permutations. For example, `[1,2]` and `[2,1]` are counted separately.

**Intuition behind generating subproblems:**
- **Subproblem:** "How many ways to form sum `i`?"
- **Why this works:** To form sum `i`, we can use any number `num` where `num <= i`, then form the remaining sum `i - num`. Since we can use numbers multiple times and order matters, we count all sequences.
- **Overlapping subproblems:** Multiple sums may use the same subproblems.

**Example walkthrough for `nums = [1,2,3], target = 4`:**
- dp[0] = 1
- dp[1] = dp[0] = 1 (use 1)
- dp[2] = dp[1] + dp[0] = 1 + 1 = 2 (use 1 or 2)
- dp[3] = dp[2] + dp[1] + dp[0] = 2 + 1 + 1 = 4 (use 1,2, or 3)
- dp[4] = dp[3] + dp[2] + dp[1] = 4 + 2 + 1 = 7 ✓

## Complexity Analysis

- **Time Complexity:** $O(target \times n)$ where `n` is the length of `nums`. We process each sum up to target and try each number.

- **Space Complexity:** $O(target)$ for the DP array.

## Similar Problems

Problems that can be solved using similar unbounded knapsack counting patterns:

1. **377. Combination Sum IV** (this problem) - Count permutations (unbounded)
2. **518. Coin Change 2** - Count combinations (unbounded)
3. **322. Coin Change** - Minimum coins (unbounded)
4. **39. Combination Sum** - Find all combinations
5. **40. Combination Sum II** - With duplicates (bounded)
6. **216. Combination Sum III** - With size constraint
7. **279. Perfect Squares** - Unbounded with squares
8. **343. Integer Break** - Unbounded breaking

