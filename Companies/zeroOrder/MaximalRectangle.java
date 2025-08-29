package zeroOrder;

import java.util.Stack;

public class MaximalRectangle {
    public int maximalRectangle(char[][] matrix) {
        if (matrix == null || matrix.length == 0)
            return 0;
        int[] height = new int[matrix[0].length];

        for (int i = 0; i < matrix[0].length; i++) {
            if (matrix[0][i] == '1'){
                height[i] = 1;
            }
        }

        int result = largestRectangleInHistogram(height);
        for (int i = 1; i < matrix.length; i++){
            resetHeight(height, matrix, i);
            result = Math.max(result,  largestRectangleInHistogram(height));
        }

        return result;
    }

    private int largestRectangleInHistogram(int[] height) {
        if (height == null  || height.length == 0)
            return 0;

        Stack<Integer> stack = new Stack<>();
        int max = 0, index = 0;

        while (index < height.length){
            if (stack.isEmpty() || height[index] >= height[stack.peek()]){
                stack.push(index++);
            } else {
                int top = stack.pop();
                max = Math.max(max, height[top] * (stack.isEmpty() ? index : index - stack.peek() - 1));
            }
        }

        while (!stack.isEmpty()){
            int top = stack.pop();
            max = Math.max(max, height[top] * (stack.isEmpty() ? index : index - stack.peek() - 1));
        }
        return max;
    }

    private void resetHeight(int[] height, char[][] matrix, int index) {
        for (int i  = 0; i < matrix[0].length; i++){
            if (matrix[index][i] == '0'){
                height[i] = 0;
            } else {
                height[i] += 1;
            }
        }
    }
}
