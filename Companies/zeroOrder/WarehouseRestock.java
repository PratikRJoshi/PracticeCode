package zeroOrder;

import java.util.ArrayList;
import java.util.List;

public class WarehouseRestock {
    public static int restock(List<Integer> itemCount, int target) {
        int sum = 0;
        int index = 0;

        while(index < itemCount.size()) {
            sum += itemCount.get(index);
            if(sum >= target)
                break;
            index++;
        }

        if(sum == target)
            return 0;
        else
            return Math.abs(target - sum);
    }

    public static void main(String[] args) {
//        int[] array = {6, 1,2, 1};
        int[] array = {1, 2, 3, 2, 1};
        int target = 4;
        List<Integer> list = new ArrayList<>();
        for (int a : array){
            list.add(a);
        }

        int result = restock(list, target);
        System.out.println(result);

    }

}
