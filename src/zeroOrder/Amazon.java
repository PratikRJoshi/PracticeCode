package zeroOrder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Amazon {
    // DFS with memoization
    public static int minDifficulty(int[] jobDifficulty, int D) {
        final int N = jobDifficulty.length;
        if(N < D) return -1;

        int[][] memo = new int[N][D + 1];
        for(int[] row : memo) Arrays.fill(row, -1);

        return dfs(D, 0, jobDifficulty, memo);
    }

    private static int dfs(int d, int len, int[] jobDifficulty, int[][] memo) {
        if (d == 0 && len == jobDifficulty.length)
            return 0;
        if (d == 0 || len == jobDifficulty.length)
            return Integer.MAX_VALUE;
        if (memo[len][d] != -1)
            return memo[len][d];

        int curMax = jobDifficulty[len];
        int min = Integer.MAX_VALUE;
        for (int schedule = len; schedule < jobDifficulty.length; ++schedule) {
            curMax = Math.max(curMax, jobDifficulty[schedule]);
            int temp = dfs(d - 1, schedule + 1, jobDifficulty, memo);
            if (temp != Integer.MAX_VALUE)
                min = Math.min(min, temp + curMax);
        }

        return memo[len][d] = min;
    }


    public static int findMinComplexity(List<Integer> complexity, int days) {
        int n = complexity.size();
        if(n < days){
            return -1;
        }

        int[][] memo = new int[n][days + 1];
        for(int[] row : memo){
            Arrays.fill(row, -1);
        }

        return dfs(days, 0, complexity, memo);
    }

    private static int dfs(int days, int length, List<Integer> complexity, int[][] memo){
        if(days == 0 && length == complexity.size()){
            return 0;
        }
        if(days == 0 || length == complexity.size()){
            return Integer.MAX_VALUE;
        }
        if(memo[length][days] != -1){
            return memo[length][days];
        }

        int currentMax = complexity.get(length);
        int min = Integer.MAX_VALUE;
        for(int i = length; i < complexity.size(); i++){
            currentMax = Math.max(currentMax, complexity.get(i));
            int temp = dfs(days - 1, i + 1, complexity, memo);
            if(temp != Integer.MAX_VALUE){
                min = Math.min(min, temp + currentMax);
            }
        }

        memo[length][days] = min;
        return memo[length][days];
    }

    public static void main(String[] args) {
        int[] complexity = {30, 10, 40, 20, 50};
        List<Integer> complex = new ArrayList<>();
        complex.add(30);
        complex.add(10);
        complex.add(40);
        complex.add(20);
        complex.add(50);
        int days = 2;

        System.out.println(minDifficulty(complexity, days));
        System.out.println(findMinComplexity(complex, days));

    }
}
