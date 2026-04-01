# HPA (Horizontal Pod Autoscaler)

## 1) Problem Description

Given initial `pods[]` and logs:

- `[1, p, x]`: set `pods[p] = x`
- `[2, -1, x]`: set every service with pod count `< x` to `x`

Return final pod counts after all logs.

## 2) Intuition/Main Idea

Forward simulation of type-2 logs is expensive (`O(nm)` worst case).

Process logs in reverse:

- Keep `globalFloor` = maximum threshold from type-2 operations seen so far in reverse (i.e., applied after current point in forward order).
- For each service index, only the **last** type-1 assignment in forward order matters.

Reverse rule:
- when visiting type-1 `[1,p,x]` first time for `p`, final value of `p` is `max(x, globalFloor)`.

After reverse pass, any service never assigned by type-1 keeps original value, then still affected by all later floors, so final is `max(initial, globalFloor)`.

### Why this intuition works

- Reverse traversal captures all operations that happen after a point as a compact floor value.
- First seen type-1 in reverse is last write in forward order (write-after-write resolution).

### How to derive it step by step

1. Initialize result with sentinel (unresolved).
2. Traverse logs from end to start.
3. Update `globalFloor` for type-2.
4. Resolve each index once on first reverse type-1 encounter.
5. Fill unresolved indices from initial array with floor applied.

## 3) Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
| --- | --- |
| @PointUpdateType1 | reverse handling for log type `1` |
| @GlobalFloorType2 | `globalFloor = Math.max(globalFloor, x)` |
| @FinalValueAfterAllLogs | resolve with `Math.max(value, globalFloor)` |
| @LargeNAndMLimits | single reverse pass + single final fill pass |

## 4) Final Java Code & Learning Pattern (Full Content)

```java
import java.util.*;

class Result {

    public static List<Integer> findPodCount(List<Integer> pods, List<List<Integer>> logs) {
        int n = pods.size();
        int[] finalPods = new int[n];
        Arrays.fill(finalPods, Integer.MIN_VALUE);

        int globalFloor = 0;

        for (int index = logs.size() - 1; index >= 0; index--) {
            List<Integer> log = logs.get(index);
            int type = log.get(0);

            if (type == 2) {
                int threshold = log.get(2);
                globalFloor = Math.max(globalFloor, threshold);
            } else {
                int serviceIndex = log.get(1) - 1; // input is 1-based
                int assignedValue = log.get(2);

                if (finalPods[serviceIndex] == Integer.MIN_VALUE) {
                    finalPods[serviceIndex] = Math.max(assignedValue, globalFloor);
                }
            }
        }

        List<Integer> answer = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            if (finalPods[i] == Integer.MIN_VALUE) {
                answer.add(Math.max(pods.get(i), globalFloor));
            } else {
                answer.add(finalPods[i]);
            }
        }

        return answer;
    }
}
```

Learning Pattern:
- For mixed point updates + global min-floor updates, reverse processing often converts expensive propagation into lazy aggregation.

## 5) Complexity Analysis

- Time Complexity: $O(n + m)$
- Space Complexity: $O(n)$

## Similar Problems

- [LeetCode 2034: Stock Price Fluctuation](https://leetcode.com/problems/stock-price-fluctuation/) (latest update dominates)
- [LeetCode 370: Range Addition](https://leetcode.com/problems/range-addition/) (lazy accumulation style thinking)
