### 9. Palindrome Number
### Problem Link: [Palindrome Number](https://leetcode.com/problems/palindrome-number/)
### Intuition
This problem asks us to determine if a given integer is a palindrome. A palindrome is a number that reads the same backward as forward.

The key insight is to compare the digits from both ends of the number. We can extract the rightmost digit using modulo 10 (`x % 10`), and the leftmost digit by dividing by the appropriate power of 10. After comparing these digits, we remove them from the number and continue the process until all digits are compared.

Another approach is to reverse the entire number and check if it equals the original number, but this could lead to overflow issues.

### Java Reference Implementation (Two-Pointer Approach)
```java
class Solution {
    public boolean isPalindrome(int x) {
        // Negative numbers are not palindromes
        if (x < 0) {
            return false;
        }
        
        // Find the highest power of 10 to get the leftmost digit
        int div = 1;
        while (x / div >= 10) {
            div *= 10;
        }
        
        while (x > 0) {
            int leftDigit = x / div;
            int rightDigit = x % 10;
            
            // If the leftmost and rightmost digits don't match, it's not a palindrome
            if (leftDigit != rightDigit) {
                return false;
            }
            
            // Remove the leftmost and rightmost digits
            x = (x % div) / 10;
            div /= 100;
        }
        
        return true;
    }
}
```

### Alternative Implementation (Reverse Half of the Number)
```java
class Solution {
    public boolean isPalindrome(int x) {
        // Special cases:
        // - Negative numbers are not palindromes
        // - Numbers ending with 0 are only palindromes if they are 0
        if (x < 0 || (x % 10 == 0 && x != 0)) {
            return false;
        }
        
        int reversedHalf = 0;
        
        // Reverse the second half of the number
        while (x > reversedHalf) {
            reversedHalf = reversedHalf * 10 + x % 10;
            x /= 10;
        }
        
        // For even number of digits: x == reversedHalf
        // For odd number of digits: x == reversedHalf / 10 (to ignore the middle digit)
        return x == reversedHalf || x == reversedHalf / 10;
    }
}
```

### Alternative Implementation (Convert to String)
```java
class Solution {
    public boolean isPalindrome(int x) {
        // Convert to string and check if it's a palindrome
        String str = String.valueOf(x);
        int left = 0;
        int right = str.length() - 1;
        
        while (left < right) {
            if (str.charAt(left) != str.charAt(right)) {
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
- **R0 (Handle negative numbers)**: `if (x < 0) { return false; }` - Negative numbers are not palindromes
- **R1 (Find leftmost digit)**: Use a loop to find the highest power of 10 that doesn't exceed the number
- **R2 (Compare digits)**: Compare the leftmost and rightmost digits
- **R3 (Remove processed digits)**: Remove the leftmost and rightmost digits after comparison
- **R4 (Optimize)**: The second implementation only reverses half the number, which is more efficient

### Example Walkthrough
For the number 12321:

1. div = 10000
2. First iteration:
   - leftDigit = 12321 / 10000 = 1
   - rightDigit = 12321 % 10 = 1
   - They match, so continue
   - x = (12321 % 10000) / 10 = 232
   - div = 10000 / 100 = 100
3. Second iteration:
   - leftDigit = 232 / 100 = 2
   - rightDigit = 232 % 10 = 2
   - They match, so continue
   - x = (232 % 100) / 10 = 3
   - div = 100 / 100 = 1
4. Third iteration:
   - leftDigit = 3 / 1 = 3
   - rightDigit = 3 % 10 = 3
   - They match, so continue
   - x = (3 % 1) / 10 = 0
   - div = 1 / 100 = 0
5. x is now 0, so we exit the loop and return true

### Complexity Analysis
- **Time Complexity**: O(log n) - We process each digit of the number
- **Space Complexity**: O(1) - We use a constant amount of extra space
