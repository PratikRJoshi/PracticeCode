# Longest Arithmetic Sequence

## Problem Description

**Problem Link:** [Longest Arithmetic Sequence](https://leetcode.com/problems/longest-arithmetic-sequence/)

Given an array `nums` of integers, return *the length of the longest arithmetic subsequence in `nums`*.

**Note that:**
- A **subsequence** is an array that can be derived from another array by deleting some or no elements without changing the order of the remaining elements.
- A sequence `seq` is arithmetic if `seq[i+1] - seq[i]` are all the same value (for `0 <= i < seq.length - 1`).

**Example 1:**

```
Input: nums = [3,6,9,12]
Output: 4
Explanation:  The whole array is an arithmetic sequence with steps of length = 3.
```

**Example 2:**

```
Input: nums = [9,4,7,2,10]
Output: 3
Explanation:  The longest arithmetic subsequence is [4,7,10].
```

**Example 3:**

```
Input: nums = [20,1,15,3,10,5,8]
Output: 4
Explanation:  The longest arithmetic subsequence is [20,15,10,5].
```

**Constraints:**
- `2 <= nums.length <= 1000`
- `0 <= nums[i] <= 500`

## Intuition/Main Idea

This is a **dynamic programming** problem. We need to find the longest arithmetic subsequence.

**Core Algorithm:**
1. Use DP where `dp[i][diff]` = length of longest arithmetic subsequence ending at `i` with difference `diff`.
2. For each position `i`, check all previous positions `j` and calculate difference `diff = nums[i] - nums[j]`.
3. `dp[i][diff] = dp[j][diff] + 1`.
4. Track the maximum length.

**Why DP works:** The problem has overlapping subproblems - finding arithmetic subsequences ending at a position with a given difference is needed multiple times. We can build the solution bottom-up.

## Code Mapping

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| DP map for sequences | HashMap array - Line 6 |
| Check previous positions | Nested loops - Lines 8-14 |
| Calculate difference | Difference calculation - Line 10 |
| Update sequence length | DP update - Line 12 |
| Track maximum length | Max tracking - Line 13 |
| Return result | Return statement - Line 16 |

## Final Java Code & Learning Pattern

### Top-Down / Memoized Version

```java
import java.util.*;

class Solution {
    public int longestArithSeqLength(int[] nums) {
        int n = nums.length;
        Map<Integer, Integer>[] dp = new Map[n];
        for (int i = 0; i < n; i++) {
            dp[i] = new HashMap<>();
        }
        
        int maxLen = 2;
        
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                int diff = nums[i] - nums[j];
                int length = dp[j].getOrDefault(diff, 1) + 1;
                dp[i].put(diff, length);
                maxLen = Math.max(maxLen, length);
            }
        }
        
        return maxLen;
    }
}
```

**Explanation of Key Code Sections:**

1. **DP Array (Line 6):** `dp[i]` is a map where `dp[i][diff]` = length of longest arithmetic subsequence ending at `i` with difference `diff`.

2. **Process Positions (Lines 8-14):** For each position `i`, check all previous positions `j`:
   - **Calculate Difference (Line 10):** `diff = nums[i] - nums[j]`.
   - **Update Length (Line 12):** `dp[i][diff] = dp[j][diff] + 1` (extend sequence from `j`).
   - **Track Maximum (Line 13):** Keep track of the maximum length found.

**Intuition behind generating subproblems:**
- **Subproblem:** "What is the length of the longest arithmetic subsequence ending at `i` with difference `diff`?"
- **Why this works:** To form an arithmetic subsequence ending at `i` with difference `diff`, we can extend a subsequence ending at `j` with the same difference, where `nums[i] - nums[j] = diff`.
- **Overlapping subproblems:** Multiple positions may have subsequences with the same difference.

**Example walkthrough for `nums = [9,4,7,2,10]`:**
- i=1, j=0: diff=4-9=-5, dp[1][-5]=2
- i=2, j=0: diff=7-9=-2, dp[2][-2]=2
- i=2, j=1: diff=7-4=3, dp[2][3]=2
- i=3, j=0: diff=2-9=-7, dp[3][-7]=2
- i=3, j=1: diff=2-4=-2, dp[3][-2]=2
- i=3, j=2: diff=2-7=-5, dp[3][-5]=2
- i=4, j=0: diff=10-9=1, dp[4][1]=2
- i=4, j=1: diff=10-4=6, dp[4][6]=2
- i=4, j=2: diff=10-7=3, dp[4][3]=dp[2][3]+1=3
- Result: maxLen = 3 ✓

## Complexity Analysis

- **Time Complexity:** $O(n^2)$ where $n$ is the array length. We check each pair of positions.

- **Space Complexity:** $O(n^2)$ in worst case for the DP maps (each position may have many differences).

## Similar Problems

Problems that can be solved using similar DP patterns:

1. **1027. Longest Arithmetic Sequence** (this problem) - DP with difference tracking
2. **1218. Longest Arithmetic Subsequence of Given Difference** - Fixed difference variant
3. **300. Longest Increasing Subsequence** - Similar DP pattern
4. **368. Largest Divisible Subset** - Similar subset DP
5. **673. Number of Longest Increasing Subsequence** - Count LIS
6. **354. Russian Doll Envelopes** - 2D LIS
7. **646. Maximum Length of Pair Chain** - Interval LIS
8. **1048. Longest String Chain** - String chain DP

