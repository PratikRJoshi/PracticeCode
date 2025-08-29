/**
 *  Given an input string, reverse the string word by word.
 *  
 *  For example,
 *  
 *  Given s = "the sky is blue",
 *  return "blue is sky the".
 *  
 *  click to show clarification.
 *  
 *  Clarification:
 *  What constitutes a word?
 *  A sequence of non-space characters constitutes a word.
 *  Could the input string contain leading or trailing spaces?
 *  Yes. However, your reversed string should not contain leading or trailing spaces.
 *  How about multiple spaces between two words?
 *  Reduce them to a single space in the reversed string.

 * */


import java.util.Stack;

public class WordsReverse {
	 public String reverseWords(String s) {
	        if(s.length() == 0 || s == null)
	            return "";
	        
	        s = s.trim();
	        String arrayOfCharacters[] = s.split("\\s+");
	        
	        Stack<String> stack = new Stack<String>();
	        for(int i = 0;i<arrayOfCharacters.length;i++)
	            stack.push(arrayOfCharacters[i]);
	            
	        StringBuilder sb = new StringBuilder();
	        while(stack.size()!= 1){
	            sb.append(stack.pop()+" ");
	        }
	        sb.append(stack.pop());
	        String result = sb.toString();
	        return result;
	    }

	public static void main(String args[]){
		String input = " 	Hi! My name is    ABCD  ";
		WordsReverse w = new WordsReverse();
		String result = w.reverseWords(input);
		System.out.println(result);
	}
}
