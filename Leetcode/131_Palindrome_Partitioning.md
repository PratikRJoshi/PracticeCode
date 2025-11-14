# Palindrome Partitioning

## Problem Description

**Problem Link:** [Palindrome Partitioning](https://leetcode.com/problems/palindrome-partitioning/)

Given a string `s`, partition `s` such that every substring of the partition is a **palindrome**. Return *all possible palindrome partitioning of `s`*.

**Example 1:**
```
Input: s = "aab"
Output: [["a","a","b"],["aa","b"]]
```

**Example 2:**
```
Input: s = "a"
Output: [["a"]]
```

**Constraints:**
- `1 <= s.length <= 16`
- `s` contains only lowercase English letters.

## Intuition/Main Idea

This is a **backtracking** problem. We need to partition the string into palindromic substrings.

**Core Algorithm:**
1. Use backtracking to try all possible partitions.
2. For each position, try all possible substrings starting from that position.
3. If a substring is a palindrome, add it to current partition and recurse.
4. When we've processed the entire string, add the partition to results.
5. Backtrack by removing the last added substring.

**Why backtracking works:** We need to explore all possible ways to partition the string. Backtracking systematically tries all possibilities by building partial partitions and undoing choices when needed.

## Code Mapping

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Store current partition | List in backtrack - Line 12 |
| Check if substring is palindrome | `isPalindrome` method - Lines 30-37 |
| Try all possible substrings | For loop - Lines 15-23 |
| Add palindrome and recurse | Backtrack call - Lines 19-21 |
| Backtrack by removing | Remove last element - Line 22 |
| Add complete partition | Result addition - Line 14 |
| Return all partitions | Return statement - Line 10 |

## Final Java Code & Learning Pattern

```java
import java.util.*;

class Solution {
    public List<List<String>> partition(String s) {
        List<List<String>> result = new ArrayList<>();
        backtrack(s, 0, new ArrayList<>(), result);
        return result;
    }
    
    private void backtrack(String s, int start, List<String> current, List<List<String>> result) {
        // Base case: processed entire string
        if (start == s.length()) {
            result.add(new ArrayList<>(current));
            return;
        }
        
        // Try all possible substrings starting from 'start'
        for (int end = start; end < s.length(); end++) {
            // Check if substring s[start..end] is a palindrome
            if (isPalindrome(s, start, end)) {
                // Choose: add palindrome substring to current partition
                current.add(s.substring(start, end + 1));
                
                // Explore: recurse for remaining string
                // CRITICAL: We use 'end + 1' here, NOT 'start + 1'
                // This is because we've just processed the substring from start to end,
                // so the next substring should begin at position (end + 1).
                // If we used 'start + 1' instead, we would:
                // 1. Skip portions of the string (if end > start + 1)
                // 2. Create overlapping partitions, which is invalid for this problem
                // 3. Never reach the base case for some inputs, causing infinite recursion
                // For example, with s = "aab", if we used 'start + 1' after finding "a" is a palindrome,
                // we would process "a" again instead of moving to "a" and "b", missing valid partitions.
                backtrack(s, end + 1, current, result);
                
                // Unchoose: remove last added substring (backtrack)
                current.remove(current.size() - 1);
            }
        }
    }
    
    // Check if substring s[start..end] is a palindrome
    private boolean isPalindrome(String s, int start, int end) {
        while (start < end) {
            if (s.charAt(start) != s.charAt(end)) {
                return false;
            }
            start++;
            end--;
        }
        return true;
    }
}
```

**Optimized Version with Memoization:**

```java
import java.util.*;

class Solution {
    public List<List<String>> partition(String s) {
        List<List<String>> result = new ArrayList<>();
        // Memoization: cache palindrome checks
        Boolean[][] memo = new Boolean[s.length()][s.length()];
        backtrack(s, 0, new ArrayList<>(), result, memo);
        return result;
    }
    
    private void backtrack(String s, int start, List<String> current, 
                           List<List<String>> result, Boolean[][] memo) {
        if (start == s.length()) {
            result.add(new ArrayList<>(current));
            return;
        }
        
        for (int end = start; end < s.length(); end++) {
            if (isPalindrome(s, start, end, memo)) {
                current.add(s.substring(start, end + 1));
                
                // CRITICAL: We use 'end + 1' here, NOT 'start + 1'
                // Using 'end + 1' ensures we move to the next unprocessed character after
                // the current palindrome substring (s[start...end]).
                // If we incorrectly used 'start + 1' here:
                // 1. We would process overlapping substrings, which violates the partitioning requirement
                // 2. We might never finish processing the entire string
                // 3. We would generate invalid partitions
                //
                // Example with s = "aab":
                // - When start=0, end=0: We find "a" is palindrome
                //   - Using end+1: We recurse with start=1, correctly moving to next character
                //   - Using start+1: We would also recurse with start=1, but we'd be ignoring
                //     the fact that we just included "a" in our partition
                // - When start=0, end=1: We find "aa" is palindrome
                //   - Using end+1: We recurse with start=2, correctly moving after "aa"
                //   - Using start+1: We would recurse with start=1, which would be wrong
                //     because we've already included "aa" in our partition
                backtrack(s, end + 1, current, result, memo);
                
                current.remove(current.size() - 1);
            }
        }
    }
    
    private boolean isPalindrome(String s, int start, int end, Boolean[][] memo) {
        if (memo[start][end] != null) {
            return memo[start][end];
        }
        
        if (start >= end) {
            return true;
        }
        
        boolean result = (s.charAt(start) == s.charAt(end)) && 
                        isPalindrome(s, start + 1, end - 1, memo);
        memo[start][end] = result;
        return result;
    }
}
```

**Explanation of Key Code Sections:**

1. **Backtrack Method (Lines 7-25):**
   - **Base Case (Lines 8-11):** When we've processed the entire string (`start == s.length()`), we have a valid partition. Add a copy to results.
   - **Try All Substrings (Lines 13-23):** For each possible ending position `end`:
     - **Check Palindrome (Line 15):** Verify if `s[start..end]` is a palindrome.
     - **Choose (Line 17):** Add the palindrome substring to current partition.
     - **Explore (Line 20):** Recursively process the remaining string starting from `end+1`.
     - **Unchoose (Line 22):** Remove the last added substring to backtrack.

2. **Palindrome Check (Lines 28-36):** Use two pointers to check if a substring is a palindrome.

**Why backtracking works:**
- **Systematic exploration:** We try all possible partitions by considering all substrings at each position.
- **Efficient:** We only explore paths where current substring is a palindrome.
- **Complete:** We explore all valid partitions.

**Example walkthrough for `s = "aab"`:**
- Start at 0: Try "a" (palindrome) → recurse with start=1
  - Start at 1: Try "a" (palindrome) → recurse with start=2
    - Start at 2: Try "b" (palindrome) → add ["a","a","b"]
  - Start at 1: Try "ab" (not palindrome) → skip
- Start at 0: Try "aa" (palindrome) → recurse with start=2
  - Start at 2: Try "b" (palindrome) → add ["aa","b"]
- Start at 0: Try "aab" (not palindrome) → skip

## Complexity Analysis

- **Time Complexity:** $O(2^n \times n)$ in the worst case. There are $2^n$ possible partitions, and each takes $O(n)$ to build and check palindromes.

- **Space Complexity:** $O(n)$ for the recursion stack and current partition. The result list takes $O(2^n \times n)$ space.

## Similar Problems

Problems that can be solved using similar backtracking patterns:

1. **131. Palindrome Partitioning** (this problem) - Backtracking with palindrome check
2. **132. Palindrome Partitioning II** - Minimum cuts (DP)
3. **93. Restore IP Addresses** - Backtracking with validation
4. **22. Generate Parentheses** - Backtracking with constraints
5. **17. Letter Combinations of a Phone Number** - Backtracking combinations
6. **39. Combination Sum** - Backtracking with repetition
7. **40. Combination Sum II** - Backtracking with duplicates
8. **46. Permutations** - Backtracking permutations
9. **78. Subsets** - Backtracking subsets
10. **90. Subsets II** - Backtracking with duplicates
