# Fair Distribution of Cookies

## Problem Description

**Problem Link:** [Fair Distribution of Cookies](https://leetcode.com/problems/fair-distribution-of-cookies/)

You are given an integer array `cookies`, where each `cookies[i]` denotes the number of cookies in the `i`th bag. You are also given an integer `k` that denotes the number of children to distribute all the bags of cookies to. All the cookies in the same bag must go to the same child and cannot be split up.

The **unfairness** of a distribution is defined as the **maximum** total cookies obtained by a single child in the distribution.

Return *the **minimum unfairness** of all distributions*.

**Example 1:**

```
Input: cookies = [8,15,10,20,8], k = 2
Output: 31
Explanation: We can distribute the cookies as follows:
- The 1st child receives [8,15,8] which has a total of 8 + 15 + 8 = 31 cookies.
- The 2nd child receives [10,20] which has a total of 10 + 20 = 30 cookies.
The unfairness of the distribution is max(31,30) = 31.
It can be shown that there is no distribution with an unfairness less than 31.
```

**Example 2:**

```
Input: cookies = [6,1,3,2,2,4,1,2], k = 3
Output: 7
Explanation: We can distribute the cookies as follows:
- The 1st child receives [1,2,2,2] which has a total of 1 + 2 + 2 + 2 = 7 cookies.
- The 2nd child receives [3,4] which has a total of 3 + 4 = 7 cookies.
- The 3rd child receives [6,1] which has a total of 6 + 1 = 7 cookies.
The unfairness of the distribution is max(7,7,7) = 7.
It can be shown that there is no distribution with an unfairness less than 7.
```

**Constraints:**
- `2 <= cookies.length <= 8`
- `1 <= cookies[i] <= 10^5`
- `2 <= k <= cookies.length`

## Intuition/Main Idea

This is a **backtracking** problem. We need to distribute cookies to `k` children to minimize the maximum sum.

**Core Algorithm:**
1. Use backtracking to assign each cookie bag to a child.
2. Track the sum for each child.
3. When all bags assigned, calculate unfairness (max sum).
4. Prune if current unfairness already exceeds best.

**Why backtracking works:** We systematically try all ways to assign cookie bags to children. Pruning improves efficiency by skipping paths that can't improve the result.

## Code Mapping

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Backtrack function | Backtrack method - Lines 6-22 |
| Base case: all assigned | Index check - Lines 8-12 |
| Pruning | Pruning check - Line 15 |
| Try each child | Child loop - Line 18 |
| Assign cookie | Assignment - Line 19 |
| Recurse | Recursive call - Line 20 |
| Backtrack | Remove assignment - Line 21 |
| Return result | Return statement - Line 24 |

## Final Java Code & Learning Pattern

```java
class Solution {
    private int minUnfairness = Integer.MAX_VALUE;
    
    public int distributeCookies(int[] cookies, int k) {
        int[] children = new int[k];
        backtrack(cookies, 0, children);
        return minUnfairness;
    }
    
    private void backtrack(int[] cookies, int index, int[] children) {
        // Base case: all cookies assigned
        if (index == cookies.length) {
            int max = 0;
            for (int sum : children) {
                max = Math.max(max, sum);
            }
            minUnfairness = Math.min(minUnfairness, max);
            return;
        }
        
        // Pruning: if current unfairness already exceeds best, skip
        int currentMax = 0;
        for (int sum : children) {
            currentMax = Math.max(currentMax, sum);
        }
        if (currentMax >= minUnfairness) {
            return;
        }
        
        // Try assigning current cookie to each child
        for (int i = 0; i < children.length; i++) {
            children[i] += cookies[index];
            backtrack(cookies, index + 1, children);
            children[i] -= cookies[index]; // Backtrack
        }
    }
}
```

**Explanation of Key Code Sections:**

1. **Base Case (Lines 8-12):** When all cookies assigned, calculate unfairness (max sum) and update minimum.

2. **Pruning (Lines 15-19):** If current unfairness already exceeds best, skip this path.

3. **Try Children (Lines 21-25):** For each child:
   - **Assign (Line 22):** Add cookie to child's sum.
   - **Recurse (Line 23):** Try next cookie.
   - **Backtrack (Line 24):** Remove cookie to try next child.

**Why backtracking:**
- We need to explore all assignment possibilities.
- After trying an assignment, we backtrack to try others.
- Pruning improves efficiency.

**Example walkthrough for `cookies = [8,15,10,20,8], k = 2`:**
- Try assigning 8 to child 0: [8,0] → assign 15 to child 0: [23,0] → ...
- Continue exploring all assignments.
- Find optimal: child 0 gets [8,15,8]=31, child 1 gets [10,20]=30 → unfairness=31 ✓

## Complexity Analysis

- **Time Complexity:** $O(k^n)$ where $n$ is the number of cookie bags. We try $k$ children for each bag.

- **Space Complexity:** $O(k)$ for children array and $O(n)$ for recursion stack.

## Similar Problems

Problems that can be solved using similar backtracking patterns:

1. **2305. Fair Distribution of Cookies** (this problem) - Backtracking distribution
2. **698. Partition to K Equal Sum Subsets** - Backtracking partition
3. **473. Matchsticks to Square** - Backtracking partition
4. **638. Shopping Offers** - Backtracking with memoization
5. **1255. Maximum Score Words Formed by Letters** - Backtracking selection
6. **1947. Maximum Compatibility Score Sum** - Backtracking assignment
7. **526. Beautiful Arrangement** - Backtracking placement
8. **980. Unique Paths III** - Backtracking paths

