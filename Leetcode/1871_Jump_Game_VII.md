# Jump Game VII

## Problem Description

**Problem Link:** [Jump Game VII](https://leetcode.com/problems/jump-game-vii/)

You are given a **0-indexed** binary string `s` and two integers `minJump` and `maxJump`. In the beginning, you are standing at index `0`, which is equal to `'0'`. You can move from index `i` to index `j` if the following conditions are fulfilled:

- `i + minJump <= j <= i + maxJump`
- `s[j] == '0'`

Return `true` *if you can reach index* `s.length - 1`*, or* `false` *otherwise*.

**Example 1:**
```
Input: s = "011010", minJump = 2, maxJump = 3
Output: true
Explanation: In the beginning, you are at index 0.
You can jump from index 0 to index 3 since s[3] == '0'.
You can jump from index 3 to index 5 since s[5] == '0'.
So we return true.
```

**Constraints:**
- `2 <= s.length <= 10^5`
- `s[i]` is either `'0'` or `'1'`
- `s[0] == '0'`
- `1 <= minJump <= maxJump < s.length`

## Intuition/Main Idea

We need to check if we can reach the last index with valid jumps. This is a BFS/DP problem.

**Core Algorithm:**
- Use BFS or DP to track reachable positions
- For each position, mark all valid next positions as reachable
- Optimize with sliding window to avoid TLE

**Why BFS/DP:** We need to explore all possible paths. BFS finds if destination is reachable efficiently.

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|------------------------|-----------------------------------|
| Check reachability | BFS - Lines 7-25 |
| Validate jumps | Range check - Lines 15-20 |
| Track reachable | Visited array - Lines 6, 19 |

## Final Java Code & Learning Pattern (Full Content)

```java
class Solution {
    public boolean canReach(String s, int minJump, int maxJump) {
        int n = s.length();
        if (s.charAt(n - 1) == '1') {
            return false;
        }
        
        boolean[] reachable = new boolean[n];
        reachable[0] = true;
        
        // Sliding window: track how many positions in current window are reachable
        int reachableCount = 1; // Count of reachable positions in current window
        
        for (int i = 1; i < n; i++) {
            // Remove positions that are out of range
            if (i - maxJump - 1 >= 0 && reachable[i - maxJump - 1]) {
                reachableCount--;
            }
            
            // Check if current position is reachable
            if (s.charAt(i) == '0' && reachableCount > 0) {
                reachable[i] = true;
            }
            
            // Add current position to window if it's reachable
            if (i >= minJump - 1 && reachable[i - minJump + 1]) {
                reachableCount++;
            }
        }
        
        return reachable[n - 1];
    }
}
```

## Complexity Analysis

**Time Complexity:** $O(n)$ where $n$ is string length. Single pass with sliding window.

**Space Complexity:** $O(n)$ for reachable array.

## Similar Problems

- [Jump Game](https://leetcode.com/problems/jump-game/) - Similar reachability problem
- [Jump Game II](https://leetcode.com/problems/jump-game-ii/) - Minimum jumps
- [Frog Jump](https://leetcode.com/problems/frog-jump/) - Similar jump constraints

