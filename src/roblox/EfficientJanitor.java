package roblox;

import java.util.Arrays;

public class EfficientJanitor {
    private static int minGroups(double[] arr) {
        Arrays.sort(arr);
        int left = 0;
        int right = arr.length - 1;
        int count = 0;
        while (left <= right) {
            if (left == right) {
                count++;
                break;
            }
            if (arr[left] + arr[right] <= 3.0) {
                left++;
                right--;
                count++;
            } else {
                right--;
                count++;
            }
        }
        return count;
    }

    public static void main(String[] args) {
        System.out.println(minGroups(new double[]{1.01, 1.01, 3.0, 2.7, 1.99, 2.3, 1.7}));
    }
}
