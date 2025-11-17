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

To detect squares, we need to efficiently store points and count potential squares that can be formed. The key insight is that for a given query point (px, py), we need to find other points that could form a square with it.

For a square to exist with (px, py) as one corner:
1. We need a point (x, y) that is diagonal to our query point
2. Then we need points at (px, y) and (x, py) to form the other two corners

By checking all possible diagonal points and then verifying if the other two corners exist, we can count all possible squares.

## Code Mapping

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Store points with duplicates | ArrayList and HashMap - Lines 3-4 |
| Add point to data structure | `add` method - Lines 11-14 |
| Count squares with given vertex | `count` method - Lines 16-28 |
| Find diagonal points | Loop through coordinates - Lines 19-21 |
| Check for square formation | Conditions in if statement - Lines 20-22 |
| Count all possible squares | Frequency multiplication - Line 24 |

## Final Java Code & Learning Pattern

```java
class DetectSquares {

    List<int[]> coordinates;
    Map<String, Integer> freq;

    public DetectSquares() {
        // Use ArrayList to store all points (including duplicates) for iteration
        coordinates = new ArrayList<>(); 
        // HashMap to efficiently look up point frequencies
        freq = new HashMap<>(); 
    }
    
    public void add(int[] point) {
        // Add point to our list of coordinates
        coordinates.add(point);
        // Create a unique key for the point and increment its frequency
        String key = point[0] + "^" + point[1]; 
        freq.put(key, freq.getOrDefault(key, 0) + 1);
    }
    
    public int count(int[] point) {
        int sum = 0;
        int px = point[0], py = point[1];
        
        // Iterate through all stored points to find potential diagonal points
        for(int[] coord : coordinates) {
            int x = coord[0], y = coord[1];
            
            // Skip points that:
            // 1. Share the same x or y coordinate (we need diagonal points)
            // 2. Don't form a square (distance in x ≠ distance in y)
            if(px == x || py == y || Math.abs(px - x) != Math.abs(py - y))
                continue;
            
            // At this point, we have:
            // - Current point (px, py) from the count method
            // - A diagonal point (x, y) from our stored coordinates
            // - We need to check if the other two corners exist: (px, y) and (x, py)
            
            // Calculate how many squares can be formed using these points
            // by multiplying the frequencies of the other two corners
            sum += freq.getOrDefault(x + "^" + py, 0) * freq.getOrDefault(px + "^" + y, 0);
        }

        return sum;
    }
}
```

## Detailed Explanation

### Data Structures Used

1. **List<int[]> coordinates**: 
   - Stores all points including duplicates
   - Used for iteration when counting squares
   - Allows us to consider all possible diagonal points

2. **Map<String, Integer> freq**:
   - Maps point coordinates to their frequency
   - Key format: "x^y" (using ^ as a separator)
   - Efficiently handles duplicate points by incrementing their count

### The `add` Method

The `add` method performs two operations:
1. Adds the point to our list of coordinates
2. Updates the frequency map with the new point

This dual storage approach allows us to:
- Iterate through all points efficiently (using the list)
- Look up point frequencies in constant time (using the map)

### The `count` Method

The `count` method is the core of the solution:

1. We start with the query point (px, py)
2. We iterate through all stored points to find potential diagonal points
3. For each point (x, y), we apply three important filters:
   - Skip if it shares the same x-coordinate (px == x)
   - Skip if it shares the same y-coordinate (py == y)
   - Skip if it doesn't form a square (Math.abs(px - x) != Math.abs(py - y))
4. For remaining points that pass these filters, we have a potential diagonal corner of a square
5. We then check if the other two corners exist: (px, y) and (x, py)
6. If they exist, we multiply their frequencies to count all possible squares

### Why This Approach Works

This approach efficiently finds all squares by:
1. Identifying diagonal points first (which uniquely determine the square's size and orientation)
2. Verifying the existence of the other two corners
3. Accounting for duplicates by multiplying frequencies

For example, with the query point (11, 10) and stored points [(3, 10), (11, 2), (3, 2)]:
- We find the diagonal point (3, 2)
- We check for points (11, 2) and (3, 10), which both exist
- We multiply their frequencies to get the total count of squares

### Handling Edge Cases

The solution handles several edge cases:
- Points with the same x or y coordinate (skipped as they can't form a diagonal)
- Points that don't form a square (distances in x and y must be equal)
- Missing corners (if either corner doesn't exist, no square is counted)
- Duplicate points (correctly counted using the frequency map)

## Complexity Analysis

- **Time Complexity**: 
  - `add`: O(1) - Constant time to add to list and update map
  - `count`: O(n) where n is the number of points added - We iterate through all points

- **Space Complexity**: O(n) where n is the number of points added
  - We store all points in both the list and the map

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
