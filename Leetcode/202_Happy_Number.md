# Happy Number

## Problem Description

**Problem Link:** [Happy Number](https://leetcode.com/problems/happy-number/)

Write an algorithm to determine if a number `n` is happy.

A **happy number** is a number defined by the following process:

- Starting with any positive integer, replace the number by the sum of the squares of its digits.
- Repeat the process until the number equals 1 (where it will stay), or it **loops endlessly in a cycle** which does not include 1.
- Those numbers for which this process **ends in 1** are happy.

Return `true` *if `n` is a happy number, and `false` if not*.

**Example 1:**
```
Input: n = 19
Output: true
Explanation:
1^2 + 9^2 = 82
8^2 + 2^2 = 68
6^2 + 8^2 = 100
1^2 + 0^2 + 0^2 = 1
```

**Example 2:**
```
Input: n = 2
Output: false
```

**Constraints:**
- `1 <= n <= 2^31 - 1`

## Intuition/Main Idea

This problem can be solved using **Floyd's Cycle Detection Algorithm** (tortoise and hare) or a **HashSet** to detect cycles.

**Key Insight:** 
- If a number is happy, it will eventually reach 1.
- If a number is not happy, it will enter a cycle (we'll see the same number again).
- We can detect cycles using Floyd's algorithm or by storing seen numbers.

**Core Algorithm:**
1. Use two pointers (slow and fast) or a HashSet.
2. Calculate the sum of squares of digits repeatedly.
3. If we reach 1, return true.
4. If we detect a cycle (same number seen twice), return false.

**Why cycle detection works:** The transformation function (sum of squares) will eventually either reach 1 (happy) or repeat a number (cycle), since the number space is bounded.

## Code Mapping

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Calculate sum of squares | `getNext` method - Lines 15-22 |
| Detect cycle | Floyd's algorithm - Lines 8-13 |
| Check if reached 1 | Return condition - Line 11 |
| Handle cycle detection | Loop condition - Line 9 |

## Final Java Code & Learning Pattern

### Floyd's Cycle Detection (Optimal Space)

```java
class Solution {
    public boolean isHappy(int n) {
        int slow = n;
        int fast = getNext(n);
        
        // Floyd's cycle detection
        // If there's a cycle, slow and fast will eventually meet
        // If n is happy, fast will reach 1 before slow
        while (fast != 1 && slow != fast) {
            slow = getNext(slow);
            fast = getNext(getNext(fast));
        }
        
        return fast == 1;
    }
    
    // Calculate sum of squares of digits
    private int getNext(int n) {
        int sum = 0;
        while (n > 0) {
            int digit = n % 10;
            sum += digit * digit;
            n /= 10;
        }
        return sum;
    }
}
```

### HashSet Approach (More Intuitive)

```java
import java.util.*;

class Solution {
    public boolean isHappy(int n) {
        Set<Integer> seen = new HashSet<>();
        
        while (n != 1 && !seen.contains(n)) {
            seen.add(n);
            n = getNext(n);
        }
        
        return n == 1;
    }
    
    private int getNext(int n) {
        int sum = 0;
        while (n > 0) {
            int digit = n % 10;
            sum += digit * digit;
            n /= 10;
        }
        return sum;
    }
}
```

**Explanation of Key Code Sections:**

1. **Floyd's Algorithm (Lines 5-12):**
   - **Two Pointers (Lines 5-6):** Initialize `slow` to `n` and `fast` to `getNext(n)`.
   - **Cycle Detection (Line 9):** Continue while `fast != 1` and `slow != fast`. If they meet, we have a cycle.
   - **Move Pointers (Lines 10-11):** Move `slow` one step and `fast` two steps.
   - **Return (Line 12):** If `fast == 1`, the number is happy.

2. **Get Next Number (Lines 15-22):** Calculate the sum of squares of digits:
   - Extract each digit using modulo and division.
   - Square each digit and add to sum.
   - Return the sum.

**Why Floyd's algorithm works:**
- **Mathematical property:** If there's a cycle, the fast pointer will eventually catch up to the slow pointer.
- **Space efficient:** Uses O(1) space instead of O(k) for HashSet where k is cycle length.
- **Correctness:** If we reach 1, `fast` will be 1. If there's a cycle, `slow` and `fast` will meet.

**Why cycle detection is necessary:**
- **Bounded values:** For any number, the sum of squares is bounded (e.g., for 999, sum is 243).
- **Finite states:** There are only finitely many possible values, so we must eventually repeat.
- **Two outcomes:** Either we reach 1 (happy) or enter a cycle (not happy).

**Example walkthrough for `n = 19`:**
- n=19 → getNext(19) = 1² + 9² = 82
- n=82 → getNext(82) = 8² + 2² = 68
- n=68 → getNext(68) = 6² + 8² = 100
- n=100 → getNext(100) = 1² + 0² + 0² = 1 ✓ (happy)

**Example for `n = 2`:**
- n=2 → 4 → 16 → 37 → 58 → 89 → 145 → 42 → 20 → 4 (cycle detected) ✗

## Complexity Analysis

- **Time Complexity:** $O(\log n)$ for calculating sum of squares, and the cycle detection takes at most O(k) iterations where k is the cycle length. Overall $O(\log n)$.

- **Space Complexity:** 
  - Floyd's: $O(1)$ - only uses two variables.
  - HashSet: $O(k)$ where k is the number of distinct values before cycle or 1.

## Similar Problems

Problems that can be solved using similar cycle detection patterns:

1. **202. Happy Number** (this problem) - Cycle detection
2. **141. Linked List Cycle** - Floyd's cycle detection
3. **142. Linked List Cycle II** - Find cycle start
4. **287. Find the Duplicate Number** - Cycle detection in array
5. **457. Circular Array Loop** - Cycle detection with direction
6. **268. Missing Number** - Can use cycle sort
7. **41. First Missing Positive** - Cycle sort variant
8. **442. Find All Duplicates in an Array** - Cycle detection
9. **448. Find All Numbers Disappeared in an Array** - Cycle detection
10. **645. Set Mismatch** - Cycle detection variant

