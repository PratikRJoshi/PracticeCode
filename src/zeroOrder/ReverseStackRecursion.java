package zeroOrder;

import java.util.Stack;

public class ReverseStackRecursion {
    Stack<Integer> stack = new Stack<>();

    public static void reverse(Stack<Integer> stack){
        if (stack.size() > 0){
            int top = stack.peek();
            stack.pop();
            reverse(stack);

            insertAtBottom(top, stack);
        }
    }

    private static void insertAtBottom(int top, Stack<Integer> stack) {
        if (stack.isEmpty()){
            stack.push(top);
        } else {

            int topMost = stack.pop();
            insertAtBottom(top, stack);

            stack.push(topMost);
        }
    }

    public static void main(String[] args) {
        Stack<Integer> stack = new Stack<>();
        pushItems(stack);

        System.out.println("Before reversing");
        printStackContents(stack);

        pushItems(stack);
        reverse(stack);

        System.out.println("After reversing");
        printStackContents(stack);
    }

    private static void pushItems(Stack<Integer> stack) {
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
    }

    private static void printStackContents(Stack<Integer> stack) {
        while (!stack.isEmpty()){
            System.out.println(stack.pop());
        }
    }
}
