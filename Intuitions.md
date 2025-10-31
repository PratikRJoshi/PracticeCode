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

### When to Use a Helper Function in Tree Problems

The decision to use a helper function usually comes down to one key question:

**"Does the signature of the function I need for my recursion match the signature of the main function given by the problem?"**

The "signature" includes:
1.  **The parameters it accepts.**
2.  **The type of value it returns.**

---

#### When to Use a Helper Function

You should create a helper function when the recursive part of your algorithm needs a **different signature** or requires **setup/cleanup** that the main function will handle.

**1. You Need to Pass Extra Parameters (State) Down the Tree**

This is the most common reason. The recursion needs to keep track of information that wasn't provided in the original function call.

*   **Pattern:** The main function calls the helper with initial state values. The helper then calls itself with updated state.
*   **Example: [LeetCode 98. Validate a Binary Search Tree](https://leetcode.com/problems/validate-a-binary-search-tree/)**
    *   **Main Function Signature:** `public boolean isValidBST(TreeNode root)`
    *   **The Problem:** To validate a node, you need to know the valid range (`min`, `max`) it must be in, which is determined by its ancestors. The original signature doesn't have a place for `min` and `max`.
    *   **Helper Function Signature:** `private boolean isValid(TreeNode node, Integer min, Integer max)`
    *   **Usage:**
        ```java
        public boolean isValidBST(TreeNode root) {
            // Initial call with no constraints
            return isValid(root, null, null); 
        }
        
        private boolean isValid(TreeNode node, Integer min, Integer max) {
            // Recursive calls pass updated constraints
            // isValid(node.left, min, node.val)
        }
        ```

**2. The Recursive Function Needs a Different Return Type**

The value your recursion needs to "bubble up" is different from the final answer's type.

*   **Pattern:** The main function calls the helper, interprets its return value, and then returns the final answer in the correct type (`boolean`, `int`, etc.).
*   **Example: [LeetCode 110. Balanced Binary Tree](https://leetcode.com/problems/balanced-binary-tree/)**
    *   **Main Function Signature:** `public boolean isBalanced(TreeNode root)` (must return a boolean).
    *   **The Problem:** The most efficient way to solve this is with a function that calculates a subtree's height but also signals imbalance. Returning an `int` (height) or a flag (`-1`) is cleaner than returning a complex object.
    *   **Helper Function Signature:** `private int checkHeight(TreeNode node)`
    *   **Usage:**
        ```java
        public boolean isBalanced(TreeNode root) {
            // Interpret the helper's int result to return a boolean
            return checkHeight(root) != -1; 
        }
        
        private int checkHeight(TreeNode node) {
            // Returns height (an int) or -1 (an int)
        }
        ```

**3. You Need to Do Setup or Teardown (Using Global State)**

The main function serves as an entry point to initialize state, trigger the recursion, and then return the final result.

*   **Pattern:** The main function initializes a class member variable, calls the helper, and then returns the variable's final value.
*   **Example: [LeetCode 543. Diameter of Binary Tree](https://leetcode.com/problems/diameter-of-binary-tree/)**
    *   **Main Function Signature:** `public int diameterOfBinaryTree(TreeNode root)`
    *   **The Problem:** We need a variable `maxDiameter` to be accessible across all recursive calls.
    *   **Helper Function Signature:** `private int height(TreeNode node)`
    *   **Usage:**
        ```java
        private int maxDiameter = 0; // Setup: Initialize state

        public int diameterOfBinaryTree(TreeNode root) {
            height(root); // Trigger recursion
            return maxDiameter; // Teardown: Return final state
        }
        
        private int height(TreeNode node) {
            // This helper's job is to calculate height, but it
            // modifies maxDiameter as a side effect.
        }
        ```

---

#### When the Given Function is Sufficient

You can just call the main function recursively if its signature is **perfectly suited** for the recursive calls. This means the parameters and return type are exactly what you need for the subproblems.

*   **Pattern:** The function calls itself on subproblems (`node.left`, `node.right`) with modified parameters that fit the existing signature.

**Examples:**

1.  **[LeetCode 112. Path Sum](https://leetcode.com/problems/path-sum/)**
    *   **Signature:** `public boolean hasPathSum(TreeNode root, int targetSum)`
    *   **Why it works:** The recursive call needs a `node` and the `remainingSum`. This perfectly matches the function's signature. We can just call `hasPathSum(node.left, newTargetSum)`. No extra parameters or different return types are needed.

2.  **[LeetCode 226. Invert Binary Tree](https://leetcode.com/problems/invert-binary-tree/)**
    *   **Signature:** `public TreeNode invertTree(TreeNode root)`
    *   **Why it works:** The function needs to operate on a node and return the modified node. The recursive calls, `invertTree(root.left)` and `invertTree(root.right)`, fit this signature perfectly.

### Summary Table

| Condition                                                              | Decision                                         | Rationale                                                                        |
| ---------------------------------------------------------------------- | ------------------------------------------------ | -------------------------------------------------------------------------------- |
| Recursion needs extra parameters (e.g., `min`, `max` bounds).          | **Use a helper function.**                       | The main function's signature is insufficient.                                   |
| Recursion's natural return value is a different type (e.g., `int` vs `boolean`). | **Use a helper function.**                       | The main function's job is to translate the helper's result into the final type. |
| You need to initialize a "global" or member variable before recursion. | **Use a helper function.**                       | The main function acts as a setup/driver for the recursive worker.                |
| The recursive subproblem fits the main function's signature exactly.   | **Call the main function recursively.**          | The signature is self-sufficient. No need for an extra layer.                    |

---

## Monotonic Stack “Waiting Room” Intuition

A monotonic stack is a **waiting room** of indices still looking for the first element to their right that satisfies some comparison (typically `>` or `<`).  The canonical pop loop:

```java
while (!stack.isEmpty() && currentValue COMPARE arr[stack.peek()]) {
    int idx = stack.pop();        // idx just found its answer: currentValue
    // record answer for idx here
}
stack.push(currentIndex);         // current now waits for its own answer
```

Key points
1. **Invariant** – the stack is kept in sorted order (decreasing for “next greater”, increasing for “next smaller”).  This guarantees that once an element is popped it will never need to be considered again.
2. **Amortised O(1)** – each index is pushed once and popped once, so the whole scan is linear.
3. **Analogy** – people queue by height.  Each newcomer either waits (push) or kicks out shorter people ahead (pop) because they found their taller person.
4. **Flexibility** – swap `>`/`<`, allow `=` on one side, or iterate `2·n` for circular arrays.  The skeleton stays identical.

Pattern table
| Problem flavour | Comparison | Invariant |
|-----------------|------------|-----------|
| Next **greater** element | `curr  >  arr[top]` | decreasing stack |
| Next **greater or equal** | `curr >= arr[top]` | *strictly* decreasing |
| Next **smaller** element  | `curr  <  arr[top]` | increasing stack |
| Circular array            | same compare, loop `2n`, push only first `n` |

The loop is the moment “waiting ends” for zero or more elements; everything else in the algorithm is just how you **record** that discovery (map assignment, span counting, water trapped…).


---

### A Guide to Pointers in Linked Lists

Here are the key principles for correctly positioning `slow` and `fast` pointers and handling null checks in linked list problems.

#### 1. The Dummy Node: Your Best Friend

- **What it is**: A `ListNode dummy = new ListNode(0); dummy.next = head;`. It's a placeholder node that sits *before* the actual head of your list.
- **Why it's essential**:
    - **Uniformity**: It turns all operations into "middle-of-the-list" operations. Removing the head is the same as removing any other node; you're always modifying a `prev.next` pointer.
    - **No Special Head Checks**: Without a dummy, code like `slow.next = slow.next.next` fails if you need to remove the head. You'd need an `if (head needs removal)` block. The dummy node eliminates this.
- **Rule of Thumb**: If you are modifying a linked list (deleting, inserting), start with a dummy node.

#### 2. Positioning `slow` and `fast` Pointers

The goal is to establish a "window" or "gap" between `slow` and `fast` that helps you find a specific node. The key is to determine where `slow` should be when `fast` hits the end.

- **Scenario A: `slow` needs to be *at* the target node.**
    - **Example**: Find the middle of the list.
    - **Logic**: When `fast` reaches the end, `slow` should be in the middle. This means `fast` must travel roughly twice as fast.
    - **Code**: `while (fast != null && fast.next != null) { slow = slow.next; fast = fast.next.next; }`
    - **Null Check**: `fast != null && fast.next != null` is the standard for a 2x speed `fast` pointer. It handles both even and odd length lists gracefully.

- **Scenario B: `slow` needs to be *before* the target node.**
    - **Example**: Remove Nth node from the end.
    - **Logic**: We need `slow` to point to the predecessor of the node to be deleted. To achieve this, we create a gap of `n` nodes between `slow` and `fast`. By advancing `fast` one extra step initially (a gap of `n+1` from the start), `slow` naturally ends up one step behind the target.
    - **Code**:
        1. `for (int i = 0; i <= n; i++) { fast = fast.next; }` (Creates the gap)
        2. `while (fast != null) { slow = slow.next; fast = fast.next; }` (Moves them together)

#### 3. Null Checks: The Defensive Playbook

Null checks prevent `NullPointerException`. The pointer you are about to access `.next` on is the one you must check.

- **`while (current != null)`**: Use this for a simple traversal where you need to visit every node.
  ```java
  ListNode current = head;
  while (current != null) {
      // Process current.val
      current = current.next;
  }

### When to Use +1 in Memoization Array Size

Use `+1` when:

1. You need to store results for value 0
2. Your problem uses 1-based indexing
3. You need to access dp[1], dp[2] directly

Don't use +1 when:
- Using 0-based indexing
- Shifting indices in your logic
- Not including 0 as valid input

---

### When to Use Helper Functions in Recursion

Use a helper function when:

1. You need extra parameters not in original signature
2. You need a different return type for recursion
3. You need to initialize state before recursion
4. You want to hide implementation details

Don't use a helper when:
- The function signature works perfectly for recursion
- No additional state or different return types needed
- A helper would only add unnecessary complexity
