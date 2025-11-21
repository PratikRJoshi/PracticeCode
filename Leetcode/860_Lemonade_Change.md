# Lemonade Change

## Problem Description

**Problem Link:** [Lemonade Change](https://leetcode.com/problems/lemonade-change/)

At a lemonade stand, each lemonade costs `$5`. Customers are standing in a queue to buy from you and order one at a time (in the order specified by bills). Each customer will only buy one lemonade and pay with either a `$5`, `$10`, or `$20` bill. You must provide the correct change to each customer so that the net transaction is that the customer pays `$5`.

Note that you do not have any change in hand at first.

Given an integer array `bills` where `bills[i]` is the bill the `i`th customer pays, return `true` *if you can provide every customer with the correct change, or* `false` *otherwise*.

**Example 1:**
```
Input: bills = [5,5,5,10,20]
Output: true
Explanation: 
From the first 3 customers, we collect three $5 bills in order.
From the fourth customer, we collect a $10 bill and give back a $5.
From the fifth customer, we collect a $20 bill and give back a $10 and a $5.
Since all customers got correct change, we output true.
```

**Constraints:**
- `1 <= bills.length <= 10^5`
- `bills[i]` is either `5`, `10`, or `20`.

## Intuition/Main Idea

This is a greedy problem. We need to give change optimally.

**Core Algorithm:**
- Track count of $5 and $10 bills
- For $5: no change needed, increment count
- For $10: need $5 change, use one $5 bill
- For $20: prefer giving $10+$5 over $5+$5+$5 (greedy choice)

**Why greedy:** Always prefer using larger bills for change when possible. This maximizes availability of smaller bills for future transactions.

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|------------------------|-----------------------------------|
| Track bill counts | Count variables - Lines 5-6 |
| Handle $5 bill | Increment count - Lines 10-11 |
| Handle $10 bill | Use $5 change - Lines 13-17 |
| Handle $20 bill | Use $10+$5 or $5+$5+$5 - Lines 19-26 |

## Final Java Code & Learning Pattern (Full Content)

```java
class Solution {
    public boolean lemonadeChange(int[] bills) {
        int fiveCount = 0;
        int tenCount = 0;
        
        for (int bill : bills) {
            if (bill == 5) {
                // $5 bill: no change needed
                fiveCount++;
            } else if (bill == 10) {
                // $10 bill: need $5 change
                if (fiveCount > 0) {
                    fiveCount--;
                    tenCount++;
                } else {
                    return false; // Cannot provide change
                }
            } else { // bill == 20
                // $20 bill: need $15 change
                // Greedy: prefer $10 + $5 over $5 + $5 + $5
                if (tenCount > 0 && fiveCount > 0) {
                    tenCount--;
                    fiveCount--;
                } else if (fiveCount >= 3) {
                    fiveCount -= 3;
                } else {
                    return false; // Cannot provide change
                }
            }
        }
        
        return true;
    }
}
```

## Complexity Analysis

**Time Complexity:** $O(n)$ where $n$ is the number of bills. We process each bill once.

**Space Complexity:** $O(1)$. We only use a constant amount of extra space.

## Similar Problems

- [Gas Station](https://leetcode.com/problems/gas-station/) - Similar greedy approach
- [Jump Game](https://leetcode.com/problems/jump-game/) - Greedy decision making
- [Best Time to Buy and Sell Stock](https://leetcode.com/problems/best-time-to-buy-and-sell-stock/) - Greedy optimization

