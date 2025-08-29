package zeroOrder;

import java.util.ArrayList;
import java.util.List;

public class SortListRecursion {
    private static void sortListRecursion(List<Integer> list){
        if (list.size() == 1 )
            return;
        int temp = list.remove(list.size() - 1);
        sortListRecursion(list);
        insertIntoSortedList(list, temp);
    }

    private static void insertIntoSortedList(List<Integer> list, int temp) {
        if (list.size() == 0 || list.get(list.size() - 1) <= temp){
            list.add(temp);
            return;
        }

        int val = list.remove(list.size() - 1);
        insertIntoSortedList(list, temp);
        list.add(val);
    }

    public static void main(String[] args) {
        int[] array = {1, 0, 2, 6, 3};
        List<Integer> list = new ArrayList<>();
        for (int i : array) {
            list.add(i);
        }
        sortListRecursion(list);
        list.forEach(System.out::println);
    }
}
