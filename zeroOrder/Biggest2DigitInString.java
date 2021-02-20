package zeroOrder;

public class Biggest2DigitInString {
    private static int biggestDigits(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }

        int max = Integer.MIN_VALUE;
        for (int i = 0; i < s.length() - 1; i++) {
            int val = Integer.parseInt("" + s.charAt(i) + s.charAt(i + 1));
            max = Math.max(max, val);
        }
        return max;
    }

    public static void main(String[] args) {
        System.out.println(biggestDigits("50552")); // 55
        System.out.println(biggestDigits("50652")); // 65
        System.out.println(biggestDigits("88")); // 88
        System.out.println(biggestDigits("10101")); // 10
    }
}
