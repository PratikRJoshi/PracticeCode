# Largest Divisible Subset

## Problem Description

**Problem Link:** [Largest Divisible Subset](https://leetcode.com/problems/largest-divisible-subset/)

Given a set of **distinct** positive integers `nums`, return *the largest subset `answer` such that every pair `(answer[i], answer[j])` of elements in this subset satisfies*:

- `answer[i] % answer[j] == 0`, or
- `answer[j] % answer[i] == 0`

If there are multiple solutions, return any of them.

**Example 1:**
```
Input: nums = [1,2,3]
Output: [1,2] or [1,3]
```

**Example 2:**
```
Input: nums = [1,2,4,8]
Output: [1,2,4,8]
```

**Constraints:**
- `1 <= nums.length <= 1000`
- `1 <= nums[i] <= 2 * 10^9`
- All the integers in `nums` are **unique**.

## Intuition/Main Idea

This is a **dynamic programming** problem similar to Longest Increasing Subsequence. The key insight is that if we sort the array, then for a number to be divisible by a previous number in the subset, we only need to check divisibility with the largest number in the current subset.

**Core Algorithm:**
1. Sort the array to ensure divisibility relationships are transitive.
2. Use DP where `dp[i]` = length of largest divisible subset ending at `nums[i]`.
3. For each number, check all previous numbers and if divisible, extend the subset.
4. Track the parent of each element to reconstruct the subset.

**Why sorting works:** After sorting, if `nums[j]` divides `nums[i]` (j < i), and `nums[k]` divides `nums[j]` (k < j), then `nums[k]` divides `nums[i]`. This transitive property allows us to build the subset incrementally.

## Code Mapping

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Sort array | Arrays.sort - Line 7 |
| DP for subset length | DP array - Line 9 |
| Track parent for reconstruction | Parent array - Line 10 |
| Check divisibility | Modulo check - Line 15 |
| Extend subset | DP update - Lines 16-19 |
| Find maximum subset | Max tracking - Lines 22-26 |
| Reconstruct subset | Backtracking - Lines 28-35 |
| Return result | Return statement - Line 36 |

## Final Java Code & Learning Pattern

### Top-Down / Memoized Version

```java
import java.util.*;

class Solution {
    public List<Integer> largestDivisibleSubset(int[] nums) {
        int n = nums.length;
        Arrays.sort(nums);
        
        // DP: dp[i] = length of largest divisible subset ending at nums[i]
        int[] dp = new int[n];
        int[] parent = new int[n];  // Track parent to reconstruct subset
        
        Arrays.fill(dp, 1);
        Arrays.fill(parent, -1);
        
        int maxLen = 1;
        int maxIdx = 0;
        
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                // If nums[j] divides nums[i], we can extend the subset
                if (nums[i] % nums[j] == 0 && dp[i] < dp[j] + 1) {
                    dp[i] = dp[j] + 1;
                    parent[i] = j;
                }
            }
            
            // Track the index with maximum subset length
            if (dp[i] > maxLen) {
                maxLen = dp[i];
                maxIdx = i;
            }
        }
        
        // Reconstruct the subset using parent array
        List<Integer> result = new ArrayList<>();
        int idx = maxIdx;
        while (idx != -1) {
            result.add(nums[idx]);
            idx = parent[idx];
        }
        
        Collections.reverse(result);
        return result;
    }
}
```

**Explanation of Key Code Sections:**

1. **Sort Array (Line 7):** Sort the array to ensure transitive divisibility relationships.

2. **DP Arrays (Lines 9-10):** 
   - `dp[i]`: Length of largest divisible subset ending at `nums[i]`
   - `parent[i]`: Index of the previous element in the subset (for reconstruction)

3. **Initialize (Lines 12-13):** Each element forms a subset of length 1 by itself.

4. **DP Transition (Lines 15-21):** For each `nums[i]`, check all previous numbers `nums[j]`:
   - If `nums[i] % nums[j] == 0`, we can extend the subset ending at `j`
   - Update `dp[i]` and set `parent[i] = j` if we get a longer subset

5. **Track Maximum (Lines 23-27):** Keep track of the index with the maximum subset length.

6. **Reconstruct Subset (Lines 29-35):** Use the parent array to backtrack and build the actual subset, then reverse it.

**Intuition behind generating subproblems:**
- **Subproblem:** "What is the length of the largest divisible subset ending at `nums[i]`?"
- **Why this works:** To form a subset ending at `nums[i]`, we can extend any subset ending at `nums[j]` where `nums[j]` divides `nums[i]`.
- **Overlapping subproblems:** Multiple numbers might check the same previous subsets.

**Top-down vs Bottom-up comparison:**
- **Bottom-up (iterative):** More efficient for this problem. Time: O(n²), Space: O(n).
- **When bottom-up is better:** Better cache locality, no recursion overhead, and easier to reconstruct the subset.

## Complexity Analysis

- **Time Complexity:** $O(n^2)$ where $n$ is the length of `nums`. We check each pair of numbers once.

- **Space Complexity:** $O(n)$ for the DP and parent arrays, plus $O(n)$ for the result list.

## Similar Problems

Problems that can be solved using similar DP patterns:

1. **368. Largest Divisible Subset** (this problem) - DP with subset reconstruction
2. **300. Longest Increasing Subsequence** - Similar DP pattern
3. **354. Russian Doll Envelopes** - 2D LIS variant
4. **673. Number of Longest Increasing Subsequence** - Count LIS
5. **646. Maximum Length of Pair Chain** - Interval DP
6. **1048. Longest String Chain** - String chain DP
7. **1027. Longest Arithmetic Sequence** - Arithmetic progression DP
8. **1218. Longest Arithmetic Subsequence of Given Difference** - Fixed difference variant

