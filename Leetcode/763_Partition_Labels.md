### 763. Partition Labels
### Problem Link: [Partition Labels](https://leetcode.com/problems/partition-labels/)

### Intuition/Main Idea
This problem asks us to partition a string into as many parts as possible such that no letter appears in more than one part. The key insight is to track the last occurrence of each character in the string. For each character, we need to include all characters up to its last occurrence in the same partition.

We can solve this using a greedy approach:
1. First, record the last index of each character in the string
2. Then, iterate through the string, keeping track of the current partition's end index
3. For each character, update the partition end to be the maximum of the current end and the last occurrence of the current character
4. When we reach the current partition's end, we've completed a partition

This approach works because if we encounter a character, we must include all characters up to its last occurrence in the same partition. As we process more characters, we might need to extend the current partition if we find a character whose last occurrence is beyond our current partition end.

### Code Mapping

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Track last occurrence of each character | `int[] lastIndex = new int[26];` |
| Find partition boundaries | `if (i == end) { result.add(end - start + 1); start = i + 1; }` |
| Update partition end based on character's last occurrence | `end = Math.max(end, lastIndex[s.charAt(i) - 'a']);` |
| Return partition sizes | `return result;` |

### Final Java Code & Learning Pattern

```java
// [Pattern: Greedy with Last Occurrence Tracking]
class Solution {
    public List<Integer> partitionLabels(String s) {
        // Record the last occurrence of each character
        int[] lastIndex = new int[26];
        for (int i = 0; i < s.length(); i++) {
            lastIndex[s.charAt(i) - 'a'] = i;
        }
        
        List<Integer> result = new ArrayList<>();
        int start = 0;  // Start of current partition
        int end = 0;    // End of current partition
        
        // Iterate through the string
        for (int i = 0; i < s.length(); i++) {
            // Update the end of current partition if needed
            end = Math.max(end, lastIndex[s.charAt(i) - 'a']);
            
            // If we've reached the end of current partition
            if (i == end) {
                // Add the length of this partition to result
                result.add(end - start + 1);
                // Update start for the next partition
                start = i + 1;
            }
        }
        
        return result;
    }
}
```

### Alternative Implementation (Using HashMap)

```java
// [Pattern: Greedy with HashMap for Last Occurrence]
class Solution {
    public List<Integer> partitionLabels(String s) {
        // Record the last occurrence of each character using HashMap
        Map<Character, Integer> lastIndex = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            lastIndex.put(s.charAt(i), i);
        }
        
        List<Integer> result = new ArrayList<>();
        int start = 0;
        int end = 0;
        
        for (int i = 0; i < s.length(); i++) {
            end = Math.max(end, lastIndex.get(s.charAt(i)));
            
            if (i == end) {
                result.add(end - start + 1);
                start = i + 1;
            }
        }
        
        return result;
    }
}
```

### Complexity Analysis
- **Time Complexity**: $O(n)$ where n is the length of the string. We iterate through the string twice: once to record the last occurrences and once to determine the partitions.
- **Space Complexity**: $O(1)$ since we use a fixed-size array of 26 characters for the last occurrences (or $O(k)$ where k is the number of unique characters if using a HashMap).

### Similar Problems
1. **LeetCode 56: Merge Intervals** - Similar approach of tracking intervals and merging overlapping ones.
2. **LeetCode 1024: Video Stitching** - Greedy approach to cover a range with minimum clips.
3. **LeetCode 435: Non-overlapping Intervals** - Remove minimum number of intervals to make the rest non-overlapping.
4. **LeetCode 452: Minimum Number of Arrows to Burst Balloons** - Find minimum arrows to burst all balloons.
5. **LeetCode 57: Insert Interval** - Insert and merge intervals if necessary.
6. **LeetCode 986: Interval List Intersections** - Find intersections of two interval lists.
7. **LeetCode 1288: Remove Covered Intervals** - Remove intervals that are covered by others.
8. **LeetCode 253: Meeting Rooms II** - Find minimum number of conference rooms required.
