package zeroOrder;

import java.util.Stack;

public class NextGreaterElements {
    public static int[] nextGreaterElements(int[] nums) {
        int[] result = new int[nums.length];

        Stack<Integer> stack = new Stack<>();
        for (int i = nums.length - 1; i >= 0; i--){
            stack.push(i);
        }

        for (int i = nums.length - 1; i >= 0; i--){
            result[i] = -1;
            while (!stack.isEmpty() && nums[stack.peek()] <= nums[i]){
                stack.pop();
            }
            if (!stack.isEmpty()){
                result[i] = nums[stack.peek()];
            }

            stack.push(i);
        }

        return result;
    }

    public static void main(String[] args) {
        int[] input = {1,2,1, 3};
        int[] ints = nextGreaterElements(input);
        for (int i : ints){
            System.out.print(i + " ");
        }
    }
}
