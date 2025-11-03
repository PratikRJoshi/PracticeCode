### 1143. Longest Common Subsequence
Problem: https://leetcode.com/problems/longest-common-subsequence/description/

---

### Main Idea & Intuition

The Longest Common Subsequence (LCS) problem asks us to find the length of the longest subsequence that appears in both input strings. A subsequence is a sequence that can be derived from another sequence by deleting some or no elements without changing the order of the remaining elements.

**Intuitive Explanation:**

Think of comparing two strings character by character from the end. At each position, we have two choices:
1. If characters match → include this character in our LCS and check the remaining prefixes
2. If they don't match → try skipping a character from either string and take the better result

The memoization approach stores results of subproblems to avoid redundant calculations. We use indices `i` and `j` to represent "consider the first i characters of text1 and first j characters of text2."

**Why memo array is [m+1][n+1] (not [m][n]):**

The array needs an extra row and column (size m+1 by n+1) to handle the base case of empty strings:
- `memo[0][j]` represents comparing an empty text1 prefix with text2 prefix of length j → LCS = 0
- `memo[i][0]` represents comparing text1 prefix of length i with an empty text2 prefix → LCS = 0

This allows us to use indices 1 to m and 1 to n for actual string positions, where `memo[i][j]` represents the LCS of the first i characters of text1 and first j characters of text2. When we access `text1.charAt(i-1)` and `text2.charAt(j-1)`, we're comparing the actual characters at positions i-1 and j-1 in the 0-indexed strings.

Without this +1 padding, we'd need special boundary checks for i=0 or j=0, making the code more complex.

```java
class Solution {
    // Memoization cache to store results of subproblems
    private Integer[][] memo;
    private String text1;
    private String text2;
    
    public int longestCommonSubsequence(String text1, String text2) {
        this.text1 = text1;
        this.text2 = text2;
        int m = text1.length();
        int n = text2.length();
        
        // Initialize memoization array with null values
        memo = new Integer[m + 1][n + 1];
        
        // Start the recursive calculation from the beginning of both strings
        return lcsHelper(m, n);
    }
    
    private int lcsHelper(int i, int j) {
        // Base case: if either string is empty, LCS is 0
        if (i == 0 || j == 0) {
            return 0;
        }
        
        // If we've already computed this subproblem, return the cached result
        if (memo[i][j] != null) {
            return memo[i][j];
        }
        
        // If the current characters match
        if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
            // Include this character in LCS and recursively solve for the rest
            memo[i][j] = 1 + lcsHelper(i - 1, j - 1);
        } else {
            // Characters don't match, take the maximum by either:
            // 1. Excluding current character from text1, or
            // 2. Excluding current character from text2
            memo[i][j] = Math.max(lcsHelper(i - 1, j), lcsHelper(i, j - 1));
        }
        
        return memo[i][j];
    }
}
```



### Complexity Analysis

For the standard 2D DP approach:

* **Time Complexity**: `O(m * n)` where `m` and `n` are the lengths of the two strings. We need to fill each cell in the DP table exactly once.

* **Space Complexity**: `O(m * n)` for the 2D DP table.

For the space-optimized approach:

* **Time Complexity**: Still `O(m * n)` as we process each combination of characters once.

* **Space Complexity**: `O(min(m, n))` as we only need two arrays of length equal to the shorter string.

### Learning the Dynamic Programming Pattern

The LCS problem demonstrates a classic DP pattern that can be applied to many string comparison problems:

1. **Identify Overlapping Subproblems**: Notice that finding the LCS of smaller prefixes helps solve the problem for larger ones.

2. **Define State Clearly**: `dp[i][j]` represents a specific subproblem - the LCS of prefixes ending at positions i-1 and j-1.

3. **Establish Base Cases**: Empty strings have an LCS of 0.

4. **Develop Recurrence Relation**: The decision at each step depends on whether the current characters match.

5. **Determine Filling Order**: Bottom-up approach ensures all dependencies are computed before needed.

This pattern can be adapted for variations like:
- Finding the actual LCS (not just its length)
- Longest Common Substring (consecutive characters)
- Edit Distance
- Shortest Common Supersequence
