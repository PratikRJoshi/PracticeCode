# Delete and Earn

## Problem Description

**Problem Link:** [Delete and Earn](https://leetcode.com/problems/delete-and-earn/)

You are given an integer array `nums`. You want to maximize the number of points you get by performing the following operation any number of times:

- Pick any `nums[i]` and delete it to earn `nums[i]` points. Afterwards, you must delete **every** element equal to `nums[i] - 1` and `nums[i] + 1`.

Return *the maximum number of points you can earn by applying the above operation some number of times*.

**Example 1:**
```
Input: nums = [3,4,2]
Output: 6
Explanation: You can perform the following operations:
- Delete 4 to earn 4 points. Consequently, 3 is also deleted. nums = [2].
- Delete 2 to earn 2 points. nums = [].
You earn a total of 6 points.
```

**Example 2:**
```
Input: nums = [2,2,3,3,3,4]
Output: 9
Explanation: You can perform the following operations:
- Delete 3 to earn 3 points. All 3's and 4's are deleted. nums = [2,2].
- Delete 2 to earn 2 points. nums = [].
You earn a total of 9 points.
```

**Constraints:**
- `1 <= nums.length <= 2 * 10^4`
- `1 <= nums[i] <= 10^4`

## Intuition/Main Idea

This problem is similar to **House Robber**. When you delete a number, you can't delete its neighbors (num-1 and num+1). We can transform it into a House Robber problem.

**Core Algorithm:**
1. Count frequency of each number and calculate total points for each number.
2. Transform into House Robber: for each number, decide whether to "rob" (delete) it or not.
3. If we delete number `i`, we get `points[i]` but can't delete `i-1` or `i+1`.
4. Use DP similar to House Robber.

**Why this works:** The constraint that deleting `i` prevents deleting `i-1` and `i+1` is exactly like House Robber where robbing a house prevents robbing adjacent houses.

## Code Mapping

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Count frequencies | Frequency array - Lines 7-10 |
| Calculate points per number | Points calculation - Line 12 |
| DP for maximum points | DP array - Line 14 |
| Base cases | Initial values - Lines 15-16 |
| DP transition | House Robber logic - Lines 18-21 |
| Return result | Return statement - Line 22 |

## Final Java Code & Learning Pattern

### Top-Down / Memoized Version

```java
class Solution {
    public int deleteAndEarn(int[] nums) {
        // Find maximum value to determine array size
        int maxNum = 0;
        for (int num : nums) {
            maxNum = Math.max(maxNum, num);
        }
        
        // Count frequency and calculate total points for each number
        int[] points = new int[maxNum + 1];
        for (int num : nums) {
            points[num] += num;
        }
        
        // Now it's House Robber: can't take adjacent numbers
        // DP: dp[i] = max points using numbers 1..i
        int[] dp = new int[maxNum + 1];
        dp[1] = points[1];
        
        for (int i = 2; i <= maxNum; i++) {
            // Option 1: Take i, can't take i-1
            // Option 2: Don't take i, can take i-1
            dp[i] = Math.max(dp[i - 1], dp[i - 2] + points[i]);
        }
        
        return dp[maxNum];
    }
}
```

**Space-Optimized Version:**

```java
class Solution {
    public int deleteAndEarn(int[] nums) {
        int maxNum = 0;
        for (int num : nums) {
            maxNum = Math.max(maxNum, num);
        }
        
        int[] points = new int[maxNum + 1];
        for (int num : nums) {
            points[num] += num;
        }
        
        // Space-optimized: only need last two values
        int prev2 = 0;
        int prev1 = points[1];
        
        for (int i = 2; i <= maxNum; i++) {
            int current = Math.max(prev1, prev2 + points[i]);
            prev2 = prev1;
            prev1 = current;
        }
        
        return prev1;
    }
}
```

**Explanation of Key Code Sections:**

1. **Find Maximum (Lines 5-8):** Find the maximum number to determine the size of our points array.

2. **Calculate Points (Lines 10-13):** For each number, sum all occurrences. `points[i]` = total points from deleting all instances of `i`.

3. **House Robber DP (Lines 15-21):** 
   - **Base Case (Line 16):** `dp[1] = points[1]`.
   - **Transition (Line 20):** For each number `i`:
     - Don't take `i`: `dp[i-1]` (can take `i-1`)
     - Take `i`: `dp[i-2] + points[i]` (can't take `i-1`)

4. **Space Optimization (Lines 25-30):** Only need the last two values, similar to House Robber optimization.

**Intuition behind generating subproblems:**
- **Subproblem:** "What is the maximum points using numbers 1..i?"
- **Why this works:** When considering number `i`, we can either take it (get `points[i]` but skip `i-1`) or skip it (can take `i-1`). This is exactly the House Robber pattern.
- **Overlapping subproblems:** Multiple numbers share the same optimal subproblems.

**Example walkthrough for `nums = [2,2,3,3,3,4]`:**
- Points: [0, 0, 4, 9, 4] (0 points for 0,1; 2+2=4 for 2; 3+3+3=9 for 3; 4 for 4)
- dp[1] = 0
- dp[2] = max(0, 0+4) = 4
- dp[3] = max(4, 0+9) = 9
- dp[4] = max(9, 4+4) = 9
- Result: 9 ✓

## Complexity Analysis

- **Time Complexity:** $O(n + m)$ where $n$ is the length of `nums` and $m$ is the maximum number. We iterate through `nums` once and through numbers 1..m once.

- **Space Complexity:** $O(m)$ for the points array. Can be optimized to $O(1)$ using variables.

## Similar Problems

Problems that can be solved using similar House Robber DP patterns:

1. **740. Delete and Earn** (this problem) - House Robber variant
2. **198. House Robber** - Classic House Robber
3. **213. House Robber II** - Circular variant
4. **337. House Robber III** - Tree variant
5. **1388. Pizza With 3n Slices** - Circular House Robber variant
6. **2320. Count Number of Ways to Place Houses** - Counting variant

