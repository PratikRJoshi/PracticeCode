# LeetCode Problems - By Order of Difficulty

**Session Dates:** 2026-04-11, 2026-04-13, 2026-04-14, 2026-04-25  
**Topics:** Tree Problems - Recursive Patterns, Graph/Grid DFS, BFS, Dijkstra, Greedy, Two Pointers  
**Total problems tracked here:** 33  
**Total unique problems solved (including pre-tracker sessions):** ~63

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

### 24. [Course Schedule](https://leetcode.com/problems/course-schedule/)
**Difficulty:** Medium  
**Pattern:** DFS cycle detection in directed graph  
**Key Concepts:**
- Build adjacency list from prerequisites
- Three-state visited array: unvisited, in-progress, completed
- Cycle exists if DFS reaches an in-progress node
- Mark node completed after all descendants are processed (no cycle found)
- Time: O(V + E), Space: O(V + E)

---

### 25. [Course Schedule II](https://leetcode.com/problems/course-schedule-ii/)
**Difficulty:** Medium  
**Pattern:** Topological sort via DFS  
**Key Concepts:**
- Same cycle detection as Course Schedule
- Add node to result in post-order (after all dependents processed)
- Reverse the result for correct topological order
- Time: O(V + E), Space: O(V + E)

---

### 26. [Word Search](https://leetcode.com/problems/word-search/)
**Difficulty:** Medium  
**Pattern:** Grid DFS + backtracking  
**Key Concepts:**
- Unlike island DFS, must unmark cells after exploring (backtracking) since a dead-end path may need the cell for a different path
- Mark with sentinel char (`#`), restore original after exploring all 4 directions
- Match characters against word index; return true when `index == word.length()`
- Use `index + 1` not `index++` to avoid mutating the variable across sibling calls
- Time: O(m*n*4^L) where L = word length, Space: O(L) recursion depth

---

### 27. [Longest Increasing Path in a Matrix](https://leetcode.com/problems/longest-increasing-path-in-a-matrix/)
**Difficulty:** Hard  
**Pattern:** Grid DFS + memoization  
**Key Concepts:**
- DFS from every cell, cache result in `memo[i][j]`
- Strictly increasing constraint prevents cycles — no visited set needed
- `memo[i][j]` stores longest path starting from cell `(i,j)`
- Return `1 + dfs(neighbor)` to include current cell in path length
- Bounds-check neighbors BEFORE accessing their values (not after entering DFS)
- Time: O(m*n) — each cell computed once, Space: O(m*n)

---

### 28. [Rotting Oranges](https://leetcode.com/problems/rotting-oranges/)
**Difficulty:** Medium  
**Pattern:** Multi-source BFS (level-by-level)  
**Key Concepts:**
- First BFS problem — processes nodes layer by layer (each layer = one minute)
- Multi-source: enqueue ALL rotten oranges at time 0, not just one
- Track fresh count; decrement when rotting a neighbor
- Off-by-one fix: either use `mins - 1` at end, or add `fresh > 0` to while condition to stop early
- Return -1 if fresh > 0 after BFS (unreachable oranges)
- Time: O(m*n), Space: O(m*n)

---

### 29. [Network Delay Time](https://leetcode.com/problems/network-delay-time/)
**Difficulty:** Medium  
**Pattern:** Dijkstra's shortest path  
**Key Concepts:**
- Weighted directed graph → Dijkstra with min-heap (PriorityQueue)
- Build adjacency list: `Map<Integer, List<int[]>>` with `computeIfAbsent`
- PQ stores `[node, distance]`, sorted by distance
- Skip already-visited nodes with `continue`; visited Set tracks processed nodes
- Last node popped has the max shortest distance — that's the answer
- If `visited.size() < n`, some nodes unreachable → return -1
- Lambda parameter names must not shadow method parameters
- Time: O(E log V), Space: O(V + E)

---

### 30. [Swim in Rising Water](https://leetcode.com/problems/swim-in-rising-water/)
**Difficulty:** Hard  
**Pattern:** Modified Dijkstra on grid (minimize maximum elevation)  
**Key Concepts:**
- PQ stores `[elevation, row, col]`, sorted by elevation
- Key insight: push `Math.max(currentElevation, grid[x][y])` — bottleneck is the tallest cell on path
- Use `boolean[][]` for visited (not `Set<int[]>` — arrays don't override equals/hashCode in Java)
- Return elevation when `(n-1, n-1)` is popped from PQ
- Start with only `(0,0)` in PQ, not all cells
- Time: O(n^2 log n), Space: O(n^2)

---

### 31. [Word Ladder](https://leetcode.com/problems/word-ladder/)
**Difficulty:** Hard  
**Pattern:** BFS on implicit graph (word transformations)  
**Key Concepts:**
- Each word is a node; edges connect words differing by one letter
- BFS guarantees shortest path (uniform cost per step)
- Use `Set<String>` from wordList for O(1) lookup; remove words when enqueued (acts as visited)
- Generate neighbors: `toCharArray()`, swap each position through 26 chars, create new String
- `String.replace()` replaces ALL occurrences — use char array for single-position swap
- Return `len + 1` when endWord found (current level + one more step)
- Early exit: if endWord not in wordSet, return 0
- Time: O(M^2 * N) where M = word length, N = wordList size, Space: O(M * N)

### 32. [Valid Parenthesis String](https://leetcode.com/problems/valid-parenthesis-string/)
**Difficulty:** Medium  
**Pattern:** Greedy range tracking (two-counter sweep)  
**Key Concepts:**
- Track `minOpen` and `maxOpen` — the range of possible unmatched `(` counts across all `*` interpretations
- `(` → both counters +1; `)` → both counters -1; `*` → `minOpen` -1, `maxOpen` +1 (most pessimistic/optimistic for each bound)
- Hard-invalid early exit: if `maxOpen < 0`, no future char can rescue us — return false
- Floor `minOpen` at 0 (a negative "unmatched open" has no real meaning — reinterpret earlier `*` as empty instead)
- Final check: `minOpen == 0` (there exists *some* interpretation that balances; `maxOpen == 0` would be too strict)
- Avoids O(n^2) DP by collapsing all valid interpretations into a single range
- Time: O(n), Space: O(1)

### 33. [Container With Most Water](https://leetcode.com/problems/container-with-most-water/)
**Difficulty:** Medium  
**Pattern:** Two pointers (opposite ends) with greedy shrinking  
**Key Concepts:**
- Start pointers at both ends (`left = 0`, `right = n-1`) — this is the maximum possible width
- Area = `min(height[left], height[right]) * (right - left)`; width strictly decreases on every move
- Greedy dominance argument: move the pointer at the **shorter** line inward — the longer one can never improve the area while paired with the shorter
  - Width shrinks, and limiting height is capped at the shorter line no matter what we land on
  - Therefore all pairs involving the shorter line are dominated and can be discarded
- On equal heights, moving either pointer is safe (both sides are simultaneously dominated)
- Time: O(n), Space: O(1)

---

## Key Patterns Learned

### 1. Bottom-Up Recursion
- **Pattern:** Return information to parent
- **Used in:** Max Depth, Min Depth, Diameter, Max Path Sum, LCA
- **Structure:** 
  ```java
  if (node == null) return baseValue;
  int left = recurse(node.left);
  int right = recurse(node.right);
  return compute(left, right, node.val);
  ```

### 2. Global Answer Tracking
- **Pattern:** Track best answer globally while returning different value
- **Used in:** Diameter, Max Path Sum
- **Structure:**
  ```java
  int globalAnswer = initialValue;

  private int dfs(TreeNode node) {
      int localAnswer = compute();
      globalAnswer = Math.max(globalAnswer, localAnswer);
      return valueForParent;
  }
  ```

### 3. Top-Down Constraint Passing
- **Pattern:** Pass constraints/bounds down the tree
- **Used in:** Validate BST, Count Good Nodes
- **Structure:**
  ```java
  private boolean validate(TreeNode node, long min, long max) {
      if (node == null) return true;
      if (node.val <= min || node.val >= max) return false;
      return validate(node.left, min, node.val)
          && validate(node.right, node.val, max);
  }
  ```

### 4. Handling Negative Values
- **Technique:** Use `Math.max(0, result)` to ignore negative contributions
- **Used in:** Max Path Sum
- **When to use:** When you want to optionally exclude a subtree if it hurts the answer

### 5. Early Return with Split Detection
- **Pattern:** Return immediately when condition is met, then analyze subtree results
- **Used in:** LCA
- **Structure:**
  ```java
  if (node == p || node == q) return node;
  TreeNode left = recurse(node.left);
  TreeNode right = recurse(node.right);
  if (left != null && right != null) return node;
  return left != null ? left : right;
  ```
- **When to use:** When finding a node that splits two targets, or when the current node itself is a target

### 6. Backtracking with Path Tracking
- **Pattern:** Build path incrementally, explore, then undo (backtrack)
- **Used in:** Path Sum II
- **Structure:**
  ```java
  private void dfs(TreeNode node, List<Integer> path, List<List<Integer>> result) {
      if (node == null) return;
      path.add(node.val);
      if (isValidEndpoint) {
          result.add(new ArrayList<>(path));
      }
      dfs(node.left, path, result);
      dfs(node.right, path, result);
      path.remove(path.size() - 1);
  }
  ```
- **When to use:** Finding all paths/combinations in trees, need to track current state

### 7. Inorder Traversal with Early Termination
- **Pattern:** Use inorder DFS (left, process, right) to visit BST nodes in sorted order, stop early when target is found
- **Used in:** Kth Smallest Element in BST
- **Structure:**
  ```java
  private void dfs(TreeNode node) {
      if (node == null || k == 0) return;
      dfs(node.left);
      k--;
      if (k == 0) {
          result = node.val;
          return;
      }
      dfs(node.right);
  }
  ```
- **When to use:** When you need sorted-order access to BST nodes, or need the kth element

### 8. Recursive Tree Construction (Divide and Conquer)
- **Pattern:** Use traversal properties to identify root, split into subtrees, recurse
- **Used in:** Construct Binary Tree from Preorder and Inorder, Serialize and Deserialize, Construct Binary Tree from Inorder and Postorder
- **Structure:**
  ```java
  private TreeNode build(int[] preorder, int preStart, int preEnd,
                         int inStart, int inEnd, Map<Integer, Integer> map) {
      if (preStart > preEnd) return null;
      TreeNode root = new TreeNode(preorder[preStart]);
      int rootIndex = map.get(root.val);
      int leftSize = rootIndex - inStart;
      root.left = build(preorder, preStart + 1, preStart + leftSize, inStart, rootIndex - 1, map);
      root.right = build(preorder, preStart + leftSize + 1, preEnd, rootIndex + 1, inEnd, map);
      return root;
  }
  ```
- **When to use:** Reconstructing a tree from two traversal orderings

### 10. In-Place Tree Rewiring (Iterative Pointer Manipulation)
- **Pattern:** Iteratively rewire tree pointers without recursion or extra space
- **Used in:** Flatten Binary Tree to Linked List
- **Structure:**
  ```java
  while (current != null) {
      if (current.left != null) {
          TreeNode rightmost = current.left;
          while (rightmost.right != null) rightmost = rightmost.right;
          rightmost.right = current.right;
          current.right = current.left;
          current.left = null;
      }
      current = current.right;
  }
  ```
- **When to use:** Restructuring a tree in-place with O(1) space, related to Morris Traversal
- **Key insight:** Despite nested loops, O(n) time because each node is visited at most twice total

### 12. Grid DFS (Island Pattern)
- **Pattern:** Iterate grid, DFS from unvisited cells, mark visited in-place
- **Used in:** Number of Islands, Max Area of Island
- **Structure:**
  ```java
  for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
          if (grid[i][j] == '1') {
              dfs(grid, i, j);
              count++;
          }
      }
  }

  private void dfs(char[][] grid, int r, int c) {
      if (r < 0 || c < 0 || r >= grid.length || c >= grid[0].length || grid[r][c] != '1') return;
      grid[r][c] = '0';
      dfs(grid, r + 1, c); dfs(grid, r - 1, c);
      dfs(grid, r, c + 1); dfs(grid, r, c - 1);
  }
  ```
- **When to use:** Counting/measuring connected components in a grid

### 13. Reverse Border DFS
- **Pattern:** DFS from border cells inward to find reachable regions
- **Used in:** Surrounded Regions, Pacific Atlantic Water Flow
- **Structure:**
  ```java
  for (int i = 0; i < rows; i++) {
      dfs(board, i, 0);
      dfs(board, i, cols - 1);
  }
  for (int j = 0; j < cols; j++) {
      dfs(board, 0, j);
      dfs(board, rows - 1, j);
  }
  // sweep: remaining 'O' → 'X', sentinel → 'O'
  ```
- **When to use:** When the answer depends on connectivity to boundaries

### 15. Graph DFS with Clone Mapping
- **Pattern:** DFS traversal with HashMap mapping original nodes to cloned nodes
- **Used in:** Clone Graph
- **Structure:**
  ```java
  Map<Node, Node> map = new HashMap<>();

  private Node dfs(Node node) {
      if (node == null) return null;
      if (map.containsKey(node)) return map.get(node);
      Node clone = new Node(node.val);
      map.put(node, clone);
      for (Node neighbor : node.neighbors) {
          clone.neighbors.add(dfs(neighbor));
      }
      return clone;
  }
  ```
- **When to use:** Deep copying graph structures where cycles exist; any problem requiring visited tracking + result caching per node

### 14. Grid DFS + Backtracking
- **Pattern:** Mark cell visited, explore, unmark after exploring
- **Used in:** Word Search
- **Structure:**
  ```java
  private boolean dfs(char[][] grid, int r, int c, String word, int index) {
      if (index == word.length()) return true;
      if (r < 0 || c < 0 || r >= grid.length || c >= grid[0].length
          || grid[r][c] != word.charAt(index)) return false;
      char original = grid[r][c];
      grid[r][c] = '#';
      boolean found = dfs(grid, r + 1, c, word, index + 1) || dfs(grid, r - 1, c, word, index + 1)
                   || dfs(grid, r, c + 1, word, index + 1) || dfs(grid, r, c - 1, word, index + 1);
      grid[r][c] = original;
      return found;
  }
  ```
- **When to use:** Finding paths in grids where cells can be reused across different search paths but not within the same path

### 15. Grid DFS + Memoization
- **Pattern:** DFS from every cell with cached results
- **Used in:** Longest Increasing Path in a Matrix
- **Structure:**
  ```java
  private int dfs(int[][] matrix, int r, int c, Integer[][] memo) {
      if (memo[r][c] != null) return memo[r][c];
      int len = 1;
      for (int[] dir : dirs) {
          int x = r + dir[0], y = c + dir[1];
          if (x >= 0 && y >= 0 && x < matrix.length && y < matrix[0].length
              && matrix[x][y] > matrix[r][c]) {
              len = Math.max(len, 1 + dfs(matrix, x, y, memo));
          }
      }
      memo[r][c] = len;
      return len;
  }
  ```
- **When to use:** Optimization problems on grids where subproblems overlap and a monotonic constraint prevents cycles

### 16. Multi-Source BFS
- **Pattern:** Enqueue all sources at once, process level by level
- **Used in:** Rotting Oranges
- **Structure:**
  ```java
  Queue<int[]> queue = new LinkedList<>();
  // add all initial sources to queue
  while (!queue.isEmpty() && fresh > 0) {
      int size = queue.size();
      for (int i = 0; i < size; i++) {
          int[] cell = queue.poll();
          for (int[] dir : dirs) {
              int x = cell[0] + dir[0], y = cell[1] + dir[1];
              if (valid && grid[x][y] == 1) { grid[x][y] = 2; queue.offer(new int[]{x, y}); fresh--; }
          }
      }
      level++;
  }
  ```
- **When to use:** Simultaneous spreading/expansion from multiple starting points; shortest distance from any source

### 17. DFS Cycle Detection (Directed Graph)
- **Pattern:** Three-state coloring (unvisited / in-progress / completed)
- **Used in:** Course Schedule, Course Schedule II
- **Structure:**
  ```java
  private boolean dfs(int node, List<List<Integer>> graph, int[] state) {
      if (state[node] == 1) return false; // in-progress → cycle
      if (state[node] == 2) return true;  // completed
      state[node] = 1;
      for (int neighbor : graph.get(node)) {
          if (!dfs(neighbor, graph, state)) return false;
      }
      state[node] = 2;
      return true;
  }
  ```
- **When to use:** Detecting cycles in directed graphs, topological ordering

### 18. Dijkstra's Algorithm
- **Pattern:** Greedy shortest path using min-heap
- **Used in:** Network Delay Time
- **Structure:**
  ```java
  PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[1] - b[1]);
  pq.offer(new int[]{source, 0});
  Set<Integer> visited = new HashSet<>();

  while (!pq.isEmpty()) {
      int[] curr = pq.poll();
      int node = curr[0], dist = curr[1];
      if (visited.contains(node)) continue;
      visited.add(node);
      for (int[] edge : graph.getOrDefault(node, List.of())) {
          if (!visited.contains(edge[0]))
              pq.offer(new int[]{edge[0], dist + edge[1]});
      }
  }
  ```
- **When to use:** Shortest paths in weighted graphs with non-negative edges

### 19. Modified Dijkstra (Minimax Path)
- **Pattern:** Dijkstra but tracking max along path instead of sum
- **Used in:** Swim in Rising Water
- **Key difference:** Push `Math.max(currentCost, neighborCost)` instead of `currentCost + edgeWeight`
- **When to use:** Finding path that minimizes the maximum edge/node weight (bottleneck path)

### 20. BFS on Implicit Graph
- **Pattern:** BFS where neighbors are generated on-the-fly, not stored in adjacency list
- **Used in:** Word Ladder
- **Structure:**
  ```java
  Queue<String> queue = new LinkedList<>();
  queue.offer(startState);
  Set<String> visited = new HashSet<>();
  visited.add(startState);
  int level = 1;

  while (!queue.isEmpty()) {
      int size = queue.size();
      for (int i = 0; i < size; i++) {
          String word = queue.poll();
          // generate all valid neighbors (swap each char position through 'a'-'z')
          if (neighbor.equals(target)) return level + 1;
          if (visited.add(neighbor)) queue.offer(neighbor);
      }
      level++;
  }
  ```
- **When to use:** State-space search where graph is too large to precompute (word transformations, puzzle states)

### 21. Edge Case Handling
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

**Sections fully completed (see leetcode-patterns-grouping.md):**
- ✅ Two Pointers (5/5)
- ✅ Stack (6/6)
- ✅ Hash Map / Set (10/10)
- ✅ Binary Tree (15/15)
- ✅ BFS / DFS (13/13)
- ✅ Monotonic Stack (3/3)
- ✅ Topological Sort (2/3, remaining is premium)

**Sections partially completed:**
- Backtracking (11/15 — remaining: Valid Sudoku, N-Queens, Word Search II)
- Sliding Window (3/5 — remaining: Minimum Window Substring, Sliding Window Maximum)
- Heap / PQ (6/7 — remaining: Top K Frequent Elements revisit)

**Sections not started (next up):**
- Binary Search (0/7) — Search in Rotated, Koko Eating Bananas, etc.
- Linked List (1/9) — Reverse Linked List, Merge Two Sorted, LRU Cache, etc.
- Dynamic Programming (0/23) — Climbing Stairs → House Robber → Coin Change
- Trie (0/4)
- Greedy (0/14)

**Recommended next section:** Binary Search or Linked List (both are foundational patterns needed before DP)

**Related Topics to Explore:**
- Morris Traversal (O(1) space)
- Bellman-Ford (negative weight edges)
- A* search

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
- ✅ Grid DFS + backtracking (mark/unmark for path search)
- ✅ Grid DFS + memoization (cache subproblem results)
- ✅ Multi-source BFS (level-by-level expansion)
- ✅ DFS cycle detection in directed graphs (three-state coloring)
- ✅ Topological sort via DFS
- ✅ Dijkstra's algorithm (weighted shortest paths)
- ✅ Modified Dijkstra / minimax path (minimize bottleneck)
- ✅ BFS on implicit graph (state-space search)
- ✅ Edge case handling
- ✅ Greedy range tracking (two-counter sweep for `*` wildcard)
- ✅ Two pointers (opposite ends) with greedy dominance shrinking
