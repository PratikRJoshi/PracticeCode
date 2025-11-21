# Clone Graph

## Problem Description

**Problem Link:** [Clone Graph](https://leetcode.com/problems/clone-graph/)

Given a reference of a node in a **[connected](https://en.wikipedia.org/wiki/Connectivity_(graph_theory)#Connected_graph)** undirected graph.

Return a [**deep copy**](https://en.wikipedia.org/wiki/Object_copying#Deep_copy) (clone) of the graph.

Each node in the graph contains a value (`int`) and a list (`List[Node]`) of its neighbors.

**Example 1:**
```
Input: adjList = [[2,4],[1,3],[2,4],[1,3]]
Output: [[2,4],[1,3],[2,4],[1,3]]
Explanation: There are 4 nodes in the graph.
1st node (val = 1)'s neighbors are 2nd node (val = 2) and 4th node (val = 4).
2nd node (val = 2)'s neighbors are 1st node (val = 1) and 3rd node (val = 3).
3rd node (val = 3)'s neighbors are 2nd node (val = 2) and 4th node (val = 4).
4th node (val = 4)'s neighbors are 1st node (val = 1) and 3rd node (val = 3).
```

**Constraints:**
- The number of nodes in the graph is in the range `[0, 100]`.
- `1 <= Node.val <= 100`
- `Node.val` is unique for each node.
- There are no repeated edges and no self-loops in the graph.
- The graph is connected and all nodes can be visited starting from the given node.

## Intuition/Main Idea

We need to create a deep copy of the graph. This requires visiting all nodes and creating copies while maintaining neighbor relationships.

**Core Algorithm:**
- Use BFS or DFS to traverse the graph
- Use HashMap to map original nodes to cloned nodes
- For each node, create clone and add to map
- For each neighbor, create clone (if not exists) and add to neighbors list

**Why HashMap:** We need to avoid creating duplicate clones and to link neighbors correctly. HashMap maps original → clone.

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|------------------------|-----------------------------------|
| Clone graph | BFS traversal - Lines 7-25 |
| Map original to clone | HashMap - Lines 5, 12 |
| Create node clones | Node creation - Line 12 |
| Link neighbors | Neighbor addition - Lines 19-24 |

## Final Java Code & Learning Pattern (Full Content)

```java
class Solution {
    public Node cloneGraph(Node node) {
        if (node == null) {
            return null;
        }
        
        // HashMap to map original nodes to cloned nodes
        Map<Node, Node> cloneMap = new HashMap<>();
        
        // Queue for BFS traversal
        Queue<Node> queue = new LinkedList<>();
        queue.offer(node);
        
        // Create clone of starting node
        cloneMap.put(node, new Node(node.val));
        
        // BFS traversal
        while (!queue.isEmpty()) {
            Node original = queue.poll();
            Node clone = cloneMap.get(original);
            
            // Process all neighbors
            for (Node neighbor : original.neighbors) {
                // If neighbor not cloned yet, create clone
                if (!cloneMap.containsKey(neighbor)) {
                    cloneMap.put(neighbor, new Node(neighbor.val));
                    queue.offer(neighbor); // Add to queue for processing
                }
                
                // Add cloned neighbor to cloned node's neighbors list
                clone.neighbors.add(cloneMap.get(neighbor));
            }
        }
        
        return cloneMap.get(node);
    }
}
```

## Complexity Analysis

**Time Complexity:** $O(V + E)$ where $V$ is number of vertices and $E$ is number of edges. We visit each node and edge once.

**Space Complexity:** $O(V)$ for the HashMap and queue.

## Similar Problems

- [Copy List with Random Pointer](https://leetcode.com/problems/copy-list-with-random-pointer/) - Similar cloning pattern
- [Serialize and Deserialize Binary Tree](https://leetcode.com/problems/serialize-and-deserialize-binary-tree/) - Graph/tree serialization
- [Course Schedule](https://leetcode.com/problems/course-schedule/) - Graph traversal

