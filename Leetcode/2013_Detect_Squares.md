# Detect Squares

## Problem Description

**Problem Link:** [Detect Squares](https://leetcode.com/problems/detect-squares/)

You are given a stream of points on the X-Y plane. Design an algorithm that:

- **Adds** new points from the stream into a data structure. **Duplicate** points are allowed and should be treated as different points.
- **Counts** the number of ways to form **axis-aligned squares** with a point from the stream as one of the vertices, where the square's sides are parallel to the X and Y axes.

Implement the `DetectSquares` class:

- `DetectSquares()` Initializes the object with an empty data structure.
- `void add(int[] point)` Adds a new point `point = [x, y]` to the data structure.
- `int count(int[] point)` Counts the number of ways to form **axis-aligned squares** with `point` as one of the vertices.

**Example 1:**

```
Input
["DetectSquares", "add", "add", "add", "count", "count", "add", "count"]
[[], [[3, 10]], [[11, 2]], [[3, 2]], [[11, 10]], [[14, 8]], [[11, 2]], [[11, 10]]]
Output
[null, null, null, null, 1, 0, null, 2]

Explanation
DetectSquares detectSquares = new DetectSquares();
detectSquares.add([3, 10]);
detectSquares.add([11, 2]);
detectSquares.add([3, 2]);
detectSquares.count([11, 10]); // return 1. You can form one square with [11,10] as vertex
detectSquares.count([14, 8]);  // return 0. No square can be formed
detectSquares.add([11, 2]);    // Adding duplicate point
detectSquares.count([11, 10]); // return 2. You can form two squares
```

**Constraints:**
- `point.length == 2`
- `0 <= x, y <= 1000`
- At most `3000` calls in total will be made to `add` and `count`.

## Intuition/Main Idea

To form an axis-aligned square with a given point `[x, y]` as a vertex, we need to find three other points that form a square. For a square, all four vertices must have coordinates that form a rectangle with equal sides.

**Key Insight:** For a point `[x, y]` to be part of a square, we need:
1. Another point at the same x-coordinate: `[x, y1]`
2. Another point at the same y-coordinate: `[x1, y]`
3. The fourth point: `[x1, y1]`

The side length is `|y - y1| = |x - x1|`.

**Core Algorithm:**
1. Use a map to count frequency of each point.
2. For `count([x, y])`, iterate through all points with the same x-coordinate.
3. For each such point `[x, y1]`, calculate side length `d = |y - y1|`.
4. Check for points `[x ± d, y]` and `[x ± d, y1]` to form squares.
5. Multiply frequencies to count all possible squares.

**Why this works:** By fixing one vertex and one side, we can uniquely determine the other two vertices of the square. We count all combinations by multiplying frequencies.

## Code Mapping

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Store point frequencies | HashMap - Line 5 |
| Add point to data structure | `add` method - Lines 9-12 |
| Count squares with given vertex | `count` method - Lines 14-35 |
| Find points with same x-coordinate | Iteration - Line 18 |
| Calculate square side length | Distance calculation - Line 20 |
| Check for other two vertices | Point existence checks - Lines 22-29 |
| Count all combinations | Frequency multiplication - Lines 30-31 |
| Return total count | Return statement - Line 33 |

## Final Java Code & Learning Pattern

```java
import java.util.HashMap;
import java.util.Map;

class DetectSquares {
    // Map to store frequency of each point
    // Key: "x,y" as string, Value: frequency count
    private Map<String, Integer> pointCount;
    
    public DetectSquares() {
        pointCount = new HashMap<>();
    }
    
    public void add(int[] point) {
        // Convert point to string key
        String key = point[0] + "," + point[1];
        // Increment frequency
        pointCount.put(key, pointCount.getOrDefault(key, 0) + 1);
    }
    
    public int count(int[] point) {
        int x = point[0];
        int y = point[1];
        int totalSquares = 0;
        
        // Iterate through all points with same x-coordinate
        for (Map.Entry<String, Integer> entry : pointCount.entrySet()) {
            String[] coords = entry.getKey().split(",");
            int x1 = Integer.parseInt(coords[0]);
            int y1 = Integer.parseInt(coords[1]);
            
            // Skip if not same x-coordinate or same point
            if (x1 != x || (x1 == x && y1 == y)) {
                continue;
            }
            
            // Calculate side length (distance in y-direction)
            int sideLength = Math.abs(y - y1);
            
            // Check for two possible squares:
            // Square 1: [x, y], [x, y1], [x + sideLength, y], [x + sideLength, y1]
            // Square 2: [x, y], [x, y1], [x - sideLength, y], [x - sideLength, y1]
            
            // Check right side square
            String rightTop = (x + sideLength) + "," + y;
            String rightBottom = (x + sideLength) + "," + y1;
            if (pointCount.containsKey(rightTop) && pointCount.containsKey(rightBottom)) {
                totalSquares += entry.getValue() * 
                               pointCount.get(rightTop) * 
                               pointCount.get(rightBottom);
            }
            
            // Check left side square
            String leftTop = (x - sideLength) + "," + y;
            String leftBottom = (x - sideLength) + "," + y1;
            if (pointCount.containsKey(leftTop) && pointCount.containsKey(leftBottom)) {
                totalSquares += entry.getValue() * 
                               pointCount.get(leftTop) * 
                               pointCount.get(leftBottom);
            }
        }
        
        return totalSquares;
    }
}
```

**Alternative Implementation (Using Point Class):**

```java
import java.util.HashMap;
import java.util.Map;

class DetectSquares {
    // Map to store frequency of each point
    private Map<String, Integer> pointCount;
    
    public DetectSquares() {
        pointCount = new HashMap<>();
    }
    
    public void add(int[] point) {
        String key = getKey(point[0], point[1]);
        pointCount.put(key, pointCount.getOrDefault(key, 0) + 1);
    }
    
    public int count(int[] point) {
        int x = point[0], y = point[1];
        int count = 0;
        
        // Check all points with same x-coordinate
        for (int y1 = 0; y1 <= 1000; y1++) {
            if (y1 == y) continue;  // Skip same point
            
            String key1 = getKey(x, y1);
            if (!pointCount.containsKey(key1)) continue;
            
            int sideLength = Math.abs(y - y1);
            int freq1 = pointCount.get(key1);
            
            // Check square to the right: [x, y], [x, y1], [x+d, y], [x+d, y1]
            String key2 = getKey(x + sideLength, y);
            String key3 = getKey(x + sideLength, y1);
            if (pointCount.containsKey(key2) && pointCount.containsKey(key3)) {
                count += freq1 * pointCount.get(key2) * pointCount.get(key3);
            }
            
            // Check square to the left: [x, y], [x, y1], [x-d, y], [x-d, y1]
            String key4 = getKey(x - sideLength, y);
            String key5 = getKey(x - sideLength, y1);
            if (pointCount.containsKey(key4) && pointCount.containsKey(key5)) {
                count += freq1 * pointCount.get(key4) * pointCount.get(key5);
            }
        }
        
        return count;
    }
    
    private String getKey(int x, int y) {
        return x + "," + y;
    }
}
```

**Explanation of Key Code Sections:**

1. **Data Structure (Line 5):** We use a `HashMap` to store the frequency of each point. The key is a string representation `"x,y"` and the value is the count.

2. **Add Method (Lines 9-12):** When adding a point, we convert it to a string key and increment its frequency. Duplicate points are handled by increasing the count.

3. **Count Method (Lines 14-35):** 
   - **Iterate Points (Line 18):** We iterate through all stored points to find those with the same x-coordinate as the query point.
   - **Skip Conditions (Lines 20-23):** Skip points that don't have the same x-coordinate or are the same point.
   - **Calculate Side Length (Line 26):** The side length is the absolute difference in y-coordinates: `|y - y1|`.
   - **Check Right Square (Lines 28-32):** Check if points `[x + sideLength, y]` and `[x + sideLength, y1]` exist. If both exist, we can form a square. Multiply frequencies to count all combinations.
   - **Check Left Square (Lines 34-38):** Similarly check for a square to the left.

4. **Frequency Multiplication:** When counting squares, we multiply the frequencies of all four vertices because:
   - If a point appears multiple times, each occurrence can form a different square.
   - We need to count all combinations: `freq(point1) × freq(point2) × freq(point3) × freq(point4)`.

**Why this approach works:**
- For an axis-aligned square, if we fix vertex `[x, y]` and another vertex `[x, y1]` (same x-coordinate), the other two vertices are uniquely determined: `[x ± d, y]` and `[x ± d, y1]` where `d = |y - y1|`.
- By checking both left and right possibilities, we cover all squares that include the query point.

## Complexity Analysis

- **Time Complexity:** 
  - `add`: $O(1)$ - HashMap insertion is constant time.
  - `count`: $O(n)$ where $n$ is the number of distinct points (or $O(1000)$ in the alternative implementation).

- **Space Complexity:** $O(n)$ where $n$ is the number of points added.

## Similar Problems

Problems that can be solved using similar geometric and counting patterns:

1. **2013. Detect Squares** (this problem) - Counting geometric shapes
2. **939. Minimum Area Rectangle** - Finding rectangles from points
3. **149. Max Points on a Line** - Counting collinear points
4. **356. Line Reflection** - Symmetry detection
5. **447. Number of Boomerangs** - Distance-based counting
6. **593. Valid Square** - Square validation
7. **963. Minimum Area Rectangle II** - Rectangle finding
8. **1266. Minimum Time Visiting All Points** - Geometric path
9. **1037. Valid Boomerang** - Collinearity check
10. **1232. Check If It Is a Straight Line** - Line validation

