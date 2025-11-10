### 47. Permutations II
### Problem Link: [Permutations II](https://leetcode.com/problems/permutations-ii/)
### Intuition
This problem is an extension of the standard permutation problem (LC 46), but with a key difference: the input array may contain duplicate elements. We need to generate all possible unique permutations without duplicates in the result.

The key insight is to use a backtracking approach, but with an additional mechanism to handle duplicates. We can:
1. Sort the array first to group duplicate elements together
2. Skip elements that have the same value as their previous element if that previous element was not used in the current permutation path
3. Use a visited array to track which elements have been used in the current permutation

### Java Implementation (Backtracking with Duplicate Handling)
```java
class Solution {
    public List<List<Integer>> permuteUnique(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        if (nums == null || nums.length == 0) {
            return result;
        }
        
        // Sort the array to group duplicates together
        Arrays.sort(nums);
        
        boolean[] used = new boolean[nums.length];
        backtrack(nums, used, new ArrayList<>(), result);
        return result;
    }
    
    private void backtrack(int[] nums, boolean[] used, List<Integer> temp, List<List<Integer>> result) {
        // Base case: If we've built a complete permutation
        if (temp.size() == nums.length) {
            result.add(new ArrayList<>(temp));
            return;
        }
        
        for (int i = 0; i < nums.length; i++) {
            // Skip used elements
            if (used[i]) {
                continue;
            }
            
            // Skip duplicates: if the current element is the same as the previous element
            // and the previous element is not used, skip this element
            if (i > 0 && nums[i] == nums[i-1] && !used[i-1]) {
                continue;
            }
            
            // Use this element
            used[i] = true;
            temp.add(nums[i]);
            
            // Recursively build the rest of the permutation
            backtrack(nums, used, temp, result);
            
            // Backtrack: remove the element and mark it as unused
            temp.remove(temp.size() - 1);
            used[i] = false;
        }
    }
}
```

### Alternative Implementation (Using a Set)
```java
class Solution {
    public List<List<Integer>> permuteUnique(int[] nums) {
        // Use a set to automatically handle duplicates
        Set<List<Integer>> resultSet = new HashSet<>();
        
        // Create a list of available numbers
        List<Integer> numsList = new ArrayList<>();
        for (int num : nums) {
            numsList.add(num);
        }
        
        backtrack(numsList, new ArrayList<>(), resultSet);
        
        // Convert set to list for return
        return new ArrayList<>(resultSet);
    }
    
    private void backtrack(List<Integer> nums, List<Integer> temp, Set<List<Integer>> resultSet) {
        // Base case: If we've used all numbers
        if (nums.isEmpty()) {
            resultSet.add(new ArrayList<>(temp));
            return;
        }
        
        for (int i = 0; i < nums.size(); i++) {
            // Take current number
            int current = nums.get(i);
            temp.add(current);
            
            // Remove the used number from available numbers
            nums.remove(i);
            
            // Recursively build the rest of the permutation
            backtrack(nums, temp, resultSet);
            
            // Backtrack: restore the state
            nums.add(i, current);
            temp.remove(temp.size() - 1);
        }
    }
}
```

### Requirement → Code Mapping
- **R0 (Generate all unique permutations)**: Use backtracking with duplicate handling
- **R1 (Sort first)**: Sort the array to group duplicates together for easier handling
- **R2 (Skip duplicates)**: Use the condition `i > 0 && nums[i] == nums[i-1] && !used[i-1]` to avoid duplicate permutations
- **R3 (Track used elements)**: Use a boolean array to mark which elements have been used
- **R4 (Backtracking)**: After exploring a path, remove the last element to try other paths

### Example Walkthrough
For the array `[1, 1, 2]`:

1. Sort the array (already sorted): `[1, 1, 2]`
2. Start with an empty list `[]`:
   - Try adding first 1: `[1]`
     - Try adding second 1: `[1, 1]`
       - Try adding 2: `[1, 1, 2]` (Complete permutation, add to result)
     - Backtrack to `[1]`
     - Try adding 2: `[1, 2]`
       - Try adding second 1: `[1, 2, 1]` (Complete permutation, add to result)
   - Backtrack to `[]`
   - Try adding second 1: Skip (because it's a duplicate of the first 1 and first 1 is not used)
   - Try adding 2: `[2]`
     - Try adding first 1: `[2, 1]`
       - Try adding second 1: `[2, 1, 1]` (Complete permutation, add to result)

The result is `[[1,1,2], [1,2,1], [2,1,1]]`, which contains all unique permutations.

### Key Insight for Handling Duplicates
The critical part of the solution is the duplicate-skipping logic:
```java
if (i > 0 && nums[i] == nums[i-1] && !used[i-1]) {
    continue;
}
```

This condition ensures that:
- We only consider duplicates in a specific order (e.g., use the first '1', then the second '1')
- We never generate the same permutation twice
- We don't skip elements when they're part of different permutation paths

### Complexity Analysis

#### Time Complexity: O(n × n!)
- In the worst case (no duplicates), there are n! permutations
- With duplicates, the number of permutations is less than n!
- For each permutation, we spend O(n) time to build it
- The sorting step takes O(n log n) time, but this is dominated by the permutation generation
- Therefore, the overall time complexity is O(n × n!)

#### Space Complexity: O(n)
- The recursion stack can go up to a depth of n
- We use a boolean array of size n to track used elements
- We use a temporary list of size n to build each permutation
- Therefore, the space complexity is O(n)

### Comparison with Permutations (LC 46)
The main differences from the standard permutation problem are:
1. We sort the array first to group duplicates
2. We add logic to skip duplicate permutations
3. We need to be careful about the order in which we use duplicate elements

### Similar Problems

1. **Permutations (LC 46)**
   - The basic version without duplicates
   - Uses the same backtracking approach but without duplicate handling

2. **Combinations (LC 77)**
   - Generates all possible combinations of k numbers from 1 to n
   - Uses backtracking but doesn't need to use all elements

3. **Subsets (LC 78)**
   - Generates all possible subsets of an array
   - Similar backtracking approach but builds subsets of all possible lengths

4. **Subsets II (LC 90)**
   - Generates all possible subsets of an array with duplicates
   - Uses the same duplicate-handling technique as Permutations II

5. **Combination Sum (LC 39)**
   - Finds all combinations of candidates that sum to a target
   - Uses backtracking with an additional constraint on the sum

6. **Combination Sum II (LC 40)**
   - Similar to Combination Sum but with duplicates in the candidates
   - Uses the same duplicate-handling technique as Permutations II

7. **Palindrome Partitioning (LC 131)**
   - Partitions a string such that every substring is a palindrome
   - Uses backtracking to try different partitioning points

8. **Letter Combinations of a Phone Number (LC 17)**
   - Maps digits to letters and generates all possible letter combinations
   - Uses backtracking to build combinations

These problems all share the common theme of generating all possible arrangements or selections under certain constraints, and they all use variations of the backtracking algorithm.