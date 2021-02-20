package zeroOrder;

public class ContiguousBinaryArray {
    public static int findMaxLength(int[] nums) {
        int max = 0;
        int countZeroes = 0, countOnes = 0;
        int start = 0, end = 0;

        while (end < nums.length){
            if (nums[end] == 0){
                countZeroes++;
            } else {
                countOnes++;
            }
            if (countOnes == countZeroes){
                max = Math.max(max, end - start + 1);
            }
            end++;
        }
        end--;
        while (start < nums.length){
            if (nums[start] == 0){
                countZeroes--;
            } else {
                countOnes--;
            }
            if (countOnes == countZeroes){
                max = Math.max(max, end - start);
            }
            start++;
        }

        return max;
    }

    public static void main(String[] args) {
        int[] nums = {0,1,0,1,1,1,0,0,1,1,0,1,1,1,1,1,1,0,1,1,0,1,1,0,0,0,1,0,1,0,0,1,0,1,1,1,1,1,1,0,0,0,0,1,0,0,0,1,1,1,0,1,0,0,1,1,1,1,1,0,0,1,1,1,1,0,0,1,0,1,1,0,0,0,0,0,0,1,0,1,0,1,1,0,0,1,1,0,1,1,1,1,0,1,1,0,0,0,1,1};
//        int[] nums = {0,0,1};
//        int[] nums = {1,1,1};
        System.out.println(findMaxLength(nums));
    }
}
