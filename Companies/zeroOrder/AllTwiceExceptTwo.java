package zeroOrder;

import java.util.Arrays;

public class AllTwiceExceptTwo {
    public static void main(String[] args) {
        int[] nums = {2, 3, 2, 4, 5, 7, 7, 5};
        AllTwiceExceptTwo allTwiceExceptTwo = new AllTwiceExceptTwo();
        int[] ints = allTwiceExceptTwo.twoNumbers(nums);
        Arrays.stream(ints).forEach(System.out::println);
    }

    private int[] twoNumbers(int[] nums){
        if(nums == null|| nums.length == 0)
            return null;

        int xor = 0;
        for (int j: nums)
            xor ^= j;

        int mask = 1;
        while ((mask & xor) == 0)
            mask <<= 1;

        int a = 0, b = 0;
        for (int j : nums){
            if ((j & mask) == 0)
                a ^= j;
            else
                b ^= j;
        }

        return new int[]{a, b};
    }
}
