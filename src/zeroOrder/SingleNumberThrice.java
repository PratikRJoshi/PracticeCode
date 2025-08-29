package zeroOrder;

public class SingleNumberThrice {
    public static void main(String[] args) {
        int[] nums = {2, 2, 3};
        SingleNumberThrice singleNumberThrice = new SingleNumberThrice();
        int result = singleNumberThrice.singleNumber(nums);
        System.out.println(result);
    }

    private int singleNumber(int[] nums) {
        int countOnes = 0;
        int result = 0;

        if (nums.length == 1)
            return nums[0];

        for (int i = 0; i < 32; i++) {
            int digit = 0;
            for (int j : nums){
                digit = (j >> i) & 1;
                if (digit == 1)
                    countOnes++;
            }
            if (countOnes % 2 == 1){
                result |= 1 << i;
                countOnes = 0;
            }
        }

        return result;
    }
}