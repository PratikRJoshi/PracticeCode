### 585. Investments in 2016
### Problem Link: [Investments in 2016](https://leetcode.com/problems/investments-in-2016/)

### Intuition/Main Idea
This problem requires finding the sum of insurance investments made in 2016 with specific conditions. The key insight is to use SQL window functions and self-joins to filter the data based on the given criteria. We need to find policies where:
1. There exists at least one other policy with the same TIV_2015
2. No other policy has the same (LAT, LON) coordinates

### Code Mapping

| Problem Requirement | SQL Code Section |
|---------------------|------------------|
| Sum of TIV_2016 | `SELECT ROUND(SUM(TIV_2016), 2) AS TIV_2016` |
| Policies with the same TIV_2015 | `WHERE TIV_2015 IN (SELECT TIV_2015 FROM Insurance GROUP BY TIV_2015 HAVING COUNT(*) > 1)` |
| Policies with unique locations | `AND (LAT, LON) IN (SELECT LAT, LON FROM Insurance GROUP BY LAT, LON HAVING COUNT(*) = 1)` |

### Final SQL Code & Learning Pattern

```sql
-- [Pattern: Window Functions and Filtering]
SELECT ROUND(SUM(TIV_2016), 2) AS TIV_2016
FROM Insurance
WHERE TIV_2015 IN (
    -- Find TIV_2015 values that appear more than once
    SELECT TIV_2015
    FROM Insurance
    GROUP BY TIV_2015
    HAVING COUNT(*) > 1
)
AND (LAT, LON) IN (
    -- Find unique location coordinates
    SELECT LAT, LON
    FROM Insurance
    GROUP BY LAT, LON
    HAVING COUNT(*) = 1
);
```

### Alternative Solution using Window Functions

```sql
-- [Pattern: Window Functions]
SELECT ROUND(SUM(TIV_2016), 2) AS TIV_2016
FROM (
    SELECT 
        TIV_2016,
        COUNT(*) OVER (PARTITION BY TIV_2015) AS same_tiv_count,
        COUNT(*) OVER (PARTITION BY LAT, LON) AS same_location_count
    FROM Insurance
) AS t
WHERE same_tiv_count > 1 AND same_location_count = 1;
```

### Complexity Analysis
- **Time Complexity**: $O(n)$ where n is the number of rows in the Insurance table. The query needs to scan the table multiple times for the subqueries.
- **Space Complexity**: $O(n)$ for storing intermediate results during query execution.

### Similar Problems
- LeetCode 184: Department Highest Salary (uses similar GROUP BY and filtering)
- LeetCode 1077: Project Employees III (uses window functions for ranking)
- LeetCode 1070: Product Sales Analysis III (filtering based on grouped data)
- LeetCode 1045: Customers Who Bought All Products (uses GROUP BY with HAVING)
