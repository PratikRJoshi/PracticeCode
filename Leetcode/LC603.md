### LeetCode 603: Consecutive Available Seats
Problem: https://leetcode.com/problems/consecutive-available-seats/

## Problem Statement

Several friends at a cinema ticket office would like to reserve consecutive available seats.
Can you help to query all the consecutive available seats?

Your query should return the `seat_id` of all the seats that are consecutive and available, ordered by `seat_id`.

The `cinema` table has the following columns:
- `seat_id` (int)
- `free` (boolean) `1` means the seat is free, and `0` means it's occupied.

---

## Intuition and Main Idea

The goal is to find every seat that is free AND has a free neighbor. A neighbor can be the seat with `seat_id - 1` or `seat_id + 1`.

So, for any given seat `s`, we need to check two conditions:
1. Is seat `s` free?
2. Is seat `s-1` free OR is seat `s+1` free?

If both are true, then seat `s` should be in our result set.

We can solve this in a few ways in SQL:
1.  **Self-Join**: Join the `cinema` table with itself to find adjacent free seats.
2.  **Window Functions**: Use `LAG()` and `LEAD()` to look at the previous and next rows in a single pass.

---

## Solution 1: Using Self-Join

We can join the `cinema` table with itself, creating two aliases, `c1` and `c2`. We look for pairs of rows where both seats are free and their `seat_id`s differ by exactly 1.

We use `DISTINCT` because a seat might be paired with both its left and right neighbors (e.g., seat 3 could be paired with 2 and 4), and we only want to list it once.

```sql
SELECT DISTINCT c1.seat_id
FROM cinema c1
JOIN cinema c2
  ON ABS(c1.seat_id - c2.seat_id) = 1
WHERE c1.free = 1 AND c2.free = 1
ORDER BY c1.seat_id;
```

### How it Works:
- `JOIN cinema c2 ON ABS(c1.seat_id - c2.seat_id) = 1`: This creates pairs of all adjacent seats.
- `WHERE c1.free = 1 AND c2.free = 1`: This filters the pairs to only include those where both seats are free.
- `SELECT DISTINCT c1.seat_id`: This selects the `seat_id` from the first alias, ensuring no duplicates.

---

## Solution 2: Using Window Functions (`LAG` and `LEAD`)

This is often a more modern and efficient approach as it avoids a potentially costly self-join. Window functions allow us to access data from other rows (like the previous or next) while processing the current row.

We can create a Common Table Expression (CTE) that, for each seat, also shows the `free` status of the previous (`LAG`) and next (`LEAD`) seat.

```sql
WITH SeatStatus AS (
    SELECT
        seat_id,
        free,
        LAG(free, 1, 0) OVER (ORDER BY seat_id) AS prev_free,
        LEAD(free, 1, 0) OVER (ORDER BY seat_id) AS next_free
    FROM cinema
)
SELECT seat_id
FROM SeatStatus
WHERE free = 1 AND (prev_free = 1 OR next_free = 1)
ORDER BY seat_id;
```

### How it Works:
1.  **CTE `SeatStatus`**:
    *   `LAG(free, 1, 0) OVER (ORDER BY seat_id) AS prev_free`: This gets the `free` status of the previous seat. If there is no previous seat (i.e., for the first `seat_id`), it defaults to `0` (occupied).
    *   `LEAD(free, 1, 0) OVER (ORDER BY seat_id) AS next_free`: This gets the `free` status of the next seat, defaulting to `0` if it doesn't exist.
2.  **Final `SELECT`**:
    *   We then query the CTE and select seats where the seat itself is free (`free = 1`) and either its previous neighbor (`prev_free = 1`) or its next neighbor (`next_free = 1`) is also free.

---

## Performance Note

- The **self-join** solution is very readable but can be slow on large tables because the database has to perform a join operation, which can be resource-intensive.
- The **window function** solution is generally more performant because it scans the table only once to compute the `LAG` and `LEAD` values, avoiding the overhead of a join.

