package zeroOrder;

import java.util.HashSet;
import java.util.Set;

public class MinDelToMakeCharFreqUnique {
    public static int minDeletions(String s) {
        if(s == null || s.length() == 0)
            return 0;

        int count[] = new int[26], result = 0;

        Set<Integer> set = new HashSet<>();

        for (int i = 0; i < s.length(); ++i)
            ++count[s.charAt(i) - 'a'];

        for (int i = 0; i < 26; ++i) {
            int freq = count[i];

            while (freq > 0 && !set.add(freq)) {
                freq--;
                result++;
            }
        }

        return result;
    }

    public static void main(String[] args) {
        System.out.println(minDeletions("ccaaffddecee")); // 6
        System.out.println(minDeletions("aaaabbbb")); // 1
        System.out.println(minDeletions("eee")); // 0
        System.out.println(minDeletions("example")); // 4
    }
}
