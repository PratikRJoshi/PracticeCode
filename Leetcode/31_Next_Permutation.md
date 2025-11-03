### LeetCode 31: Next Permutation
Problem · https://leetcode.com/problems/next-permutation/

---

## Problem Statement
Given an integer array `nums` representing a permutation, **re-arrange** it *in-place* into the next lexicographically greater permutation. 
If no such permutation exists (i.e. the array is in descending order) you must rearrange it into the **lowest possible order** (ascending).

---

## Intuition & Algorithm with Running Example

Below is the classic “next permutation” recipe explained **step-by-step** on the sequence
```
0 1 2 5 3 3 0
```
The guiding principle is: *make the smallest possible increase* in lexicographic order, which means modifying the array as far to the **right** as possible.

### 1  Find the longest non-increasing suffix
Scan from the end until you meet the first ascent:
```
index : 0 1 2 3 4 5 6
value : 0 1 2 5 3 3 0
                        ↑ suffix (5 3 3 0) is already maximal
```
This suffix is already the **largest** permutation of its digits, so changing any of them cannot yield the *next* permutation—we must bump something **before** it.  (If the whole array is non-increasing there is no larger permutation; we simply reverse it to the lowest order.)

### 2  Identify the pivot (element just before the suffix)
Here the pivot is `2` at index 2.

### 3  Choose the *smallest* digit in the suffix that is **greater** than the pivot
Starting from the right guarantees we pick the right-most `>` pivot, which is the minimal successor.
```
Suffix digits greater than 2 → 5,3,3 → pick the right-most 3 (index 5)
```
Swap pivot and successor:
```
0 1 3 5 3 2 0   (prefix has increased by the smallest step)
```

### 4  Reverse the suffix to minimal order
The suffix after the swap `(5 3 2 0)` is still in descending order.  Reversing it produces the **lowest** arrangement of those digits:
```
0 1 3 0 2 3 5   ← next permutation
```

The same four operations always transform a permutation to the next lexicographic one.

---

## Concise “Why it Works” (Quick Recap)
1. **Find the longest non-increasing suffix.**  It is already the largest ordering of those digits, so any next permutation must change a digit before it.
2. **Pivot = right-most ascent.**  Swapping a more significant digit would overshoot; picking the right-most pivot yields the smallest possible jump.
3. **Successor = smallest digit in the suffix that is > pivot.**  Guarantees we increase the pivot position by the minimum amount.
4. **Reverse the suffix.**  After swapping, the suffix is still in descending order.  Reversing puts it in ascending order, the minimal tail to pair with the new prefix—so the overall array is the next permutation.

---

## Condensed algorithm** (matches the code):
1. Find largest index `i` such that `nums[i-1] < nums[i]`.   If none → reverse whole array.
2. Find largest `j ≥ i` with `nums[j] > nums[i-1]`.
3. Swap `nums[i-1]` and `nums[j]`.
4. Reverse the suffix starting at `i`.

---

## Mapping requirements → code
| Requirement | Lines in code |
|-------------|---------------|
| Find pivot `i` (`nums[i]<nums[i+1]`) | 15-17 |
| If none → sort ascending (reverse entire) | 18-20 |
| Find rightmost successor `j` (`> nums[i]`) | 22-24 |
| Swap pivot / successor | 25 |
| Reverse suffix `i+1 … end` | 26-29 |

---

## Java Implementation (mirrors LC556 digits version)
```java
class Solution {
    public void nextPermutation(int[] nums) {
        int n = nums.length;
        // 1) Find pivot
        int i = n - 2;
        while (i >= 0 && nums[i] >= nums[i + 1]) i--;

        if (i >= 0) {
            // 2) Find rightmost successor
            int j = n - 1;
            while (nums[j] <= nums[i]) j--;
            // 3) Swap pivot & successor
            swap(nums, i, j);
        }
        // 4) Reverse suffix (if i<0 entire array gets reversed to ascending)
        reverse(nums, i + 1, n - 1);
    }

    private void swap(int[] a, int i, int j) {
        int tmp = a[i]; a[i] = a[j]; a[j] = tmp;
    }
    private void reverse(int[] a, int l, int r) {
        while (l < r) swap(a, l++, r--);
    }
}
```

---

## Complexity
Time `O(n)` – single passes to find pivot, successor, and reverse. Space `O(1)` in-place.

---

### Re-using the Pattern
LC556 is **Next Greater Integer** using the *same* three-step algorithm on a char-array of digits plus a 32-bit overflow check.  LC31 is the generic permutation form; hence the two solutions are nearly identical line-for-line.