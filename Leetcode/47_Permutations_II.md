### 47. Permutations II
### Problem Link: [Permutations II](https://leetcode.com/problems/permutations-ii/)
### Intuition
This problem is an extension of the standard permutation problem (LC 46), but with a key difference: the input array may contain duplicate elements. We need to generate all possible unique permutations without duplicates in the result.

The key insight is still to use backtracking, but with an additional mechanism to handle duplicates. We can:
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