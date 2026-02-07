### 139. Word Break
Problem: https://leetcode.com/problems/word-break/

## Problem Mapping to Code

Given a string `s` and a dictionary `wordDict`, we want to check if `s` can be segmented into words from the dictionary.  
We can map the problem requirements to code using **state transition + memoization**:

1. **State Definition**:
    - `start` → current index in string `s` from where we are trying to segment.
    - `memo[start]` → whether the substring `s[start:]` can be segmented.

2. **Base Case**:
    - If `start == s.length()`, return `true`.  
      _This satisfies "we have successfully segmented the whole string."_

3. **Recursion / Transition**:
    - Loop over `end` from `start + 1` to `s.length()`.  
      _This checks every possible prefix starting at `start`._
    - `word = s.substring(start, end)`
    - If `word` is in the dictionary AND `dfs(end)` returns `true`, then `memo[start] = true`.  
      _This satisfies "you can reuse words from the dictionary multiple times."_

4. **Memoization**:
    - Use `Boolean[] memo` to store computed results.
        - `null` → not yet computed
        - `true` / `false` → result for this starting index

---

## Top-Down Java Code

```java
class Solution {
    public boolean wordBreak(String s, List<String> wordDict) {
        Set<String> dict = new HashSet<>(wordDict); // Fast O(1) lookups
        Boolean[] memo = new Boolean[s.length()];
        return dfs(s, 0, dict, memo);
    }

    private boolean dfs(String s, int start, Set<String> dict, Boolean[] memo) {
        // Base case: we reached the end of the string
        if (start == s.length()) {
            return true;
        }

        // Return memoized result if available
        if (memo[start] != null) {
            return memo[start];
        }

        // Try every possible end index for the substring
        for (int end = start + 1; end <= s.length(); end++) {
            String word = s.substring(start, end);
            if (dict.contains(word) && dfs(s, end, dict, memo)) {
                memo[start] = true;
                return true;
            }
        }

        memo[start] = false;
        return false;
    }
}
// T: O(n^2)
// S: O(n)
```
