### 49. Group Anagrams
### Problem Link: [Group Anagrams](https://leetcode.com/problems/group-anagrams/)
### Intuition
This problem asks us to group anagrams together from an array of strings. Anagrams are words or phrases formed by rearranging the letters of a different word or phrase, using all the original letters exactly once.

The key insight is that anagrams will have the same characters, just in a different order. Therefore, if we sort the characters of each string, all anagrams will have the same sorted string. We can use this sorted string as a key in a hash map to group anagrams together.

### Java Reference Implementation
```java
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Solution {
    public List<List<String>> groupAnagrams(String[] strs) {
        if (strs == null || strs.length == 0) {
            return new ArrayList<>();
        }
        
        Map<String, List<String>> anagramGroups = new HashMap<>();
        
        for (String str : strs) {
            // Sort the characters of the string to create a key
            char[] chars = str.toCharArray();
            Arrays.sort(chars);
            String key = String.valueOf(chars);
            
            // Add the original string to its anagram group
            if (!anagramGroups.containsKey(key)) {
                anagramGroups.put(key, new ArrayList<>());
            }
            anagramGroups.get(key).add(str);
        }
        
        // Convert the map values to a list of lists
        return new ArrayList<>(anagramGroups.values());
    }
}
```

### Alternative Implementation (Using Character Count)
```java
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Solution {
    public List<List<String>> groupAnagrams(String[] strs) {
        if (strs == null || strs.length == 0) {
            return new ArrayList<>();
        }
        
        Map<String, List<String>> anagramGroups = new HashMap<>();
        
        for (String str : strs) {
            // Create a key based on character counts
            int[] counts = new int[26]; // Assuming lowercase English letters
            for (char c : str.toCharArray()) {
                counts[c - 'a']++;
            }
            
            // Build the key string
            StringBuilder keyBuilder = new StringBuilder();
            for (int i = 0; i < 26; i++) {
                if (counts[i] > 0) {
                    keyBuilder.append((char) ('a' + i)).append(counts[i]);
                }
            }
            String key = keyBuilder.toString();
            
            // Add the original string to its anagram group
            if (!anagramGroups.containsKey(key)) {
                anagramGroups.put(key, new ArrayList<>());
            }
            anagramGroups.get(key).add(str);
        }
        
        // Convert the map values to a list of lists
        return new ArrayList<>(anagramGroups.values());
    }
}
```

### Requirement → Code Mapping
- **R0 (Group anagrams)**: Use a hash map to group strings with the same sorted characters
- **R1 (Create a key)**: Sort the characters of each string to create a unique key for anagrams
- **R2 (Handle edge cases)**: Check if the input array is null or empty
- **R3 (Return result)**: Return a list of lists, where each inner list contains a group of anagrams
- **R4 (Optimize for large strings)**: For very large strings, consider using character counts instead of sorting

### Example Walkthrough
For the array `["eat", "tea", "tan", "ate", "nat", "bat"]`:

1. Process "eat":
   - Sorted: "aet"
   - Add to map: {"aet": ["eat"]}

2. Process "tea":
   - Sorted: "aet"
   - Add to map: {"aet": ["eat", "tea"]}

3. Process "tan":
   - Sorted: "ant"
   - Add to map: {"aet": ["eat", "tea"], "ant": ["tan"]}

4. Process "ate":
   - Sorted: "aet"
   - Add to map: {"aet": ["eat", "tea", "ate"], "ant": ["tan"]}

5. Process "nat":
   - Sorted: "ant"
   - Add to map: {"aet": ["eat", "tea", "ate"], "ant": ["tan", "nat"]}

6. Process "bat":
   - Sorted: "abt"
   - Add to map: {"aet": ["eat", "tea", "ate"], "ant": ["tan", "nat"], "abt": ["bat"]}

7. Return the values: [["eat", "tea", "ate"], ["tan", "nat"], ["bat"]]

### Complexity Analysis
- **Time Complexity**: O(n * k log k), where n is the number of strings and k is the maximum length of a string. Sorting each string takes O(k log k) time.
- **Space Complexity**: O(n * k) for storing the hash map and the result
