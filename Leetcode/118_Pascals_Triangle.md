# Pascal's Triangle

## Problem Description

**Problem Link:** [Pascal's Triangle](https://leetcode.com/problems/pascals-triangle/)

Given an integer `numRows`, return the first `numRows` of **Pascal's triangle**.

In Pascal's triangle, each number is the sum of the two numbers directly above it as shown:

```
    1
   1 1
  1 2 1
 1 3 3 1
1 4 6 4 1
```

**Example 1:**
```
Input: numRows = 5
Output: [[1],[1,1],[1,2,1],[1,3,3,1],[1,4,6,4,1]]
```

**Example 2:**
```
Input: numRows = 1
Output: [[1]]
```

**Constraints:**
- `1 <= numRows <= 30`

## Intuition/Main Idea

Pascal's triangle is built row by row. Each row has one more element than the previous row, and each element (except the first and last) is the sum of the two elements above it.

**Core Algorithm:**
- First row is always `[1]`
- For each subsequent row:
  - First element is always `1`
  - Middle elements are sum of two elements above: `triangle[i-1][j-1] + triangle[i-1][j]`
  - Last element is always `1`

**Why this works:** This directly follows the mathematical definition of Pascal's triangle. Each element represents a binomial coefficient, and the pattern of summing adjacent elements above is the fundamental property.

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|------------------------|-----------------------------------|
| Generate numRows rows | Outer loop - Line 6 |
| First row is [1] | Base case - Line 7 |
| Each row starts with 1 | First element - Line 11 |
| Middle elements are sum | Sum calculation - Line 14 |
| Each row ends with 1 | Last element - Line 17 |
| Build triangle row by row | List construction - Lines 9-18 |

## Final Java Code & Learning Pattern (Full Content)

```java
class Solution {
    public List<List<Integer>> generate(int numRows) {
        // Result list to store all rows of Pascal's triangle
        List<List<Integer>> triangle = new ArrayList<>();
        
        // Generate each row from 0 to numRows-1
        for (int i = 0; i < numRows; i++) {
            // Create a new row
            // Each row i has i+1 elements (row 0 has 1 element, row 1 has 2, etc.)
            List<Integer> row = new ArrayList<>();
            
            // First element of every row is always 1
            row.add(1);
            
            // For rows beyond the first row (i > 0), calculate middle elements
            // Middle elements are sum of two elements from previous row
            for (int j = 1; j < i; j++) {
                // Get previous row
                List<Integer> prevRow = triangle.get(i - 1);
                
                // Current element = sum of element above-left and above-right
                // prevRow.get(j-1) is the element directly above-left
                // prevRow.get(j) is the element directly above-right
                int sum = prevRow.get(j - 1) + prevRow.get(j);
                row.add(sum);
            }
            
            // Last element of every row (except first row) is always 1
            // For first row (i == 0), we skip this since we already added 1 above
            if (i > 0) {
                row.add(1);
            }
            
            // Add completed row to triangle
            triangle.add(row);
        }
        
        return triangle;
    }
}
```

## Complexity Analysis

**Time Complexity:** $O(numRows^2)$. We generate $numRows$ rows, and row $i$ has $i+1$ elements. Total elements = $1 + 2 + ... + numRows = \frac{numRows(numRows+1)}{2} = O(numRows^2)$.

**Space Complexity:** $O(numRows^2)$ for storing the triangle. This is the space required for the output.

## Similar Problems

- [Pascal's Triangle II](https://leetcode.com/problems/pascals-triangle-ii/) - Return only the nth row
- [Unique Paths](https://leetcode.com/problems/unique-paths/) - Uses similar combinatorial logic
- [Combination Sum](https://leetcode.com/problems/combination-sum/) - Related to combinations

