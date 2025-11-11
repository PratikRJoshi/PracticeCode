# Longest Arithmetic Subsequence of Given Difference

## Problem Description

**Problem Link:** [Longest Arithmetic Subsequence of Given Difference](https://leetcode.com/problems/longest-arithmetic-subsequence-of-given-difference/)

Given an integer array `arr` and an integer `difference`, return *the length of the longest subsequence in `arr` which is an arithmetic sequence such that the difference between adjacent elements in the subsequence equals `difference`*.

A **subsequence** is a sequence that can be derived from `arr` by deleting some or no elements without changing the order of the remaining elements.

**Example 1:**

```
Input: arr = [1,2,3,4], difference = 1
Output: 4
Explanation: The longest arithmetic subsequence is [1,2,3,4].
```

**Example 2:**

```
Input: arr = [1,3,5,7], difference = 1
Output: 1
Explanation: The longest arithmetic subsequence is any single element.
```

**Example 3:**

```
Input: arr = [1,5,7,8,5,3,4,2,1], difference = -2
Output: 4
Explanation: The longest arithmetic subsequence is [1,3,5,7].
```

**Constraints:**
- `1 <= arr.length <= 10^5`
- `-10^4 <= arr[i] <= 10^4`
- `-10^4 <= difference <= 10^4`

## Intuition/Main Idea

This is a **dynamic programming** problem with a fixed difference. We need to find the longest arithmetic subsequence with a given difference.

**Core Algorithm:**
1. Use DP where `dp[value]` = length of longest arithmetic subsequence ending with `value`.
2. For each number `arr[i]`, check if `arr[i] - difference` exists in DP.
3. `dp[arr[i]] = dp[arr[i] - difference] + 1`.
4. Track the maximum length.

**Why DP works:** The problem has overlapping subproblems - finding subsequences ending with a value is needed multiple times. Since the difference is fixed, we can use a map to track lengths by value.

## Code Mapping

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| DP map for lengths | HashMap - Line 5 |
| Process each number | For loop - Line 7 |
| Check previous value | Previous value check - Line 8 |
| Update sequence length | DP update - Line 9 |
| Track maximum length | Max tracking - Line 10 |
| Return result | Return statement - Line 12 |

## Final Java Code & Learning Pattern

### Top-Down / Memoized Version

```java
import java.util.*;

class Solution {
    public int longestSubsequence(int[] arr, int difference) {
        Map<Integer, Integer> dp = new HashMap<>();
        int maxLen = 1;
        
        for (int num : arr) {
            int prev = num - difference;
            int length = dp.getOrDefault(prev, 0) + 1;
            dp.put(num, length);
            maxLen = Math.max(maxLen, length);
        }
        
        return maxLen;
    }
}
```

**Explanation of Key Code Sections:**

1. **DP Map (Line 5):** `dp[value]` = length of longest arithmetic subsequence ending with `value`.

2. **Process Numbers (Lines 7-10):** For each number `num`:
   - **Previous Value (Line 8):** `prev = num - difference` (the value that should come before `num`).
   - **Update Length (Line 9):** `dp[num] = dp[prev] + 1` (extend sequence from `prev`).
   - **Track Maximum (Line 10):** Keep track of the maximum length found.

**Intuition behind generating subproblems:**
- **Subproblem:** "What is the length of the longest arithmetic subsequence ending with `value`?"
- **Why this works:** To form an arithmetic subsequence ending with `num` and difference `difference`, we can extend a subsequence ending with `num - difference`. Since the difference is fixed, we only need to track lengths by value.
- **Overlapping subproblems:** Multiple numbers may extend from the same previous value.

**Example walkthrough for `arr = [1,5,7,8,5,3,4,2,1], difference = -2`:**
- num=1: prev=3, dp[1]=dp[3]+1=0+1=1, maxLen=1
- num=5: prev=7, dp[5]=dp[7]+1=0+1=1, maxLen=1
- num=7: prev=9, dp[7]=dp[9]+1=0+1=1, maxLen=1
- num=8: prev=10, dp[8]=dp[10]+1=0+1=1, maxLen=1
- num=5: prev=7, dp[5]=dp[7]+1=1+1=2, maxLen=2
- num=3: prev=5, dp[3]=dp[5]+1=2+1=3, maxLen=3
- num=4: prev=6, dp[4]=dp[6]+1=0+1=1, maxLen=3
- num=2: prev=4, dp[2]=dp[4]+1=1+1=2, maxLen=3
- num=1: prev=3, dp[1]=dp[3]+1=3+1=4, maxLen=4
- Result: 4 ✓

## Complexity Analysis

- **Time Complexity:** $O(n)$ where $n$ is the array length. We process each number once.

- **Space Complexity:** $O(n)$ for the DP map in worst case.

## Similar Problems

Problems that can be solved using similar DP patterns:

1. **1218. Longest Arithmetic Subsequence of Given Difference** (this problem) - Fixed difference DP
2. **1027. Longest Arithmetic Sequence** - Variable difference DP
3. **300. Longest Increasing Subsequence** - Similar DP pattern
4. **368. Largest Divisible Subset** - Similar subset DP
5. **673. Number of Longest Increasing Subsequence** - Count LIS
6. **354. Russian Doll Envelopes** - 2D LIS
7. **646. Maximum Length of Pair Chain** - Interval LIS
8. **1048. Longest String Chain** - String chain DP

