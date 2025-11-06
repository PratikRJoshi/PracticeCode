### 190. Reverse Bits
### Problem Link: [Reverse Bits](https://leetcode.com/problems/reverse-bits/)
### Intuition
This problem asks us to reverse the bits of a 32-bit unsigned integer. For example, if the input is 43261596 (binary: 00000010100101000001111010011100), the output should be 964176192 (binary: 00111001011110000010100101000000).

The key insight is to process the input bit by bit, extracting the least significant bit in each iteration and adding it to the result after shifting the result left by 1. This effectively builds the reversed number from right to left.

### Java Reference Implementation
```java
public class Solution {
    // you need treat n as an unsigned value
    public int reverseBits(int n) {
        int result = 0; // [R0] Initialize result to 0
        
        // [R1] Process all 32 bits
        for (int i = 0; i < 32; i++) {
            // [R2] Left shift the result by 1 to make room for the next bit
            result <<= 1;
            
            // [R3] Extract the least significant bit of n and add it to result
            result |= (n & 1);
            
            // [R4] Right shift n by 1 to process the next bit
            n >>>= 1;
        }
        
        return result; // [R5] Return the reversed bits
    }
}
```

### Alternative Implementation (Using Divide and Conquer)
```java
public class Solution {
    // Constants for bit masks
    private static final int M1 = 0x55555555; // 01010101010101010101010101010101
    private static final int M2 = 0x33333333; // 00110011001100110011001100110011
    private static final int M4 = 0x0f0f0f0f; // 00001111000011110000111100001111
    private static final int M8 = 0x00ff00ff; // 00000000111111110000000011111111
    private static final int M16 = 0x0000ffff; // 00000000000000001111111111111111
    
    public int reverseBits(int n) {
        // Swap adjacent bits
        n = ((n & M1) << 1) | ((n >>> 1) & M1);
        // Swap adjacent pairs of bits
        n = ((n & M2) << 2) | ((n >>> 2) & M2);
        // Swap adjacent nibbles (4 bits)
        n = ((n & M4) << 4) | ((n >>> 4) & M4);
        // Swap adjacent bytes
        n = ((n & M8) << 8) | ((n >>> 8) & M8);
        // Swap adjacent words (16 bits)
        n = ((n & M16) << 16) | ((n >>> 16) & M16);
        
        return n;
    }
}
```

### Understanding the Algorithm and Bit Manipulation

1. **Bit-by-Bit Reversal:**
   - We process each bit of the input number from right to left
   - For each bit, we:
     - Shift the result left by 1 to make room for the next bit
     - Extract the least significant bit of the input using `n & 1`
     - Add this bit to the result using the OR operation `|=`
     - Shift the input right by 1 to process the next bit
   - After processing all 32 bits, the result contains the reversed bits

2. **Unsigned Right Shift (`>>>`):**
   - We use the unsigned right shift operator `>>>` instead of the signed right shift `>>`
   - This ensures that 0s are always filled in from the left, regardless of the sign bit
   - This is important for handling negative numbers correctly

3. **Divide and Conquer Approach:**
   - The alternative implementation uses a divide-and-conquer strategy
   - It reverses bits in groups: first pairs, then groups of 4, 8, 16, and finally 32
   - This approach is more efficient for multiple calls with different inputs (can be cached)

4. **Edge Cases:**
   - Input with all 0s: Output is 0
   - Input with all 1s: Output is all 1s
   - Input with alternating bits: Output has the same pattern but reversed

### Requirement → Code Mapping
- **R0 (Initialize result)**: `int result = 0;` - Start with an empty result
- **R1 (Process all bits)**: `for (int i = 0; i < 32; i++)` - Iterate through all 32 bits
- **R2 (Shift result)**: `result <<= 1;` - Make room for the next bit
- **R3 (Extract and add bit)**: `result |= (n & 1);` - Add the least significant bit to the result
- **R4 (Shift input)**: `n >>>= 1;` - Move to the next bit of the input
- **R5 (Return result)**: `return result;` - Return the number with reversed bits

### Complexity Analysis
- **Time Complexity**: O(1)
  - We always process exactly 32 bits
  - The number of operations is constant regardless of the input
  - The divide-and-conquer approach also has O(1) time complexity

- **Space Complexity**: O(1)
  - We use only a constant amount of extra space regardless of input size

### Related Problems
- **Number of 1 Bits** (Problem 191): Count the number of 1 bits in an integer
- **Power of Two** (Problem 231): Determine if a number is a power of two
- **Single Number** (Problem 136): Find the element that appears only once
