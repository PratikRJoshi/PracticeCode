### 198. House Robber
### Problem Link: [House Robber](https://leetcode.com/problems/house-robber/)
### Intuition
This problem asks us to determine the maximum amount of money that can be robbed from houses arranged in a row, given that adjacent houses cannot be robbed (as it would alert the police).

The key insight is to use dynamic programming. At each house, we have two options:
1. Rob the current house and add its value to the maximum amount we could rob from houses up to two positions back.
2. Skip the current house and take the maximum amount we could rob from houses up to one position back.

We choose the option that gives us the maximum amount.

### Java Reference Implementation
```java
class Solution {
    public int rob(int[] nums) {
        if (nums == null || nums.length == 0) { // [R0] Handle edge cases
            return 0;
        }
        
        if (nums.length == 1) { // [R1] Handle single house case
            return nums[0];
        }
        
        // [R2] Initialize DP array
        int[] dp = new int[nums.length];
        dp[0] = nums[0]; // Maximum money if we only consider first house
        dp[1] = Math.max(nums[0], nums[1]); // Maximum money if we only consider first two houses
        
        // [R3] Fill the DP array
        for (int i = 2; i < nums.length; i++) {
            // [R4] Choose maximum between robbing current house or skipping it
            dp[i] = Math.max(dp[i-1], dp[i-2] + nums[i]);
        }
        
        // [R5] Return maximum amount that can be robbed
        return dp[nums.length - 1];
    }
}
```

### Space-Optimized Implementation
```java
class Solution {
    public int rob(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        
        if (nums.length == 1) {
            return nums[0];
        }
        
        // We only need to keep track of the last two maximum values
        int prevMax = nums[0]; // Max amount if we consider up to two houses back
        int currMax = Math.max(nums[0], nums[1]); // Max amount if we consider up to one house back
        
        for (int i = 2; i < nums.length; i++) {
            int temp = currMax;
            currMax = Math.max(currMax, prevMax + nums[i]);
            prevMax = temp;
        }
        
        return currMax;
    }
}
```

### Requirement → Code Mapping
- **R0 (Handle edge cases)**: `if (nums == null || nums.length == 0) { return 0; }` - Return 0 for empty arrays
- **R1 (Handle single house)**: `if (nums.length == 1) { return nums[0]; }` - If there's only one house, rob it
- **R2 (Initialize DP array)**: Set up the base cases for the first two houses
- **R3 (Fill DP array)**: Iterate through the remaining houses to build the solution
- **R4 (Make optimal choice)**: `dp[i] = Math.max(dp[i-1], dp[i-2] + nums[i])` - Either skip current house or rob it
- **R5 (Return result)**: `return dp[nums.length - 1]` - Return the maximum amount that can be robbed

### Complexity Analysis
- **Time Complexity**: O(n) - We process each house once
- **Space Complexity**: O(n) for the DP array implementation, O(1) for the space-optimized implementation

### Relation to Other Problems
This problem is related to:
- **House Robber II** (Problem 213): Similar but with houses arranged in a circle
- **House Robber III** (Problem 337): Similar but with houses arranged in a binary tree