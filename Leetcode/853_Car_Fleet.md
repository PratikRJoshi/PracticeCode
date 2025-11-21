# Car Fleet

## Problem Description

**Problem Link:** [Car Fleet](https://leetcode.com/problems/car-fleet/)

There are `n` cars going to the same destination along a one-lane road. The destination is `target` miles away.

You are given two integer arrays `position` and `speed`, both of length `n`, where `position[i]` is the position of the `i`th car and `speed[i]` is the speed of the `i`th car (in miles per hour).

A car can never pass another car ahead of it, but it can catch up to it and drive together at the speed of the slower car.

A **car fleet** is some non-empty set of cars driving at the same position and same speed. Note that a single car is also a car fleet.

If a car catches up to a car fleet right at the destination point, it will still be considered as one car fleet.

Return *the number of car fleets that will arrive at the destination*.

**Example 1:**
```
Input: target = 12, position = [10,8,0,5,3], speed = [2,4,1,1,3]
Output: 3
Explanation:
The cars starting at 10 (speed 2) and 8 (speed 4) become a fleet, meeting at 12.
The car starting at 0 (speed 1) becomes a fleet.
The cars starting at 5 (speed 1) and 3 (speed 3) become a fleet, meeting at 12.
The car at position 5 never catches up to the car at position 3, because the car at position 3 reaches 12 first.
```

**Example 2:**
```
Input: target = 10, position = [3], speed = [3]
Output: 1
```

**Example 3:**
```
Input: target = 100, position = [0,2,4], speed = [4,2,1]
Output: 1
```

**Constraints:**
- `n == position.length == speed.length`
- `1 <= n <= 10^5`
- `0 < target <= 10^6`
- `0 <= position[i] < target`
- `1 <= speed[i] <= 10^6`

## Intuition/Main Idea

This problem requires understanding when cars will form fleets. The key insight is to process cars from **back to front** (farthest to nearest) and calculate when each car reaches the destination.

**Key Insights:**
1. A car can only catch up to cars ahead of it (cars with greater position).
2. If a car behind reaches the destination **before or at the same time** as a car ahead, they form a fleet.
3. We should process cars from back to front and track the time each car/fleet reaches the destination.

**Core Algorithm:**
1. Create pairs of (position, speed) and sort by position (descending).
2. For each car (from back to front), calculate time to reach destination: `(target - position) / speed`.
3. If current car's time <= previous fleet's time, it joins the fleet (don't count as new fleet).
4. If current car's time > previous fleet's time, it forms a new fleet.

**Why this works:** By processing from back to front, we ensure that when we check if a car joins a fleet, we're comparing with the fleet that's immediately ahead. If a car can't catch up to the immediate fleet ahead, it forms its own fleet.

## Code Mapping

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Pair position and speed | 2D array - Lines 6-9 |
| Sort by position descending | Sorting - Line 11 |
| Track previous fleet arrival time | `prevTime` variable - Line 13 |
| Calculate time to destination | Time calculation - Line 17 |
| Check if car joins fleet | Comparison - Line 19 |
| Count new fleets | Counter increment - Line 22 |
| Return fleet count | Return statement - Line 25 |

## Final Java Code & Learning Pattern

```java
import java.util.Arrays;
import java.util.Comparator;

class Solution {
    public int carFleet(int target, int[] position, int[] speed) {
        int n = position.length;
        // Create pairs of (position, speed)
        double[][] cars = new double[n][2];
        for (int i = 0; i < n; i++) {
            cars[i][0] = position[i];
            cars[i][1] = speed[i];
        }
        
        // Sort by position in descending order (farthest to nearest)
        Arrays.sort(cars, (a, b) -> Double.compare(b[0], a[0]));
        
        int fleets = 0;
        double prevTime = -1;  // Time for previous fleet to reach destination
        
        for (int i = 0; i < n; i++) {
            // Calculate time for current car to reach destination
            double time = (target - cars[i][0]) / cars[i][1];
            
            // If current car takes longer than previous fleet, it forms a new fleet
            // (can't catch up to the fleet ahead)
            if (time > prevTime) {
                fleets++;
                prevTime = time;
            }
            // If time <= prevTime, current car joins the previous fleet
            // (catches up before or at destination)
        }
        
        return fleets;
    }
}
```

**Explanation of Key Code Sections:**

1. **Create Car Pairs (Lines 6-9):** We create a 2D array to store position and speed pairs. This makes it easier to sort and process.

2. **Sort by Position Descending (Line 11):** We sort cars by position in descending order (farthest to nearest). This allows us to process cars from back to front.

3. **Process Each Car (Lines 14-23):**
   - **Time Calculation (Line 17):** For each car, calculate the time to reach the destination: `(target - position) / speed`. This tells us when the car will arrive.
   - **Fleet Formation Logic (Lines 19-22):**
     - If `time > prevTime`: The current car takes longer than the fleet ahead, so it can't catch up. It forms a new fleet.
     - If `time <= prevTime`: The current car reaches the destination before or at the same time as the fleet ahead, so it joins that fleet (we don't increment the counter).

4. **Return Fleet Count (Line 25):** After processing all cars, `fleets` contains the number of distinct fleets.

**Why processing back-to-front works:**
- Cars can only catch up to cars ahead (with greater position).
- By processing from farthest to nearest, when we check a car, we compare with the fleet immediately ahead.
- If a car can't catch the immediate fleet ahead, it definitely can't catch any fleet further ahead.

**Example walkthrough for `target = 12, position = [10,8,0,5,3], speed = [2,4,1,1,3]`:**
- Sorted (by position desc): [(10,2), (8,4), (5,1), (3,3), (0,1)]
- Car at 10: time = (12-10)/2 = 1.0 → Fleet 1, prevTime = 1.0
- Car at 8: time = (12-8)/4 = 1.0 → time = prevTime, joins Fleet 1
- Car at 5: time = (12-5)/1 = 7.0 → time > prevTime, Fleet 2, prevTime = 7.0
- Car at 3: time = (12-3)/3 = 3.0 → time < prevTime, joins Fleet 2
- Car at 0: time = (12-0)/1 = 12.0 → time > prevTime, Fleet 3, prevTime = 12.0
- Total fleets = 3

**Why we use `>` instead of `>=`:**
- If `time == prevTime`, the car reaches exactly when the fleet ahead reaches, so they meet at the destination and form one fleet.
- If `time < prevTime`, the car reaches before the fleet ahead, so it catches up and joins.
- Only when `time > prevTime` does the car form a new fleet.

---

## Alternative Solution: Using TreeMap

We can use `TreeMap` to automatically maintain cars sorted by position, eliminating the need for manual sorting. This approach leverages TreeMap's built-in sorting capabilities.

### The Key Insight: TreeMap Maintains Sorted Order

`TreeMap` automatically keeps keys in sorted order. By using `Collections.reverseOrder()` as the comparator, we can:
1. Create TreeMap sorted in descending order by position (farthest to nearest)
2. Insert all cars into TreeMap (automatically sorted in descending order)
3. Iterate normally - keys are already in the order we need
4. Apply the same fleet formation logic

**Advantages:**
- No manual sorting needed - TreeMap handles it
- No need for `descendingKeySet()` - normal iteration works
- Cleaner code - direct position → speed mapping
- Natural key-value relationship

### Alternative Java Code Using TreeMap

```java
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

class Solution {
    public int carFleet(int target, int[] position, int[] speed) {
        // TreeMap sorted in descending order by position (farthest to nearest)
        Map<Integer, Double> m = new TreeMap<>(Collections.reverseOrder());
        
        // Store position -> speed mapping
        for (int i = 0; i < position.length; i++) {
            m.put(position[i], (double)speed[i]);
        }
        
        int fleets = 0;
        double prevTime = -1;  // Time for previous fleet to reach destination
        
        // Iterate in descending order of position (farthest to nearest)
        // TreeMap is already sorted in descending order, so normal iteration works
        for (int pos : m.keySet()) {
            double spd = m.get(pos);
            
            // Calculate time for current car to reach destination
            double time = (target - pos) / spd;
            
            // If current car takes longer than previous fleet, it forms a new fleet
            if (time > prevTime) {
                fleets++;
                prevTime = time;
            }
            // If time <= prevTime, current car joins the previous fleet
        }
        
        return fleets;
    }
}
```

### How TreeMap Works Here

**TreeMap Properties:**
- **Reverse Order Comparator**: `Collections.reverseOrder()` makes TreeMap sort keys in descending order
- **Automatic Sorting**: Keys are automatically sorted in descending order (farthest to nearest)
- **Efficient Operations**: Insertion and lookup are O(log n)
- **Normal Iteration**: Since TreeMap is already in descending order, we can iterate normally

**Step-by-Step Process:**

1. **TreeMap Creation:**
   ```java
   Map<Integer, Double> m = new TreeMap<>(Collections.reverseOrder());
   ```
   - `Collections.reverseOrder()` creates a comparator that sorts in descending order
   - TreeMap will maintain positions sorted from highest to lowest

2. **Insertion Phase:**
   ```java
   m.put(position[i], (double)speed[i]);
   ```
   - TreeMap automatically sorts by position (descending order)
   - If duplicate positions exist, the last speed overwrites (though positions are typically unique)
   - Speed is stored as `Double` to avoid casting during division

3. **Iteration Phase:**
   ```java
   for (int pos : m.keySet())
   ```
   - `keySet()` returns positions in descending order (already sorted that way)
   - We process from farthest to nearest (same as sorted array approach)
   - No need for `descendingKeySet()` since TreeMap is already in reverse order

4. **Fleet Logic:**
   - Same logic as before: compare time with previous fleet
   - If `time > prevTime`, form new fleet

### Example Walkthrough

**Input:** `target = 12, position = [10,8,0,5,3], speed = [2,4,1,1,3]`

```
Step 1: Build TreeMap (with reverseOrder, so descending from the start)
  Insert (10, 2) → TreeMap: {10=2.0}
  Insert (8, 4)  → TreeMap: {10=2.0, 8=4.0}
  Insert (0, 1)  → TreeMap: {10=2.0, 8=4.0, 5=1.0, 3=3.0, 0=1.0}
  Insert (5, 1)  → TreeMap: {10=2.0, 8=4.0, 5=1.0, 3=3.0, 0=1.0}
  Insert (3, 3)  → TreeMap: {10=2.0, 8=4.0, 5=1.0, 3=3.0, 0=1.0}
  Note: TreeMap maintains descending order automatically

Step 2: Process in descending order (normal iteration, already sorted)
  Position 10: time = (12-10)/2 = 1.0 → Fleet 1, prevTime = 1.0
  Position 8:  time = (12-8)/4 = 1.0  → time = prevTime, joins Fleet 1
  Position 5:  time = (12-5)/1 = 7.0  → time > prevTime, Fleet 2, prevTime = 7.0
  Position 3:  time = (12-3)/3 = 3.0  → time < prevTime, joins Fleet 2
  Position 0:  time = (12-0)/1 = 12.0 → time > prevTime, Fleet 3, prevTime = 12.0

Result: 3 fleets
```

### Comparison: Array Sorting vs TreeMap

| Aspect | Array Sorting Approach | TreeMap Approach |
|--------|----------------------|------------------|
| **Sorting** | Manual `Arrays.sort()` | Automatic (TreeMap with `reverseOrder()`) |
| **Data Structure** | 2D array `double[][]` | `Map<Integer, Double>` |
| **Iteration** | Normal array iteration | Normal iteration (already in reverse order) |
| **Code Lines** | ~35 lines | ~25 lines |
| **Time Complexity** | O(n log n) | O(n log n) |
| **Space Complexity** | O(n) | O(n) |
| **Readability** | Explicit sorting step | More concise, natural key-value mapping |
| **When to Use** | When you need array operations | When you need sorted map operations |

### Why TreeMap is Elegant

1. **Self-Documenting**: The map structure clearly shows position → speed relationship
2. **Less Code**: No need to create pairs array or write comparator
3. **Built-in Sorting**: TreeMap handles sorting automatically with `reverseOrder()`
4. **Natural Iteration**: Normal `keySet()` iteration works - no need for `descendingKeySet()`
5. **Type Safety**: Using `Double` for speed avoids casting during division

### Handling Edge Cases

- **Duplicate Positions**: If two cars have the same position, TreeMap will overwrite (last speed wins). However, constraints suggest positions are unique.
- **Single Car**: Works correctly - one car forms one fleet
- **All Cars Form One Fleet**: Correctly handled by the time comparison logic

### Complexity Analysis (TreeMap Approach)

- **Time Complexity:** $O(n \log n)$ where $n$ is the number of cars. 
  - TreeMap insertion: $O(\log n)$ per car → $O(n \log n)$ total
  - Iteration: $O(n)$
  - Overall: $O(n \log n)$ (same as array sorting)

- **Space Complexity:** $O(n)$ for storing the TreeMap entries.

**Note:** Both approaches have the same time complexity. TreeMap is slightly more memory-efficient in terms of code clarity, but the actual space usage is similar.

---

## Complexity Analysis

- **Time Complexity:** $O(n \log n)$ where $n$ is the number of cars. The sorting step dominates the time complexity.

- **Space Complexity:** $O(n)$ for storing the car pairs array.

## Similar Problems

Problems that can be solved using similar sorting and greedy patterns:

1. **853. Car Fleet** (this problem) - Sorting and time-based grouping
2. **56. Merge Intervals** - Sorting and merging
3. **435. Non-overlapping Intervals** - Sorting and greedy selection
4. **452. Minimum Number of Arrows to Burst Balloons** - Sorting and greedy covering
5. **253. Meeting Rooms II** - Sorting and greedy room allocation
6. **252. Meeting Rooms** - Sorting and overlap detection
7. **646. Maximum Length of Pair Chain** - Sorting and greedy chaining
8. **1288. Remove Covered Intervals** - Sorting and coverage detection
9. **759. Employee Free Time** - Sorting and interval merging
10. **1229. Meeting Scheduler** - Sorting and time slot finding

