package zeroOrder;

import java.util.ArrayList;
import java.util.List;

public class Subsets {
    public static void main(String[] args) {
        int[] nums = {1, 2, 3};
        Subsets s = new Subsets();
        List<List<Integer>> result = s.subsets(nums);
        result.forEach(System.out::println);
    }

    private List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        result.add(new ArrayList<>());
        for(int n : nums){
//            int size = result.size();
            for(int i=0; i<result.size(); i++){
                List<Integer> subset = new ArrayList<>(result.get(i));
                subset.add(n);
                result.add(subset);
            }
        }
        return result;
    }
}
