package zeroOrder;

public class RemoveDupElSortedArray {
    private static int[] input = {2, 3, 3, 5, 7, 7, 11, 11, 13};

   private static int removeDuplicates(int A[], int n, int k) {

        if (n <= k) return n;

        int i = 1, j = 1;
        int cnt = 1;
        while (j < n) {
            if (A[j] != A[j-1]) {
                cnt = 1;
                A[i++] = A[j];
            }
            else {
                if (cnt < k) {
                    A[i++] = A[j];
                    cnt++;
                }
            }
            ++j;
        }
        return i;
    }


    private static int removeDups(int[] array) {
        int count = 0;

        for (int i = 1; i < array.length; i++) {
            if (array[i] == array[i - 1])
                count++;
            else
                array[i - count - 1] = array[i];
        }
        return array.length - count;
    }

    public static void main(String[] args) {
//        int resultLength = removeDups(input);
        int resultLength = removeDuplicates(input, input.length, 1);

        for (int i = 0; i < resultLength; i++)
            System.out.print(input[i] + "\t");
    }
}
