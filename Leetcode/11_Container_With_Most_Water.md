### 11. Container With Most Water
### Problem Link: [Container With Most Water](https://leetcode.com/problems/container-with-most-water/)
### Intuition
This problem asks us to find two lines that, together with the x-axis, form a container that can hold the most water. The amount of water a container can hold is determined by the height of the shorter line and the distance between the lines.

The key insight is to use a two-pointer approach, starting with the widest possible container (pointers at both ends) and then moving inward. At each step, we move the pointer pointing to the shorter line inward, since keeping the shorter line and reducing width would only decrease the container's capacity.

### Java Reference Implementation
```java
class Solution {
    public int maxArea(int[] height) {
        int start = 0, end = height.length - 1; // [R0] Initialize two pointers at both ends
        int temp = 0, max = Integer.MIN_VALUE; // [R1] Track current and maximum area

        while(start < end){ // [R2] Continue until pointers meet
            if(height[start] < height[end]){ // [R3] Compare heights at both pointers
                temp = (end - start) * height[start]; // [R4] Calculate area with shorter height
                start++; // [R5] Move the pointer with shorter height inward
            } else {
                temp = (end - start) * height[end]; // [R4] Calculate area with shorter height
                end--; // [R5] Move the pointer with shorter height inward
            }

            max = Math.max(max, temp); // [R6] Update maximum area if current area is larger
        }

        return max; // [R7] Return the maximum area found
    }
}
```
### Requirement → Code Mapping
- **R0 (Initialize pointers)**: `int start = 0, end = height.length - 1;` - Start with the widest possible container
- **R1 (Track areas)**: `int temp = 0, max = Integer.MIN_VALUE;` - Variables to track current and maximum areas
- **R2 (Iterate until pointers meet)**: `while(start < end)` - Continue until we've examined all possible containers
- **R3 (Compare heights)**: `if(height[start] < height[end])` - Determine which line is shorter
- **R4 (Calculate area)**: `temp = (end - start) * height[start/end]` - Area is width × height of shorter line
- **R5 (Move pointer)**: `start++` or `end--` - Move the pointer with shorter height inward
- **R6 (Update maximum)**: `max = Math.max(max, temp)` - Keep track of the maximum area seen so far
- **R7 (Return result)**: `return max` - Return the maximum container capacity

### Complexity Analysis
- **Time Complexity**: O(n) - We make a single pass through the array with the two pointers
- **Space Complexity**: O(1) - We use a constant amount of extra space

### Comparison with Trapping RainWater (Problem 42)

While both problems use a two-pointer approach, they solve fundamentally different water-related challenges:

#### Key Differences:

1. **Problem Statement**:
    - **Trapping Rain Water**: Calculate water trapped *above* positions between bars
    - **Container With Most Water**: Find maximum water contained *between* two lines

2. **What We're Optimizing**:
    - **Trapping Rain Water**: Total accumulated water across all positions
    - **Container With Most Water**: Single maximum area between two lines

3. **Pointer Movement Logic**:
    - **Trapping Rain Water**: Move pointer with smaller *maximum height seen so far*
    - **Container With Most Water**: Move pointer with smaller *current height*

4. **Calculation Formula**:
    - **Trapping Rain Water**: `water += min(leftMax, rightMax) - height[current]`
    - **Container With Most Water**: `area = (right - left) * min(height[left], height[right])`

#### Code Comparison:

```java
// Trapping Rain Water (42)
while (left < right) {
    leftMax = Math.max(leftMax, height[left]);    // Track maximum heights
    rightMax = Math.max(rightMax, height[right]);
    
    if (leftMax < rightMax) {
        water += leftMax - height[left];          // Accumulate water
        left++;
    } else {
        water += rightMax - height[right];
        right--;
    }
}
```

```java
// Container With Most Water (11)
while (start < end) {
    if (height[start] < height[end]) {
        temp = (end - start) * height[start];     // Calculate area
        start++;
    } else {
        temp = (end - start) * height[end];
        end--;
    }
    
    max = Math.max(max, temp);                    // Track maximum
}
```

Both solutions achieve O(n) time complexity and O(1) space complexity, but they solve different problems with subtle yet important variations in their implementation details.
