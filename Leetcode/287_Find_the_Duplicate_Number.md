### 287. Find the Duplicate Number
### Problem Link: [Find the Duplicate Number](https://leetcode.com/problems/find-the-duplicate-number/)

### Intuition/Main Idea
This problem asks us to find the duplicate number in an array of integers where each integer is between 1 and n (inclusive), and there is exactly one duplicate number. The key constraints are that we cannot modify the array and must use only constant extra space.

The most elegant approach is to use the **Floyd's Tortoise and Hare (Cycle Detection)** algorithm. The insight is to treat the array as a linked list where the value at each index points to the next index. Since there's a duplicate number, there must be a cycle in this "linked list".

For example, if `nums = [1,3,4,2,2]`, we can visualize it as:
- Index 0 points to index 1 (value 3)
- Index 1 points to index 3 (value 2)
- Index 3 points to index 2 (value 4)
- Index 2 points to index 4 (value 2)
- Index 4 points to index 2 (value 4)

Notice that indices 2 and 4 both point to index 4, creating a cycle. The duplicate number (2) is the entry point of this cycle.

The algorithm has two phases:
1. **Phase 1**: Find the intersection point inside the cycle using the tortoise and hare pointers
2. **Phase 2**: Find the entrance to the cycle (the duplicate number) by starting one pointer at the beginning and another at the intersection point

### Code Mapping

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Find intersection point (Phase 1) | `while (slow != fast) { slow = nums[slow]; fast = nums[nums[fast]]; }` |
| Find cycle entrance (Phase 2) | `while (slow != fast) { slow = nums[slow]; fast = nums[fast]; }` |
| Return duplicate number | `return slow;` |
| Use constant extra space | Only using two pointers: slow and fast |

### Final Java Code & Learning Pattern

```java
// [Pattern: Floyd's Cycle Detection (Tortoise and Hare)]
class Solution {
    public int findDuplicate(int[] nums) {
        // Phase 1: Find the intersection point inside the cycle
        int slow = nums[0];
        int fast = nums[0];
        
        do {
            slow = nums[slow];          // Move one step
            fast = nums[nums[fast]];    // Move two steps
        } while (slow != fast);
        
        // Phase 2: Find the entrance to the cycle (the duplicate number)
        slow = nums[0];                 // Reset slow to the beginning
        
        while (slow != fast) {
            slow = nums[slow];          // Move one step
            fast = nums[fast];          // Move one step (not two!)
        }
        
        return slow;  // Either slow or fast is the duplicate number
    }
}
```

### Alternative Implementation (Binary Search)

```java
// [Pattern: Binary Search on Answer Space]
class Solution {
    public int findDuplicate(int[] nums) {
        int left = 1;
        int right = nums.length - 1;
        
        while (left < right) {
            int mid = left + (right - left) / 2;
            
            // Count numbers less than or equal to mid
            int count = 0;
            for (int num : nums) {
                if (num <= mid) {
                    count++;
                }
            }
            
            // If count is greater than mid, the duplicate is in the left half
            if (count > mid) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        
        return left;
    }
}
```

### Complexity Analysis
- **Time Complexity**: $O(n)$ for the Floyd's Cycle Detection approach, where n is the length of the array. We traverse the array at most twice.
- **Space Complexity**: $O(1)$ as we only use two pointers regardless of the input size.

For the binary search approach:
- **Time Complexity**: $O(n \log n)$ because we do a binary search ($O(\log n)$) and for each step, we count elements ($O(n)$).
- **Space Complexity**: $O(1)$ as we only use a few variables.

### Binary Search Problems Explanation
For the binary search approach:
- **Using < vs <=**: We use `left < right` because we're searching for a specific value, not a position. When `left == right`, we've found our answer.
- **Setting pointers**: We set `right = mid` (not `mid - 1`) when the duplicate is in the left half because the duplicate could be `mid` itself. We set `left = mid + 1` when the duplicate is in the right half because we've confirmed it's not in the range `[left, mid]`.
- **Return value**: We return `left` because when the loop terminates, `left == right`, and this value is our duplicate number.

### Similar Problems
1. **LeetCode 142: Linked List Cycle II** - The original problem that uses Floyd's Cycle Detection to find the start of a cycle.
2. **LeetCode 141: Linked List Cycle** - Detect if a linked list has a cycle.
3. **LeetCode 268: Missing Number** - Find the missing number in an array.
4. **LeetCode 442: Find All Duplicates in an Array** - Find all duplicates in an array where each integer appears once or twice.
5. **LeetCode 448: Find All Numbers Disappeared in an Array** - Find all numbers that don't appear in the array.
6. **LeetCode 645: Set Mismatch** - Find the duplicate and missing numbers.
7. **LeetCode 41: First Missing Positive** - Find the smallest missing positive integer.
8. **LeetCode 4: Median of Two Sorted Arrays** - Another problem that can be solved using binary search.
