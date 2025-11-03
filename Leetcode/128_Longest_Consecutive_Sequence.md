### 128. Longest Consecutive Sequence
### Problem Link: [Longest Consecutive Sequence](https://leetcode.com/problems/longest-consecutive-sequence/)
### Intuition
The key insight for this problem is to use a HashSet for O(1) lookups. Instead of sorting the array (which would take O(n log n)), we can use a set to check if a number exists in constant time. For each number, we check if it's the start of a sequence by verifying that its predecessor doesn't exist in the set. If it is a start, we count how many consecutive numbers follow it.

### Java Reference Implementation
```java
import java.util.HashSet;
import java.util.Set;

class Solution {
    public int longestConsecutive(int[] nums) {
        // Edge cases
        if (nums == null || nums.length == 0) {
            return 0;
        }
        
        // Add all numbers to a HashSet for O(1) lookups
        Set<Integer> numSet = new HashSet<>();
        for (int num : nums) {
            numSet.add(num);
        }
        
        int longestStreak = 0;
        
        // Check each number if it's the start of a sequence
        for (int num : numSet) {
            // If this number is the start of a sequence (its predecessor doesn't exist)
            if (!numSet.contains(num - 1)) {
                int currentNum = num;
                int currentStreak = 1;
                
                // Count consecutive numbers
                while (numSet.contains(currentNum + 1)) {
                    currentNum++;
                    currentStreak++;
                }
                
                // Update longest streak
                longestStreak = Math.max(longestStreak, currentStreak);
            }
        }
        
        return longestStreak;
    }
}
```

### Requirement → Code Mapping
- **R0 (Handle edge cases)**: `if (nums == null || nums.length == 0) { return 0; }`
- **R1 (Create HashSet for O(1) lookups)**: `Set<Integer> numSet = new HashSet<>();`
- **R2 (Find sequence starts)**: `if (!numSet.contains(num - 1)) { ... }` - Only process numbers that are the start of a sequence
- **R3 (Count consecutive numbers)**: `while (numSet.contains(currentNum + 1)) { ... }` - Extend the sequence as far as possible
- **R4 (Track maximum length)**: `longestStreak = Math.max(longestStreak, currentStreak);`

### Complexity
- **Time Complexity**: O(n) - Although there are nested loops, each number is only visited at most twice
- **Space Complexity**: O(n) - For storing the HashSet
