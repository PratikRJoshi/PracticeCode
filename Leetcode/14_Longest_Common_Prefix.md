# [14. Longest Common Prefix](https://leetcode.com/problems/longest-common-prefix/)

Write a function to find the longest common prefix string amongst an array of strings.

If there is no common prefix, return an empty string `""`.

**Example 1:**

```
Input: strs = ["flower","flow","flight"]
Output: "fl"
```

**Example 2:**

```
Input: strs = ["dog","racecar","car"]
Output: ""
Explanation: There is no common prefix among the input strings.
```

**Constraints:**

- `1 <= strs.length <= 200`
- `0 <= strs[i].length <= 200`
- `strs[i]` consists of only lowercase English letters.

## Intuition/Main Idea:

To find the longest common prefix among an array of strings, we need to compare characters at the same position across all strings. There are several approaches to solve this problem:

1. **Horizontal Scanning**: Compare the prefix of the first string with each subsequent string, shortening the prefix as needed.
2. **Vertical Scanning**: Compare characters at the same position for all strings.
3. **Divide and Conquer**: Recursively find the common prefix of subgroups of strings.
4. **Binary Search**: Use binary search to find the length of the common prefix.

The horizontal scanning approach is intuitive and efficient for this problem. We start with the first string as our prefix and then iterate through the remaining strings, updating the prefix to be the common part between the current prefix and the current string.

## Code Mapping:

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Find the longest common prefix | The entire solution implements this logic |
| Return empty string if no common prefix | `if (strs.length == 0) return "";` and `if (prefix.length() == 0) return "";` |

## Final Java Code & Learning Pattern:

```java
class Solution {
    public String longestCommonPrefix(String[] strs) {
        // Edge case: empty array
        if (strs.length == 0) {
            return "";
        }
        
        // Start with the first string as the prefix
        String prefix = strs[0];
        
        // Iterate through the remaining strings
        for (int i = 1; i < strs.length; i++) {
            // Keep reducing the prefix until it's a prefix of the current string
            while (strs[i].indexOf(prefix) != 0) {
                prefix = prefix.substring(0, prefix.length() - 1);
                // If the prefix becomes empty, there is no common prefix
                if (prefix.isEmpty()) {
                    return "";
                }
            }
        }
        
        return prefix;
    }
}
```

This solution uses the horizontal scanning approach:

1. We start with the first string as our prefix.
2. For each subsequent string, we check if our current prefix is a prefix of that string.
3. If not, we shorten our prefix by removing the last character, and check again.
4. We continue this process until either:
   - The prefix becomes a prefix of the current string, or
   - The prefix becomes empty, in which case there is no common prefix.
5. After processing all strings, the remaining prefix is the longest common prefix.

The key insight is that the longest common prefix can only get shorter as we consider more strings, never longer.

## Alternative Implementations:

### Vertical Scanning Approach:

```java
class Solution {
    public String longestCommonPrefix(String[] strs) {
        if (strs == null || strs.length == 0) {
            return "";
        }
        
        // Iterate through characters of the first string
        for (int i = 0; i < strs[0].length(); i++) {
            char c = strs[0].charAt(i);
            
            // Compare with the same character in all other strings
            for (int j = 1; j < strs.length; j++) {
                // If we've reached the end of a string or found a mismatch
                if (i >= strs[j].length() || strs[j].charAt(i) != c) {
                    return strs[0].substring(0, i);
                }
            }
        }
        
        // If we get here, the entire first string is a prefix
        return strs[0];
    }
}
```

This approach compares characters vertically (character by character) across all strings. It's more efficient when the strings have a very short common prefix or when the array contains a very short string.

### Divide and Conquer Approach:

```java
class Solution {
    public String longestCommonPrefix(String[] strs) {
        if (strs == null || strs.length == 0) {
            return "";
        }
        return longestCommonPrefix(strs, 0, strs.length - 1);
    }
    
    private String longestCommonPrefix(String[] strs, int start, int end) {
        if (start == end) {
            return strs[start];
        }
        
        int mid = (start + end) / 2;
        String leftPrefix = longestCommonPrefix(strs, start, mid);
        String rightPrefix = longestCommonPrefix(strs, mid + 1, end);
        
        return commonPrefix(leftPrefix, rightPrefix);
    }
    
    private String commonPrefix(String left, String right) {
        int min = Math.min(left.length(), right.length());
        for (int i = 0; i < min; i++) {
            if (left.charAt(i) != right.charAt(i)) {
                return left.substring(0, i);
            }
        }
        return left.substring(0, min);
    }
}
```

This approach divides the array of strings into two halves, recursively finds the common prefix for each half, and then finds the common prefix of the two results.

## Complexity Analysis:

- **Time Complexity**: 
  - Horizontal Scanning: O(S) where S is the sum of all characters in all strings.
  - Vertical Scanning: O(S) in the worst case, but can be better when the common prefix is very short.
  - Divide and Conquer: O(S * log n) where n is the number of strings.
  
- **Space Complexity**: 
  - Horizontal Scanning: O(1) extra space (not counting the output).
  - Vertical Scanning: O(1) extra space.
  - Divide and Conquer: O(m * log n) where m is the maximum string length and n is the number of strings, due to the recursion stack.

## Similar Problems:

1. [208. Implement Trie (Prefix Tree)](https://leetcode.com/problems/implement-trie-prefix-tree/)
2. [1065. Index Pairs of a String](https://leetcode.com/problems/index-pairs-of-a-string/)
3. [1268. Search Suggestions System](https://leetcode.com/problems/search-suggestions-system/)
4. [1048. Longest String Chain](https://leetcode.com/problems/longest-string-chain/)
5. [720. Longest Word in Dictionary](https://leetcode.com/problems/longest-word-in-dictionary/)
