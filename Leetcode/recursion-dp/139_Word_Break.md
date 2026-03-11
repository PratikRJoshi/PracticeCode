# Word Break

## Problem Description

**Problem Link:** [Word Break](https://leetcode.com/problems/word-break/)

Given string `s` and dictionary `wordDict`, return `true` if `s` can be segmented into a sequence of dictionary words.

## Intuition/Main Idea

1D DP on string index.

### Subproblem definition
`canBreak(start)` = can substring `s[start..]` be segmented?

### State transition
Try all end positions:
- if `s[start..end]` is a dictionary word and `canBreak(end + 1)` is true, then `canBreak(start)` is true.

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|-------------------------|-------------------------------------|
| Segment entire string | base `start == s.length()` returns true |
| Use dictionary words only | substring check against hash set |
| Reuse overlapping states | memo by start index |

## Final Java Code & Learning Pattern

### Top-Down / Memoized Version

```java
import java.util.*;

class Solution {
    public boolean wordBreak(String s, List<String> wordDict) {
        Set<String> dictionary = new HashSet<>(wordDict);

        // Size s.length()+1 because state start can be 0..s.length().
        Boolean[] memo = new Boolean[s.length() + 1];
        return dfs(0, s, dictionary, memo);
    }

    private boolean dfs(int start, String s, Set<String> dictionary, Boolean[] memo) {
        if (start == s.length()) {
            return true;
        }
        if (memo[start] != null) {
            return memo[start];
        }

        for (int end = start + 1; end <= s.length(); end++) {
            if (dictionary.contains(s.substring(start, end)) && dfs(end, s, dictionary, memo)) {
                memo[start] = true;
                return true;
            }
        }

        memo[start] = false;
        return false;
    }
}
```

### Bottom-Up Version

Bottom-up usually avoids recursion overhead and is interview-friendly.

```java
import java.util.*;

class Solution {
    public boolean wordBreak(String s, List<String> wordDict) {
        Set<String> dictionary = new HashSet<>(wordDict);
        int n = s.length();

        // Size n+1 because dp[i] means prefix s[0..i-1] is segmentable.
        boolean[] dp = new boolean[n + 1];
        dp[0] = true;

        for (int end = 1; end <= n; end++) {
            for (int start = 0; start < end; start++) {
                if (dp[start] && dictionary.contains(s.substring(start, end))) {
                    dp[end] = true;
                    break;
                }
            }
        }

        return dp[n];
    }
}
```

## Complexity Analysis

Let `n = s.length()`.

- **Time Complexity:** $O(n^3)$ in worst case with substring operations.
- **Space Complexity:** $O(n)$ for DP/memo (excluding dictionary storage).

## Similar Problems

1. [140. Word Break II](https://leetcode.com/problems/word-break-ii/)
2. [322. Coin Change](https://leetcode.com/problems/coin-change/)
