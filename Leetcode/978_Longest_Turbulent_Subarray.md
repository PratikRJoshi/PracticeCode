# Longest Turbulent Subarray

## Problem Description

**Problem Link:** [Longest Turbulent Subarray](https://leetcode.com/problems/longest-turbulent-subarray/)

Given an integer array `arr`, return *the length of a maximum size turbulent subarray of `arr`*.

A subarray is **turbulent** if the comparison sign flips between each adjacent pair of elements in the subarray.

More formally, a subarray `[arr[i], arr[i+1], ..., arr[j]]` of `arr` is said to be turbulent if and only if:

- For `i <= k < j`:
  - `arr[k] > arr[k+1]` when `k` is odd, and
  - `arr[k] < arr[k+1]` when `k` is even, or
- For `i <= k < j`:
  - `arr[k] > arr[k+1]` when `k` is even, and
  - `arr[k] < arr[k+1]` when `k` is odd.

**Example 1:**

```
Input: arr = [9,4,2,10,7,8,8,1,9]
Output: 5
Explanation: arr[1] > arr[2] < arr[3] > arr[4] < arr[5]
```

**Example 2:**

```
Input: arr = [4,8,12,16]
Output: 2
```

**Example 3:**

```
Input: arr = [100]
Output: 1
```

**Constraints:**
- `1 <= arr.length <= 4 * 10^4`
- `0 <= arr[i] <= 10^9`

## Intuition/Main Idea

This is a **dynamic programming** or **sliding window** problem. We need to find the longest turbulent subarray.

**Core Algorithm:**
1. Track two states: `up` and `down` - length of turbulent subarray ending at current position where last comparison is up or down.
2. For each position:
   - If `arr[i] > arr[i-1]`: `up = down + 1`, `down = 1`
   - If `arr[i] < arr[i-1]`: `down = up + 1`, `up = 1`
   - If `arr[i] == arr[i-1]`: `up = down = 1`
3. Track maximum length.

**Why DP works:** The problem has optimal substructure - the longest turbulent subarray ending at position `i` depends on the state at `i-1`. We can build the solution iteratively.

## Code Mapping

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Track up/down states | State variables - Line 5 |
| Process each position | For loop - Line 7 |
| Handle increasing | Up update - Line 9 |
| Handle decreasing | Down update - Line 11 |
| Handle equal | Reset - Line 13 |
| Track maximum | Max tracking - Line 15 |
| Return result | Return statement - Line 17 |

## Final Java Code & Learning Pattern

### Top-Down / Memoized Version

```java
class Solution {
    public int maxTurbulenceSize(int[] arr) {
        int n = arr.length;
        if (n == 1) {
            return 1;
        }
        
        int up = 1, down = 1;
        int maxLen = 1;
        
        for (int i = 1; i < n; i++) {
            if (arr[i] > arr[i - 1]) {
                up = down + 1;
                down = 1;
            } else if (arr[i] < arr[i - 1]) {
                down = up + 1;
                up = 1;
            } else {
                up = 1;
                down = 1;
            }
            
            maxLen = Math.max(maxLen, Math.max(up, down));
        }
        
        return maxLen;
    }
}
```

**Explanation of Key Code Sections:**

1. **State Variables (Line 5):** `up` and `down` track length of turbulent subarray ending at current position where last comparison is up or down.

2. **Process Positions (Lines 7-15):** For each position:
   - **Increasing (Lines 9-10):** If `arr[i] > arr[i-1]`, extend `up` from `down` and reset `down`.
   - **Decreasing (Lines 11-12):** If `arr[i] < arr[i-1]`, extend `down` from `up` and reset `up`.
   - **Equal (Lines 13-14):** If equal, reset both to 1.

3. **Track Maximum (Line 15):** Keep track of maximum length seen.

**Intuition behind generating subproblems:**
- **Subproblem:** "What is the length of longest turbulent subarray ending at position `i` with last comparison up/down?"
- **Why this works:** To form a turbulent subarray ending at `i`, we need the previous state. If current comparison is up, we extend from previous down state, and vice versa.
- **Overlapping subproblems:** Multiple positions may share the same optimal states.

**Example walkthrough for `arr = [9,4,2,10,7,8,8,1,9]`:**
- i=1: 9>4 → up=2, down=1, maxLen=2
- i=2: 4>2 → up=down+1=2, down=1, maxLen=2
- i=3: 2<10 → down=up+1=3, up=1, maxLen=3
- i=4: 10>7 → up=down+1=4, down=1, maxLen=4
- i=5: 7<8 → down=up+1=5, up=1, maxLen=5
- Continue...
- Result: 5 ✓

## Complexity Analysis

- **Time Complexity:** $O(n)$ where $n$ is the array length. We process each element once.

- **Space Complexity:** $O(1)$ extra space.

## Similar Problems

Problems that can be solved using similar DP patterns:

1. **978. Longest Turbulent Subarray** (this problem) - State DP
2. **300. Longest Increasing Subsequence** - Similar DP pattern
3. **673. Number of Longest Increasing Subsequence** - Count LIS
4. **53. Maximum Subarray** - Kadane's algorithm
5. **152. Maximum Product Subarray** - Product variant
6. **121. Best Time to Buy and Sell Stock** - Similar pattern
7. **122. Best Time to Buy and Sell Stock II** - Multiple transactions
8. **376. Wiggle Subsequence** - Similar pattern

