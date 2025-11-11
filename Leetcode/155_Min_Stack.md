# Min Stack

## Problem Description

**Problem Link:** [Min Stack](https://leetcode.com/problems/min-stack/)

Design a stack that supports push, pop, top, and retrieving the minimum element in constant time.

Implement the `MinStack` class:

- `MinStack()` initializes the stack object.
- `void push(int val)` pushes the element `val` onto the stack.
- `void pop()` removes the element on the top of the stack.
- `int top()` gets the top element of the stack.
- `int getMin()` retrieves the minimum element in the stack.

You must implement a solution with `O(1)` time complexity for each function.

**Example 1:**
```
Input
["MinStack","push","push","push","getMin","pop","top","getMin"]
[[],[-2],[0],[-3],[],[],[],[]]

Output
[null,null,null,null,-3,null,0,-2]

Explanation
MinStack minStack = new MinStack();
minStack.push(-2);
minStack.push(0);
minStack.push(-3);
minStack.getMin(); // return -3
minStack.pop();
minStack.top();    // return 0
minStack.getMin(); // return -2
```

**Constraints:**
- `-2^31 <= val <= 2^31 - 1`
- Methods `pop`, `top` and `getMin` operations will always be called on non-empty stacks.
- At most `3 * 10^4` calls will be made to `push`, `pop`, `top`, and `getMin`.

## Intuition/Main Idea

The core challenge is maintaining the minimum element in O(1) time while supporting standard stack operations. The naive approach of scanning the entire stack to find the minimum would take O(n) time, which violates the requirement.

**Key Insight:** Use two stacks - one to store all elements and another to track the minimum value at each level of the stack. The minimum stack maintains the minimum value that exists at or below each position in the main stack. This way, when we pop from the main stack, we can also pop from the minimum stack to get the correct minimum for the remaining elements.

**Why this works:** When we push a new element, we compare it with the current minimum. If it's smaller or equal, it becomes the new minimum. Otherwise, we keep the previous minimum. This ensures that the top of the minimum stack always represents the minimum value in the current state of the main stack.

## Code Mapping

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Initialize empty stack | Constructor `MinStack()` - Lines 7-10 |
| Push element onto stack | `push(int val)` method - Lines 12-18 |
| Remove top element | `pop()` method - Lines 20-23 |
| Get top element | `top()` method - Lines 25-27 |
| Get minimum element in O(1) | `getMin()` method - Lines 29-31 |
| Maintain minimum at each level | `minStack` data structure - Line 5 |

## Final Java Code & Learning Pattern

```java
import java.util.Stack;

class MinStack {
    // Main stack to store all elements
    private Stack<Integer> stack;
    // Minimum stack to track minimum at each level
    private Stack<Integer> minStack;
    
    public MinStack() {
        // Initialize both stacks as empty
        stack = new Stack<>();
        minStack = new Stack<>();
    }
    
    public void push(int val) {
        // Always push to main stack
        stack.push(val);
        
        // For minimum stack: if empty or new value is <= current min, push the new value
        // Otherwise, push the current minimum again to maintain synchronization
        if (minStack.isEmpty() || val <= minStack.peek()) {
            minStack.push(val);
        } else {
            minStack.push(minStack.peek());
        }
    }
    
    public void pop() {
        // Pop from both stacks to maintain synchronization
        // This ensures minStack always reflects the minimum of remaining elements
        stack.pop();
        minStack.pop();
    }
    
    public int top() {
        // Return top element from main stack
        return stack.peek();
    }
    
    public int getMin() {
        // Return top element from min stack, which is the current minimum
        return minStack.peek();
    }
}
```

**Explanation of Key Code Sections:**

1. **Two Stack Approach (Lines 4-5):** We maintain two stacks - `stack` for all elements and `minStack` for minimum values. This separation allows us to track the minimum independently.

2. **Push Operation (Lines 12-18):** 
   - We always push to the main stack (line 14).
   - For the minimum stack, we check if it's empty or if the new value is less than or equal to the current minimum (line 17). 
   - If true, we push the new value (line 18). Otherwise, we push the current minimum again (line 20). This ensures that `minStack` has the same number of elements as `stack`, with each position storing the minimum value at that level.

3. **Pop Operation (Lines 22-25):** We pop from both stacks simultaneously. This maintains the invariant that `minStack.peek()` always represents the minimum of all elements currently in `stack`.

4. **Top Operation (Lines 27-29):** Simply return the top element of the main stack.

5. **GetMin Operation (Lines 31-33):** Return the top element of the minimum stack, which is guaranteed to be the minimum value in the current stack state.

**Why `val <= minStack.peek()` instead of `val < minStack.peek()`?** We use `<=` to handle duplicate minimum values correctly. If we only used `<`, when we have multiple instances of the same minimum value and pop one, we might incorrectly remove the minimum from `minStack` even though another instance still exists in the main stack.

## Complexity Analysis

- **Time Complexity:** $O(1)$ for all operations (`push`, `pop`, `top`, `getMin`). Each operation performs a constant number of stack operations.

- **Space Complexity:** $O(n)$ where $n$ is the number of elements pushed onto the stack. We use two stacks, each storing up to $n$ elements, resulting in $O(2n) = O(n)$ space.

## Similar Problems

Problems that can be solved using similar patterns or related stack techniques:

1. **716. Max Stack** - Similar concept but tracks maximum instead of minimum
2. **225. Implement Stack using Queues** - Stack implementation problem
3. **232. Implement Queue using Stacks** - Related data structure implementation
4. **496. Next Greater Element I** - Uses stack to track elements efficiently
5. **503. Next Greater Element II** - Stack-based problem with circular array
6. **739. Daily Temperatures** - Monotonic stack pattern
7. **84. Largest Rectangle in Histogram** - Uses stack to maintain increasing/decreasing sequences
8. **42. Trapping Rain Water** - Stack-based approach for tracking heights
9. **155. Min Stack** (this problem) - Two-stack pattern for tracking minimum
10. **901. Online Stock Span** - Monotonic stack to track previous greater elements

