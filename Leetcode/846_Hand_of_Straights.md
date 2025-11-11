# Hand of Straights

## Problem Description

**Problem Link:** [Hand of Straights](https://leetcode.com/problems/hand-of-straights/)

Alice has some number of cards and she wants to rearrange the cards into groups so that each group is of size `groupSize`, and consists of `groupSize` consecutive cards.

Given an integer array `hand` where `hand[i]` is the value written on the `i`th card and an integer `groupSize`, return `true` *if she can rearrange the cards, or `false` otherwise*.

**Example 1:**
```
Input: hand = [1,2,3,6,2,3,4,7,8], groupSize = 3
Output: true
Explanation: Alice's hand can be rearranged as [1,2,3],[2,3,4],[6,7,8]
```

**Example 2:**
```
Input: hand = [1,2,3,4,5], groupSize = 3
Output: false
Explanation: Alice's hand cannot be rearranged into groups of 3.
```

**Constraints:**
- `1 <= hand.length <= 10^4`
- `0 <= hand[i] <= 10^9`
- `1 <= groupSize <= hand.length`

## Intuition/Main Idea

This problem requires checking if we can partition the cards into groups of consecutive numbers. We can use a **greedy algorithm** with a **frequency map**.

**Core Algorithm:**
1. Count the frequency of each card value.
2. Sort the unique card values.
3. For each card value, if it has remaining frequency, try to form a group starting from that card.
4. For each group, check if we have consecutive cards for `groupSize` consecutive values.
5. Decrease the frequency of cards used in the group.

**Why greedy works:** If we can form a group starting from a card, we should form it immediately. Waiting doesn't help because we need consecutive cards, and if we skip a card now, we might not be able to use it later.

## Code Mapping

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Count frequency of each card | TreeMap - Lines 6-9 |
| Check if total cards divisible by groupSize | Validation - Lines 11-13 |
| Process each unique card value | For loop - Line 16 |
| Form group starting from current card | While loop - Lines 18-26 |
| Check consecutive cards availability | Frequency check - Line 21 |
| Decrease frequency after using | Decrement operation - Line 24 |
| Return result | Return statement - Line 28 |

## Final Java Code & Learning Pattern

```java
import java.util.TreeMap;

class Solution {
    public boolean isNStraightHand(int[] hand, int groupSize) {
        // Count frequency of each card value
        TreeMap<Integer, Integer> count = new TreeMap<>();
        for (int card : hand) {
            count.put(card, count.getOrDefault(card, 0) + 1);
        }
        
        // Check if total cards can be divided into groups
        if (hand.length % groupSize != 0) {
            return false;
        }
        
        // Process each unique card value
        while (!count.isEmpty()) {
            // Get the smallest card value
            int firstCard = count.firstKey();
            
            // Try to form a group starting from firstCard
            for (int i = 0; i < groupSize; i++) {
                int currentCard = firstCard + i;
                
                // Check if we have this card
                if (!count.containsKey(currentCard)) {
                    return false;
                }
                
                // Use one occurrence of this card
                count.put(currentCard, count.get(currentCard) - 1);
                
                // Remove card if frequency becomes 0
                if (count.get(currentCard) == 0) {
                    count.remove(currentCard);
                }
            }
        }
        
        return true;
    }
}
```

**Explanation of Key Code Sections:**

1. **Frequency Counting (Lines 6-9):** We use a `TreeMap` to count the frequency of each card value. `TreeMap` automatically keeps keys sorted, which helps us process cards in order.

2. **Validation (Lines 11-13):** If the total number of cards is not divisible by `groupSize`, it's impossible to form groups of that size, so we return false immediately.

3. **Greedy Group Formation (Lines 15-28):** 
   - **Get Smallest Card (Line 17):** We always start with the smallest available card. This ensures we form groups in order.
   - **Form Consecutive Group (Lines 19-26):** For `groupSize` consecutive values starting from `firstCard`:
     - **Check Availability (Line 21):** If we don't have the required card, we can't form the group, so return false.
     - **Use Card (Line 24):** Decrease the frequency of the card.
     - **Clean Up (Lines 26-28):** If a card's frequency becomes 0, remove it from the map.
   - **Repeat (Line 15):** Continue until all cards are used.

**Why greedy approach is correct:**
- **Lemma:** If we can form a group starting from card `x`, we should form it immediately.
- **Proof:** If we skip card `x` and try to use it later, we still need consecutive cards. But if we don't use `x` now, we might end up with gaps that prevent forming groups. Using the smallest available card ensures we don't create gaps unnecessarily.

**Example walkthrough for `hand = [1,2,3,6,2,3,4,7,8], groupSize = 3`:**
- Count: {1:1, 2:2, 3:2, 4:1, 6:1, 7:1, 8:1}
- Group 1: Start at 1 → use 1,2,3 → Count: {2:1, 3:1, 4:1, 6:1, 7:1, 8:1}
- Group 2: Start at 2 → use 2,3,4 → Count: {6:1, 7:1, 8:1}
- Group 3: Start at 6 → use 6,7,8 → Count: {} → Success!

## Complexity Analysis

- **Time Complexity:** $O(n \log n)$ where $n$ is the number of cards. We sort the cards (via TreeMap) and process each card once.

- **Space Complexity:** $O(n)$ for the TreeMap storing card frequencies.

## Similar Problems

Problems that can be solved using similar greedy and frequency counting patterns:

1. **846. Hand of Straights** (this problem) - Greedy consecutive grouping
2. **659. Split Array into Consecutive Subsequences** - Similar consecutive grouping
3. **1296. Divide Array in Sets of K Consecutive Numbers** - Exact same problem
4. **621. Task Scheduler** - Greedy task scheduling
5. **767. Reorganize String** - Greedy string reorganization
6. **358. Rearrange String k Distance Apart** - Greedy with distance constraint
7. **435. Non-overlapping Intervals** - Greedy interval selection
8. **452. Minimum Number of Arrows to Burst Balloons** - Greedy interval covering
9. **56. Merge Intervals** - Greedy interval merging
10. **763. Partition Labels** - Greedy partitioning

