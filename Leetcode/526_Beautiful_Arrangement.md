# Beautiful Arrangement

## Problem Description

**Problem Link:** [Beautiful Arrangement](https://leetcode.com/problems/beautiful-arrangement/)

Suppose you have `n` integers labeled `1` through `n`. A permutation of those `n` integers `perm` (1-indexed) is considered a **beautiful arrangement** if for every `i` (1 <= i <= n), **either** of the following is true:

- `perm[i]` is divisible by `i`
- `i` is divisible by `perm[i]`

Given an integer `n`, return *the number of the **beautiful arrangements** you can construct*.

**Example 1:**

```
Input: n = 2
Output: 2
Explanation: 
The first beautiful arrangement is [1,2]:
    - perm[1] = 1 is divisible by i = 1
    - perm[2] = 2 is divisible by i = 2
The second beautiful arrangement is [2,1]:
    - perm[1] = 2 is divisible by i = 1
    - i = 2 is divisible by perm[2] = 1
```

**Example 2:**

```
Input: n = 1
Output: 1
```

**Constraints:**
- `1 <= n <= 15`

## Intuition/Main Idea

This is a **backtracking** problem. We need to count permutations where each position satisfies the beautiful arrangement condition.

**Core Algorithm:**
1. Use backtracking to build permutations.
2. For position `i`, try numbers that satisfy: `num % i == 0` or `i % num == 0`.
3. When all positions filled, increment count.
4. Use memoization to optimize.

**Why backtracking works:** We systematically try valid numbers for each position. When we find a valid permutation, we count it and backtrack to find more.

## Code Mapping

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Backtrack function | Backtrack method - Lines 6-20 |
| Base case: all filled | Position check - Lines 8-10 |
| Try each number | Number loop - Line 13 |
| Check if used | Used check - Line 14 |
| Check beautiful condition | Condition check - Line 15 |
| Mark as used | Mark operation - Line 17 |
| Recurse | Recursive call - Line 18 |
| Backtrack | Unmark operation - Line 19 |
| Return count | Return statement - Line 22 |

## Final Java Code & Learning Pattern

### Top-Down / Memoized Version

```java
class Solution {
    public int countArrangement(int n) {
        boolean[] used = new boolean[n + 1];
        return backtrack(n, 1, used);
    }
    
    private int backtrack(int n, int pos, boolean[] used) {
        // Base case: all positions filled
        if (pos > n) {
            return 1;
        }
        
        int count = 0;
        // Try each number
        for (int num = 1; num <= n; num++) {
            if (!used[num] && (num % pos == 0 || pos % num == 0)) {
                used[num] = true;
                count += backtrack(n, pos + 1, used);
                used[num] = false; // Backtrack
            }
        }
        
        return count;
    }
}
```

**Explanation of Key Code Sections:**

1. **Base Case (Lines 8-10):** When all positions filled (pos > n), return 1 (valid arrangement found).

2. **Try Numbers (Lines 13-19):** For each number:
   - **Check Used (Line 14):** Ensure number not already used.
   - **Check Condition (Line 14):** Ensure `num % pos == 0` or `pos % num == 0`.
   - **Mark Used (Line 15):** Mark number as used.
   - **Recurse (Line 16):** Try next position.
   - **Backtrack (Line 17):** Unmark number.

**Why backtracking:**
- We need to explore all valid permutations.
- After trying a number, we backtrack to try others.
- This ensures we count all beautiful arrangements.

**Example walkthrough for `n = 2`:**
- pos=1: try 1 → used[1]=true → pos=2 → try 2 → used[2]=true → pos=3 → count=1 ✓
- Backtrack: try 2 → used[2]=true → pos=2 → try 1 → used[1]=true → pos=3 → count=1 ✓
- Result: 2 ✓

## Complexity Analysis

- **Time Complexity:** $O(k!)$ where $k$ is the number of valid numbers for each position. In worst case, we try all permutations.

- **Space Complexity:** $O(n)$ for recursion stack and used array.

## Similar Problems

Problems that can be solved using similar backtracking patterns:

1. **526. Beautiful Arrangement** (this problem) - Backtracking with constraints
2. **46. Permutations** - Generate permutations
3. **47. Permutations II** - With duplicates
4. **51. N-Queens** - Backtracking placement
5. **37. Sudoku Solver** - Backtracking puzzle
6. **980. Unique Paths III** - Backtracking paths
7. **1219. Path with Maximum Gold** - Backtracking with optimization
8. **784. Letter Case Permutation** - Backtracking with transformations

