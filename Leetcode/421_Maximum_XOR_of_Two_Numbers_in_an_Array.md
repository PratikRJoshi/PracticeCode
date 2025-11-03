### 421. Maximum XOR of Two Numbers in an Array
### Problem Link: [Maximum XOR of Two Numbers in an Array](https://leetcode.com/problems/maximum-xor-of-two-numbers-in-an-array/)
### Intuition
This problem asks us to find the maximum XOR value that can be obtained from any two numbers in an array. The brute force approach would be to check all possible pairs, but a more efficient solution uses a bit manipulation technique and a hash set to check for potential XOR values.

The key insight is to build the maximum XOR bit by bit, starting from the most significant bit. For each bit position, we check if we can make that bit 1 in the final XOR result.

### Java Reference Implementation (Bit Manipulation with Hash Set)
```java
import java.util.*;

class Solution {
    public int findMaximumXOR(int[] nums) {
        int maxResult = 0;
        int mask = 0;
        
        // Start from the most significant bit (30th bit for integers)
        // and work towards the least significant bit (0th bit)
        for (int i = 30; i >= 0; i--) {
            // Add the current bit to the mask
            mask |= (1 << i);
            
            // Set of prefixes seen so far
            Set<Integer> prefixes = new HashSet<>();
            
            // Get the prefix of each number (with respect to the current mask)
            for (int num : nums) {
                prefixes.add(num & mask);
            }
            
            // Check if current bit can be set in the result
            int potentialMax = maxResult | (1 << i);
            
            // For the current bit to be set in the result, we need two numbers
            // whose prefixes XOR to potentialMax
            for (int prefix : prefixes) {
                // If prefix ^ x = potentialMax, then x = prefix ^ potentialMax
                if (prefixes.contains(prefix ^ potentialMax)) {
                    maxResult = potentialMax;
                    break;
                }
            }
        }
        
        return maxResult;
    }
}
```

### Alternative Implementation (Brute Force)
```java
class Solution {
    public int findMaximumXOR(int[] nums) {
        int maxXOR = 0;
        
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                maxXOR = Math.max(maxXOR, nums[i] ^ nums[j]);
            }
        }
        
        return maxXOR;
    }
}
```

### Alternative Implementation (Trie)
```java
class Solution {
    // Trie node
    class TrieNode {
        TrieNode[] children;
        
        public TrieNode() {
            children = new TrieNode[2];
        }
    }
    
    public int findMaximumXOR(int[] nums) {
        // Build the Trie
        TrieNode root = new TrieNode();
        
        // Insert all numbers into the Trie
        for (int num : nums) {
            TrieNode node = root;
            
            // Process each bit from most significant to least significant
            for (int i = 30; i >= 0; i--) {
                int bit = (num >> i) & 1;
                
                if (node.children[bit] == null) {
                    node.children[bit] = new TrieNode();
                }
                
                node = node.children[bit];
            }
        }
        
        // Find maximum XOR
        int maxXOR = 0;
        
        for (int num : nums) {
            TrieNode node = root;
            int currentXOR = 0;
            
            // Try to go in the opposite direction of each bit to maximize XOR
            for (int i = 30; i >= 0; i--) {
                int bit = (num >> i) & 1;
                int oppositeBit = 1 - bit;
                
                // If opposite bit exists, go that way to maximize XOR
                if (node.children[oppositeBit] != null) {
                    currentXOR |= (1 << i);
                    node = node.children[oppositeBit];
                } else {
                    node = node.children[bit];
                }
            }
            
            maxXOR = Math.max(maxXOR, currentXOR);
        }
        
        return maxXOR;
    }
}
```

### Requirement → Code Mapping (Bit Manipulation)
- **R0 (Process bits from MSB to LSB)**: `for (int i = 30; i >= 0; i--) { ... }` - Start from the most significant bit
- **R1 (Build mask)**: `mask |= (1 << i);` - Add the current bit to the mask
- **R2 (Collect prefixes)**: `prefixes.add(num & mask);` - Get the prefix of each number with respect to the current mask
- **R3 (Check potential max)**: `int potentialMax = maxResult | (1 << i);` - Try to set the current bit in the result
- **R4 (Verify possibility)**: `if (prefixes.contains(prefix ^ potentialMax)) { ... }` - Check if the current bit can be set

### Complexity Analysis
- **Time Complexity**:
  - Bit Manipulation: O(n * 32) = O(n) where n is the number of elements in the array
  - Brute Force: O(n²) where n is the number of elements in the array
  - Trie: O(n * 32) = O(n) where n is the number of elements in the array
- **Space Complexity**:
  - Bit Manipulation: O(n) for the hash set
  - Brute Force: O(1)
  - Trie: O(n * 32) = O(n) for the trie structure
