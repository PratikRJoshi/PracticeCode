# Robot Bounded In Circle

## Problem Description

**Problem Link:** [Robot Bounded In Circle](https://leetcode.com/problems/robot-bounded-in-circle/)

On an infinite plane, a robot initially stands at `(0, 0)` and faces north. The robot can receive one of three instructions:

- `'G'`: go straight 1 unit;
- `'L'`: turn 90 degrees to the left (i.e., anti-clockwise);
- `'R'`: turn 90 degrees to the right (i.e., clockwise).

The robot performs the `instructions` given in order, and repeats them forever.

Return `true` if and only if there exists a circle in the plane such that the robot never leaves the circle.

**Example 1:**
```
Input: instructions = "GGLLGG"
Output: true
Explanation: The robot moves from (0,0) to (0,2), turns 180 degrees, and then returns to (0,0).
When repeating these instructions, the robot remains in the circle of radius 2 centered at the origin.
```

**Constraints:**
- `1 <= instructions.length <= 100`
- `instructions[i]` is `'G'`, `'L'`, or `'R'`.

## Intuition/Main Idea

The robot is bounded in a circle if:
1. After one cycle, it returns to (0,0), OR
2. After one cycle, it doesn't face north (direction changed)

**Core Algorithm:**
- Simulate one cycle of instructions
- Track position (x, y) and direction
- If ends at (0,0) OR direction changed, robot is bounded

**Why this works:** If direction changed, repeating instructions will eventually bring robot back. If at origin, it stays bounded.

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|------------------------|-----------------------------------|
| Simulate robot movement | Instruction processing - Lines 8-20 |
| Track position | x, y coordinates - Lines 5-6 |
| Track direction | Direction vector - Lines 7, 15-18 |
| Check bounded | Position and direction check - Line 21 |

## Final Java Code & Learning Pattern (Full Content)

```java
class Solution {
    public boolean isRobotBounded(String instructions) {
        // Direction vectors: [north, east, south, west]
        int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        int x = 0, y = 0;
        int dir = 0; // 0 = north, 1 = east, 2 = south, 3 = west
        
        for (char instruction : instructions.toCharArray()) {
            if (instruction == 'G') {
                // Move in current direction
                x += directions[dir][0];
                y += directions[dir][1];
            } else if (instruction == 'L') {
                // Turn left (counter-clockwise)
                dir = (dir + 3) % 4;
            } else { // instruction == 'R'
                // Turn right (clockwise)
                dir = (dir + 1) % 4;
            }
        }
        
        // Robot is bounded if:
        // 1. Returns to origin after one cycle, OR
        // 2. Direction changed (not facing north)
        return (x == 0 && y == 0) || dir != 0;
    }
}
```

## Complexity Analysis

**Time Complexity:** $O(n)$ where $n$ is instruction length. We process each instruction once.

**Space Complexity:** $O(1)$. We only use a constant amount of extra space.

## Similar Problems

- [Spiral Matrix](https://leetcode.com/problems/spiral-matrix/) - Similar direction tracking
- [Walking Robot Simulation](https://leetcode.com/problems/walking-robot-simulation/) - Robot movement
- [Robot Room Cleaner](https://leetcode.com/problems/robot-room-cleaner/) - Robot navigation

