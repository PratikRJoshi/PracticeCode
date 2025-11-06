### 338. Counting Bits
### Problem Link: [Counting Bits](https://leetcode.com/problems/counting-bits/)
### Intuition
This problem asks us to count the number of 1 bits in the binary representation of each number from 0 to n. The key insight is to use dynamic programming to leverage previously computed results.

There are several patterns we can exploit:
1. For even numbers, the number of 1 bits is the same as for the number divided by 2 (right shift by 1)
2. For odd numbers, the number of 1 bits is one more than for the number divided by 2

Alternatively, we can use the trick `x & (x-1)` to clear the least significant 1 bit, which allows us to build up results from previously computed values.

### Java Reference Implementation (DP with Bit Manipulation)
```java
class Solution {
    public int[] countBits(int n) {
        int[] result = new int[n + 1]; // [R0] Initialize result array
        
        // [R1] Base case
        result[0] = 0;
        
        // [R2] Fill the array using dynamic programming
        for (int i = 1; i <= n; i++) {
            // [R3] For any number i, the number of 1 bits is:
            // 1 + the number of 1 bits in i with its least significant 1 bit cleared
            result[i] = result[i & (i - 1)] + 1;
        }
        
        return result; // [R4] Return the array with counts
    }
}
```

### Alternative Implementation (DP with Offset)
```java
class Solution {
    public int[] countBits(int n) {
        int[] result = new int[n + 1];
        result[0] = 0;
        
        // Compute the most significant bit offset
        int offset = 1;
        
        for (int i = 1; i <= n; i++) {
            // When i equals a power of 2, update the offset
            if (offset * 2 == i) {
                offset = i;
            }
            
            // The number of 1 bits in i is 1 + the number of 1 bits in (i - offset)
            result[i] = 1 + result[i - offset];
        }
        
        return result;
    }
}
```

### Alternative Implementation (Using Right Shift)
```java
class Solution {
    public int[] countBits(int n) {
        int[] result = new int[n + 1];
        
        for (int i = 0; i <= n; i++) {
            // For even numbers: bits(i) = bits(i/2)
            // For odd numbers: bits(i) = bits(i/2) + 1
            result[i] = result[i >> 1] + (i & 1);
        }
        
        return result;
    }
}
```

### Understanding the Algorithm and Dynamic Programming

1. **DP with Bit Manipulation (`i & (i-1)`):**
   - We use the property that `i & (i-1)` clears the least significant 1 bit of i
   - For any number i, the number of 1 bits is 1 + the number of 1 bits in i with its least significant 1 bit cleared
   - This allows us to build up results using previously computed values

2. **DP with Offset:**
   - We observe that for powers of 2, there's only one 1 bit
   - For any number i between 2^k and 2^(k+1), the number of 1 bits is 1 + the number of 1 bits in (i - 2^k)
   - We keep track of the most significant bit (offset) and use it to compute new values

3. **DP with Right Shift:**
   - For any number i, the number of 1 bits is:
     - For even numbers: Same as i/2 (right shift by 1)
     - For odd numbers: 1 + the number of 1 bits in i/2
   - We can express this as: bits(i) = bits(i >> 1) + (i & 1)

4. **Edge Cases:**
   - n = 0: Return [0]
   - n = 1: Return [0, 1]
   - Powers of 2: Have exactly one 1 bit

### Requirement → Code Mapping
- **R0 (Initialize array)**: `int[] result = new int[n + 1];` - Create an array to store the counts
- **R1 (Base case)**: `result[0] = 0;` - Set the count for 0 (no 1 bits)
- **R2 (Fill array)**: Use dynamic programming to fill the array
- **R3 (DP relation)**: `result[i] = result[i & (i - 1)] + 1;` - Use bit manipulation to leverage previous results
- **R4 (Return result)**: `return result;` - Return the array with counts for all numbers

### Complexity Analysis
- **Time Complexity**: O(n)
  - We process each number from 0 to n exactly once
  - Each operation inside the loop takes constant time
  - Overall: O(n)

- **Space Complexity**: O(n)
  - We use an array of size n+1 to store the results
  - No additional space is used beyond the output array

### Related Problems
- **Number of 1 Bits** (Problem 191): Count the number of 1 bits in a single number
- **Power of Two** (Problem 231): Determine if a number is a power of two
- **Bitwise AND of Numbers Range** (Problem 201): Find the bitwise AND of all numbers in a range
