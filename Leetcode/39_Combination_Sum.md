# Combination Sum - LeetCode 39

## Main Idea / Intuition

The problem asks for all unique combinations of numbers from a given array `candidates` that sum up to a `target`. We can use the same number multiple times. This problem is a classic example of a backtracking problem.

The core idea is to use a recursive approach (depth-first search) to explore all possible combinations. We build a combination step by step. At each step, we try to add a candidate number to our current combination. If the sum exceeds the target, we abandon that path. If the sum equals the target, we've found a valid combination and add it to our results. If the sum is less than the target, we continue building upon the current combination by making a recursive call.

To avoid duplicate combinations (like `[2,3]` and `[3,2]`), we only consider candidates from the current index onwards in our recursive calls. Since we can reuse the same number, when we make a recursive call with a chosen candidate, we pass the same index to allow it to be chosen again.

## Code

Here is the complete and corrected Java code for the Combination Sum problem.

```java
import java.util.ArrayList;
import java.util.List;

class Solution {
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        if (candidates == null || candidates.length == 0) {
            return result;
        }

        findCombinations(candidates, target, 0, new ArrayList<>(), result);
        return result;
    }

    private void findCombinations(int[] candidates, int target, int index, List<Integer> currentCombination, List<List<Integer>> result) {
        // Base case 1: A valid combination is found.
        if (target == 0) {
            result.add(new ArrayList<>(currentCombination));
            return;
        }

        // Base case 2: The current path is invalid.
        if (target < 0) {
            return;
        }

        // Explore possibilities.
        for (int i = index; i < candidates.length; i++) {
            // Choose the candidate.
            currentCombination.add(candidates[i]);
            
            // Explore further with the chosen candidate.
            // We pass 'i' as the next index because we can reuse the same element.
            findCombinations(candidates, target - candidates[i], i, currentCombination, result);
            
            // Backtrack: remove the candidate to explore other paths.
            currentCombination.remove(currentCombination.size() - 1);
        }
    }
}
```

## Mapping Requirements to Code

*   **Requirement**: Find all unique combinations that sum to `target`.
    *   **Code**: The `findCombinations` method recursively explores all paths. The final `result` list stores all valid combinations found.

*   **Requirement**: The same number may be chosen an unlimited number of times.
    *   **Code**: In the recursive call `findCombinations(..., i, ...)` inside the `for` loop, we pass the current index `i` instead of `i + 1`. This allows the element `candidates[i]` to be considered again in the next level of recursion.

*   **Requirement**: Two combinations are unique if the frequency of at least one number is different.
    *   **Code**: By starting the `for` loop at `int i = index`, we ensure that we only move forward in the `candidates` array. This prevents permutations from being treated as unique combinations (e.g., if we've considered `[2, 3]`, we won't later consider `[3, 2]` as a separate combination).

*   **Requirement**: The main recursive logic (backtracking).
    *   **Code**:
        1.  `currentCombination.add(candidates[i]);` - **Choose**: Add a candidate to the current path.
        2.  `findCombinations(...)` - **Explore**: Recursively call the function to find solutions based on the choice.
        3.  `currentCombination.remove(currentCombination.size() - 1);` - **Unchoose/Backtrack**: Remove the candidate to explore other branches of the recursion tree.

## Complexity Analysis

Let `N` be the number of candidates and `T` be the target value.

*   **Time Complexity**: `O(N^(T/M + 1))`, where `M` is the minimum value among the candidates. In the worst case, the recursion tree can go as deep as `T/M`. At each level, we branch out `N` times. This is a loose upper bound. A tighter analysis is complex, but it's exponential in nature due to the combinatorial possibilities.

*   **Space Complexity**: `O(T/M)`. The space complexity is determined by the maximum depth of the recursion stack. In the worst case, the smallest candidate `M` is used repeatedly to reach the target `T`, leading to a recursion depth of `T/M`.

```
