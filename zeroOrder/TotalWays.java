package zeroOrder;/* package whatever; // don't place package name! */

/* Name of the class has to be "Main" only if the class is public. */
class TotalWays
{
    private static int totalWays(int n, int m) {
        int[] lookup = new int[n + 1];

        lookup[0] = 1;
        lookup[1] = 1;
        lookup[2] = 2;

        for(int i = 3; i <= n; i++) {
            for(int j = 1; j <= m && (i - j) >= 0; j++){
                lookup[i] += lookup[i - j];
            }
        }

        return lookup[n];
    }
    public static void main (String[] args) {
        int n = 4, m = 3;

        System.out.printf("Total ways to reach the %d'th stair with at-most " +
                "%d steps are %d", n, m, totalWays(n, m));
    }
}