### LeetCode 45: Jump Game II

## Problem Statement

You are given a 0-indexed array of integers `nums` of length `n`. You are initially positioned at `nums[0]`.

Each element `nums[i]` represents the maximum length of a forward jump from index `i`. In other words, if you are at `nums[i]`, you can jump to any `nums[i + j]` where `0 <= j <= nums[i]` and `i + j < n`.

Return the minimum number of jumps to reach `nums[n - 1]`. The test cases are generated such that you can always reach `nums[n - 1]`.

---

## Intuition and Main Idea

This problem is a classic extension of Jump Game I. Since we need the *minimum* number of jumps, this smells like a shortest path problem on a graph, which is often solved with Breadth-First Search (BFS). The greedy approach we'll use is a direct optimization of BFS.

Imagine the jumps as levels in a BFS:
- **Level 0**: Starts and ends at index 0.
- **Level 1**: All indices reachable from Level 0.
- **Level 2**: All indices reachable from Level 1.

The minimum number of jumps is just the level number you are on when you first reach the end.

We can implement this idea greedily without an explicit queue. We'll keep track of the "window" of indices we can reach with the current number of jumps.

Let's define two pointers:
- `current_jump_end`: The end of the range of indices we can reach with our current number of jumps.
- `farthest_reach`: The farthest index we can possibly reach from any position within our current jump's window.

We iterate through the array. As we explore the indices within our current window (from `0` to `current_jump_end`), we continuously update `farthest_reach`. When our iteration index `i` finally reaches `current_jump_end`, it means we've exhausted all possibilities for the current jump. We must now make another jump. At this point, we increment our jump count and set our new window's end to be the `farthest_reach` we found.

---

## Algorithm: Greedy (BFS-like)

1.  **Initialization**:
    *   `jumps = 0`: The number of jumps taken so far.
    *   `current_jump_end = 0`: The end of the current jump's reach.
    *   `farthest_reach = 0`: The farthest we can reach in the next jump.

2.  **Iteration**: Loop through the `nums` array with an index `i` from `0` up to `nums.length - 2`. (We don't need to jump from the last element).

3.  **Inside the Loop**:
    *   **Update farthest reach**: At each index `i`, calculate how far we could get: `i + nums[i]`. Update `farthest_reach` to be the maximum of itself and this new potential: `farthest_reach = Math.max(farthest_reach, i + nums[i])`.
    *   **Trigger a jump**: If our loop index `i` reaches the end of our current jump's window (`i == current_jump_end`), it's time to make the next jump.
        *   Increment `jumps`.
        *   Set the end of the next jump's window to be the `farthest_reach` we've calculated: `current_jump_end = farthest_reach`.

4.  **Conclusion**: After the loop, `jumps` will hold the minimum number of jumps required to reach the end. Return `jumps`.

---

## Java Implementation

```java
class Solution {
    public int jump(int[] nums) {
        // If there's only one element, 0 jumps are needed.
        if (nums.length <= 1) {
            return 0;
        }

        int jumps = 0;
        int current_jump_end = 0;
        int farthest_reach = 0;

        // We iterate up to the second to last element.
        for (int i = 0; i < nums.length - 1; i++) {
            // Continuously update the farthest we can reach.
            farthest_reach = Math.max(farthest_reach, i + nums[i]);

            // If we've reached the end of the current jump's window,
            // we must make another jump.
            if (i == current_jump_end) {
                jumps++;
                // The new window ends at the farthest point we could see
                // from the previous window.
                current_jump_end = farthest_reach;

                // Optimization: if the new window covers the end, we can stop.
                if (current_jump_end >= nums.length - 1) {
                    break;
                }
            }
        }

        return jumps;
    }
}
```

---

## From Jump Game I to Jump Game II

The greedy solution for Jump Game I is the perfect foundation for solving Jump Game II. Let's see how we can tweak the logic.

**The Goal of Jump Game I:** Find out *if* we can reach the end. We do this by tracking the `farthest` possible reach. As long as our current position `i` is within this `farthest` boundary, we keep exploring and expanding it.

**The Goal of Jump Game II:** Find the *minimum number of jumps* to reach the end. This means we don't just care about the `farthest` reach, but also *how many jumps it took* to establish that reach.

This requires us to think in terms of "jump windows" or levels (like in a BFS). We need two more pieces of information:

1.  `jumps`: A counter for the number of jumps we've made.
2.  `current_jump_end`: A marker for the end of the current jump's window. This tells us when we are forced to make another jump.

Here's the modification to the logic:

1.  We still iterate through the array, and we still update `farthest` in exactly the same way.
2.  We add a check: when our loop index `i` reaches the `current_jump_end`, it signifies that we have explored all the positions reachable within the current jump. We are now stepping into the next "level."
3.  At this point, we must increment our `jumps` counter and update the boundary of our *new* window by setting `current_jump_end = farthest`.

By adding these two variables, we transform the "can we reach it?" algorithm into a "how many jumps to reach it?" algorithm, while keeping the core greedy structure.

```java
class Solution {
    public int jump(int[] nums) {
        if (nums.length <= 1) {
            return 0;
        }

        int jumps = 0;
        int current_jump_end = 0;
        int farthest = 0;

        for (int i = 0; i < nums.length - 1; i++) {
            // This part is the same as Jump Game I: 
            // always update the farthest possible reach.
            farthest = Math.max(farthest, i + nums[i]);

            // This is the new part for Jump Game II:
            // If we've reached the end of the current jump's window...
            if (i == current_jump_end) {
                // ...we are forced to make another jump.
                jumps++;
                // The new window will extend to the farthest point we found.
                current_jump_end = farthest;
            }
        }

        return jumps;
    }
}
```

---

## Complexity Analysis

Let `n` be the number of elements in the `nums` array.

*   **Time Complexity: O(n)**
    *   We perform a single pass through the array from left to right. The loop runs `n-1` times. Therefore, the time complexity is linear.

*   **Space Complexity: O(1)**
    *   We only use a few variables (`jumps`, `current_jump_end`, `farthest_reach`) to keep track of our state. No auxiliary data structures that scale with the input size are used. Therefore, the space complexity is constant.
