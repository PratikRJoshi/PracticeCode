### 238. Product of Array Except Self
### Problem Link: [Product of Array Except Self](https://leetcode.com/problems/product-of-array-except-self/)
### Intuition
This problem asks us to create an array where each element is the product of all elements in the input array except the element at that index. The key constraint is to solve it without using the division operator and in O(n) time.

The key insight is to use two passes through the array:
1. First, calculate the product of all elements to the left of each index
2. Then, calculate the product of all elements to the right of each index
3. Finally, multiply these two products for each index

### Java Reference Implementation
```java
class Solution {
    public int[] productExceptSelf(int[] nums) {
        int n = nums.length;
        int[] result = new int[n];
        
        // Calculate products of all elements to the left of each index
        int leftProduct = 1;
        for (int i = 0; i < n; i++) {
            result[i] = leftProduct;
            leftProduct *= nums[i];
        }
        
        // Calculate products of all elements to the right of each index
        // and multiply with the left products
        int rightProduct = 1;
        for (int i = n - 1; i >= 0; i--) {
            result[i] *= rightProduct;
            rightProduct *= nums[i];
        }
        
        return result;
    }
}
```

### Alternative Implementation (Using Extra Space)
```java
class Solution {
    public int[] productExceptSelf(int[] nums) {
        int n = nums.length;
        
        // Arrays to store products of all elements to the left and right
        int[] leftProducts = new int[n];
        int[] rightProducts = new int[n];
        int[] result = new int[n];
        
        // Calculate products of all elements to the left
        leftProducts[0] = 1; // No elements to the left of the first element
        for (int i = 1; i < n; i++) {
            leftProducts[i] = leftProducts[i - 1] * nums[i - 1];
        }
        
        // Calculate products of all elements to the right
        rightProducts[n - 1] = 1; // No elements to the right of the last element
        for (int i = n - 2; i >= 0; i--) {
            rightProducts[i] = rightProducts[i + 1] * nums[i + 1];
        }
        
        // Calculate the final result
        for (int i = 0; i < n; i++) {
            result[i] = leftProducts[i] * rightProducts[i];
        }
        
        return result;
    }
}
```

### Requirement → Code Mapping
- **R0 (No division)**: The solution doesn't use the division operator
- **R1 (O(n) time complexity)**: The solution uses two passes through the array, each taking O(n) time
- **R2 (Left products)**: `result[i] = leftProduct;` and `leftProduct *= nums[i];` - Calculate products of all elements to the left
- **R3 (Right products)**: `result[i] *= rightProduct;` and `rightProduct *= nums[i];` - Calculate products of all elements to the right
- **R4 (Combine products)**: `result[i] *= rightProduct;` - Multiply left and right products

### Example Walkthrough
For the array `[4, 3, 2, 1, 2]`:

**First Pass (Left Products):**
- result[0] = 1 (no elements to the left)
- result[1] = 4 (product of elements to the left: 4)
- result[2] = 12 (product of elements to the left: 4 * 3)
- result[3] = 24 (product of elements to the left: 4 * 3 * 2)
- result[4] = 24 (product of elements to the left: 4 * 3 * 2 * 1)

**Second Pass (Right Products and Final Result):**
- result[4] = 24 * 1 = 24 (no elements to the right)
- result[3] = 24 * 2 = 48 (product of elements to the right: 2)
- result[2] = 12 * 2 = 24 (product of elements to the right: 1 * 2)
- result[1] = 4 * 4 = 16 (product of elements to the right: 2 * 1 * 2)
- result[0] = 1 * 12 = 12 (product of elements to the right: 3 * 2 * 1 * 2)

Final result: `[12, 16, 24, 48, 24]`

### Complexity Analysis
- **Time Complexity**: O(n) - We make two passes through the array
- **Space Complexity**: O(1) - We only use the output array and a few variables
