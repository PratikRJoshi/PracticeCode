### 46. Permutations
### Problem Link: [Permutations](https://leetcode.com/problems/permutations/)
### Intuition
This problem asks us to generate all possible permutations of a given array of distinct integers. A permutation is an arrangement of all elements in different orders.

The key insight is to use backtracking, a systematic way to generate all possible combinations by exploring all potential candidates and abandoning a candidate (backtracking) as soon as we determine it cannot lead to a valid solution. For permutations, we can:
1. Build permutations one element at a time
2. For each position, try all unused elements
3. Use a temporary list to track the current permutation being built
4. Use a contains check to avoid using the same element twice

### Java Implementation (Backtracking with Contains Check)
```java
class Solution {
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        if(nums == null || nums.length == 0) {
            return result;
        }

        backtrack(nums, new ArrayList<>(), result); // no need for 'start' param since all elements have to be added for generating 1 permutation
        return result;
    }

    private void backtrack(int[] nums,  List<Integer> temp, List<List<Integer>> result) {
        if(temp.size() == nums.length) { // only when all num elements are added to temp in some order
            result.add(new ArrayList<>(temp));
            return;
        }

        for(int i = 0; i < nums.length; i++) {
            if(temp.contains(nums[i])) { // this line helps to pick the next correct element to put in the permutation
                continue;
            }

            temp.add(nums[i]);
            backtrack(nums, temp, result);
            temp.remove(temp.size() - 1);
        }
    }
}
```

### Alternative Implementation (Using a Visited Array)
For better performance, we can use a boolean array to track visited elements instead of using the contains method:

```java
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
- **R1 (Base case)**: When the temporary list size equals the input array length, add the current permutation to the result
- **R2 (Try all possibilities)**: For each position, try all available elements that haven't been used yet
- **R3 (Avoid duplicates)**: Skip elements that are already in the current permutation using contains check or visited array
- **R4 (Backtracking)**: After exploring a path, remove the last element to try other paths

### Example Walkthrough
For the array `[1, 2, 3]`:

1. Start with an empty list `[]`:
   - Try adding 1: `[1]`
     - Try adding 2: `[1, 2]`
       - Try adding 3: `[1, 2, 3]` (Complete permutation, add to result)
     - Backtrack to `[1]`
     - Try adding 3: `[1, 3]`
       - Try adding 2: `[1, 3, 2]` (Complete permutation, add to result)
   - Backtrack to `[]`
   - Try adding 2: `[2]`
     - Try adding 1: `[2, 1]`
       - Try adding 3: `[2, 1, 3]` (Complete permutation, add to result)
     - Backtrack to `[2]`
     - Try adding 3: `[2, 3]`
       - Try adding 1: `[2, 3, 1]` (Complete permutation, add to result)
   - Backtrack to `[]`
   - Try adding 3: `[3]`
     - Try adding 1: `[3, 1]`
       - Try adding 2: `[3, 1, 2]` (Complete permutation, add to result)
     - Backtrack to `[3]`
     - Try adding 2: `[3, 2]`
       - Try adding 1: `[3, 2, 1]` (Complete permutation, add to result)

### Complexity Analysis
- **Time Complexity**: O(n!) - There are n! permutations, and we generate all of them
- **Space Complexity**: O(n) - The recursion stack can go up to a depth of n, and we use O(n) space to store each permutation
  - Note: The primary implementation has an additional O(n²) factor in time complexity due to the contains check, which is an O(n) operation
