### 437. Path Sum III
Problem: https://leetcode.com/problems/path-sum-iii/

---

### Main Idea & Intuition

This problem asks for the number of paths that sum to a `targetSum`. The paths must go downwards but can start and end anywhere. A brute-force approach would be to start a DFS from every node, which would be inefficient (O(N^2) or O(N log N)).

A much more efficient solution uses the **prefix sum** technique, which is common in array problems, adapted for a tree.

---

### Intuitive Explanation (The 'Aha!' Moment)

Let's break down the key insight in simple terms, without the code.

#### The Idea in One Sentence

At any point in the tree, the sum of the path *ending at your current location* is just the `current_total_sum` (from the root) minus some `old_total_sum` from an ancestor.

#### The Array Analogy

Imagine you have a simple array of numbers, and you want to find a subarray that sums to `8`.

`Array: [10, 5, 3, -2, ...]`

Let's calculate the "prefix sum" as we go:
- At index 0 (value 10): `prefix_sum = 10`
- At index 1 (value 5): `prefix_sum = 10 + 5 = 15`
- At index 2 (value 3): `prefix_sum = 15 + 3 = 18`

Now, notice something cool at index 2. Our current prefix sum is `18`. The target is `8`.
If we ask the question: **"Have I seen a prefix sum of `18 - 8 = 10` before?"**

Yes, we have! We saw it at index 0.

This means the part of the array *between* where we saw the prefix sum of `10` and where we are now (with a prefix sum of `18`) must sum to `8`.

`[ (10),  5,  3 ]`
   ^      ^----^
   |      This part sums to 8
   Prefix sum was 10 here

That's the entire trick! **`current_prefix_sum - target_sum = old_prefix_sum`**

#### Translating this to a Tree

A tree path from the root to any node is just like a prefix sum.

Let's use an example. `targetSum = 8`.

```
      10
     /
    5
   /
  3
```

1.  **Start at the root (10):**
    *   The sum from the root to here is `10`.
    *   We ask: "Is there an old sum of `10 - 8 = 2`?" No.
    *   We store our current sum: "I've seen a path sum of `10`." 

2.  **Move to node (5):**
    *   The sum from the root to here is `10 + 5 = 15`.
    *   We ask: "Is there an old sum of `15 - 8 = 7`?" No.
    *   We store our current sum: "I've seen a path sum of `15`."

3.  **Move to node (3):**
    *   The sum from the root to here is `15 + 3 = 18`.
    *   We ask: "Is there an old sum of `18 - 8 = 10`?" **YES!** We saw a path sum of `10` when we were at the root.
    *   This means the path *between* the node where the sum was `10` (the root) and our current node (3) must sum to `8`.
    *   The path is `5 -> 3`. Its sum is `8`. We found one valid path! **`count = 1`**.

#### Why the Hash Map is Important

The hash map (`prefixSumMap`) is just a super-fast way to answer the question, **"Have I seen this old sum before, and how many times?"**

-   **Key:** The `old_prefix_sum`.
-   **Value:** How many times we've seen it on the path to get here.

#### The Final Piece: Backtracking

When we finish with a subtree, we have to undo our changes to the map. 

```
      10
     /  \
    5   -3  <-- We are about to explore this branch
```

When we were exploring the `5` branch, we stored sums like `10`, `15`, `18`. These sums have no relevance to the `-3` branch. So, after we finish exploring the entire subtree of a node, we must **remove its path sum from the map**. This is "backtracking." It's like saying, "Okay, I'm done with this path, let's pretend I never went down it," so that it doesn't interfere with the calculations for its sibling branches.

### Core Logic:
We traverse the tree using a pre-order DFS. As we go from the root down to a node, we calculate the `currentPathSum`. The key insight is: if a path from node `A` to node `B` (where `A` is an ancestor of `B`) sums to `targetSum`, then `prefixSum(B) - prefixSum(A) = targetSum`. Rearranging this gives `prefixSum(A) = prefixSum(B) - targetSum`.

So, at any node `B`, we need to know how many of its ancestors `A` have a prefix sum that satisfies this condition. We can track this efficiently using a hash map that stores `{prefixSum -> frequency}`.

### The Algorithm:
1.  Use a helper function for a pre-order traversal.
2.  Pass a hash map `prefixSumMap` that stores the counts of prefix sums encountered on the current path.
3.  Before the traversal, initialize the map with `{0, 1}`. This handles cases where a path starting from the root equals the `targetSum`.
4.  In the traversal at a `node`:
    a. Update `currentPathSum` by adding `node.val`.
    b. Check the map for `currentPathSum - targetSum`. The count of this value is the number of valid paths ending at the current node. Add this to our total count.
    c. Increment the map count for `currentPathSum`.
    d. Recurse to the left and right children.
    e. **Backtrack:** After returning from the children, decrement the map count for `currentPathSum`. This is crucial because prefix sums from one branch should not affect a sibling branch.

### Applying General Tree Intuitions

-   **Global Variable vs. Return Value**: We need a way to track the total count. A simple approach is a global (or class member) variable. The core of the algorithm, however, involves passing the `prefixSumMap` by reference, which is a form of shared state.

-   **Pre-order vs. Post-order**: This is a **pre-order (top-down)** traversal. We process the current node (update sum, check map, update map) *before* recursing to the children. The state (current path sum and the map) is passed downwards.

-   **Helper Function**: Yes, a helper function is essential. The main function needs to initialize the count, the map, and then kick off the recursion. The helper needs to accept the current node, the map, the target sum, and the current path sum.

---

### Code Solution (Prefix Sum with DFS)

This version is refactored to avoid using class member variables. State is passed through the helper function's parameters, which is generally a cleaner approach.

```java
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
import java.util.HashMap;

class Solution {
    public int pathSum(TreeNode root, int targetSum) {
        // The map stores {prefix_sum -> frequency}.
        HashMap<Long, Integer> prefixSumMap = new HashMap<>();
        // A prefix sum of 0 has a frequency of 1 to handle paths that start from the root.
        prefixSumMap.put(0L, 1);
        
        return dfs(root, targetSum, prefixSumMap, 0L);
    }

    private int dfs(TreeNode node, long targetSum, HashMap<Long, Integer> prefixSumMap, long currentPathSum) {
        if (node == null) {
            return 0;
        }

        // 1. Update the current path sum
        currentPathSum += node.val;

        // 2. Calculate paths ending at the CURRENT node
        // This is the core logic: currentPathSum - (currentPathSum - targetSum) = targetSum
        int pathCount = prefixSumMap.getOrDefault(currentPathSum - targetSum, 0);

        // 3. Update the map with the current path sum for descendant nodes
        prefixSumMap.put(currentPathSum, prefixSumMap.getOrDefault(currentPathSum, 0) + 1);

        // 4. Recurse and get path counts from children
        pathCount += dfs(node.left, targetSum, prefixSumMap, currentPathSum);
        pathCount += dfs(node.right, targetSum, prefixSumMap, currentPathSum);

        // 5. Backtrack: Remove the current path sum from the map to not affect sibling paths
        prefixSumMap.put(currentPathSum, prefixSumMap.get(currentPathSum) - 1);
        
        return pathCount;
    }
}

---

### Complexity Analysis

*   **Time Complexity: O(N)**
    *   We visit every node in the tree exactly once. The hash map operations (get, put) take O(1) time on average.

*   **Space Complexity: O(H)**
    *   The space complexity is determined by the recursion stack depth (`H`) and the size of the hash map. In the worst case (a skewed tree), the path from root to leaf contains `N` nodes, so the hash map could store up to `N` unique prefix sums, making the space complexity `O(N)`. In a balanced tree, the height `H` is `log N`, so the space is `O(log N)`.
