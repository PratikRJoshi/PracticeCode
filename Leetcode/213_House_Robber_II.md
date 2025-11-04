### 213. House Robber II
### Problem Link: [House Robber II](https://leetcode.com/problems/house-robber-ii/)
### Intuition
This problem is an extension of the House Robber problem, but with a circular arrangement of houses. The key insight is that we cannot rob both the first and last houses simultaneously due to the circular arrangement.

To solve this, we can break it down into two subproblems:
1. Rob houses from index 0 to n-2 (excluding the last house)
2. Rob houses from index 1 to n-1 (excluding the first house)

Then, we take the maximum of these two scenarios.

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

         if (nums.length == 2) { // [R2] Handle two houses case
            return Math.max(nums[0], nums[1]);
        }
         
        // R3 : Rob houses from 0 to n-2
        int max1 = robRange(nums, 0, nums.length - 2);
        // R4 : Rob houses from 1 to n-1
        int max2 = robRange(nums, 1, nums.length - 1);

        return Math.max(max1, max2);
    }

    private int robRange(int[] nums, int start, int end){
        int[] dp = new int[end - start + 1];

        dp[0] = nums[start];
        dp[1] = Math.max(nums[start], nums[start + 1]);

        for(int i = 2; i < dp.length; i++){
            dp[i] = Math.max(dp[i - 1], dp[i - 2] + nums[start + i]);
        }

        return dp[dp.length - 1];
    }
}
```

### Requirement → Code Mapping
- **R0 (Handle edge cases)**: `if (nums == null || nums.length == 0) { return 0; }` - Return 0 for empty arrays
- **R1 (Handle single house)**: `if (nums.length == 1) { return nums[0]; }` - If there's only one house, rob it
- **R2 (Handle two houses)**: `if (nums.length == 2) { return Math.max(nums[0], nums[1]); }` - If there are only two houses, rob the one with more money
- **R3 (First scenario)**: `int max1 = robRange(nums, 0, nums.length - 2);` - Rob houses from 0 to n-2
- **R4 (Second scenario)**: `int max2 = robRange(nums, 1, nums.length - 1);` - Rob houses from 1 to n-1
- **R5 (Return maximum)**: `return Math.max(max1, max2);` - Return the maximum of the two scenarios
- **R6 (Make optimal choice)**: `currMax = Math.max(currMax, prevMax + nums[i]);` - Either skip current house or rob it

### Complexity Analysis
- **Time Complexity**: O(n) - We process each house twice (once in each subproblem)
- **Space Complexity**: O(1) for the optimized implementation, O(n) for the DP array implementation

### Relation to Other Problems
This problem is related to:
- **House Robber** (Problem 198): Similar but with houses arranged in a line
- **House Robber III** (Problem 337): Similar but with houses arranged in a binary tree
