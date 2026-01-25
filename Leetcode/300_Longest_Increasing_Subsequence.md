# 300. Longest Increasing Subsequence
[Longest Increasing Subsequence](https://leetcode.com/problems/longest-increasing-subsequence/)

## Problem Description
Given an integer array `nums`, return the length of the longest strictly increasing subsequence.

A subsequence is a sequence that can be derived from an array by deleting some or no elements without changing the order of the remaining elements.

**Example 1:**
```
Input: nums = [10,9,2,5,3,7,101,18]
Output: 4
Explanation: The longest increasing subsequence is [2,3,7,101], therefore the length is 4.
```

**Example 2:**
```
Input: nums = [0,1,0,3,2,3]
Output: 4
```

**Example 3:**
```
Input: nums = [7,7,7,7,7,7,7]
Output: 1
```

**Constraints:**
- 1 <= nums.length <= 2500
- -10^4 <= nums[i] <= 10^4

## Intuition/Main Idea
This problem asks us to find the length of the longest subsequence where all elements are in strictly increasing order. The key insight is to use dynamic programming. For each element, we consider all previous elements and extend the longest increasing subsequence if possible. We maintain an array `dp` where `dp[i]` represents the length of the longest increasing subsequence ending at index `i`.

Alternatively, a more efficient approach uses binary search to maintain an array of the smallest values that can end subsequences of different lengths.

## Code Mapping

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Handle empty or null input | Lines 12-14: Check if array is null or empty |
| Initialize DP array | Lines 17-18: Create and initialize DP array with 1s |
| Find longest subsequence | Lines 22-28: Double loop to build DP table |
| Return maximum length | Lines 30-31: Return the maximum length found |

## Final Java Code & Learning Pattern

### Dynamic Programming Approach (O(n²))

```java
class Solution {
    public int lengthOfLIS(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        
        int n = nums.length;
        // dp[i] represents the length of the longest increasing subsequence ending at index i
        int[] dp = new int[n];
        // Each element by itself forms a subsequence of length 1
        Arrays.fill(dp, 1);
        
        int maxLength = 1;
        
        // For each position, check all previous positions
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                // If current element is greater than previous element, we can extend the subsequence
                if (nums[i] > nums[j]) {
                    // Update LIS length at current position to be max of current or (previous + 1)
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            // Keep track of the maximum LIS length found so far
            maxLength = Math.max(maxLength, dp[i]);
        }
        
        return maxLength;
    }
}
```

### Binary Search Approach (O(n log n))

```java
class Solution {
    public int lengthOfLIS(int[] nums) {
        // This list stores the smallest ending value for subsequences of each length
        List<Integer> tails = new ArrayList<>();
        
        for (int num : nums) {
            // Find the position where this number should be inserted or replaced
            int pos = binarySearch(num, tails);
            
            if (pos < tails.size()) {
                // Replace the existing element (optimize the subsequence)
                tails.set(pos, num);
            } else {
                // Append to create a longer subsequence
                tails.add(num);
            }
        }
        
        // The size of tails is the length of LIS
        return tails.size();
    }
    
    /**
     * Binary search to find the position where num should be inserted
     * Returns the index of the first element >= num, or tails.size() if all elements < num
     */
    private int binarySearch(int num, List<Integer> tails) {
        int left = 0, right = tails.size() - 1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (tails.get(mid) == num) {
                return mid;
            } else if (tails.get(mid) < num) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        
        return left;
    }
}
```

## Dynamic Programming Explanations

### Top-Down Approach (Memoization)
The intuition behind the top-down approach is to start from each position and ask: "What is the longest increasing subsequence starting from here?" We can use recursion with memoization to avoid recalculating subproblems.

```java
class Solution {
    public int lengthOfLIS(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        
        int n = nums.length;
        // memo[i] represents the length of LIS starting from index i
        // Size n is allocated as we need to store results for each starting position
        Integer[] memo = new Integer[n];
        
        int maxLength = 0;
        for (int i = 0; i < n; i++) {
            maxLength = Math.max(maxLength, findLIS(nums, i, memo));
        }
        
        return maxLength;
    }
    
    private int findLIS(int[] nums, int start, Integer[] memo) {
        // If we've already computed this subproblem
        if (memo[start] != null) {
            return memo[start];
        }
        
        // Base case: every element forms a subsequence of length 1
        int maxLength = 1;
        
        // Try to extend the subsequence with elements after the current one
        for (int next = start + 1; next < nums.length; next++) {
            if (nums[next] > nums[start]) {
                maxLength = Math.max(maxLength, 1 + findLIS(nums, next, memo));
            }
        }
        
        // Memoize and return
        memo[start] = maxLength;
        return maxLength;
    }
}
```

### Bottom-Up Approach
The bottom-up approach builds the solution iteratively, starting from smaller subproblems. It's generally more efficient than top-down for this problem because we avoid the overhead of recursion and can directly compute the final answer.

The time complexity is better in the bottom-up approach because we process each element exactly once in the outer loop and at most n times in the inner loop, giving us O(n²) without the overhead of function calls.

## Complexity Analysis
- **Time Complexity**: 
  - DP Approach: $O(n^2)$ where n is the length of the array
  - Binary Search Approach: $O(n \log n)$
- **Space Complexity**: $O(n)$ for both approaches

## Binary Search Explanations
- We use `left <= right` in the loop condition to ensure we find the exact insertion position, even when the array is empty.
- We set pointers to `mid + 1` or `mid - 1` to narrow down the search range, ensuring we don't get stuck in an infinite loop.
- The return value is `left` because after the binary search completes, `left` points to the position where the element should be inserted to maintain the sorted order.

## Similar Problems
- [354. Russian Doll Envelopes](https://leetcode.com/problems/russian-doll-envelopes/)
- [646. Maximum Length of Pair Chain](https://leetcode.com/problems/maximum-length-of-pair-chain/)
- [673. Number of Longest Increasing Subsequences](https://leetcode.com/problems/number-of-longest-increasing-subsequences/)
- [674. Longest Continuous Increasing Subsequence](https://leetcode.com/problems/longest-continuous-increasing-subsequence/)
- [1048. Longest String Chain](https://leetcode.com/problems/longest-string-chain/)
