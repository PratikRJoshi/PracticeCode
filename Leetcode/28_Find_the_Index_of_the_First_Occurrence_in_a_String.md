### 28. Find the Index of the First Occurrence in a String
### Problem Link: [Find the Index of the First Occurrence in a String](https://leetcode.com/problems/find-the-index-of-the-first-occurrence-in-a-string/)
### Intuition
This problem asks us to find the index of the first occurrence of a substring (needle) in a string (haystack). The brute force approach is to check every possible starting position in the haystack and see if the needle matches from that position. More efficient algorithms like KMP (Knuth-Morris-Pratt) or Boyer-Moore can be used for larger strings, but the brute force approach is sufficient for most cases.

### Java Reference Implementation (Brute Force)
```java
class Solution {
    public int strStr(String haystack, String needle) {
        // Edge cases
        if (needle.isEmpty()) {
            return 0;
        }
        
        if (haystack.isEmpty()) {
            return -1;
        }
        
        int haystackLength = haystack.length();
        int needleLength = needle.length();
        
        // Check all potential starting positions
        for (int i = 0; i <= haystackLength - needleLength; i++) {
            int j;
            
            // Check if needle matches starting from position i
            for (j = 0; j < needleLength; j++) {
                if (haystack.charAt(i + j) != needle.charAt(j)) {
                    break;
                }
            }
            
            // If we've matched the entire needle
            if (j == needleLength) {
                return i;
            }
        }
        
        return -1;
    }
}
```

### Alternative Implementation (Using Java's Built-in Methods)
```java
class Solution {
    public int strStr(String haystack, String needle) {
        return haystack.indexOf(needle);
    }
}
```

### Implementation Using KMP Algorithm (More Efficient)
```java
class Solution {
    public int strStr(String haystack, String needle) {
        if (needle.isEmpty()) {
            return 0;
        }
        
        int m = haystack.length();
        int n = needle.length();
        
        // Create the LPS array (Longest Proper Prefix which is also Suffix)
        int[] lps = new int[n];
        computeLPSArray(needle, n, lps);
        
        int i = 0; // Index for haystack
        int j = 0; // Index for needle
        
        while (i < m) {
            // Current characters match
            if (needle.charAt(j) == haystack.charAt(i)) {
                i++;
                j++;
            }
            
            // Found the pattern
            if (j == n) {
                return i - j;
            } 
            // Mismatch after j matches
            else if (i < m && needle.charAt(j) != haystack.charAt(i)) {
                // Do not match lps[0..lps[j-1]] characters, they will match anyway
                if (j != 0) {
                    j = lps[j - 1];
                } else {
                    i++;
                }
            }
        }
        
        return -1;
    }
    
    private void computeLPSArray(String pattern, int m, int[] lps) {
        int len = 0; // Length of the previous longest prefix & suffix
        int i = 1;
        
        lps[0] = 0; // lps[0] is always 0
        
        while (i < m) {
            if (pattern.charAt(i) == pattern.charAt(len)) {
                len++;
                lps[i] = len;
                i++;
            } else {
                if (len != 0) {
                    len = lps[len - 1];
                } else {
                    lps[i] = 0;
                    i++;
                }
            }
        }
    }
}
```

### Requirement → Code Mapping
- **R0 (Handle edge cases)**: Check if needle is empty (return 0) or haystack is empty (return -1)
- **R1 (Brute force approach)**: Iterate through all possible starting positions in haystack
- **R2 (Match checking)**: For each position, check if needle matches from that position
- **R3 (Return index)**: Return the index of the first occurrence, or -1 if not found
- **R4 (KMP algorithm)**: Use the KMP algorithm for more efficient string matching

### Complexity
- **Time Complexity**: 
  - Brute Force: O(m*n) where m is the length of haystack and n is the length of needle
  - KMP Algorithm: O(m+n)
- **Space Complexity**: 
  - Brute Force: O(1)
  - KMP Algorithm: O(n) for the LPS array
