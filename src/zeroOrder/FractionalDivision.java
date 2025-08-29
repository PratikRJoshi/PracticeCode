package zeroOrder;

import java.util.HashSet;
import java.util.Set;

public class FractionalDivision {
    public static String fractionToDecimal(int numerator, int denominator) {
        StringBuilder sb = new StringBuilder();

        Set<Integer> repeatedFractionals = new HashSet<>();
        int quotient = numerator / denominator;
        int remainder = numerator % denominator;
        sb.append(quotient);

        while(remainder != 0){
            if(remainder > 0 && remainder < numerator){
                remainder *= 10;
                if(sb.indexOf(".") == -1){
                    sb.append(".");
                }
            }
            numerator = remainder;
            quotient = numerator / denominator;
            remainder = numerator % denominator;

            if (sb.indexOf(".") != -1 && !repeatedFractionals.add(quotient)){
                
                break;
            }

            sb.append(quotient);
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        int numerator = 61;
        int denominator = 3;

        System.out.println(fractionToDecimal(numerator, denominator));
    }
}
