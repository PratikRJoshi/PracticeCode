package zeroOrder;

public class ReverseString {
    public static void reverseString(char[] str){
        reverseString(str, 0, str.length - 1);
    }

    private static void reverseString(char[] str, int start, int end) {
        if (str == null || start >= end)
            return;
        // swap first and last char
        char c = str[start];
        str[start] = str[end];
        str[end] = c;

        reverseString(str, start + 1, end - 1);
    }


    public static void main(String[] args) {
        String input = "hello";
        reverseString(input.toCharArray());
        System.out.println(input);
    }
}
