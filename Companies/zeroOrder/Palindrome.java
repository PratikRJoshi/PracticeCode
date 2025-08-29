package zeroOrder;

public class Palindrome {
    public static boolean isPalindrome(String s) {
        if(s == null || s.length() == 0)
            return true;

        s = s.trim();
        s = s.toLowerCase();
        s = s.replaceAll("\\s+", "");
        s = s.replaceAll("[^a-zA-Z0-9]", "");

        int start = 0, end = s.length() - 1;

        while(start < end){
            if(s.charAt(start) == s.charAt(end)){
                start++;
                end--;
            } else {
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args) {
        String s = "A man, a plan, a canal: Panama";
        System.out.println(isPalindrome(s));
    }
}
