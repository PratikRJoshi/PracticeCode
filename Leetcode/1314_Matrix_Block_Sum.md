# Matrix Block Sum

## Problem Description

**Problem Link:** [Matrix Block Sum](https://leetcode.com/problems/matrix-block-sum/)

Given a `m x n` matrix `mat` and an integer `k`, return *a matrix `answer` where each `answer[i][j]` is the sum of all elements `mat[r][c]` for*:

- `i - k <= r <= i + k`,
- `j - k <= c <= j + k`, and
- `(r, c)` is a valid position in the matrix.

**Example 1:**

```
Input: mat = [[1,2,3],[4,5,6],[7,8,9]], k = 1
Output: [[12,21,16],[27,45,33],[24,39,28]]
```

**Example 2:**

```
Input: mat = [[1,2,3],[4,5,6],[7,8,9]], k = 2
Output: [[45,45,45],[45,45,45],[45,45,45]]
```

**Constraints:**
- `m == mat.length`
- `n == mat[i].length`
- `1 <= m, n, k <= 100`
- `1 <= mat[i][j] <= 100`

## Intuition/Main Idea

This is a **2D prefix sum** problem. We need to compute the sum of elements in a `(2k+1) × (2k+1)` block centered at each cell.

**Core Algorithm:**
1. Precompute 2D prefix sum array.
2. For each cell `(i, j)`, compute the sum of the block using prefix sums.
3. Handle boundary cases where the block extends beyond matrix boundaries.

**Why prefix sum works:** Instead of summing elements for each query, we precompute prefix sums and use them to compute block sums in O(1) time per cell.

## Code Mapping

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Precompute prefix sum | Prefix sum calculation - Lines 7-12 |
| Calculate block sum | Block sum calculation - Lines 18-22 |
| Handle boundaries | Clamp coordinates - Lines 19-20 |
| Return result | Return statement - Line 24 |

## Final Java Code & Learning Pattern

```java
class Solution {
    public int[][] matrixBlockSum(int[][] mat, int k) {
        int m = mat.length;
        int n = mat[0].length;
        
        // Precompute 2D prefix sum
        int[][] prefix = new int[m + 1][n + 1];
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                prefix[i][j] = mat[i - 1][j - 1] + 
                              prefix[i - 1][j] + 
                              prefix[i][j - 1] - 
                              prefix[i - 1][j - 1];
            }
        }
        
        // Compute answer
        int[][] answer = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                // Calculate block boundaries
                int r1 = Math.max(0, i - k);
                int c1 = Math.max(0, j - k);
                int r2 = Math.min(m - 1, i + k);
                int c2 = Math.min(n - 1, j + k);
                
                // Use prefix sum to compute block sum
                answer[i][j] = prefix[r2 + 1][c2 + 1] - 
                               prefix[r1][c2 + 1] - 
                               prefix[r2 + 1][c1] + 
                               prefix[r1][c1];
            }
        }
        
        return answer;
    }
}
```

**Explanation of Key Code Sections:**

1. **Prefix Sum (Lines 7-12):** Precompute `prefix[i][j]` = sum of all elements in `mat[0..i-1][0..j-1]`.
   - Formula: `prefix[i][j] = mat[i-1][j-1] + prefix[i-1][j] + prefix[i][j-1] - prefix[i-1][j-1]`

2. **Block Boundaries (Lines 19-20):** Clamp boundaries to stay within matrix:
   - `r1 = max(0, i-k)`, `r2 = min(m-1, i+k)`
   - `c1 = max(0, j-k)`, `c2 = min(n-1, j+k)`

3. **Block Sum (Lines 22-25):** Use prefix sum to compute sum of block `[r1..r2][c1..c2]`:
   - Formula: `prefix[r2+1][c2+1] - prefix[r1][c2+1] - prefix[r2+1][c1] + prefix[r1][c1]`

**Why prefix sum formula works:**
- `prefix[r2+1][c2+1]` = sum of `[0..r2][0..c2]`
- Subtract `prefix[r1][c2+1]` = sum of `[0..r1-1][0..c2]`
- Subtract `prefix[r2+1][c1]` = sum of `[0..r2][0..c1-1]`
- Add `prefix[r1][c1]` = sum of `[0..r1-1][0..c1-1]` (added twice, so add once)

## Complexity Analysis

- **Time Complexity:** $O(m \times n)$ where $m$ and $n$ are matrix dimensions. We compute prefix sum once and answer array once.

- **Space Complexity:** $O(m \times n)$ for the prefix sum array and answer array.

## Similar Problems

Problems that can be solved using similar prefix sum patterns:

1. **1314. Matrix Block Sum** (this problem) - 2D prefix sum
2. **304. Range Sum Query 2D - Immutable** - 2D prefix sum
3. **303. Range Sum Query - Immutable** - 1D prefix sum
4. **307. Range Sum Query - Mutable** - Segment tree/Fenwick tree
5. **308. Range Sum Query 2D - Mutable** - 2D segment tree
6. **363. Max Sum of Rectangle No Larger Than K** - 2D prefix sum
7. **1074. Number of Submatrices That Sum to Target** - 2D prefix sum
8. **1292. Maximum Side Length of a Square with Sum Less than or Equal to Threshold** - 2D prefix sum

