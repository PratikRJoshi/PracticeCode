package zeroOrder;

public class Mountains {
    public static int longestMountain(int[] A) {
        int result = 0;

        int i = 1;
        while(i < A.length){
            while(i < A.length && A[i] == A[i - 1])
                i++;
            int up = 0;
            while(i < A.length && A[i] > A[i - 1]){
                up++;
                i++;
            }

            int down = 0;
            while(i < A.length && A[i] < A[i - 1]){
                down++;
                i++;
            }

            if(up > 0 && down > 0)
                result = Math.max(result, up + down + 1);
        }

        return result;
    }

    public static void main(String[] args) {
        int[] A = {2,1,4,7,3,2,5};
        System.out.println(longestMountain(A));
    }
}
