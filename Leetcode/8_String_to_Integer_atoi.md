### 8. String to Integer (atoi)
### Problem Link: [String to Integer (atoi)](https://leetcode.com/problems/string-to-integer-atoi/)
### Intuition
This problem asks us to implement the `atoi` (ASCII to Integer) function, which converts a string to a 32-bit signed integer. The function needs to handle various edge cases and follow specific rules:

1. Discard leading whitespace
2. Handle optional plus or minus sign
3. Read digits until a non-digit character is encountered
4. Handle overflow/underflow
5. Return 0 if no valid conversion could be performed

The key insight is to carefully process the string character by character, keeping track of the sign and the accumulated value, and checking for overflow/underflow at each step.

### Java Reference Implementation
```java
class Solution {
    public int myAtoi(String s) {
        if (s == null || s.isEmpty()) {
            return 0;
        }
        
        // Step 1: Discard leading whitespace
        int index = 0;
        while (index < s.length() && s.charAt(index) == ' ') {
            index++;
        }
        
        // Check if we've reached the end of the string
        if (index == s.length()) {
            return 0;
        }
        
        // Step 2: Check for sign
        int sign = 1;
        if (s.charAt(index) == '+' || s.charAt(index) == '-') {
            sign = (s.charAt(index) == '-') ? -1 : 1;
            index++;
        }
        
        // Step 3: Read digits and convert to integer
        int result = 0;
        while (index < s.length() && Character.isDigit(s.charAt(index))) {
            int digit = s.charAt(index) - '0';
            
            // Step 4: Check for overflow/underflow
            if (result > Integer.MAX_VALUE / 10 || 
                (result == Integer.MAX_VALUE / 10 && digit > Integer.MAX_VALUE % 10)) {
                return (sign == 1) ? Integer.MAX_VALUE : Integer.MIN_VALUE;
            }
            
            result = result * 10 + digit;
            index++;
        }
        
        return result * sign;
    }
}
```

### Alternative Implementation (Using Long)
```java
class Solution {
    public int myAtoi(String s) {
        if (s == null || s.isEmpty()) {
            return 0;
        }
        
        s = s.trim(); // Remove leading and trailing whitespace
        
        if (s.isEmpty()) {
            return 0;
        }
        
        // Check for sign
        int sign = 1;
        int index = 0;
        if (s.charAt(0) == '+' || s.charAt(0) == '-') {
            sign = (s.charAt(0) == '-') ? -1 : 1;
            index++;
        }
        
        // Read digits and convert to long
        long result = 0;
        while (index < s.length() && Character.isDigit(s.charAt(index))) {
            result = result * 10 + (s.charAt(index) - '0');
            
            // Check for overflow/underflow
            if (result * sign > Integer.MAX_VALUE) {
                return Integer.MAX_VALUE;
            }
            if (result * sign < Integer.MIN_VALUE) {
                return Integer.MIN_VALUE;
            }
            
            index++;
        }
        
        return (int) (result * sign);
    }
}
```

### Requirement → Code Mapping
- **R0 (Discard leading whitespace)**: `s = s.trim()` or use a loop to skip whitespace characters
- **R1 (Handle sign)**: Check for '+' or '-' at the beginning of the string after trimming
- **R2 (Read digits)**: Use a loop to read consecutive digits and build the result
- **R3 (Stop at non-digit)**: Break the loop when a non-digit character is encountered
- **R4 (Handle overflow/underflow)**: Check if the result exceeds the 32-bit integer range and return INT_MAX or INT_MIN accordingly

### Example Walkthrough
For the string "  -42":

1. Discard leading whitespace: "-42"
2. Check for sign: sign = -1, index = 1
3. Read digits:
   - digit = 4, result = 0 * 10 + 4 = 4
   - digit = 2, result = 4 * 10 + 2 = 42
4. No more digits, return result * sign = -42

For the string "4193 with words":

1. Discard leading whitespace: "4193 with words"
2. No sign, sign = 1, index = 0
3. Read digits:
   - digit = 4, result = 0 * 10 + 4 = 4
   - digit = 1, result = 4 * 10 + 1 = 41
   - digit = 9, result = 41 * 10 + 9 = 419
   - digit = 3, result = 419 * 10 + 3 = 4193
4. Encounter space (non-digit), stop reading
5. Return result * sign = 4193

### Complexity Analysis
- **Time Complexity**: O(n) - We process each character in the string at most once
- **Space Complexity**: O(1) - We use a constant amount of extra space
