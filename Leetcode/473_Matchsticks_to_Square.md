# [473. Matchsticks to Square](https://leetcode.com/problems/matchsticks-to-square/)

You are given an integer array `matchsticks` where `matchsticks[i]` is the length of the `ith` matchstick. You want to use **all the matchsticks** to make one square. You **should not break** any stick, but you can link them up, and each matchstick must be used **exactly one time**.

Return `true` if you can make this square and `false` otherwise.

**Example 1:**

![Example 1](https://assets.leetcode.com/uploads/2021/04/09/matchsticks1-grid.jpg)

```
Input: matchsticks = [1,1,2,2,2]
Output: true
Explanation: You can form a square with length 2, one side of the square came two sticks with length 1.
```

**Example 2:**

```
Input: matchsticks = [3,3,3,3,4]
Output: false
Explanation: You cannot find a way to form a square with all the matchsticks.
```

**Constraints:**

- `1 <= matchsticks.length <= 15`
- `1 <= matchsticks[i] <= 10^8`

## Intuition/Main Idea:

This problem is essentially asking if we can partition the array into 4 subsets, each with an equal sum. The sum of each side of the square must be equal to the total sum of all matchsticks divided by 4.

We can approach this as a backtracking problem:
1. Calculate the total sum of all matchsticks.
2. If the total sum is not divisible by 4, return false immediately.
3. Sort the matchsticks in descending order to optimize the backtracking (this helps us fail faster).
4. Use backtracking to try placing each matchstick into one of the 4 sides of the square.

The key insight is to use an array to represent the current length of each side of the square, and try to build all sides simultaneously rather than one by one.

## Code Mapping:

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Use all matchsticks | The backtracking ensures all matchsticks are used |
| Should not break any stick | Each matchstick is used as a whole |
| Each matchstick must be used exactly once | `if (dfs(matchsticks, sides, index + 1)) return true;` increments index after each placement |
| Return true if square can be made | `return dfs(matchsticks, 0, new int[4], target);` |

## Final Java Code & Learning Pattern:

```java
class Solution {
    public boolean makesquare(int[] matchsticks) {
        if (matchsticks == null || matchsticks.length < 4) {
            return false;
        }
        
        // Calculate the total sum
        int sum = 0;
        for (int stick : matchsticks) {
            sum += stick;
        }
        
        // If the sum is not divisible by 4, we cannot form a square
        if (sum % 4 != 0) {
            return false;
        }
        
        // Sort in descending order to optimize backtracking
        // (we try larger matchsticks first to fail faster)
        Arrays.sort(matchsticks);
        reverse(matchsticks);
        
        // The target length of each side of the square
        int target = sum / 4;
        
        // Check if any matchstick is longer than the target side length
        if (matchsticks[0] > target) {
            return false;
        }
        
        // Use backtracking to try placing each matchstick
        return dfs(matchsticks, 0, new int[4], target);
    }
    
    private boolean dfs(int[] matchsticks, int index, int[] sides, int target) {
        // Base case: all matchsticks have been used
        if (index == matchsticks.length) {
            // Check if all sides are equal
            return sides[0] == sides[1] && sides[1] == sides[2] && sides[2] == sides[3];
        }
        
        // Try to place the current matchstick in each of the 4 sides
        for (int i = 0; i < 4; i++) {
            // Skip if adding this matchstick would exceed the target length
            // or if this side is the same as the previous side we tried (to avoid duplicate work)
            if (sides[i] + matchsticks[index] > target || (i > 0 && sides[i] == sides[i-1])) {
                continue;
            }
            
            // Place the matchstick on this side
            sides[i] += matchsticks[index];
            
            // Recursively try to place the next matchstick
            if (dfs(matchsticks, index + 1, sides, target)) {
                return true;
            }
            
            // Backtrack: remove the matchstick from this side
            sides[i] -= matchsticks[index];
        }
        
        // If no valid placement is found
        return false;
    }
    
    // Helper method to reverse an array
    private void reverse(int[] arr) {
        int left = 0, right = arr.length - 1;
        while (left < right) {
            int temp = arr[left];
            arr[left] = arr[right];
            arr[right] = temp;
            left++;
            right--;
        }
    }
}
```

This solution uses backtracking to explore all possible ways to place the matchsticks:

1. First, we calculate the total sum of all matchsticks.
2. If the sum is not divisible by 4, we cannot form a square, so we return false.
3. We sort the matchsticks in descending order to optimize the backtracking. This helps us fail faster because we try to place the larger matchsticks first.
4. We check if the largest matchstick is longer than the target side length. If it is, we cannot form a square.
5. We use a depth-first search (DFS) approach to try placing each matchstick on one of the four sides:
   - For each matchstick, we try to place it on each of the four sides.
   - If placing it on a side would exceed the target length, we skip that side.
   - We also skip a side if it has the same length as the previous side we tried, to avoid duplicate work.
   - After placing a matchstick, we recursively try to place the next one.
   - If we can't find a valid placement, we backtrack by removing the matchstick from the current side.
6. The base case is when all matchsticks have been placed. We check if all sides have the same length.

## Optimization Techniques:

1. **Sorting in descending order**: This helps us fail faster by trying to place larger matchsticks first.
2. **Early termination**: We check if the sum is divisible by 4 and if the largest matchstick is not longer than the target side length.
3. **Avoiding duplicate work**: We skip sides that have the same length as sides we've already tried for the current matchstick.

## Complexity Analysis:

- **Time Complexity**: $O(4^n)$ where n is the number of matchsticks. In the worst case, we try each of the 4 sides for each matchstick. The sorting step is $O(n \log n)$, but it's dominated by the backtracking.
- **Space Complexity**: $O(n)$ for the recursion stack.

## Similar Problems:

1. [698. Partition to K Equal Sum Subsets](https://leetcode.com/problems/partition-to-k-equal-sum-subsets/)
2. [416. Partition Equal Subset Sum](https://leetcode.com/problems/partition-equal-subset-sum/)
3. [1723. Find Minimum Time to Finish All Jobs](https://leetcode.com/problems/find-minimum-time-to-finish-all-jobs/)
4. [2305. Fair Distribution of Cookies](https://leetcode.com/problems/fair-distribution-of-cookies/)
5. [996. Number of Squareful Arrays](https://leetcode.com/problems/number-of-squareful-arrays/)
