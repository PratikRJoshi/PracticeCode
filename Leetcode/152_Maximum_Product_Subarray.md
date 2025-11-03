### 152. Maximum Product Subarray
### Problem Link: [Maximum Product Subarray](https://leetcode.com/problems/maximum-product-subarray/)
### Intuition
The key insight for this problem is that we need to track both the maximum and minimum product ending at each position. This is because a negative number can turn a minimum product into a maximum product and vice versa. For example, if we have a very negative minimum product and we encounter another negative number, multiplying them will give us a large positive number.

### Java Reference Implementation
```java
class Solution {
    public int maxProduct(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        
        int maxSoFar = nums[0]; // Initialize with first element
        int currentMax = nums[0]; // Max product ending at current position
        int currentMin = nums[0]; // Min product ending at current position
        
        for (int i = 1; i < nums.length; i++) {
            // If current number is negative, swap max and min
            // because multiplying by a negative flips the values
            if (nums[i] < 0) {
                int temp = currentMax;
                currentMax = currentMin;
                currentMin = temp;
            }
            
            // Update max and min ending at current position
            currentMax = Math.max(nums[i], currentMax * nums[i]);
            currentMin = Math.min(nums[i], currentMin * nums[i]);
            
            // Update global max
            maxSoFar = Math.max(maxSoFar, currentMax);
        }
        
        return maxSoFar;
    }
}
```

### Requirement → Code Mapping
- **R0 (Handle edge cases)**: `if (nums == null || nums.length == 0) { return 0; }`
- **R1 (Initialize variables)**: Set `maxSoFar`, `currentMax`, and `currentMin` to the first element
- **R2 (Swap max and min for negative numbers)**: `if (nums[i] < 0) { ... }` - When encountering a negative number, swap max and min since multiplication by a negative flips their values
- **R3 (Update current max and min)**: Calculate new max and min products ending at current position
- **R4 (Update global max)**: `maxSoFar = Math.max(maxSoFar, currentMax);`

### Complexity
- **Time Complexity**: O(n) - We process each element in the array exactly once
- **Space Complexity**: O(1) - We use a constant amount of extra space regardless of input size
