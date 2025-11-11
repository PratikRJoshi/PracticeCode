# Gas Station

## Problem Description

**Problem Link:** [Gas Station](https://leetcode.com/problems/gas-station/)

There are `n` gas stations along a circular route, where the amount of gas at the `i`th station is `gas[i]`.

You have a car with an unlimited gas tank and it costs `cost[i]` of gas to travel from the `i`th station to its next `(i + 1)`th station. You begin the journey with an empty tank at one of the gas stations.

Given two integer arrays `gas` and `cost`, return *the starting gas station's index if you can travel around the circuit once in the clockwise direction, otherwise return `-1`*. If there exists a solution, it is **guaranteed to be unique**.

**Example 1:**
```
Input: gas = [1,2,3,4,5], cost = [3,4,5,1,2]
Output: 3
Explanation:
Start at station 3 (index 3) and fill up with 4 unit of gas. Your tank = 0 + 4 = 4
Travel to station 4. Your tank = 4 - 1 + 5 = 8
Travel to station 0. Your tank = 8 - 2 + 1 = 7
Travel to station 1. Your tank = 7 - 2 + 3 = 8
Travel to station 2. Your tank = 8 - 3 + 3 = 8
Travel to station 3. The cost is 3. Your tank = 8 - 3 = 5, which is enough to return to station 3.
Therefore, return 3 as the starting index.
```

**Example 2:**
```
Input: gas = [2,3,4], cost = [3,4,3]
Output: -1
Explanation:
You can't start at any station to make the trip around.
```

**Constraints:**
- `gas.length == n`
- `cost.length == n`
- `1 <= n <= 10^5`
- `0 <= gas[i], cost[i] <= 10^4`

## Intuition/Main Idea

This problem can be solved using a **greedy algorithm** with a key insight about the circular route.

**Key Insights:**
1. If the total gas is less than total cost, it's impossible to complete the circuit.
2. If we start at station `i` and run out of gas at station `j`, then starting at any station between `i` and `j` will also fail at station `j`. This is because we would have even less gas when reaching `j`.
3. Therefore, if we fail at station `j` starting from `i`, we should try starting from `j + 1` next.

**Core Algorithm:**
1. Check if total gas >= total cost. If not, return -1.
2. Start from station 0 and track the current tank level.
3. If tank becomes negative at station `i`, reset and try starting from `i + 1`.
4. If we complete the circuit, return the starting index.

**Why this works:** The greedy approach is valid because if we can't reach station `j` from station `i`, we can't reach it from any station between `i` and `j` either. So we can skip all those stations and try from `j + 1`.

## Code Mapping

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Check if solution exists | Total gas vs total cost check - Lines 6-9 |
| Track current tank level | `tank` variable - Line 11 |
| Track starting station | `start` variable - Line 12 |
| Process each station | For loop - Line 14 |
| Update tank after visiting station | Tank update - Line 15 |
| Reset if tank becomes negative | Reset logic - Lines 16-19 |
| Return starting index | Return statement - Line 22 |

## Final Java Code & Learning Pattern

```java
class Solution {
    public int canCompleteCircuit(int[] gas, int[] cost) {
        int n = gas.length;
        int totalGas = 0, totalCost = 0;
        
        // Check if total gas is sufficient
        for (int i = 0; i < n; i++) {
            totalGas += gas[i];
            totalCost += cost[i];
        }
        
        // If total gas < total cost, impossible to complete circuit
        if (totalGas < totalCost) {
            return -1;
        }
        
        // Greedy approach: find starting point
        int tank = 0;
        int start = 0;
        
        for (int i = 0; i < n; i++) {
            // Add gas and subtract cost for current station
            tank += gas[i] - cost[i];
            
            // If tank becomes negative, we can't reach here from start
            // Try starting from next station
            if (tank < 0) {
                tank = 0;  // Reset tank
                start = i + 1;  // Try next starting point
            }
        }
        
        return start;
    }
}
```

**Explanation of Key Code Sections:**

1. **Feasibility Check (Lines 6-13):** We first check if the total gas available is at least equal to the total cost. If not, it's impossible to complete the circuit, so we return -1 immediately.

2. **Greedy Search (Lines 15-25):** We iterate through all stations:
   - **Tank Update (Line 17):** At each station, we add the gas and subtract the cost. `tank += gas[i] - cost[i]` represents the net change in gas after visiting station `i`.
   - **Negative Tank Detection (Lines 19-22):** If the tank becomes negative, it means we can't reach the current station from our starting point. According to the key insight, we can't reach here from any station between `start` and `i` either, so we reset and try starting from `i + 1`.
   - **Tank Reset (Line 20):** We reset the tank to 0 because we're starting fresh from a new station.

3. **Return Starting Index (Line 25):** If we complete the loop without returning -1, `start` contains the valid starting index. This works because:
   - We've verified total gas >= total cost (feasibility)
   - We've found a starting point where the tank never goes negative
   - Since the total is sufficient and we can complete from `start`, this must be the solution

**Why the greedy approach is correct:**
- **Lemma:** If we start at station `i` and fail at station `j` (tank < 0), then starting at any station between `i` and `j` will also fail at station `j`.
- **Proof:** If we start at `i`, we have `gas[i]` at station `i`. If we start at `i+1`, we have `gas[i+1]` but we've "lost" the net gas from station `i` (which was `gas[i] - cost[i]`). Since we failed starting from `i`, the cumulative gas from `i` to `j-1` was negative, so starting from `i+1` gives us even less gas.
- **Conclusion:** We can safely skip all stations between `i` and `j` and try starting from `j+1`.

**Example walkthrough for `gas = [1,2,3,4,5], cost = [3,4,5,1,2]`:**
- Total gas = 15, total cost = 15 ✓ (feasible)
- Start at 0: tank = 1-3 = -2 (fail, try start = 1)
- Start at 1: tank = 2-4 = -2 (fail, try start = 2)
- Start at 2: tank = 3-5 = -2 (fail, try start = 3)
- Start at 3: tank = 4-1 = 3, then 3+5-2 = 6, then 6+1-3 = 4, then 4+2-4 = 2, then 2+3-5 = 0 ✓ (success)

## Complexity Analysis

- **Time Complexity:** $O(n)$ where $n$ is the number of gas stations. We iterate through the array once.

- **Space Complexity:** $O(1)$ as we only use a constant amount of extra space.

## Similar Problems

Problems that can be solved using similar greedy or circular array techniques:

1. **134. Gas Station** (this problem) - Greedy on circular route
2. **55. Jump Game** - Greedy reachability
3. **45. Jump Game II** - Greedy with minimum jumps
4. **121. Best Time to Buy and Sell Stock** - Greedy profit maximization
5. **122. Best Time to Buy and Sell Stock II** - Greedy with multiple transactions
6. **135. Candy** - Greedy distribution
7. **330. Patching Array** - Greedy patching
8. **763. Partition Labels** - Greedy partitioning
9. **406. Queue Reconstruction by Height** - Greedy insertion
10. **435. Non-overlapping Intervals** - Greedy interval selection

