
### When to Use a Global Variable in Recursive Problems

Knowing when to use a "global" variable (or a class member variable, in this case) versus passing all state through function returns is a key design decision in recursive problems.

Here’s a simple framework to help you decide.

#### The Core Question to Ask

When designing your recursive function, ask yourself:

**"Does the information my function needs to *return to its parent* differ from the information I need to *solve the overall problem*?"**

Let's break this down:

1.  **Information for the Parent (The Return Value):** This is the data the calling function needs to continue its own calculation. In a tree traversal, it's what a parent node needs from its child.
2.  **Information for the Overall Problem (The "Side" Calculation):** This is the data you're ultimately trying to find, which might be updated at every step of the recursion, but isn't necessarily what the parent node needs.

#### Two Scenarios

##### Scenario 1: The information is the SAME.

If the parent node needs the exact same piece of information that you are calculating to solve the problem, **you do not need a global variable.** You can simply use the function's return value.

*   **Example: Find the height of a binary tree.**
    *   A parent node needs to know the height of its children to calculate its own height.
    *   The final answer is the height of the root node.
    *   The information passed up (`height`) is the same as the information needed to solve the problem.
    *   **Conclusion:** Just `return 1 + Math.max(leftHeight, rightHeight)`. No global variable needed.

##### Scenario 2: The information is DIFFERENT.

If the information the parent needs is different from the information you need to track for the final answer, you have two main choices: a global variable or a complex return type. This is where the "Diameter of a Binary Tree" problem fits.

*   **Analysis of the Diameter Problem:**
    *   **What the parent needs:** To calculate its own height, a parent node needs the `height` of its child. So, our recursive function *must* return a height.
    *   **What we need for the final answer:** We need to find the maximum `left_height + right_height` across the *entire tree*. This value is the diameter at a given node.
    *   **The Conflict:** The parent doesn't care about the diameter found deep in its child's subtree. It only wants the child's height. The `diameter` is a separate piece of information we're tracking on the side.

This conflict leads to two patterns:

**Pattern A: Use a Global Variable (or Class Member)**

This is the approach we took. It's often cleaner and more intuitive.

*   **How it works:** The recursive function has one clear responsibility: **return the height**. As a *side effect*, it also updates a global `maxDiameter` variable whenever it has the information to do so (i.e., after it gets the heights of both children).
*   **When to use it:** This is a great choice when the recursive function has a clear, primary return value (like `height`), and the overall problem requires tracking some maximum/minimum/count on the side.

**Pattern B: Return a Complex Object (e.g., an array or pair)**

This approach avoids global state, which is often considered better practice in software engineering, but can make the code more complex.

*   **How it works:** The function would return both pieces of information at once. For example, it could return an array `[height, maxDiameterInSubtree]`.
*   **The `diameter` code using this pattern would look like this:**

    ```java
    // No global variable needed
    public int diameterOfBinaryTree(TreeNode root) {
        // The final answer is the diameter from the result of the root call
        return heightAndDiameter(root)[1];
    }

    // Returns an array: [height, diameter]
    private int[] heightAndDiameter(TreeNode node) {
        if (node == null) {
            return new int[]{0, 0}; // {height, diameter}
        }

        int[] leftResult = heightAndDiameter(node.left);
        int[] rightResult = heightAndDiameter(node.right);

        int leftHeight = leftResult[0];
        int rightHeight = rightResult[0];

        // Diameter at this node
        int diameterThroughNode = leftHeight + rightHeight;

        // The max diameter so far is the max of:
        // 1. The max diameter found in the left subtree.
        // 2. The max diameter found in the right subtree.
        // 3. The diameter passing through the current node.
        int maxDiameterSoFar = Math.max(diameterThroughNode, Math.max(leftResult[1], rightResult[1]));

        // The height of this node
        int currentHeight = 1 + Math.max(leftHeight, rightHeight);

        return new int[]{currentHeight, maxDiameterSoFar};
    }
    ```
*   **When to use it:** When you absolutely must avoid side effects and global state. As you can see, the logic to combine results from the children becomes more complicated.

### Summary

| Condition                                                                    | Your Best Bet                                      | Why                                                                                             |
| ---------------------------------------------------------------------------- | -------------------------------------------------- | ----------------------------------------------------------------------------------------------- |
| The recursive function's return value is the **same** as the info you need.  | **Use the return value.**                          | Simple, clean, and no side effects.                                                             |
| The return value is **different** from the info you need to track globally.  | **Use a global/member variable.** (Usually easier) | Keeps the recursive function's logic simple and focused on its primary return value.            |
| Same as above, but you want to avoid global state.                           | **Return a complex object/array.** (More advanced) | More "pure" functional style, but requires more complex logic to combine results from children. |

---

### Pre-order vs. Post-order Processing in Recursive Helpers

The decision of whether to do work *before* or *after* the recursive calls depends on the flow of information: are you passing information **down** the tree, or are you bubbling results **up** the tree?

This corresponds to two common tree traversal patterns: **Pre-order** (process node, then recurse) and **Post-order** (recurse, then process node).

---

### Calculations AFTER Recursive Calls (Post-order Processing)

You perform calculations *after* the recursive calls when the calculation for the current node **depends on the results from its children**.

**Intuition:** "I can't decide for myself until I hear back from my children."

You use this pattern when you need to aggregate or synthesize information from the bottom of the tree upwards. The base cases (leaves) provide the initial values, and each parent combines the results from its children to compute its own result.

#### When to use this pattern:

*   When the problem involves calculating a property of a node that is defined by its subtrees.
*   Think of words like "height," "depth," "count," "sum of subtree," "diameter."

#### Examples:

1.  **Diameter of a Binary Tree (Our Problem):**
    *   **Logic:** To find the diameter passing through a node, you *must* know the `height` of its left and right subtrees.
    *   **Code:** You have to call `height(node.left)` and `height(node.right)` *first*. Only after they return can you perform the calculation `maxDiameter = Math.max(maxDiameter, leftHeight + rightHeight)`.

2.  **Find the Height of a Tree:**
    *   **Logic:** A node's height is `1 + max(height of left child, height of right child)`. You need the children's heights before you can determine your own.
    *   **Code:** `int left = height(node.left); int right = height(node.right); return 1 + Math.max(left, right);` The calculation happens *after* the recursive calls.

3.  **Count Nodes in a Tree:**
    *   **Logic:** The total nodes in a subtree are `1 (for the root) + nodes in left subtree + nodes in right subtree`.
    *   **Code:** `return 1 + countNodes(node.left) + countNodes(node.right);` The addition happens *after* the recursive calls return their counts.

---

### Calculations BEFORE Recursive Calls (Pre-order Processing)

You perform calculations *before* the recursive calls when you need to pass information **from a parent down to its children**.

**Intuition:** "I'll process myself first, and then I'll tell my children what they need to know based on my state."

You use this pattern when the state of the current node influences the problem for its descendants. The information flows from the top down.

#### When to use this pattern:

*   When you are searching for a specific path or need to maintain state (like a running sum) as you descend.
*   When you need to check if a node is valid based on constraints imposed by its ancestors.

#### Examples:

1.  **Path Sum (Find if a root-to-leaf path sums to a target):**
    *   **Logic:** At each node, you subtract its value from the target sum. You then ask your children to find the *remaining* sum. The information (the remaining sum) is passed downwards.
    *   **Code:**
        ```java
        public boolean hasPathSum(TreeNode node, int targetSum) {
            if (node == null) return false;
            
            // Calculation before recursion:
            int newTarget = targetSum - node.val; 
            
            if (node.left == null && node.right == null && newTarget == 0) {
                return true; // Leaf node check
            }
            
            // Pass the new, updated information down
            return hasPathSum(node.left, newTarget) || hasPathSum(node.right, newTarget);
        }
        ```

2.  **Validate a Binary Search Tree:**
    *   **Logic:** To validate a node, you need to know the valid range (`min`, `max`) it must fall into, as defined by its ancestors. The root passes constraints down to its children. The left child must be less than the parent; the right child must be greater.
    *   **Code:**
        ```java
        boolean isValidBST(TreeNode node, Integer min, Integer max) {
            if (node == null) return true;

            // Calculation/check before recursion:
            if ((min != null && node.val <= min) || (max != null && node.val >= max)) {
                return false;
            }

            // Pass updated constraints down
            return isValidBST(node.left, min, node.val) && isValidBST(node.right, node.val, max);
        }
        ```

---

### Summary

| Timing                      | Information Flow                | Intuition                                                              | Key Use Cases                                                              |
| --------------------------- | ------------------------------- | ---------------------------------------------------------------------- | -------------------------------------------------------------------------- |
| **After** Recursive Calls   | **Bottom-up** (Post-order)      | "I need results from my children to calculate my own result."          | Height, depth, diameter, counting, summing subtrees.                       |
| **Before** Recursive Calls  | **Top-down** (Pre-order)        | "I'll process myself and pass information down to my children."        | Pathfinding, maintaining running state, validating against ancestor rules.  |