package zeroOrder;

public class PalindromeString {
    public boolean isPalindrome(String s) {
        if(s == null )
            return false;
        if(s.length() == 0)
            return true;

        String parsedString = s.trim().replaceAll("\\s+", " ").replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
        int start = 0, end = parsedString.length() - 1;

        while(start <= end) {
            if(parsedString.charAt(start) != (parsedString.charAt(end)))
                return false;
            start++;
            end--;
        }

        return true;
    }

    public static void main(String[] args) {
        String input = "A man, a plan, a canal: Panama";
        PalindromeString palindromeString = new PalindromeString();
        boolean isPalindrome = palindromeString.isPalindrome(input);
        System.out.println(isPalindrome);
    }
}
