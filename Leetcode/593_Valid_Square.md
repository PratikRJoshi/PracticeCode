# Valid Square

## Problem Description

**Problem Link:** [Valid Square](https://leetcode.com/problems/valid-square/)

Given the coordinates of four points in 2D space `p1`, `p2`, `p3` and `p4`, return `true` *if the four points construct a square*.

The coordinate of a point `pi` is represented as `[xi, yi]`. The input is **not** given in any particular order.

A **valid square** has four equal sides with positive length and four equal angles (90-degree angles).

**Example 1:**
```
Input: p1 = [0,0], p2 = [1,1], p3 = [1,0], p4 = [0,1]
Output: true
```

**Constraints:**
- `p1.length == p2.length == p3.length == p4.length == 2`
- `-10^4 <= xi, yi <= 10^4`

## Intuition/Main Idea

A square has:
- Four equal sides
- Four equal diagonals
- Right angles (90 degrees)

**Core Algorithm:**
- Calculate distances between all pairs of points
- There should be exactly 2 unique distances: side length and diagonal length
- Diagonal should be √2 times side length
- Check that we have 4 sides and 2 diagonals

**Why distance checking:** A square's geometric properties can be verified by checking distances between points.

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|------------------------|-----------------------------------|
| Check if square | Distance calculation - Lines 8-25 |
| Calculate distances | Distance formula - Lines 27-29 |
| Verify square properties | Distance counting - Lines 12-22 |

## Final Java Code & Learning Pattern (Full Content)

```java
class Solution {
    public boolean validSquare(int[] p1, int[] p2, int[] p3, int[] p4) {
        // Calculate distances between all pairs
        int[][] points = {p1, p2, p3, p4};
        Set<Integer> distances = new HashSet<>();
        
        for (int i = 0; i < 4; i++) {
            for (int j = i + 1; j < 4; j++) {
                int dist = getDistance(points[i], points[j]);
                if (dist == 0) {
                    return false; // Duplicate points
                }
                distances.add(dist);
            }
        }
        
        // A square has exactly 2 unique distances:
        // 4 sides (equal length) and 2 diagonals (equal length)
        // Diagonal = side * √2
        return distances.size() == 2;
    }
    
    private int getDistance(int[] p1, int[] p2) {
        int dx = p1[0] - p2[0];
        int dy = p1[1] - p2[1];
        return dx * dx + dy * dy; // Return squared distance
    }
}
```

## Complexity Analysis

**Time Complexity:** $O(1)$. We check a constant number of point pairs (6 pairs).

**Space Complexity:** $O(1)$. We use a set with at most 2 elements.

## Similar Problems

- [Rectangle Overlap](https://leetcode.com/problems/rectangle-overlap/) - Check rectangle properties
- [Max Points on a Line](https://leetcode.com/problems/max-points-on-a-line/) - Geometry with points
- [Valid Triangle Number](https://leetcode.com/problems/valid-triangle-number/) - Check triangle properties

