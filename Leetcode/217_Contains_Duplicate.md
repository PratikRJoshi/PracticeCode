# [217. Contains Duplicate](https://leetcode.com/problems/contains-duplicate/)

Given an integer array `nums`, return `true` if any value appears **at least twice** in the array, and return `false` if every element is distinct.

**Example 1:**

```
Input: nums = [1,2,3,1]
Output: true
```

**Example 2:**

```
Input: nums = [1,2,3,4]
Output: false
```

**Example 3:**

```
Input: nums = [1,1,1,3,3,4,3,2,4,2]
Output: true
```

**Constraints:**

- `1 <= nums.length <= 10^5`
- `-10^9 <= nums[i] <= 10^9`

## Intuition/Main Idea:

To determine if an array contains any duplicate elements, we need to track which elements we've seen so far as we iterate through the array. When we encounter an element that we've already seen, we know there's a duplicate.

There are several approaches to solve this problem:

1. **HashSet Approach**: Use a HashSet to keep track of elements we've seen. This gives us O(1) lookup time.
2. **Sorting Approach**: Sort the array and then check if adjacent elements are the same.
3. **Brute Force Approach**: Compare each element with every other element (not efficient).

The HashSet approach is the most efficient for this problem, as it allows us to check for duplicates in a single pass through the array with O(n) time complexity.

## Code Mapping:

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Return true if any value appears at least twice | `if (seen.contains(num)) return true;` |
| Return false if every element is distinct | `return false;` (after checking all elements) |

## Final Java Code & Learning Pattern:

```java
class Solution {
    public boolean containsDuplicate(int[] nums) {
        // Use a HashSet to track elements we've seen
        Set<Integer> seen = new HashSet<>();
        
        // Iterate through the array
        for (int num : nums) {
            // If we've seen this element before, return true
            if (seen.contains(num)) {
                return true;
            }
            
            // Otherwise, add it to our set of seen elements
            seen.add(num);
        }
        
        // If we've checked all elements and found no duplicates, return false
        return false;
    }
}
```

This solution uses a HashSet to efficiently check for duplicates:

1. We create a HashSet to keep track of elements we've seen.
2. We iterate through each element in the array.
3. For each element:
   - If it's already in our set, we've found a duplicate, so we return true.
   - If not, we add it to our set.
4. If we finish iterating through the array without finding any duplicates, we return false.

The HashSet data structure is perfect for this problem because it provides O(1) average time complexity for both contains and add operations.

## Alternative Implementations:

### Sorting Approach:

```java
class Solution {
    public boolean containsDuplicate(int[] nums) {
        // Sort the array
        Arrays.sort(nums);
        
        // Check for adjacent duplicates
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] == nums[i - 1]) {
                return true;
            }
        }
        
        return false;
    }
}
```

This approach first sorts the array, then checks if any adjacent elements are the same. While this works, it's less efficient than the HashSet approach due to the sorting step.

### One-liner Using Stream API:

```java
class Solution {
    public boolean containsDuplicate(int[] nums) {
        return nums.length > new HashSet<>(Arrays.stream(nums).boxed().collect(Collectors.toList())).size();
    }
}
```

This one-liner converts the array to a HashSet (which automatically removes duplicates) and compares the sizes. If the HashSet size is smaller than the array length, there must be duplicates.

## Complexity Analysis:

- **Time Complexity**: 
  - HashSet Approach: O(n) where n is the length of the array. We iterate through the array once.
  - Sorting Approach: O(n log n) due to the sorting step.
  
- **Space Complexity**: 
  - HashSet Approach: O(n) in the worst case (when all elements are distinct).
  - Sorting Approach: O(1) if we sort in place, or O(n) if the sorting algorithm uses additional space.

## Similar Problems:

1. [219. Contains Duplicate II](https://leetcode.com/problems/contains-duplicate-ii/)
2. [220. Contains Duplicate III](https://leetcode.com/problems/contains-duplicate-iii/)
3. [1. Two Sum](https://leetcode.com/problems/two-sum/)
4. [128. Longest Consecutive Sequence](https://leetcode.com/problems/longest-consecutive-sequence/)
5. [287. Find the Duplicate Number](https://leetcode.com/problems/find-the-duplicate-number/)
