### LeetCode 503: Next Greater Element II
Problem: https://leetcode.com/problems/next-greater-element-ii/

---

## Problem Statement
Given a circular integer array `nums`, for every index `i` find the **next greater element**—the first element to the right (wrapping around) that is strictly larger than `nums[i]`.  If it does not exist, store `-1`.

---

## Intuition (Monotone Stack on *2×* Traversal)
For a linear array LC496 solved NGE by one pass.  To handle circularity, pretend we concatenate the array with itself.  We iterate **twice** (indices `0 … 2n-1`) but only store answers for the first `n` positions.  The monotone decreasing stack holds *indices modulo n* whose answer we haven’t found yet.

---

## Mapping Requirements → Code
| Requirement | Code line(s) |
|-------------|-------------|
| Double length loop `2*n-1` | for `(int i=0;i<2*n;i++)` (15) |
| Convert to real index | `int idx = i % n;` (16) |
| While current > stack-top value | `while(!st.isEmpty() && nums[idx]>nums[st.peek()])` (17) |
| Record answer only once | `ans[top]=nums[idx];` (18) |
| Push indices from first pass only | `if(i<n) st.push(idx);` (22) |

---

## Java Implementation
```java
class Solution {
    public int[] nextGreaterElements(int[] nums) {
        int n = nums.length;
        int[] ans = new int[n];
        Arrays.fill(ans, -1);
        Deque<Integer> st = new ArrayDeque<>(); // store indices whose NGE not found

        for (int i = 0; i < 2 * n; i++) {
            int idx = i % n;
            while (!st.isEmpty() && nums[idx] > nums[st.peek()]) {
                int top = st.pop();
                ans[top] = nums[idx];
            }
            if (i < n) { // only push indices from first cycle
                st.push(idx);
            }
        }
        return ans;
    }
}
```

---

## Complexity
Time `O(n)` (each index pushed/popped ≤1).  Space `O(n)` for the stack.

---

## Relation to LC496/907
Same decreasing‐stack skeleton; we just iterate twice and gate pushes to handle circular property.