# LeetCode Problems - By Order of Difficulty

**Session Dates:** 2026-04-11, 2026-04-13  
**Topics:** Tree Problems - Recursive Patterns, Graph/Grid DFS

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

### 10. [Serialize and Deserialize Binary Tree](https://leetcode.com/problems/serialize-and-deserialize-binary-tree/)
**Difficulty:** Hard  
**Pattern:** Preorder DFS serialization with mutable list deserialization  
**Key Concepts:**
- Serialize: preorder DFS appending values and "null" markers with a delimiter
- Deserialize: consume tokens from a LinkedList (O(1) remove from front)
- Every token (value or "null") must include the delimiter for consistent splitting
- Remove consumed token before recursing — applies to both null and value cases
- Don't short-circuit serialize for null root — let the recursive helper handle it
- LinkedList chosen over ArrayList for O(1) `remove(0)` (avoids O(n^2))
- Time: O(n), Space: O(n)

---

### 13. [Construct Binary Tree from Inorder and Postorder Traversal](https://leetcode.com/problems/construct-binary-tree-from-inorder-and-postorder-traversal/)
**Difficulty:** Medium  
**Pattern:** Recursive tree construction with divide and conquer (postorder variant)  
**Key Concepts:**
- Mirror of preorder+inorder (problem #9): root is last element in postorder (not first)
- Inorder split logic is identical: find root, everything left is left subtree, right is right subtree
- `inorderLen = rootInInorder - instart` bridges both arrays — same number of nodes in each subtree
- Postorder ranges: left = `[poststart, poststart+inorderLen-1]`, right = `[poststart+inorderLen, postend-1]`
- Build **right subtree before left** (consuming postorder from the back)
- HashMap for O(1) root lookup in inorder array
- Index visualization trick: draw arrays side by side, use inorderLen as a ruler
- Time: O(n), Space: O(n)

---

### 11. [Flatten Binary Tree to Linked List](https://leetcode.com/problems/flatten-binary-tree-to-linked-list/)
**Difficulty:** Medium  
**Pattern:** Iterative in-place tree rewiring (Morris-like)  
**Key Concepts:**
- Flatten to right-skewed chain in preorder order, in-place (void return)
- For each node with a left child: find rightmost of left subtree, attach current right subtree there, move left to right, null out left
- Outer loop: `while(root != null)`, advance `root = root.right`
- Inner loop: walk `temp.right` to find rightmost — each node visited at most twice across entire run
- No recursion needed — pure iterative pointer manipulation
- Time: O(n), Space: O(1)

---

### 12. [Count Good Nodes in Binary Tree](https://leetcode.com/problems/count-good-nodes-in-binary-tree/)
**Difficulty:** Medium  
**Pattern:** Top-down DFS with max tracking (constraint passing)  
**Key Concepts:**
- Pass the max value seen so far on the root-to-node path as a parameter
- A node is "good" if `node.val >= maxSoFar`
- At each node: count = (good ? 1 : 0) + left count + right count
- Update max before recursing: `Math.max(max, node.val)`
- Root is always good (initial max = root.val)
- Time: O(n), Space: O(h)

---

### 14. [Same Tree](https://leetcode.com/problems/same-tree/)
**Difficulty:** Easy  
**Pattern:** Simultaneous tree traversal  
**Key Concepts:**
- Compare two trees node by node recursively
- Both null → true, one null → false, vals differ → false
- Recurse on both left and right pairs
- Time: O(n), Space: O(h)

---

### 15. [Invert Binary Tree](https://leetcode.com/problems/invert-binary-tree/)
**Difficulty:** Easy  
**Pattern:** Top-down recursion with pointer swap  
**Key Concepts:**
- Swap left and right children at each node, then recurse
- Base case: null returns null
- Time: O(n), Space: O(h)

---

### 16. [Balanced Binary Tree](https://leetcode.com/problems/balanced-binary-tree/)
**Difficulty:** Easy  
**Pattern:** Bottom-up recursion with sentinel early termination  
**Key Concepts:**
- Return height if balanced, -1 sentinel if any subtree is unbalanced
- At each node: check `Math.abs(left - right) > 1`
- Propagate -1 upward to short-circuit further computation
- Time: O(n), Space: O(h)

---

### 17. [Lowest Common Ancestor of a BST](https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-search-tree/)
**Difficulty:** Medium  
**Pattern:** BST property-based traversal  
**Key Concepts:**
- Exploit BST ordering: if both p, q < root, go left; both > root, go right
- When p and q split (or one equals root), current node is LCA
- Simpler than general LCA — no need to search both subtrees
- Tail-recursive — easily convertible to iterative O(1) space
- Time: O(h), Space: O(h)

---

### 18. [Subtree of Another Tree](https://leetcode.com/problems/subtree-of-another-tree/)
**Difficulty:** Easy  
**Pattern:** DFS with nested tree comparison  
**Key Concepts:**
- Two DFS functions: one to traverse, one to compare (isSameTree)
- At each node, check if subtree matches; if not, try left OR right (`||` not `&&`)
- Time: O(m*n), Space: O(h)

---

### 19. [Number of Islands](https://leetcode.com/problems/number-of-islands/)
**Difficulty:** Medium  
**Pattern:** Grid DFS with in-place visited marking  
**Key Concepts:**
- First grid DFS problem — 4-directional neighbors instead of tree children
- Sink visited land ('1' → '0') to avoid extra visited set
- For each unvisited '1', run DFS to mark entire island, increment count
- Boundary check: `r < 0 || c < 0 || r >= rows || c >= cols`
- Time: O(m*n), Space: O(m*n) worst case recursion depth

---

### 20. [Max Area of Island](https://leetcode.com/problems/max-area-of-island/)
**Difficulty:** Medium  
**Pattern:** Grid DFS returning area count  
**Key Concepts:**
- Same as Number of Islands, but DFS returns area instead of void
- `return 1 + dfs(up) + dfs(down) + dfs(left) + dfs(right)`
- Track max area across all islands
- Watch for `int` grid vs `char` grid — don't compare against `'0'` (char 48)
- Time: O(m*n), Space: O(m*n)

---

### 21. [Surrounded Regions](https://leetcode.com/problems/surrounded-regions/)
**Difficulty:** Medium  
**Pattern:** Reverse DFS from borders  
**Key Concepts:**
- Insight: instead of finding surrounded regions, find UNsurrounded ones (connected to border)
- DFS from all border 'O's, mark safe cells with temporary sentinel ('S')
- Final sweep: remaining 'O' → 'X' (captured), 'S' → 'O' (restored)
- Watch for diagonal moves — grid DFS is 4-directional only
- Time: O(m*n), Space: O(m*n)

---

### 22. [Pacific Atlantic Water Flow](https://leetcode.com/problems/pacific-atlantic-water-flow/)
**Difficulty:** Medium  
**Pattern:** Dual border DFS with reachability intersection  
**Key Concepts:**
- Two boolean matrices: pacific-reachable and atlantic-reachable
- DFS from Pacific borders (top + left) and Atlantic borders (bottom + right)
- Reverse flow: move to neighbor if `heights[neighbor] >= heights[current]`
- Result: cells where both matrices are true
- Use `dirs` array for cleaner neighbor iteration when height comparison is needed
- Time: O(m*n), Space: O(m*n)

---

### 23. [Clone Graph](https://leetcode.com/problems/clone-graph/)
**Difficulty:** Medium  
**Pattern:** Graph DFS with HashMap for visited tracking and clone mapping  
**Key Concepts:**
- HashMap<Node, Node> maps original → clone (serves as both visited set and clone lookup)
- DFS through original graph; if node already in map, return its clone (cycle breaker)
- Create clone, add to map, then recursively clone each neighbor and add to clone's neighbor list
- Pass original neighbor to recursive call, not map.get(neighbor)
- Using node reference (not int value) as key avoids collisions when nodes share values
- Time: O(V + E), Space: O(V)

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
- **Used in:** Validate BST, Count Good Nodes
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
- **Used in:** Construct Binary Tree from Preorder and Inorder, Serialize and Deserialize, Construct Binary Tree from Inorder and Postorder
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

### 10. In-Place Tree Rewiring (Iterative Pointer Manipulation)
- **Pattern:** Iteratively rewire tree pointers without recursion or extra space
- **Used in:** Flatten Binary Tree to Linked List
- **Structure:**
  ```
  while(current != null):
      if(current.left != null):
          find rightmost of left subtree
          rightmost.right = current.right
          current.right = current.left
          current.left = null
      current = current.right
  ```
- **When to use:** Restructuring a tree in-place with O(1) space, related to Morris Traversal
- **Key insight:** Despite nested loops, O(n) time because each node is visited at most twice total

### 12. Grid DFS (Island Pattern)
- **Pattern:** Iterate grid, DFS from unvisited cells, mark visited in-place
- **Used in:** Number of Islands, Max Area of Island
- **Structure:**
  ```
  for each cell:
      if cell == land:
          dfs(cell)  // mark connected component
          count++
  
  function dfs(r, c):
      if out_of_bounds or not land: return
      mark visited
      dfs(up), dfs(down), dfs(left), dfs(right)
  ```
- **When to use:** Counting/measuring connected components in a grid

### 13. Reverse Border DFS
- **Pattern:** DFS from border cells inward to find reachable regions
- **Used in:** Surrounded Regions, Pacific Atlantic Water Flow
- **Structure:**
  ```
  for each border cell:
      if matches condition:
          dfs(cell)  // mark reachable
  sweep grid to process marked/unmarked cells
  ```
- **When to use:** When the answer depends on connectivity to boundaries

### 15. Graph DFS with Clone Mapping
- **Pattern:** DFS traversal with HashMap mapping original nodes to cloned nodes
- **Used in:** Clone Graph
- **Structure:**
  ```
  map = HashMap<Node, Node>

  function dfs(node, map):
      if node == null: return null
      if map.contains(node): return map.get(node)
      clone = new Node(node.val)
      map.put(node, clone)
      for neighbor in node.neighbors:
          clone.neighbors.add(dfs(neighbor, map))
      return clone
  ```
- **When to use:** Deep copying graph structures where cycles exist; any problem requiring visited tracking + result caching per node

### 14. Edge Case Handling
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

**Upcoming Problems (Graph DFS):**
- Course Schedule (LeetCode 207) - DFS cycle detection in directed graph
- Course Schedule II (LeetCode 210) - Topological sort via DFS
- Word Search (LeetCode 79) - Grid DFS + backtracking
- Longest Increasing Path in a Matrix (LeetCode 329) - Grid DFS + memoization

**Related Topics to Explore:**
- Morris Traversal (O(1) space)
- BFS (Level Order, Rotting Oranges, Word Ladder)
- Dijkstra / Bellman-Ford (Network Delay Time, Swim in Rising Water)

**Patterns Mastered:**
- ✅ Bottom-up recursion (return info to parent)
- ✅ Global answer tracking
- ✅ Top-down constraint passing
- ✅ Handling negative values
- ✅ Early return with split detection
- ✅ Backtracking with path tracking
- ✅ Inorder traversal with early termination
- ✅ Recursive tree construction (divide and conquer)
- ✅ Preorder DFS serialization/deserialization
- ✅ In-place tree rewiring (iterative pointer manipulation)
- ✅ BST property-based traversal
- ✅ Grid DFS (island pattern / in-place marking)
- ✅ Reverse border DFS (reachability from boundaries)
- ✅ Graph DFS with clone mapping (visited + cache via HashMap)
- ✅ Edge case handling
