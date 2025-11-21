# Asteroid Collision

## Problem Description

**Problem Link:** [Asteroid Collision](https://leetcode.com/problems/asteroid-collision/)

We are given an array `asteroids` of integers representing asteroids in a row.

For each asteroid, the absolute value represents its size, and the sign represents its direction (positive meaning right, negative meaning left). Each asteroid moves at the same speed.

Find out the state of the asteroids after all collisions. If two asteroids meet, the smaller one will explode. If both are the same size, both will explode. Two asteroids moving in the same direction will never meet.

**Example 1:**
```
Input: asteroids = [5,10,-5]
Output: [5,10]
Explanation: The 10 and -5 collide resulting in 10. The 5 and 10 never collide.
```

**Example 2:**
```
Input: asteroids = [8,-8]
Output: []
Explanation: The 8 and -8 collide exploding each other.
```

**Example 3:**
```
Input: asteroids = [10,2,-5]
Output: [10]
Explanation: The 2 and -5 collide, but 2 is smaller so it explodes. Then 10 and -5 collide and 10 wins.
```

**Constraints:**
- `2 <= asteroids.length <= 10^4`
- `-1000 <= asteroids[i] <= 1000`
- `asteroids[i] != 0`

## Intuition/Main Idea

This is a stack problem. We process asteroids and handle collisions when a right-moving asteroid (positive) meets a left-moving one (negative).

**Core Algorithm:**
- Use a stack to store surviving asteroids
- For each asteroid:
  - If positive or stack empty, push
  - If negative:
    - While stack has positive smaller asteroids, pop them (they explode)
    - If stack top is same size positive, both explode (pop)
    - If stack top is larger positive, current explodes (don't push)
    - Otherwise, push current

**Why stack:** Collisions happen between the last right-moving asteroid and current left-moving one. A stack naturally tracks the sequence of right-moving asteroids we've seen.

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|------------------------|-----------------------------------|
| Handle collisions | Stack-based processing - Lines 5-25 |
| Right-moving asteroids | Positive values - Line 7 |
| Left-moving asteroids | Negative values - Line 9 |
| Smaller explodes | Size comparison - Lines 12-13 |
| Same size both explode | Equality check - Lines 15-17 |
| Larger survives | Size check - Line 19 |

## Final Java Code & Learning Pattern (Full Content)

```java
class Solution {
    public int[] asteroidCollision(int[] asteroids) {
        // Stack to store surviving asteroids
        Stack<Integer> stack = new Stack<>();
        
        // Process each asteroid
        for (int asteroid : asteroids) {
            // If asteroid is moving right (positive) or stack is empty, add it
            // Right-moving asteroids don't collide with previous right-moving ones
            if (asteroid > 0 || stack.isEmpty()) {
                stack.push(asteroid);
            } else {
                // Asteroid is moving left (negative)
                // It can collide with right-moving asteroids in stack
                
                // Remove all smaller right-moving asteroids that will explode
                // Continue until stack is empty or we find a larger/same size asteroid
                while (!stack.isEmpty() && stack.peek() > 0 && stack.peek() < Math.abs(asteroid)) {
                    stack.pop();
                }
                
                // Check if top asteroid is same size (both explode)
                if (!stack.isEmpty() && stack.peek() == Math.abs(asteroid)) {
                    stack.pop();
                }
                // If stack is empty or top is negative, current asteroid survives
                else if (stack.isEmpty() || stack.peek() < 0) {
                    stack.push(asteroid);
                }
                // Otherwise, top is larger positive, current explodes (do nothing)
            }
        }
        
        // Convert stack to array
        int[] result = new int[stack.size()];
        for (int i = stack.size() - 1; i >= 0; i--) {
            result[i] = stack.pop();
        }
        
        return result;
    }
}
```

## Complexity Analysis

**Time Complexity:** $O(n)$ where $n$ is the number of asteroids. Each asteroid is pushed and popped at most once.

**Space Complexity:** $O(n)$ for the stack in worst case.

## Similar Problems

- [Remove All Adjacent Duplicates In String](https://leetcode.com/problems/remove-all-adjacent-duplicates-in-string/) - Similar removal pattern
- [Daily Temperatures](https://leetcode.com/problems/daily-temperatures/) - Similar stack-based processing
- [Next Greater Element](https://leetcode.com/problems/next-greater-element-i/) - Stack for comparisons

