### 621. Task Scheduler
### Problem Link: [Task Scheduler](https://leetcode.com/problems/task-scheduler/)

### Intuition/Main Idea
This problem asks us to find the minimum time needed to execute a list of tasks, where identical tasks must be separated by at least n units of time. The key insight is that the most frequent tasks will determine the minimum time required.

The optimal approach is to execute the most frequent tasks first, with cooling periods in between. If we have other tasks, we can use them to fill the cooling periods. If we don't have enough other tasks, we'll need to insert idle time.

Let's break down the approach:
1. Count the frequency of each task
2. Find the task with the maximum frequency (maxFreq)
3. Calculate the minimum time required for this task: (maxFreq - 1) * (n + 1) + (count of tasks with maxFreq)
4. Return the maximum of this calculated time and the total number of tasks

The formula (maxFreq - 1) * (n + 1) + (count of tasks with maxFreq) can be understood as:
- We need (maxFreq - 1) complete cycles, each of length (n + 1)
- Plus one final row containing all tasks with maxFreq

However, if we have many different tasks, we might be able to execute all tasks without any idle time. In that case, the answer is simply the total number of tasks.

### Code Mapping

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Count task frequencies | `int[] frequencies = new int[26];` |
| Find maximum frequency | `int maxFreq = 0; for (int freq : frequencies) { maxFreq = Math.max(maxFreq, freq); }` |
| Count tasks with max frequency | `int maxCount = 0; for (int freq : frequencies) { if (freq == maxFreq) maxCount++; }` |
| Calculate minimum time | `return Math.max((maxFreq - 1) * (n + 1) + maxCount, tasks.length);` |

### Final Java Code & Learning Pattern

```java
// [Pattern: Greedy with Frequency Counting]
class Solution {
    public int leastInterval(char[] tasks, int n) {
        // Count the frequency of each task
        int[] frequencies = new int[26];
        for (char task : tasks) {
            frequencies[task - 'A']++;
        }
        
        // Find the maximum frequency
        int maxFreq = 0;
        for (int freq : frequencies) {
            maxFreq = Math.max(maxFreq, freq);
        }
        
        // Count how many tasks have the maximum frequency
        int maxCount = 0;
        for (int freq : frequencies) {
            if (freq == maxFreq) {
                maxCount++;
            }
        }
        
        // Calculate the minimum time required
        // Formula: (maxFreq - 1) * (n + 1) + maxCount
        // This represents (maxFreq - 1) complete cycles of length (n + 1),
        // plus one final row containing all tasks with maxFreq
        int minTime = (maxFreq - 1) * (n + 1) + maxCount;
        
        // If we have many different tasks, we might not need any idle time
        return Math.max(minTime, tasks.length);
    }
}
```

### Alternative Implementation (Using Priority Queue)

```java
// [Pattern: Priority Queue with Greedy Scheduling]
class Solution {
    public int leastInterval(char[] tasks, int n) {
        // Count the frequency of each task
        Map<Character, Integer> taskFreq = new HashMap<>();
        for (char task : tasks) {
            taskFreq.put(task, taskFreq.getOrDefault(task, 0) + 1);
        }
        
        // Create a max heap based on task frequency
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        maxHeap.addAll(taskFreq.values());
        
        int cycles = 0;
        
        // Process tasks until the heap is empty
        while (!maxHeap.isEmpty()) {
            // Tasks that can be executed in the current cycle
            List<Integer> temp = new ArrayList<>();
            
            // Try to execute n+1 different tasks (one cycle)
            for (int i = 0; i <= n; i++) {
                if (!maxHeap.isEmpty()) {
                    temp.add(maxHeap.poll());
                }
            }
            
            // Decrement the frequency of executed tasks and add back to heap if needed
            for (int freq : temp) {
                if (--freq > 0) {
                    maxHeap.offer(freq);
                }
            }
            
            // If heap is empty, we've processed all tasks, so add the exact number of tasks executed
            // Otherwise, we completed a full cycle of n+1 units
            cycles += maxHeap.isEmpty() ? temp.size() : n + 1;
        }
        
        return cycles;
    }
}
```

### Complexity Analysis
- **Time Complexity**: $O(m)$ where m is the number of tasks. We iterate through the tasks once to count frequencies, and the operations on the frequency array are O(1) since it has a fixed size of 26.
- **Space Complexity**: $O(1)$ since we use a fixed-size array of 26 characters.

For the priority queue approach:
- **Time Complexity**: $O(m \log 26)$ which simplifies to $O(m)$ since 26 is a constant.
- **Space Complexity**: $O(1)$ for storing at most 26 different task frequencies.

### Similar Problems
1. **LeetCode 767: Reorganize String** - Rearrange characters in a string so no adjacent characters are the same.
2. **LeetCode 358: Rearrange String k Distance Apart** - Rearrange a string such that the same characters are at least k distance apart.
3. **LeetCode 1054: Distant Barcodes** - Rearrange barcodes so that no two adjacent barcodes are equal.
4. **LeetCode 1405: Longest Happy String** - Construct the lexicographically smallest string with constraints on character counts.
5. **LeetCode 984: String Without AAA or BBB** - Construct a string with constraints on consecutive characters.
6. **LeetCode 1953: Maximum Number of Weeks for Which You Can Work** - Schedule tasks with dependencies.
7. **LeetCode 1834: Single-Threaded CPU** - Schedule tasks based on processing time and arrival time.
8. **LeetCode 1882: Process Tasks Using Servers** - Assign tasks to servers based on availability and weight.
