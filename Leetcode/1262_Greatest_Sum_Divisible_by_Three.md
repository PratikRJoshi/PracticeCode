# Greatest Sum Divisible by Three

## Problem Description

**Problem Link:** [Greatest Sum Divisible by Three](https://leetcode.com/problems/greatest-sum-divisible-by-three/)

Given an integer array `nums`, return *the **maximum possible sum** of elements of the array such that it is divisible by three*.

**Example 1:**

```
Input: nums = [3,6,5,1,8]
Output: 18
Explanation: Pick numbers 3, 6, 1 and 8 their sum is 18 (maximum sum divisible by 3).
```

**Example 2:**

```
Input: nums = [4]
Output: 0
Explanation: Since 4 is not divisible by 3, do not pick any numbers.
```

**Example 3:**

```
Input: nums = [1,2,3,4,4]
Output: 12
Explanation: Pick numbers 1, 3, 4, 4 their sum is 12.
```

**Constraints:**
- `1 <= nums.length <= 4 * 10^4`
- `1 <= nums[i] <= 10^4`

## Intuition/Main Idea

This is a **dynamic programming** problem. We need to find the maximum sum divisible by 3.

**Core Algorithm:**
1. Use DP where `dp[i][rem]` = maximum sum with remainder `rem` (0, 1, or 2) using first `i` elements.
2. For each number, update all three remainders:
   - `newRem = (rem + num) % 3`
   - `dp[i][newRem] = max(dp[i-1][newRem], dp[i-1][rem] + num)`
3. Return `dp[n][0]`.

**Why DP works:** The problem has overlapping subproblems - finding maximum sums with given remainders is needed multiple times. We can build the solution bottom-up.

## Code Mapping

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| DP for remainders | DP array - Line 5 |
| Initialize DP | Initialization - Lines 6-8 |
| Process each number | For loop - Line 10 |
| Update remainders | Remainder update - Lines 12-15 |
| Return result | Return statement - Line 17 |

## Final Java Code & Learning Pattern

### Top-Down / Memoized Version

```java
class Solution {
    public int maxSumDivThree(int[] nums) {
        int n = nums.length;
        Integer[][] memo = new Integer[n][3];
        return maxSum(nums, 0, 0, memo);
    }
    
    private int maxSum(int[] nums, int index, int remainder, Integer[][] memo) {
        if (index == nums.length) {
            return remainder == 0 ? 0 : Integer.MIN_VALUE;
        }
        
        if (memo[index][remainder] != null) {
            return memo[index][remainder];
        }
        
        // Don't take current number
        int notTake = maxSum(nums, index + 1, remainder, memo);
        
        // Take current number
        int newRem = (remainder + nums[index]) % 3;
        int take = maxSum(nums, index + 1, newRem, memo);
        if (take != Integer.MIN_VALUE) {
            take += nums[index];
        }
        
        memo[index][remainder] = Math.max(notTake, take);
        return memo[index][remainder];
    }
}
```

### Bottom-Up Version

```java
class Solution {
    public int maxSumDivThree(int[] nums) {
        // DP: dp[rem] = maximum sum with remainder rem (0, 1, or 2)
        int[] dp = new int[3];
        dp[1] = Integer.MIN_VALUE;
        dp[2] = Integer.MIN_VALUE;
        
        for (int num : nums) {
            int[] next = new int[3];
            System.arraycopy(dp, 0, next, 0, 3);
            
            for (int rem = 0; rem < 3; rem++) {
                if (dp[rem] != Integer.MIN_VALUE) {
                    int newRem = (rem + num) % 3;
                    next[newRem] = Math.max(next[newRem], dp[rem] + num);
                }
            }
            
            dp = next;
        }
        
        return dp[0];
    }
}
```

**Explanation of Key Code Sections:**

1. **Initialize DP (Lines 6-8):** `dp[0] = 0` (sum 0 has remainder 0), `dp[1] = dp[2] = MIN_VALUE` (invalid states).

2. **Process Numbers (Lines 10-17):** For each number:
   - **Copy State (Line 12):** Copy current DP state to next state.
   - **Update Remainders (Lines 14-16):** For each valid remainder, calculate new remainder after adding current number and update.

3. **Return Result (Line 17):** Return `dp[0]` (maximum sum with remainder 0).

**Intuition behind generating subproblems:**
- **Subproblem:** "What is the maximum sum with remainder `rem` using first `i` elements?"
- **Why this works:** To get sum divisible by 3, we need remainder 0. We track maximum sums for each remainder and update as we process numbers.
- **Overlapping subproblems:** Multiple numbers may lead to the same remainder states.

**Top-down vs Bottom-up comparison:**
- **Top-down (memoized):** More intuitive, recursive. Time: O(n×3), Space: O(n×3).
- **Bottom-up (iterative):** More efficient, no recursion. Time: O(n×3), Space: O(3).
- **When bottom-up is better:** Better for this problem due to no recursion overhead and space optimization to O(1).

## Complexity Analysis

- **Time Complexity:** $O(n)$ where $n$ is the array length. We process each number once.

- **Space Complexity:** $O(1)$ for bottom-up (only 3 states), $O(n)$ for top-down.

## Similar Problems

Problems that can be solved using similar DP patterns:

1. **1262. Greatest Sum Divisible by Three** (this problem) - DP with remainder tracking
2. **518. Coin Change 2** - Count ways DP
3. **322. Coin Change** - Minimum coins DP
4. **416. Partition Equal Subset Sum** - Subset sum DP
5. **494. Target Sum** - Target sum DP
6. **1049. Last Stone Weight II** - Partition DP
7. **198. House Robber** - Similar DP pattern
8. **740. Delete and Earn** - Similar DP pattern

