package zeroOrder;

public class TowerOfHanoi {

    static int count = 0;

    private static void solve(int n, int s, int d, int h){
        count++;
        if (n == 1){
            System.out.println("Moving plate " + n + " from " + s + " to " + d);
            return;
        }

        solve(n - 1, s, h, d);
        System.out.println("Moving plate " + n + " from " + s + " to " + d);
        solve(n - 1, h, d , s);
    }

    public static void main(String[] args) {
        int n = 3;
        int s = 1, d = 2, h = 3;

        solve(n, s, d, h);
        System.out.println("Number of steps = " + count);
    }
}
