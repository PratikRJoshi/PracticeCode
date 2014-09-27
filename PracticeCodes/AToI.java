/*************************************************************************
 *  Implement atoi to convert a string to an integer.

Hint: Carefully consider all possible input cases. 
If you want a challenge, please do not see below and ask yourself what are the possible input cases.

Notes: It is intended for this problem to be specified vaguely (ie, no given input specs). 
You are responsible to gather all the input requirements up front.


Requirements for atoi:

 * The function first discards as many whitespace characters as necessary until the first non-whitespace character is found. 
 * Then, starting from this character, takes an optional initial plus or minus sign followed by as many numerical digits as 
 * possible, and interprets them as a numerical value.

 * The string can contain additional characters after those that form the integral number, which are ignored and have no effect 
 * on the behavior of this function.

 * If the first sequence of non-whitespace characters in str is not a valid integral number, 
 * or if no such sequence exists because either str is empty or it contains only whitespace characters, no conversion is performed.

 * If no valid conversion could be performed, a zero value is returned. 
 * If the correct value is out of the range of representable values, INT_MAX (2147483647) or INT_MIN (-2147483648) is returned.

 *
 *************************************************************************/



public class AtoI {
	public int atoi(String str) {
        //if the string is empty or null, return 0
        if(str.length()==0 || str.isEmpty())
            return 0;
            
        //trim the leading and trailing whitespaces
        str = str.trim();
        
        int signFlag = 1;
        int index = 0;
        
        //check for the negative sign
        if(str.charAt(0)=='-'){
            signFlag = -1;
            index++;
        }
        if(str.charAt(0)=='+'){
            signFlag = 1;
            index++;
        }
        
        double finalInteger = 0;
//        int power = 0;
        
        while(index<str.length()){
        	char c = str.charAt(index);
        	if((c-'0'>=0 && c-'0'<=9)){
        		finalInteger = finalInteger*10 + (str.charAt(index)-'0');
        		index++;
        	}
        	else if(c==' '){
//        		index++;
        		finalInteger*=signFlag;
    		return (int) finalInteger;
        }
        	else{
        		finalInteger*=signFlag;
        		return (int) finalInteger;
        	}
        }
        
        finalInteger*=signFlag;
        
        //check for overflow or underflow
        if(finalInteger>Integer.MAX_VALUE)
            finalInteger = Integer.MAX_VALUE;
        
        if(finalInteger<Integer.MIN_VALUE)
            finalInteger = Integer.MIN_VALUE;
            
        return (int)finalInteger;
        
    }
	public static void main(String args[]){
		AtoI t = new AtoI();
		String input = " +0 123";
		int result = t.atoi(input);
		System.out.println(result);
	}
}
