# The kth Factor of n

## Problem Description

**Problem Link:** [The kth Factor of n](https://leetcode.com/problems/the-kth-factor-of-n/)

You are given two positive integers `n` and `k`.

A factor of an integer `n` is defined as an integer `i` where `n % i == 0`.

Consider a list of all factors of `n` sorted in **ascending order**, return *the* `k`th *factor* in this list or return `-1` if `n` has less than `k` factors.

**Example 1:**
```
Input: n = 12, k = 3
Output: 3
Explanation: Factors list is [1, 2, 3, 4, 6, 12], the 3rd factor is 3.
```

**Example 2:**
```
Input: n = 7, k = 2
Output: 7
Explanation: Factors list is [1, 7], the 2nd factor is 7.
```

**Constraints:**
- `1 <= k <= n <= 1000`

## Intuition/Main Idea

We need to find the kth factor of n. Factors come in pairs: if i is a factor, then n/i is also a factor.

**Core Algorithm:**
- Iterate from 1 to √n
- For each factor i, count it and its pair n/i
- Return kth factor found
- Handle perfect squares (don't count twice)

**Why iterate to √n:** Factors come in pairs, so we only need to check up to √n. This optimizes from O(n) to O(√n).

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|------------------------|-----------------------------------|
| Find kth factor | Factor iteration - Lines 6-20 |
| Count factors | Counter tracking - Lines 7, 11, 15 |
| Handle factor pairs | Pair calculation - Lines 13-16 |

## Final Java Code & Learning Pattern (Full Content)

```java
class Solution {
    public int kthFactor(int n, int k) {
        int count = 0;
        
        // Check factors from 1 to sqrt(n)
        for (int i = 1; i * i <= n; i++) {
            if (n % i == 0) {
                count++;
                if (count == k) {
                    return i;
                }
            }
        }
        
        // Check factors from sqrt(n) down to 1 (the pairs)
        // But skip if i*i == n (perfect square, already counted)
        for (int i = (int) Math.sqrt(n); i >= 1; i--) {
            if (n % i == 0) {
                int pair = n / i;
                // Skip if it's the same factor (perfect square)
                if (pair != i) {
                    count++;
                    if (count == k) {
                        return pair;
                    }
                }
            }
        }
        
        return -1;
    }
}
```

## Complexity Analysis

**Time Complexity:** $O(\sqrt{n})$. We iterate up to √n.

**Space Complexity:** $O(1)$. We only use a constant amount of extra space.

## Similar Problems

- [Count Primes](https://leetcode.com/problems/count-primes/) - Factor-related problem
- [Perfect Number](https://leetcode.com/problems/perfect-number/) - Sum of factors
- [Self Dividing Numbers](https://leetcode.com/problems/self-dividing-numbers/) - Number properties

