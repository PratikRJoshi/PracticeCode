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