# Maximum Length of Pair Chain

## Problem Description

**Problem Link:** [Maximum Length of Pair Chain](https://leetcode.com/problems/maximum-length-of-pair-chain/)

You are given an array of `n` pairs `pairs` where `pairs[i] = [lefti, righti]` and `lefti < righti`.

A pair `p2 = [c, d]` **follows** a pair `p1 = [a, b]` if and only if `b < c`. A **chain** of pairs can be formed in this fashion.

Return *the length of the longest chain which can be formed*.

You do not need to use up all the given pairs. You can select pairs in any order.

**Example 1:**
```
Input: pairs = [[1,2],[2,3],[3,4]]
Output: 2
Explanation: The longest chain is [1,2] -> [3,4].
```

**Example 2:**
```
Input: pairs = [[1,2],[7,8],[4,5]]
Output: 3
Explanation: The longest chain is [1,2] -> [4,5] -> [7,8].
```

**Constraints:**
- `n == pairs.length`
- `1 <= n <= 1000`
- `-1000 <= lefti < righti <= 1000`

## Intuition/Main Idea

This is a **greedy** or **DP** problem. The greedy approach is optimal: sort pairs by ending time and always choose the pair with the smallest ending time that doesn't conflict.

**Core Algorithm (Greedy):**
1. Sort pairs by their ending time (right value).
2. Greedily select pairs: always choose the pair with the smallest ending time that doesn't conflict with previously selected pairs.
3. This ensures maximum room for future pairs.

**Why greedy works:** By always choosing the pair with the smallest ending time, we leave maximum room for future pairs. This is the optimal strategy.

## Code Mapping

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Sort by end time | Custom comparator - Line 7 |
| Track last end time | Last variable - Line 10 |
| Greedily select pairs | For loop - Lines 11-15 |
| Check non-conflict | Comparison - Line 12 |
| Count chain length | Counter increment - Line 14 |
| Return maximum length | Return statement - Line 16 |

## Final Java Code & Learning Pattern

### Greedy Approach (Optimal)

```java
import java.util.*;

class Solution {
    public int findLongestChain(int[][] pairs) {
        // Sort pairs by ending time (right value)
        Arrays.sort(pairs, (a, b) -> a[1] - b[1]);
        
        int chainLength = 0;
        int lastEnd = Integer.MIN_VALUE;
        
        for (int[] pair : pairs) {
            // If current pair starts after last pair ends, add to chain
            if (pair[0] > lastEnd) {
                chainLength++;
                lastEnd = pair[1];
            }
        }
        
        return chainLength;
    }
}
```

### DP Approach

```java
import java.util.*;

class Solution {
    public int findLongestChain(int[][] pairs) {
        int n = pairs.length;
        Arrays.sort(pairs, (a, b) -> a[0] - b[0]);
        
        // DP: dp[i] = length of longest chain ending at pairs[i]
        int[] dp = new int[n];
        Arrays.fill(dp, 1);
        
        int maxLen = 1;
        
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                // If pairs[j] can be followed by pairs[i]
                if (pairs[j][1] < pairs[i][0]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            maxLen = Math.max(maxLen, dp[i]);
        }
        
        return maxLen;
    }
}
```

**Explanation of Key Code Sections:**

**Greedy Approach:**

1. **Sort by End Time (Line 7):** Sort pairs by their ending time (right value). This ensures we consider pairs in order of when they end.

2. **Greedy Selection (Lines 11-15):** For each pair:
   - If it starts after the last selected pair ends (`pair[0] > lastEnd`), add it to the chain.
   - Update `lastEnd` to the current pair's end time.

**DP Approach:**

1. **Sort by Start Time (Line 7):** Sort pairs by start time for DP approach.

2. **DP Transition (Lines 13-18):** For each pair `i`, check all previous pairs `j`:
   - If `pairs[j]` can be followed by `pairs[i]` (end < start), extend the chain.

**Why greedy is better:**
- **Time:** O(n log n) vs O(n²) for DP
- **Space:** O(1) vs O(n) for DP
- **Optimal:** Greedy always finds the optimal solution

**Example walkthrough for `pairs = [[1,2],[2,3],[3,4]]`:**
- After sort by end: `[[1,2],[2,3],[3,4]]`
- Select [1,2]: chainLength=1, lastEnd=2
- Skip [2,3]: 2 is not > 2
- Select [3,4]: chainLength=2, lastEnd=4
- Result: 2 ✓

## Complexity Analysis

- **Time Complexity:** 
  - Greedy: $O(n \log n)$ for sorting.
  - DP: $O(n^2)$ for nested loops.

- **Space Complexity:** 
  - Greedy: $O(1)$ extra space.
  - DP: $O(n)$ for the DP array.

## Similar Problems

Problems that can be solved using similar greedy or interval DP patterns:

1. **646. Maximum Length of Pair Chain** (this problem) - Greedy interval selection
2. **435. Non-overlapping Intervals** - Greedy interval removal
3. **452. Minimum Number of Arrows to Burst Balloons** - Greedy interval covering
4. **56. Merge Intervals** - Interval merging
5. **57. Insert Interval** - Interval insertion
6. **253. Meeting Rooms II** - Interval scheduling
7. **354. Russian Doll Envelopes** - 2D interval chaining
8. **300. Longest Increasing Subsequence** - Similar pattern

