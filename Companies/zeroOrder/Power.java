package zeroOrder;

public class Power {
    public static double myPow(double x, int n) {
        if (n == 0)
            return 1;
        if (n < 0){
            n = -n;
            x = 1/x;
        }
        if (n == Integer.MAX_VALUE){
            x = x * x;
            n = n / 2;
        }

        if (n % 2 == 0){
            return myPow(x, n / 2) * myPow(x, n / 2);
        } else {
            return x * myPow(x, n / 2) * myPow(x, n / 2);
        }
    }

    public static void main(String[] args) {
        double base = 0.00001;
        int power = 2147483647;

        System.out.println(myPow(base, power));
    }
}
