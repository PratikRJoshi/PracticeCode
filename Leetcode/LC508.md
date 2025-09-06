### 508. Most Frequent Subtree Sum
Problem: https://leetcode.com/problems/most-frequent-subtree-sum/

### Main Idea & Intuition

The problem asks us to find which "subtree sum" appears most often. A subtree sum for a node is the sum of its own value plus the sums of its entire left and right subtrees.

This problem has two main parts:
1.  **Calculate the subtree sum for every node.** To get the sum for a node, we first need to know the sums of its children. This tells us we need a post-order traversal that bubbles the sum information up the tree.
2.  **Count the frequencies of these sums.** As we calculate each subtree sum, we need to store it somewhere and count how many times we've seen it. A hash map (`sum -> frequency`) is the perfect data structure for this.

So, the overall strategy is to create a recursive helper function that traverses the tree. For each node, it will:
1.  Recursively call itself on its left and right children to get their subtree sums.
2.  Calculate its own subtree sum using `node.val + leftSum + rightSum`.
3.  Update the frequency of this new sum in our hash map.
4.  Return its own subtree sum to its parent.

After the traversal is complete, we'll iterate through our frequency map to find the highest frequency and collect all the sums that have that frequency.

### Applying General Tree Intuitions

-   **Global Variable vs. Return Value**: This is a perfect example of **Scenario 2 (Different Information)** from your notes.
    -   The recursive helper's primary job is to **return** the `subtreeSum` to its parent.
    -   Simultaneously, we need to track the frequency of every sum we calculate. This requires a shared hash map that acts as a **"global" state**. We also need a variable to track the `maxFrequency` seen so far.
    -   The return value (`int sum`) is different from the side-effect (updating the map).

-   **Pre-order vs. Post-order**: This is a clear **Post-order (bottom-up)** traversal. To calculate the sum for a node, you *must* have the results from its children first.

-   **Helper Function**: Yes, a **helper function is essential**. The main function needs to return an `int[]`, while our recursive helper's job is to return an `int` (the subtree sum). The main function will initialize the map, call the helper to populate it, and then process the map to build the final result.

### Step-by-Step Code Build-Up

**Step 1: Set up the main function and shared state.**
We need a `Map<Integer, Integer>` to store sum frequencies and an `int` to track the maximum frequency found.

```java
class Solution {
    Map<Integer, Integer> sumFrequencies = new HashMap<>();
    int maxFrequency = 0;

    public int[] findFrequentTreeSum(TreeNode root) {
        // ... call helper and process map
    }
}
```

**Step 2: Create the post-order helper function.**
This function, `postOrderSum`, will return the subtree sum.

```java
private int postOrderSum(TreeNode node) {
    // ...
}
```

**Step 3: Define the base case.**
An empty node contributes `0` to the sum.

```java
private int postOrderSum(TreeNode node) {
    if (node == null) {
        return 0;
    }
    // ...
}
```

**Step 4: Make recursive calls.**
Get the sums from the left and right children first (post-order).

```java
private int postOrderSum(TreeNode node) {
    if (node == null) return 0;
    int leftSum = postOrderSum(node.left);
    int rightSum = postOrderSum(node.right);
    // ...
}
```

**Step 5: Calculate sum and update frequencies.**
Calculate the current node's subtree sum. Then, update its frequency in the map and update `maxFrequency` if needed.

```java
private int postOrderSum(TreeNode node) {
    // ... recursive calls
    int currentSum = node.val + leftSum + rightSum;
    int newFrequency = sumFrequencies.getOrDefault(currentSum, 0) + 1;
    sumFrequencies.put(currentSum, newFrequency);
    maxFrequency = Math.max(maxFrequency, newFrequency);
    // ...
}
```

**Step 6: Return the current sum.**
The helper must return its calculated sum to its parent.

```java
private int postOrderSum(TreeNode node) {
    // ... calculations
    return currentSum;
}
```

**Step 7: Finalize the main function.**
Call the helper. Then, stream through the map's keys, filter for those whose frequency matches `maxFrequency`, and collect them into an array.

```java
public int[] findFrequentTreeSum(TreeNode root) {
    postOrderSum(root); // Populate the map

    List<Integer> resultList = new ArrayList<>();
    for (int key : sumFrequencies.keySet()) {
        if (sumFrequencies.get(key) == maxFrequency) {
            resultList.add(key);
        }
    }

    return resultList.stream().mapToInt(i -> i).toArray();
}
```

### Final Code

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Solution {
    Map<Integer, Integer> sumFrequencies = new HashMap<>();
    int maxFrequency = 0;

    public int[] findFrequentTreeSum(TreeNode root) {
        // Helper function populates the map and updates maxFrequency.
        postOrderSum(root);

        // Collect all sums that have the max frequency.
        List<Integer> resultList = new ArrayList<>();
        for (int key : sumFrequencies.keySet()) {
            if (sumFrequencies.get(key) == maxFrequency) {
                resultList.add(key);
            }
        }

        // Convert List<Integer> to int[].
        return resultList.stream().mapToInt(i -> i).toArray();
    }

    private int postOrderSum(TreeNode node) {
        if (node == null) {
            return 0;
        }

        // 1. Get sums from children first (post-order).
        int leftSum = postOrderSum(node.left);
        int rightSum = postOrderSum(node.right);

        // 2. Calculate the sum for the current subtree.
        int currentSum = node.val + leftSum + rightSum;

        // 3. Update the frequency of this sum.
        int newFrequency = sumFrequencies.getOrDefault(currentSum, 0) + 1;
        sumFrequencies.put(currentSum, newFrequency);

        // 4. Update the overall max frequency seen so far.
        maxFrequency = Math.max(maxFrequency, newFrequency);

        // 5. Return the sum to the parent.
        return currentSum;
    }
}
```

### Complexity Analysis

*   **Time Complexity: O(N)**
    *   We visit each node exactly once during our post-order traversal. The hash map operations and the final iteration over the map keys take time proportional to the number of nodes.

*   **Space Complexity: O(N)**
    *   In the worst case, the recursion stack can go as deep as the height of the tree, `H`, which is `O(N)` for a skewed tree. Additionally, the hash map can store up to `N` unique subtree sums in the worst case (e.g., a tree where every subtree sum is unique). Therefore, the total space complexity is `O(N)`.