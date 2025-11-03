### 268. Missing Number
### Problem Link: [Missing Number](https://leetcode.com/problems/missing-number/)
### Intuition
This problem asks us to find the missing number in an array containing n distinct numbers in the range [0, n]. The key insight is to use the mathematical formula for the sum of the first n natural numbers: n * (n + 1) / 2. By calculating the expected sum and subtracting the actual sum of the array elements, we can find the missing number.

Alternative approaches include using XOR operations or a hash set, but the mathematical approach is the most elegant and efficient.

### Java Reference Implementation
```java
class Solution {
    public int missingNumber(int[] nums) {
        if(nums == null || nums.length == 0){ // [R0] Handle edge cases
            return 0;
        }

        int sum = 0; // [R1] Initialize sum of array elements
        for(int n : nums){ // [R2] Calculate sum of all elements in the array
            sum += n;
        }

        int n = nums.length; // [R3] Get the length of the array
        // [R4] Return the difference between expected sum and actual sum
        return (n * (n + 1) / 2 - sum);
    }
}
```

### Alternative Implementation (Using XOR)
```java
class Solution {
    public int missingNumber(int[] nums) {
        int missing = nums.length; // Initialize with n
        
        for (int i = 0; i < nums.length; i++) {
            // XOR the index and value at that index
            missing ^= i ^ nums[i];
        }
        
        return missing;
    }
}
```

### Requirement → Code Mapping
- **R0 (Handle edge cases)**: `if(nums == null || nums.length == 0) { return 0; }` - Return 0 for empty arrays
- **R1 (Initialize sum)**: `int sum = 0` - Prepare to calculate the sum of array elements
- **R2 (Calculate actual sum)**: `for(int n : nums) { sum += n; }` - Sum all elements in the array
- **R3 (Get array length)**: `int n = nums.length` - The length equals the expected maximum number
- **R4 (Find missing number)**: `return (n * (n + 1) / 2 - sum)` - Use the formula for sum of first n numbers and subtract the actual sum

### Complexity Analysis
- **Time Complexity**: O(n) - We make a single pass through the array
- **Space Complexity**: O(1) - We use a constant amount of extra space

### Mathematics Proof
For an array containing numbers from 0 to n with one missing number:
- Expected sum = 0 + 1 + 2 + ... + n = n * (n + 1) / 2
- Actual sum = sum of all elements in the array
- Missing number = Expected sum - Actual sum