package zeroOrder;

import java.util.Stack;

public class ScoreParantheses {
    public int scoreOfParentheses(String S) {
        Stack<Integer> stack = new Stack<>();
        int current = 0;

        for (int i = 0; i < S.length(); i++) {
            char c = S.charAt(i);
            if (c == '(') {
                stack.push(current);
                current = 0;
            } else if (c == ')') {
                current = stack.pop() + Math.max(1, 2 * current);
            }
        }

        return current;
    }

    public static void main(String[] args) {
        String s = "(()(()))";
        ScoreParantheses scoreParantheses = new ScoreParantheses();
        int score = scoreParantheses.scoreOfParentheses(s);
        System.out.println(score);
    }
}
