package zeroOrder;

public class QuickSortRecurse {
    private static int[] quickSort(int[] array){
        return quickSort(array, 0, array.length - 1);
    }

    private static int[] quickSort(int[] array, int low, int high) {
        if (low < high){
            int pivot = partition(array, low, high);
            array = quickSort(array, low, pivot - 1);
            array = quickSort(array, pivot + 1, high);
        }

        return array;
    }

    private static int partition(int[] array, int low, int high) {
        int pivot = array[high];
        int pivotIndex = low;
        for (int i = low; i < array.length; i++){
            if (array[i] < pivot){
                int temp = array[pivotIndex];
                array[pivotIndex] = array[i];
                array[i] = temp;
                pivotIndex++;
            }
        }

        int temp = array[pivotIndex];
        array[pivotIndex] = array[high];
        array[high] = temp;

        return pivotIndex;
    }

    public static void main(String[] args) {
        int[] input = new int[]{1,5,3,2,8,7,6,4};

        int[] ints = quickSort(input);
        for (int i : ints){
            System.out.print(i + " ");
        }
    }
}
