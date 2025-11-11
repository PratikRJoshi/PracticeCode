# Daily Temperatures

## Problem Description

**Problem Link:** [Daily Temperatures](https://leetcode.com/problems/daily-temperatures/)

Given an array of integers `temperatures` represents the daily temperatures, return *an array `answer` such that `answer[i]` is the number of days you have to wait after the `i`th day to get a warmer temperature. If there is no future day for which this is possible, keep `answer[i] == 0` instead.

**Example 1:**
```
Input: temperatures = [73,74,75,71,69,72,76,73]
Output: [1,1,4,2,1,1,0,0]
```

**Example 2:**
```
Input: temperatures = [30,40,50,60]
Output: [1,1,1,0]
```

**Example 3:**
```
Input: temperatures = [30,60,90]
Output: [1,1,0]
```

**Constraints:**
- `1 <= temperatures.length <= 10^5`
- `30 <= temperatures[i] <= 100`

## Intuition/Main Idea

This problem requires finding the next greater element for each position. We can use a **monotonic stack** (specifically, a decreasing stack) to efficiently solve this.

**Core Algorithm:**
1. Use a stack to store indices of temperatures in decreasing order.
2. For each temperature, while the current temperature is greater than the temperature at the top of the stack:
   - Pop the index from the stack.
   - Calculate the number of days (difference in indices).
   - Store this in the result array.
3. Push the current index onto the stack.

**Why stack works:** When we encounter a warmer temperature, it becomes the answer for all previous colder temperatures that are still on the stack. The stack maintains temperatures in decreasing order, so we can efficiently find all temperatures that are colder than the current one.

## Code Mapping

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Store indices of temperatures | Stack data structure - Line 5 |
| Track result for each day | Result array - Line 6 |
| Process each temperature | For loop - Line 9 |
| Find warmer temperature | While loop condition - Line 10 |
| Calculate days to wait | Index difference calculation - Line 13 |
| Store result | Result array assignment - Line 14 |
| Maintain decreasing stack | Push operation - Line 17 |

## Final Java Code & Learning Pattern

```java
import java.util.Stack;

class Solution {
    public int[] dailyTemperatures(int[] temperatures) {
        // Stack to store indices of temperatures in decreasing order
        Stack<Integer> stack = new Stack<>();
        int n = temperatures.length;
        int[] result = new int[n];
        
        // Process each temperature from left to right
        for (int i = 0; i < n; i++) {
            // While current temperature is warmer than temperature at top of stack
            while (!stack.isEmpty() && temperatures[stack.peek()] < temperatures[i]) {
                // Pop the index of the colder temperature
                int prevIndex = stack.pop();
                // Calculate days to wait: current index - previous index
                result[prevIndex] = i - prevIndex;
            }
            // Push current index onto stack
            stack.push(i);
        }
        
        // Remaining indices in stack have no warmer temperature (already 0 in result)
        return result;
    }
}
```

**Explanation of Key Code Sections:**

1. **Stack Initialization (Line 5):** We use a stack to store indices (not temperatures) of days. This allows us to calculate the number of days between indices.

2. **Result Array (Line 6):** We create an array to store the answer. By default, Java initializes integer arrays with zeros, which is exactly what we need for days with no warmer temperature.

3. **Main Iteration (Line 9):** We iterate through each temperature from left to right. For each day, we check if it's warmer than previous days stored in the stack.

4. **Finding Warmer Temperature (Lines 10-15):** 
   - **Condition (Line 10):** While the stack is not empty and the current temperature is greater than the temperature at the top of the stack, we've found a warmer day for the previous day.
   - **Pop and Calculate (Lines 12-14):** We pop the index of the colder day and calculate the number of days to wait: `i - prevIndex`. This gives us the difference in days.
   - **Store Result (Line 14):** We store this value in the result array at the previous day's index.

5. **Push Current Index (Line 17):** After processing all warmer temperatures for previous days, we push the current index onto the stack. The stack maintains indices in decreasing order of temperature.

6. **Remaining Elements (Line 20):** Any indices remaining in the stack represent days that never had a warmer temperature. Since the result array is initialized with zeros, these are already handled correctly.

**Why this approach works:**
- The stack maintains a decreasing sequence of temperatures.
- When we find a warmer temperature, it resolves all colder temperatures that are still on the stack.
- Each index is pushed and popped exactly once, giving us O(n) time complexity.

**Example walkthrough for `[73,74,75,71,69,72,76,73]`:**
- i=0: stack=[0], result=[0,0,0,0,0,0,0,0]
- i=1: 74>73, pop 0, result[0]=1, stack=[1], result=[1,0,0,0,0,0,0,0]
- i=2: 75>74, pop 1, result[1]=1, stack=[2], result=[1,1,0,0,0,0,0,0]
- i=3: 71<75, push 3, stack=[2,3]
- i=4: 69<71, push 4, stack=[2,3,4]
- i=5: 72>69, pop 4, result[4]=1; 72>71, pop 3, result[3]=2; stack=[2,5], result=[1,1,0,2,1,0,0,0]
- i=6: 76>72, pop 5, result[5]=1; 76>75, pop 2, result[2]=4; stack=[6], result=[1,1,4,2,1,1,0,0]
- i=7: 73<76, push 7, stack=[6,7]
- Final: result=[1,1,4,2,1,1,0,0]

## Complexity Analysis

- **Time Complexity:** $O(n)$ where $n$ is the length of the temperatures array. Each index is pushed and popped from the stack exactly once.

- **Space Complexity:** $O(n)$ for the stack in the worst case when temperatures are in decreasing order, and $O(n)$ for the result array.

## Similar Problems

Problems that can be solved using similar monotonic stack patterns:

1. **739. Daily Temperatures** (this problem) - Next greater element with distance
2. **496. Next Greater Element I** - Next greater element in array
3. **503. Next Greater Element II** - Circular array variant
4. **84. Largest Rectangle in Histogram** - Monotonic stack for area calculation
5. **42. Trapping Rain Water** - Stack for height tracking
6. **316. Remove Duplicate Letters** - Stack for lexicographical order
7. **402. Remove K Digits** - Stack to maintain increasing sequence
8. **456. 132 Pattern** - Stack to track decreasing sequence
9. **901. Online Stock Span** - Stack for previous greater elements
10. **1019. Next Greater Node in Linked List** - Stack on linked list

