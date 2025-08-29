package zeroOrder;

public class ValidPalindromeAtMost1 {
    public static boolean validPalindrome(String s) {
        int k = 1;

        return validPalindrome(s, 0, s.length() - 1,  k);
    }

    private static boolean validPalindrome(String s, int start, int end, int k){
        if(start >= end){
            return true;
        }

        if(s.charAt(start) == s.charAt(end)){
            return validPalindrome(s, start + 1, end - 1, k);
        } else if(k > 0){
            return validPalindrome(s, start + 1, end, k - 1) || validPalindrome(s, start, end - 1, k - 1);
        }

        return false;
    }

    public static void main(String[] args) {
        String s = "adbca";
        System.out.println(validPalindrome(s));
    }
}
