# Copy List with Random Pointer

## Problem Description

**Problem Link:** [Copy List with Random Pointer](https://leetcode.com/problems/copy-list-with-random-pointer/)

A linked list of length `n` is given such that each node contains an additional random pointer, which could point to any node in the list, or `null`.

Construct a **deep copy** of the list. The deep copy should consist of exactly `n` brand new nodes, where each new node has its value set to the value of its corresponding original node. Both the `next` and `random` pointer of the new nodes should point to new nodes in the copied list such that the pointers in the original list and copied list represent the same list state. **None of the pointers in the new list should point to nodes in the original list**.

Return *the head of the copied linked list*.

The linked list is represented in the input/output as a list of `n` nodes. Each node is represented as a `[val, random_index]` where:

- `val`: an integer representing `Node.val`
- `random_index`: the index of the node (range from `0` to `n-1`) that the `random` pointer points to, or `null` if it does not point to any node.

Your code will **only** be given the `head` of the original linked list.

**Example 1:**

```
Input: head = [[7,null],[13,0],[11,4],[10,2],[1,0]]
Output: [[7,null],[13,0],[11,4],[10,2],[1,0]]
```

**Example 2:**

```
Input: head = [[1,1],[2,1]]
Output: [[1,1],[2,1]]
```

**Example 3:**

```
Input: head = [[3,null],[3,0],[3,null]]
Output: [[3,null],[3,0],[3,null]]
```

**Constraints:**
- `0 <= n <= 1000`
- `-10^4 <= Node.val <= 10^4`
- `Node.random` is `null` or is pointing to some node in the linked list.

## Intuition/Main Idea

This problem requires creating a deep copy of a linked list with random pointers. The challenge is that random pointers can point to any node, including nodes we haven't created yet.

**Core Algorithm (Two-Pass Approach):**
1. **First pass:** Create all new nodes and store mapping from old nodes to new nodes.
2. **Second pass:** Set `next` and `random` pointers using the mapping.

**Alternative (One-Pass with Interleaving):**
1. Create new nodes and interleave them with original nodes.
2. Set random pointers.
3. Separate the two lists.

**Why mapping works:** By creating a mapping from original nodes to copied nodes, we can easily set random pointers even if they point to nodes we haven't processed yet.

## Code Mapping

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Map original to copy | HashMap - Line 6 |
| Create all new nodes | First pass - Lines 8-12 |
| Set next pointers | Second pass - Lines 14-17 |
| Set random pointers | Second pass - Lines 18-20 |
| Return copied head | Return statement - Line 22 |

## Final Java Code & Learning Pattern

### Two-Pass Approach with HashMap

```java
import java.util.*;

/*
// Definition for a Node.
class Node {
    int val;
    Node next;
    Node random;

    public Node(int val) {
        this.val = val;
        this.next = null;
        this.random = null;
    }
}
*/

class Solution {
    public Node copyRandomList(Node head) {
        if (head == null) {
            return null;
        }
        
        // Map: original node -> copied node
        Map<Node, Node> map = new HashMap<>();
        
        // First pass: create all new nodes
        Node current = head;
        while (current != null) {
            map.put(current, new Node(current.val));
            current = current.next;
        }
        
        // Second pass: set next and random pointers
        current = head;
        while (current != null) {
            Node copy = map.get(current);
            
            // Set next pointer
            if (current.next != null) {
                copy.next = map.get(current.next);
            }
            
            // Set random pointer
            if (current.random != null) {
                copy.random = map.get(current.random);
            }
            
            current = current.next;
        }
        
        return map.get(head);
    }
}
```

### One-Pass Approach with Interleaving (Space Optimized)

```java
class Solution {
    public Node copyRandomList(Node head) {
        if (head == null) {
            return null;
        }
        
        // First pass: create new nodes and interleave with original
        Node current = head;
        while (current != null) {
            Node copy = new Node(current.val);
            copy.next = current.next;
            current.next = copy;
            current = copy.next;
        }
        
        // Second pass: set random pointers
        current = head;
        while (current != null) {
            if (current.random != null) {
                current.next.random = current.random.next;
            }
            current = current.next.next;
        }
        
        // Third pass: separate the two lists
        Node original = head;
        Node copied = head.next;
        Node copiedHead = copied;
        
        while (original != null) {
            original.next = original.next.next;
            if (copied.next != null) {
                copied.next = copied.next.next;
            }
            original = original.next;
            copied = copied.next;
        }
        
        return copiedHead;
    }
}
```

**Explanation of Key Code Sections:**

**Two-Pass Approach:**

1. **First Pass (Lines 13-17):** Create all new nodes and store them in a map with original nodes as keys. This ensures we can access any copied node given its original.

2. **Second Pass (Lines 19-30):** For each original node:
   - **Get Copy (Line 21):** Retrieve the copied node from the map.
   - **Set Next (Lines 23-25):** If original has a next, set copy's next to the copy of original's next.
   - **Set Random (Lines 27-29):** If original has a random, set copy's random to the copy of original's random.

**One-Pass Approach:**

1. **Interleave Nodes (Lines 10-16):** Create new nodes and insert them right after their originals. This creates: `original1 -> copy1 -> original2 -> copy2 -> ...`

2. **Set Random Pointers (Lines 18-23):** For each original node, set its copy's random to `original.random.next` (which is the copy of `original.random`).

3. **Separate Lists (Lines 25-34):** Restore original list's next pointers and connect copied nodes together.

**Why interleaving works:**
- **Access to copies:** By placing copies right after originals, we can access a copy via `original.next`.
- **Random pointers:** `original.random.next` gives us the copy of `original.random`.
- **Space efficient:** Uses O(1) extra space (excluding the new list).

**Example walkthrough:**
- Original: A -> B -> C (with random pointers)
- After interleaving: A -> A' -> B -> B' -> C -> C'
- Set random: A'.random = A.random.next
- Separate: A -> B -> C and A' -> B' -> C'

## Complexity Analysis

- **Time Complexity:** $O(n)$ where $n$ is the number of nodes. We traverse the list a constant number of times.

- **Space Complexity:** 
  - HashMap approach: $O(n)$ for the map.
  - Interleaving approach: $O(1)$ extra space (excluding the new list).

## Similar Problems

Problems that can be solved using similar techniques:

1. **138. Copy List with Random Pointer** (this problem) - Deep copy with random pointers
2. **133. Clone Graph** - Deep copy of graph
3. **148. Sort List** - Linked list manipulation
4. **25. Reverse Nodes in k-Group** - Linked list reversal
5. **92. Reverse Linked List II** - Partial reversal
6. **206. Reverse Linked List** - Complete reversal
7. **21. Merge Two Sorted Lists** - List merging
8. **23. Merge k Sorted Lists** - Multiple list merging
9. **141. Linked List Cycle** - Cycle detection
10. **142. Linked List Cycle II** - Find cycle start

