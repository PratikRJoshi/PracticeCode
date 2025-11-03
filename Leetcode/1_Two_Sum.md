### 1. Two Sum
### Problem Link: [Two Sum](https://leetcode.com/problems/two-sum/)
### Intuition
This problem asks us to find two numbers in an array that add up to a specific target value. A naive approach would be to use two nested loops to check all possible pairs, but this would result in O(n²) time complexity.

The key insight is to use a hash map to store the numbers we've seen so far and their indices. For each number, we check if the complement (target - current number) exists in the hash map. If it does, we've found our pair. This approach reduces the time complexity to O(n).

### Java Reference Implementation
```java
class Solution {
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for(int i = 0; i < nums.length; i++){
            if(map.containsKey(target - nums[i])){
                return new int[]{i, map.get(target - nums[i])};
            }
            map.put(nums[i], i);
        }

        return new int[]{};
    }
}
```

### Requirement → Code Mapping
- **R0 (Find two numbers)**: Use a hash map to efficiently find pairs that sum to the target
- **R1 (Return indices)**: Return the indices of the two numbers, not the numbers themselves
- **R2 (One solution)**: The problem guarantees exactly one solution, so we can return as soon as we find a pair
- **R3 (Efficient lookup)**: Use a hash map for O(1) lookup time to check if the complement exists
- **R4 (Handle edge cases)**: Check if the array is empty or if no solution exists

### Example Walkthrough
For the array `[2, 7, 11, 15]` with target `9`:

1. Process `nums[0] = 2`:
   - Complement = 9 - 2 = 7
   - 7 is not in the map, so add 2 to the map with index 0

2. Process `nums[1] = 7`:
   - Complement = 9 - 7 = 2
   - 2 is in the map with index 0
   - Return [0, 1]

### Complexity Analysis
- **Time Complexity**: O(n) - We make a single pass through the array
- **Space Complexity**: O(n) - In the worst case, we might need to store all elements in the hash map
