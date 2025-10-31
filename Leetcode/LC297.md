### 297. Serialize and Deserialize Binary Tree

#### Problem Statement
[Serialize and Deserialize Binary Tree](https://leetcode.com/problems/serialize-and-deserialize-binary-tree/)

---

### Main Idea & Intuition

The core of this problem is to convert a binary tree into a string (serialize) and then convert that string back into an identical binary tree (deserialize).

1.  **Serialization**: I will use a **pre-order traversal** to walk through the tree. For each node, I'll append its value to a string. To preserve the tree's structure, I must also record the `null` children. I'll use a special marker like `"null"` for this. A `StringBuilder` is efficient for constructing the string.
2.  **Deserialization**: To rebuild the tree, I'll first split the serialized string into a list of values. Then, I'll use a queue to process these values in the same pre-order sequence. I'll create a recursive helper function that reads from the front of the queue. If it reads a value, it creates a node and recursively builds its left and right children. If it reads `"null"`, it returns `null`.

### Code Implementation

```java
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
public class Codec {

    private static final String NULL_SYMBOL = "null";
    private static final String DELIMITER = ",";

    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        StringBuilder sb = new StringBuilder();
        serializeHelper(root, sb);
        // Remove the trailing delimiter
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 1);
        }
        return sb.toString();
    }

    private void serializeHelper(TreeNode node, StringBuilder sb) {
        if (node == null) {
            sb.append(NULL_SYMBOL).append(DELIMITER);
            return;
        }
        sb.append(node.val).append(DELIMITER);
        serializeHelper(node.left, sb);
        serializeHelper(node.right, sb);
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        if (data == null || data.isEmpty()) {
            return null;
        }
        Queue<String> nodes = new LinkedList<>(Arrays.asList(data.split(DELIMITER)));
        return deserializeHelper(nodes);
    }

    private TreeNode deserializeHelper(Queue<String> nodes) {
        String value = nodes.poll();
        if (value == null || value.equals(NULL_SYMBOL)) {
            return null;
        }
        
        TreeNode node = new TreeNode(Integer.parseInt(value));
        node.left = deserializeHelper(nodes);
        node.right = deserializeHelper(nodes);
        
        return node;
    }
}

// Your Codec object will be instantiated and called as such:
// Codec ser = new Codec();
// Codec deser = new Codec();
// TreeNode ans = deser.deserialize(ser.serialize(root));
```

### Complexity Analysis
*   **Time Complexity**: `O(N)`, where `N` is the number of nodes in the tree. Both serialization and deserialization visit each node exactly once.
*   **Space Complexity**: `O(N)`. In the worst case (a skewed tree), the recursion depth for both serialization and deserialization can be `O(N)`. The space for the `StringBuilder` and the `Queue` is also `O(N)`.
