### 42. Trapping Rain Water
### Problem Link: [Trapping Rain Water](https://leetcode.com/problems/trapping-rain-water/)
### Intuition
This problem asks us to calculate how much rainwater can be trapped between bars of different heights. The key insight is that the amount of water that can be trapped at any position depends on the minimum of the maximum heights to its left and right, minus the height of the current position.

There are several approaches to solve this problem:
1. Brute Force: For each position, find the maximum height to its left and right
2. Dynamic Programming: Precompute the maximum heights to the left and right of each position
3. Two Pointers: Use two pointers to track the maximum heights from both ends
4. Stack-based: Use a stack to keep track of bars that can trap water

The two-pointer approach is the most efficient in terms of both time and space complexity.

### Java Reference Implementation (Two Pointers)
```java
class Solution {
    public int trap(int[] height) {
        if (height == null || height.length < 3) { // [R0] Handle edge cases
            return 0;
        }
        
        int left = 0;
        int right = height.length - 1;
        int leftMax = 0;
        int rightMax = 0;
        int water = 0;
        
        // [R1] Use two pointers to scan from both ends
        while (left < right) {
            // [R2] Update the maximum height from the left
            leftMax = Math.max(leftMax, height[left]);
            
            // [R3] Update the maximum height from the right
            rightMax = Math.max(rightMax, height[right]);
            
            // [R4] Calculate water trapped at the current position
            if (leftMax < rightMax) {
                // [R5] If leftMax is smaller, calculate water at left position
                water += leftMax - height[left];
                left++;
            } else {
                // [R6] If rightMax is smaller, calculate water at right position
                water += rightMax - height[right];
                right--;
            }
        }
        
        return water; // [R7] Return the total trapped water
    }
}
```

### Alternative Implementation (Dynamic Programming)
```java
class Solution {
    public int trap(int[] height) {
        if (height == null || height.length < 3) {
            return 0;
        }
        
        int n = height.length;
        int[] leftMax = new int[n];
        int[] rightMax = new int[n];
        
        // Precompute the maximum height to the left of each position
        leftMax[0] = height[0];
        for (int i = 1; i < n; i++) {
            leftMax[i] = Math.max(leftMax[i - 1], height[i]);
        }
        
        // Precompute the maximum height to the right of each position
        rightMax[n - 1] = height[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            rightMax[i] = Math.max(rightMax[i + 1], height[i]);
        }
        
        // Calculate the trapped water at each position
        int water = 0;
        for (int i = 0; i < n; i++) {
            water += Math.min(leftMax[i], rightMax[i]) - height[i];
        }
        
        return water;
    }
}
```

### Alternative Implementation (Stack-based)
```java
class Solution {
    public int trap(int[] height) {
        if (height == null || height.length < 3) {
            return 0;
        }
        
        int water = 0;
        Stack<Integer> stack = new Stack<>();
        
        for (int i = 0; i < height.length; i++) {
            // While the current bar is taller than the bar at the top of the stack
            while (!stack.isEmpty() && height[i] > height[stack.peek()]) {
                int top = stack.pop();
                
                // If the stack is empty, no water can be trapped
                if (stack.isEmpty()) {
                    break;
                }
                
                // Calculate the distance and bounded height
                int distance = i - stack.peek() - 1;
                int boundedHeight = Math.min(height[i], height[stack.peek()]) - height[top];
                
                // Add the trapped water
                water += distance * boundedHeight;
            }
            
            // Push the current index onto the stack
            stack.push(i);
        }
        
        return water;
    }
}
```

### Understanding the Algorithm and Two-Pointer Technique

1. **Water Trapping Principle:**
   - At any position i, the amount of water that can be trapped is:
     - min(maximum height to the left of i, maximum height to the right of i) - height[i]
   - If this value is negative, no water is trapped at that position (we treat it as 0)

2. **Two-Pointer Approach:**
   - We use two pointers, left and right, starting from the ends of the array
   - We maintain leftMax and rightMax to track the maximum heights seen so far from both ends
   - At each step, we move the pointer pointing to the smaller height
   - This ensures that we always have the correct minimum of the maximum heights

3. **Why This Works:**
   - If leftMax < rightMax, we know that the water trapped at the left position is determined by leftMax
   - Similarly, if rightMax < leftMax, the water trapped at the right position is determined by rightMax
   - By moving the pointer with the smaller maximum, we ensure that we have the correct boundary for calculating trapped water

4. **Dynamic Programming Approach:**
   - We precompute the maximum heights to the left and right of each position
   - This allows us to calculate the trapped water in a single pass through the array
   - However, it requires O(n) extra space

5. **Stack-based Approach:**
   - We use a stack to keep track of bars that can potentially trap water
   - When we find a bar taller than the one at the top of the stack, we know that water can be trapped
   - We calculate the trapped water based on the distance and the bounded height

### Requirement → Code Mapping
- **R0 (Handle edge cases)**: `if (height == null || height.length < 3) { return 0; }` - Return 0 for invalid inputs
- **R1 (Use two pointers)**: Initialize pointers at both ends of the array
- **R2 (Update leftMax)**: Keep track of the maximum height from the left
- **R3 (Update rightMax)**: Keep track of the maximum height from the right
- **R4 (Calculate water)**: Determine which pointer to move based on the maximum heights
- **R5 (Left case)**: Calculate water at the left position and move the left pointer
- **R6 (Right case)**: Calculate water at the right position and move the right pointer
- **R7 (Return result)**: Return the total trapped water

### Complexity Analysis
- **Time Complexity**: O(n)
  - Two-Pointer approach: We scan the array once with two pointers
  - Dynamic Programming approach: We make three passes through the array
  - Stack-based approach: Each element is pushed and popped at most once

- **Space Complexity**:
  - Two-Pointer approach: O(1) - We use only a constant amount of extra space
  - Dynamic Programming approach: O(n) - We use two arrays of size n
  - Stack-based approach: O(n) - In the worst case, the stack can contain all elements

### Related Problems
- **Container With Most Water** (Problem 11): Similar concept but with different constraints
- **Largest Rectangle in Histogram** (Problem 84): Uses a similar stack-based approach
- **Product of Array Except Self** (Problem 238): Uses a similar technique of computing from both ends


### Comparison with Container With Most Water (Problem 11)

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
