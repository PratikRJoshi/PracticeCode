package zeroOrder;

public class ContainerWithMostWater {
    private int maxArea(int[] height) {
        int left = 0, right = height.length - 1;
        int tempArea = 0, maxArea = 0;

        while (left < right) {
            tempArea = Math.abs(left - right) * (height[left] < height[right] ? height[left] : height[right]);
            if (tempArea > maxArea)
                maxArea = tempArea;
            if (height[left] < height[right]) {
                left++;
            } else {
                right--;
            }
        }

        return maxArea;
    }

    public static void main(String[] args) {
        int[] heights = {1,8,6,2,5,4,8,3,7};
        ContainerWithMostWater containerWithMostWater = new ContainerWithMostWater();
        int maxArea = containerWithMostWater.maxArea(heights);
        System.out.println(maxArea);
    }
}
