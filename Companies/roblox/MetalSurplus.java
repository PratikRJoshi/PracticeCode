package roblox;

public class MetalSurplus {
    public static void main(String[] args) {
        MetalSurplus m = new MetalSurplus();
        System.out.println(m.cut(new int[]{30, 59, 110}, 10, 1));
    }

    public int cut(int[] nums, int price, int cutcost) {
        int n = nums.length;
        if (n == 0) {
            return 0;
        }
        int left = 1;
        int right = 0;
        for (int i : nums) {
            right = Math.max(i, right);
        }

        return binarySearch(nums, price, cutcost, left, right);
    }

    int binarySearch(int[] nums, int price, int cutcost, int l, int r) {
        if (l > r) {
            return 0;
        }
        int mid = l + (r - l) / 2;
        int cut_atleft = helper(nums, price, cutcost, l);
        int cut_atright = helper(nums, price, cutcost, r);
        int cut_atmid = helper(nums, price, cutcost, mid);
        int left = binarySearch(nums, price, cutcost, l, mid - 1);
        int right = binarySearch(nums, price, cutcost, mid + 1, r);
        int max = Math.max(left, right);
        max = Math.max(max, Math.max(cut_atleft, Math.max(cut_atright, cut_atmid)));
        return max;
    }

    int helper(int[] nums, int price, int cutcost, int length) {
        int len = 0;
        int cuts = 0;
        for (int i = 0; i < nums.length; i++) {
            len += nums[i] / length;
            if (nums[i] % length == 0) {
                cuts += (nums[i] / length) - 1;
            } else {
                cuts += (nums[i] / length);
            }
        }
        return (length * len * price) - (cuts * cutcost);
    }
}
