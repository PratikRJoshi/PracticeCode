# Combinations

## Problem Description

**Problem Link:** [Combinations](https://leetcode.com/problems/combinations/)

Given two integers `n` and `k`, return *all possible combinations of `k` numbers chosen from the range `[1, n]`*.

You may return the answer in **any order**.

**Example 1:**

```
Input: n = 4, k = 2
Output: [[1,2],[1,3],[1,4],[2,3],[2,4],[3,4]]
Explanation: There are 4 choose 2 = 6 total combinations.
Note that combinations are unordered, i.e., [1,2] and [2,1] are considered to be the same combination.
```

**Example 2:**

```
Input: n = 1, k = 1
Output: [[1]]
```

**Constraints:**
- `1 <= n <= 20`
- `1 <= k <= n`

## Intuition/Main Idea

This is a **backtracking** problem. We need to generate all combinations of `k` numbers from `[1, n]`.

**Core Algorithm:**
1. Use backtracking to build combinations.
2. For each position, try numbers from `start` to `n`.
3. When combination size reaches `k`, add to result.
4. Backtrack by removing the last added number.

**Why backtracking works:** We systematically explore all possible combinations by trying each number at each position and backtracking when we've explored all possibilities.

## Code Mapping

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Backtrack function | Backtrack method - Lines 6-20 |
| Base case: size k | Size check - Lines 8-11 |
| Try each number | For loop - Line 13 |
| Add to combination | Add operation - Line 15 |
| Recurse | Recursive call - Line 16 |
| Backtrack | Remove operation - Line 17 |
| Return result | Return statement - Line 22 |

## Final Java Code & Learning Pattern

```java
import java.util.*;

class Solution {
    public List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> current = new ArrayList<>();
        backtrack(n, k, 1, current, result);
        return result;
    }
    
    private void backtrack(int n, int k, int start, List<Integer> current, List<List<Integer>> result) {
        // Base case: combination size is k
        if (current.size() == k) {
            result.add(new ArrayList<>(current));
            return;
        }
        
        // Try each number from start to n
        for (int i = start; i <= n; i++) {
            current.add(i);
            backtrack(n, k, i + 1, current, result);
            current.remove(current.size() - 1); // Backtrack
        }
    }
}
```

**Explanation of Key Code Sections:**

1. **Base Case (Lines 8-11):** When combination size reaches `k`, add a copy to result and return.

2. **Try Numbers (Lines 13-17):** For each number `i` from `start` to `n`:
   - **Add (Line 15):** Add `i` to current combination.
   - **Recurse (Line 16):** Recursively build combination starting from `i+1` (to avoid duplicates).
   - **Backtrack (Line 17):** Remove `i` to try next number.

**Why backtracking:**
- We need to explore all possibilities systematically.
- After trying a number, we remove it to try the next one.
- This ensures we generate all combinations without duplicates.

**Example walkthrough for `n=4, k=2`:**
- Start: []
- Try 1: [1] → Try 2: [1,2] ✓ → Backtrack → Try 3: [1,3] ✓ → Backtrack → Try 4: [1,4] ✓
- Backtrack: [] → Try 2: [2] → Try 3: [2,3] ✓ → Try 4: [2,4] ✓
- Continue...
- Result: [[1,2],[1,3],[1,4],[2,3],[2,4],[3,4]] ✓

## Complexity Analysis

- **Time Complexity:** $O(C(n,k) \times k)$ where $C(n,k)$ is the number of combinations. We generate all combinations and each takes $O(k)$ time.

- **Space Complexity:** $O(k)$ for recursion stack and current combination.

## Similar Problems

Problems that can be solved using similar backtracking patterns:

1. **77. Combinations** (this problem) - Generate combinations
2. **78. Subsets** - Generate all subsets
3. **90. Subsets II** - Subsets with duplicates
4. **39. Combination Sum** - Combinations with sum
5. **40. Combination Sum II** - With duplicates
6. **216. Combination Sum III** - With size constraint
7. **46. Permutations** - Generate permutations
8. **47. Permutations II** - With duplicates

