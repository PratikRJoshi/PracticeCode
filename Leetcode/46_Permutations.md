### 46. Permutations
### Problem Link: [Permutations](https://leetcode.com/problems/permutations/)
### Intuition
This problem asks us to generate all possible permutations of a given array of distinct integers. A permutation is an arrangement of all elements in different orders.

The key insight is to use backtracking, a systematic way to generate all possible combinations by exploring all potential candidates and abandoning a candidate (backtracking) as soon as we determine it cannot lead to a valid solution. For permutations, we can:
1. Fix one position at a time
2. Try all possible elements for that position
3. Recursively generate permutations for the remaining positions

### Java Reference Implementation (Backtracking with Swapping)
```java
import java.util.ArrayList;
import java.util.List;

class Solution {
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        backtrack(nums, 0, result);
        return result;
    }
    
    private void backtrack(int[] nums, int start, List<List<Integer>> result) {
        // Base case: If we've fixed all positions, add the permutation to the result
        if (start == nums.length) {
            List<Integer> permutation = new ArrayList<>();
            for (int num : nums) {
                permutation.add(num);
            }
            result.add(permutation);
            return;
        }
        
        // Try each possible element for the current position
        for (int i = start; i < nums.length; i++) {
            // Swap elements to place nums[i] at the current position
            swap(nums, start, i);
            
            // Recursively generate permutations for the next position
            backtrack(nums, start + 1, result);
            
            // Backtrack: restore the array to its original state
            swap(nums, start, i);
        }
    }
    
    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
```

### Alternative Implementation (Using a Visited Array)
```java
import java.util.ArrayList;
import java.util.List;

class Solution {
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        boolean[] visited = new boolean[nums.length];
        backtrack(nums, visited, new ArrayList<>(), result);
        return result;
    }
    
    private void backtrack(int[] nums, boolean[] visited, List<Integer> current, List<List<Integer>> result) {
        // Base case: If the current permutation is complete
        if (current.size() == nums.length) {
            result.add(new ArrayList<>(current));
            return;
        }
        
        // Try each number that hasn't been used yet
        for (int i = 0; i < nums.length; i++) {
            if (visited[i]) {
                continue;
            }
            
            // Use this number in the current position
            visited[i] = true;
            current.add(nums[i]);
            
            // Recursively generate permutations for the next position
            backtrack(nums, visited, current, result);
            
            // Backtrack: remove the number and mark it as unvisited
            current.remove(current.size() - 1);
            visited[i] = false;
        }
    }
}
```

### Requirement → Code Mapping
- **R0 (Generate all permutations)**: Use backtracking to systematically explore all possible arrangements
- **R1 (Base case)**: When all positions are fixed, add the current permutation to the result
- **R2 (Try all possibilities)**: For each position, try all available elements
- **R3 (Backtracking)**: After exploring a path, restore the state to try other paths
- **R4 (Swapping approach)**: Use swapping to place different elements at the current position

### Example Walkthrough
For the array `[1, 2, 3]`:

1. Start with position 0:
   - Place 1 at position 0, then recursively generate permutations for positions 1 and 2
     - Place 2 at position 1, then recursively generate permutations for position 2
       - Place 3 at position 2 → [1, 2, 3]
     - Place 3 at position 1, then recursively generate permutations for position 2
       - Place 2 at position 2 → [1, 3, 2]
   - Place 2 at position 0, then recursively generate permutations for positions 1 and 2
     - Place 1 at position 1, then recursively generate permutations for position 2
       - Place 3 at position 2 → [2, 1, 3]
     - Place 3 at position 1, then recursively generate permutations for position 2
       - Place 1 at position 2 → [2, 3, 1]
   - Place 3 at position 0, then recursively generate permutations for positions 1 and 2
     - Place 1 at position 1, then recursively generate permutations for position 2
       - Place 2 at position 2 → [3, 1, 2]
     - Place 2 at position 1, then recursively generate permutations for position 2
       - Place 1 at position 2 → [3, 2, 1]

### Complexity Analysis
- **Time Complexity**: O(n!) - There are n! permutations, and we generate all of them
- **Space Complexity**: O(n) - The recursion stack can go up to a depth of n, and we use O(n) space to store each permutation
