### HackerRank: Utopian Tree
### Problem Link: [Utopian Tree](https://www.hackerrank.com/challenges/utopian-tree/problem)
### Intuition
This problem describes a tree with a specific growth pattern: it doubles in height during spring cycles and increases by 1 meter during summer cycles. Starting with a height of 1 meter, we need to calculate the height after N growth cycles.

The key insight is to recognize the pattern:
- Cycle 0: Height = 1 (initial)
- Cycle 1 (Spring): Height doubles = 2
- Cycle 2 (Summer): Height increases by 1 = 3
- Cycle 3 (Spring): Height doubles = 6
- Cycle 4 (Summer): Height increases by 1 = 7
And so on...

We can solve this by simulating the growth cycle by cycle, or by using a mathematical formula based on the pattern.

### Java Reference Implementation (Iterative)
```java
class Solution {
    public int utopianTree(int n) {
        int height = 1; // Initial height
        
        for (int i = 1; i <= n; i++) {
            if (i % 2 == 1) {
                // Spring cycle: height doubles
                height *= 2;
            } else {
                // Summer cycle: height increases by 1
                height += 1;
            }
        }
        
        return height;
    }
}
```

### Alternative Implementation (Mathematical)
```java
class Solution {
    public int utopianTree(int n) {
        // Calculate the number of complete 2-cycle periods
        int periods = n / 2;
        
        // Calculate the height after complete periods
        // Each period multiplies the height by 2 and adds 1
        // So after k periods, height = (2^k)(initial_height) + (2^k - 1)
        int height = (1 << periods) + ((1 << periods) - 1);
        
        // If there's an extra spring cycle (odd n)
        if (n % 2 == 1) {
            height *= 2;
        }
        
        return height;
    }
}
```

### Alternative Implementation (Recursive)
```java
class Solution {
    public int utopianTree(int n) {
        if (n == 0) {
            return 1;
        } else if (n % 2 == 1) {
            // Spring cycle: height doubles
            return 2 * utopianTree(n - 1);
        } else {
            // Summer cycle: height increases by 1
            return utopianTree(n - 1) + 1;
        }
    }
}
```

### Requirement → Code Mapping
- **R0 (Initial height)**: `int height = 1;` - The tree starts with a height of 1 meter
- **R1 (Spring cycle)**: `if (i % 2 == 1) { height *= 2; }` - During spring (odd cycles), the height doubles
- **R2 (Summer cycle)**: `else { height += 1; }` - During summer (even cycles), the height increases by 1
- **R3 (Return final height)**: `return height;` - Return the height after N cycles

### Example Walkthrough
For N = 4:
- Initial height = 1
- Cycle 1 (Spring): height = 1 * 2 = 2
- Cycle 2 (Summer): height = 2 + 1 = 3
- Cycle 3 (Spring): height = 3 * 2 = 6
- Cycle 4 (Summer): height = 6 + 1 = 7
- Final height = 7

### Complexity Analysis
- **Time Complexity**: O(n) - We simulate each growth cycle
- **Space Complexity**: O(1) - We use a constant amount of extra space
