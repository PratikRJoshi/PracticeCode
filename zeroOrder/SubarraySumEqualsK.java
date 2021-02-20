package zeroOrder;

import java.util.HashMap;
import java.util.Map;

public class SubarraySumEqualsK {
    private static int subarraySum(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();

        int sum = 0, result = 0;
        for(int i : nums) {
            sum += i;
            if(sum == k)
                result++;
            if(map.containsKey(sum - k))
                result += map.get(sum - k);

            map.put(sum, map.getOrDefault(sum, 0) + 1);
        }
        return result;
    }

    public static void main(String[] args) {
        int[] input = new int[]{1,-1,1,2,3};
        int k = 5;
        System.out.println(subarraySum(input, k));

    }
}
