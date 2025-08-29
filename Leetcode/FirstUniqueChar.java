import java.util.HashMap;
import java.util.Map;

/* 
 * Given a string, find the first non-repeating character in it. 
 * For example, if the input string is “GeeksforGeeks”, then output should be ‘f’ 
 * and if input string is “GeeksQuiz”, then output should be ‘G’.
 * 
 * */
public class FirstUniqueChar {
	
	public char firstUniqueChar(String input){
		input = input.toLowerCase();
		Map<Character, Integer> map = new HashMap<Character, Integer>();
		
		//store each character, its position and its count in the map
		for(int i = 0; i<input.length(); i++){
			char current = input.charAt(i);
			
			if(map.containsKey(current)){
				map.put(current, map.get(current) + 1);
			}
			else{
				map.put(current, 1);
			}
		}
		
		//traverse the string again to find the character for whom the count is 1
		for(int i = 0; i < input.length(); i++){
			char current = input.charAt(i);
			if(map.get(current) == 1)
				return current;
		}
		return (char) -1;

	}
	
	public static void main(String args[]){
		FirstUniqueChar fuc = new FirstUniqueChar();
		String input = "Amazom";
		char result = fuc.firstUniqueChar(input);
		System.out.println("First unique character is "+((result == -1) ? "not found!" : result));
	}
}

