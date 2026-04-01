# Salesforce Case Management

## 1) Problem Description

Given `priorities[]`, sort all values by:
1. increasing frequency
2. increasing value (if frequencies tie)

Return resulting expanded list.

Example:

```text
priorities = [6, 5, 9, 5, 6, 7]
frequencies: 7->1, 9->1, 5->2, 6->2
answer = [7, 9, 5, 5, 6, 6]
```

## 2) Intuition/Main Idea

This is a frequency sorting problem.

### Why this intuition works

- Sorting rule depends on value frequency and value itself.
- So first count frequencies, then sort unique values by comparator `(freq, value)`.
- Finally expand each value by its frequency.

### How to derive it step by step

1. Build frequency map.
2. Get distinct values list.
3. Sort with custom comparator:
   - frequency ascending
   - value ascending
4. Reconstruct output by repeating each value frequency times.

## 3) Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
| --- | --- |
| @SortByFrequencyAscending | comparator uses `frequencyByValue.get(a)` vs `get(b)` |
| @TieBreakByValueAscending | comparator tie uses `Integer.compare(a, b)` |
| @ReturnExpandedSortedList | loop adds each value `frequency` times |
| @LargeNUpTo2e5 | hash map + sorting distinct keys |

## 4) Final Java Code & Learning Pattern (Full Content)

```java
import java.util.*;

class Result {

    public static List<Integer> getSortedCases(List<Integer> priorities) {
        Map<Integer, Integer> frequencyByValue = new HashMap<>();
        for (int value : priorities) {
            frequencyByValue.merge(value, 1, Integer::sum);
        }

        List<Integer> distinctValues = new ArrayList<>(frequencyByValue.keySet());
        distinctValues.sort((leftValue, rightValue) -> {
            int leftFrequency = frequencyByValue.get(leftValue);
            int rightFrequency = frequencyByValue.get(rightValue);

            if (leftFrequency != rightFrequency) {
                return Integer.compare(leftFrequency, rightFrequency);
            }
            return Integer.compare(leftValue, rightValue);
        });

        List<Integer> answer = new ArrayList<>(priorities.size());
        for (int value : distinctValues) {
            int repeat = frequencyByValue.get(value);
            for (int count = 0; count < repeat; count++) {
                answer.add(value);
            }
        }

        return answer;
    }
}
```

Learning Pattern:
- For custom ordering based on counts, separate problem into: count → sort keys → expand.

## 5) Complexity Analysis

- Let `n` = total items, `u` = distinct values.
- Time Complexity: $O(n + u \log u)$
- Space Complexity: $O(u)$ (excluding output)

## Similar Problems

- [LeetCode 1636: Sort Array by Increasing Frequency](https://leetcode.com/problems/sort-array-by-increasing-frequency/)
- [LeetCode 451: Sort Characters By Frequency](https://leetcode.com/problems/sort-characters-by-frequency/) (frequency ordering pattern)