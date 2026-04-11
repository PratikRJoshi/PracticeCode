# LeetCode Problems - By Order of Difficulty

**Session Date:** 2026-04-11  
**Topic:** Tree Problems - Recursive Patterns

---

## Problems Solved (In Order)

### 1. Maximum Depth of Binary Tree (LeetCode 104)
**Difficulty:** Easy  
**Pattern:** Bottom-up recursion  
**Key Concepts:**
- Basic tree recursion structure
- Base case: null node returns 0
- Recursive case: `1 + max(left_depth, right_depth)`
- Time: O(n), Space: O(h) where h = height

**Solution:**
```java
public int maxDepth(TreeNode root) {
    if(root == null) return 0;
    int left = maxDepth(root.left);
    int right = maxDepth(root.right);
    return 1 + Math.max(left, right);
}
```

---

### 2. Minimum Depth of Binary Tree (LeetCode 111)
**Difficulty:** Easy  
**Pattern:** Bottom-up recursion with edge case handling  
**Key Concepts:**
- Must reach a leaf node (both children null)
- Handle single-child case: ignore null side
- Simplified logic: `if(left == 0 || right == 0) return 1 + left + right`
- Time: O(n), Space: O(h)

**Solution:**
```java
public int minDepth(TreeNode root) {
    if(root == null) return 0;
    int left = minDepth(root.left);
    int right = minDepth(root.right);
    
    if(left == 0 || right == 0) {
        return 1 + left + right;
    } else {
        return 1 + Math.min(left, right);
    }
}
```

---

### 3. Diameter of Binary Tree (LeetCode 543)
**Difficulty:** Easy  
**Pattern:** Track global answer, return local contribution  
**Key Concepts:**
- Diameter = longest path between any two nodes
- At each node: `diameter = left_height + right_height`
- Return height to parent: `1 + max(left, right)`
- Use global variable to track max diameter
- Time: O(n), Space: O(h)

**Solution:**
```java
int max = 0;

public int diameterOfBinaryTree(TreeNode root) {
    dfs(root);
    return max;
}

private int dfs(TreeNode node) {
    if(node == null) return 0;
    
    int left = dfs(node.left);
    int right = dfs(node.right);
    
    max = Math.max(max, left + right);  // Update diameter
    
    return 1 + Math.max(left, right);   // Return height
}
```

---

### 4. Binary Tree Maximum Path Sum (LeetCode 124)
**Difficulty:** Hard  
**Pattern:** Track global answer with negative value handling  
**Key Concepts:**
- Path can start/end at any node
- Node values can be negative
- Use `Math.max(0, childResult)` to ignore negative paths
- Track max sum globally
- Initialize max to `Integer.MIN_VALUE` (not 0!)
- Time: O(n), Space: O(h)

**Solution:**
```java
int max = Integer.MIN_VALUE;

public int maxPathSum(TreeNode root) {
    dfs(root);
    return max;
}

private int dfs(TreeNode node) {
    if(node == null) return 0;
    
    int left = Math.max(0, dfs(node.left));   // Ignore negative
    int right = Math.max(0, dfs(node.right)); // Ignore negative
    
    max = Math.max(max, left + right + node.val);  // Path through node
    
    return node.val + Math.max(left, right);       // Max gain to parent
}
```

---

### 5. Validate Binary Search Tree (LeetCode 98)
**Difficulty:** Medium  
**Pattern:** Top-down recursion with constraint passing  
**Key Concepts:**
- Pass min/max bounds down the tree
- Each node must satisfy: `min < node.val < max`
- Left child: `min` stays same, `max` becomes `node.val`
- Right child: `min` becomes `node.val`, `max` stays same
- Use `Long.MIN_VALUE`/`Long.MAX_VALUE` to handle edge cases
- Time: O(n), Space: O(h)

**Solution:**
```java
public boolean isValidBST(TreeNode root) {
    if(root == null) return true;
    return isValidBST(root.left, Long.MIN_VALUE, root.val) 
            && isValidBST(root.right, root.val, Long.MAX_VALUE);
}

private boolean isValidBST(TreeNode root, long min, long max) {
    if(root == null) return true;
    
    return (root.val > min && root.val < max) 
            && isValidBST(root.left, min, root.val) 
            && isValidBST(root.right, root.val, max);
}
```

---

## Key Patterns Learned

### 1. Bottom-Up Recursion
- **Pattern:** Return information to parent
- **Used in:** Max Depth, Min Depth, Diameter, Max Path Sum
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

### 5. Edge Case Handling
- **Null nodes:** Usually return 0 or true (valid empty subtree)
- **Single node:** Count as depth 1, valid BST, etc.
- **Sentinel values:** Use `Long.MIN/MAX_VALUE` when `Integer` range isn't sufficient

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
- Lowest Common Ancestor of a Binary Tree (LeetCode 236) - Combines bottom-up and information passing
- Path Sum variants (LeetCode 112, 113) - Backtracking with trees
- Serialize and Deserialize Binary Tree (LeetCode 297) - Tree traversal and reconstruction

**Related Topics to Explore:**
- Binary Tree Traversals (Inorder, Preorder, Postorder)
- Morris Traversal (O(1) space)
- Level Order Traversal (BFS)
- Tree construction from traversals
