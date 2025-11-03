### 93. Restore IP Addresses
### Problem Link: [Restore IP Addresses](https://leetcode.com/problems/restore-ip-addresses/)
### Intuition
This problem asks us to find all possible valid IP addresses that can be formed by inserting three dots into a string of digits. A valid IP address consists of four numbers separated by dots, where each number is between 0 and 255, and cannot have leading zeros (except for the number 0 itself).

The key insight is to use backtracking to try all possible positions for placing the three dots. We need to ensure that each segment is valid according to IP address rules.

### Java Reference Implementation
```java
import java.util.ArrayList;
import java.util.List;

class Solution {
    public List<String> restoreIpAddresses(String s) {
        List<String> result = new ArrayList<>();
        backtrack(s, 0, 0, new StringBuilder(), result);
        return result;
    }
    
    private void backtrack(String s, int index, int segments, StringBuilder current, List<String> result) {
        // Base case: if we've used all characters and formed 4 segments, we have a valid IP
        if (index == s.length() && segments == 4) {
            // Remove the trailing dot
            result.add(current.substring(0, current.length() - 1));
            return;
        }
        
        // If we've already formed 4 segments or used all characters, but not both, return
        if (segments == 4 || index == s.length()) {
            return;
        }
        
        // Get the current length to backtrack later
        int len = current.length();
        
        // Try forming segments of length 1 to 3
        for (int i = 1; i <= 3 && index + i <= s.length(); i++) {
            String segment = s.substring(index, index + i);
            
            // Check if the segment is valid
            if (isValidSegment(segment)) {
                // Add the segment and a dot
                current.append(segment).append(".");
                
                // Recursively try to form the next segment
                backtrack(s, index + i, segments + 1, current, result);
                
                // Backtrack: remove the segment and dot
                current.setLength(len);
            }
        }
    }
    
    private boolean isValidSegment(String segment) {
        // Segment must not be empty
        if (segment.isEmpty()) {
            return false;
        }
        
        // Segment must not have leading zeros (unless it's just "0")
        if (segment.length() > 1 && segment.charAt(0) == '0') {
            return false;
        }
        
        // Segment must be between 0 and 255
        int value = Integer.parseInt(segment);
        return value >= 0 && value <= 255;
    }
}
```

### Requirement → Code Mapping
- **R0 (Initialize result list)**: `List<String> result = new ArrayList<>();`
- **R1 (Start backtracking)**: `backtrack(s, 0, 0, new StringBuilder(), result);`
- **R2 (Base case)**: `if (index == s.length() && segments == 4) { ... }` - We've used all characters and formed 4 segments
- **R3 (Early termination)**: `if (segments == 4 || index == s.length()) { return; }` - We've either formed too many segments or used all characters
- **R4 (Try different segment lengths)**: `for (int i = 1; i <= 3 && index + i <= s.length(); i++) { ... }` - Try segments of length 1 to 3
- **R5 (Validate segment)**: `isValidSegment(segment)` - Check if the segment is a valid IP address component
- **R6 (Backtracking)**: `current.setLength(len);` - Revert changes to try different possibilities

### Complexity
- **Time Complexity**: O(1) - Since the input is constrained (IP addresses have at most 12 digits), the number of possible combinations is bounded
- **Space Complexity**: O(1) - The recursion depth and result size are bounded by the constraints of the problem
