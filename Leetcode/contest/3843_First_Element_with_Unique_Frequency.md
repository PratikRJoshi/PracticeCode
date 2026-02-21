# First Element with Unique Frequency

## Problem Description

**Problem Link:** [First Element with Unique Frequency](https://leetcode.com/problems/first-element-with-unique-frequency/)

You are given an integer array `nums`.

Return the **first element** (scanning from left to right) whose **frequency is unique**.

A frequency is unique if **no other number** in the array appears exactly the same number of times.

If no such element exists, return `-1`.

**Example 1:**
```
Input: nums = [20,10,30,30]
Output: 30
Explanation:
- 20 appears 1 time
- 10 appears 1 time
- 30 appears 2 times
Only frequency 2 is unique, so the first element with unique frequency is 30.
```

**Example 2:**
```
Input: nums = [20,20,10,30,30,30]
Output: 20
Explanation:
- 20 appears 2 times
- 10 appears 1 time
- 30 appears 3 times
All frequencies are unique; scanning from left, the first element is 20.
```

**Example 3:**
```
Input: nums = [10,10,20,20]
Output: -1
Explanation:
- 10 appears 2 times
- 20 appears 2 times
Frequency 2 is not unique.
```

**Constraints:**

- `1 <= nums.length <= 10^5`
- `1 <= nums[i] <= 10^5`

## Intuition/Main Idea

### Build the intuition (mentor-style)

We need two pieces of information:

1. For each value `x`, how many times does it appear? (its frequency)
2. For each frequency `f`, how many different values have that frequency?

Once we know these:

- An element `x` has a **unique frequency** iff `frequencyCount[freq[x]] == 1`.

Then we just scan the array from left to right and return the first number that satisfies that condition.

### Why the intuition works

The problem is asking for uniqueness of the **frequency**, not uniqueness of the element.

So we must compare a number’s frequency with frequencies of other numbers. That is exactly what the second map (`frequencyCount`) captures.

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|------------------------|-----------------------------------|
| Compute each number’s frequency | Build `valueToFrequency` map (lines 14-20) |
| Determine whether a frequency is unique | Build `frequencyToCount` map (lines 22-28) |
| Return first element whose frequency is unique | Second pass through `nums` (lines 30-38) |

## Final Java Code & Learning Pattern (Full Content)

```java
import java.util.HashMap;
import java.util.Map;

class Solution {
    public int firstUniqueFrequency(int[] nums) {
        // valueToFrequency tracks how many times each value appears.
        Map<Integer, Integer> valueToFrequency = new HashMap<>();
        for (int value : nums) {
            valueToFrequency.put(value, valueToFrequency.getOrDefault(value, 0) + 1);
        }

        // frequencyToCount tells us how many distinct values share the same frequency.
        // If frequencyToCount[f] == 1, that frequency is unique.
        Map<Integer, Integer> frequencyToCount = new HashMap<>();
        for (int frequency : valueToFrequency.values()) {
            frequencyToCount.put(frequency, frequencyToCount.getOrDefault(frequency, 0) + 1);
        }

        // Scan from left to right and return the first element whose frequency is unique.
        for (int value : nums) {
            int frequency = valueToFrequency.get(value);
            if (frequencyToCount.get(frequency) == 1) {
                return value;
            }
        }

        return -1;
    }
}
```

## Complexity Analysis

**Time Complexity:** $O(n)$

- One pass to count values
- One pass over distinct values to count frequencies
- One pass to find the first valid element

**Space Complexity:** $O(n)$

- Hash maps store up to `n` distinct values in the worst case.

## Similar Problems

- [First Unique Character in a String](https://leetcode.com/problems/first-unique-character-in-a-string/) - similar “first by scanning order” idea
- [Unique Number of Occurrences](https://leetcode.com/problems/unique-number-of-occurrences/) - uniqueness of frequencies (but not “first element”)
