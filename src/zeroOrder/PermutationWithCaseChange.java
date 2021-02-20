package zeroOrder;

public class PermutationWithCaseChange {
    private static void permuteWithCaseChange(String input, String output){
        if (input.equals("")){
            System.out.println(output);
            return;
        }

        String s1 = output + input.charAt(0);
        String s2 = output + Character.toUpperCase(input.charAt(0));
        input = input.substring(1);
        permuteWithCaseChange(input, s1);
        permuteWithCaseChange(input, s2);
    }

    public static void main(String[] args) {
        String input = "ab";
        permuteWithCaseChange(input, "");

    }
}
