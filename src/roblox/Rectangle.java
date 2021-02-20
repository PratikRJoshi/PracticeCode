package roblox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Rectangle {
    public static void main(String[] args) {
        int[][] boundary = new Rectangle().findBoundary(new int[][]{{1, 1, 1, 1},
                                                                    {1, 0, 0, 1},
                                                                    {1, 0, 0, 1},
                                                                    {1, 1, 1, 1}});
        System.out.println(Arrays.toString(boundary[0]) + " " + Arrays.toString(boundary[1]));

        boundary = new Rectangle().findBoundary(new int[][]{{0, 1, 1, 1},
                                                                    {1, 0, 0, 1},
                                                                    {1, 0, 0, 1},
                                                                    {1, 1, 1, 1}});
        System.out.println(Arrays.toString(boundary[0]) + " " + Arrays.toString(boundary[1]));
    }

    private int[][] findBoundary(int[][] grid) {
        List<int[]> result = new ArrayList<>();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == 0) {

                    result.add(new int[]{i, j});
                    result.add(bfs(grid, i, j));
                }
            }
        }
        return result.toArray(new int[result.size()][]);
    }

    private int[] bfs(int[][] grid, int i, int j) {
        if (grid == null || grid.length == 0) {
            return new int[]{};
        }

        Queue<int[]> q = new LinkedList<>();
        q.offer(new int[]{i, j});

        int[][] dirs = {{1, 0}, {0, 1}};
        int[] result = new int[2];
        while (!q.isEmpty()) {
            int[] cell = q.poll();
            result[0] = cell[0];
            result[1] = cell[1];
            for (int[] dir : dirs) {
                int dx = dir[0] + cell[0];
                int dy = dir[1] + cell[1];

                if (dx < grid.length && dy < grid[i].length && grid[dx][dy] == 0) {
                    grid[dx][dy] = 2;
                    q.offer(new int[]{dx, dy});
                }
            }
        }

        return result;
    }
}
