# 1519. Number of Nodes in the Sub-Tree With the Same Label

[LeetCode Link](https://leetcode.com/problems/number-of-nodes-in-the-sub-tree-with-the-same-label/)

## Problem Description
You are given a tree (an undirected connected graph with no cycles) of `n` nodes numbered from `0` to `n - 1`.

You are given an array `edges` where `edges[i] = [ai, bi]` indicates an undirected edge between nodes `ai` and `bi`.

You are also given a string `labels` where `labels[i]` is a lowercase character representing the label of the node `i`.

Return an array `answer` of size `n` where `answer[i]` is the number of nodes in the subtree of node `i` (including itself) that have the same label as node `i`.

The tree is rooted at node `0`.

### Examples

#### Example 1
- Input: `n = 7, edges = [[0,1],[0,2],[1,4],[1,5],[2,3],[2,6]], labels = "abaedcd"`
- Output: `[2,1,1,1,1,1,1]`

#### Example 2
- Input: `n = 4, edges = [[0,1],[1,2],[0,3]], labels = "bbbb"`
- Output: `[4,2,1,1]`

---

## Intuition/Main Idea
This is a rooted tree DP / DFS aggregation problem.

For each node, we want counts of labels in its subtree.

During DFS from the root:

- Let `count[26]` represent frequency of each label in the current subtree.
- For each child, recursively compute `childCount[26]`.
- Add all child counts into the current node’s count.
- Add the current node’s own label.
- The answer for this node is `count[labelOfNode]`.

---

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|---|---|
| Tree is rooted at 0 | Start DFS from `0` with `parent = -1` |
| Subtree counts require aggregating children | Sum `childCount` arrays into `currentCount` |
| Count nodes with same label as current node | `answer[node] = currentCount[labelIndex]` |
| Avoid revisiting parent in undirected edges | Pass `parent` and skip it in adjacency list |

---

## Final Java Code & Learning Pattern (Full Content)
```java
import java.util.*;

class Solution {
    private List<List<Integer>> graph;
    private String labels;
    private int[] answer;

    public int[] countSubTrees(int n, int[][] edges, String labels) {
        this.labels = labels;
        this.answer = new int[n];

        graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }

        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            graph.get(u).add(v);
            graph.get(v).add(u);
        }

        dfs(0, -1);
        return answer;
    }

    private int[] dfs(int node, int parent) {
        int[] currentCount = new int[26];

        for (int neighbor : graph.get(node)) {
            if (neighbor == parent) {
                continue;
            }

            int[] childCount = dfs(neighbor, node);
            for (int i = 0; i < 26; i++) {
                currentCount[i] += childCount[i];
            }
        }

        int labelIndex = labels.charAt(node) - 'a';
        currentCount[labelIndex]++;
        answer[node] = currentCount[labelIndex];

        return currentCount;
    }
}
```

### Learning Pattern
- For tree problems that ask “compute something for every node’s subtree”:
  - root the tree
  - DFS returns an aggregate structure (here: `int[26]`)
  - parent merges child aggregates

---

## Complexity Analysis
- Time Complexity: $O(n \cdot 26)$
  - each edge is traversed once in DFS, and merging counts costs 26
- Space Complexity: $O(n)$
  - adjacency list + recursion stack + returned arrays

---

## Tree Problems

### Why or why not a helper function is required
- A helper function is required to run DFS and return subtree label counts to the parent.

### Why or why not a global variable is required
- Global variables (`graph`, `labels`, `answer`) are convenient to avoid passing large structures through every recursive call.

### What all is calculated at the current level or node of the tree
- Merge all children’s label counts into `currentCount`.
- Add the current node’s label.
- Set `answer[node]` from the updated count.

### What is returned to the parent from the current level of the tree
- The `int[26]` label-frequency array for the entire subtree rooted at `node`.

### How to decide if a recursive call to children needs to be made before current node calculation or vice versa
- We must process children first so we can merge their counts before computing `answer[node]`.

---

## Similar Problems
- [543. Diameter of Binary Tree](https://leetcode.com/problems/diameter-of-binary-tree/) (tree DFS aggregation)
- [124. Binary Tree Maximum Path Sum](https://leetcode.com/problems/binary-tree-maximum-path-sum/) (combine child results)
- [1448. Count Good Nodes in Binary Tree](https://leetcode.com/problems/count-good-nodes-in-binary-tree/) (DFS with carried state)
