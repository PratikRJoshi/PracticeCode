package zeroOrder;

import java.util.Arrays;

public class NextPermutation {
    public static void nextPermutation(int[] nums) {
        if (nums == null || nums.length == 0)
            return;

        // find rightmost element for which nums[k] < nums[k + 1]
        int k = -1;
        for (int i = 0; i < nums.length - 1; i++){
            if (nums[i] < nums[i + 1]){
                k = i;
            }
        }
        if (k == -1){
            Arrays.sort(nums);
            return;
        }

        // find the leftmost element to the right of k for which nums[k] < nums[l]
        int l = k + 1;
        for (int i = k + 2; i < nums.length; i++){
            if (nums[k] < nums[i]){
                l = i;
            }
        }

        // swap nums[k] and nums[l]
        int temp = nums[k];
        nums[k] = nums[l];
        nums[l] = temp;

        // reverse the elements from k + 1 to end
        for (int i = 1; k + i < nums.length - i; i++){
            temp = nums[k + i];
            nums[k + i] = nums[nums.length - i];
            nums[nums.length - i] = temp;
        }
    }

    public static void main(String[] args) {
        int[] nums = new int[]{1, 2, 4, 3};
        nextPermutation(nums);
        for (int i : nums){
            System.out.print(i + " ");
        }
    }
}
