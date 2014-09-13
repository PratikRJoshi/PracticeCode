import java.util.ArrayList;
import java.util.List;

public class Parentheses {
    public List<String> generateParenthesis(int n) {
        List<String> result = new ArrayList<String>();
        StringBuilder string = new StringBuilder();
        int openBracketsCount = 0;
        int closedBracketsCount = n;
        result = parenthesisGenerator(openBracketsCount, closedBracketsCount, string, result);
        return result;
    }
    
    public List<String> parenthesisGenerator(int openBracketsCount, int closedBracketsCount, StringBuilder parenthesisString, List<String> tempList){
        if(closedBracketsCount == 0 && openBracketsCount ==0){
            tempList.add(parenthesisString.toString());
            return tempList;
        }
        //if there is an open bracket remaining, then we need to close it
        if(openBracketsCount > 0){
            parenthesisString.append(")");
            parenthesisGenerator(openBracketsCount - 1, closedBracketsCount, parenthesisString, tempList);
            //after closing an open bracket, go back one step so that we can find more valid pairs
            parenthesisString.setLength(parenthesisString.length()-1);
        }
        //if closed brackets are paired, then we can start a new pair
        if(closedBracketsCount > 0){
            parenthesisString.append("(");
            parenthesisGenerator(openBracketsCount+1, closedBracketsCount-1, parenthesisString, tempList);
            parenthesisString.setLength(parenthesisString.length()-1);
        }
        return tempList;
    }
    public static void main(String args[]){
    	Parentheses p = new Parentheses();
    	List<String> resultantList = new ArrayList<String>();
    	resultantList = p.generateParenthesis(3);
    	System.out.println(resultantList);
    }
}