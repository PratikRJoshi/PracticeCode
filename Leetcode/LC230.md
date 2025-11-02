### 230. Kth Smallest Element in a BST

#### Problem Statement
[Kth Smallest Element in a BST](https://leetcode.com/problems/kth-smallest-element-in-a-bst/)

---

### Main Idea & Intuition

The key property of a Binary Search Tree (BST) is that an **in-order traversal** (`Left`, `Root`, `Right`) visits the nodes in ascending sorted order. This means the k-th smallest element is simply the k-th element we encounter during an in-order traversal.

I will demonstrate two common ways to solve this: a recursive approach and an iterative approach using a stack.

### 1. Recursive Solution

In this approach, we perform a standard recursive in-order traversal. We can maintain a counter to keep track of the visited nodes. When the counter matches `k`, we have found our element.

> **Why Use Member Variables (`count`, `result`)?**
>
> This is a great example of when to use member variables (or "global" state) in a recursive problem. The key insight comes from asking: **"Does the information my function needs to *return to its parent* differ from the information I need to *solve the overall problem*?"**
>
> 1.  **What the Parent Needs:** Our `inorderTraversal(node)` function has a `void` return type. Its only job is to traverse. It doesn't need to return any computed value to its parent call.
> 2.  **What We Need for the Overall Problem:** We need to maintain two pieces of state across the entire traversal: a `count` of visited nodes and the `result` once we find it. This state must be preserved as we move between left subtrees, parent nodes, and right subtrees.
>
> Since the recursive function doesn't have a meaningful value to return, but we need to track state globally across all calls, member variables are the perfect tool. The function's main purpose becomes its **side effect**: updating `count` and `result`.


```java
import java.util.ArrayList;

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
class SolutionRecursive {
    private int count;
    private int result;

    public int kthSmallest(TreeNode root, int k) {
        this.count = k;
        this.result = -1; // Default value
        inorderTraversal(root);
        return result;
    }

    private void inorderTraversal(TreeNode node) {
        if (node == null || count == 0) {
            return;
        }

        // Traverse left subtree
        inorderTraversal(node.left);

        // Process the current node
        if (count > 0) {
            count--;
            if (count == 0) {
                result = node.val;
                return; // Found the kth element, stop traversal
            }
        }

        // Traverse right subtree
        inorderTraversal(node.right);
    }
}
```

### 2. Iterative Solution

This approach uses an explicit `Stack` to simulate the in-order traversal, which can be more efficient in terms of memory for highly unbalanced trees by avoiding deep recursion.

```java
import java.util.Stack;

class SolutionIterative {
    public int kthSmallest(TreeNode root, int k) {
        Stack<TreeNode> stack = new Stack<>();
        TreeNode currentNode = root;

        while (currentNode != null || !stack.isEmpty()) {
            // 1. Go left as far as possible
            while (currentNode != null) {
                stack.push(currentNode);
                currentNode = currentNode.left;
            }

            // 2. Visit the node at the top of the stack
            currentNode = stack.pop();
            k--;

            // 3. Check if this is the kth smallest element
            if (k == 0) {
                return currentNode.val;
            }

            // 4. Move to the right subtree
            currentNode = currentNode.right;
        }
        
        return -1; // Should not be reached if k is valid
    }
}
```

### Complexity Analysis
*   **Time Complexity**: `O(H + k)`, where `H` is the height of the tree. We traverse down to the smallest element (which takes `O(H)` time) and then perform `k-1` more steps of the in-order traversal. In the worst case of a skewed tree, this becomes `O(N + k)`, which simplifies to `O(N)`.
*   **Space Complexity**: `O(H)`. This space is used by the recursion call stack in the recursive solution, or by the explicit `Stack` in the iterative solution. In the worst case of a skewed tree, `H = N`, so the space complexity is `O(N)`. For a balanced tree, it is `O(log N)`.