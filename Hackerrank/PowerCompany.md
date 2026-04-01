# Power Company ([LeetCode 1338: Reduce Array Size to The Half](https://leetcode.com/problems/reduce-array-size-to-the-half/))

## 1) Problem Description

You are given an array `model` where each element is a generator model ID.

If a model is chosen, **all generators of that model** are deactivated together.

Find the minimum number of distinct models to deactivate so that at least half of all generators are shut down.

Example:

```text
model = [3, 4, 6, 11, 9, 9, 9, 9, 8, 8, 8, 8, 8, 8]
n = 14
need to deactivate at least ceil(14/2) = 7 generators
```

Choosing models `8` (6 generators) and `9` (4 generators) deactivates `10` generators, so answer is `2`.

Constraints:
- `1 <= n <= 10^5`
- `1 <= model[i] <= 10^6`

## 2) Intuition/Main Idea

If each chosen model removes some count of generators, then to minimize number of chosen models we should always take the model with the largest count first.

### Why this intuition works

- This is a classic greedy setup:
  - each model contributes a "gain" = its frequency.
  - objective: reach target gain (`ceil(n/2)`) using minimum picks.
- Picking larger frequencies first reaches target in fewer picks than picking smaller ones first.

### How to derive it step by step

1. Count frequency of each model.
2. Sort frequencies in descending order.
3. Keep adding largest frequencies until removed count reaches at least half.
4. Number of selected frequencies is answer.

## 3) Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
| --- | --- |
| @GroupByModel | `frequencyByModel.merge(modelId, 1, Integer::sum)` |
| @DeactivateAllOfChosenModel | each chosen frequency adds full count from one model |
| @AtLeastHalfRemoved | `targetToRemove = (totalGenerators + 1) / 2` |
| @MinimumDistinctModels | greedy descending scan over sorted frequencies |

## 4) Final Java Code & Learning Pattern (Full Content)

```java
import java.util.*;

class Result {

    public static int getMinimumModelsToDeactivate(List<Integer> model) {
        Map<Integer, Integer> frequencyByModel = new HashMap<>();
        for (int modelId : model) {
            frequencyByModel.merge(modelId, 1, Integer::sum);
        }

        List<Integer> frequencies = new ArrayList<>(frequencyByModel.values());
        frequencies.sort(Collections.reverseOrder());

        int totalGenerators = model.size();
        int targetToRemove = (totalGenerators + 1) / 2;

        int removedSoFar = 0;
        int selectedModelCount = 0;

        for (int frequency : frequencies) {
            removedSoFar += frequency;
            selectedModelCount++;

            if (removedSoFar >= targetToRemove) {
                break;
            }
        }

        return selectedModelCount;
    }
}
```

Learning Pattern:
- When task says "minimum number of groups/items to reach threshold", first think of greedy by contribution size.
- Convert raw data into frequency/count contributions, then pick largest contributions first.

## 5) Complexity Analysis

- Time Complexity: $O(n + m \log m)$, where `m` is number of distinct models.
- Space Complexity: $O(m)$ for frequency map and frequency list.

## Similar Problems

- [LeetCode 1338: Reduce Array Size to The Half](https://leetcode.com/problems/reduce-array-size-to-the-half/)
- [LeetCode 347: Top K Frequent Elements](https://leetcode.com/problems/top-k-frequent-elements/) (frequency-first greedy/selection pattern)