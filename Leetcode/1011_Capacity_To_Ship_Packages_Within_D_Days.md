### 1011. Capacity To Ship Packages Within D Days
### Problem Link: [Capacity To Ship Packages Within D Days](https://leetcode.com/problems/capacity-to-ship-packages-within-d-days/)

### Intuition/Main Idea
This problem asks us to find the minimum capacity of a ship that can ship all packages within D days, where packages must be shipped in order. The key insight is that this is a binary search problem on the answer space.

We know that the minimum possible capacity is the weight of the heaviest package (since we can't split packages), and the maximum possible capacity is the sum of all weights (shipping everything in one day). The optimal capacity lies somewhere in between.

For any given capacity, we can determine if it's possible to ship all packages within D days by simulating the shipping process. We greedily pack as many packages as possible into each day without exceeding the capacity, and check if we can complete the shipping within D days.

Using binary search, we can efficiently find the minimum capacity that allows shipping within D days. This approach works because:
1. If we can ship with capacity X, we can also ship with any capacity > X
2. If we cannot ship with capacity X, we cannot ship with any capacity < X

This monotonic property makes binary search the perfect approach for this problem.

### Code Mapping

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Find minimum and maximum possible capacities | `int left = getMax(weights); int right = getSum(weights);` |
| Binary search on capacity | `while (left < right) { int mid = left + (right - left) / 2; ... }` |
| Check if shipping is possible with given capacity | `if (canShip(weights, days, mid)) { right = mid; } else { left = mid + 1; }` |
| Simulate shipping process | `for (int weight : weights) { if (currentLoad + weight > capacity) { days--; currentLoad = 0; } currentLoad += weight; }` |

### Final Java Code & Learning Pattern

```java
// [Pattern: Binary Search on Answer Space]
class Solution {
    public int shipWithinDays(int[] weights, int days) {
        // The minimum capacity is the maximum weight (can't split packages)
        // The maximum capacity is the sum of all weights (ship all in one day)
        int left = getMax(weights);
        int right = getSum(weights);
        
        // Binary search to find the minimum capacity
        while (left < right) {
            int mid = left + (right - left) / 2;
            
            // If we can ship with capacity mid, try a smaller capacity
            // Otherwise, we need a larger capacity
            if (canShip(weights, days, mid)) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        
        return left;
    }
    
    // Check if we can ship all packages within 'days' days with given capacity
    private boolean canShip(int[] weights, int days, int capacity) {
        int daysNeeded = 1;
        int currentLoad = 0;
        
        for (int weight : weights) {
            // If adding this package exceeds capacity, start a new day
            if (currentLoad + weight > capacity) {
                daysNeeded++;
                currentLoad = 0;
            }
            
            // Add the current package
            currentLoad += weight;
            
            // If we need more days than allowed, return false
            if (daysNeeded > days) {
                return false;
            }
        }
        
        return true;
    }
    
    // Get the maximum weight in the array
    private int getMax(int[] weights) {
        int max = Integer.MIN_VALUE;
        for (int weight : weights) {
            max = Math.max(max, weight);
        }
        return max;
    }
    
    // Get the sum of all weights
    private int getSum(int[] weights) {
        int sum = 0;
        for (int weight : weights) {
            sum += weight;
        }
        return sum;
    }
}
```

### Alternative Implementation (More Concise)

```java
// [Pattern: Binary Search with Optimized Implementation]
class Solution {
    public int shipWithinDays(int[] weights, int days) {
        // Find minimum and maximum possible capacities
        int left = 0;
        int right = 0;
        
        for (int weight : weights) {
            left = Math.max(left, weight);
            right += weight;
        }
        
        // Binary search for the minimum capacity
        while (left < right) {
            int mid = left + (right - left) / 2;
            
            // Count days needed with capacity 'mid'
            int daysNeeded = 1;
            int currentLoad = 0;
            
            for (int weight : weights) {
                if (currentLoad + weight > mid) {
                    daysNeeded++;
                    currentLoad = 0;
                }
                currentLoad += weight;
            }
            
            // Adjust search range based on days needed
            if (daysNeeded <= days) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        
        return left;
    }
}
```

### Complexity Analysis
- **Time Complexity**: $O(n \log S)$ where n is the number of packages and S is the sum of all weights. The binary search takes $O(\log S)$ iterations, and in each iteration, we scan the weights array once, which takes $O(n)$ time.
- **Space Complexity**: $O(1)$ as we only use a constant amount of extra space.

### Binary Search Problems Explanation
- **Using < vs <=**: We use `left < right` in the binary search because we're looking for the minimum capacity that works. When `left == right`, we've found our answer.
- **Setting pointers**: When we can ship with capacity `mid`, we set `right = mid` (not `mid - 1`) because `mid` might be the minimum valid capacity. When we can't ship with capacity `mid`, we set `left = mid + 1` because we know we need a larger capacity.
- **Return value**: We return `left` because when the loop terminates, `left == right`, and this value is the minimum capacity that allows shipping within D days.

### Similar Problems
1. **LeetCode 875: Koko Eating Bananas** - Find minimum eating speed to finish all bananas within H hours.
2. **LeetCode 410: Split Array Largest Sum** - Split array into m subarrays to minimize the largest sum.
3. **LeetCode 1231: Divide Chocolate** - Divide chocolate to maximize the minimum sweetness.
4. **LeetCode 1283: Find the Smallest Divisor Given a Threshold** - Find smallest divisor such that sum of division results is less than threshold.
5. **LeetCode 1482: Minimum Number of Days to Make m Bouquets** - Find minimum days to make m bouquets.
6. **LeetCode 1552: Magnetic Force Between Two Balls** - Maximize the minimum magnetic force between balls.
7. **LeetCode 1760: Minimum Limit of Balls in a Bag** - Find minimum maximum number of balls in a bag after operations.
8. **LeetCode 2064: Minimized Maximum of Products Distributed to Any Store** - Distribute products to minimize the maximum number of products in any store.
