# Array Reduction

## 1) Problem Description

Array is balanced if:

```text
maxValue <= 2 * minValue
```

Allowed operations:
- remove any number of elements
- change at most one element to any positive integer

Return minimum removals to make array balanced.

## 2) Intuition/Main Idea

First ignore the one-change operation.

After sorting, a balanced kept subset must form a window where:

```text
sorted[right] <= 2 * sorted[left]
```

Let `bestWindow` be largest such window size.

Now use the one allowed change:
- we can take one element outside this best window and change it to any value inside the window.
- so we can keep at most `bestWindow + 1` elements (capped by `n`).

### Why this intuition works

- Any unchanged kept elements must already satisfy balance together.
- Only one outlier can be repaired by one change.
- Therefore optimal keep count = best unchanged balanced count + up to one repaired element.

### How to derive it step by step

1. Sort array.
2. Use two pointers to find largest balanced window.
3. `maxKeep = min(n, bestWindow + 1)`.
4. `answer = n - maxKeep`.

## 3) Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
| --- | --- |
| @BalancedCondition | while condition `sorted[right] > 2 * sorted[left]` |
| @AtMostOneChangeAllowed | `maxKeep = Math.min(n, bestWindow + 1)` |
| @MinimumRemovals | return `n - maxKeep` |
| @LargeInput2e5 | sort + linear sliding window |

## 4) Final Java Code & Learning Pattern (Full Content)

```java
import java.util.*;

class Result {

    public static int minimumRemovals(List<Integer> arr) {
        int n = arr.size();
        List<Integer> sorted = new ArrayList<>(arr);
        Collections.sort(sorted);

        int left = 0;
        int bestWindow = 0;

        for (int right = 0; right < n; right++) {
            while ((long) sorted.get(right) > 2L * sorted.get(left)) {
                left++;
            }
            bestWindow = Math.max(bestWindow, right - left + 1);
        }

        int maxKeep = Math.min(n, bestWindow + 1);
        return n - maxKeep;
    }
}
```

Learning Pattern:
- When constraint is ratio/range based, sorting often converts subset validity into sliding-window validity.
- If one repair operation exists, solve base case first then add one-unit improvement.

## 5) Complexity Analysis

- Time Complexity: $O(n \log n)$
- Space Complexity: $O(n)$

## Similar Problems

- [LeetCode 1838: Frequency of the Most Frequent Element](https://leetcode.com/problems/frequency-of-the-most-frequent-element/) (maximize kept window under constraint)
- [LeetCode 2009: Minimum Number of Operations to Make Array Continuous](https://leetcode.com/problems/minimum-number-of-operations-to-make-array-continuous/) (sort + sliding window + minimal changes)