package zeroOrder;

public class SearchInRotatedSortedArray {
    private static int search(int[] nums, int target) {
        if(nums == null || nums.length == 0)
            return -1;

        int start = 0, end = nums.length - 1;

        while(start <= end) {
            int mid = start + (end - start) / 2;
            if(nums[mid] == target){
                return mid;
            }

            if(nums[start] < nums[mid]){
                if(nums[start] <= target && target < nums[mid]){
                    end = mid - 1;
                } else {
                    start = mid + 1;
                }
            } else {
                if(nums[mid] < target && target <= nums[end]){
                    start = mid + 1;
                } else {
                    end = mid - 1;
                }
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] nums = {3,1};
        int target = 1;

        int searchIndex = search(nums, target);
        System.out.println(searchIndex);
    }
}
