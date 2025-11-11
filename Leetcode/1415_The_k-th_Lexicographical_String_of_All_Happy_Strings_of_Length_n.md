# The k-th Lexicographical String of All Happy Strings of Length n

## Problem Description

**Problem Link:** [The k-th Lexicographical String of All Happy Strings of Length n](https://leetcode.com/problems/the-k-th-lexicographical-string-of-all-happy-strings-of-length-n/)

A **happy string** is a string that:

- consists only of letters of the set `['a', 'b', 'c']`.
- `s[i] != s[i + 1]` for all values of `i` from `1` to `s.length - 1` (string is 1-indexed).

For example, strings **"abc"**, **"ac"**, **"b"** and **"abcbabcbcb"** are all happy strings and strings **"aa"**, **"baa"** and **"ababbc"** are not happy strings.

Given two integers `n` and `k`, consider a list of all happy strings of length `n` sorted in lexicographical order.

Return *the kth string* of this list or return an **empty string** if there are less than `k` happy strings.

**Example 1:**

```
Input: n = 1, k = 3
Output: "c"
Explanation: The list ["a", "b", "c"] contains 3 happy strings of length 1. The third string is "c".
```

**Example 2:**

```
Input: n = 1, k = 4
Output: ""
Explanation: There are only 3 happy strings of length 1.
```

**Example 3:**

```
Input: n = 3, k = 9
Output: "cab"
Explanation: There are 12 different happy string of length 3 ["aba", "abc", "aca", "acb", "bab", "bac", "bcb", "bca", "cab", "cac", "cba", "cbc"]. The 9th string is "cab".
```

**Constraints:**
- `1 <= n <= 10`
- `1 <= k <= 100`

## Intuition/Main Idea

This is a **backtracking** problem. We need to find the k-th lexicographical happy string.

**Core Algorithm:**
1. Generate happy strings in lexicographical order.
2. Count strings as we generate.
3. When count reaches k, return the string.
4. Prune if we can't reach k strings.

**Why backtracking works:** We generate strings in lexicographical order. When we find the k-th string, we return it. Pruning improves efficiency.

## Code Mapping

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Backtrack function | Backtrack method - Lines 6-25 |
| Base case: length n | Length check - Lines 8-13 |
| Try each character | Character loop - Line 16 |
| Check happy condition | Condition check - Line 17 |
| Add character | Add operation - Line 18 |
| Recurse | Recursive call - Line 19 |
| Backtrack | Remove operation - Line 20 |
| Return result | Return statement - Line 24 |

## Final Java Code & Learning Pattern

```java
class Solution {
    private int count = 0;
    private String result = "";
    
    public String getHappyString(int n, int k) {
        backtrack(n, k, new StringBuilder());
        return result;
    }
    
    private void backtrack(int n, int k, StringBuilder current) {
        // Base case: string length is n
        if (current.length() == n) {
            count++;
            if (count == k) {
                result = current.toString();
            }
            return;
        }
        
        // Try each character in lexicographical order
        for (char ch : new char[]{'a', 'b', 'c'}) {
            // Check happy condition: current character != last character
            if (current.length() == 0 || current.charAt(current.length() - 1) != ch) {
                current.append(ch);
                backtrack(n, k, current);
                if (count >= k) {
                    return; // Early termination
                }
                current.deleteCharAt(current.length() - 1); // Backtrack
            }
        }
    }
}
```

**Explanation of Key Code Sections:**

1. **Base Case (Lines 8-13):** When string length is `n`, increment count. If count equals `k`, save result.

2. **Try Characters (Lines 16-23):** For each character in lexicographical order:
   - **Check Condition (Line 17):** Ensure character differs from last character.
   - **Add (Line 18):** Add character to string.
   - **Recurse (Line 19):** Build next character.
   - **Early Termination (Lines 20-21):** If found k-th string, return early.
   - **Backtrack (Line 22):** Remove character to try next.

**Why backtracking:**
- We need to generate strings in lexicographical order.
- After generating a string, we backtrack to generate the next.
- Early termination improves efficiency.

**Example walkthrough for `n = 3, k = 9`:**
- Generate: "aba", "abc", "aca", "acb", "bab", "bac", "bcb", "bca", "cab" ✓
- Return "cab" ✓

## Complexity Analysis

- **Time Complexity:** $O(3^n)$ in worst case, but early termination improves this.

- **Space Complexity:** $O(n)$ for recursion stack and current string.

## Similar Problems

Problems that can be solved using similar backtracking patterns:

1. **1415. The k-th Lexicographical String of All Happy Strings of Length n** (this problem) - Backtracking with counting
2. **784. Letter Case Permutation** - Backtracking with transformations
3. **17. Letter Combinations of a Phone Number** - Backtracking combinations
4. **22. Generate Parentheses** - Backtracking generation
5. **1238. Circular Permutation in Binary Representation** - Binary generation
6. **1980. Find Unique Binary String** - Binary string generation
7. **46. Permutations** - Generate permutations
8. **77. Combinations** - Generate combinations

