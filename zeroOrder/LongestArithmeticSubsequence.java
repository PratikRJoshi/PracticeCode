package zeroOrder;

import java.util.HashMap;
import java.util.Map;

public class LongestArithmeticSubsequence {
    public static int longestArithSeqLength(int[] A) {
        if(A == null || A.length == 0)
            return 0;

        int max = 2; // default max arith seq length is 2 since we can always find 2 numbers to get a difference
        Map<Integer, Integer>[] map = new HashMap[A.length];

        for(int i = 0; i < A.length; i++){
            map[i] = new HashMap<>();
            for(int j = 0; j < i; j++){
                int difference = A[i] - A[j];
                map[i].put(difference, map[j].getOrDefault(difference, 1) + 1);
                max = Math.max(max, map[i].get(difference));
            }
        }
        return max;
    }

    public static void main(String[] args) {
//        int[] A = new int[]{9,4,7,2,10};
        int[] A = new int[]{3,6,9,12};
        System.out.println(longestArithSeqLength(A));
    }
}
