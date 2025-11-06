### 191. Number of 1 Bits
### Problem Link: [Number of 1 Bits](https://leetcode.com/problems/number-of-1-bits/)
### Intuition
This problem asks us to count the number of '1' bits in a 32-bit unsigned integer (also known as the Hamming weight). The key insight is to iterate through each bit of the number and count the ones.

There are several approaches to solve this problem:
1. Check each bit using bitwise AND with a mask
2. Use the trick `n & (n-1)` to clear the least significant 1 bit
3. Use built-in functions like `Integer.bitCount()` in Java

The second approach is particularly elegant as it directly handles only the set bits, making it more efficient for sparse numbers.

### Java Reference Implementation
```java
public class Solution {
    // you need to treat n as an unsigned value
    public int hammingWeight(int n) {
        int count = 0; // [R0] Initialize count to 0
        
        // [R1] Process until n becomes 0
        while (n != 0) {
            // [R2] Clear the least significant 1 bit and increment count
            n &= (n - 1);
            count++;
        }
        
        return count; // [R3] Return the count of 1 bits
    }
}
```

### Alternative Implementation (Checking Each Bit)
```java
public class Solution {
    public int hammingWeight(int n) {
        int count = 0;
        
        // Check each of the 32 bits
        for (int i = 0; i < 32; i++) {
            // Check if the i-th bit is 1
            if ((n & (1 << i)) != 0) {
                count++;
            }
        }
        
        return count;
    }
}
```

### Alternative Implementation (Using Right Shift)
```java
public class Solution {
    public int hammingWeight(int n) {
        int count = 0;
        
        // Process each bit by right shifting
        for (int i = 0; i < 32; i++) {
            // Check if the least significant bit is 1
            if ((n & 1) == 1) {
                count++;
            }
            // Right shift to check the next bit
            n >>>= 1;
        }
        
        return count;
    }
}
```

### Understanding the Algorithm and Bit Manipulation

1. **Brian Kernighan's Algorithm (`n & (n-1)`):**
   - This technique clears the least significant 1 bit in each iteration
   - For example, if n = 10110, then n-1 = 10101
   - n & (n-1) = 10110 & 10101 = 10100 (the rightmost 1 is cleared)
   - We repeat this until n becomes 0, counting each operation
   - The number of operations equals the number of 1 bits

2. **How `n & (n-1)` Works:**
   - When we subtract 1 from n, all bits from the rightmost 1 to the end are flipped
   - When we then AND this with the original number, the rightmost 1 becomes 0
   - All other bits remain unchanged
   - This effectively removes exactly one 1 bit in each iteration

3. **Alternative Approaches:**
   - Checking each bit: We use a mask (1 << i) to check if the i-th bit is 1
   - Right shift: We check the least significant bit and then right shift the number
   - Both alternatives always perform 32 operations, while Brian Kernighan's algorithm performs operations equal to the number of 1 bits

4. **Edge Cases:**
   - n = 0: Return 0 (no 1 bits)
   - n = -1 (all 1s in two's complement): Return 32
   - n with alternating bits: Return the number of 1s in the pattern

### Requirement → Code Mapping
- **R0 (Initialize count)**: `int count = 0;` - Start with a count of 0
- **R1 (Process until zero)**: `while (n != 0)` - Continue until all 1 bits are cleared
- **R2 (Clear least significant 1)**: `n &= (n - 1);` - Clear the rightmost 1 bit and increment count
- **R3 (Return count)**: `return count;` - Return the total number of 1 bits

### Complexity Analysis
- **Time Complexity**: O(k)
  - k is the number of 1 bits in the integer
  - In the worst case (all bits are 1), this is O(32) = O(1)
  - The alternative approaches always take O(32) = O(1) time

- **Space Complexity**: O(1)
  - We use only a constant amount of extra space regardless of input size

### Related Problems
- **Reverse Bits** (Problem 190): Reverse the bits of a 32-bit integer
- **Power of Two** (Problem 231): Determine if a number is a power of two
- **Counting Bits** (Problem 338): Count the number of 1 bits for a range of numbers
