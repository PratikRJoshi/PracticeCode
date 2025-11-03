### 78. Subsets
Problem: https://leetcode.com/problems/subsets/

---

Below is a compact mapping from **problem requirements → code** followed by a standard backtracking (DFS) Java solution.

## Main Idea / Intuition

This problem asks for the "power set" — all possible subsets of a given set of numbers. The core idea for generating them is to think about each number one by one and make a decision:

**For each number, you have two choices:**
1.  **Include** it in the current subset.
2.  **Do not include** it in the current subset.

This binary choice structure naturally leads to a recursive backtracking solution. We can build subsets of increasing size. At each step of the recursion, we add the current subset to our results and then explore further by adding subsequent elements.

The **"choose/explore/unchoose"** pattern is perfect here:
1.  **Add** the current subset to the results.
2.  **Choose:** Iterate through the remaining numbers. Pick one and add it to the current subset.
3.  **Explore:** Recursively call the function to build upon this new subset, starting from the next number.
4.  **Unchoose:** After the recursion returns, remove the number you just added (backtrack) to explore paths where that number wasn't chosen.

---

## Full Java Code (Backtracking)

```java
import java.util.ArrayList;
import java.util.List;

class Solution {
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        if (nums == null || nums.length == 0) {
            result.add(new ArrayList<>()); // Handle empty input, should return [[]]
            return result;
        }
        
        generateSubsets(nums, 0, new ArrayList<>(), result);
        return result;
    }

    private void generateSubsets(int[] nums, int index, List<Integer> current, List<List<Integer>> result) {
        // Add the subset formed so far to the result list
        result.add(new ArrayList<>(current));

        // Explore further options by adding one of the remaining elements
        for (int i = index; i < nums.length; i++) {
            // 1. Choose the element
            current.add(nums[i]);
            
            // 2. Explore with this element included
            generateSubsets(nums, i + 1, current, result);
            
            // 3. Unchoose the element (backtrack)
            current.remove(current.size() - 1);
        }
    }
}
```

---

## Mapping of Requirements → Code

*   **Return all possible subsets:** The recursive structure ensures all paths (all combinations of choices) are explored. Adding `result.add(new ArrayList<>(current))` at the *start* of the recursive call ensures that subsets of all sizes (including the empty one) are captured.
*   **Elements in a subset must be in non-descending order:** By processing the input array from left to right (`for (int i = index; ...`) and passing `i + 1` in the recursion, we guarantee that we never consider an element that appeared earlier in the array. This naturally produces sorted subsets.
*   **The solution set must not contain duplicate subsets:** Since the input `nums` contains unique elements, this backtracking approach will not generate duplicate subsets.

---

## Complexity

*   **Time Complexity:** `O(n * 2^n)`. There are `2^n` possible subsets. For each subset, it can take up to `O(n)` time to create a copy of it to add to the result list.
*   **Space Complexity:** `O(n)`. The primary space usage comes from the recursion stack, which can go up to `n` levels deep.

---