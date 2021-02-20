package zeroOrder;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class ExclusiveTimes {
    public static int[] exclusiveTime(int n, List<String> logs) {
        int[] result = new int[n];
        Stack<Integer> stack = new Stack<>();

        int prev = 0;
        for(String log : logs) {
            String[] strs = log.split(":");
            int id = Integer.parseInt(strs[0]);
            int current = Integer.parseInt(strs[2]);
            if(strs[1].equals("start")) {
                if(!stack.isEmpty()) {
                    result[stack.peek()] += current - prev;
                }
                stack.push(id);
                prev = current;
            } else {
                result[stack.pop()] += current - prev + 1;
                prev = current + 1;
            }
        }

        return result;
    }

    public static void main(String[] args) {
        int n = 2;
        String[] array = {"0:start:0","1:start:2","0:end:5","1:end:6"};
        List<String> logs = Arrays.asList(array);
        int[] exclusiveTime = exclusiveTime(n, logs);
        for (int i : exclusiveTime) {
            System.out.print(i + " ");
        }
    }
}
