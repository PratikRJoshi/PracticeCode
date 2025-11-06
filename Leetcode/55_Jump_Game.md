### 55. Jump Game
### Problem Link: [Jump Game](https://leetcode.com/problems/jump-game/)
### Intuition
This problem asks us to determine if we can reach the last index of an array, starting from the first index. At each position, we can jump forward by at most the value at that position.

The key insight is to use a greedy approach: we keep track of the furthest position we can reach from any position we've visited so far. If at any point, our current position exceeds the furthest reachable position, we can't proceed further. If the furthest reachable position is at least the last index, we can reach the end.

### Java Reference Implementation
```java
class Solution {
    public boolean canJump(int[] nums) {
        if (nums == null || nums.length == 0) { // [R0] Handle edge cases
            return false;
        }
        
        int maxReach = 0; // [R1] Initialize the furthest position we can reach
        
        // [R2] Iterate through the array
        for (int i = 0; i < nums.length; i++) {
            // [R3] If current position is beyond our reach, we can't proceed
            if (i > maxReach) {
                return false;
            }
            
            // [R4] Update the furthest position we can reach
            maxReach = Math.max(maxReach, i + nums[i]);
            
            // [R5] If we can reach the last index, return true
            if (maxReach >= nums.length - 1) {
                return true;
            }
        }
        
        return true; // [R6] If we've gone through the entire array, we can reach the end
    }
}
```

### Alternative Implementation (Working Backwards)
```java
class Solution {
    public boolean canJump(int[] nums) {
        if (nums == null || nums.length == 0) {
            return false;
        }
        
        int lastPos = nums.length - 1;
        
        // Start from the second-to-last position and work backwards
        for (int i = nums.length - 2; i >= 0; i--) {
            // If we can reach the last position from current position
            if (i + nums[i] >= lastPos) {
                // Update the last position to the current position
                lastPos = i;
            }
        }
        
        // If the last position is 0, we can reach the end from the start
        return lastPos == 0;
    }
}
```

### Understanding the Algorithm and Boundary Checks

1. **Greedy Approach (Forward):**
   - We maintain a variable `maxReach` that represents the furthest position we can reach
   - For each position `i`, we update `maxReach = max(maxReach, i + nums[i])`
   - If at any point `i > maxReach`, it means we can't reach position `i`, so we return false
   - If `maxReach` becomes greater than or equal to the last index, we can reach the end

2. **Greedy Approach (Backward):**
   - We start from the end and work backwards
   - We maintain a variable `lastPos` that represents the last position from which we can reach the end
   - For each position `i`, if we can reach `lastPos` from `i` (i.e., `i + nums[i] >= lastPos`), we update `lastPos = i`
   - If `lastPos` becomes 0, it means we can reach the end from the start

3. **Early Termination:**
   - In the forward approach, we return true as soon as `maxReach` is greater than or equal to the last index
   - This optimization can save time for large arrays where we can reach the end early

4. **Edge Cases:**
   - Empty array: Return false (no elements to jump through)
   - Single element: Return true (we're already at the end)

### Requirement → Code Mapping
- **R0 (Handle edge cases)**: `if (nums == null || nums.length == 0) { return false; }` - Return false for empty arrays
- **R1 (Initialize maxReach)**: `int maxReach = 0;` - Initialize the furthest position we can reach
- **R2 (Iterate through array)**: Process each position in the array
- **R3 (Check if position is reachable)**: `if (i > maxReach) { return false; }` - Return false if we can't reach the current position
- **R4 (Update maxReach)**: `maxReach = Math.max(maxReach, i + nums[i]);` - Update the furthest position we can reach
- **R5 (Check if end is reachable)**: `if (maxReach >= nums.length - 1) { return true; }` - Return true if we can reach the end
- **R6 (Return result)**: `return true;` - If we've gone through the entire array, we can reach the end

### Complexity Analysis
- **Time Complexity**: O(n)
  - We iterate through the array once
  - Each operation inside the loop takes constant time
  - Overall: O(n)

- **Space Complexity**: O(1)
  - We use only a constant amount of extra space regardless of input size
  - We only need to keep track of `maxReach` (or `lastPos` in the backward approach)

### Related Problems
- **Jump Game II** (Problem 45): Find the minimum number of jumps to reach the end
- **Jump Game III** (Problem 1306): Determine if you can reach any index with value 0
- **Jump Game IV** (Problem 1345): Minimum jumps to reach the last index with different jump rules
