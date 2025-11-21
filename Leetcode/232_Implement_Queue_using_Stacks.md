# Implement Queue using Stacks

## Problem Description

**Problem Link:** [Implement Queue using Stacks](https://leetcode.com/problems/implement-queue-using-stacks/)

Implement a first in first out (FIFO) queue using only two stacks. The implemented queue should support all the functions of a normal queue (`push`, `peek`, `pop`, and `empty`).

Implement the `MyQueue` class:

- `void push(int x)` Pushes element x to the back of the queue.
- `int pop()` Removes the element from the front of the queue and returns it.
- `int peek()` Returns the element at the front of the queue.
- `boolean empty()` Returns `true` if the queue is empty, `false` otherwise.

**Notes:**

- You must use **only** standard operations of a stack, which means only `push to top`, `peek/pop from top`, `size`, and `is empty` operations are valid.
- Depending on your language, the stack may not be supported natively. You may simulate a stack using a list or deque (double-ended queue) as long as you use only a stack's standard operations.

**Example 1:**
```
Input
["MyQueue", "push", "push", "peek", "pop", "empty"]
[[], [1], [2], [], [], []]
Output
[null, null, null, 1, 1, false]

Explanation
MyQueue myQueue = new MyQueue();
myQueue.push(1); // queue is: [1]
myQueue.push(2); // queue is: [1, 2] (leftmost is front of the queue)
myQueue.peek(); // return 1
myQueue.pop(); // return 1, queue is [2]
myQueue.empty(); // return false
```

**Constraints:**
- `1 <= x <= 9`
- At most `100` calls will be made to `push`, `pop`, `peek`, and `empty`.
- All the calls to `pop` and `peek` are valid.

## Intuition/Main Idea

We need to implement FIFO (queue) using LIFO (stack). The key insight is to use two stacks: one for input, one for output.

**Core Algorithm:**
- Use two stacks: `inputStack` and `outputStack`
- `push`: Add to input stack
- `pop`/`peek`: If output stack empty, transfer all from input to output, then pop/peek from output
- This reverses the order, making FIFO behavior

**Why two stacks:** When we transfer from input to output stack, the order reverses. The first element pushed becomes the last in output stack (top), which we can pop first. This achieves FIFO behavior.

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|------------------------|-----------------------------------|
| Implement queue with stacks | Two stacks - Lines 3-4 |
| Push operation | Add to input stack - Lines 8-10 |
| Pop operation | Transfer and pop - Lines 12-18 |
| Peek operation | Transfer and peek - Lines 20-26 |
| Empty check | Both stacks empty - Lines 28-30 |
| Transfer stacks | Move all elements - Lines 15-17 |

## Final Java Code & Learning Pattern (Full Content)

```java
class MyQueue {
    // Two stacks: input for push, output for pop/peek
    // Transferring from input to output reverses order, achieving FIFO
    private Stack<Integer> inputStack;
    private Stack<Integer> outputStack;
    
    public MyQueue() {
        inputStack = new Stack<>();
        outputStack = new Stack<>();
    }
    
    public void push(int x) {
        // Push to input stack
        // This is O(1) operation
        inputStack.push(x);
    }
    
    public int pop() {
        // If output stack is empty, transfer all from input to output
        // This reverses the order, making oldest element at top of output
        if (outputStack.isEmpty()) {
            while (!inputStack.isEmpty()) {
                outputStack.push(inputStack.pop());
            }
        }
        // Pop from output stack (oldest element)
        return outputStack.pop();
    }
    
    public int peek() {
        // Similar to pop, but don't remove element
        if (outputStack.isEmpty()) {
            while (!inputStack.isEmpty()) {
                outputStack.push(inputStack.pop());
            }
        }
        // Peek at top of output stack (oldest element)
        return outputStack.peek();
    }
    
    public boolean empty() {
        // Queue is empty if both stacks are empty
        return inputStack.isEmpty() && outputStack.isEmpty();
    }
}
```

## Complexity Analysis

**Time Complexity:** 
- `push`: $O(1)$
- `pop`: Amortized $O(1)$ (each element transferred once)
- `peek`: Amortized $O(1)$
- `empty`: $O(1)$

**Space Complexity:** $O(n)$ for storing elements in stacks.

## Similar Problems

- [Implement Stack using Queues](https://leetcode.com/problems/implement-stack-using-queues/) - Reverse problem
- [Min Stack](https://leetcode.com/problems/min-stack/) - Stack with additional functionality
- [Design Circular Queue](https://leetcode.com/problems/design-circular-queue/) - Different queue implementation

