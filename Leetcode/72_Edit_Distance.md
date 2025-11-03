### 72. Edit Distance
### Problem Link: [Edit Distance](https://leetcode.com/problems/edit-distance/)
### Intuition
The Edit Distance problem, also known as the Levenshtein Distance, asks for the minimum number of operations required to convert one string to another. The allowed operations are:
1. Insert a character
2. Delete a character
3. Replace a character

This is a classic dynamic programming problem. The key insight is to build a 2D table where `dp[i][j]` represents the minimum number of operations required to convert the first `i` characters of word1 to the first `j` characters of word2. We can fill this table iteratively by considering the minimum cost of the three operations at each step.

### Java Reference Implementation
```java
class Solution {
    public int minDistance(String word1, String word2) {
        int m = word1.length();
        int n = word2.length();
        
        // Handle edge cases
        if (m == 0) return n;
        if (n == 0) return m;
        
        // Create a DP table
        int[][] dp = new int[m + 1][n + 1];
        
        // Initialize the first row and column
        for (int i = 0; i <= m; i++) {
            dp[i][0] = i; // Delete i characters from word1
        }
        for (int j = 0; j <= n; j++) {
            dp[0][j] = j; // Insert j characters from word2
        }
        
        // Fill the DP table
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    // Characters match, no operation needed
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    // Calculate the minimum cost of the three operations
                    int replace = dp[i - 1][j - 1] + 1; // Replace
                    int delete = dp[i - 1][j] + 1;      // Delete
                    int insert = dp[i][j - 1] + 1;      // Insert
                    
                    dp[i][j] = Math.min(replace, Math.min(delete, insert));
                }
            }
        }
        
        return dp[m][n];
    }
}
```

### Alternative Implementation (Space-Optimized)
```java
class Solution {
    public int minDistance(String word1, String word2) {
        int m = word1.length();
        int n = word2.length();
        
        // Handle edge cases
        if (m == 0) return n;
        if (n == 0) return m;
        
        // Use two arrays to optimize space
        int[] prev = new int[n + 1];
        int[] curr = new int[n + 1];
        
        // Initialize the first row
        for (int j = 0; j <= n; j++) {
            prev[j] = j;
        }
        
        // Fill the DP table
        for (int i = 1; i <= m; i++) {
            curr[0] = i; // First column of current row
            
            for (int j = 1; j <= n; j++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    // Characters match, no operation needed
                    curr[j] = prev[j - 1];
                } else {
                    // Calculate the minimum cost of the three operations
                    int replace = prev[j - 1] + 1; // Replace
                    int delete = prev[j] + 1;      // Delete
                    int insert = curr[j - 1] + 1;  // Insert
                    
                    curr[j] = Math.min(replace, Math.min(delete, insert));
                }
            }
            
            // Swap arrays for the next iteration
            int[] temp = prev;
            prev = curr;
            curr = temp;
        }
        
        return prev[n];
    }
}
```

### Requirement → Code Mapping
- **R0 (Handle edge cases)**: If one string is empty, the edit distance is the length of the other string
- **R1 (Initialize DP table)**: Set up the base cases for empty strings
- **R2 (Character match)**: If characters match, no operation is needed, so `dp[i][j] = dp[i-1][j-1]`
- **R3 (Character mismatch)**: Calculate the minimum cost of replace, delete, and insert operations
- **R4 (Return result)**: The final answer is at `dp[m][n]`

### Example Walkthrough
For the strings "horse" and "ros":

1. Initialize the DP table:
   ```
     | "" | r | o | s
   ---|---|---|---|---
   "" | 0 | 1 | 2 | 3
   h  | 1 | ? | ? | ?
   o  | 2 | ? | ? | ?
   r  | 3 | ? | ? | ?
   s  | 4 | ? | ? | ?
   e  | 5 | ? | ? | ?
   ```

2. Fill the table:
   - For "h" and "r": Different, so min(1+1, 1+1, 0+1) = 1+1 = 2
   - For "h" and "ro": Different, so min(2+1, 2+1, 1+1) = 1+1 = 2
   - And so on...

3. Final DP table:
   ```
     | "" | r | o | s
   ---|---|---|---|---
   "" | 0 | 1 | 2 | 3
   h  | 1 | 1 | 2 | 3
   o  | 2 | 2 | 1 | 2
   r  | 3 | 2 | 2 | 2
   s  | 4 | 3 | 3 | 2
   e  | 5 | 4 | 4 | 3
   ```

4. The answer is `dp[5][3] = 3`, which means we need 3 operations:
   - Replace "h" with "r"
   - Delete "r"
   - Delete "e"

### Complexity Analysis
- **Time Complexity**: O(m * n) - We fill a 2D table of size m * n
- **Space Complexity**: O(m * n) for the standard implementation, O(n) for the space-optimized version
