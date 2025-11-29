# Arranging Numbers

## Problem Description
There is a list of consecutive numbers beginning with 1. An arrangement of these numbers is called "beautiful" if at least one of the following is true for each position i using 1-based indexing:

The number at position i is divisible by i
i is divisible by the number at position i
Determine how many beautiful arrangements are possible for a given n integers.



Example

n = 5

The possible beautiful arrangements are:

[1,2,3,4,5]

[2,1,3,4,5]

[3,2,1,4,5]

[4,2,3,1,5]

[5,2,3,4,1]

[4,1,3,2,5]

[1,4,3,2,5]

[3,4,1,2,5]

[2,4,3,1,5]

[5,4,3,2,1]



In the first arrangement, both conditions hold for all elements: each i equals the value at index i, so each i is divisible by the element, and each element is divisible by its index i. In subsequent arrangements, at least one of the conditions is satisfied at each position.



Function Description

Complete the function arrangements in the editor with the following parameter(s):

    n:  an integer



Returns

int: the number of beautiful arrangements possible.



Constraints

1 < n < 20


Input Format For Custom Testing
The first line contains an integer, n.

Sample Case 0
Sample Input 0

2
Sample Output 0

2


Explanation 0

The beautiful arrangements for n=2 are [1,2] and [2,1].

For the arrangement [1, 2]: the number at each position is divisible by its position number

For the arrangement [2, 1]: 2 is divisible by 1 (its index), and the index 2 is divisible by 1 (the value at the position)

Sample Case 1
Sample Input 1

3
Sample Output 1

3
Explanation 1

[1,2,3]
[2,1,3]
[3,2,1]
[1, 2, 3] -> all values are divisible by their indices and vice versa, satisfy both

[2, 1, 3] -> in order: value divisible by index, index divisible by value, 3/3 satisfies both

[3, 2, 1] -> in order: value divisible by index, 2/2 satisfies both, index divisible by value

Sample Case 2
Sample Input 2

4
Sample Output 2

8
Explanation 2

[1,2,3,4]
[2,1,3,4]
[3,2,1,4]
[4,2,3,1]
[1,4,3,2]
[4,1,3,2]
[3,4,1,2]
[2,4,3,1]

## Intuition/Main Idea:
This is a permutation problem with specific constraints. We need to count all permutations of numbers 1 to n where each position satisfies the "beautiful" condition.

The key insight is to use backtracking with pruning. Instead of generating all permutations and then checking if they satisfy the condition, we can check the condition during the generation process. This allows us to prune branches of the search tree that would not lead to valid arrangements, significantly reducing the search space.

For each position, we try placing each unused number and only proceed if the number satisfies the "beautiful" condition at that position. This way, we only explore valid partial arrangements.

## Code Mapping:

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Beautiful arrangement condition | `if (i % position == 0 || position % i == 0)` |
| Tracking used numbers | `boolean[] used = new boolean[n + 1]` |
| Backtracking algorithm | `backtrack(n, position + 1, used)` and `used[i] = false; // Backtrack` |
| Counting valid arrangements | `if (position > n) { count++; return; }` |

## Final Java Code & Learning Pattern:

```java
import java.util.*;

class Solution {
    private int count = 0;
    
    public int arrangements(int n) {
        boolean[] used = new boolean[n + 1]; // To track which numbers have been used
        backtrack(n, 1, used);
        return count;
    }
    
    private void backtrack(int n, int position, boolean[] used) {
        // If we've placed all numbers, we found a valid arrangement
        if (position > n) {
            count++;
            return;
        }
        
        // Try placing each number from 1 to n at the current position
        for (int i = 1; i <= n; i++) {
            // Skip if the number is already used
            if (used[i]) continue;
            
            // Check if placing i at position satisfies the beautiful condition
            if (i % position == 0 || position % i == 0) {
                used[i] = true;
                backtrack(n, position + 1, used);
                used[i] = false; // Backtrack
            }
        }
    }
    
    // Main method for testing
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        Solution solution = new Solution();
        System.out.println(solution.arrangements(n));
        scanner.close();
    }
}
```

## Complexity Analysis:

**Time Complexity**: $O(k)$, where k is the number of valid permutations. In the worst case, this could approach $O(n!)$, but due to our pruning strategy (only exploring paths that satisfy the "beautiful" condition), the actual number of explored paths is much smaller. Each valid permutation requires $O(n)$ work to construct.

**Space Complexity**: $O(n)$ for the recursion stack and the boolean array used to track which numbers have been used.

## Similar Problems:
- [LeetCode 526: Beautiful Arrangement](https://leetcode.com/problems/beautiful-arrangement/) - This is essentially the same problem
- [LeetCode 46: Permutations](https://leetcode.com/problems/permutations/) - General permutation problem without the "beautiful" constraint
- [LeetCode 47: Permutations II](https://leetcode.com/problems/permutations-ii/) - Permutation problem with duplicates
- [LeetCode 784: Letter Case Permutation](https://leetcode.com/problems/letter-case-permutation/) - Another permutation problem with different constraints
