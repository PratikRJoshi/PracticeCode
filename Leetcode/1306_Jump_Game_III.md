# Jump Game III

## Problem Description

**Problem Link:** [Jump Game III](https://leetcode.com/problems/jump-game-iii/)

You are given an array of non-negative integers `arr` and an integer `start`.

When you are at an index `i`, you can jump to:

- `i + arr[i]` (to the right)
- `i - arr[i]` (to the left)

as long as the destination index is within bounds.

Return `true` if you can reach **any** index with value `0`, otherwise return `false`.

**Example 1:**
```
Input: arr = [4,2,3,0,3,1,2], start = 5
Output: true
Explanation:
One possible sequence is 5 -> 4 -> 1 -> 3, and arr[3] == 0.
```

**Example 2:**
```
Input: arr = [4,2,3,0,3,1,2], start = 0
Output: true
Explanation:
One possible sequence is 0 -> 4 -> 1 -> 3, and arr[3] == 0.
```

**Example 3:**
```
Input: arr = [3,0,2,1,2], start = 2
Output: false
Explanation:
There is no way to reach an index with value 0.
```

**Constraints:**
- `1 <= arr.length <= 5 * 10^4`
- `0 <= arr[i] < arr.length`
- `0 <= start < arr.length`

## Intuition/Main Idea

### Think of the array as a graph

Even though this is an array problem, it behaves like a graph reachability problem:

- Each index `i` is a node.
- From `i`, you have up to 2 directed edges:
  - to `i + arr[i]`
  - to `i - arr[i]`

The question “can I reach any index with value 0?” becomes:

- Starting from node `start`, can we reach any node `j` where `arr[j] == 0`?

This is the exact same type of thinking as Jump Game I/II (reachability / BFS levels), except here:

- jumps are not “up to nums[i]”
- jumps are exactly “± arr[i]”

### Key pitfall: cycles

Because you can jump left and right, it’s easy to create cycles like:

- `i -> j -> i -> j -> ...`

So we must track `visited` indices.

### BFS vs DFS

Either works because we only need reachability.

- **BFS** is iterative and avoids recursion depth issues.
- **DFS** is also fine, but recursion might be risky for large inputs.

We’ll implement BFS with a queue:

1. Start by putting `start` in the queue.
2. Pop an index `i`.
3. If `arr[i] == 0`, return `true`.
4. Push its valid neighbors if not visited.
5. If queue empties, return `false`.

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|------------------------|-----------------------------------|
| From index `i`, jump to `i + arr[i]` and `i - arr[i]` | Compute `rightIndex` and `leftIndex` neighbors (lines 23-33) |
| Return `true` if any reachable index has value `0` | Check `arr[currentIndex] == 0` during traversal (lines 18-22) |
| Avoid infinite loops due to cycles | `visited[]` prevents revisiting indices (lines 12-17, 26-33) |
| Work within bounds | Bound checks before enqueuing neighbors (lines 27-33) |

## Final Java Code & Learning Pattern (Full Content)

```java
import java.util.ArrayDeque;
import java.util.Queue;

class Solution {
    public boolean canReach(int[] arr, int start) {
        // visited[i] tells us whether we've already processed index i.
        // This is crucial because the left/right jumps can create cycles.
        boolean[] visited = new boolean[arr.length];

        Queue<Integer> queue = new ArrayDeque<>();
        queue.offer(start);
        visited[start] = true;

        while (!queue.isEmpty()) {
            int currentIndex = queue.poll();

            // If we land on a 0-valued index, we succeed.
            if (arr[currentIndex] == 0) {
                return true;
            }

            // From currentIndex, we have up to 2 next positions.
            int jumpDistance = arr[currentIndex];
            int rightIndex = currentIndex + jumpDistance;
            int leftIndex = currentIndex - jumpDistance;

            // Try the right jump if it stays inside the array and we haven't visited it.
            if (rightIndex >= 0 && rightIndex < arr.length && !visited[rightIndex]) {
                visited[rightIndex] = true;
                queue.offer(rightIndex);
            }

            // Try the left jump if it stays inside the array and we haven't visited it.
            if (leftIndex >= 0 && leftIndex < arr.length && !visited[leftIndex]) {
                visited[leftIndex] = true;
                queue.offer(leftIndex);
            }
        }

        // If we explored everything reachable and never saw a 0, it's impossible.
        return false;
    }
}
```

## Complexity Analysis

**Time Complexity:** $O(n)$

- Each index is enqueued/dequeued at most once.
- Each index generates at most 2 neighbor checks.

**Space Complexity:** $O(n)$

- `visited[]` and the queue can hold up to `n` indices.

## Similar Problems

- [Jump Game](https://leetcode.com/problems/jump-game/) - greedy reachability on an array
- [Jump Game II](https://leetcode.com/problems/jump-game-ii/) - minimum jumps (BFS-level idea)
- [Jump Game IV](https://leetcode.com/problems/jump-game-iv/) - BFS on an implicit graph of indices
- [Keys and Rooms](https://leetcode.com/problems/keys-and-rooms/) - graph reachability with visited tracking
