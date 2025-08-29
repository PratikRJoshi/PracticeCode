
public class SubstringPresenceCheck {
	public String strStr(String haystack, String needle) {

		//if there is no needle, then terminate without botheringo to start
		if (needle.length()==0){
			return haystack;
		}
		
		int i=0;
		//check from the beginning till the end of the haystack
		while (i<haystack.length()){
			//if the length of needle is more than the length of remaining part in haystack, 
			//just terminate because there won't be any match ahead
			if (haystack.length()-i<needle.length()){
				break;
			}
			//if there is a match in the haystack with the current character, then proceed with comparisons
			if (haystack.charAt(i)==needle.charAt(0)){
				//make a mark of the possible starting solution substring 
				int j=i;
				//while the characters at subsequent positions are same
				//and the length from the previously made mark till current position is within the needle length
				while (j-i<needle.length()&&needle.charAt(j-i)==haystack.charAt(j)){
					j++;
					
					// if we have been able to match all the characters till length equal to that of the needle
					//then we have found the solutoion
					if (j-i==needle.length()){
						return haystack.substring(i);
					}
				}
			}
			i++;
		}
		return null;
	}
	
	public static void main(String args[]){
		SubstringPresenceCheck sp = new SubstringPresenceCheck();
		String needle = "issip";
		String haystack = "mississippi";
		String result = sp.strStr(haystack, needle);
		System.out.println("Result is of class "+result.getClass().getName());
		System.out.println(result);
	}
}
