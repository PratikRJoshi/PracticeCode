package zeroOrder;

public class SortColors {

    private static int[] sort(int[] nums) {
        int zero = 0, one = 0, three = nums.length - 1;
        for (int two = 0; two <= three;) {
            if (nums[two] == 0) {
                swap(nums, two, zero);
                zero++;
                if (two < zero)
                    two++;
                if (one < zero)
                    one++;
            } else if (nums[two] == 1) {
                swap(nums, one, two);
                two++;
                one++;
            } else if (nums[two] == 2) {
                two++;
            } else if (nums[two] == 3) {
                swap(nums, two, three);
                three--;
            }
        }
        return nums;
    }

    private static void swap(int[] A, int i, int j) {
        int temp = A[i];
        A[i] = A[j];
        A[j] = temp;
    }

    public static void main(String[] args) {
//        int[] input = {1, 3, 2, 0, 0, 3, 2, 3, 0, 2, 1};
        int[] input = {1, 2, 0, 0, 2, 0, 2, 1};
        int[] res = sort(input);
        for (int i : res)
            System.out.print(i + "\t");
    }
}
