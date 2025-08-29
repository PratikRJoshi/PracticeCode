package zeroOrder;

import java.util.ArrayList;
import java.util.List;

public class More1sThan0sInBinary {
    public static List<String> generateBinaryNumbers(int n) {
        List<String> result = new ArrayList<>();
        if(n == 0)
            return result;
        StringBuilder sb = new StringBuilder();
        generateBinaryNumbers(n, 0, 0, sb, result);
        return result;
    }

    private static void generateBinaryNumbers(int n, int open, int closed, StringBuilder sb, List<String> result){
        if(sb.length() <= n){
            result.add(sb.toString());
            return;
        }

        if(open < n){
            sb.append('1');
            generateBinaryNumbers(n, open + 1, closed, sb, result);
            sb.deleteCharAt(sb.length() - 1);
        }
        if(closed < open){
            sb.append('0');
            generateBinaryNumbers(n, open, closed + 1, sb, result);
            sb.deleteCharAt(sb.length() - 1);
        }
    }

    public static void main(String[] args) {
        int length = 2;
        List<String> binaryNumbers = generateBinaryNumbers(length);
        binaryNumbers.forEach(System.out::println);
    }
}
