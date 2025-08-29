package zeroOrder;

import java.util.Stack;

public class SortStackRecursion {
    private static void sortStackRecursion(Stack<Integer> stack) {
        if (stack.size() == 1) {
            return;
        }

        int top = stack.pop();
        sortStackRecursion(stack);
        insertIntoStackRecursion(stack, top);
    }

    private static void insertIntoStackRecursion(Stack<Integer> stack, int top) {
        if (stack.isEmpty() || top > stack.peek()){
            stack.push(top);
            return;
        }

        int t = stack.pop();
        insertIntoStackRecursion(stack, top);
        stack.push(t);
    }

    public static void main(String[] args) {
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        stack.push(0);
        stack.push(2);
        stack.push(6);
        stack.push(3);
        sortStackRecursion(stack);

        while (!stack.isEmpty()){
            System.out.println(stack.pop());
        }
    }
}
