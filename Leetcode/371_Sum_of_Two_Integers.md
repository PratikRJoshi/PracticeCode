### 371. Sum of Two Integers
### Problem Link: [Sum of Two Integers](https://leetcode.com/problems/sum-of-two-integers/)
### Intuition
This problem asks us to add two integers without using the `+` or `-` operators. The key insight is to use bitwise operations to simulate addition. Binary addition can be broken down into two parts:

1. XOR (`^`) operation, which gives the sum without considering carry
2. AND (`&`) operation followed by a left shift (`<<`), which gives the carry

We repeat this process until there's no carry left.

### Java Reference Implementation
```java
class Solution {
    public int getSum(int a, int b) {
        // [R0] Continue until there's no carry left
        while (b != 0) {
            // [R1] Calculate carry using AND and left shift
            int carry = a & b;
            
            // [R2] Calculate sum without carry using XOR
            a = a ^ b;
            
            // [R3] Prepare carry for the next iteration
            b = carry << 1;
        }
        
        return a; // [R4] Return the final sum
    }
}
```

### Alternative Implementation (Recursive)
```java
class Solution {
    public int getSum(int a, int b) {
        // Base case: no carry left
        if (b == 0) {
            return a;
        }
        
        // Calculate sum without carry
        int sum = a ^ b;
        
        // Calculate carry
        int carry = (a & b) << 1;
        
        // Recursive call with sum and carry
        return getSum(sum, carry);
    }
}
```

### Understanding the Algorithm and Bitwise Operations

1. **Binary Addition Process:**
   - In binary addition, we add bits at each position
   - If both bits are 1, we get 0 with a carry of 1
   - If one bit is 1 and the other is 0, we get 1 with no carry
   - If both bits are 0, we get 0 with no carry

2. **XOR Operation (`^`):**
   - XOR gives 1 if the bits are different, 0 if they're the same
   - This is exactly what we want for the sum without considering carry
   - 0 ^ 0 = 0, 0 ^ 1 = 1, 1 ^ 0 = 1, 1 ^ 1 = 0

3. **AND Operation (`&`) and Left Shift (`<<`):**
   - AND gives 1 only if both bits are 1, otherwise 0
   - This identifies positions where a carry will be generated
   - Left shifting by 1 moves the carry to the next significant bit position
   - (a & b) << 1 gives us the carry for the next iteration

4. **Iterative Process:**
   - We calculate the sum without carry using XOR
   - We calculate the carry using AND and left shift
   - We repeat this process, treating the previous sum as one operand and the carry as the other
   - We continue until there's no carry left (b == 0)

5. **Edge Cases:**
   - When one of the numbers is 0, the result is the other number
   - Negative numbers are handled correctly due to two's complement representation

### Requirement → Code Mapping
- **R0 (Continue until no carry)**: `while (b != 0)` - Continue the process until there's no carry left
- **R1 (Calculate carry)**: `int carry = a & b;` - Identify positions where a carry will be generated
- **R2 (Calculate sum without carry)**: `a = a ^ b;` - Calculate the sum without considering carry
- **R3 (Prepare carry for next iteration)**: `b = carry << 1;` - Shift the carry to the next bit position
- **R4 (Return final sum)**: `return a;` - Return the final sum when there's no carry left

### Complexity Analysis
- **Time Complexity**: O(log n)
  - In the worst case, we need to process all bits of the input numbers
  - For 32-bit integers, this is O(32) = O(1)
  - For arbitrary precision integers, this would be O(log n) where n is the maximum of the two numbers

- **Space Complexity**: O(1)
  - We use only a constant amount of extra space regardless of input size
  - The recursive implementation uses O(log n) space for the call stack

### Related Problems
- **Add Binary** (Problem 67): Similar concept but with binary strings
- **Plus One** (Problem 66): Increment a large integer represented as an array
- **Subtract Strings** (Problem 415): Similar concept but with string representation
