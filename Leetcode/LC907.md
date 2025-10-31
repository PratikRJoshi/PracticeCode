### LeetCode 907: Sum of Subarray Minimums
Problem: https://leetcode.com/problems/sum-of-subarray-minimums/

---

## Problem Statement
Given an array of integers `arr`, find the sum of `min(subarray)` over **every** contiguous subarray of `arr`. Because the answer can be very large, return it modulo `1_000_000_007`.

---

## Intuition / Main Idea ( Monotonic-Stack pattern )
The key observation is that every element `arr[i]` can serve as the minimum of many subarrays. If we count **how many** subarrays have `arr[i]` as their minimum, we can sum `arr[i] * (count)` for all `i` instead of enumerating `O(n²)` subarrays.

For an index `i` :
* `leftSpan[i]`  = # of strictly greater elements to the **left** that stop before a smaller (or equal) value. These define how far left a subarray can stretch while keeping `arr[i]` as the min.
* `rightSpan[i]` = # of greater-or-equal elements to the **right** that stop before a strictly smaller value. These define how far right the subarray can stretch.

Then `arr[i]` is minimum in `leftSpan[i] * rightSpan[i]` subarrays.

A **monotonic increasing stack** gives those spans in `O(n)`; push indices, pop while violated.

---

## Mapping Requirements → Code (Lines refer to Java snippet)
| Requirement | Code Lines |
|-------------|-----------|
| Store MOD | `final int MOD = 1_000_000_007;` (11) |
| Compute left spans with `>` rule | stack loop (19-28) |
| Compute right spans with `>=` rule | reverse stack loop (31-40) |
| Sum contribution `arr[i]*left*right` | (43-46) |
| Return answer mod MOD | (47) |

---

## Java Implementation
```java
class Solution {
    public int sumSubarrayMins(int[] arr) {
        int n = arr.length;
        final int MOD = 1_000_000_007;

        int[] left  = new int[n]; // strict >
        int[] right = new int[n]; // >=
        Deque<Integer> st = new ArrayDeque<>();

        // 1) Left span – elements strictly greater
        for (int i = 0; i < n; i++) {
            while (!st.isEmpty() && arr[st.peek()] > arr[i]) st.pop();
            left[i] = st.isEmpty() ? i + 1 : i - st.peek();
            st.push(i);
        }

        st.clear();
        // 2) Right span – elements >= (current)
        for (int i = n - 1; i >= 0; i--) {
            while (!st.isEmpty() && arr[st.peek()] >= arr[i]) st.pop();
            right[i] = st.isEmpty() ? n - i : st.peek() - i;
            st.push(i);
        }

        long ans = 0;
        for (int i = 0; i < n; i++) {
            long contrib = ((long) arr[i] * left[i] % MOD) * right[i] % MOD;
            ans = (ans + contrib) % MOD;
        }
        return (int) ans;
    }
}
```

---

## Complexity Analysis
* **Time :** `O(n)` – each index is pushed/popped at most once per stack pass.
* **Space:** `O(n)` – arrays `left`, `right`, and the stack`.  (Can be O(1)` extra if computed on the fly but less clear.)

---

## Pattern Parallels
* Exactly like LC1019 (Next Greater Node in Linked List) or LC42/739 stack patterns: we decompose a global sum by counting span products using monotonic stacks.
* Both left and right passes mirror each other—only the comparison sign (`>` vs `>=`) changes to avoid double-counting equal values.