package zeroOrder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class MergingPackages {

    static int[] getIndicesOfItemWeights(int[] arr, int limit) {
        // your code goes here
        if(arr == null || arr.length == 0)
            return new int[0];

        Map<Integer, Integer> map = new HashMap<>(); // <element, index>

        for(int i = 0; i < arr.length; i++){
            map.put(arr[i], i);
        }

        List<Integer> result = new ArrayList<>();
//    int[] result = new int[2];
        for(int i = 0; i < arr.length; i++){
            int currentElem = arr[i];
            if(map.containsKey(limit - currentElem)
                    && map.get(limit - currentElem) != i){
                //result[0] = map.get(limit - currentElem);
                result.add(map.get(limit - currentElem));
                result.add(i);
                break;
            }
        }

        int[] ans = new int[2];
        if(result.size() == 0){
            return new int[0];
        } else {

            ans[0] = result.get(0);
            ans[1] = result.get(1);

        }

        return ans ;
    }

    /**
     [4, 15, 10, 6, 16]
     */
    public static void main(String[] args) {
        int[] arr = {4, 15, 10, 6, 16};
        int limit = 21;

        int[] result = getIndicesOfItemWeights(arr, limit);
        for(int i : result){
            System.out.println(i);
        }
    }

}