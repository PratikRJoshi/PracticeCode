package zeroOrder;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Battleship {
    public static String hitsAndSunks(int N, String S, String T) {
        Set<String> hitSlots = new HashSet<>(Arrays.asList(T.split("\\s+")));
        int hits = 0, sunk = 0;
        String[] shipLoc = S.split(",");
        for (String ship : shipLoc){
            Set<String> shipParts = new HashSet<>();
            char top = ship.charAt(0);
            char left = ship.charAt(1);
            char bottom = ship.charAt(3);
            char right = ship.charAt(4);

            for (char i = top; i <= bottom; i++){
                for (char j = left; j <= right; j++){
                    shipParts.add(""+i + j);
                }
            }

            if (hitSlots.containsAll(shipParts)){
                sunk++;
            } else {
                for (String s : shipParts){
                    if (hitSlots.contains(s)){
                        hits++;
                    }
                }
            }
        }

        return sunk + "," + hits;
    }

    public static void main(String[] args) {
//        String ans = hitsAndSunks(12, "1A 2A,12A 12A", "12A");
        String ans = hitsAndSunks(12, "1B 2C,2D 4D", "2B 2D 3D 4D 4A");
        System.out.println(ans);
    }
}
