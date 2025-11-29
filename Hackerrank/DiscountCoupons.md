# Discount Coupons

## Problem Description
A fruit-selling shop in Hackerland sells n varieties of fruit.  The ith variety sells for price[i], and it provides a nutrition value of nutrition[i].



One of the regular customers has m amount of money to purchase fruits. Additionally, the customer has k discount coupons that can be used to purchase some fruit variety at half the original price (rounded down to an integer). Note that each coupon can only be used on one variety of fruit, and each variety of fruit can only be purchased once.



Find the maximum nutrition value that the customer can obtain by purchasing fruits with the given amount of money using at most k discount coupons.



Example

The customer has m = 4 money to select from n = 3 types of fruit that cost price = [2, 4, 5], and have nutrition value nutrition = [20, 17, 15]. The customer has k = 1 discount coupons.



With 0-based indexing, in the most optimal case, the customer buys the 0th variety of fruit at the original price and the 1st variety of fruit using a discount coupon.

Total amount of money spent = 2 + (4 / 2) = 4, which is within the budget
Total nutrition value obtained = 20 + 17 = 37, which is the maximum possible.
Return the maximum nutrition value, 37.



Function Description

Complete the function getMaximumNutrition in the editor below.



getMaximumNutrition has the following parameter:

    int m: the available money

    int price[n]: the prices of the fruits

    int nutrition[n]: the nutrition value of the fruits

    int k: the number of discount coupons available



Returns

    int: the maximum nutrition value possible



Constraints

1 ≤ m ≤ 103
1 ≤ n ≤ 500
1 ≤ price[i], nutrition[i] ≤ 103
0 ≤ k ≤ 10
Input Format For Custom Testing
The first line contains an integer, m.

The next line contains an integer n, the number of elements in price.

Each line i of the n subsequent lines (where 0 ≤ i < n) contains an integer, price[i].

The next line contains an integer n, the number of elements in nutrition.

Each line i of the n subsequent lines (where 0 ≤ i < n) contains an integer, nutrition[i].

The next line contains an integer, k.

Sample Case 0
Sample Input For Custom Testing

STDIN          FUNCTION
-----          --------
10        →    m = 10
2         →    price[] size n = 2
10        →    price = [10, 20]
20
2         →    nutrition[] size n = 2
9         →    nutrition = [9, 10]
10
1         →    k = 1
Sample Output

10
Explanation



Buy the 0th fruit variety using a discount coupon.
The total amount of money spent = 20 / 2 = 10, which is within the allowed budget.
Total nutrition value obtained = 10, which is the maximum possible.


Sample Case 1
Sample Input For Custom Testing

STDIN          FUNCTION
-----          --------
20        →    m = 20
4         →    price[] size n = 4
4         →    price = [4, 4, 9, 3]
4
9
3
4         →    nutrition[] size n = 4
10        →    nutrition = [10, 7, 13, 3]
7
13
3
4         →    k = 4
Sample Output

33
Explanation





Since the total price of all the fruit varieties is equal to the amount of money the customer can spend, all fruits can be purchased without the need to use any of the discount coupons.
Total amount of money spend = 4 + 4 + 9 + 3 = 20, which is within the allowed budget.
Total nutrition value obtained = 10 + 7 + 13 + 3 = 33, which is maximum possible.

## Intuition/Main Idea:
This is a classic knapsack problem with an additional dimension for the discount coupons. We need to decide which fruits to buy and which ones to apply discounts to, in order to maximize the total nutrition value while staying within the budget.

The key insight is to use dynamic programming with three dimensions:
1. The current fruit index we're considering
2. The remaining money
3. The remaining discount coupons

For each fruit, we have three choices:
1. Skip it
2. Buy it at full price
3. Buy it at half price using a discount coupon

## Code Mapping:

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Dynamic programming approach | 3D array: `dp[i][j][l]` for fruit index, money, and coupons |
| Handling discount coupons | Logic for applying discount: `if (l > 0 && j >= discountedPrice)` |
| Maximizing nutrition value | Taking maximum of three options: skip, buy at full price, buy with discount |

## Final Java Code & Learning Pattern:

```java
import java.util.*;

class Solution {
    public static int getMaximumNutrition(int m, int[] price, int[] nutrition, int k) {
        int n = price.length;
        
        // Create a 3D DP array: [fruit index][money][coupons]
        int[][][] dp = new int[n + 1][m + 1][k + 1];
        
        // Initialize with -1 to indicate unprocessed states
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= m; j++) {
                for (int l = 0; l <= k; l++) {
                    dp[i][j][l] = -1;
                }
            }
        }
        
        // Base case: no fruits, no nutrition
        for (int j = 0; j <= m; j++) {
            for (int l = 0; l <= k; l++) {
                dp[0][j][l] = 0;
            }
        }
        
        // Fill the DP table
        for (int i = 1; i <= n; i++) {
            int currentPrice = price[i - 1];
            int currentNutrition = nutrition[i - 1];
            
            for (int j = 0; j <= m; j++) {
                for (int l = 0; l <= k; l++) {
                    // Option 1: Skip this fruit
                    dp[i][j][l] = dp[i - 1][j][l];
                    
                    // Option 2: Buy at full price if we have enough money
                    if (j >= currentPrice) {
                        dp[i][j][l] = Math.max(dp[i][j][l], dp[i - 1][j - currentPrice][l] + currentNutrition);
                    }
                    
                    // Option 3: Buy with discount if we have a coupon and enough money
                    int discountedPrice = currentPrice / 2;
                    if (l > 0 && j >= discountedPrice) {
                        dp[i][j][l] = Math.max(dp[i][j][l], dp[i - 1][j - discountedPrice][l - 1] + currentNutrition);
                    }
                }
            }
        }
        
        return dp[n][m][k];
    }
    
    // Main method for testing
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int m = scanner.nextInt();
        
        int n = scanner.nextInt();
        int[] price = new int[n];
        for (int i = 0; i < n; i++) {
            price[i] = scanner.nextInt();
        }
        
        // Read nutrition values
        int[] nutrition = new int[n];
        for (int i = 0; i < n; i++) {
            nutrition[i] = scanner.nextInt();
        }
        
        int k = scanner.nextInt();
        
        System.out.println(getMaximumNutrition(m, price, nutrition, k));
        scanner.close();
    }
}
```

## Complexity Analysis:

**Time Complexity**: $O(n \times m \times k)$, where:
- $n$ is the number of fruit varieties
- $m$ is the amount of money
- $k$ is the number of discount coupons

We need to fill a 3D DP table of size (n+1) × (m+1) × (k+1).

**Space Complexity**: $O(n \times m \times k)$ for the 3D DP array.

## Similar Problems:
- [LeetCode 416: Partition Equal Subset Sum](https://leetcode.com/problems/partition-equal-subset-sum/) - Similar DP approach
- [LeetCode 494: Target Sum](https://leetcode.com/problems/target-sum/) - Another DP problem with choices
- [LeetCode 322: Coin Change](https://leetcode.com/problems/coin-change/) - Classic DP problem with money constraints
