# Minimum Amount

## 1) Problem Description

Given `prices[]`, items must be purchased in order.

Rules:
- First item is paid in full.
- For each next item, discount equals the minimum price seen before this item.
- Item price cannot become negative.

So cost at index `i` is:

```text
max(prices[i] - min(prices[0..i-1]), 0)
```

Return total cost.

## 2) Intuition/Main Idea

At every step, only one historical value matters: the smallest prior price.

### Why this intuition works

- Discount for current item depends only on previous minimum, not full history.
- So we can process left to right with a running variable `minimumPriceSoFar`.
- Each step is constant time.

### How to derive it step by step

1. Pay first item fully and initialize running minimum.
2. For each next item, compute discounted cost with running minimum.
3. Add max(discounted cost, 0).
4. Update running minimum with current original price.

## 3) Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
| --- | --- |
| @OrderMustBePreserved | single left-to-right loop on `prices` |
| @DiscountUsesPreviousMinimum | `minimumPriceSoFar` variable |
| @CostCannotBeNegative | `Math.max(currentPrice - minimumPriceSoFar, 0)` |
| @ReturnTotalAmount | accumulate in `long totalCost` |

## 4) Final Java Code & Learning Pattern (Full Content)

```java
import java.util.*;

class Result {

    public static long getMinimumAmount(List<Integer> prices) {
        if (prices == null || prices.isEmpty()) {
            return 0L;
        }

        long totalCost = prices.get(0);
        int minimumPriceSoFar = prices.get(0);

        for (int index = 1; index < prices.size(); index++) {
            int currentPrice = prices.get(index);
            int discountedCost = Math.max(currentPrice - minimumPriceSoFar, 0);
            totalCost += discountedCost;

            minimumPriceSoFar = Math.min(minimumPriceSoFar, currentPrice);
        }

        return totalCost;
    }
}
```

Learning Pattern:
- If each step depends on a prefix summary (min/max/sum), maintain that summary incrementally.
- Convert nested-prefix logic into a single scan.

## 5) Complexity Analysis

- Time Complexity: $O(n)$
- Space Complexity: $O(1)$

## Similar Problems

- [LeetCode 121: Best Time to Buy and Sell Stock](https://leetcode.com/problems/best-time-to-buy-and-sell-stock/) (running minimum pattern)
- [LeetCode 2016: Maximum Difference Between Increasing Elements](https://leetcode.com/problems/maximum-difference-between-increasing-elements/) (prefix minimum tracking)