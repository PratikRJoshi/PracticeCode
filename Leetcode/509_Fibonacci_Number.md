### 509. Fibonacci Number
### Problem Link: [Fibonacci Number](https://leetcode.com/problems/fibonacci-number/)
### Intuition
The Fibonacci sequence is defined as F(0) = 0, F(1) = 1, and F(n) = F(n-1) + F(n-2) for n > 1. This problem asks us to calculate the nth Fibonacci number. There are several approaches to solve this problem, including recursion, dynamic programming, and matrix exponentiation.

Additionally, there's an interesting mathematical property that can be used to check if a number is a Fibonacci number: a number is a Fibonacci number if and only if one or both of (5*n² + 4) or (5*n² - 4) is a perfect square.

### Java Reference Implementation (Dynamic Programming)
```java
class Solution {
    public int fib(int n) {
        if (n <= 1) {
            return n;
        }
        
        int[] dp = new int[n + 1];
        dp[0] = 0;
        dp[1] = 1;
        
        for (int i = 2; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        
        return dp[n];
    }
}
```

### Alternative Implementation (Space-Optimized)
```java
class Solution {
    public int fib(int n) {
        if (n <= 1) {
            return n;
        }
        
        int prev = 0;
        int curr = 1;
        
        for (int i = 2; i <= n; i++) {
            int next = prev + curr;
            prev = curr;
            curr = next;
        }
        
        return curr;
    }
}
```

### Implementation to Check if a Number is Fibonacci
```java
class Solution {
    public boolean isFibonacciNumber(int n) {
        // A number is Fibonacci if and only if one or both of 
        // (5*n² + 4) or (5*n² - 4) is a perfect square
        return isPerfectSquare(5 * n * n + 4) || isPerfectSquare(5 * n * n - 4);
    }
    
    private boolean isPerfectSquare(int num) {
        int sqrt = (int) Math.sqrt(num);
        return sqrt * sqrt == num;
    }
}
```

### Alternative Implementation (Matrix Exponentiation - Efficient for Large n)
```java
class Solution {
    public int fib(int n) {
        if (n <= 1) {
            return n;
        }
        
        int[][] F = {{1, 1}, {1, 0}};
        power(F, n - 1);
        
        return F[0][0];
    }
    
    private void power(int[][] F, int n) {
        if (n <= 1) {
            return;
        }
        
        int[][] M = {{1, 1}, {1, 0}};
        
        power(F, n / 2);
        multiply(F, F);
        
        if (n % 2 != 0) {
            multiply(F, M);
        }
    }
    
    private void multiply(int[][] F, int[][] M) {
        int a = F[0][0] * M[0][0] + F[0][1] * M[1][0];
        int b = F[0][0] * M[0][1] + F[0][1] * M[1][1];
        int c = F[1][0] * M[0][0] + F[1][1] * M[1][0];
        int d = F[1][0] * M[0][1] + F[1][1] * M[1][1];
        
        F[0][0] = a;
        F[0][1] = b;
        F[1][0] = c;
        F[1][1] = d;
    }
}
```

### Requirement → Code Mapping
- **R0 (Base cases)**: `if (n <= 1) { return n; }` - Handle the base cases F(0) = 0 and F(1) = 1
- **R1 (Recurrence relation)**: `dp[i] = dp[i - 1] + dp[i - 2];` - Apply the Fibonacci recurrence relation
- **R2 (Space optimization)**: `int next = prev + curr;` and updating prev and curr - Use only O(1) space
- **R3 (Check if Fibonacci)**: `isPerfectSquare(5 * n * n + 4) || isPerfectSquare(5 * n * n - 4)` - Use the mathematical property

### Complexity Analysis
- **Time Complexity**:
  - Dynamic Programming: O(n)
  - Space-Optimized: O(n)
  - Matrix Exponentiation: O(log n)
  - Check if Fibonacci: O(1)
- **Space Complexity**:
  - Dynamic Programming: O(n)
  - Space-Optimized: O(1)
  - Matrix Exponentiation: O(1)
  - Check if Fibonacci: O(1)
