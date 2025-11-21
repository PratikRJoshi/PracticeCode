# Open the Lock

## Problem Description

**Problem Link:** [Open the Lock](https://leetcode.com/problems/open-the-lock/)

You have a lock in front of you with 4 circular wheels. Each wheel has 10 slots: `'0'`, `'1'`, `'2'`, `'3'`, `'4'`, `'5'`, `'6'`, `'7'`, `'8'`, `'9'`. The wheels can rotate freely and wrap around: for example we can turn `'9'` to be `'0'`, or `'0'` to be `'9'`. Each move consists of turning one wheel one slot.

The lock initially starts at `"0000"`, a string representing the state of the 4 wheels.

You are given a list of `deadends` dead ends, meaning if the lock displays any of these codes, the wheels of the lock will stop turning and you will be unable to open it.

Given a `target` representing the value of the wheels that will unlock the lock, return *the minimum total number of turns required to open the lock, or* `-1` *if it is impossible*.

**Example 1:**
```
Input: deadends = ["0201","0101","0102","1212","2002"], target = "0202"
Output: 6
Explanation: A sequence of valid moves would be "0000" -> "1000" -> "1100" -> "1200" -> "1201" -> "1202" -> "0202".
```

**Constraints:**
- `1 <= deadends.length <= 500`
- `deadends[i].length == 4`
- `target.length == 4`
- target will not be in the list `deadends`.

## Intuition/Main Idea

This is a shortest path problem. We need minimum moves from "0000" to target, avoiding deadends.

**Core Algorithm:**
- Use BFS to find shortest path
- Each state has 8 neighbors (4 wheels × 2 directions each)
- Avoid deadend states
- Track visited states

**Why BFS:** BFS finds shortest path in unweighted graph. First time we reach target is minimum moves.

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|------------------------|-----------------------------------|
| Find minimum turns | BFS - Lines 9-35 |
| Generate neighbors | Neighbor generation - Lines 37-52 |
| Avoid deadends | Deadend check - Lines 6, 15 |
| Track visited | Visited set - Lines 7, 18 |

## Final Java Code & Learning Pattern (Full Content)

```java
class Solution {
    public int openLock(String[] deadends, String target) {
        Set<String> deadendSet = new HashSet<>(Arrays.asList(deadends));
        Set<String> visited = new HashSet<>();
        
        Queue<String> queue = new LinkedList<>();
        queue.offer("0000");
        visited.add("0000");
        int turns = 0;
        
        while (!queue.isEmpty()) {
            int size = queue.size();
            
            for (int i = 0; i < size; i++) {
                String current = queue.poll();
                
                // Check if target reached
                if (current.equals(target)) {
                    return turns;
                }
                
                // Skip if deadend
                if (deadendSet.contains(current)) {
                    continue;
                }
                
                // Generate all neighbors (8 possible moves)
                for (int j = 0; j < 4; j++) {
                    // Turn wheel j forward
                    String next1 = turnWheel(current, j, 1);
                    if (!visited.contains(next1) && !deadendSet.contains(next1)) {
                        visited.add(next1);
                        queue.offer(next1);
                    }
                    
                    // Turn wheel j backward
                    String next2 = turnWheel(current, j, -1);
                    if (!visited.contains(next2) && !deadendSet.contains(next2)) {
                        visited.add(next2);
                        queue.offer(next2);
                    }
                }
            }
            
            turns++;
        }
        
        return -1;
    }
    
    // Turn wheel at position 'pos' by 'direction' (1 forward, -1 backward)
    private String turnWheel(String current, int pos, int direction) {
        char[] chars = current.toCharArray();
        int digit = chars[pos] - '0';
        digit = (digit + direction + 10) % 10; // Handle wrap-around
        chars[pos] = (char)('0' + digit);
        return new String(chars);
    }
}
```

## Complexity Analysis

**Time Complexity:** $O(10^4) = O(1)$ since there are at most 10000 states (4 digits, 10 possibilities each).

**Space Complexity:** $O(10^4)$ for visited set and queue.

## Similar Problems

- [Word Ladder](https://leetcode.com/problems/word-ladder/) - Similar BFS pattern
- [Snakes and Ladders](https://leetcode.com/problems/snakes-and-ladders/) - Similar shortest path
- [Shortest Path in Binary Matrix](https://leetcode.com/problems/shortest-path-in-binary-matrix/) - BFS shortest path

