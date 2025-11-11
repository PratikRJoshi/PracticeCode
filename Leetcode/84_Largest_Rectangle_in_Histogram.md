# Largest Rectangle in Histogram

## Problem Description

**Problem Link:** [Largest Rectangle in Histogram](https://leetcode.com/problems/largest-rectangle-in-histogram/)

Given an array of integers `heights` representing the histogram's bar height where the width of each bar is `1`, return *the area of the largest rectangle in the histogram*.

**Example 1:**

```
Input: heights = [2,1,5,6,2,3]
Output: 10
Explanation: The above is a histogram where width of each bar is 1.
The largest rectangle is shown in the red area, which has an area = 10 units.
```

**Example 2:**

```
Input: heights = [2,4]
Output: 4
```

**Constraints:**
- `1 <= heights.length <= 10^5`
- `0 <= heights[i] <= 10^4`

## Intuition/Main Idea

The key insight is to use a **monotonic stack** to efficiently find the largest rectangle. For each bar, we want to find the largest rectangle that uses that bar as its shortest bar (the limiting height).

**Core Algorithm:**
1. Use a stack to keep track of indices of bars in increasing order of height.
2. For each bar, if it's shorter than the bar at the top of the stack, we calculate the area of rectangles ending at the previous taller bars.
3. The width of a rectangle ending at index `i` is determined by the distance from the previous smaller bar (on the stack) to the current position.

**Why this works:** When we encounter a bar shorter than the one at the top of the stack, it means we've found the right boundary for all rectangles that use the taller bars. We can then calculate their areas and remove them from consideration, as they can't extend further right.

## Code Mapping

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Track bar indices in increasing height order | Stack data structure - Line 5 |
| Calculate rectangle area for each bar | Area calculation in while loop - Lines 12-15 |
| Find left boundary (previous smaller bar) | Stack peek operation - Line 11 |
| Find right boundary (current smaller bar) | Current index `i` - Line 10 |
| Handle remaining bars after iteration | Final while loop - Lines 18-22 |
| Return maximum area found | `maxArea` variable - Lines 6, 15, 22 |

## Final Java Code & Learning Pattern

```java
import java.util.Stack;

class Solution {
    public int largestRectangleArea(int[] heights) {
        // Stack to store indices of bars in increasing order of height
        Stack<Integer> stack = new Stack<>();
        int maxArea = 0;
        int n = heights.length;
        
        // Iterate through all bars
        for (int i = 0; i < n; i++) {
            // While current bar is shorter than bar at top of stack,
            // calculate area of rectangles ending at taller bars
            while (!stack.isEmpty() && heights[stack.peek()] > heights[i]) {
                // Pop the top bar and calculate area using it as the shortest bar
                int height = heights[stack.pop()];
                // Width extends from the previous smaller bar (stack.peek()) 
                // to current position (i), or from start if stack is empty
                int width = stack.isEmpty() ? i : i - stack.peek() - 1;
                maxArea = Math.max(maxArea, height * width);
            }
            // Push current bar index onto stack
            stack.push(i);
        }
        
        // Process remaining bars in stack
        // These bars extend to the end of the histogram
        while (!stack.isEmpty()) {
            int height = heights[stack.pop()];
            // Width extends from previous smaller bar to end of array
            int width = stack.isEmpty() ? n : n - stack.peek() - 1;
            maxArea = Math.max(maxArea, height * width);
        }
        
        return maxArea;
    }
}
```

**Explanation of Key Code Sections:**

1. **Stack Initialization (Line 5):** We use a stack to store indices, not heights. This allows us to calculate widths accurately by knowing the positions of bars.

2. **Main Iteration (Lines 9-19):** For each bar:
   - **While loop condition (Line 11):** If the current bar is shorter than the bar at the top of the stack, we've found the right boundary for rectangles using taller bars.
   - **Height extraction (Line 13):** We pop the taller bar and use its height as the rectangle height.
   - **Width calculation (Line 15):** The width is the distance between the previous smaller bar (stack.peek()) and the current position. If stack is empty, the bar extends from the start.
   - **Area update (Line 16):** We calculate the area and update the maximum.

3. **Remaining Bars Processing (Lines 22-26):** After processing all bars, any remaining bars in the stack extend to the end of the histogram. We calculate their areas similarly, with width extending to `n`.

**Why we append 0 at the end (alternative approach):** Some solutions append a 0-height bar at the end to force processing of all remaining bars. Our approach explicitly handles this with a second while loop, which is clearer.

## Complexity Analysis

- **Time Complexity:** $O(n)$ where $n$ is the number of bars. Each bar is pushed and popped from the stack exactly once.

- **Space Complexity:** $O(n)$ for the stack in the worst case when all bars are in increasing order.

## Similar Problems

Problems that can be solved using similar monotonic stack patterns:

1. **42. Trapping Rain Water** - Uses stack to track decreasing heights
2. **739. Daily Temperatures** - Monotonic stack to find next greater element
3. **496. Next Greater Element I** - Stack-based next greater element
4. **503. Next Greater Element II** - Circular array variant
5. **907. Sum of Subarray Minimums** - Similar rectangle area calculation
6. **85. Maximal Rectangle** - Extension to 2D using this problem's approach
7. **316. Remove Duplicate Letters** - Monotonic stack for lexicographical order
8. **402. Remove K Digits** - Stack to maintain increasing sequence
9. **456. 132 Pattern** - Stack to track decreasing sequence
10. **84. Largest Rectangle in Histogram** (this problem) - Classic monotonic stack problem

