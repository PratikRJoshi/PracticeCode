### 621. Task Scheduler
### Problem Link: [Task Scheduler](https://leetcode.com/problems/task-scheduler/)

### Problem Description
You are given an array of CPU `tasks`, each represented by letters A to Z, and a cooling time `n`. Each cycle or interval allows the completion of one task. Tasks can be completed in any order, but there's a constraint: **identical tasks must be separated by at least `n` intervals** due to cooling time.

Return the *minimum number of intervals* required to complete all tasks.

**Example 1:**
```
Input: tasks = ["A","A","A","B","B","B"], n = 2
Output: 8
Explanation: A possible sequence is: A -> B -> idle -> A -> B -> idle -> A -> B.
After completing task A, you must wait two cycles before doing A again. The same applies to task B. In the 3rd interval, neither A nor B can be done, so you idle. By the 4th cycle, you can do A again as 2 intervals have passed.
```

**Example 2:**
```
Input: tasks = ["A","C","A","B","D","B"], n = 1
Output: 6
Explanation: A possible sequence is: A -> B -> C -> D -> A -> B.
With a cooling interval of 1, you can repeat a task after just one other task.
```

**Example 3:**
```
Input: tasks = ["A","A","A","B","B","B"], n = 3
Output: 10
Explanation: A possible sequence is: A -> B -> idle -> idle -> A -> B -> idle -> idle -> A -> B.
There are only two types of tasks, A and B, which need to be separated by 3 intervals. This leads to idling twice between repetitions of these tasks.
```

**Constraints:**
- `1 <= tasks.length <= 10^4`
- `tasks[i]` is an uppercase English letter.
- `0 <= n <= 100`

---

### Intuition / Main Idea

The problem asks for the minimum time required to execute all tasks given a cooling period `n` between identical tasks. 

#### Why the Intuition Works
The total time taken is dictated by the **most frequent task(s)**. Let's say task `A` appears the most times. If we schedule all the `A`s as early as possible, we have to insert `n` empty slots (idles) between each `A`. 
For example, if `A` appears 3 times and `n = 2`, the minimum skeleton looks like this:
`A -> idle -> idle -> A -> idle -> idle -> A`

The other less frequent tasks (like `B`, `C`) can simply be slotted into these `idle` spaces! If we have enough other tasks to fill all the idles, the total time is just the length of the `tasks` array. If we don't have enough, the total time is the size of this "skeleton" we built.

#### How to Derive It Step by Step

1. **Find the maximum frequency:** Let's find the task that occurs the most. Let's call its count `maxFreq`.
2. **Build the skeleton blocks:** The most frequent task will divide the timeline into `maxFreq - 1` blocks (or chunks) of waiting time. In our example above with 3 `A`s, we have `3 - 1 = 2` blocks of waiting time between the `A`s.
3. **Calculate block size:** Each block needs to be of size `n` (the cooling period) so that the next `A` can be placed legally. 
4. **Calculate total skeleton time:** The total time to execute just these blocks is `(maxFreq - 1) * (n + 1)`. Wait, why `n + 1`? Because each block conceptually consists of the task itself followed by `n` idles. 
5. **Handle the final row:** What about the very last occurrence of `A`? It doesn't need trailing idle times because no more `A`s are coming. So we just add `1` to represent that final `A`.
   - Formula so far: `(maxFreq - 1) * (n + 1) + 1`
6. **Handle ties for maximum frequency:** What if both `A` and `B` appear 3 times? The skeleton ends with `A B`, so the final row length isn't just `1`, it's the number of tasks that share this maximum frequency. Let's call this `maxCount`.
   - Final formula: `(maxFreq - 1) * (n + 1) + maxCount`
7. **The fallback:** What if `n` is very small or there are tons of other tasks? We might fill all the idle slots and still have tasks left over. Since we can just append them at the end (or interleave them without violating the `n` rule), the total time will just be the total number of tasks. So we return `Math.max(calculated_time, tasks.length)`.

---

### Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
| :--- | :--- |
| Track task frequencies | `int[] taskFrequencies = new int[26];` |
| Find the maximum frequency (`maxFreq`) | `int maxFreq = 0; ... maxFreq = Math.max(maxFreq, freq);` |
| Count how many tasks tie for `maxFreq` | `int tasksWithMaxFreq = 0; ... if (freq == maxFreq) tasksWithMaxFreq++;` |
| Calculate skeleton size based on `n` | `int minimumIntervals = (maxFreq - 1) * (n + 1) + tasksWithMaxFreq;` |
| Return total tasks if it exceeds skeleton | `return Math.max(minimumIntervals, tasks.length);` |

---

### Final Java Code & Learning Pattern (Full Content)

```java
class Solution {
    public int leastInterval(char[] tasks, int n) {
        // We only have uppercase English letters, so an array of size 26 is perfect for counting.
        int[] taskFrequencies = new int[26];
        
        // Step 1: Count the frequency of each task
        for (char task : tasks) {
            taskFrequencies[task - 'A']++;
        }
        
        // Step 2: Find the highest frequency among all tasks
        int maxFreq = 0;
        for (int freq : taskFrequencies) {
            maxFreq = Math.max(maxFreq, freq);
        }
        
        // Step 3: Count how many distinct tasks have this maximum frequency.
        // If A appears 3 times and B appears 3 times, maxFreq is 3, and tasksWithMaxFreq is 2.
        int tasksWithMaxFreq = 0;
        for (int freq : taskFrequencies) {
            if (freq == maxFreq) {
                tasksWithMaxFreq++;
            }
        }
        
        // Step 4: Apply the greedy formula.
        // (maxFreq - 1) gives us the number of "intervals" or "blocks" between the most frequent tasks.
        // (n + 1) is the length of each block (1 task + n cooling periods).
        // tasksWithMaxFreq accounts for the final row of tasks that are executed at the very end.
        int minimumIntervals = (maxFreq - 1) * (n + 1) + tasksWithMaxFreq;
        
        // Step 5: If the calculated intervals are less than the total number of tasks, 
        // it means we have enough distinct tasks to fill all cooling periods without any idle time.
        // In that case, the answer is simply the number of tasks.
        return Math.max(minimumIntervals, tasks.length);
    }
}
```

---

### Alternative Approach: Priority Queue (Simulation)

While the math formula is $O(N)$ and optimal, understanding the simulation approach using a Max Heap (Priority Queue) is highly valuable because it generalizes well to other scheduling problems where tasks have different weights or release times.

#### Intuition for Max Heap
We want to always schedule the **most frequent available task** to prevent it from piling up and forcing idle time later. 
1. We keep a `Max Heap` of task frequencies.
2. We simulate time in cycles of length `n + 1`.
3. In each cycle, we pop up to `n + 1` tasks from the heap, execute them (decrement their frequency), and put them back in a temporary list.
4. After the cycle finishes, we push the remaining tasks from the temporary list back into the heap for the next cycle.
5. We add the time taken to our total (either `n + 1` if the heap is not empty, or just the number of tasks we actually executed if the heap is empty and we're done).

#### Priority Queue Java Code

```java
class Solution {
    public int leastInterval(char[] tasks, int n) {
        // Step 1: Count frequencies
        int[] taskFrequencies = new int[26];
        for (char task : tasks) {
            taskFrequencies[task - 'A']++;
        }
        
        // Step 2: Build a Max Heap to always pick the most frequent task
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        for (int freq : taskFrequencies) {
            if (freq > 0) {
                maxHeap.offer(freq);
            }
        }
        
        int totalTime = 0;
        
        // Step 3: Simulate the cycles
        while (!maxHeap.isEmpty()) {
            List<Integer> waitList = new ArrayList<>();
            int tasksExecutedThisCycle = 0;
            
            // A full cycle is n + 1 slots
            for (int i = 0; i <= n; i++) {
                if (!maxHeap.isEmpty()) {
                    // Execute the most frequent task
                    int currentFreq = maxHeap.poll();
                    currentFreq--;
                    tasksExecutedThisCycle++;
                    
                    // If it still needs to be executed, put it in the waitlist
                    if (currentFreq > 0) {
                        waitList.add(currentFreq);
                    }
                }
            }
            
            // Put the waiting tasks back into the heap for the next cycle
            maxHeap.addAll(waitList);
            
            // If the heap is empty, we only add the exact number of tasks we executed
            // Otherwise, we had to wait out the full cycle of (n + 1)
            if (maxHeap.isEmpty()) {
                totalTime += tasksExecutedThisCycle;
            } else {
                totalTime += (n + 1);
            }
        }
        
        return totalTime;
    }
}
```

#### Complexity Analysis (Priority Queue)
- **Time Complexity:** $O(N \log(26)) = O(N)$. We process $N$ tasks. Each insertion/extraction from the Priority Queue takes $\log(26)$ time because there are at most 26 unique characters. Since $\log(26)$ is a constant, this simplifies to $O(N)$.
- **Space Complexity:** $O(1)$. The Priority Queue and the temporary `waitList` will hold at most 26 integers, which is constant space.

---

### Complexity Analysis

- **Time Complexity:** $O(N)$ where $N$ is the number of tasks in the input array. We iterate through the `tasks` array once to populate the frequency array. Iterating over the size-26 frequency array takes $O(1)$ constant time. Thus, the overall time complexity is $O(N)$.
- **Space Complexity:** $O(1)$ because we only use an integer array of size 26, which uses constant extra space regardless of the input size $N$.

---

### Similar Problems

- [767. Reorganize String](https://leetcode.com/problems/reorganize-string/) - Very similar greedy logic. You need to arrange characters such that no two adjacent characters are the same (equivalent to `n = 1`).
- [358. Rearrange String k Distance Apart](https://leetcode.com/problems/rearrange-string-k-distance-apart/) - Premium problem, but identical logic where identical characters must be at least `k` distance apart.
- [1054. Distant Barcodes](https://leetcode.com/problems/distant-barcodes/) - You must rearrange an array so that no two adjacent elements are equal.
- [1405. Longest Happy String](https://leetcode.com/problems/longest-happy-string/) - Construct a string using A, B, C with constraints on consecutive characters, heavily relying on priority queue / greedy frequency logic.
