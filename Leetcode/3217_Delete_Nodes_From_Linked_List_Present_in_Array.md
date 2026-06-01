# 3217. Delete Nodes From Linked List Present in Array

## Problem Description

[Delete Nodes From Linked List Present in Array](https://leetcode.com/problems/delete-nodes-from-linked-list-present-in-array/)

You are given an array of integers `nums` and the `head` of a linked list. Return the `head` of the modified linked list after **removing all nodes** whose value appears in `nums`.

### Example 1

Input: `nums = [1,2,3]`, `head = [1,2,3,4,5]`

Output: `[4,5]`

Explanation: Nodes with values `1`, `2`, `3` are removed.

### Example 2

Input: `nums = [1]`, `head = [1,2,1,2,1,2]`

Output: `[2,2,2]`

### Constraints

- `1 <= nums.length <= 10^5`, values distinct, `1 <= nums[i] <= 10^5`.
- The number of list nodes is in `[1, 10^5]`, `1 <= Node.val <= 10^5`.

## Intuition / Main Idea

Membership testing is the core: "is this node's value in `nums`?" A `HashSet` answers that in `O(1)`. Then it's a standard linked-list deletion sweep.

### Build the intuition step by step

1. Dump all of `nums` into a `HashSet<Integer>` for `O(1)` lookups.
2. Use a **dummy node** before the head so deletions at the front need no special-casing.
3. Walk a single pointer `temp` and inspect `temp.next`:
   - If `temp.next.val` is in the set, **splice it out**: `temp.next = temp.next.next` (do **not** advance `temp` — the new `temp.next` must also be checked).
   - Otherwise advance `temp = temp.next`.
4. Return `dummy.next`.

### Why this works

Inspecting `temp.next` (rather than the current node) means we always hold the predecessor, so splicing is a single pointer assignment. The dummy guarantees a valid predecessor even for the original head. Splicing auto-links the survivor to whatever follows, so **no trailing-null cleanup** is needed.

## Code Mapping

| Problem Requirement (@) | Java Code Section |
|---|---|
| O(1) membership test for `nums` | `Set<Integer> set; for (int n : nums) set.add(n);` |
| Remove nodes whose value is in `nums` | `if (set.contains(temp.next.val)) temp.next = temp.next.next;` |
| Handle deletions at the head | `dummy` node before `head`, return `dummy.next` |

## Final Java Code & Learning Pattern

```java
// [Pattern: HashSet membership + dummy-node single-pointer splice]
import java.util.HashSet;
import java.util.Set;

class Solution {
    public ListNode modifiedList(int[] nums, ListNode head) {
        Set<Integer> set = new HashSet<>();
        for (int n : nums) {          // int[] is NOT a Collection -> no addAll
            set.add(n);
        }

        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode temp = dummy;

        while (temp.next != null) {
            if (set.contains(temp.next.val)) {
                temp.next = temp.next.next;   // splice out; do not advance temp
            } else {
                temp = temp.next;
            }
        }
        return dummy.next;
    }
}
```

### Why each part exists

- **Manual loop instead of `set.addAll(nums)`** — `nums` is a primitive `int[]`, which is not a `Collection`; `addAll` won't compile.
- **`dummy` node** — uniform handling so head deletions need no branch.
- **Splice, don't advance, on a match** — the freshly linked `temp.next` could also be a deletion target.

## Complexity Analysis

- **Time Complexity:** $O(n + m)$ where `m = nums.length` (build set) and `n` = list length (one sweep).
- **Space Complexity:** $O(m)$ for the set.

## LinkedList Notes

- **`dummy` pointer**: provides a stable predecessor for the head; without it, deleting the first node(s) needs special logic.
- **`temp` pointer inspects `temp.next`**: keeps the predecessor in hand, making deletion an `O(1)` pointer swap.
- **Loop `while (temp.next != null)`**: stop when there is no next node to inspect; `temp` itself is always a kept node.

## Similar Problems

1. [LeetCode 203. Remove Linked List Elements](https://leetcode.com/problems/remove-linked-list-elements/) — same dummy + splice idiom for a single target value.
2. [LeetCode 83. Remove Duplicates from Sorted List](https://leetcode.com/problems/remove-duplicates-from-sorted-list/) — splice based on the next node's value.
3. [LeetCode 1836. Remove Duplicates From an Unsorted Linked List](https://leetcode.com/problems/remove-duplicates-from-an-unsorted-linked-list/) — HashMap counts + dummy splice.
