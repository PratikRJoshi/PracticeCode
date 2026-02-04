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

---

## Understanding the `for` loop in Word Break

### 1) Why `for(int end = index + 1; end <= s.length(); end++)`?

**What the loop does:**
- It tries **every possible substring** starting from `index`.
- `end` represents where the substring **ends** (exclusive, because `substring(index, end)` doesn't include `s.charAt(end)`).

**Why `index + 1`?**
- You need at least **one character** to form a word.
- If `index = 0`, then `end = 1` gives you `s.substring(0, 1)` → first character.

**Why `end <= s.length()`?**
- `s.length()` is a valid endpoint because `substring(index, s.length())` gives you everything from `index` to the end of the string.
- Example: if `s = "leetcode"` and `index = 4`, then `end = 8` gives `substring(4, 8)` → `"code"`.

**Concrete example:**
```
s = "leetcode", index = 0

end = 1 → substring(0, 1) = "l"       → check if "l" is in dict
end = 2 → substring(0, 2) = "le"      → check if "le" is in dict
end = 3 → substring(0, 3) = "lee"     → check if "lee" is in dict
end = 4 → substring(0, 4) = "leet"    → check if "leet" is in dict ✓
         → if found, recurse from index = 4
end = 5 → substring(0, 5) = "leetc"   → check if "leetc" is in dict
...
end = 8 → substring(0, 8) = "leetcode" → check if "leetcode" is in dict
```

---

### 2) Why `memo[index] = true` instead of `memo[end] = true`?

**What does `memo[index]` mean?**
- `memo[index]` stores the answer to: **"Can we break `s[index...]` (the substring from `index` to the end) into valid words?"**

**Why store at `index`, not `end`?**
- The **question** you're answering is: "Starting from position `index`, can we break the rest of the string?"
- When you find a valid word from `index` to `end`, and the recursive call `wordBreak(s, dict, end, memo)` returns `true`, it means:
  - The substring `s[index..end)` is a valid word, **AND**
  - The rest of the string starting from `end` can also be broken into words.
- So the **entire substring starting from `index`** can be broken → store `true` at `memo[index]`.

**Concrete example:**
```
s = "leetcode", dict = ["leet", "code"]

Call: wordBreak(s, dict, 0, memo)
  → Try end = 4: substring(0, 4) = "leet" ✓ in dict
  → Recurse: wordBreak(s, dict, 4, memo)
      → Try end = 8: substring(4, 8) = "code" ✓ in dict
      → Recurse: wordBreak(s, dict, 8, memo)
          → index == s.length() → return true
      → memo[4] = true (because starting from index 4, we can break "code")
      → return true
  → memo[0] = true (because starting from index 0, we can break "leetcode")
  → return true
```

**Why not `memo[end]`?**
- `end` is just a **temporary loop variable** showing where the current substring ends.
- The recursive call `wordBreak(s, dict, end, memo)` will handle memoizing `memo[end]` when it's called.
- You're responsible for memoizing the **current problem** (`index`), not the subproblem (`end`).

---

### Mental model (simple)

Think of it like this:
- **"I'm at position `index`. Can I break the rest of the string?"**
- Try cutting the string at different points (`end`).
- If I find a valid word **and** the rest can also be broken, then **yes, I can break from `index`**.
- Store that answer at `memo[index]` so I don't recompute it.
