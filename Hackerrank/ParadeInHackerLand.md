# Parade In HackerLand

## 1) Problem Description

Given binary string `color`:
- `'0'` = red
- `'1'` = blue

Every second, all `"01"` substrings are replaced simultaneously with `"10"`.

Find how many seconds until no `"01"` exists.

Example:

```text
color = "001011"
answer = 4
```

Constraints:
- `1 <= |color| <= 3 * 10^5`

## 2) Intuition/Main Idea

Direct simulation each second can be too slow.

Think of each `'1'` moving left across zeros.

When scanning left to right:
- `zeroCount` = number of zeros seen so far
- for each `'1'`, it needs to cross `zeroCount` zeros
- but it may also need to wait because earlier ones are also moving

Use recurrence:

```text
timeForCurrentOne = max(timeForPreviousOne + 1, zeroCount)
```

### Why this intuition works

- `zeroCount` is minimum time needed if no blocking by earlier ones.
- `timeForPreviousOne + 1` handles congestion: two ones cannot occupy same spot progression in same second.
- Maximum of both gives true finish time of this `'1'`.

Final answer is finish time of the last `'1'` that has zeros before it.

### How to derive it step by step

1. Initialize `zeroCount = 0`, `seconds = 0`.
2. For each char:
   - if `'0'`, increment `zeroCount`.
   - if `'1'` and `zeroCount > 0`, update `seconds = max(seconds + 1, zeroCount)`.
3. Return `seconds`.

## 3) Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
| --- | --- |
| @Simultaneous01To10Transitions | recurrence captures parallel movement without full simulation |
| @NeedTimeUntilNo01Left | `seconds` stores maximum stabilization time |
| @LargeStringUpTo3e5 | single linear scan |

## 4) Final Java Code & Learning Pattern (Full Content)

```java
import java.util.*;

class Result {

    public static int getSwapTime(String color) {
        int zeroCount = 0;
        int seconds = 0;

        for (int index = 0; index < color.length(); index++) {
            char current = color.charAt(index);

            if (current == '0') {
                zeroCount++;
            } else if (zeroCount > 0) {
                seconds = Math.max(seconds + 1, zeroCount);
            }
        }

        return seconds;
    }
}
```

Learning Pattern:
- Some "parallel swap" simulations can be solved by tracking per-item finish times instead of mutating strings repeatedly.
- Look for queue/congestion style recurrences in movement problems.

## 5) Complexity Analysis

- Time Complexity: $O(n)$
- Space Complexity: $O(1)$

## Similar Problems

- [LeetCode 2380: Time Needed to Rearrange a Binary String](https://leetcode.com/problems/time-needed-to-rearrange-a-binary-string/) (same problem pattern)