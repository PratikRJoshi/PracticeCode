### 90. Subsets II
Problem: https://leetcode.com/problems/subsets-ii/

---

This solution uses the exact same backtracking pattern as **Subsets I (LC78)**, with one key addition to handle duplicate numbers.

## Main Idea / Intuition

The core idea is to build subsets by making a decision for each number: either include it or don't. This is solved with the same **"choose/explore/unchoose"** pattern from Subsets I.

**The Challenge:** The input array can have duplicates (e.g., `[1, 2, 2]`). A naive backtracking approach would produce duplicate subsets: `[1, 2]` (from the first 2) and another `[1, 2]` (from the second 2).

**The Solution:**
1.  **Sort the Array:** First, sort the input `nums`. This places all duplicate numbers next to each other.
2.  **Add a Skip Condition:** In the loop where you choose the next number, add a rule: *if the current number is the same as the previous one, and you are not at the start of the loop, skip it.* This ensures that for a group of identical numbers, you only start a new recursive path with the very first one.

---

## Full Java Code (Backtracking with Duplicate Handling)

```java
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Solution {
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        if (nums == null || nums.length == 0) {
            result.add(new ArrayList<>());
            return result;
        }

        // Sort the array to handle duplicates effectively
        Arrays.sort(nums);
        generateSubsets(nums, 0, new ArrayList<>(), result);
        return result;
    }

    private void generateSubsets(int[] nums, int index, List<Integer> current, List<List<Integer>> result) {
        // Add the subset formed so far (e.g., [], [1], [1,2], etc.)
        result.add(new ArrayList<>(current));

        for (int i = index; i < nums.length; i++) {
            // **The key change from Subsets I**
            // Skip duplicates to avoid creating identical subsets.
            // We only proceed with nums[i] if it's the first element in this loop (i == index)
            // or if it's different from the previous element.
            if (i > index && nums[i] == nums[i - 1]) {
                continue;
            }

            // 1. Choose
            current.add(nums[i]);
            // 2. Explore
            generateSubsets(nums, i + 1, current, result);
            // 3. Unchoose (backtrack)
            current.remove(current.size() - 1);
        }
    }
}

---

## Mapping of Requirements → Code

*   **Return all unique subsets:** This is achieved by the combination of sorting and the duplicate-skipping logic.
    *   `Arrays.sort(nums);` (line 11): Groups duplicates together.
    *   `if (i > index && nums[i] == nums[i - 1])` (line 22): Prevents starting a new path with a duplicate number, thus avoiding duplicate subsets.
*   **Backtracking Structure:** The `generateSubsets` method follows the identical choose/explore/unchoose pattern as in Subsets I.

---

## Complexity

*   **Time Complexity:** `O(n * 2^n)`. We generate `2^n` subsets, and each can take up to `O(n)` to copy. The `O(n log n)` sort is dominated by the exponential part.
*   **Space Complexity:** `O(n)` for the recursion stack depth.

---
