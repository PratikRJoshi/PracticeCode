public class PalindromeNumber {
    public boolean isPalindrome(int x) {
        if(x<0)
            return false;
        
        //count the number of digits first
        int powerOfTenByCountOfDigits = 1;
        while(x/powerOfTenByCountOfDigits>=10)
            powerOfTenByCountOfDigits*=10;
        
        while(x!=0){
            int leftMostDigit = x/powerOfTenByCountOfDigits;
            int rightMostDigit = x%10;
            
            if(leftMostDigit!=rightMostDigit)
                return false;
            x = (x%powerOfTenByCountOfDigits)/10;
            powerOfTenByCountOfDigits/=100;         //since we need to leave out the leftmost and the rightmost digits in the                                                           subsequent iterations
        }
        return true;
    }
    
    public static void main(String args[]){
    	PalindromeNumber pn = new PalindromeNumber();
    	boolean result = pn.isPalindrome(1234321);
    	System.out.println("The entered number is "+(result?"a palindrome":"not a palindrome"));
    }
}