package zeroOrder;

public class MergeArrays {
    private static int[] merge(int[] nums1, int m, int[] nums2, int n) {
        int n1 = m - 1, n2 = n - 1, r = m + n - 1;

        while(n1 >= 0 && n2 >= 0) {
            if(nums1[n1] > nums2[n2]) {
                nums1[r] = nums1[n1];
                r--;
                n1--;
            } else  {
                nums1[r] = nums2[n2];
                r--;
                n2--;
            }
        }

        while(n2 >= 0){
            nums1[r--] = nums2[n2--];
        }

        return nums1;
    }

    public static void main(String[] args) {
        int[] a1 = {1,2,3,0,0,0};
        int[] b1 = {2,5,6};

        int[] merge = merge(a1, 3, b1, 3);
        for (int i : merge) {
            System.out.print(i + "\t");
        }
    }
}
