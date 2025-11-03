### 387. First Unique Character in a String
### Problem Link: [First Unique Character in a String](https://leetcode.com/problems/first-unique-character-in-a-string/)
### Intuition
This problem asks us to find the first non-repeating character in a string and return its index. If no such character exists, return -1. A character is considered non-repeating if it appears exactly once in the string.

The key insight is to count the occurrences of each character in the string and then find the first character with a count of 1. We can use a hash map or an array to store the character counts.

### Java Reference Implementation
```java
class Solution {
    public int firstUniqChar(String s) {
        // Create a frequency map for each character
        int[] frequency = new int[26]; // Assuming lowercase English letters
        
        // Count the frequency of each character
        for (char c : s.toCharArray()) {
            frequency[c - 'a']++;
        }
        
        // Find the first character with frequency 1
        for (int i = 0; i < s.length(); i++) {
            if (frequency[s.charAt(i) - 'a'] == 1) {
                return i;
            }
        }
        
        return -1; // No unique character found
    }
}
```

### Alternative Implementation (Using HashMap)
```java
import java.util.HashMap;
import java.util.Map;

class Solution {
    public int firstUniqChar(String s) {
        Map<Character, Integer> frequency = new HashMap<>();
        
        // Count the frequency of each character
        for (char c : s.toCharArray()) {
            frequency.put(c, frequency.getOrDefault(c, 0) + 1);
        }
        
        // Find the first character with frequency 1
        for (int i = 0; i < s.length(); i++) {
            if (frequency.get(s.charAt(i)) == 1) {
                return i;
            }
        }
        
        return -1; // No unique character found
    }
}
```

### Alternative Implementation (Single Pass with LinkedHashMap)
```java
import java.util.LinkedHashMap;
import java.util.Map;

class Solution {
    public int firstUniqChar(String s) {
        Map<Character, Integer> charToIndex = new LinkedHashMap<>();
        Set<Character> duplicates = new HashSet<>();
        
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            
            if (duplicates.contains(c)) {
                continue;
            }
            
            if (charToIndex.containsKey(c)) {
                charToIndex.remove(c);
                duplicates.add(c);
            } else {
                charToIndex.put(c, i);
            }
        }
        
        return charToIndex.isEmpty() ? -1 : charToIndex.values().iterator().next();
    }
}
```

### Requirement → Code Mapping
- **R0 (Count character occurrences)**: Use a hash map or array to count the frequency of each character
- **R1 (Find first unique character)**: Iterate through the string to find the first character with a count of 1
- **R2 (Return index)**: Return the index of the first unique character, not the character itself
- **R3 (Handle no unique character)**: Return -1 if no unique character exists
- **R4 (Case sensitivity)**: Handle case sensitivity as needed (the problem typically specifies lowercase letters)

### Example Walkthrough
For the string "leetcode":

1. Count frequencies: {'l': 1, 'e': 3, 't': 1, 'c': 1, 'o': 1, 'd': 1}
2. Scan the string:
   - 'l' has frequency 1, so return index 0

For the string "loveleetcode":

1. Count frequencies: {'l': 2, 'o': 2, 'v': 1, 'e': 4, 't': 1, 'c': 1, 'd': 1}
2. Scan the string:
   - 'l' has frequency 2, continue
   - 'o' has frequency 2, continue
   - 'v' has frequency 1, so return index 2

### Complexity Analysis
- **Time Complexity**: O(n) - We make two passes through the string, each taking O(n) time
- **Space Complexity**: O(k) - We use extra space to store the character frequencies, where k is the size of the character set (26 for lowercase English letters)
