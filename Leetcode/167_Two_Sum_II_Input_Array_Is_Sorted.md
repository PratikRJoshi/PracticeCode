# [167. Two Sum II - Input Array Is Sorted](https://leetcode.com/problems/two-sum-ii-input-array-is-sorted/)

Given a **1-indexed** array of integers `numbers` that is already **sorted in non-decreasing order**, find two numbers such that they add up to a specific `target` number. Let these two numbers be `numbers[index1]` and `numbers[index2]` where `1 <= index1 < index2 <= numbers.length`.

Return *the indices of the two numbers, `index1` and `index2`, **added by one** as an integer array `[index1, index2]` of length 2*.

The tests are generated such that there is **exactly one solution**. You **may not** use the same element twice.

Your solution must use only constant extra space.

**Example 1:**

```
Input: numbers = [2,7,11,15], target = 9
Output: [1,2]
Explanation: The sum of 2 and 7 is 9. Therefore, index1 = 1, index2 = 2. We return [1, 2].
```

**Example 2:**

```
Input: numbers = [2,3,4], target = 6
Output: [1,3]
Explanation: The sum of 2 and 4 is 6. Therefore, index1 = 1, index2 = 3. We return [1, 3].
```

**Example 3:**

```
Input: numbers = [-1,0], target = -1
Output: [1,2]
Explanation: The sum of -1 and 0 is -1. Therefore, index1 = 1, index2 = 2. We return [1, 2].
```

**Constraints:**

- `2 <= numbers.length <= 3 * 10^4`
- `-1000 <= numbers[i] <= 1000`
- `numbers` is sorted in **non-decreasing order**.
- `-1000 <= target <= 1000`
- The tests are generated such that there is **exactly one solution**.

## Intuition/Main Idea:

Since the array is sorted, we can use a two-pointer approach to efficiently find the pair that sums to the target. We'll start with two pointers:
1. `left` at the beginning of the array
2. `right` at the end of the array

Then we'll check the sum of the elements at these pointers:
- If the sum is equal to the target, we've found our answer
- If the sum is less than the target, we need to increase the sum, so we move the `left` pointer to the right
- If the sum is greater than the target, we need to decrease the sum, so we move the `right` pointer to the left

This approach works because the array is sorted, and we can systematically narrow down our search space.

## Code Mapping:

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Find two numbers that add up to target | `while (left < right) { int sum = numbers[left] + numbers[right]; ... }` |
| Return indices added by one | `return new int[] {left + 1, right + 1};` |
| Use constant extra space | Using only two pointers without additional data structures |
| Exactly one solution | The solution handles this by systematically exploring all possible pairs |

## Final Java Code & Learning Pattern:

```java
class Solution {
    public int[] twoSum(int[] numbers, int target) {
        // Initialize two pointers, one at the beginning and one at the end
        int left = 0;
        int right = numbers.length - 1;
        
        // Continue until the pointers meet
        while (left < right) {
            int sum = numbers[left] + numbers[right];
            
            if (sum == target) {
                // Found the pair, return indices (1-indexed)
                return new int[] {left + 1, right + 1};
            } else if (sum < target) {
                // Sum is too small, move left pointer to increase the sum
                left++;
            } else {
                // Sum is too large, move right pointer to decrease the sum
                right--;
            }
        }
        
        // This line should never be reached as the problem guarantees a solution
        return new int[] {-1, -1};
    }
}
```

This solution uses the two-pointer technique:
1. We start with two pointers: `left` at index 0 and `right` at the last index.
2. We calculate the sum of the elements at these pointers.
3. If the sum equals the target, we've found our answer and return the indices (adding 1 to convert to 1-indexed).
4. If the sum is less than the target, we need a larger sum, so we increment the `left` pointer.
5. If the sum is greater than the target, we need a smaller sum, so we decrement the `right` pointer.
6. We continue this process until we find the target sum.

The two-pointer approach works here because the array is sorted. This allows us to systematically adjust our pointers to find the target sum efficiently.

## Complexity Analysis:

- **Time Complexity**: $O(n)$ where n is the length of the array. In the worst case, we might need to scan the entire array once.
- **Space Complexity**: $O(1)$ as we only use two pointers regardless of the input size.

## Binary Search Alternative:

While the two-pointer approach is optimal for this problem, it's worth noting that a binary search approach could also work:
- For each element `numbers[i]`, we could binary search for `target - numbers[i]` in the range `[i+1, n-1]`.
- This would result in a time complexity of $O(n \log n)$, which is less efficient than the two-pointer approach.

## Similar Problems:

1. [1. Two Sum](https://leetcode.com/problems/two-sum/)
2. [15. 3Sum](https://leetcode.com/problems/3sum/)
3. [16. 3Sum Closest](https://leetcode.com/problems/3sum-closest/)
4. [18. 4Sum](https://leetcode.com/problems/4sum/)
5. [653. Two Sum IV - Input is a BST](https://leetcode.com/problems/two-sum-iv-input-is-a-bst/)
