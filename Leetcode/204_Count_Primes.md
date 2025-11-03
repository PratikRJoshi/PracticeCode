### 204. Count Primes
### Problem Link: [Count Primes](https://leetcode.com/problems/count-primes/)
### Intuition
This problem asks us to count the number of prime numbers less than a given non-negative integer n. The most efficient approach is to use the Sieve of Eratosthenes algorithm, which works by iteratively marking the multiples of each prime number as composite (not prime), starting from 2. After the algorithm completes, all unmarked numbers are prime.

### Java Reference Implementation (Sieve of Eratosthenes)
```java
class Solution {
    public int countPrimes(int n) {
        if (n <= 2) {
            return 0;
        }
        
        // Initialize a boolean array to track prime numbers
        // isPrime[i] = true means i is prime
        boolean[] isPrime = new boolean[n];
        for (int i = 2; i < n; i++) {
            isPrime[i] = true;
        }
        
        // Apply the Sieve of Eratosthenes
        for (int i = 2; i * i < n; i++) {
            if (isPrime[i]) {
                // Mark all multiples of i as non-prime
                for (int j = i * i; j < n; j += i) {
                    isPrime[j] = false;
                }
            }
        }
        
        // Count the number of primes
        int count = 0;
        for (int i = 2; i < n; i++) {
            if (isPrime[i]) {
                count++;
            }
        }
        
        return count;
    }
}
```

### Alternative Implementation (Optimized Sieve)
```java
class Solution {
    public int countPrimes(int n) {
        if (n <= 2) {
            return 0;
        }
        
        // Use a boolean array to mark composite numbers
        // isComposite[i] = true means i is composite (not prime)
        boolean[] isComposite = new boolean[n];
        
        // Initialize count with n-2 (all numbers from 2 to n-1)
        // We'll decrement this count for each composite number we find
        int count = n - 2;
        
        // Apply the Sieve of Eratosthenes
        for (int i = 2; i * i < n; i++) {
            if (!isComposite[i]) {
                // Mark all multiples of i as composite
                for (int j = i * i; j < n; j += i) {
                    if (!isComposite[j]) {
                        isComposite[j] = true;
                        count--;
                    }
                }
            }
        }
        
        return count;
    }
}
```

### Requirement → Code Mapping
- **R0 (Handle edge cases)**: `if (n <= 2) { return 0; }` - Return 0 for n <= 2 as there are no primes less than 2
- **R1 (Initialize prime array)**: `boolean[] isPrime = new boolean[n];` - Create a boolean array to track prime numbers
- **R2 (Apply Sieve of Eratosthenes)**: `for (int j = i * i; j < n; j += i) { isPrime[j] = false; }` - Mark multiples as non-prime
- **R3 (Optimization)**: Start marking from i*i since smaller multiples would have been marked already
- **R4 (Count primes)**: Count the number of unmarked (prime) numbers in the array

### Complexity Analysis
- **Time Complexity**: O(n log log n) - This is the time complexity of the Sieve of Eratosthenes algorithm
- **Space Complexity**: O(n) - We use a boolean array of size n to track prime numbers
