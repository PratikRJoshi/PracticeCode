# Number of Longest Increasing Subsequence

## Problem Description

**Problem Link:** [Number of Longest Increasing Subsequence](https://leetcode.com/problems/number-of-longest-increasing-subsequence/)

Given an integer array `nums`, return *the number of longest increasing subsequences*.

**Notice** that the number of longest increasing subsequences may be very large, so return it **modulo** $10^9 + 7$.

**Example 1:**
```
Input: nums = [1,3,5,4,7]
Output: 2
Explanation: The two longest increasing subsequences are [1, 3, 4, 7] and [1, 3, 5, 7].
```

**Example 2:**
```
Input: nums = [2,2,2,2,2]
Output: 5
Explanation: The length of longest increasing subsequence is 1, and there are 5 subsequences with length 1, so output 5.
```

**Constraints:**
- `1 <= nums.length <= 2000`
- `-10^6 <= nums[i] <= 10^6`

## Intuition/Main Idea

This problem extends the Longest Increasing Subsequence problem by counting how many LIS exist. We need to track both the length and the count of subsequences.

**Core Algorithm:**
1. Use DP where `length[i]` = length of LIS ending at `i`.
2. Use `count[i]` = number of LIS of length `length[i]` ending at `i`.
3. For each position, find all previous positions that can extend to current.
4. Update length and count accordingly.
5. Sum counts for all positions with maximum length.

**Why this works:** We need to track both the maximum length and how many ways to achieve that length. By counting subsequences ending at each position, we can compute the total count.

## Code Mapping

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Track LIS length | Length array - Line 7 |
| Track LIS count | Count array - Line 8 |
| Initialize arrays | Arrays.fill - Lines 9-10 |
| Find extending positions | Nested loops - Lines 12-24 |
| Update length and count | DP transitions - Lines 16-22 |
| Track maximum length | Max tracking - Lines 26-28 |
| Sum counts for max length | Count summation - Lines 30-33 |
| Return result | Return statement - Line 34 |

## Final Java Code & Learning Pattern

### Top-Down / Memoized Version

```java
class Solution {
    public int findNumberOfLIS(int[] nums) {
        int n = nums.length;
        int[] length = new int[n];  // Length of LIS ending at i
        int[] count = new int[n];   // Count of LIS of length[i] ending at i
        
        Arrays.fill(length, 1);
        Arrays.fill(count, 1);
        
        int maxLen = 1;
        
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[j] < nums[i]) {
                    // If extending from j gives longer subsequence
                    if (length[j] + 1 > length[i]) {
                        length[i] = length[j] + 1;
                        count[i] = count[j];
                    }
                    // If extending from j gives same length, add to count
                    else if (length[j] + 1 == length[i]) {
                        count[i] += count[j];
                    }
                }
            }
            maxLen = Math.max(maxLen, length[i]);
        }
        
        // Sum counts for all positions with maximum length
        int result = 0;
        for (int i = 0; i < n; i++) {
            if (length[i] == maxLen) {
                result += count[i];
            }
        }
        
        return result;
    }
}
```

**Explanation of Key Code Sections:**

1. **Arrays Initialization (Lines 7-10):** 
   - `length[i]`: Length of LIS ending at position `i` (initially 1).
   - `count[i]`: Number of LIS of length `length[i]` ending at `i` (initially 1).

2. **DP Transition (Lines 12-24):** For each position `i`, check all previous positions `j`:
   - **If `nums[j] < nums[i]`:** Can extend subsequence ending at `j`.
   - **Longer subsequence (Lines 17-19):** If `length[j] + 1 > length[i]`, update length and reset count to `count[j]`.
   - **Same length (Lines 20-22):** If `length[j] + 1 == length[i]`, add `count[j]` to `count[i]`.

3. **Track Maximum (Line 26):** Keep track of the maximum LIS length.

4. **Sum Counts (Lines 30-33):** Sum the counts of all positions that have the maximum length.

**Intuition behind generating subproblems:**
- **Subproblem:** "What is the length and count of LIS ending at position `i`?"
- **Why this works:** To form a LIS ending at `i`, we extend LIS ending at any `j < i` where `nums[j] < nums[i]`. We need to track both the maximum length achievable and how many ways to achieve it.
- **Overlapping subproblems:** Multiple positions may extend from the same previous positions.

**Example walkthrough for `nums = [1,3,5,4,7]`:**
- i=0: length=1, count=1
- i=1: extends from 0 → length=2, count=1
- i=2: extends from 1 → length=3, count=1
- i=3: extends from 1 → length=2, count=1; also extends from 0 → length=2, count=1+1=2
- i=4: extends from 2 → length=4, count=1; extends from 3 → length=3, count=2
- Max length = 4, count = 1 (from position 4) ✓

Wait, let me recalculate: Actually position 4 can extend from both 2 and 3, giving length 4 and 3 respectively. So max is 4 with count 1.

## Complexity Analysis

- **Time Complexity:** $O(n^2)$ where $n$ is the length of `nums`. We check each pair of positions.

- **Space Complexity:** $O(n)$ for the length and count arrays.

## Similar Problems

Problems that can be solved using similar DP counting patterns:

1. **673. Number of Longest Increasing Subsequence** (this problem) - Count LIS
2. **300. Longest Increasing Subsequence** - Find LIS length
3. **368. Largest Divisible Subset** - Similar DP pattern
4. **354. Russian Doll Envelopes** - 2D LIS
5. **646. Maximum Length of Pair Chain** - Interval LIS
6. **1048. Longest String Chain** - String chain counting
7. **115. Distinct Subsequences** - Count subsequences
8. **940. Distinct Subsequences II** - Count distinct subsequences

