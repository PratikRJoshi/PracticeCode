package zeroOrder;

import java.util.List;

public class ModifyArray {
    public static long modifyArray(List<Integer> array) {
        int increasing = 0, dec = 0;
        long result = 0L;

        for (int i = 0; i < array.size() - 1; i++) {
            if (array.get(i) >= array.get(i + 1)){
                dec++;
            }
        }

        for (int i = 0; i < array.size() - 1; i++) {
            if (array.get(i) <= array.get(i + 1)){
                increasing++;
            }
        }

        if (increasing > dec){
            result = makeIncreasing(array);
        } else {
            result = makeDecreasing(array);
        }

        return result;
    }

    private static long makeIncreasing(List<Integer> array) {
        return 0;
    }

    private static long makeDecreasing(List<Integer> array) {
        return 0;
    }
}
