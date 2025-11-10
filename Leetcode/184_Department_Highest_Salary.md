### 184. Department Highest Salary
### Problem Link: [Department Highest Salary](https://leetcode.com/problems/department-highest-salary/)

### Intuition/Main Idea
This problem asks us to find the employees with the highest salary in each department. The key insight is to use a JOIN operation to combine the Employee and Department tables, and then use window functions or a subquery to identify the highest salary in each department. We need to:
1. Join the Employee and Department tables to get employee information with department names
2. Group the data by department and find the maximum salary in each department
3. Select employees whose salary matches the maximum salary in their department

### Code Mapping

| Problem Requirement | SQL Code Section |
|---------------------|------------------|
| Join Employee and Department tables | `FROM Employee e JOIN Department d ON e.departmentId = d.id` |
| Find highest salary in each department | `WHERE (e.departmentId, e.salary) IN (SELECT departmentId, MAX(salary) FROM Employee GROUP BY departmentId)` |
| Return Department name, Employee name, and Salary | `SELECT d.name AS Department, e.name AS Employee, e.salary AS Salary` |

### Final SQL Code & Learning Pattern

```sql
-- [Pattern: JOIN with Subquery]
SELECT d.name AS Department, e.name AS Employee, e.salary AS Salary
FROM Employee e
JOIN Department d ON e.departmentId = d.id
WHERE (e.departmentId, e.salary) IN (
    SELECT departmentId, MAX(salary)
    FROM Employee
    GROUP BY departmentId
);
```

### Alternative Solution using Window Functions

```sql
-- [Pattern: Window Functions]
SELECT Department, Employee, Salary
FROM (
    SELECT 
        d.name AS Department,
        e.name AS Employee,
        e.salary AS Salary,
        RANK() OVER (PARTITION BY e.departmentId ORDER BY e.salary DESC) AS salary_rank
    FROM Employee e
    JOIN Department d ON e.departmentId = d.id
) ranked
WHERE salary_rank = 1;
```

### Complexity Analysis
- **Time Complexity**: $O(n \log n)$ where n is the number of employees. The sorting operation in the GROUP BY or window function dominates the complexity.
- **Space Complexity**: $O(n)$ for storing the intermediate results and the final output.

### Similar Problems
1. **LeetCode 185: Department Top Three Salaries** - Find the top three highest paid employees in each department.
2. **LeetCode 176: Second Highest Salary** - Find the second highest salary from the Employee table.
3. **LeetCode 177: Nth Highest Salary** - Find the Nth highest salary from the Employee table.
4. **LeetCode 178: Rank Scores** - Rank scores in a consistent way with potential ties.
5. **LeetCode 180: Consecutive Numbers** - Find numbers that appear consecutively in a table.
6. **LeetCode 585: Investments in 2016** - Similar pattern of filtering based on aggregated subqueries.
7. **LeetCode 1070: Product Sales Analysis III** - Find the first year of sales for each product.
8. **LeetCode 1077: Project Employees III** - Find the most experienced employees in each project.
