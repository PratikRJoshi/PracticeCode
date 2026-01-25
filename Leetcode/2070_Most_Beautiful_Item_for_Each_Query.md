# Most Beautiful Item for Each Query

## Problem Description

**Problem Link:** [Most Beautiful Item for Each Query](https://leetcode.com/problems/most-beautiful-item-for-each-query/)

You are given a 2D integer array `items` where `items[i] = [pricei, beautyi]` describes an item with price `pricei` and beauty `beautyi`.

You are also given an integer array `queries`.

For each query `queries[j]`, determine the **maximum beauty** among all items with `price <= queries[j]`. If no such item exists, return `0` for that query.

Return an array `answer` where `answer[j]` is the result for `queries[j]`.

**Example 1:**
```
Input: items = [[1,2],[3,2],[2,4],[5,6],[3,5]], queries = [1,2,3,4,5,6]
Output: [2,4,5,5,6,6]
Explanation:
- query=1 -> prices<=1 => max beauty=2
- query=2 -> prices<=2 => max beauty=4
- query=3 -> prices<=3 => max beauty=5
- query=4 -> prices<=4 => max beauty=5
- query=5 -> prices<=5 => max beauty=6
- query=6 -> prices<=6 => max beauty=6
```

**Example 2:**
```
Input: items = [[1,2],[1,2],[1,3],[1,4]], queries = [1]
Output: [4]
```

**Constraints:**
- `1 <= items.length, queries.length <= 10^5`
- `items[i].length == 2`
- `1 <= pricei, beautyi <= 10^9`
- `1 <= queries[j] <= 10^9`

## Intuition/Main Idea

For each query `q`, we need:

- `max(beauty)` among items with `price <= q`

A direct scan per query would be `O(n * m)` and too slow.

Key idea:

- Sort `items` by `price`.
- Sort `queries` (but keep original indices).
- Sweep through items in increasing price while processing queries in increasing value.

While sweeping:

- Maintain `bestBeautySoFar` = maximum beauty among all items we have passed (i.e., `price <= currentQuery`).
- When we move from one query to the next (non-decreasing), we only need to advance the item pointer further; we never move backward.

This turns the problem into:

- `sort + two pointers / sweep line`

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|-------------------------|------------------------------------|
| For each query, return max beauty among items with `price <= query` | Sorting + sweep (`while (i < n && items[i][0] <= qVal)`) and storing `ans[qIdx] = bestBeautySoFar` |
| Preserve original query order after sorting queries | `int[][] q = new int[m][2]` storing `[queryValue, originalIndex]`, and assign into `ans[originalIndex]` |
| Handle case where no item is affordable | Initialize `bestBeautySoFar = 0`, so result stays `0` when nothing is added |
| Must handle up to `10^5` items/queries efficiently | `O(n log n + m log m)` sorting and linear sweep |

## Final Java Code & Learning Pattern (Full Content)

```java
import java.util.*;

class Solution {
    public int[] maximumBeauty(int[][] items, int[] queries) {
        // Sort items by price ascending.
        Arrays.sort(items, (a, b) -> Integer.compare(a[0], b[0]));

        // Store queries along with original indices so we can restore order.
        int m = queries.length;
        int[][] q = new int[m][2];
        for (int idx = 0; idx < m; idx++) {
            q[idx][0] = queries[idx];
            q[idx][1] = idx;
        }

        // Sort queries by query value ascending.
        Arrays.sort(q, (a, b) -> Integer.compare(a[0], b[0]));

        int[] ans = new int[m];

        // Sweep pointer over items.
        int i = 0;
        int bestBeautySoFar = 0;

        // Process queries in increasing order.
        for (int qi = 0; qi < m; qi++) {
            int qVal = q[qi][0];
            int originalIndex = q[qi][1];

            // Add all items whose price <= current query value.
            // For those items, keep track of the maximum beauty seen so far.
            while (i < items.length && items[i][0] <= qVal) {
                bestBeautySoFar = Math.max(bestBeautySoFar, items[i][1]);
                i++;
            }

            // bestBeautySoFar now equals the maximum beauty among all items with price <= qVal.
            // If there were no such items, it remains 0.
            ans[originalIndex] = bestBeautySoFar;
        }

        return ans;
    }
}
```

## Complexity Analysis

- **Time Complexity:** $O(n \log n + m \log m)$ where `n = items.length` and `m = queries.length`.
  - Sort `items`: `O(n log n)`
  - Sort `queries` with indices: `O(m log m)`
  - Single sweep: `O(n + m)`
- **Space Complexity:** $O(m)$ for storing queries with original indices and the answer array.

## Similar Problems

- [1283. Find the Smallest Divisor Given a Threshold](https://leetcode.com/problems/find-the-smallest-divisor-given-a-threshold/) - answer-space pattern, different but also transforms query into monotonic condition
- [2300. Successful Pairs of Spells and Potions](https://leetcode.com/problems/successful-pairs-of-spells-and-potions/) - sort + per-query searching with maintained ordering
- [35. Search Insert Position](https://leetcode.com/problems/search-insert-position/) - lower-bound style query on sorted data
