### LeetCode 556: Next Greater Element III
Problem: https://leetcode.com/problems/next-greater-element-iii/

---

## Problem Statement
Given a positive 32-bit integer `n`, find the **next greater integer** that contains *exactly the same digits* as `n`.  If no such integer exists (i.e. you are already at the highest permutation) **or** the answer exceeds 32-bit signed-int range, return `-1`.

---

## Main Idea — classic *Next Permutation* (lexicographically next arrangement)
This is identical to generating the next lexicographic permutation of an array of digits:
1. Scan from right-to-left to find the first **pivot** index `i` where `digits[i] < digits[i+1]`. If none exists the digits are in descending order → no greater permutation.
2. From the tail, find the **successor** index `j` with the *smallest digit > digits[i]*.
3. Swap `digits[i]` and `digits[j]`.
4. Reverse the suffix starting at `i+1` to obtain the minimal arrangement after the pivot.
5. Convert the digits back to a long; if it exceeds `Integer.MAX_VALUE` return `-1`.

This is the exact pattern used in LeetCode 31 “Next Permutation.”

---

## Mapping Requirements → Code
| Requirement | Code Lines |
|-------------|-----------|
| Convert `n` to digit array | `char[] digits = ...` (13-14) |
| Find pivot where `digits[i] < digits[i+1]` | while loop (18-20) |
| No pivot ⇒ return `-1` | (21-22) |
| Find rightmost successor `>` pivot | while loop (25-27) |
| Swap pivot/successor | util swap (28) |
| Reverse suffix | two-pointer while (30-32) |
| Rebuild number & range-check | parse + compare (35-38) |

---

## Java Implementation
```java
class Solution {
    public int nextGreaterElement(int n) {
        char[] digits = String.valueOf(n).toCharArray();
        int len = digits.length;

        // 1) Find pivot (first decreasing from right)
        int i = len - 2;
        while (i >= 0 && digits[i] >= digits[i + 1]) i--;
        if (i < 0) return -1; // already max permutation

        // 2) Find rightmost successor larger than pivot
        int j = len - 1;
        while (digits[j] <= digits[i]) j--;

        // 3) Swap
        swap(digits, i, j);
        // 4) Reverse suffix to get minimal tail
        reverse(digits, i + 1, len - 1);

        long val = Long.parseLong(new String(digits));
        return val > Integer.MAX_VALUE ? -1 : (int) val;
    }

    private void swap(char[] a, int i, int j) {
        char tmp = a[i]; a[i] = a[j]; a[j] = tmp;
    }
    private void reverse(char[] a, int l, int r) {
        while (l < r) swap(a, l++, r--);
    }
}
```

---

## Why does the pivot-swap-reverse recipe work?
1. **Suffix is non-increasing.**
   Scanning from the end until `digits[i] < digits[i+1]` finds the longest suffix that is in descending order.  That suffix is already the *largest* permutation of its digits.  Any larger overall number must modify a digit *before* this suffix.

2. **Pivot is the rightmost ascent.**  Changing a more significant digit to something bigger yields a larger number; to keep the increase as small as possible we choose the *rightmost* such digit (smallest place value).

3. **Successor choice is minimal.**  Among the digits to the right, picking the *smallest digit greater than the pivot* ensures we step up by the smallest possible amount at that position.

4. **Reversing suffix minimises remainder.**  After we swap, the suffix is still in descending order.  Reversing it makes it ascending, i.e. the **smallest** arrangement of those digits—thereby giving the overall smallest number greater than the original.

Together these four facts guarantee we get the **next** permutation in lexicographic order, not just some greater permutation, and hence the next greater integer for LC556.

---

## Complexity Analysis
* **Time:** `O(d)` where `d` is number of digits (≤10). All loops are linear.
* **Space:** `O(d)` for char array (can be `O(1)` if we operate on char array in-place after `String.valueOf`).

---

## Pattern Parallel
This is still a “next greater” question but the monotonic-stack isn’t required. Instead we use the universal *next permutation* template—pivot, successor, reverse.  Same three-step technique is re-usable for problems like LC31 and generating next permutation of strings.