package zeroOrder;

import java.util.Stack;

public class WordValidAfterSubstitution {
    public boolean isValid(String S) {
        if(S == null || S.length() == 0)
            return false;

        Stack<Character> stack = new Stack<>();
        for(char c : S.toCharArray()) {
            if(c == 'a'){
                stack.push('c');
                stack.push('b');
            } else {
                if(!stack.isEmpty() && stack.pop() != c)
                    return false;
            }
        }
        return stack.isEmpty();
    }

    public static void main(String[] args) {
        String input = "aabcbc";
        WordValidAfterSubstitution wordValidAfterSubstitution = new WordValidAfterSubstitution();
        System.out.println(wordValidAfterSubstitution.isValid(input));
    }
}
