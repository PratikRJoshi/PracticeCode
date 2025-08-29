/*************************************************************************
 * Given an unsorted array of integers, find the length of the longest consecutive elements sequence.

 * For example,
 * Given [100, 4, 200, 1, 3, 2],
 * The longest consecutive elements sequence is [1, 2, 3, 4]. Return its length: 4.

 * Your algorithm should run in O(n) complexity. 
 *************************************************************************/


import java.util.HashMap;
import java.util.Map;

public class LongestIncreasingSequence {
    public int longestConsecutive(int[] num) {
        if(num.length==0)
            return 0;
        if(num.length==1)
            return 1;
            
        Map<Integer, Integer> consecutiveMap = new HashMap<Integer, Integer>();
        
        //store the elements in hashmap so that we can get them in constant time when needed
        for(int i=0;i<num.length;i++)
            consecutiveMap.put(num[i], i);
        
        //find the minimum element in the given array which wil be the starting point of the solution
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;
        for(int i=0;i<num.length;i++){
            if(num[i]<min)
                min = num[i];
            if(num[i]>max)
            	max = num[i];
        }
        
        int countOfConsecutiveElements = 1, maxCountOfConsecutiveElements = 0;
        for(int i=min;i<max;i++){
            if(consecutiveMap.get(min+1)!=null){
                countOfConsecutiveElements++;
                min = min + 1;
            }
            else{
                if(maxCountOfConsecutiveElements<countOfConsecutiveElements)
                    maxCountOfConsecutiveElements = countOfConsecutiveElements;
                countOfConsecutiveElements = 0;
                min = min + 1;
            }
        }
        
        return maxCountOfConsecutiveElements>countOfConsecutiveElements?maxCountOfConsecutiveElements:countOfConsecutiveElements;
    }
    
    public static void main(String args[]){
    	
    	LongestIncreasingSequence s = new LongestIncreasingSequence();
    	int input[] = {2147483646,-2147483647,0,2,2147483644,-2147483645,2147483645};
    	int value = s.longestConsecutive(input);
    	System.out.println(value);
    }
}
