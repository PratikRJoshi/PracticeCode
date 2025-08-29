package zeroOrder;

import java.util.ArrayList;
import java.util.List;

public class IntervalIntersection {
    public int[][] intervalIntersection(int[][] A, int[][] B) {
        List<int[]> result = new ArrayList<>();

        int i = 0, j = 0;
        while(i < A.length && j < B.length){
            if(A[i][1] < B[j][0]){
                i++;
            } else if(B[j][1] < A[i][0]){
                j++;
            } else { // they intersect
                result.add(new int[]{Math.max(A[i][0], B[j][0]), Math.min(A[i][1], B[j][1])});
                if(A[i][1] < B[j][1]){
                    i++;
                } else {
                    j++;
                }
            }
        }

        return result.toArray(new int[result.size()][]);
    }
}
