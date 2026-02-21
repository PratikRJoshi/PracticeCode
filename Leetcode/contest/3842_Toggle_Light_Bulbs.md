# Toggle Light Bulbs

## Problem Description

**Problem Link:** [Toggle Light Bulbs](https://leetcode.com/problems/toggle-light-bulbs/)

There are 100 light bulbs numbered from `1` to `100`. All of them are initially **off**.

You are given an integer array `bulbs`.

For each value `bulbs[i]`:

- If the `bulbs[i]`-th bulb is **off**, switch it **on**.
- Otherwise, switch it **off**.

Return a list of integers representing the bulb numbers that are **on** at the end, sorted in ascending order. If no bulb is on, return an empty list.

**Example 1:**
```
Input: bulbs = [10,30,20,10]
Output: [20,30]
Explanation:
- Toggle 10: on
- Toggle 30: on
- Toggle 20: on
- Toggle 10 again: off
Final on bulbs: 20, 30
```

**Example 2:**
```
Input: bulbs = [100,100]
Output: []
Explanation:
- Toggle 100: on
- Toggle 100 again: off
Final on bulbs: (none)
```

**Constraints:**

- `1 <= bulbs.length <= 100`
- `1 <= bulbs[i] <= 100`

## Intuition/Main Idea

### Build the intuition (mentor-style)

Every time we see a bulb number `x`, we **flip** its state.

That means the only thing that matters is:

- **How many times did we toggle bulb `x`?**

If a bulb is toggled:

- an **odd** number of times, it ends **on**
- an **even** number of times, it ends **off** (because flips cancel in pairs)

So the problem reduces to counting toggles for each bulb `1..100` and returning the ones with odd counts.

### Why the intuition works

A flip is its own inverse:

- Off -> On
- On -> Off

So toggling twice returns the bulb to its original state. Therefore, only the **parity** (odd/even) of the number of toggles changes the final state.

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|------------------------|-----------------------------------|
| Toggle bulb state for each `bulbs[i]` | Use boolean array and flip `isOn[bulb]` (lines 16-22) |
| Bulbs are numbered 1..100 | Allocate size `101` so we can index directly by bulb number (line 14) |
| Return final on bulbs sorted | Scan from 1 to 100 and collect those on (lines 24-31) |

## Final Java Code & Learning Pattern (Full Content)

```java
import java.util.ArrayList;
import java.util.List;

class Solution {
    public List<Integer> toggleBulbs(int[] bulbs) {
        // We have exactly 100 bulbs numbered 1..100.
        // We allocate size 101 so that index == bulbNumber is a direct mapping,
        // and index 0 is unused.
        boolean[] isOn = new boolean[101];

        // Each occurrence of bulbNumber flips its state.
        for (int bulbNumber : bulbs) {
            // If it was off -> becomes on; if it was on -> becomes off.
            isOn[bulbNumber] = !isOn[bulbNumber];
        }

        // Collect all bulbs that are on, in ascending order.
        // Scanning from 1..100 guarantees sorted output.
        List<Integer> result = new ArrayList<>();
        for (int bulbNumber = 1; bulbNumber <= 100; bulbNumber++) {
            if (isOn[bulbNumber]) {
                result.add(bulbNumber);
            }
        }

        return result;
    }
}
```

## Complexity Analysis

**Time Complexity:** $O(n + 100)$ which is effectively $O(n)$

- One pass to toggle `n = bulbs.length` elements
- One pass over bulbs `1..100` to build the answer

**Space Complexity:** $O(1)$

- The boolean array size is fixed at 101.

## Similar Problems

- [Bulb Switcher](https://leetcode.com/problems/bulb-switcher/) - bulb toggling / parity pattern
- [Find the XOR of Numbers Which Appear Twice](https://leetcode.com/problems/find-the-xor-of-numbers-which-appear-twice/) - parity of occurrences idea
