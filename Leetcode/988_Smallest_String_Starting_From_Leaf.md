# 988. Smallest String Starting From Leaf

[LeetCode Link](https://leetcode.com/problems/smallest-string-starting-from-leaf/)

## Problem Description
You are given the `root` of a binary tree where each node has a value from `0` to `25` representing the letters `'a'` to `'z'`.

Return the lexicographically smallest string that starts at a leaf of this tree and ends at the root.

A string `s` is lexicographically smaller than a string `t` if at the first position where they differ, the character in `s` comes before the character in `t` in the alphabet.

### Examples

#### Example 1
- Input: `root = [0,1,2,3,4,3,4]`
- Output: `"dba"`

#### Example 2
- Input: `root = [25,1,3,1,3,0,2]`
- Output: `"adz"`

#### Example 3
- Input: `root = [2,2,1,null,1,0,null,0]`
- Output: `"abc"`

---

## Intuition/Main Idea
We must compare strings that are formed **from leaf to root**, but during a DFS we naturally build the path **from root to current node**.

So we:

- do a DFS from root to leaves
- maintain the current root-to-node path in a `StringBuilder`
- whenever we reach a leaf, we can construct the leaf-to-root string by reading the path backwards
- keep a global `best` string and update it whenever we find a smaller candidate

Important detail: after exploring a child, we must backtrack (remove the last character) so the `StringBuilder` continues to represent the current path.

---

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|---|---|
| Convert node values `0..25` to letters | `char currentChar = (char) ('a' + node.val);` |
| String must be leaf -> root (reverse direction) | At leaf, build `candidate` by iterating `path` from end to start |
| Need lexicographically smallest among all leaf paths | Maintain `best` and update via `candidate.compareTo(best)` |
| Must consider all leaves | DFS visits every node; update only at `node.left == null && node.right == null` |

---

## Final Java Code & Learning Pattern (Full Content)
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
class Solution {
    private String best;

    public String smallestFromLeaf(TreeNode root) {
        best = null;
        dfs(root, new StringBuilder());
        return best;
    }

    private void dfs(TreeNode node, StringBuilder path) {
        if (node == null) {
            return;
        }

        char currentChar = (char) ('a' + node.val);
        path.append(currentChar);

        if (node.left == null && node.right == null) {
            String candidate = buildLeafToRoot(path);

            if (best == null || candidate.compareTo(best) < 0) {
                best = candidate;
            }
        } else {
            dfs(node.left, path);
            dfs(node.right, path);
        }

        path.deleteCharAt(path.length() - 1);
    }

    private String buildLeafToRoot(StringBuilder path) {
        StringBuilder reversed = new StringBuilder(path.length());
        for (int i = path.length() - 1; i >= 0; i--) {
            reversed.append(path.charAt(i));
        }
        return reversed.toString();
    }
}
```

### Learning Pattern
- If the required output path direction is opposite to the DFS build direction:
  - build the natural direction during DFS (root -> leaf)
  - at “answer points” (here: leaves), transform it (here: reverse) for comparison/output
- For “best among many root-to-leaf paths” problems:
  - compute candidate at each leaf
  - keep a running global `best` and update via lexicographic compare

### Alternative Java Code (String Return, No Global Variable, No Reverse)
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
class Solution {
    public String smallestFromLeaf(TreeNode root) {
        return dfs(root);
    }

    private String dfs(TreeNode node) {
        if (node == null) {
            return null;
        }

        char currentChar = (char) ('a' + node.val);

        String left = dfs(node.left);
        String right = dfs(node.right);

        if (left == null && right == null) {
            return String.valueOf(currentChar);
        }

        String leftCandidate = left == null ? null : left + currentChar;
        String rightCandidate = right == null ? null : right + currentChar;

        if (leftCandidate == null) {
            return rightCandidate;
        }
        if (rightCandidate == null) {
            return leftCandidate;
        }

        return leftCandidate.compareTo(rightCandidate) <= 0 ? leftCandidate : rightCandidate;
    }
}
```

### Learning Pattern
- Another way to avoid reversing is to make recursion return the best leaf-to-current-node string.
- Important detail: compare `left + currentChar` vs `right + currentChar` (not just `left` vs `right`), because lexicographic ordering can change after appending the same character.

---

## Complexity Analysis
- Time Complexity: $O(n \cdot h)$
  - DFS visits all `n` nodes
  - at each leaf, we may build a string of length up to `h` (tree height)
- Space Complexity: $O(h)$
  - recursion stack + `path` length

---

## Tree Problems

### Why or why not a helper function is required
- A helper function is required to carry the current root-to-node path (`StringBuilder path`) while doing DFS.

### Why or why not a global variable is required
- A global variable (`best`) is convenient because every leaf produces a candidate, and we want to update the best answer across all recursion branches.
- In the alternative String-return approach, a global variable is not required because each call returns the best string for its subtree.

### What all is calculated at the current level or node of the tree
- Append the current character to `path`.
- If it’s a leaf, compute the leaf-to-root candidate by iterating `path` backwards and compare with `best`.

### What is returned to the parent from the current level of the tree
- Nothing is returned; the answer is accumulated by updating `best`.

### How to decide if a recursive call to children needs to be made before current node calculation or vice versa
- We must process the current node first (append its character), because children paths depend on this updated `path`.
- After visiting children, we backtrack by removing the last character.

---

## Similar Problems
- [257. Binary Tree Paths](https://leetcode.com/problems/binary-tree-paths/) (construct all root-to-leaf paths)
- [1026. Maximum Difference Between Node and Ancestor](https://leetcode.com/problems/maximum-difference-between-node-and-ancestor/) (carry path state down DFS)
- [1448. Count Good Nodes in Binary Tree](https://leetcode.com/problems/count-good-nodes-in-binary-tree/) (carry best-so-far state down DFS)
