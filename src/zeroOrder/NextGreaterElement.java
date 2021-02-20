package zeroOrder;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class NextGreaterElement {
    public static int[] nextGreaterElement(int[] nums1, int[] nums2) {
        int[] result = new int[nums1.length];
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums2.length; i++){
            map.put(nums2[i], i);
        }

        Arrays.fill(result, -1);

        for(int i = 0; i < nums1.length; i++){
            int j = map.get(nums1[i]);
            for (int k = j + 1; k < nums2.length; k++){
                if (nums2[k] > nums1[i]){
                    result[i] = nums2[k];
                    break;
                }
            }
        }

        return result;
    }

    public static void main(String[] args) {
        int[] nums1 = {2,4};
        int[] nums2 = {1,2,3,4};

        int[] ints = nextGreaterElement(nums1, nums2);
        for (int i : ints){
            System.out.print(i + " ");
        }
    }
}
