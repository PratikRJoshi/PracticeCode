# Shopping Offers

## Problem Description

**Problem Link:** [Shopping Offers](https://leetcode.com/problems/shopping-offers/)

In LeetCode Store, there are `n` items to sell. Each item has a price. However, there are some special offers, and a special offer consists of one or more different kinds of items with a sale price.

You are given an integer array `price` where `price[i]` is the price of the `i`th item, and an integer array `needs` where `needs[i]` is the number of pieces of the `i`th item you want to buy.

You are also given an array `special` where `special[i]` is of size `n + 1` where `special[i][j]` is the number of `j`th item in the `i`th offer and `special[i][n]` (i.e., the last integer in the array) is the price of the `i`th offer.

Return *the lowest price you have to pay for exactly the items as given, where you could make optimal use of the special offers*. You are not allowed to buy more items than you want, even if that would lower the overall price.

**Example 1:**

```
Input: price = [2,5], special = [[3,0,5],[1,2,10]], needs = [3,2]
Output: 14
Explanation: There are two kinds of items, A and B. Their prices are $2 and $5 respectively. 
In special offer 1, you can pay $5 for 3A and 0B
In special offer 2, you can pay $10 for 1A and 2B. 
You need to buy 3A and 2B, so you may pay $10 for 1A and 2B (special offer #2), and $4 for 2A.
```

**Example 2:**

```
Input: price = [2,3,4], special = [[1,1,0,4],[2,2,1,9]], needs = [1,2,1]
Output: 11
Explanation: The price of A is $2, and $3 for B, $4 for C. 
You may pay $4 for 1A and 1B, and $9 for 2A, 2B and 1C. 
You need to buy 1A, 2B, and 1C, so you may pay $4 for 1A and 1B (special offer #1), and $3 for 1B, and $4 for 1C.
You cannot add more items, though only $9 for 2A, 2B and 1C.
```

**Constraints:**
- `n == price.length == needs.length`
- `1 <= n <= 6`
- `0 <= price[i] <= 10`
- `0 <= needs[i] <= 10`
- `1 <= special.length <= 100`
- `special[i].length == n + 1`
- `0 <= special[i][j] <= 10`

## Intuition/Main Idea

This is a **backtracking with memoization** problem. We need to find the minimum cost to buy exactly the needed items.

**Core Algorithm:**
1. For each state (remaining needs), try:
   - Buy items at regular price.
   - Apply each special offer if valid.
2. Use memoization to avoid recomputing the same state.
3. Return the minimum cost.

**Why backtracking works:** We explore all possible ways to buy items (regular price or special offers) and find the minimum cost. Memoization avoids recomputing the same needs state.

## Code Mapping

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Memoization | Memo map - Line 6 |
| Calculate regular price | Regular price calculation - Lines 10-13 |
| Try each special offer | Special offer loop - Lines 15-25 |
| Check if offer valid | Validity check - Lines 17-20 |
| Apply offer | Needs update - Lines 22-24 |
| Recurse | Recursive call - Line 25 |
| Restore state | State restoration - Line 26 |
| Return result | Return statement - Line 28 |

## Final Java Code & Learning Pattern

### Top-Down / Memoized Version

```java
import java.util.*;

class Solution {
    public int shoppingOffers(List<Integer> price, List<List<Integer>> special, List<Integer> needs) {
        Map<String, Integer> memo = new HashMap<>();
        return minCost(price, special, needs, memo);
    }
    
    private int minCost(List<Integer> price, List<List<Integer>> special, List<Integer> needs, Map<String, Integer> memo) {
        String key = needs.toString();
        if (memo.containsKey(key)) {
            return memo.get(key);
        }
        
        // Calculate cost without any special offers
        int cost = 0;
        for (int i = 0; i < needs.size(); i++) {
            cost += needs.get(i) * price.get(i);
        }
        
        // Try each special offer
        for (List<Integer> offer : special) {
            List<Integer> newNeeds = new ArrayList<>(needs);
            boolean valid = true;
            
            // Check if offer can be applied
            for (int i = 0; i < needs.size(); i++) {
                if (offer.get(i) > needs.get(i)) {
                    valid = false;
                    break;
                }
                newNeeds.set(i, needs.get(i) - offer.get(i));
            }
            
            if (valid) {
                cost = Math.min(cost, offer.get(needs.size()) + minCost(price, special, newNeeds, memo));
            }
        }
        
        memo.put(key, cost);
        return cost;
    }
}
```

**Explanation of Key Code Sections:**

1. **Memoization (Lines 6-9):** Use needs state as key to cache results.

2. **Regular Price (Lines 11-14):** Calculate cost without special offers.

3. **Try Special Offers (Lines 16-28):** For each offer:
   - **Check Validity (Lines 18-22):** Ensure offer doesn't exceed needs.
   - **Apply Offer (Lines 23-24):** Update needs after applying offer.
   - **Recurse (Line 25):** Calculate minimum cost for remaining needs.
   - **Take Minimum (Line 25):** Compare with regular price.

**Intuition behind generating subproblems:**
- **Subproblem:** "What is the minimum cost to buy items with given needs?"
- **Why this works:** To minimize cost, we try all options: regular price or special offers. After applying an offer, we recursively solve for remaining needs.
- **Overlapping subproblems:** Multiple paths may lead to the same needs state.

**Example walkthrough for `price=[2,5], special=[[3,0,5],[1,2,10]], needs=[3,2]`:**
- Regular: 3×2 + 2×5 = 16
- Try offer 1: [3,0,5] → needs=[0,2] → cost=5+10=15
- Try offer 2: [1,2,10] → needs=[2,0] → cost=10+4=14
- Result: min(16,15,14) = 14 ✓

## Complexity Analysis

- **Time Complexity:** $O(m \times n \times k)$ where $m$ is number of offers, $n$ is number of items, and $k$ is the number of distinct needs states.

- **Space Complexity:** $O(k)$ for memoization.

## Similar Problems

Problems that can be solved using similar backtracking with memoization patterns:

1. **638. Shopping Offers** (this problem) - Backtracking with memoization
2. **698. Partition to K Equal Sum Subsets** - Backtracking with memoization
3. **473. Matchsticks to Square** - Backtracking with memoization
4. **2305. Fair Distribution of Cookies** - Backtracking
5. **1255. Maximum Score Words Formed by Letters** - Backtracking
6. **1947. Maximum Compatibility Score Sum** - Backtracking
7. **526. Beautiful Arrangement** - Backtracking
8. **980. Unique Paths III** - Backtracking

