package zeroOrder;

import java.util.Stack;

public class ValidString {
    public static boolean checkValidString(String s) {
        if (s == null || s.length() == 0)
            return true;
        Stack<Integer> leftParenStack = new Stack<>();
        Stack<Integer> asteriskStack = new Stack<>();

        for (int i = 0; i < s.length(); i++){
            char c = s.charAt(i);
            if (c == '('){
                leftParenStack.push(i);
            } else if (c == ')'){
                if (!leftParenStack.empty()){
                    leftParenStack.pop();
                } else if (!asteriskStack.empty()){
                    asteriskStack.pop();
                } else {
                    return false;
                }
            } else if (c == '*'){
                asteriskStack.push(i);
            }
        }

        while (!leftParenStack.empty() && !asteriskStack.empty()){
            int leftParenIndex = leftParenStack.pop();
            int asteriskIndex = asteriskStack.pop();

            if (leftParenIndex > asteriskIndex)
                return false;
        }



        return leftParenStack.empty();

    }

    public static void main(String[] args) {
        String input =  "(())((())()()(*)(*()(())())())()()((()())((()))*";
        System.out.println(checkValidString(input));
    }
}
