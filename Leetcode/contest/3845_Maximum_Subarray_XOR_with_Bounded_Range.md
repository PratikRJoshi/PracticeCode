# Maximum Subarray XOR with Bounded Range

## Problem Description

**Problem Link:** [Maximum Subarray XOR with Bounded Range](https://leetcode.com/problems/maximum-subarray-xor-with-bounded-range/)

You are given a non-negative integer array `nums` and an integer `k`.

You must choose a subarray such that:

- `max(subarray) - min(subarray) <= k`

The **value** of a chosen subarray is the bitwise XOR of all its elements.

Return the maximum possible value among all valid subarrays.

**Example 1:**
```
Input: nums = [5,4,5,6], k = 2
Output: 7
Explanation:
Choose subarray [5,4,5,6]. max-min = 6-4 = 2.
XOR = 5 ^ 4 ^ 5 ^ 6 = 7.
```

**Example 2:**
```
Input: nums = [5,4,5,6], k = 1
Output: 6
Explanation:
The best valid subarray is [6]. max-min = 0 <= 1.
XOR = 6.
```

**Constraints:**

- `1 <= nums.length <= 4 * 10^4`
- `0 <= nums[i] < 2^15`
- `0 <= k < 2^15`

## Intuition/Main Idea

### Build the intuition (mentor-style)

We have two requirements at the same time:

1. Subarray must satisfy a **range constraint**: `max - min <= k`.
2. We want to maximize the **XOR of the subarray**.

The range constraint is a classic sliding-window pattern:

- Maintain a window `[left..right]` such that it is always valid.
- Use two monotonic deques to track:
  - current minimum in the window
  - current maximum in the window
- When the window becomes invalid, move `left` forward to restore validity.

Now, within each valid window ending at `right`, we need the maximum XOR of any subarray ending at `right` (or inside the window). XOR subarray queries are usually handled using prefix XOR:

- `prefixXor[i] = nums[0] ^ nums[1] ^ ... ^ nums[i-1]`
- XOR of subarray `[l..r]` is `prefixXor[r+1] ^ prefixXor[l]`

So for each `right`, maximizing subarray XOR reduces to:

- maximize `prefixXor[right+1] ^ prefixXor[l]` over all valid `l` in `[left..right]`

That is a classic “max XOR with a set” problem, solved with a binary trie.

### Core plan

As `right` grows:

- insert `prefixXor[right]` into a trie when it becomes eligible
- remove `prefixXor[left]` when `left` moves (we need a trie that supports deletions)
- query the trie with `prefixXor[right+1]` to get best XOR

### Why the intuition works

- The window logic guarantees the max/min constraint.
- Prefix XOR turns subarray XOR into XOR between two prefix values.
- A bitwise trie finds the best partner prefix value to maximize XOR greedily bit-by-bit.

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|------------------------|-----------------------------------|
| Enforce `max - min <= k` for chosen subarray | Sliding window with minDeque/maxDeque; shrink while invalid (lines 28-65) |
| Compute XOR of any subarray quickly | Prefix XOR array (lines 20-26) |
| Maximize XOR over valid starts | Binary trie query for max XOR (lines 67-141) |
| Support moving left boundary | Trie supports insert/delete counts (lines 79-141) |

## Final Java Code & Learning Pattern (Full Content)

```java
import java.util.ArrayDeque;
import java.util.Deque;

class Solution {
    private static final int MAX_BIT = 14; // nums[i] < 2^15 => bits 14..0

    public int maxSubarrayXorWithBoundedRange(int[] nums, int k) {
        int n = nums.length;

        // prefixXor[i] = XOR of nums[0..i-1].
        // We allocate size n+1 because we want prefixXor[0] = 0 (empty prefix),
        // and prefixXor[r+1] for subarray ending at r.
        int[] prefixXor = new int[n + 1];
        for (int i = 0; i < n; i++) {
            prefixXor[i + 1] = prefixXor[i] ^ nums[i];
        }

        // Sliding window for range constraint.
        int left = 0;
        Deque<Integer> minDeque = new ArrayDeque<>(); // increasing values' indices
        Deque<Integer> maxDeque = new ArrayDeque<>(); // decreasing values' indices

        BinaryTrie trie = new BinaryTrie();

        // Initially, for right = 0, valid l can be 0 only. We insert prefixXor[0].
        trie.add(prefixXor[0]);

        int best = 0;

        for (int right = 0; right < n; right++) {
            // Update min deque: maintain increasing order of nums values.
            while (!minDeque.isEmpty() && nums[minDeque.peekLast()] >= nums[right]) {
                minDeque.pollLast();
            }
            minDeque.addLast(right);

            // Update max deque: maintain decreasing order of nums values.
            while (!maxDeque.isEmpty() && nums[maxDeque.peekLast()] <= nums[right]) {
                maxDeque.pollLast();
            }
            maxDeque.addLast(right);

            // Shrink from the left while window invalid.
            while (!minDeque.isEmpty() && !maxDeque.isEmpty()
                    && nums[maxDeque.peekFirst()] - nums[minDeque.peekFirst()] > k) {
                // Before moving left forward, we must remove prefixXor[left] from trie
                // because starts at left are no longer valid.
                trie.remove(prefixXor[left]);

                // Move left.
                left++;

                // Pop outdated indices.
                if (!minDeque.isEmpty() && minDeque.peekFirst() < left) {
                    minDeque.pollFirst();
                }
                if (!maxDeque.isEmpty() && maxDeque.peekFirst() < left) {
                    maxDeque.pollFirst();
                }
            }

            // Now the valid starts l are in [left..right].
            // We want max(prefixXor[right+1] XOR prefixXor[l]).
            int currentPrefix = prefixXor[right + 1];
            best = Math.max(best, trie.maxXor(currentPrefix));

            // Make prefixXor[right+1] available for future windows.
            trie.add(prefixXor[right + 1]);
        }

        return best;
    }

    private static class BinaryTrie {
        private final Node root = new Node();

        private static class Node {
            Node zero;
            Node one;
            int count; // how many numbers go through this node (for deletions)
        }

        public void add(int value) {
            Node current = root;
            current.count++;

            for (int bit = MAX_BIT; bit >= 0; bit--) {
                int b = (value >> bit) & 1;
                if (b == 0) {
                    if (current.zero == null) {
                        current.zero = new Node();
                    }
                    current = current.zero;
                } else {
                    if (current.one == null) {
                        current.one = new Node();
                    }
                    current = current.one;
                }
                current.count++;
            }
        }

        public void remove(int value) {
            Node current = root;
            current.count--;

            for (int bit = MAX_BIT; bit >= 0; bit--) {
                int b = (value >> bit) & 1;
                if (b == 0) {
                    current = current.zero;
                } else {
                    current = current.one;
                }
                current.count--;
            }
        }

        public int maxXor(int value) {
            Node current = root;
            int result = 0;

            for (int bit = MAX_BIT; bit >= 0; bit--) {
                int b = (value >> bit) & 1;

                // Greedy choice:
                // - if current bit is 0, prefer a 1 in trie
                // - if current bit is 1, prefer a 0 in trie
                Node preferred = (b == 0) ? current.one : current.zero;
                Node fallback = (b == 0) ? current.zero : current.one;

                if (preferred != null && preferred.count > 0) {
                    result |= (1 << bit);
                    current = preferred;
                } else {
                    current = fallback;
                }
            }

            return result;
        }
    }
}
```

## Complexity Analysis

**Time Complexity:** $O(n \cdot B)$ where $B = 15$

- Each index does:
  - deque maintenance amortized $O(1)$
  - trie query/insert/remove: $O(B)$

**Space Complexity:** $O(n \cdot B)$ worst case for the trie

- Trie stores up to `n+1` prefix XOR values.

## Similar Problems

- [Maximum XOR of Two Numbers in an Array](https://leetcode.com/problems/maximum-xor-of-two-numbers-in-an-array/) - binary trie for max XOR
- [Subarray XOR Queries](https://leetcode.com/problems/xor-queries-of-a-subarray/) - prefix XOR technique
- [Longest Continuous Subarray With Absolute Diff Less Than or Equal to Limit](https://leetcode.com/problems/longest-continuous-subarray-with-absolute-diff-less-than-or-equal-to-limit/) - sliding window with min/max deques
