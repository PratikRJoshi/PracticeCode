# Single-Threaded CPU

## Problem Description

**Problem Link:** [Single-Threaded CPU](https://leetcode.com/problems/single-threaded-cpu/)

You are given `n` tasks labeled from `0` to `n - 1` represented by a 2D integer array `tasks`, where `tasks[i] = [enqueueTimei, processingTimei]` means that the `i`th task will be available to process at `enqueueTimei` and will take `processingTimei` to finish processing.

You have a single-threaded CPU that can process **at most one task at a time** and will act in the following way:

- If the CPU is idle and there are no available tasks to process, the CPU remains idle.
- If the CPU is idle and there are available tasks, the CPU will choose the task with the **smallest processing time**. If multiple tasks have the same smallest processing time, it will choose the task with the smallest index.
- Once a task is started, the CPU will **process the entire task** without stopping.
- The CPU can finish a task then immediately start a new one.

Return *the order in which the CPU will process the tasks*.

**Example 1:**
```
Input: tasks = [[1,2],[2,4],[3,2],[4,1]]
Output: [0,2,3,1]
Explanation: The events go as follows: 
- At time = 1, task 0 is available to process. Available tasks = {0}.
- Also at time = 1, the CPU starts processing task 0. Available tasks = {}.
- At time = 3, task 0 finishes and task 2 is available. Available tasks = {2}.
- Also at time = 3, the CPU starts processing task 2. Available tasks = {}.
- At time = 5, task 2 finishes and task 3 is available. Available tasks = {3}.
- Also at time = 5, the CPU starts processing task 3. Available tasks = {}.
- At time = 6, task 3 finishes and task 1 is available. Available tasks = {1}.
- Also at time = 6, the CPU starts processing task 1. Available tasks = {}.
- At time = 10, task 1 finishes.
```

**Constraints:**
- `tasks.length == n`
- `1 <= n <= 10^5`
- `1 <= enqueueTimei, processingTimei <= 10^9`

## Intuition/Main Idea

This is a scheduling problem. We need to simulate CPU task processing with priority rules.

**Core Algorithm:**
- Sort tasks by enqueue time
- Use a min-heap for available tasks (prioritize by processing time, then index)
- Simulate time: at each time, add available tasks to heap, process shortest task
- Track current time and process tasks accordingly

**Why heap:** We need to efficiently select the task with smallest processing time (and smallest index as tiebreaker) from available tasks.

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|------------------------|-----------------------------------|
| Process tasks in order | Simulation loop - Lines 10-30 |
| Choose smallest processing time | Min-heap - Lines 6, 18-19 |
| Handle tie by index | Heap comparator - Lines 18-19 |
| Track available tasks | Time-based filtering - Lines 12-16 |

## Final Java Code & Learning Pattern (Full Content)

```java
class Solution {
    public int[] getOrder(int[][] tasks) {
        int n = tasks.length;
        
        // Create array with index for each task
        // [enqueueTime, processingTime, originalIndex]
        int[][] tasksWithIndex = new int[n][3];
        for (int i = 0; i < n; i++) {
            tasksWithIndex[i] = new int[]{tasks[i][0], tasks[i][1], i};
        }
        
        // Sort by enqueue time
        Arrays.sort(tasksWithIndex, (a, b) -> a[0] - b[0]);
        
        // Min-heap: [processingTime, originalIndex]
        // Priority: smallest processing time, then smallest index
        PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> {
            if (a[0] != b[0]) {
                return a[0] - b[0]; // Smaller processing time first
            }
            return a[1] - b[1]; // Smaller index first
        });
        
        List<Integer> result = new ArrayList<>();
        int taskIndex = 0;
        long currentTime = 0;
        
        // Process until all tasks are completed
        while (result.size() < n) {
            // Add all tasks that are available at current time
            while (taskIndex < n && tasksWithIndex[taskIndex][0] <= currentTime) {
                heap.offer(new int[]{tasksWithIndex[taskIndex][1], tasksWithIndex[taskIndex][2]});
                taskIndex++;
            }
            
            // If heap is empty, jump to next task's enqueue time
            if (heap.isEmpty()) {
                currentTime = tasksWithIndex[taskIndex][0];
                continue;
            }
            
            // Process task with smallest processing time (and smallest index)
            int[] task = heap.poll();
            result.add(task[1]); // Add original index to result
            currentTime += task[0]; // Update time
        }
        
        return result.stream().mapToInt(i -> i).toArray();
    }
}
```

## Complexity Analysis

**Time Complexity:** $O(n \log n)$ where $n$ is the number of tasks. Sorting takes $O(n \log n)$, and heap operations take $O(n \log n)$.

**Space Complexity:** $O(n)$ for the heap and arrays.

## Similar Problems

- [Task Scheduler](https://leetcode.com/problems/task-scheduler/) - Different scheduling problem
- [Meeting Rooms II](https://leetcode.com/problems/meeting-rooms-ii/) - Similar priority queue usage
- [IPO](https://leetcode.com/problems/ipo/) - Similar heap + sorting pattern

