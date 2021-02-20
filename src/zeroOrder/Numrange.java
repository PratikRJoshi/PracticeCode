package zeroOrder;

import java.util.Arrays;
import java.util.List;

public class Numrange {
    public static int numRange(List<Integer> A, int B, int C) {
        return countSumLessThanK(A, C) - countSumLessThanK(A, B - 1);
    }

    private static int countSumLessThanK(List<Integer> A, int C) {
        if (A == null || A.size() == 0)
            return 0;

        int count = 0;
        int start = 0, end = 0, sum = 0;
        while (end < A.size()){
            sum += A.get(end);
            /*if (sum >= B && sum <= C){
                count++;
            }*/

            while (sum > C && start < A.size()){
                sum -= A.get(start);
                start++;
            }

            count += (end - start + 1);
            end++;
        }
        return count;
    }

    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(10, 5, 1, 0, 2);
        int B = 6, C = 8;
        int count = numRange(list, B, C);
        System.out.println(count);
    }
}
