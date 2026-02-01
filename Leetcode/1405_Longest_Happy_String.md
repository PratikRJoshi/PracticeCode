# 1405. Longest Happy String

[LeetCode Link](https://leetcode.com/problems/longest-happy-string/)

## Problem Description
You are given three integers `a`, `b`, and `c`.

You need to return the **longest possible string** that:

- consists only of the characters `'a'`, `'b'`, and `'c'`
- uses **at most** `a` occurrences of `'a'`, **at most** `b` occurrences of `'b'`, and **at most** `c` occurrences of `'c'`
- is a **happy string**, meaning it does **not** contain `"aaa"`, `"bbb"`, or `"ccc"` as a substring

If there are multiple answers, return any of them.

### Examples

#### Example 1
- Input: `a = 1, b = 1, c = 7`
- Output: `"ccaccbcc"`
- Explanation: It uses at most 1 `'a'`, at most 1 `'b'`, and at most 7 `'c'`, and never has 3 of the same in a row.

#### Example 2
- Input: `a = 7, b = 1, c = 0`
- Output: `"aabaa"`

---

## Intuition/Main Idea
We want the longest string, so we should be greedy: always try to place the character that still has the **most remaining count**.

But we must respect the happy-string constraint: we can’t append a character if the last two characters are already that same character (otherwise we would create `"xxx"`).

Greedy strategy:

- Keep the remaining counts of `'a'`, `'b'`, `'c'` in a max-heap (priority queue) so we can always pick the most available character.
- At each step, try to append the most frequent character.
  - If appending it would create three-in-a-row, take the second best character instead.
  - If even the second best doesn’t exist, we must stop (no valid extension possible).

This works because:

- If the best character is allowed, using it is always safe for maximizing length.
- If the best is disallowed due to the last two characters, the only way to continue is to temporarily use another character to break the streak.

---

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|---|---|
| Build the longest possible string | Greedy loop that appends one character per iteration until stuck |
| Use at most `a`, `b`, `c` occurrences | Decrement remaining count in `remainingByLetter` and reinsert into heap if still > 0 |
| Must not contain "aaa", "bbb", "ccc" | Before appending, check last two chars of `result` and possibly pick second-best |
| Only characters `'a'`, `'b'`, `'c'` | Heap only stores entries for these three letters |

---

## Final Java Code & Learning Pattern (Full Content)
```java
import java.util.*;

class Solution {
    public String longestDiverseString(int a, int b, int c) {
        Map<Character, Integer> remainingByLetter = new HashMap<>();
        if (a > 0) remainingByLetter.put('a', a);
        if (b > 0) remainingByLetter.put('b', b);
        if (c > 0) remainingByLetter.put('c', c);

        PriorityQueue<Character> maxHeap = new PriorityQueue<>(
                (letter1, letter2) -> Integer.compare(
                        remainingByLetter.get(letter2),
                        remainingByLetter.get(letter1)
                )
        );

        maxHeap.addAll(remainingByLetter.keySet());

        StringBuilder result = new StringBuilder();

        while (!maxHeap.isEmpty()) {
            char mostAvailableLetter = maxHeap.poll();

            // If adding this letter creates "xxx", we must use another letter (if possible).
            int currentLength = result.length();
            boolean wouldCreateTriple = currentLength >= 2
                    && result.charAt(currentLength - 1) == mostAvailableLetter
                    && result.charAt(currentLength - 2) == mostAvailableLetter;

            if (wouldCreateTriple) {
                if (maxHeap.isEmpty()) {
                    // No alternative letter to break the streak, so we cannot extend further.
                    break;
                }

                char secondMostAvailableLetter = maxHeap.poll();

                // Use the second best to break the streak.
                result.append(secondMostAvailableLetter);
                remainingByLetter.put(
                        secondMostAvailableLetter,
                        remainingByLetter.get(secondMostAvailableLetter) - 1
                );

                // Put the best one back since we didn't use it this round.
                maxHeap.offer(mostAvailableLetter);

                // If the second still has remaining count, put it back as well.
                if (remainingByLetter.get(secondMostAvailableLetter) > 0) {
                    maxHeap.offer(secondMostAvailableLetter);
                } else {
                    remainingByLetter.remove(secondMostAvailableLetter);
                }
            } else {
                // Safe to use the most frequent letter.
                result.append(mostAvailableLetter);
                remainingByLetter.put(
                        mostAvailableLetter,
                        remainingByLetter.get(mostAvailableLetter) - 1
                );

                if (remainingByLetter.get(mostAvailableLetter) > 0) {
                    maxHeap.offer(mostAvailableLetter);
                } else {
                    remainingByLetter.remove(mostAvailableLetter);
                }
            }
        }

        return result.toString();
    }
}
```

### Learning Pattern
- Greedy + max-heap is a good fit when:
  - you always want to pick the currently “best” remaining choice
  - but you must respect a local constraint (here: no 3 in a row)
- Memory hook:
  - pop best
  - if it violates constraint, pop second best, append it, push best back
  - decrement counts in `remainingByLetter` and reinsert letters into the heap while their remaining count is > 0

---

## Complexity Analysis
- Time Complexity: $O(L \log 3)$ where `L` is the length of the output string
  - each appended character does a constant number of heap operations
  - since the heap size is at most 3, this is effectively $O(L)$
- Space Complexity: $O(1)$ extra
  - heap contains at most 3 elements

---

## Similar Problems
- [767. Reorganize String](https://leetcode.com/problems/reorganize-string/) (greedy + max heap with adjacency constraints)
- [621. Task Scheduler](https://leetcode.com/problems/task-scheduler/) (greedy scheduling with constraints)
- [358. Rearrange String k Distance Apart](https://leetcode.com/problems/rearrange-string-k-distance-apart/) (greedy + heap)
