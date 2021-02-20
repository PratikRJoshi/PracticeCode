package zeroOrder;

public class Base7 {

    public static String convertToBase7(int num) {

        if (num == 0)
            return "0";

        int remainder = 0;
        boolean isNegative = false;

        StringBuilder sb = new StringBuilder();
        if (num < 0) {
            isNegative = true;
            num = -num;
        }
        while(num != 0){
            remainder = num % 7;
            sb.insert(0,remainder);
            num = num / 7;
        }

        return isNegative ? sb.insert(0, "-").toString() : sb.toString();
    }

    public static void main(String[] args) {
        int num = 0;
        String toBase7 = convertToBase7(num);
        System.out.println(toBase7);
    }
}
