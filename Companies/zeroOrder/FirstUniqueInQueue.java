package zeroOrder;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class FirstUniqueInQueue {
    static class FirstUnique {

        Set<Integer> allElements, uniqueSet;

        public FirstUnique(int[] nums) {
            allElements = new HashSet<>();
            uniqueSet = new LinkedHashSet<>();

            for (int n : nums){
                add(n);
            }
        }

        public int showFirstUnique() {
            return !uniqueSet.isEmpty() ? uniqueSet.iterator().next() : -1;
        }

        public void add(int value) {
            if (allElements.add(value)){
                uniqueSet.add(value);
            } else {
                uniqueSet.remove(value);
            }
        }
    }

    public static void main(String[] args) {
        int[] input = new int[]{7,7,7,7};
        FirstUnique firstUnique = new FirstUnique(input);
        System.out.println(firstUnique.showFirstUnique());
        firstUnique.add(7);
        firstUnique.add(17);
        firstUnique.add(3);
        System.out.println(firstUnique.showFirstUnique());
    }
}
