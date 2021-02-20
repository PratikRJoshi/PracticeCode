package zeroOrder;

import java.util.Stack;

public class RemoveKDigits {
    public static String removeKDigits(String num, int k) {
        Stack<Character> stack = new Stack<>();

        int index = 0;
        while (index < num.length()) {
            char current = num.charAt(index);

            while (k > 0 && !stack.isEmpty() && current < stack.peek()) {
                stack.pop();
                k--;
            }
            stack.push(current);
            index++;
        }

        while (k > 0) {
            stack.pop();
            k--;
        }

        StringBuilder sb = new StringBuilder();
        while (!stack.isEmpty()) {
            sb.insert(0, stack.pop());
        }

        while (sb.length() > 0 && sb.charAt(0) == '0'){
            sb.deleteCharAt(0);
        }

        return sb.length() == 0 ? "0" : sb.toString();
    }

    public static void main(String[] args) {
        String number = "10200";
        int k = 2;

        System.out.println(removeKDigits(number, k));
    }
}
