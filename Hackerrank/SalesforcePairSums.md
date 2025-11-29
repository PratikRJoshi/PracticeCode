# Salesforce Pair Sums

## Problem Description
Salesforce's Case Management System now operates at a massive scale, processing up to a million cases per batch. To assign work efficiently, the operations team must quickly find distinct pairs of cases whose combined resolution times (in minutes) are a non-zero multiple of 60. Pairs are considered distinct by their indices. If two cases share the same resolution time, choosing a different index yields a new pair.



Example

n = 3
resolutionTimes = [30, 30, 60]
Only one valid pair, indices (0, 1), sums to 60. Return 1.



Function Description

Complete the function multipleSums in the editor below.

multipleSums(int resolutionTimes[n]) — list of integers representing case-resolution times


Returns

long: the number of valid pairs whose sum is a non-zero multiple of 60


Constraints

1 ≤ n ≤ 106
1 ≤ resolutionTimes[i] ≤ 109
The answer can be as large as n × (n − 1) / 2; store and return it in 64-bit (long) precision.
Your algorithm must run in O(n) time and use at most O(60) additional space.


Note:

The sum must be a non-zero multiple of 60; ignore pairs that sum to 0.
An efficient strategy counts how many times each remainder r = t mod 60 occurs, then uses combinatorics to total all valid pairings.
Input Format For Custom Testing
Input from stdin will be processed as follows and passed to the function.

The first line contains an integer, n, that denotes the number of cases.
Each of the next n lines contains an integer, resolutionTimes[i].
Sample Case 0
Sample Input For Custom Testing

STDIN           Function
-----           --------
4               →  resolutionTimes[] size n = 4
20              →  resolutionTimes = [20, 40, 60, 120]
40
60
120
Sample Output

2
Explanation

Valid index pairs: (0, 1) and (2, 3).

Sample Case 1
Sample Input For Custom Testing

STDIN           Function
-----           -------- 
5               →  resolutionTimes[] size n = 5
30              →  resolutionTimes = [30, 40, 90, 80, 140]
40
90
80
140
Sample Output

3
Explanation

Valid index pairs: (1, 3), (0, 2), and (1, 4).

## Intuition/Main Idea:
The key insight is to use the properties of modular arithmetic. If (a + b) % 60 = 0, then (a % 60 + b % 60) % 60 = 0, which means (a % 60 + b % 60) is either 0 or 60.

We can count the frequency of each remainder when divided by 60, and then pair them up:
1. For each number a, we find how many previous numbers b have the complementary remainder such that (a % 60 + b % 60) % 60 = 0
2. The complementary remainder for a % 60 is (60 - a % 60) % 60

This approach allows us to solve the problem in O(n) time with O(60) space.

## Code Mapping:

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Count pairs with sum divisible by 60 | Using remainders and complementary remainders |
| Handle large input constraints | Using long data type for counting |
| O(n) time complexity | Single pass through the array |
| O(60) space complexity | Fixed-size array for remainder frequencies |

## Final Java Code & Learning Pattern:

```java
import java.util.*;

class Solution {
    public static long multipleSums(int[] resolutionTimes) {
        // Array to store the frequency of each remainder when divided by 60
        long[] remainderCount = new long[60];
        long pairCount = 0;
        
        for (int time : resolutionTimes) {
            // Calculate the remainder when divided by 60
            int remainder = time % 60;
            
            // Find the complementary remainder that would make the sum divisible by 60
            int complementaryRemainder = (60 - remainder) % 60;
            
            // Add the number of pairs we can form with the current time
            pairCount += remainderCount[complementaryRemainder];
            
            // Increment the count for the current remainder
            remainderCount[remainder]++;
        }
        
        return pairCount;
    }
    
    // Main method for testing
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        
        int[] resolutionTimes = new int[n];
        for (int i = 0; i < n; i++) {
            resolutionTimes[i] = scanner.nextInt();
        }
        
        System.out.println(multipleSums(resolutionTimes));
        scanner.close();
    }
}
```

## Complexity Analysis:

**Time Complexity**: $O(n)$, where n is the number of resolution times. We process each time exactly once.

**Space Complexity**: $O(1)$ or more specifically $O(60)$, as we use a fixed-size array of 60 elements to store the frequency of remainders.

## Similar Problems:
- [LeetCode 1010: Pairs of Songs With Total Durations Divisible by 60](https://leetcode.com/problems/pairs-of-songs-with-total-durations-divisible-by-60/) - Almost identical problem
- [LeetCode 1: Two Sum](https://leetcode.com/problems/two-sum/) - Similar approach using complementary values
- [LeetCode 560: Subarray Sum Equals K](https://leetcode.com/problems/subarray-sum-equals-k/) - Another problem using frequency counting
