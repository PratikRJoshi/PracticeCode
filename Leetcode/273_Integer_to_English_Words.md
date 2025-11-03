### 273. Integer to English Words
### Problem Link: [Integer to English Words](https://leetcode.com/problems/integer-to-english-words/)
### Intuition
This problem requires converting a non-negative integer to its English word representation. The key insight is to break down the number into groups of three digits (billions, millions, thousands, and ones) and handle each group separately. Within each group, we need to handle hundreds, tens, and ones places.

### Java Reference Implementation
```java
class Solution {
    private final String[] LESS_THAN_20 = {"", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen", "Nineteen"};
    private final String[] TENS = {"", "", "Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninety"};
    private final String[] THOUSANDS = {"", "Thousand", "Million", "Billion"};
    
    public String numberToWords(int num) {
        if (num == 0) {
            return "Zero";
        }
        
        String words = "";
        int i = 0;
        
        while (num > 0) {
            if (num % 1000 != 0) {
                words = helper(num % 1000) + THOUSANDS[i] + " " + words;
            }
            num /= 1000;
            i++;
        }
        
        return words.trim();
    }
    
    private String helper(int num) {
        if (num == 0) {
            return "";
        } else if (num < 20) {
            return LESS_THAN_20[num] + " ";
        } else if (num < 100) {
            return TENS[num / 10] + " " + helper(num % 10);
        } else {
            return LESS_THAN_20[num / 100] + " Hundred " + helper(num % 100);
        }
    }
}
```

### Requirement → Code Mapping
- **R0 (Handle zero)**: `if (num == 0) { return "Zero"; }`
- **R1 (Define word arrays)**: Create arrays for numbers less than 20, tens, and thousands
- **R2 (Process by thousands)**: Break the number into groups of three digits and process each group
- **R3 (Handle hundreds)**: For numbers >= 100, add the hundreds place word followed by "Hundred"
- **R4 (Handle tens)**: For numbers >= 20, use the tens array and recursively handle the ones place
- **R5 (Handle less than 20)**: For numbers < 20, use the LESS_THAN_20 array

### Complexity
- **Time Complexity**: O(log n) - We process each digit in the number
- **Space Complexity**: O(1) - We use a constant amount of extra space for the word arrays and recursion stack
