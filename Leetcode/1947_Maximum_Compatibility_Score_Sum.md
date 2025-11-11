# Maximum Compatibility Score Sum

## Problem Description

**Problem Link:** [Maximum Compatibility Score Sum](https://leetcode.com/problems/maximum-compatibility-score-sum/)

There is a survey that consists of `n` questions where each question's answer is either `0` (no) or `1` (yes). The survey was given to `m` students numbered from `0` to `m - 1` and `m` mentors numbered from `0` to `m - 1`.

The answers of the students are represented by a 2D integer array `students` where `students[i]` is an integer array that contains the answers of the `i`th student (0-indexed). The answers of the mentors are represented by a 2D integer array `mentors` where `mentors[j]` is an integer array that contains the answers of the `j`th mentor (0-indexed).

Each student will be assigned to **one** mentor, and each mentor will have **one** student assigned to them. The **compatibility score** of a student-mentor pair is the number of answers that are the same between both of them.

- For example, if the student's answers were `[1, 0, 1]` and the mentor's answers were `[0, 0, 1]`, their compatibility score would be `2` because they both have the same answer at index `1` and index `2`.

Return *the **maximum compatibility score sum** that can be achieved by assigning each student to a mentor*.

**Example 1:**

```
Input: students = [[1,1,0],[1,0,1],[0,0,1]], mentors = [[1,0,0],[0,0,1],[1,1,0]]
Output: 8
Explanation: We assign students to mentors in the following way:
- Student 0 is assigned to mentor 2 with a compatibility score of 3.
- Student 1 is assigned to mentor 1 with a compatibility score of 2.
- Student 2 is assigned to mentor 0 with a compatibility score of 3.
The total compatibility score is 3 + 2 + 3 = 8.
```

**Example 2:**

```
Input: students = [[0,0],[0,0],[0,0]], mentors = [[1,1],[1,1],[1,1]]
Output: 0
Explanation: The compatibility score of any student-mentor pair is 0.
```

**Constraints:**
- `m == students.length == mentors.length`
- `n == students[i].length == mentors[j].length`
- `1 <= m, n <= 8`
- `students[i][k]` and `mentors[j][k]` are either `0` or `1`.

## Intuition/Main Idea

This is a **backtracking** or **Hungarian algorithm** problem. We need to assign students to mentors to maximize total compatibility.

**Core Algorithm:**
1. Precompute compatibility scores between all student-mentor pairs.
2. Use backtracking to assign students to mentors.
3. Try assigning each student to each unassigned mentor.
4. When all assigned, calculate total score.
5. Track maximum score.

**Why backtracking works:** We systematically try all assignment possibilities. When we finish trying assignments, we backtrack to try others.

## Code Mapping

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Precompute scores | Score calculation - Lines 7-13 |
| Backtrack function | Backtrack method - Lines 15-28 |
| Base case: all assigned | Index check - Lines 17-20 |
| Try each mentor | Mentor loop - Line 23 |
| Check if assigned | Assigned check - Line 24 |
| Assign mentor | Assignment - Line 25 |
| Recurse | Recursive call - Line 26 |
| Backtrack | Remove assignment - Line 27 |
| Return result | Return statement - Line 30 |

## Final Java Code & Learning Pattern

```java
class Solution {
    public int maxCompatibilitySum(int[][] students, int[][] mentors) {
        int m = students.length;
        int n = students[0].length;
        
        // Precompute compatibility scores
        int[][] scores = new int[m][m];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < m; j++) {
                for (int k = 0; k < n; k++) {
                    if (students[i][k] == mentors[j][k]) {
                        scores[i][j]++;
                    }
                }
            }
        }
        
        boolean[] used = new boolean[m];
        return backtrack(scores, 0, used, 0);
    }
    
    private int backtrack(int[][] scores, int studentIndex, boolean[] used, int currentScore) {
        // Base case: all students assigned
        if (studentIndex == scores.length) {
            return currentScore;
        }
        
        int maxScore = 0;
        // Try assigning current student to each mentor
        for (int mentorIndex = 0; mentorIndex < scores.length; mentorIndex++) {
            if (!used[mentorIndex]) {
                used[mentorIndex] = true;
                int score = backtrack(scores, studentIndex + 1, used, 
                                     currentScore + scores[studentIndex][mentorIndex]);
                maxScore = Math.max(maxScore, score);
                used[mentorIndex] = false; // Backtrack
            }
        }
        
        return maxScore;
    }
}
```

**Explanation of Key Code Sections:**

1. **Precompute Scores (Lines 7-13):** Calculate compatibility score for each student-mentor pair.

2. **Backtrack (Lines 15-28):**
   - **Base Case (Lines 17-20):** When all students assigned, return current score.
   - **Try Mentors (Lines 23-27):** For each unassigned mentor:
     - **Assign (Line 24):** Mark mentor as used.
     - **Recurse (Line 25):** Assign next student.
     - **Backtrack (Line 26):** Unmark mentor.

**Why backtracking:**
- We need to explore all assignment possibilities.
- After trying an assignment, we backtrack to try others.
- This ensures we find the maximum score.

**Example walkthrough for `students = [[1,1,0],[1,0,1],[0,0,1]], mentors = [[1,0,0],[0,0,1],[1,1,0]]`:**
- scores: student0-mentor0=1, student0-mentor1=1, student0-mentor2=3, etc.
- Try: student0→mentor2 (score=3), student1→mentor1 (score=2), student2→mentor0 (score=3)
- Total: 3+2+3=8 ✓

## Complexity Analysis

- **Time Complexity:** $O(m! \times n)$ where we try all $m!$ assignments and each takes $O(n)$ to compute scores.

- **Space Complexity:** $O(m)$ for used array and recursion stack.

## Similar Problems

Problems that can be solved using similar backtracking patterns:

1. **1947. Maximum Compatibility Score Sum** (this problem) - Backtracking assignment
2. **2305. Fair Distribution of Cookies** - Backtracking distribution
3. **698. Partition to K Equal Sum Subsets** - Backtracking partition
4. **526. Beautiful Arrangement** - Backtracking placement
5. **51. N-Queens** - Backtracking placement
6. **37. Sudoku Solver** - Backtracking puzzle
7. **980. Unique Paths III** - Backtracking paths
8. **1219. Path with Maximum Gold** - Backtracking with optimization

