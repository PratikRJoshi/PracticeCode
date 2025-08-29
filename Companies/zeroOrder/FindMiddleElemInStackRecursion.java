package zeroOrder;

import java.util.Stack;

public class FindMiddleElemInStackRecursion {
    private static Stack<Integer> deleteMiddleElement(Stack<Integer> stack){
        if (stack.size() == 0)
            return stack;

        int k = stack.size() / 2 + 1;
        solve(stack, k);

        return stack;
    }

    private static void solve(Stack<Integer> stack, int k) {
        if (k == 1) {
            stack.pop();
            return;
        }
        int top = stack.pop();
        solve(stack, k -1);
        stack.push(top);
    }

    public static void main(String[] args) {
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        stack.push(5);

        Stack<Integer> deleteMiddleElementStack = deleteMiddleElement(stack);
        while (!deleteMiddleElementStack.isEmpty()){
            System.out.println(deleteMiddleElementStack.pop());
        }
    }
}
