# Is Number Present

## 1) Problem Description

Given `num[]`, for each index `i` produce:

- String 1 bit = `1` if `num[i]` appears in `num[0..i-1]`, else `0`
- String 2 bit = `1` if `num[i]` appears in `num[i+1..n-1]`, else `0`

Return `[string1, string2]`.

Example:

```text
num = [1, 2, 3, 2, 1]
output = ["00011", "11000"]
```

## 2) Intuition/Main Idea

For each index we need membership on left side and right side.

Use two frequency maps:
- `leftSeenCount` for processed prefix
- `rightRemainingCount` initialized with whole array and reduced as we move

### Why this intuition works

- Before processing index `i`, `leftSeenCount` exactly represents elements left of `i`.
- After decreasing current element from `rightRemainingCount`, it represents elements strictly right of `i`.
- So each bit is one map lookup.

### How to derive it step by step

1. Build `rightRemainingCount` from full array.
2. For each index from left to right:
   - Remove current value from right map.
   - `string1` bit = whether current value exists in left map.
   - `string2` bit = whether current value still exists in right map.
   - Add current value into left map.

## 3) Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
| --- | --- |
| @AppearsEarlierBit | `leftSeenCount.getOrDefault(value, 0) > 0 ? '1' : '0'` |
| @AppearsLaterBit | `rightRemainingCount.getOrDefault(value, 0) > 0 ? '1' : '0'` |
| @TwoOutputStrings | `StringBuilder earlierBits`, `StringBuilder laterBits` |
| @EfficientPerIndexCheck | hash map counts updated once per index |

## 4) Final Java Code & Learning Pattern (Full Content)

```java
import java.util.*;

class Result {

    public static List<String> isNumberPresent(List<Integer> num) {
        Map<Integer, Integer> rightRemainingCount = new HashMap<>();
        for (int value : num) {
            rightRemainingCount.merge(value, 1, Integer::sum);
        }

        Map<Integer, Integer> leftSeenCount = new HashMap<>();
        StringBuilder earlierBits = new StringBuilder();
        StringBuilder laterBits = new StringBuilder();

        for (int value : num) {
            int rightCountBeforeRemovingCurrent = rightRemainingCount.get(value);
            if (rightCountBeforeRemovingCurrent == 1) {
                rightRemainingCount.remove(value);
            } else {
                rightRemainingCount.put(value, rightCountBeforeRemovingCurrent - 1);
            }

            earlierBits.append(leftSeenCount.getOrDefault(value, 0) > 0 ? '1' : '0');
            laterBits.append(rightRemainingCount.getOrDefault(value, 0) > 0 ? '1' : '0');

            leftSeenCount.merge(value, 1, Integer::sum);
        }

        return Arrays.asList(earlierBits.toString(), laterBits.toString());
    }
}
```

Learning Pattern:
- If each index asks about "left side" and "right side", maintain rolling prefix/suffix state.
- Prefix/suffix frequency maps often convert quadratic checks to linear time.

## 5) Complexity Analysis

- Time Complexity: $O(n)$ average
- Space Complexity: $O(u)$ where `u` is distinct values

## Similar Problems

- [LeetCode 238: Product of Array Except Self](https://leetcode.com/problems/product-of-array-except-self/) (prefix/suffix thinking)
- [LeetCode 2670: Find the Distinct Difference Array](https://leetcode.com/problems/find-the-distinct-difference-array/) (left-right set/frequency tracking)