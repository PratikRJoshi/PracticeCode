### 514. Freedom Trail
### Problem Link: [Freedom Trail](https://leetcode.com/problems/freedom-trail/)
### Intuition
This problem asks us to find the minimum number of steps to spell out a target string by rotating a ring and selecting characters. The key insight is to use dynamic programming to find the optimal solution.

For each character in the target string, we need to decide which occurrence of that character in the ring to use. We want to minimize the total number of rotations plus the number of key presses. This is a classic optimization problem where we need to consider all possible choices at each step.

### Java Reference Implementation
```java
class Solution {
    public int findRotateSteps(String ring, String key) {
        int n = ring.length();
        int m = key.length();
        
        // [R0] Create a map to store all positions of each character in the ring
        Map<Character, List<Integer>> charToIndices = new HashMap<>();
        for (int i = 0; i < n; i++) {
            char c = ring.charAt(i);
            charToIndices.putIfAbsent(c, new ArrayList<>());
            charToIndices.get(c).add(i);
        }
        
        // [R1] Initialize DP array: dp[i][j] represents the minimum steps to spell key[i...] when the ring is at position j
        int[][] dp = new int[m + 1][n];
        
        // [R2] Fill the DP array bottom-up
        for (int i = m - 1; i >= 0; i--) {
            char targetChar = key.charAt(i);
            
            for (int j = 0; j < n; j++) {
                dp[i][j] = Integer.MAX_VALUE;
                
                // [R3] Try all occurrences of the target character in the ring
                for (int nextPos : charToIndices.getOrDefault(targetChar, Collections.emptyList())) {
                    // [R4] Calculate the minimum rotation distance
                    int diff = Math.abs(j - nextPos);
                    int rotateSteps = Math.min(diff, n - diff);
                    
                    // [R5] Total steps = rotation steps + 1 (for the key press) + minimum steps for the rest of the key
                    dp[i][j] = Math.min(dp[i][j], rotateSteps + 1 + dp[i + 1][nextPos]);
                }
            }
        }
        
        // [R6] Return the minimum steps starting from position 0
        return dp[0][0];
    }
}
```

### Alternative Implementation (Top-down with Memoization)
```java
class Solution {
    private Map<Character, List<Integer>> charToIndices;
    private Integer[][] memo;
    private String ring;
    private String key;
    
    public int findRotateSteps(String ring, String key) {
        int n = ring.length();
        int m = key.length();
        this.ring = ring;
        this.key = key;
        
        // Create a map to store all positions of each character in the ring
        charToIndices = new HashMap<>();
        for (int i = 0; i < n; i++) {
            char c = ring.charAt(i);
            charToIndices.putIfAbsent(c, new ArrayList<>());
            charToIndices.get(c).add(i);
        }
        
        // Initialize memoization array
        memo = new Integer[m][n];
        
        // Start the recursion from the first character of key and position 0 of ring
        return dfs(0, 0);
    }
    
    private int dfs(int keyIdx, int ringPos) {
        // Base case: all characters in key have been spelled
        if (keyIdx == key.length()) {
            return 0;
        }
        
        // If we've already computed this state, return the cached result
        if (memo[keyIdx][ringPos] != null) {
            return memo[keyIdx][ringPos];
        }
        
        char targetChar = key.charAt(keyIdx);
        int minSteps = Integer.MAX_VALUE;
        
        // Try all occurrences of the target character in the ring
        for (int nextPos : charToIndices.getOrDefault(targetChar, Collections.emptyList())) {
            // Calculate the minimum rotation distance
            int diff = Math.abs(ringPos - nextPos);
            int rotateSteps = Math.min(diff, ring.length() - diff);
            
            // Total steps = rotation steps + 1 (for the key press) + minimum steps for the rest of the key
            int totalSteps = rotateSteps + 1 + dfs(keyIdx + 1, nextPos);
            minSteps = Math.min(minSteps, totalSteps);
        }
        
        // Cache and return the result
        return memo[keyIdx][ringPos] = minSteps;
    }
}
```

### Understanding the Algorithm and Dynamic Programming

1. **State Definition:**
   - In the bottom-up approach, `dp[i][j]` represents the minimum steps to spell out the substring `key[i...]` when the ring is at position `j`
   - In the top-down approach, `memo[keyIdx][ringPos]` represents the same concept

2. **Transition Function:**
   - For each character in the key, we consider all its occurrences in the ring
   - For each occurrence, we calculate the minimum rotation distance from the current position
   - We add 1 for the key press and recursively solve for the rest of the key
   - We take the minimum of all these possibilities

3. **Rotation Distance Calculation:**
   - The minimum rotation distance between positions `i` and `j` is `min(|i-j|, n-|i-j|)`
   - This accounts for both clockwise and counterclockwise rotations

4. **Optimization:**
   - We precompute the positions of each character in the ring to avoid scanning the entire ring for each character
   - This reduces the time complexity from O(n²m) to O(nm²) in the worst case

5. **Base Case:**
   - When we've spelled out all characters in the key, the remaining steps are 0

### Requirement → Code Mapping
- **R0 (Create character map)**: Precompute the positions of each character in the ring
- **R1 (Initialize DP array)**: Set up the dynamic programming table
- **R2 (Fill DP array)**: Process each character in the key from right to left
- **R3 (Try all occurrences)**: Consider all positions of the target character in the ring
- **R4 (Calculate rotation)**: Determine the minimum rotation distance
- **R5 (Update DP value)**: Calculate and update the minimum steps
- **R6 (Return result)**: Return the minimum steps starting from position 0

### Complexity Analysis
- **Time Complexity**: O(n×m²)
  - n is the length of the ring
  - m is the length of the key
  - For each of the m characters in the key, we consider all n positions in the ring
  - For each position, we might need to check up to n occurrences of the target character
  - With the character map optimization, this is reduced to O(n×m×k) where k is the average number of occurrences of each character in the ring

- **Space Complexity**: O(n×m)
  - We use a DP array of size n×m
  - The character map uses O(n) space

### Related Problems
- **Minimum Number of Operations to Move All Balls to Each Box** (Problem 1769): Similar concept of calculating minimum moves
- **Minimum Genetic Mutation** (Problem 433): Finding the minimum steps to transform one string to another
- **Word Ladder** (Problem 127): Finding the shortest transformation sequence
