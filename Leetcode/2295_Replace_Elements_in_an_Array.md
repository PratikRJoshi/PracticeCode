# 2295. Replace Elements in an Array

## Problem Description

[Replace Elements in an Array](https://leetcode.com/problems/replace-elements-in-an-array/)

You are given a 0-indexed array `nums` of **distinct** integers and a 2D array `operations`, where `operations[i] = [a, b]` means "replace the value `a` in `nums` with `b`." It is guaranteed that at the time of each operation, `a` exists in `nums` and `b` does not. Return the array after all operations.

### Example 1

Input: `nums = [1,2,4,6]`, `operations = [[1,3],[4,7],[6,1]]`

Output: `[3,2,7,1]`

### Example 2

Input: `nums = [1,2]`, `operations = [[1,3],[2,1],[3,2]]`

Output: `[2,1]`

### Constraints

- `1 <= n, m <= 10^5`, all values distinct (and stay distinct after each op).

## Intuition / Main Idea

The only hard part is **finding** the value `a` to replace. Scanning the array each time is O(n·m) ≈ 10¹⁰ → TLE. A hash map `value → index` makes each locate O(1), and we mutate `nums` in place.

### Build the intuition step by step

1. Build `map: value → index` from the initial array.
2. For each op `[a, b]`: `idx = map.get(a)`, set `nums[idx] = b`, and record `map.put(b, idx)`.
3. Keeping the map current matters: a later op might use this `b` as *its* `a`.
4. After the loop, `nums` already holds the answer — just return it.

### Why this works

The map always reflects where each *live* value sits. Because values stay distinct, `map.get(a)` is unambiguous. Mutating `nums` directly avoids any rebuild step (and its stale-key pitfall).

## Code Mapping

| Problem Requirement (@) | Java Code Section |
|---|---|
| O(1) locate of value `a` | `int index = map.get(oldValue);` |
| Apply replacement | `nums[index] = newValue;` |
| Keep map usable for future ops | `map.put(newValue, index);` |
| Final array | `return nums;` |

## Final Java Code & Learning Pattern

```java
// [Pattern: HashMap value->index for O(1) locate-and-mutate]
import java.util.HashMap;
import java.util.Map;

class Solution {
    public int[] arrayChange(int[] nums, int[][] operations) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            map.put(nums[i], i);
        }

        for (int[] operation : operations) {
            int oldValue = operation[0];
            int newValue = operation[1];

            int index = map.get(oldValue);
            nums[index] = newValue;        // mutate in place
            map.put(newValue, index);      // future ops may reference newValue
        }

        return nums;
    }
}
```

### Why each part exists

- **Initial map** — one O(n) pass so every value's position is known in O(1).
- **`map.put(newValue, index)`** — without it, a later op using `newValue` as its target would fail `map.get`.
- **In-place mutation + `return nums`** — the array is the source of truth; rebuilding from the map risks stale-key collisions (`a` and `b` both pointing at the same index).

## Complexity Analysis

- **Time Complexity:** $O(n + m)$ — O(n) to build the map, O(1) per operation across m ops.
- **Space Complexity:** $O(n)$ for the map.

## Similar Problems

1. [LeetCode 1. Two Sum](https://leetcode.com/problems/two-sum/) — HashMap value→index for O(1) complement lookup.
2. [LeetCode 380. Insert Delete GetRandom O(1)](https://leetcode.com/problems/insert-delete-getrandom-o1/) — value→index map enabling O(1) removal via swap-with-last.
3. [LeetCode 2102. Sequentially Ordinal Rank Tracker](https://leetcode.com/problems/sequentially-ordinal-rank-tracker/) — maintaining a live structure under streaming operations.
