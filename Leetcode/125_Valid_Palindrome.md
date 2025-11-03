### 125. Valid Palindrome
### Problem Link: [Valid Palindrome](https://leetcode.com/problems/valid-palindrome/)
### Intuition
This problem asks us to determine if a string is a palindrome, considering only alphanumeric characters and ignoring case. The approach is to use a two-pointer technique: one pointer starts from the beginning of the string and the other from the end. We move these pointers towards each other, skipping non-alphanumeric characters, and check if the characters at both pointers match.

### Java Reference Implementation
```java
class Solution {
    public boolean isPalindrome(String s) {
        if (s == null || s.isEmpty()) {
            return true;
        }
        
        // Convert to lowercase and remove non-alphanumeric characters
        String cleanString = s.toLowerCase().replaceAll("[^a-z0-9]", "");
        
        // Two-pointer approach
        int left = 0;
        int right = cleanString.length() - 1;
        
        while (left < right) {
            if (cleanString.charAt(left) != cleanString.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        
        return true;
    }
}
```

### Alternative Implementation (Without using regex)
```java
class Solution {
    public boolean isPalindrome(String s) {
        if (s == null || s.isEmpty()) {
            return true;
        }
        
        int left = 0;
        int right = s.length() - 1;
        
        while (left < right) {
            // Skip non-alphanumeric characters from left
            while (left < right && !Character.isLetterOrDigit(s.charAt(left))) {
                left++;
            }
            
            // Skip non-alphanumeric characters from right
            while (left < right && !Character.isLetterOrDigit(s.charAt(right))) {
                right--;
            }
            
            // Compare characters (case insensitive)
            if (Character.toLowerCase(s.charAt(left)) != Character.toLowerCase(s.charAt(right))) {
                return false;
            }
            
            left++;
            right--;
        }
        
        return true;
    }
}
```

### Requirement → Code Mapping
- **R0 (Handle edge cases)**: `if (s == null || s.isEmpty()) { return true; }`
- **R1 (Clean the string)**: `String cleanString = s.toLowerCase().replaceAll("[^a-z0-9]", "");` or use the character-by-character approach in the alternative solution
- **R2 (Two-pointer approach)**: Initialize `left = 0` and `right = length - 1`
- **R3 (Compare characters)**: `if (cleanString.charAt(left) != cleanString.charAt(right)) { return false; }`
- **R4 (Move pointers)**: `left++; right--;`

### Complexity
- **Time Complexity**: O(n) - We process each character in the string at most once
- **Space Complexity**: O(n) for the first solution (creating a new string), O(1) for the alternative solution (in-place comparison)
