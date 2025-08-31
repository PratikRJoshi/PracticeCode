### 91. Decode Ways
Problem: https://leetcode.com/problems/decode-ways/

## Mapping the Problem to Code

We can solve this problem using **top-down memoization (DP with recursion)** similar to the "Best Time to Buy and Sell Stock" problems, because we are dealing with **choices at each index** and can cache results for subproblems.

---

### Step 1: Understand the Requirements

1. We decode **1 character** at a time, e.g., "1" → 'A'.
2. We can also decode **2 characters** together if they form a valid number between 10 and 26, e.g., "25" → 'Y'.
3. Strings starting with '0' are **invalid**.
4. We need to count **all valid decoding ways**.

This naturally maps to a **recursive function**:

- State: `index` (current position in the string)
- Choices:
    - Take 1 digit if valid
    - Take 2 digits if valid
- Memoization: store `Integer[index]` → number of ways to decode the substring starting at `index`.

---

### Step 2: Map Requirements to Code Lines

| Requirement | Line(s) in Code |
|-------------|----------------|
| Decode 1 digit | `if(s.charAt(index) != '0') count += dfs(index + 1, s, memo);` |
| Decode 2 digits (10–26) | `if(index + 1 < s.length() && valid 2-digit) count += dfs(index + 2, s, memo);` |
| Return 0 if index exceeds string | `if(index == s.length()) return 1;` |
| Memoization to avoid recomputation | `Integer[] memo` and check `memo[index] != null` |

---

### Step 3: Top-Down Memoization Code

```java
class Solution {
    public int numDecodings(String s) {
        if(s == null || s.length() == 0){
            return 0;
        }

        Integer[] memo = new Integer[s.length()];
        return dfs(s, 0, memo);
    }

    private int dfs(String s, int index, Integer[] memo){
        if(index == s.length()){
            return 1;
        }
        if(memo[index] != null){
            return memo[index];
        }
        if(s.charAt(index) == '0'){
            return 0;
        }

        int count = 0;

        // decode single digit
        count += dfs(s, index + 1, memo);

        // decode 2 digits, if valid
        if(index + 1 < s.length()){
            int twoDigit = Integer.parseInt(s.substring(index, index + 2));
            if(twoDigit >= 10 && twoDigit <= 26){
                count += dfs(s, index + 2, memo);
            }
        }

        memo[index] = count;
        return count;
    }
}
// T: O(n) -> iterating over string once
// S: O(n) -> space for memoization
```
