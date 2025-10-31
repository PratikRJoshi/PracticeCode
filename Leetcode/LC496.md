### LeetCode 496: Next Greater Element I
Problem: https://leetcode.com/problems/next-greater-element-i/

---

## Problem Statement
Given two integer arrays `nums1` and `nums2`, where `nums1` is a subset of `nums2`, return an array `ans` of the same length as `nums1` such that `ans[i]` is the **next greater element** of `nums1[i]` in `nums2`. 

The **next greater element** of `x` in `nums2` is the first element to the right of `x` that is **strictly larger** than `x`. If it does not exist, store `-1`.

---

## Intuition / Main Idea ( Monotonic Stack pattern )
We must answer “next greater” queries for many elements quickly. Scanning rightward from every index separately would be `O(n²)`.  Instead, we traverse `nums2` **once** from left to right while maintaining a *monotonically decreasing* stack of indices.  Whenever we see a value that is **greater** than the value at the stack’s top, we have discovered that value’s next‐greater element.

Steps:
1. Prepare a hash `Map<Integer,Integer>` to store `value → nextGreater` for every number in `nums2`.
2. Walk through `nums2`:
   * While the stack is **not empty** and `nums2[i] > nums2[stack.peek()]`, pop `idx` and record `map.put(nums2[idx], nums2[i])`.
   * Push current index `i`.
3. After the pass, remaining indices have no next greater; they default to `-1`.
4. Produce the answer for `nums1` by looking up each value in the map—if absent, output `-1`.

---

## Mapping Requirements → Code
| Requirement | Code Lines |
|-------------|-----------|
| Compute mapping for every element of `nums2` | Stack loop (19-28) |
| Store discover-time result | `next.put(nums2[idx], nums2[i])` (23) |
| Default to `-1` when map missing | `ans[k] = next.getOrDefault(val, -1)` (32) |
| Return result array | `return ans;` (34) |

---

## Java Implementation
```java
class Solution {
    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
        Map<Integer, Integer> next = new HashMap<>();    // value -> NGE
        Deque<Integer> st = new ArrayDeque<>();          // index stack (monotone dec)

        // 1) Build next-greater map for every element in nums2
        for (int i = 0; i < nums2.length; i++) {
            while (!st.isEmpty() && nums2[i] > nums2[st.peek()]) {
                int idx = st.pop();
                next.put(nums2[idx], nums2[i]);
            }
            st.push(i);
        }
        // Remaining values get default -1 via map default.

        // 2) Construct answer for nums1 in the given order
        int[] ans = new int[nums1.length];
        for (int k = 0; k < nums1.length; k++) {
            int val = nums1[k];
            ans[k] = next.getOrDefault(val, -1);
        }
        return ans;
    }
}
```

---

## Complexity Analysis
* **Time:** `O(m + n)` where `n=nums2.length`, `m=nums1.length` — each `nums2` index is pushed/popped once.
* **Space:** `O(n)` for the stack and map.

---

## Pattern Parallel to LC907
Both problems count or lookup “next greater”. LC907 uses spans to aggregate contributions; LC496 simply records the first greater value.  The **monotone decreasing stack** skeleton is identical:
```
for (element in array)
    while stack not empty and current > stack.top()
        process(stack.pop())
    stack.push(current)
```
The inner operation differs (map assignment vs span count), but the structural pattern remains the same.