# Total Cost to Hire K Workers

## Problem Description

**Problem Link:** [Total Cost to Hire K Workers](https://leetcode.com/problems/total-cost-to-hire-k-workers/)

You are given a **0-indexed** integer array `costs` where `costs[i]` is the cost of hiring the `i`th worker.

You are also given two integers `k` and `candidates`. We want to hire exactly `k` workers according to the following rules:

- You will run `k` sessions and hire exactly one worker in each session.
- In each hiring session, choose the worker with the lowest cost from either the first `candidates` workers or the last `candidates` workers. Break the tie by the smallest index.
- For example, if `costs = [3,2,7,7,1,2]` and `candidates = 2`, then in the first hiring session, we will choose the `4`th worker because they have the lowest cost `[3,2,7,7,1,2]`.
- If there are fewer than `candidates` workers remaining, choose the worker with the lowest cost among them. Break the tie by the smallest index.
- A worker can only be chosen once.

Return *the total cost to hire exactly* `k` *workers*.

**Example 1:**
```
Input: costs = [17,12,10,2,7,2,11,20,8], k = 3, candidates = 4
Output: 11
Explanation: We hire 3 workers in total. The total cost is initially 0.
- In the first hiring round we choose the worker from [17,12,10,2]. The lowest cost is 2, and we break the tie by the smallest index, which is 3. The total cost = 0 + 2 = 2.
- In the second hiring round we choose the worker from [17,12,10,7]. The lowest cost is 7, and we break the tie by the smallest index, which is 4. The total cost = 2 + 7 = 9.
- In the third hiring round we choose the worker from [17,12,10,11,20,8]. The lowest cost is 10, and we break the tie by the smallest index, which is 2. The total cost = 9 + 10 = 11.
Notice that the worker at index 3 was common in the first and second rounds but was not chosen.
```

**Constraints:**
- `1 <= costs.length <= 10^5`
- `1 <= costs[i] <= 10^5`
- `1 <= k, candidates <= costs.length`

## Intuition/Main Idea

We need to hire k workers, each time choosing from the first `candidates` or last `candidates` workers.

**Core Algorithm:**
- Use two min-heaps: one for left candidates, one for right candidates
- Maintain pointers to track which workers are still available
- For each hire: compare top of both heaps, choose smaller
- Update heaps as workers are hired

**Why two heaps:** We need to efficiently get minimum from either end. Two heaps allow us to maintain candidates from both ends separately.

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|------------------------|-----------------------------------|
| Hire k workers | Loop k times - Lines 12-35 |
| Choose from first/last candidates | Two heaps - Lines 6-7 |
| Get minimum cost | Compare heap tops - Lines 14-28 |
| Update heaps | Poll and offer - Lines 20-27, 30-33 |

## Final Java Code & Learning Pattern (Full Content)

```java
class Solution {
    public long totalCost(int[] costs, int k, int candidates) {
        int n = costs.length;
        
        // Two min-heaps: left and right candidates
        // Each heap stores [cost, index] pairs
        PriorityQueue<int[]> leftHeap = new PriorityQueue<>((a, b) -> {
            if (a[0] != b[0]) return a[0] - b[0];
            return a[1] - b[1]; // Break tie by index
        });
        PriorityQueue<int[]> rightHeap = new PriorityQueue<>((a, b) -> {
            if (a[0] != b[0]) return a[0] - b[0];
            return a[1] - b[1];
        });
        
        // Initialize left and right heaps
        int left = 0;
        int right = n - 1;
        
        // Add first 'candidates' workers to left heap
        for (int i = 0; i < candidates && left <= right; i++) {
            leftHeap.offer(new int[]{costs[left], left});
            left++;
        }
        
        // Add last 'candidates' workers to right heap
        for (int i = 0; i < candidates && left <= right; i++) {
            rightHeap.offer(new int[]{costs[right], right});
            right--;
        }
        
        long totalCost = 0;
        
        // Hire k workers
        for (int i = 0; i < k; i++) {
            // Choose worker from left heap if right heap is empty or left is smaller
            if (rightHeap.isEmpty() || 
                (!leftHeap.isEmpty() && leftHeap.peek()[0] <= rightHeap.peek()[0])) {
                int[] worker = leftHeap.poll();
                totalCost += worker[0];
                
                // Add next worker from left if available
                if (left <= right) {
                    leftHeap.offer(new int[]{costs[left], left});
                    left++;
                }
            } else {
                // Choose worker from right heap
                int[] worker = rightHeap.poll();
                totalCost += worker[0];
                
                // Add next worker from right if available
                if (left <= right) {
                    rightHeap.offer(new int[]{costs[right], right});
                    right--;
                }
            }
        }
        
        return totalCost;
    }
}
```

## Complexity Analysis

**Time Complexity:** $O(n \log n + k \log n)$ where $n$ is array length. Initial heap building takes $O(n \log n)$, and each hire operation takes $O(\log n)$.

**Space Complexity:** $O(n)$ for the heaps.

## Similar Problems

- [IPO](https://leetcode.com/problems/ipo/) - Similar heap-based selection
- [Maximum Subsequence Score](https://leetcode.com/problems/maximum-subsequence-score/) - Heap + sorting
- [K Closest Points to Origin](https://leetcode.com/problems/k-closest-points-to-origin/) - Heap for selection

