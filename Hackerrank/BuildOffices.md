# Build Offices

## Problem Description
A company wants to develop an office park on a grid where each cell represents a potential building lot. The goal is to place office buildings so that the most distant lot is as close as possible to an office building.

Movement is restricted to horizontal and vertical directions only; diagonal movement is not allowed.

**Example:**
Consider a grid with:
- w = 4 (width)
- h = 4 (height)
- n = 3 (number of office buildings)

An optimal placement would ensure that any lot is within two units distance of an office building. In the distance grid below, offices are marked as cells with distance 0:
```
1 0 1 2
2 1 2 1
1 0 1 0
2 1 2 1
```

## Intuition/Main Idea:
This is a combinatorial optimization problem where we need to find the optimal placement of n office buildings on a w×h grid to minimize the maximum distance from any cell to the nearest office.

The key insight is to try all possible combinations of placing n offices and find the one that minimizes the maximum distance. Since the grid size is small (w×h ≤ 28), a brute force approach is feasible.

For each combination:
1. We place the offices at the selected positions
2. We use BFS to calculate the minimum distance from each cell to any office
3. We find the maximum of these minimum distances
4. We keep track of the minimum of these maximum distances across all combinations

## Code Mapping:

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Grid representation | Using a 1D array to represent the 2D grid for simplicity |
| Placing n buildings | Using combinatorial generation to try all possible placements |
| Distance calculation | BFS implementation to calculate distances from offices |
| Finding minimum maximum distance | Tracking the minimum of maximum distances across all combinations |

## Final Java Code & Learning Pattern:

```java
import java.util.*;

class Solution {
    // Direction arrays for moving in 4 directions (up, right, down, left)
    private static final int[] dx = {-1, 0, 1, 0};
    private static final int[] dy = {0, 1, 0, -1};
    
    public static int findMinDistance(int w, int h, int n) {
        int totalCells = w * h;
        int minMaxDistance = Integer.MAX_VALUE;
        
        // Generate all combinations of n offices from totalCells positions
        List<List<Integer>> combinations = generateCombinations(totalCells, n);
        
        for (List<Integer> combination : combinations) {
            // Calculate the maximum distance for this combination
            int maxDistance = calculateMaxDistance(combination, w, h);
            
            // Update the minimum of maximum distances
            minMaxDistance = Math.min(minMaxDistance, maxDistance);
        }
        
        return minMaxDistance;
    }
    
    // Generate all combinations of selecting n elements from a set of size total
    private static List<List<Integer>> generateCombinations(int total, int n) {
        List<List<Integer>> result = new ArrayList<>();
        generateCombinationsHelper(result, new ArrayList<>(), 0, total, n);
        return result;
    }
    
    private static void generateCombinationsHelper(
            List<List<Integer>> result, 
            List<Integer> current, 
            int start, 
            int total, 
            int n) {
        
        // Base case: if we've selected n elements, add this combination to the result
        if (current.size() == n) {
            result.add(new ArrayList<>(current));
            return;
        }
        
        // Try adding each remaining element
        for (int i = start; i < total; i++) {
            current.add(i);
            generateCombinationsHelper(result, current, i + 1, total, n);
            current.remove(current.size() - 1); // backtrack
        }
    }
    
    // Calculate the maximum distance for a given office placement
    private static int calculateMaxDistance(List<Integer> officePositions, int w, int h) {
        // Create a queue for BFS
        Queue<Integer> queue = new LinkedList<>();
        
        // Create a distance array initialized to -1 (unvisited)
        int[] distance = new int[w * h];
        Arrays.fill(distance, -1);
        
        // Add all office positions to the queue and mark their distances as 0
        for (int pos : officePositions) {
            queue.add(pos);
            distance[pos] = 0;
        }
        
        // Perform BFS to calculate distances
        while (!queue.isEmpty()) {
            int current = queue.poll();
            int x = current / w;
            int y = current % w;
            
            // Try all four directions
            for (int i = 0; i < 4; i++) {
                int newX = x + dx[i];
                int newY = y + dy[i];
                int newPos = newX * w + newY;
                
                // Check if the new position is valid and unvisited
                if (newX >= 0 && newX < h && newY >= 0 && newY < w && distance[newPos] == -1) {
                    distance[newPos] = distance[current] + 1;
                    queue.add(newPos);
                }
            }
        }
        
        // Find the maximum distance
        int maxDistance = 0;
        for (int dist : distance) {
            maxDistance = Math.max(maxDistance, dist);
        }
        
        return maxDistance;
    }
    
    // Main method for testing
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int w = scanner.nextInt();
        int h = scanner.nextInt();
        int n = scanner.nextInt();
        
        System.out.println(findMinDistance(w, h, n));
        scanner.close();
    }
}
```

## Complexity Analysis:

**Time Complexity**: $O(C(w \times h, n) \times w \times h)$, where $C(w \times h, n)$ is the number of ways to choose n positions from w×h cells. In the worst case, this is $C(28, 5)$ which is approximately 98,280. For each combination, we perform a BFS which takes O(w×h) time.

**Space Complexity**: $O(w \times h + C(w \times h, n))$ for storing the distance array and the list of combinations.

## Similar Problems:
- [LeetCode 296: Best Meeting Point](https://leetcode.com/problems/best-meeting-point/) - Finding a point that minimizes the total distance to all points
- [LeetCode 317: Shortest Distance from All Buildings](https://leetcode.com/problems/shortest-distance-from-all-buildings/) - Finding a point that minimizes the total distance to all buildings
- [LeetCode 1162: As Far from Land as Possible](https://leetcode.com/problems/as-far-from-land-as-possible/) - Finding the maximum distance from any cell to the nearest land
