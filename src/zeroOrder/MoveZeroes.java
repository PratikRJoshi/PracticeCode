package zeroOrder;

public class MoveZeroes {

    public static void main(String[] args) {
        int[] nums = {5, 0, 1, 0, 3, 12};
        int[] result = moveZeroes1(nums);
        printElements(result);
    }

    private static void printElements(int[] o) {
        for (int i : o) {
            System.out.print(i + " ");
        }
    }

    private static int[] moveZeroes1(int[] nums) {
        if (nums == null || nums.length == 0)
            return nums;

        int zeroPtr = 0, j = 0;
        while (j < nums.length && nums[j] != 0) {
            j++;
        }

        while (j < nums.length) {
            // swap
            nums[zeroPtr] = nums[j];
            nums[j] = 0;
            zeroPtr++;

            while (j < nums.length && nums[j] == 0) {
                j++;
            }
        }

        return nums;
    }

    private static int[] moveZeroes(int[] nums) {
        if (nums == null || nums.length == 0)
            return nums;

        int z_ptr = 0, i = 0;

        while (nums[i] == 0) {
            ++i;
        }

        while (i < nums.length) {
            nums[z_ptr] = nums[i];
            nums[i] = 0;
            z_ptr++;

            while (i < nums.length & nums[i] == 0)
                ++i;
        }

        return nums;
    }
}
