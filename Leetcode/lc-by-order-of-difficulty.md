# LeetCode Problems - By Order of Difficulty

**Session Date:** 2026-04-11  
**Topic:** Tree Problems - Recursive Patterns

---

## Problems Solved (In Order)

### 1. [Maximum Depth of Binary Tree](https://leetcode.com/problems/maximum-depth-of-binary-tree/)
**Difficulty:** Easy  
**Pattern:** Bottom-up recursion  
**Key Concepts:**
- Basic tree recursion structure
- Base case: null node returns 0
- Recursive case: `1 + max(left_depth, right_depth)`
- Time: O(n), Space: O(h) where h = height

---

### 2. [Minimum Depth of Binary Tree](https://leetcode.com/problems/minimum-depth-of-binary-tree/)
**Difficulty:** Easy  
**Pattern:** Bottom-up recursion with edge case handling  
**Key Concepts:**
- Must reach a leaf node (both children null)
- Handle single-child case: ignore null side
- Simplified logic: `if(left == 0 || right == 0) return 1 + left + right`
- Time: O(n), Space: O(h)

---

### 3. [Diameter of Binary Tree](https://leetcode.com/problems/diameter-of-binary-tree/)
**Difficulty:** Easy  
**Pattern:** Track global answer, return local contribution  
**Key Concepts:**
- Diameter = longest path between any two nodes
- At each node: `diameter = left_height + right_height`
- Return height to parent: `1 + max(left, right)`
- Use global variable to track max diameter
- Time: O(n), Space: O(h)

---

### 4. [Binary Tree Maximum Path Sum](https://leetcode.com/problems/binary-tree-maximum-path-sum/)
**Difficulty:** Hard  
**Pattern:** Track global answer with negative value handling  
**Key Concepts:**
- Path can start/end at any node
- Node values can be negative
- Use `Math.max(0, childResult)` to ignore negative paths
- Track max sum globally
- Initialize max to `Integer.MIN_VALUE` (not 0!)
- Time: O(n), Space: O(h)

---

### 5. [Validate Binary Search Tree](https://leetcode.com/problems/validate-binary-search-tree/)
**Difficulty:** Medium  
**Pattern:** Top-down recursion with constraint passing  
**Key Concepts:**
- Pass min/max bounds down the tree
- Each node must satisfy: `min < node.val < max`
- Left child: `min` stays same, `max` becomes `node.val`
- Right child: `min` becomes `node.val`, `max` stays same
- Use `Long.MIN_VALUE`/`Long.MAX_VALUE` to handle edge cases
- Time: O(n), Space: O(h)

---

### 6. [Lowest Common Ancestor of a Binary Tree](https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree/)
**Difficulty:** Medium  
**Pattern:** Bottom-up recursion with early returns and split detection  
**Key Concepts:**
- Return node if current node is p or q (early return)
- If both left and right return non-null → current node is LCA (split point)
- If only one side returns non-null → that subtree contains both nodes
- Node can be its own ancestor (if p is ancestor of q, return p)
- Time: O(n), Space: O(h)

---

### 7. [Path Sum II](https://leetcode.com/problems/path-sum-ii/)
**Difficulty:** Medium  
**Pattern:** Backtracking with path tracking  
**Key Concepts:**
- Track current path during recursion (add node before recursing)
- Backtrack by removing last node after exploring children
- Clone path list when adding to result (avoid reference issues)
- Valid path: root to leaf where sum equals target
- Time: O(n), Space: O(h) where h = height

---

### 8. [Kth Smallest Element in a BST](https://leetcode.com/problems/kth-smallest-element-in-a-bst/)
**Difficulty:** Medium  
**Pattern:** Inorder DFS traversal with early termination  
**Key Concepts:**
- Inorder traversal of BST visits nodes in ascending sorted order
- Use instance variables to share countdown (`level`) and result across recursive calls
- Early termination: stop recursing once kth node is found (`level == 0`)
- Java primitives are pass-by-value — use instance variables, not parameters, for shared state
- Time: O(n), Space: O(n) worst case; O(h + k) time, O(h) space for balanced trees

---

### 9. [Construct Binary Tree from Preorder and Inorder Traversal](https://leetcode.com/problems/construct-binary-tree-from-preorder-and-inorder-traversal/)
**Difficulty:** Medium  
**Pattern:** Recursive tree construction with divide and conquer  
**Key Concepts:**
- Preorder's first element is always the root
- Find root in inorder array to split into left/right subtrees
- Left subtree size (`rootInInorder - inStart`) maps inorder indices to preorder indices
- Use HashMap for O(1) root lookup in inorder array (avoid O(n^2) linear scan)
- Time: O(n), Space: O(n)

---

## Key Patterns Learned

### 1. Bottom-Up Recursion
- **Pattern:** Return information to parent
- **Used in:** Max Depth, Min Depth, Diameter, Max Path Sum, LCA
- **Structure:** 
  ```
  if(node == null) return base_value;
  left_result = recurse(left);
  right_result = recurse(right);
  return compute(left_result, right_result, node.val);
  ```

### 2. Global Answer Tracking
- **Pattern:** Track best answer globally while returning different value
- **Used in:** Diameter, Max Path Sum
- **Structure:**
  ```
  global_answer = initial_value;
  
  function dfs(node):
      compute local answer
      update global_answer
      return value_for_parent (different from local answer)
  ```

### 3. Top-Down Constraint Passing
- **Pattern:** Pass constraints/bounds down the tree
- **Used in:** Validate BST
- **Structure:**
  ```
  function validate(node, constraint1, constraint2):
      if(node == null) return base_case;
      if(!satisfies_constraints) return false;
      return validate(left, updated_constraint1, updated_constraint2)
             && validate(right, updated_constraint1, updated_constraint2);
  ```

### 4. Handling Negative Values
- **Technique:** Use `Math.max(0, result)` to ignore negative contributions
- **Used in:** Max Path Sum
- **When to use:** When you want to optionally exclude a subtree if it hurts the answer

### 5. Early Return with Split Detection
- **Pattern:** Return immediately when condition is met, then analyze subtree results
- **Used in:** LCA
- **Structure:**
  ```
  if(node matches condition) return node;  // Early return
  left = recurse(left);
  right = recurse(right);
  if(left != null && right != null) return node;  // Split detected
  return non-null side;
  ```
- **When to use:** When finding a node that splits two targets, or when the current node itself is a target

### 6. Backtracking with Path Tracking
- **Pattern:** Build path incrementally, explore, then undo (backtrack)
- **Used in:** Path Sum II
- **Structure:**
  ```
  function dfs(node, path, result):
      if(node == null) return;
      path.add(node.val);              // Add to path
      if(is_valid_endpoint):
          result.add(clone(path));      // Clone before adding!
      dfs(left, path, result);
      dfs(right, path, result);
      path.remove(last);                // Backtrack
  ```
- **When to use:** Finding all paths/combinations in trees, need to track current state

### 7. Inorder Traversal with Early Termination
- **Pattern:** Use inorder DFS (left, process, right) to visit BST nodes in sorted order, stop early when target is found
- **Used in:** Kth Smallest Element in BST
- **Structure:**
  ```
  function dfs(node):
      if(node == null || found) return;
      dfs(left);
      countdown--;
      if(countdown == 0):
          result = node.val;
          return;
      dfs(right);
  ```
- **When to use:** When you need sorted-order access to BST nodes, or need the kth element

### 8. Recursive Tree Construction (Divide and Conquer)
- **Pattern:** Use traversal properties to identify root, split into subtrees, recurse
- **Used in:** Construct Binary Tree from Preorder and Inorder
- **Structure:**
  ```
  function build(preorder, preStart, preEnd, inorder, inStart, inEnd, map):
      if(preStart > preEnd) return null;
      root = new Node(preorder[preStart]);
      rootIndex = map.get(root.val);
      leftSize = rootIndex - inStart;
      root.left = build(preStart+1, preStart+leftSize, inStart, rootIndex-1);
      root.right = build(preStart+leftSize+1, preEnd, rootIndex+1, inEnd);
      return root;
  ```
- **When to use:** Reconstructing a tree from two traversal orderings

### 9. Edge Case Handling
- **Null nodes:** Usually return 0 or true (valid empty subtree)
- **Single node:** Count as depth 1, valid BST, etc.
- **Sentinel values:** Use `Long.MIN/MAX_VALUE` when `Integer` range isn't sufficient
- **Node as own ancestor:** A node can be ancestor of itself (important for LCA)
- **Cloning paths:** Always clone when adding to result to avoid reference issues

---

## Complexity Analysis Summary

All problems follow similar complexity patterns for tree recursion:

**Time Complexity:** O(n)
- Visit each node exactly once
- Constant work per node

**Space Complexity:** O(h) where h = height of tree
- Recursion stack space
- Best case (balanced tree): O(log n)
- Worst case (skewed tree): O(n)

---

## Next Steps

**Upcoming Problems:**
- Serialize and Deserialize Binary Tree (LeetCode 297) - Tree serialization and reconstruction
- Flatten Binary Tree to Linked List (LeetCode 114) - In-place tree modification
- Binary Tree Right Side View (LeetCode 199) - Level order traversal variant
- Construct Binary Tree from Inorder and Postorder Traversal (LeetCode 106) - Variant of tree construction

**Related Topics to Explore:**
- Morris Traversal (O(1) space)
- Level Order Traversal (BFS)
- In-place tree modification
- Tree serialization/deserialization

**Patterns Mastered:**
- ✅ Bottom-up recursion (return info to parent)
- ✅ Global answer tracking
- ✅ Top-down constraint passing
- ✅ Handling negative values
- ✅ Early return with split detection
- ✅ Backtracking with path tracking
- ✅ Inorder traversal with early termination
- ✅ Recursive tree construction (divide and conquer)
- ✅ Edge case handling
