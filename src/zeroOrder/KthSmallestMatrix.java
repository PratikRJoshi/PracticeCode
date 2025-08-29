package zeroOrder;

public class KthSmallestMatrix {
    private int kthSmallest(int[][] matrix, int k) {
        int lo = matrix[0][0], hi = matrix[matrix.length - 1][matrix[0].length - 1] + 1;//[lo, hi)
        while(lo < hi) {
            int mid = lo + (hi - lo) / 2;
            int count = 0,  j = matrix[0].length - 1;
            for(int i = 0; i < matrix.length; i++) {
                while(j >= 0 && matrix[i][j] > mid)
                    j--;
                count += (j + 1);
            }
            if(count < k)
                lo = mid + 1;
            else
                hi = mid;
        }
        return lo;
    }

    public static void main(String[] args) {
//        int[][] matrix = {{-5}};
        int[][] matrix = {{ 1,  5,  9}, {10, 11, 13}, {12, 13, 15}};
        int k = 1;
        KthSmallestMatrix kthSmallestMatrix = new KthSmallestMatrix();
        int kthSmallest = kthSmallestMatrix.kthSmallest(matrix, k);
        System.out.println(kthSmallest);
    }
}
