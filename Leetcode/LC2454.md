### LeetCode 2454: Next Greater Element IV (Second Greater Element)
Problem: https://leetcode.com/problems/next-greater-element-iv/

---

## Problem Statement
For each index `i` in an integer array `nums`, find the **second** element to the right that is **greater** than `nums[i]`. If it doesn’t exist, store `-1`.

---

## Intuition – Two-Pass Stack + Min-Heap
A single monotone stack gives the *first* greater element.  We need the *second* greater.

Idea:
1. First pass left→right with decreasing stack `st1` to resolve **first** greater indices.  For each `j` popped (whose first greater is at `i`), we **enqueue** `j` into a bucket list keyed by `i`.
2. Second pass left→right, iterate `i`.  Maintain a **min-heap** `wait` of indices whose *first* greater was met and now wait for their *second* greater.  When we reach index `i`, pop from `wait` all indices with `nums[heapTop] < nums[i]`, assign `ans[idx] = nums[i]`.

---

## Mapping Requirements → Code
| Requirement | Code lines |
|-------------|-----------|
| Build first-greater buckets | stack loop (18-25) |
| Min-heap of candidates | PriorityQueue<Integer> wait (27) |
| Add indices whose first greater == i | for(int idx: bucket[i]) (34-35) |
| Pop heap while current > nums[idx] | while(!wait.isEmpty() && nums[wait.peek()] < nums[i]) (36-39) |
| Return ans | 42 |

---

## Java Implementation
```java
class Solution {
    public int[] secondGreaterElement(int[] nums) {
        int n = nums.length;
        int[] ans = new int[n];
        Arrays.fill(ans, -1);

        List<List<Integer>> bucket = new ArrayList<>();
        for(int i=0;i<n;i++) bucket.add(new ArrayList<>());
        Deque<Integer> st = new ArrayDeque<>(); // first greater stack

        // Pass1: find first greater positions, store indices waiting for second greater under that pos
        for(int i=0;i<n;i++){
            while(!st.isEmpty() && nums[i] > nums[st.peek()]){
                int idx = st.pop();
                bucket.get(i).add(idx); // idx got its first greater at i
            }
            st.push(i);
        }

        // Pass2: sweep again, heap handles second greater
        PriorityQueue<Integer> wait = new PriorityQueue<>((a,b)->Integer.compare(nums[a], nums[b]));
        for(int i=0;i<n;i++){
            // add new indices whose first greater is i
            for(int idx: bucket.get(i)) wait.offer(idx);

            // resolve second greater for those smaller than nums[i]
            while(!wait.isEmpty() && nums[wait.peek()] < nums[i]){
                int idx = wait.poll();
                ans[idx] = nums[i];
            }
        }
        return ans;
    }
}
```

---

## Complexity
Time `O(n log n)` (each index enters/leaves heap once). Space `O(n)`.

---

## Pattern Relation
Extends monotone-stack idea: first stack gives *first* greater; a secondary structure (heap) tracks waiting indices for *second* greater — layering two “next greater” discoveries.