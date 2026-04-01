# Palindromic Strings

## 1) Problem Description

You are given an array of lowercase strings `arr`.

You may swap characters between any two strings any number of times. So effectively, characters are globally redistributable while each string keeps its original length.

Return the maximum number of strings that can be made palindromic.

## 2) Intuition/Main Idea

A palindrome of length `L` needs:
- `floor(L / 2)` character pairs
- and one center character if `L` is odd

Since swaps are global, only total available pairs matter for feasibility.

Let:
- `availablePairs = sum(freq[c] / 2)` across all characters.

For each string length `L`, required pairs are `L / 2`.

To maximize number of palindromes, satisfy smaller pair requirements first, so sort lengths ascending and greedily allocate pairs.

### Why this intuition works

- Every palindrome consumes pair budget; smaller strings consume less.
- If you cannot satisfy a smaller required pair count, you cannot satisfy any larger one.
- Therefore ascending greedy is optimal.

### How to derive it step by step

1. Count global character frequencies.
2. Compute total pair budget.
3. Extract string lengths and sort ascending.
4. For each length, spend `length/2` pairs if possible; count formed palindromes.
5. Stop when pair budget is insufficient.

## 3) Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
| --- | --- |
| @GlobalSwapsAllowed | global frequency counting across all strings |
| @PalindromeNeedsSymmetricPairs | `requiredPairs = length / 2` |
| @MaximizeCountOfPalindromicStrings | sort lengths ascending and greedy pair allocation |
| @LowercaseAlphabet | fixed-size `int[26]` frequency array |

## 4) Final Java Code & Learning Pattern (Full Content)

```java
import java.util.*;

class Result {

    public static int countPalindromes(List<String> arr) {
        int[] characterFrequency = new int[26];

        for (String word : arr) {
            for (int index = 0; index < word.length(); index++) {
                characterFrequency[word.charAt(index) - 'a']++;
            }
        }

        int availablePairs = 0;
        for (int count : characterFrequency) {
            availablePairs += count / 2;
        }

        List<Integer> lengths = new ArrayList<>();
        for (String word : arr) {
            lengths.add(word.length());
        }
        Collections.sort(lengths);

        int palindromeCount = 0;
        for (int length : lengths) {
            int requiredPairs = length / 2;
            if (availablePairs < requiredPairs) {
                break;
            }

            availablePairs -= requiredPairs;
            palindromeCount++;
        }

        return palindromeCount;
    }
}
```

Learning Pattern:
- When free swaps allow global redistribution, reduce problem to resource accounting (pairs/singles) instead of exact positions.
- To maximize count under limited resource, sort by required resource and greedily fill smallest first.

## 5) Complexity Analysis

- Let `N` = number of strings, `T` = total characters.
- Time Complexity: $O(T + N \log N)$
- Space Complexity: $O(N + 26)$

## Similar Problems

- [LeetCode 3035: Maximum Palindromes After Operations](https://leetcode.com/problems/maximum-palindromes-after-operations/) (same core idea)
- [LeetCode 409: Longest Palindrome](https://leetcode.com/problems/longest-palindrome/) (pair counting for palindrome construction)