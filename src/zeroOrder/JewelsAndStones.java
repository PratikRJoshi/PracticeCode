package zeroOrder;

import java.util.HashMap;
import java.util.Map;

public class JewelsAndStones {
    public int numJewelsInStones(String J, String S) {
        Map<Character, Integer> map = new HashMap<>();

        for(char c : S.toCharArray()) {
            if(map.containsKey(c)) {
                map.put(c, map.get(c) + 1);
            } else {
                map.put(c, 1);
            }
        }

        int count = 0;
        for(char c : J.toCharArray()) {
            count += map.getOrDefault(c, 0);
        }

        return count;
    }

    public static void main(String[] args) {
        String S = "ZZ";
        String J = "z";
        JewelsAndStones jewelsAndStones = new JewelsAndStones();
        int numJewelsInStones = jewelsAndStones.numJewelsInStones(J, S);
        System.out.println(numJewelsInStones);
    }
}
