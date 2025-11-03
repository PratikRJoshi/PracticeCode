### 50. Pow(x, n)
### Problem Link: [Pow(x, n)](https://leetcode.com/problems/powx-n/)
### Intuition
This problem asks us to implement the pow(x, n) function, which calculates x raised to the power of n (x^n). A naive approach would be to multiply x by itself n times, but this would be inefficient for large values of n. 

The key insight is to use the binary exponentiation algorithm (also known as exponentiation by squaring), which reduces the number of multiplications from O(n) to O(log n). The idea is to break down the calculation of x^n into smaller subproblems:
- If n is even, x^n = (x^(n/2))^2
- If n is odd, x^n = x * (x^(n/2))^2

We also need to handle negative exponents by using the relationship: x^(-n) = 1 / (x^n).

### Java Reference Implementation
```java
class Solution {
    public double myPow(double x, int n) {
        if (n < 0) {
            // Handle negative exponents
            return 1 / power(x, -n);
        } else {
            return power(x, n);
        }
    }
    
    private double power(double x, int n) {
        // Base case
        if (n == 0) {
            return 1;
        }
        
        // Recursive case
        double half = power(x, n / 2);
        
        if (n % 2 == 0) {
            // If n is even
            return half * half;
        } else {
            // If n is odd
            return half * half * x;
        }
    }
}
```

### Alternative Implementation (Iterative)
```java
class Solution {
    public double myPow(double x, int n) {
        // Handle edge cases
        if (n == 0) {
            return 1;
        }
        
        // Convert to long to handle Integer.MIN_VALUE
        long N = n;
        if (N < 0) {
            x = 1 / x;
            N = -N;
        }
        
        double result = 1;
        double currentProduct = x;
        
        // Binary exponentiation
        while (N > 0) {
            if (N % 2 == 1) {
                // If the current bit is 1, multiply the result by the current product
                result *= currentProduct;
            }
            // Square the current product for the next bit
            currentProduct *= currentProduct;
            // Move to the next bit
            N /= 2;
        }
        
        return result;
    }
}
```

### Requirement → Code Mapping
- **R0 (Handle negative exponents)**: `if (n < 0) { return 1 / power(x, -n); }` - Convert negative exponents to positive and take the reciprocal
- **R1 (Base case)**: `if (n == 0) { return 1; }` - Any number raised to the power of 0 is 1
- **R2 (Binary exponentiation)**: `double half = power(x, n / 2);` - Divide the problem into smaller subproblems
- **R3 (Handle even exponents)**: `if (n % 2 == 0) { return half * half; }` - For even exponents, square the result of the subproblem
- **R4 (Handle odd exponents)**: `else { return half * half * x; }` - For odd exponents, multiply by an extra x

### Complexity Analysis
- **Time Complexity**: O(log n) - Each recursive call reduces the exponent by half
- **Space Complexity**: O(log n) - The recursion stack has a depth of log n
