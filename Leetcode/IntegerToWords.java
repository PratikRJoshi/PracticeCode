

public class IntegerToWords {
	
	//maximum of 4 digits for this method
	public String integerToWords(String input){
		if(input.length() > 4){
			System.err.println("This method can only handle maximum of 4 digits");
			return null;
		}
			
		
		/* The first string is not used, it is to make array indexing simple */
	    String single_digits[] = { "zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};
	 
	    /* The first string is not used, it is to make array indexing simple */
	    String two_digits[] = {"", "ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen"};
	 
	    /* The first two string are not used, they are to make array indexing simple*/
	    String tens_multiple[] = {"", "", "twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninety"};
	 
	    String tens_power[] = {"hundred", "thousand"};
	    
	    StringBuilder sb = new StringBuilder();
	    
	    int start = 0;
	    int length = input.length();
	    
	    if(length == 1)
	    	return single_digits[Integer.parseInt(input)];
	    
	    while(start != input.length()){
	    	if(length >= 3){

	    		sb.append(single_digits[input.charAt(start) - '0']).append(" ").append(tens_power[length - 3]).append(" ");
	    		length--;
	    	}
	    	//Code for last 2 digits
	    	else{
	    		if(input.charAt(start) == '1'){
	    			int sum = (input.charAt(start) - '0') + (input.charAt(start+1) - '0');
	    			sb.append(two_digits[sum]).append(" ");
	    			return sb.toString();
	    		}
	    		/* Need to explicitely handle 20 */
	    		else if(input.charAt(start) == '2' && input.charAt(start + 1) == '0'){
	    			sb.append("twenty").append(" ");
	    			return sb.toString();
	    		}
	    		
	    		else{
	    			int i = input.charAt(start) - '0';
	    			sb.append(tens_multiple[i]).append(" ");
	    			start++;
	    			if(input.charAt(start)!='0')
	    				sb.append(single_digits[input.charAt(start) - '0']).append(" ");
	    		}
	    	}
	    	start++;
	    }
	    
	    return sb.toString();
	}
	
	public static void main(String args[]){
		IntegerToWords itw = new IntegerToWords();
		String input = "12335";
		String result = itw.integerToWords(input);
		System.out.println(input + " -> "+(result == null? "No result" : result));
	}
}
