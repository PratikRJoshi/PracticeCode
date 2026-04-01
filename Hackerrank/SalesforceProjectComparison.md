# Salesforce Project Comparison

## 1) Problem Description

You are given a string `banner` with lowercase letters and `'.'` placeholders.

Replace every `'.'` with lowercase letters to maximize number of substrings consisting of identical characters only.

For a run of same character length `L`, contribution is:

```text
L * (L + 1) / 2
```

Total answer is sum over all runs.

## 2) Intuition/Main Idea

To maximize same-character substrings, we should merge and extend runs as much as possible.

For each consecutive `'.'` block:
- if both neighbors exist and are same letter, fill block with that letter to connect both runs
- else fill with one available neighbor letter
- if no neighbors (all dots or isolated), fill with any fixed letter like `'a'`

Then compute run-sum formula on final string.

### Why this intuition works

- The run score `L(L+1)/2` is convex in `L`, so combining lengths increases total more than splitting.
- Therefore joining equal neighboring runs is always optimal.

### How to derive it step by step

1. Convert string to char array.
2. Process each maximal dot segment `[start..end]`.
3. Choose fill character using neighbor rules above.
4. Fill the segment.
5. One final pass to sum run contributions.

## 3) Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
| --- | --- |
| @ReplaceAllDots | dot-segment loop fills every `'.'` |
| @MaximizeWellDesignedSubstrings | neighbor-aware fill merges runs greedily |
| @WellDesignedSubstringCount | run contribution `len * (len + 1) / 2` |
| @LengthUpTo5000 | linear processing passes |

## 4) Final Java Code & Learning Pattern (Full Content)

```java
import java.util.*;

class Result {

    public static long getMaxWellDesignedSubstrings(String banner) {
        char[] characters = banner.toCharArray();
        int n = characters.length;

        int index = 0;
        while (index < n) {
            if (characters[index] != '.') {
                index++;
                continue;
            }

            int start = index;
            while (index < n && characters[index] == '.') {
                index++;
            }
            int end = index - 1;

            char leftNeighbor = (start > 0) ? characters[start - 1] : 0;
            char rightNeighbor = (index < n) ? characters[index] : 0;

            char fillCharacter;
            if (leftNeighbor != 0 && rightNeighbor != 0 && leftNeighbor == rightNeighbor) {
                fillCharacter = leftNeighbor;
            } else if (leftNeighbor != 0) {
                fillCharacter = leftNeighbor;
            } else if (rightNeighbor != 0) {
                fillCharacter = rightNeighbor;
            } else {
                fillCharacter = 'a';
            }

            for (int position = start; position <= end; position++) {
                characters[position] = fillCharacter;
            }
        }

        long answer = 0L;
        int runLength = 1;

        for (int i = 1; i < n; i++) {
            if (characters[i] == characters[i - 1]) {
                runLength++;
            } else {
                answer += (long) runLength * (runLength + 1) / 2;
                runLength = 1;
            }
        }

        answer += (long) runLength * (runLength + 1) / 2;
        return answer;
    }
}
```

Learning Pattern:
- For maximizing same-character substrings, think in terms of maximizing run lengths.
- Local fill choices should favor merging runs whenever possible.

## 5) Complexity Analysis

- Time Complexity: $O(n)$
- Space Complexity: $O(n)$ for mutable char array

## Similar Problems

- [LeetCode 1750: Minimum Length of String After Deleting Similar Ends](https://leetcode.com/problems/minimum-length-of-string-after-deleting-similar-ends/) (run boundary reasoning)
- [LeetCode 1180: Count Substrings with Only One Distinct Letter](https://leetcode.com/problems/count-substrings-with-only-one-distinct-letter/) (run contribution formula)