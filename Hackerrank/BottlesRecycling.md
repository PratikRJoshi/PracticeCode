# Bottles Recycling

## Problem Description
A store offers a bottle recycling program where customers can either earn money for each recycled bottle or combine recycled bottles with cash to buy special products known as "perks items." A customer participating in this program wants to maximize the number of perks items they can purchase. Given a specific number of bottles and dollars, determine how many perks items the customer can acquire.



Example

bottles = 10

dollars = 10

recycleReward = 2, the amount earned by recycling a single bottle

perksCost = 2, the amount required for a customer to buy a single perks item combined with a recycled bottle



The best thing the customer can do is to first recycle 3 bottles, earning 6 dollars. After this, 7 bottles and 16 dollars are left. The 7 bottles  can be combined with 14 dollars to purchase 7 perks items. This is the maximum number of perks items that can be bought.



Function Description

Complete the maxPerksItems function in the editor withthe following parameter(s):

    int bottles: the number of bottles on hand

    int dollars: the number of dollars on hand

    int recycleReward: dollars earned per recycled bottle

    int perksCost: dollar cost of a perks item, in addition to recycling a single bottle



Returns

    int: the maximum number of perks items that can be bought



Constraints

1 ≤ bottles, dollars, recycleReward, perksCost ≤ 109



Input Format For Custom Testing
The first line contains an integer, bottles.

The next line contains an integer, dollars.

The next line contains an integer, recycleReward.

The next line contains an integer, perksCost.

Sample Case 0
Sample Input

STDIN     Function
-----     -----
4      →  bottles = 4
8      →  dollars = 8
3      →  recycleReward = 3
4      →  perksCost = 4
Sample Output

2
Explanation

The optimal solution is to combine 2 bottles with 8 dollars to buy 2 perks items. There is no way to get more than 2 perks items.

Sample Case 1
Sample Input

STDIN     Function
-----     -----
3      →  bottles = 3
6      →  dollars = 6
4      →  recycleReward = 4
5      →  perksCost = 5
Sample Output

2
Explanation

The optimal solution is to first recycle 1 bottle, earning 4 dollars. This leaves 2 bottles and 10 dollars. The 2 bottles can be combined with the 10 dollars to buy 2 perks items. There is no way to obtain more than 2 perks items.

## Intuition/Main Idea:
The key insight is to determine when it's beneficial to recycle bottles versus using them directly for perks items. If recycling a bottle gives more value than using it directly for a perks item, we should recycle some bottles first to maximize our dollars, and then use the remaining bottles with the accumulated dollars to buy perks items.

We need to find the optimal number of bottles to recycle that maximizes the total number of perks items we can buy.

## Code Mapping:

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Determine optimal bottles to recycle | Binary search or mathematical calculation to find optimal recycling count |
| Calculate maximum perks items | Formula: `Math.min(remainingBottles, (dollars + recycledBottles * recycleReward) / perksCost)` |
| Handle edge cases | Check if recycling is beneficial: `if (recycleReward > perksCost)` |

## Final Java Code & Learning Pattern:

```java
import java.util.*;

class Solution {
    public static long maxPerksItems(long bottles, long dollars, long recycleReward, long perksCost) {
        // If recycling reward is greater than perks cost, it's always beneficial to recycle
        if (recycleReward > perksCost) {
            // Recycle all bottles and use the money to buy perks items
            dollars += bottles * recycleReward;
            return dollars / perksCost;
        }
        
        // If recycling reward equals perks cost, it doesn't matter if we recycle or not
        if (recycleReward == perksCost) {
            // We can use all bottles for perks items
            return Math.min(bottles, (bottles * recycleReward + dollars) / perksCost);
        }
        
        // If recycling reward is less than perks cost, we need to find optimal recycling strategy
        // Use binary search to find the optimal number of bottles to recycle
        long left = 0;
        long right = bottles;
        long maxPerks = 0;
        
        while (left <= right) {
            long mid = left + (right - left) / 2;
            
            // Calculate perks items after recycling 'mid' bottles
            long remainingBottles = bottles - mid;
            long totalDollars = dollars + mid * recycleReward;
            long perksItems = Math.min(remainingBottles, totalDollars / perksCost);
            
            if (perksItems > maxPerks) {
                maxPerks = perksItems;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        
        return maxPerks;
    }
    
    // Main method for testing
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        long bottles = scanner.nextLong();
        long dollars = scanner.nextLong();
        long recycleReward = scanner.nextLong();
        long perksCost = scanner.nextLong();
        
        System.out.println(maxPerksItems(bottles, dollars, recycleReward, perksCost));
        scanner.close();
    }
}
```

## Complexity Analysis:

**Time Complexity**: $O(\log n)$ where n is the number of bottles. We use binary search to find the optimal number of bottles to recycle.

**Space Complexity**: $O(1)$ as we only use a constant amount of extra space.

## Similar Problems:
- [LeetCode 322: Coin Change](https://leetcode.com/problems/coin-change/) - Similar optimization problem with different resources
- [LeetCode 518: Coin Change 2](https://leetcode.com/problems/coin-change-2/) - Counting ways to make change with different denominations
- [LeetCode 1262: Greatest Sum Divisible by Three](https://leetcode.com/problems/greatest-sum-divisible-by-three/) - Another resource optimization problem
