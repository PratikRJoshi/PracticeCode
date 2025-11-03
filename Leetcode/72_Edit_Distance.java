
public class EditDistance {
	int minDistance(String word1, String word2) {
		if(word1 == null || word2 == null)
			return 0;
		
		if(word1.length()==0){
        	return word2.length();
        }
        
        if(word2.length()==0){
        	return word1.length();
        }
		int lengtOfWord1 = word1.length();
        int lengthOfWord2 = word2.length();
        
        
        
        int editDistanceArray[][] = new int[lengtOfWord1+1][lengthOfWord2+1];
        
        for(int i=0;i<= lengtOfWord1;i++)
        	editDistanceArray[i][0] = i;
        
        for(int j=0;j<=lengthOfWord2;j++)
        	editDistanceArray[0][j] = j;
        
        for(int i=0;i<lengtOfWord1;i++){
        	char firstChar = word1.charAt(i);
        	
        	for(int j=0;j<lengthOfWord2;j++){
        		char secondChar = word2.charAt(j);
        		
        		if(firstChar == secondChar)
        			editDistanceArray[i+1][j+1] = editDistanceArray[i][j];
        		else{
                    //the cost of replacing a character
        			int costR = editDistanceArray[i][j] ;
        			int replaceCost = costR + 1;
        			
                    //the cost of deleting a character
        			int costD = editDistanceArray[i][j+1] ;
        			int deleteCost = costD + 1;
        			
                    //the cost of inserting a character
        			int costI = editDistanceArray[i+1][j] ;
        			int insertCost = costI + 1;
        			
                    //the minimum cost among all the above three costs
        			int min = replaceCost < deleteCost ? replaceCost : deleteCost;
        			min = min < insertCost ? min : insertCost;
        			editDistanceArray[i+1][j+1] = min;
        		}
        	}
        }
        return editDistanceArray[lengtOfWord1][lengthOfWord2];
    }
	
	public static void main(String args[]){
		EditDistance ed = new EditDistance();
		String firstWord = "distance";
		String secondWord = "springbok";
		int result = ed.minDistance(firstWord, secondWord);
		System.out.println("Minimum distance = "+result);
	}
}
