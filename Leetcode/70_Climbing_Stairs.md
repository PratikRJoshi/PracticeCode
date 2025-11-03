### LC70. Climbing Stairs
#### Problem Statement
### [LC70. climbing Stairs](https://leetcode.com/problems/climbing-stairs/)

You are climbing a staircase. It takes n steps to reach the top.

Each time you can either climb 1 or 2 steps. In how many distinct ways can you climb to the top?

#### Examples

**Example 1:**
```
Input: n = 2
Output: 2
Explanation: There are two ways to climb to the top.
1. 1 step + 1 step
2. 2 steps
```

**Example 2:**
```
Input: n = 3
Output: 3
Explanation: There are three ways to climb to the top.
1. 1 step + 1 step + 1 step
2. 1 step + 2 steps
3. 2 steps + 1 step
```

#### Solution with Recursive Memoization

```java
class Solution {
    public int climbStairs(int n) {
        // Create a memoization array to store already computed results
        int[] memo = new int[n + 1];
        
        // Fill with -1 to indicate results not yet computed
        Arrays.fill(memo, -1);
        
        // Call the recursive helper function
        return climbStairsHelper(n, memo);
    }
    
    private int climbStairsHelper(int n, int[] memo) {
        // Base cases
        if (n == 0) return 1; // Found a valid way
        if (n < 0) return 0;  // Invalid way
        
        // If result already computed, return it
        if (memo[n] != -1) {
            return memo[n];
        }
        
        // Calculate ways by taking either 1 step or 2 steps
        memo[n] = climbStairsHelper(n - 1, memo) + climbStairsHelper(n - 2, memo);
        
        return memo[n];
    }
}
```

#### Intuition
The key insight for this problem is recognizing that to reach step n, we can only come from:
- Step n-1 (by taking 1 step)
- Step n-2 (by taking 2 steps)

This creates a recursive relationship: `ways(n) = ways(n-1) + ways(n-2)`

This is actually the Fibonacci sequence pattern! The number of ways to climb n stairs is the same as the (n+1)th Fibonacci number.

#### Approach Mapping
1. **Base Cases**: 
   - If n = 0, we've reached the top (1 way)
   - If n < 0, we've gone too far (0 ways)

2. **Recursive Relation**: 
   - For any step n, we can either take a 1-step from (n-1) or a 2-step from (n-2)
   - Therefore, ways(n) = ways(n-1) + ways(n-2)

3. **Memoization**: 
   - We use an array `memo[]` to store already computed results
   - Before computing, we check if the result is already in our memo table
   - After computing, we store the result in our memo table

#### Complexity Analysis
- **Time Complexity**: O(n) - Each subproblem is solved exactly once
- **Space Complexity**: O(n) - For the memoization array and recursion stack

#### Alternative Iterative Solution
We can also solve this problem iteratively to avoid recursion overhead:

```java
class Solution {
    public int climbStairs(int n) {
        if (n <= 2) return n;
        
        int[] dp = new int[n + 1];
        dp[1] = 1;
        dp[2] = 2;
        
        for (int i = 3; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        
        return dp[n];
    }
}
```

This iterative solution has the same time and space complexity but avoids the overhead of function calls.
