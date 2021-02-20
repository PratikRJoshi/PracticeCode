package zeroOrder;

import java.util.Stack;

public class BasicCalculator2 {
    public int calculate(String s) {
        if(s == null || s.length() == 0)
            return 0;
        Stack<Integer> stack = new Stack<>();
        int digits = 0;
        char sign = '+';

        for(int i = 0; i < s.length(); i++){
            char c = s.charAt(i);
            if(Character.isDigit(c)) {
                digits = digits * 10 + c - '0';
            }
            if(c != ' ' || i == s.length() - 1) {
                if(sign == '+') {
                    stack.push(digits);
                } else if(sign == '-') {
                    stack.push(-digits);
                } else if(sign == '*') {
                    stack.push(stack.pop() * digits);
                } else if(sign == '/') {
                    stack.push(stack.pop() / digits);
                }
                sign = c;
                digits = 0;
            }
        }

        int result = 0;
        for(int i : stack) {
            result += i;
        }

        return result;
    }

    public static void main(String[] args) {
        String s = "3+2*2";
        BasicCalculator2 basicCalculator2 = new BasicCalculator2();
        int value = basicCalculator2.calculate(s);
        System.out.println(value);
    }
}
