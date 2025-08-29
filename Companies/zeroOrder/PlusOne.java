package zeroOrder;

public class PlusOne {
    private static int[] plusOne(int[] digits) {
        int carry = 0;
        digits[digits.length - 1] += 1;

        for (int i = digits.length - 1; i >= 0; i--) {
            if (digits[i] + carry >= 10) {
                digits[i] = digits[i] + carry - 10;
                carry = 1;
            } else {
                digits[i] += carry;
                carry = 0;
            }
        }

        if (carry == 1) {
            int[] resultArray = new int[digits.length + 1];
            System.arraycopy(digits, 0, resultArray, 1, digits.length);
            resultArray[0] = carry;
            return resultArray;
        }

        return digits;
    }

    public static void main(String[] args) {
        int[] input = {9, 9, 9};
        int[] result = plusOne(input);
        for (int i : result) {
            System.out.print(i + "\t");
        }
    }
}
