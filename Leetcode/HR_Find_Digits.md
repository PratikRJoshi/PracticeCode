### HackerRank: Find Digits
### Problem Link: [Find Digits](https://www.hackerrank.com/challenges/find-digits/problem)
### Intuition
This problem asks us to count the number of digits in a given number N that divide N evenly (without a remainder). The key insight is to extract each digit of the number and check if it divides the original number. We need to be careful about handling the digit 0, as division by 0 is undefined.

### Java Reference Implementation
```java
import java.util.*;

public class Solution {
    public static int findDigits(int n) {
        int count = 0;
        int temp = n;
        
        while (temp > 0) {
            int digit = temp % 10;
            
            // Check if digit divides n evenly (and is not 0)
            if (digit != 0 && n % digit == 0) {
                count++;
            }
            
            temp /= 10;
        }
        
        return count;
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int t = scanner.nextInt();
        
        for (int i = 0; i < t; i++) {
            int n = scanner.nextInt();
            int result = findDigits(n);
            System.out.println(result);
        }
        
        scanner.close();
    }
}
```

### Requirement → Code Mapping
- **R0 (Extract digits)**: `int digit = temp % 10;` - Get the last digit of the number
- **R1 (Check divisibility)**: `if (digit != 0 && n % digit == 0) { count++; }` - Check if the digit divides the original number evenly
- **R2 (Handle edge case)**: Skip digits that are 0, as division by 0 is undefined
- **R3 (Process all digits)**: `temp /= 10;` - Move to the next digit by integer division

### Example
For the number 1012:
1. Digit 2: 1012 % 2 = 0, so 2 divides 1012 evenly. Count = 1
2. Digit 1: 1012 % 1 = 0, so 1 divides 1012 evenly. Count = 2
3. Digit 0: Skip (division by 0 is undefined)
4. Digit 1: 1012 % 1 = 0, so 1 divides 1012 evenly. Count = 3

So the answer is 3.

### Complexity
- **Time Complexity**: O(log n) - We process each digit of the number, and the number of digits is logarithmic in n
- **Space Complexity**: O(1) - We use a constant amount of extra space
