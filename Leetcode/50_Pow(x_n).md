### 50. Pow(x, n)
Problem: https://leetcode.com/problems/powx-n/

---

Below is a compact mapping from **problem requirements → code** followed by an efficient recursive Java solution.

## Main Idea / Intuition

The goal is to calculate `x` raised to the power of `n`. A naive approach would be to multiply `x` by itself `n` times in a loop. This would be too slow (`O(n)`) for large `n`.

**The Efficient Approach: Exponentiation by Squaring**

The key insight is to break the problem down by repeatedly halving the exponent `n`. This is a classic **Divide and Conquer** strategy.

1.  **If `n` is even:** `x^n` is the same as `(x^2)^(n/2)` or `(x^(n/2))^2`. We can calculate `x^(n/2)` once and then square the result.
2.  **If `n` is odd:** `x^n` is the same as `x * x^(n-1)`. Since `n-1` is now even, we can write this as `x * (x^((n-1)/2))^2`.

This recursive relationship allows us to reduce the problem size by half at each step, leading to a much faster `O(log n)` solution.

**Handling Edge Cases:**
*   **`n = 0`:** Any number to the power of 0 is 1.
*   **`n < 0`:** A negative exponent means we take the reciprocal: `x^-n = 1 / x^n`.
*   **`n = Integer.MIN_VALUE`:** This is a tricky case because `-Integer.MIN_VALUE` overflows a standard `int`. The easiest way to handle this is to convert `n` to a `long` before making it positive.

---

## Full Java Code (Recursive)

```java
class Solution {
    public double myPow(double x, int n) {
        // Convert n to a long to handle the Integer.MIN_VALUE case gracefully.
        long N = n;
        
        if (N < 0) {
            x = 1 / x;
            N = -N;
        }
        
        return power(x, N);
    }

    /**
     * Helper function to compute x^n using exponentiation by squaring.
     * Assumes n is non-negative.
     */
    private double power(double x, long n) {
        // Base case: Anything to the power of 0 is 1.
        if (n == 0) {
            return 1.0;
        }

        // Recursively compute power for n/2.
        double half = power(x, n / 2);

        // If n is even, result is half * half.
        if (n % 2 == 0) {
            return half * half;
        } else {
            // If n is odd, result is x * half * half.
            return x * half * half;
        }
    }
}
```

---

## Mapping of Requirements → Code

*   **Implement `pow(x, n)`:** The `myPow` method is the public entry point.
*   **Handle negative exponents:** The `if (N < 0)` block (lines 6-9) correctly inverts `x` and makes the exponent positive.
*   **Handle `n = 0`:** The base case `if (n == 0)` in the `power` helper function (line 18) returns `1.0`.
*   **Efficient computation for large `n`:** The recursive calls in the `power` function (line 21) divide the problem by half at each step.
*   **Handle `Integer.MIN_VALUE`:** Converting `n` to a `long N` at the start (line 3) prevents overflow when `n` is `Integer.MIN_VALUE` and we calculate `-n`.
*   **Distinguish even/odd exponents:** The `if (n % 2 == 0)` block (lines 24-28) implements the core logic of the algorithm.

---

## Complexity

*   **Time Complexity:** `O(log n)`. At each step of the recursion, we divide `n` by 2. The number of such divisions is logarithmic with respect to `n`.
*   **Space Complexity:** `O(log n)`. This is the space used by the recursion stack. The depth of the recursion is `log n`.

---