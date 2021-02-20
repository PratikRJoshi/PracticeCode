package zeroOrder;

public class MinInsertionsToMakePalindrome {
    public static int minInsertions(String s) {
        return s.length() - lcs(s, new StringBuilder(s).reverse().toString());
    }

    private static int lcs(String s1, String s2) {
        int m = s1.length(), n = s2.length();

        int[][] dp = new int[m + 1][n + 1];

        for (int i = 1; i < m + 1; i++) {
            for (int j = 1; j < n + 1; j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)){
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Math.max(dp[i][j - 1], dp[i - 1][j]);
                }
            }
        }

        return dp[m][n];
    }

    public static void main(String[] args) {
//        String s = "mbadm";
//        String s = "leetcode";
//        String s = "zzazz";
//        String s = "g";
        String s = "no";
        System.out.println(minInsertions(s));
    }
}
