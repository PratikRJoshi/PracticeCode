package zeroOrder;

import java.util.Stack;

public class DailyTemperatures {
    public int[] dailyTemperatures(int[] T) {
        int[] result = new int[T.length];

        Stack<Integer> stack = new Stack<>();
        result[T.length - 1] = 0;
        stack.push(T.length - 1);

        for(int i = result.length - 2; i >= 0; i--) {
            if(T[i] < T[stack.peek()]) {
                result[i] = stack.peek() - i;
            } else {
                while(!stack.isEmpty() && T[i] >= T[stack.peek()])
                    stack.pop();
                if (stack.isEmpty())
                    result[i] = 0;
                else
                    result[i] = stack.peek() - i;
            }
            stack.push(i);
        }

        return result;
    }

    public static void main(String[] args) {
        int[] temperatures = new int[]{89,62,70,58,47,47,46,76,100,70};
        DailyTemperatures dailyTemperatures = new DailyTemperatures();
        int[] result = dailyTemperatures.dailyTemperatures(temperatures);
        for (int i : result)
            System.out.print(i + " ");
    }
}
