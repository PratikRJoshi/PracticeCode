# Compression

## 1) Problem Description

Given a lowercase string `message`, compress consecutive repeating characters:

- If a character appears once in a run, append only the character.
- If it appears more than once consecutively, append character followed by run length.

Example:

```text
message = "aabbccca"
output  = "a2b2c3a"
```

Constraints:
- `message` contains only `a-z`
- `1 <= message.length <= 10^5`

## 2) Intuition/Main Idea

This is classic run-length encoding.

### Why this intuition works

- Compression is defined exactly by contiguous runs.
- A single left-to-right pass can detect each run and emit its compressed representation.

### How to derive it step by step

1. Start from index `0`.
2. Count how many same characters continue from current index.
3. Append character.
4. Append count only if count > 1.
5. Jump to next run.

## 3) Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
| --- | --- |
| @ConsecutiveGrouping | inner while loop extends run while characters are equal |
| @SingleCharRule | append only character when `runLength == 1` |
| @RepeatedCharRule | append `runLength` when `runLength > 1` |
| @LinearScanForLargeInput | two-pointer style single pass over string |

## 4) Final Java Code & Learning Pattern (Full Content)

```java
import java.util.*;

class Result {

    public static String compress(String message) {
        if (message == null || message.isEmpty()) {
            return "";
        }

        StringBuilder compressed = new StringBuilder();
        int length = message.length();
        int index = 0;

        while (index < length) {
            char currentCharacter = message.charAt(index);
            int runLength = 0;

            while (index < length && message.charAt(index) == currentCharacter) {
                runLength++;
                index++;
            }

            compressed.append(currentCharacter);
            if (runLength > 1) {
                compressed.append(runLength);
            }
        }

        return compressed.toString();
    }
}
```

Learning Pattern:
- When problem talks about contiguous repeats, think "run detection" with two pointers.
- Emit result once per run, not once per character.

## 5) Complexity Analysis

- Time Complexity: $O(n)$
- Space Complexity: $O(n)$ for output

## Similar Problems

- [LeetCode 443: String Compression](https://leetcode.com/problems/string-compression/)
- [LeetCode 38: Count and Say](https://leetcode.com/problems/count-and-say/) (run-based generation idea)
