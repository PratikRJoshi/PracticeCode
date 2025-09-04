### 40. Combination Sum II
Problem: https://leetcode.com/problems/combination-sum-ii/

---

Below is a compact mapping from **problem requirements → code** followed by a corrected backtracking (DFS) Java solution.

## Main Idea / Intuition

This is a classic backtracking problem where we need to find all unique combinations that sum to a target. The core idea is a **"choose/explore/unchoose"** pattern:

1.  **Sort:** First, sort the `candidates` array. This is crucial for efficiently handling duplicates.
2.  **Choose:** Pick a number from the candidates and add it to your current combination list.
3.  **Explore:** Recursively call the function with the updated target (target - chosen number) and the next starting position.
4.  **Unchoose:** After the recursive call returns, remove the number you just added. This is the **backtracking** step, which allows you to explore other combinations.

**Key Constraints & How to Handle Them:**
*   **Each number may only be used once:** In the recursive call, we pass `i + 1` as the next starting index, ensuring we can't reuse the same element.
*   **The solution set must not contain duplicate combinations:** After sorting, we add a check: `if (i > index && candidates[i] == candidates[i - 1]) continue;`. This line ensures that for a set of duplicate numbers, we only start a new path with the *first* one. For example, with `[1, 2, 2, 2, 5]` and target 8, we try `[1, 2, 5]` once, not three times.

---

## Full Corrected Java Code

```java
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Solution {
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        if (candidates == null || candidates.length == 0 || target < 0) {
            return result;
        }

        Arrays.sort(candidates);
        findCombinationSum(candidates, target, 0, new ArrayList<>(), result);
        return result;
    }

    private void findCombinationSum(int[] candidates, int target, int index, List<Integer> temp, List<List<Integer>> result) {
        // Base Case: A valid combination is found
        if (target == 0) {
            result.add(new ArrayList<>(temp));
            return;
        }

        for (int i = index; i < candidates.length; i++) {
            // Pruning: If the current candidate is too large, stop.
            if (target - candidates[i] < 0) {
                break;
            }
            
            // Skip duplicates to avoid duplicate combinations
            if (i > index && candidates[i] == candidates[i - 1]) {
                continue;
            }

            // 1. Choose
            temp.add(candidates[i]);
            // 2. Explore (pass the same temp list, not a new one)
            findCombinationSum(candidates, target - candidates[i], i + 1, temp, result);
            // 3. Unchoose (backtrack)
            temp.remove(temp.size() - 1);
        }
    }
}
```

---

## Mapping of Requirements → Code

*   **Find all unique combinations:** The backtracking structure explores all paths. The duplicate-skipping logic (`if (i > index && ...`) ensures combinations are unique.
*   **Sum to `target`:** The `target == 0` base case identifies a valid combination. The `target - candidates[i]` logic reduces the target at each step.
*   **Each number used at most once:** The recursive call uses `i + 1` as the next index, preventing reuse of the same element in a single combination path.
*   **No duplicate combinations:** `Arrays.sort()` (line 11) and the `if (i > index && candidates[i] == candidates[i-1])` check (line 28) work together to prevent this.

---

## Complexity

*   **Time Complexity:** `O(2^n)`. In the worst case, we have to explore every possible subset. The sorting takes `O(n log n)`, but the exponential backtracking dominates.
*   **Space Complexity:** `O(n)` or `O(target)`. The depth of the recursion stack can go up to `n` (if all numbers are 1) or `target` (if the smallest number is 1). The space for the `result` list is not counted in the auxiliary space complexity.

---