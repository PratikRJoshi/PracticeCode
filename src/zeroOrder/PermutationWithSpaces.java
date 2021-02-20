package zeroOrder;

import java.util.ArrayList;
import java.util.List;

public class PermutationWithSpaces {
    private static List<String> generatePermutationWithSpaces(String s){
        List<String> result = new ArrayList<>();
        if (s == null || s.length() == 0)
            return result;

//        StringBuilder sb = new StringBuilder();
//        sb.append(s.charAt(0));
//        int index = 1;
//        generatePermutationWithSpaces(s, index, sb, result);

        generatePermutationWithSpaces(s.substring(1), String.valueOf(s.charAt(0)));

        return result;
    }

    private static void generatePermutationWithSpaces(String input, String output) {
        if (input.length() == 0){
            System.out.println(output);
            return;
        }

        String s1 = output + "_" + input.charAt(0);
        String s2 = output + input.charAt(0);

        input = input.substring(1);
        generatePermutationWithSpaces(input, s1);
        generatePermutationWithSpaces(input, s2);
    }


    private static void generatePermutationWithSpaces(String s, int index, StringBuilder sb, List<String> result) {
        if (index == s.length()){
            result.add(sb.toString());
            return;
        }

        StringBuilder sb1 = sb.append("_").append(s.charAt(index));
        generatePermutationWithSpaces(s, index + 1, sb1, result);
        sb1.setLength(sb1.length() - 2);

        StringBuilder sb2 = sb.append(s.charAt(index));
        generatePermutationWithSpaces(s, index + 1, sb2, result);
        sb2.setLength(sb2.length() - 3);
    }

    public static void main(String[] args) {
        String input = "abc";
        List<String> permutationWithSpaces = generatePermutationWithSpaces(input);
        permutationWithSpaces.forEach(System.out::println);
    }
}
