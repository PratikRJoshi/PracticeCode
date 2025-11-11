# Combination Sum III

## Problem Description

**Problem Link:** [Combination Sum III](https://leetcode.com/problems/combination-sum-iii/)

Find all valid combinations of `k` numbers that sum up to `n` such that the following conditions are true:

- Only numbers `1` through `9` are used.
- Each number is used **at most once**.

Return *a list of all possible valid combinations*. The list must not contain the same combination twice, and the combinations may be returned in any order.

**Example 1:**

```
Input: k = 3, n = 7
Output: [[1,2,4]]
Explanation:
1 + 2 + 4 = 7
There are no other valid combinations.
```

**Example 2:**

```
Input: k = 3, n = 9
Output: [[1,2,6],[1,3,5],[2,3,4]]
Explanation:
1 + 2 + 6 = 9
1 + 3 + 5 = 9
2 + 3 + 4 = 9
There are no other valid combinations.
```

**Example 3:**

```
Input: k = 4, n = 1
Output: []
Explanation: There are no valid combinations.
```

**Constraints:**
- `2 <= k <= 9`
- `1 <= n <= 60`

## Intuition/Main Idea

This is a **backtracking** problem. We need to find combinations of `k` numbers from `[1,9]` that sum to `n`.

**Core Algorithm:**
1. Use backtracking to build combinations.
2. Try numbers from `start` to `9`.
3. When combination size is `k` and sum is `n`, add to result.
4. Prune early if sum exceeds `n` or remaining numbers can't reach target.

**Why backtracking works:** We systematically explore all possible combinations, pruning invalid paths early to improve efficiency.

## Code Mapping

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Backtrack function | Backtrack method - Lines 6-25 |
| Base case: size k and sum n | Condition check - Lines 8-12 |
| Pruning: sum too large | Pruning check - Line 15 |
| Try each number | For loop - Line 17 |
| Add to combination | Add operation - Line 19 |
| Recurse | Recursive call - Line 20 |
| Backtrack | Remove operation - Line 21 |
| Return result | Return statement - Line 27 |

## Final Java Code & Learning Pattern

```java
import java.util.*;

class Solution {
    public List<List<Integer>> combinationSum3(int k, int n) {
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> current = new ArrayList<>();
        backtrack(k, n, 1, 0, current, result);
        return result;
    }
    
    private void backtrack(int k, int n, int start, int sum, List<Integer> current, List<List<Integer>> result) {
        // Base case: combination size is k and sum is n
        if (current.size() == k && sum == n) {
            result.add(new ArrayList<>(current));
            return;
        }
        
        // Pruning: if sum already exceeds n or we can't form k numbers
        if (sum > n || current.size() > k) {
            return;
        }
        
        // Try each number from start to 9
        for (int i = start; i <= 9; i++) {
            current.add(i);
            backtrack(k, n, i + 1, sum + i, current, result);
            current.remove(current.size() - 1); // Backtrack
        }
    }
}
```

**Explanation of Key Code Sections:**

1. **Base Case (Lines 8-12):** When combination size is `k` and sum is `n`, add to result.

2. **Pruning (Lines 15-17):** Early return if sum exceeds `n` or size exceeds `k`.

3. **Try Numbers (Lines 19-23):** For each number `i` from `start` to `9`:
   - **Add (Line 20):** Add `i` to combination and update sum.
   - **Recurse (Line 21):** Recursively build combination starting from `i+1`.
   - **Backtrack (Line 22):** Remove `i` to try next number.

**Why backtracking:**
- We need to explore all valid combinations.
- Pruning improves efficiency by skipping invalid paths.
- Backtracking ensures we try all possibilities.

**Example walkthrough for `k=3, n=7`:**
- Start: [], sum=0
- Try 1: [1], sum=1 → Try 2: [1,2], sum=3 → Try 3: [1,2,3], sum=6 → Try 4: [1,2,4], sum=7 ✓
- Result: [[1,2,4]] ✓

## Complexity Analysis

- **Time Complexity:** $O(C(9,k) \times k)$ where we generate combinations and each takes $O(k)$ time.

- **Space Complexity:** $O(k)$ for recursion stack and current combination.

## Similar Problems

Problems that can be solved using similar backtracking patterns:

1. **216. Combination Sum III** (this problem) - Combinations with size and sum
2. **39. Combination Sum** - Combinations with sum (reuse allowed)
3. **40. Combination Sum II** - With duplicates
4. **77. Combinations** - Generate combinations
5. **78. Subsets** - Generate subsets
6. **90. Subsets II** - With duplicates
7. **46. Permutations** - Generate permutations
8. **47. Permutations II** - With duplicates

