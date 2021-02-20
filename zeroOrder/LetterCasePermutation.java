package zeroOrder;

import java.util.ArrayList;
import java.util.List;

public class LetterCasePermutation {
    private static List<String> letterCasePermutations(String input){
        List<String> result = new ArrayList<>();
        if (input == null || input.length() == 0)
            return result;

        solve(input, "", 0, result);
        return result;
    }

    private static void solve(String input, String output, int index, List<String> result) {
        if (index == input.length()){
            result.add(output);
            return;
        }

        if (Character.isDigit(input.charAt(index))){
            solve(input, output + input.charAt(index), index + 1, result);
        } else {
            solve(input, output + Character.toLowerCase(input.charAt(index)), index + 1, result);
            solve(input, output + Character.toUpperCase(input.charAt(index)), index + 1, result);
        }
    }

    public static void main(String[] args) {
        String input = "a1B2";
        List<String> letterCasePermutations = letterCasePermutations(input);
        letterCasePermutations.forEach(System.out::println);
    }
}
