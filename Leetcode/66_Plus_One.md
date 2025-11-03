### 66. Plus One
### Problem Link: [Plus One](https://leetcode.com/problems/plus-one/)
### Intuition
This problem asks us to add 1 to a number represented as an array of digits. The digits are stored such that the most significant digit is at the head of the list.

The key insight is to handle the carry propagation correctly. We start from the least significant digit (the rightmost digit), add 1 to it, and propagate any carry to the left. If there's still a carry after processing all digits, we need to create a new array with one more digit to accommodate the carry.

### Java Reference Implementation
```java
class Solution {
    public int[] plusOne(int[] digits) {
        int n = digits.length;
        
        // Start from the least significant digit
        for (int i = n - 1; i >= 0; i--) {
            // If the current digit is less than 9, we can simply increment it and return
            if (digits[i] < 9) {
                digits[i]++;
                return digits;
            }
            
            // If the current digit is 9, set it to 0 and continue to the next digit
            digits[i] = 0;
        }
        
        // If we reach here, it means all digits were 9, so we need a new array with an additional digit
        int[] result = new int[n + 1];
        result[0] = 1; // The most significant digit becomes 1, rest are already 0
        
        return result;
    }
}
```

### Alternative Implementation (Using Carry)
```java
class Solution {
    public int[] plusOne(int[] digits) {
        int n = digits.length;
        int carry = 1; // Start with a carry of 1 (the "plus one")
        
        for (int i = n - 1; i >= 0; i--) {
            int sum = digits[i] + carry;
            digits[i] = sum % 10; // Update the current digit
            carry = sum / 10;     // Update the carry
            
            // If there's no carry, we can return early
            if (carry == 0) {
                return digits;
            }
        }
        
        // If we still have a carry, create a new array with an additional digit
        int[] result = new int[n + 1];
        result[0] = carry;
        
        return result;
    }
}
```

### Requirement → Code Mapping
- **R0 (Add 1 to the number)**: Start with the least significant digit and add 1
- **R1 (Handle digit overflow)**: If a digit becomes 10 after adding, set it to 0 and carry 1 to the next digit
- **R2 (Propagate carry)**: Continue propagating the carry to the left as needed
- **R3 (Handle array expansion)**: If there's still a carry after processing all digits, create a new array with one more digit

### Example Walkthrough
For the digits `[1, 2, 3]`:

1. Start from the rightmost digit: 3
2. Add 1: 3 + 1 = 4
3. Update the array: [1, 2, 4]
4. No carry, so return [1, 2, 4]

For the digits `[9, 9, 9]`:

1. Start from the rightmost digit: 9
2. Add 1: 9 + 1 = 10, set to 0, carry 1
3. Process the middle digit: 9 + 1 = 10, set to 0, carry 1
4. Process the leftmost digit: 9 + 1 = 10, set to 0, carry 1
5. We've processed all digits but still have a carry, so create a new array: [1, 0, 0, 0]
6. Return [1, 0, 0, 0]

### Complexity Analysis
- **Time Complexity**: O(n) - In the worst case, we need to iterate through all digits
- **Space Complexity**: O(n) - In the worst case, we need to create a new array with one more digit
