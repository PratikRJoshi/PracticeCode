# Glide Typing

## Problem Description
Implement a basic prototype for detecting words typed using glide typing (swiping) on a keyboard. In glide typing, a user intends to type a subsequence of the characters they swipe over.



Given an input string s made up of lowercase letters and an array of n strings called dictionary, find the alphabetically smallest word in dictionary that is a subsequence of the given string s. If there is no such string, return "-1" as the answer.



Note: A string x is considered alphabetically smaller than another string y if string x would occur before y in dictionary order.



Example

s = "hgferyjkllkop"

dictionary = ["coffee", "coding", "happy", "hello", "hop"]



The only words in the dictionary that are subsequences of the given string are "hello" and "hop". Since "hello" is alphabetically smaller than "hop", the answer is "hello".



Function Description

Complete the function getValidWord in the editor with the following arguments:

    string s: the string given by the user

    string dictionary[n]: the list of valid words



Returns

    string: the alphabetically smallest word that is a subsequence of the user-given string



Constraints

1 ≤ length of s ≤ 20
1 ≤ n ≤ 105
1 ≤ length of word[i] ≤ 20
All the strings consist of lowercase English letters only, ascii[a-z].


Input Format For Custom Testing
The first line contains a string, s.

The next line contains an integer, n, the number of elements in dictionary.

Each of the next n lines contains a string, dictionary[i].

Sample Case 0
Sample Input For Custom Testing

STDIN          FUNCTION
-----          --------
ocbms    →     s = "ocbms"
5        →     dictionary[] size n = 5
rdnothix →     dictionary = ["rdnothix", "obms", "qhlrpiaiv", "ms", "jaupx"]
obms
qhlrpiaiv
ms
jaupx
Sample Output

ms
Explanation



The valid subsequence words are "ms" and "obms" out of which "ms" is smaller.

Sample Case 1
Sample Input For Custom Testing

STDIN          FUNCTION
-----          --------
zafqz    →     s = "zafqz"
3        →     dictionary[] size n = 3
dvuyaufe →     dictionary = ["dvuyaufe", "gtxbc", "hbckuhyh"]
gtxbc
hbckuhyh
Sample Output

-1
Explanation



None of the words is a subsequence of the given string.

## Intuition/Main Idea:
To solve this problem, we need to:
1. Check each word in the dictionary to see if it's a subsequence of the input string `s`
2. Among all valid subsequences, find the alphabetically smallest one

A string is a subsequence of another if all its characters appear in the same order (but not necessarily contiguously) in the other string. We can check this efficiently by using a two-pointer approach.

## Code Mapping:

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Check if a word is a subsequence | `isSubsequence(String word, String s)` function |
| Find alphabetically smallest word | Comparison in the main loop: `if (result.equals("-1") || word.compareTo(result) < 0)` |
| Return "-1" if no valid word | Initialize `result = "-1"` and return if unchanged |

## Final Java Code & Learning Pattern:

```java
import java.util.*;

class Solution {
    public static String getValidWord(String s, String[] dictionary) {
        String result = "-1";
        
        for (String word : dictionary) {
            // Check if the current word is a subsequence of s
            if (isSubsequence(word, s)) {
                // If this is the first valid word or it's alphabetically smaller than the current result
                if (result.equals("-1") || word.compareTo(result) < 0) {
                    result = word;
                }
            }
        }
        
        return result;
    }
    
    // Function to check if word is a subsequence of s
    private static boolean isSubsequence(String word, String s) {
        int wordIndex = 0;
        int sIndex = 0;
        
        // Try to find each character of word in s
        while (wordIndex < word.length() && sIndex < s.length()) {
            if (word.charAt(wordIndex) == s.charAt(sIndex)) {
                wordIndex++; // Move to next character in word
            }
            sIndex++; // Always move to next character in s
        }
        
        // If we found all characters of word in s
        return wordIndex == word.length();
    }
    
    // Main method for testing
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        
        int n = Integer.parseInt(scanner.nextLine());
        String[] dictionary = new String[n];
        
        for (int i = 0; i < n; i++) {
            dictionary[i] = scanner.nextLine();
        }
        
        System.out.println(getValidWord(s, dictionary));
        scanner.close();
    }
}
```

## Complexity Analysis:

**Time Complexity**: $O(n \times (m + k))$, where:
- $n$ is the number of words in the dictionary
- $m$ is the average length of words in the dictionary
- $k$ is the length of the input string `s`

For each word in the dictionary, we check if it's a subsequence of `s`, which takes O(m + k) time.

**Space Complexity**: $O(1)$ excluding the input storage. We only use a constant amount of extra space for variables.

## Similar Problems:
- [LeetCode 392: Is Subsequence](https://leetcode.com/problems/is-subsequence/) - Checking if one string is a subsequence of another
- [LeetCode 524: Longest Word in Dictionary through Deleting](https://leetcode.com/problems/longest-word-in-dictionary-through-deleting/) - Finding the longest word that is a subsequence
- [LeetCode 792: Number of Matching Subsequences](https://leetcode.com/problems/number-of-matching-subsequences/) - Counting the number of words that are subsequences
