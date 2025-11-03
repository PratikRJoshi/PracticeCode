### 53. Maximum Subarray
### Problem Link: [Maximum Subarray](https://leetcode.com/problems/maximum-subarray/)
### Intuition
This problem asks us to find a contiguous subarray with the largest sum. The key insight is to use Kadane's algorithm, which is a dynamic programming approach. At each position, we decide whether to start a new subarray or extend the existing one based on which gives a larger sum.

For each element, we have two choices:
1. Include it in the current subarray (add it to the running sum)
2. Start a new subarray from this element (if the running sum becomes negative)

We keep track of the maximum sum seen so far and update it whenever we find a larger sum.

### Java Reference Implementation
```java
class Solution {
    public int maxSubArray(int[] nums) {
        if(nums == null || nums.length == 0){ // [R0] Handle edge cases
            return 0;
        }

        int maxSoFar = nums[0]; // [R1] Initialize maximum sum with first element
        int currentMax = nums[0]; // [R2] Initialize current sum with first element

        for(int i = 1; i < nums.length; i++){ // [R3] Iterate through the array
            // [R4] Either extend current subarray or start a new one
            currentMax = Math.max(nums[i], currentMax + nums[i]);
            // [R5] Update maximum sum if current sum is larger
            maxSoFar = Math.max(maxSoFar, currentMax);
        }

        return maxSoFar; // [R6] Return the maximum sum found
    }
}
```

### Requirement → Code Mapping
- **R0 (Handle edge cases)**: `if(nums == null || nums.length == 0) { return 0; }` - Return 0 for empty arrays
- **R1 (Initialize maximum sum)**: `int maxSoFar = nums[0]` - Start with the first element as the maximum sum
- **R2 (Initialize current sum)**: `int currentMax = nums[0]` - Start with the first element as the current sum
- **R3 (Iterate through array)**: `for(int i = 1; i < nums.length; i++)` - Process each element
- **R4 (Make optimal choice)**: `currentMax = Math.max(nums[i], currentMax + nums[i])` - Either start new subarray or extend current one
- **R5 (Update maximum)**: `maxSoFar = Math.max(maxSoFar, currentMax)` - Keep track of the largest sum seen
- **R6 (Return result)**: `return maxSoFar` - Return the maximum subarray sum

### Alternative Approaches
1. **Divide and Conquer**: O(n log n) time complexity, divides the array and finds the maximum subarray that crosses the middle.
2. **Brute Force**: O(n²) time complexity, checks all possible subarrays.

### Complexity Analysis
- **Time Complexity**: O(n) - We make a single pass through the array
- **Space Complexity**: O(1) - We use a constant amount of extra space