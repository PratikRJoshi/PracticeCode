# Smallest Strings

## 1) Problem Description

You are given:
- a binary string `dataSequence`
- maximum allowed adjacent swaps `maxSwaps`

In one operation, you can swap two adjacent characters.

Return the lexicographically smallest binary string possible after at most `maxSwaps` swaps.

Example:

```text
dataSequence = "11011010"
maxSwaps = 5
output = "01011110"
```

Constraints:
- `1 <= |dataSequence| <= 2 * 10^5`
- `1 <= maxSwaps <= 10^9`

## 2) Intuition/Main Idea

In binary strings, lexicographically smaller means getting `'0'` as left as possible.

So we should greedily move zeros left, as much as swaps allow.

### Why this intuition works

- The earliest index where two candidate strings differ decides lexicographic order.
- Placing a zero earlier is always beneficial.
- Adjacent swaps only allow moving a zero left across ones.
- Zeros cannot cross each other, so the `j`-th zero can never go left of index `j`.

### How to derive it step by step

1. Collect original positions of all zeros.
2. Process zeros from left to right.
3. For zero at `currentPosition`, the leftmost legal position is `zeroIndex` (to preserve zero order).
4. Move it left by `min(maxSwaps, currentPosition - zeroIndex)`.
5. Decrease remaining swaps and continue.
6. Build result from final zero positions.

## 3) Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
| --- | --- |
| @AtMostKAdjacentSwaps | `moveLeftBy = Math.min(remainingSwaps, currentPosition - leftmostAllowedPosition)` |
| @LexicographicallySmallest | process zeros from left to right and push each as far left as possible |
| @PreserveCharacterCounts | build answer with all `'1'`, then place `'0'` at computed positions |
| @NoZeroCrossing | `leftmostAllowedPosition = zeroIndex` enforces zero order |

## 4) Final Java Code & Learning Pattern (Full Content)

```java
import java.util.*;

class Result {

    public static String getSmallestString(String dataSequence, long maxSwaps) {
        int length = dataSequence.length();
        List<Integer> originalZeroPositions = new ArrayList<>();

        for (int index = 0; index < length; index++) {
            if (dataSequence.charAt(index) == '0') {
                originalZeroPositions.add(index);
            }
        }

        int zeroCount = originalZeroPositions.size();
        int[] finalZeroPositions = new int[zeroCount];
        long remainingSwaps = maxSwaps;

        for (int zeroIndex = 0; zeroIndex < zeroCount; zeroIndex++) {
            int currentPosition = originalZeroPositions.get(zeroIndex);
            int leftmostAllowedPosition = zeroIndex;

            long moveLeftBy = Math.min(remainingSwaps, (long) currentPosition - leftmostAllowedPosition);
            finalZeroPositions[zeroIndex] = (int) (currentPosition - moveLeftBy);
            remainingSwaps -= moveLeftBy;
        }

        char[] answer = new char[length];
        Arrays.fill(answer, '1');
        for (int finalZeroPosition : finalZeroPositions) {
            answer[finalZeroPosition] = '0';
        }

        return new String(answer);
    }
}
```

Learning Pattern:
- For lexicographic minimization under adjacent swaps, greedily move smaller characters left.
- Track movement constraints from relative order (same characters cannot cross each other when minimizing moves).

## 5) Complexity Analysis

- Time Complexity: $O(n)$
- Space Complexity: $O(n)$

## Similar Problems

- [LeetCode 1505: Minimum Possible Integer After at Most K Adjacent Swaps On Digits](https://leetcode.com/problems/minimum-possible-integer-after-at-most-k-adjacent-swaps-on-digits/)
- [LeetCode 3216: Lexicographically Smallest String After a Swap](https://leetcode.com/problems/lexicographically-smallest-string-after-a-swap/)