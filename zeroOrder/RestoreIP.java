package zeroOrder;

import java.util.ArrayList;
import java.util.List;

public class RestoreIP {
    public static List<String> restoreIpAddresses(String s) {
        List<String> result = new ArrayList<>();
        doRestore(s, 0, "", result);
        return result;
    }

    private static void doRestore(String s, int segments, String current, List<String> result) {
        if (s.isEmpty() || segments == 4) {
            if (s.isEmpty() && segments == 4) {
                result.add(current.substring(1));
            }
            return;
        }
        for (int i = 1; i <= s.length() && i <= (s.charAt(0) == '0' ? 1 : 3) ; i++) { // Avoid leading 0
            String part = s.substring(0, i);
            if (Integer.parseInt(part) <= 255)
                doRestore(s.substring(i), segments + 1, current + "." + part, result);
        }
    }

    public static void main(String[] args) {
        String s = "25525511135";
        List<String> ipAddresses = restoreIpAddresses(s);
        ipAddresses.forEach(System.out::println);
    }
}
