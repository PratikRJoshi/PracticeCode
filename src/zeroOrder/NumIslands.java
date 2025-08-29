package zeroOrder;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class NumIslands {
    public static int[][] numIslands(char[][] grid) {
        List<int[]> result = new ArrayList<>();

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 0) {
                    result.add(new int[]{i, j});
                    result.add(bfs(grid, i, j));
                }
            }
        }

        int[][] res = new int[result.size()][2];
        for (int i = 0; i < res.length; i++) {
            res[i] = result.get(i);
        }

        return res;
    }

    private static int[] bfs(char[][] grid, int i, int j) {
        Queue<int[]> q = new LinkedList<>();
        q.offer(new int[]{i, j});
        int[][] dirs = {{0, 1}, {1, 0}};
        int[] result = new int[2];
        while (!q.isEmpty()) {
            int[] cell = q.poll();

            for (int[] dir : dirs) {
                int x = cell[0] + dir[0];
                int y = cell[1] + dir[1];

                if (x > grid.length - 1 || y > grid[0].length - 1 || x < 0 || y < 0 || grid[x][y] == 1) {
                    continue;
                }
                grid[x][y] = 2;
                q.offer(new int[]{x, y});
                result[0] = x;
                result[1] = y;
            }
        }

        return result;
    }

    public static void main(String[] args) {
        int[][] islands = numIslands(new char[][]{{1, 1, 1, 1},
                                               {1, 0, 0, 1},
                                               {1, 0, 0, 1},
                                               {1, 1, 1, 1}}
        );
        for (int[] island : islands){
            for (int i : island){
                System.out.print(i + ",");
            }
            System.out.println();
        }
    }
}
