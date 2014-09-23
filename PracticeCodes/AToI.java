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