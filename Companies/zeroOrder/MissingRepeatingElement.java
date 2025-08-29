package zeroOrder;

public class MissingRepeatingElement {
    private static int[] findMissingRepeatedElements(int[] array) {
        int[] result = new int[2];
        for(int i = 0; i < array.length; i++) {
            int val = Math.abs(array[i]);
            if(array[val - 1] > 0) {
                array[val - 1] = -array[val - 1];
            } else {
                result[0] = val;
            }
        }

        for(int i = 0; i < array.length; i++) {
            if(array[i] > 0) {
                result[1] = i + 1;
                break;
            }
        }

        return result;
    }

    public static void main(String[] args) {
        int[] input = { 7, 3, 4, 5, 5, 6, 2 };
        int[] missingRepeatedElements = findMissingRepeatedElements(input);
        System.out.println("Repeated element: " + missingRepeatedElements[0]);
        System.out.println("Missing element: " + missingRepeatedElements[1]);
    }
}
