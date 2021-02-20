package zeroOrder;

public class JumpGame {
    public static boolean canJump(int[] nums) {
        if(nums == null || nums.length == 0)
            return false;

        int farthest = nums[0];
        for(int i = 0; i <= farthest; i++){
            if(farthest >= nums.length - 1)
                return true;

            int temp = i + nums[i];
            if(temp > farthest){
                farthest = temp;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        int[] input = new int[]{3,2,1,0,4, 1};
//        int[] input = new int[]{2,3,1,1,4};
        System.out.println(canJump(input));
    }
}
