
public class WordPalindrome {
	
	public boolean isWordAPalindrome(String text){
		
		String finalStringToCheck = text.trim().replaceAll("\\s+", " ").replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
		int start = 0;
		int end = finalStringToCheck.length() - 1;
		
		while(start<=end){
			if(!(finalStringToCheck.charAt(start) == finalStringToCheck.charAt(end)))
				return false;
			start++;
			end--;
		}
		
		return true;
	}
	
	public static void main(String args[]){
		WordPalindrome w = new WordPalindrome();
		String input = "		Are we not pure? 		No sir! Panama's moody Noriega brags. It is garbage! Irony dooms a man; a prisoner up to new era.	";
		boolean result = w.isWordAPalindrome(input);
		System.out.println(result);
	}
}
