/*************************************************************************
 *  Given an array of integers, find two numbers such that they add up to a specific target number.
 *  
 *  The function twoSum should return indices of the two numbers such that they add up to the target, 
 *  where index1 must be less than index2. 
 *  Please note that your returned answers (both index1 and index2) are not zero-based.
 *  You may assume that each input would have exactly one solution.
 *  
 *  Input: numbers={2, 7, 11, 15}, target=9
 *  Output: index1=1, index2=2 
 * 

 *************************************************************************/


import java.util.HashMap;
import java.util.Map;

public class TwoSum {
    public int[] twoSum(int[] numbers, int target) {
         if(numbers.length==0)
            return null;
        
        Map<Integer, Integer> targetMatchMap = new HashMap<Integer, Integer>();
        //insert the target - numbers[i] in the map
        for(int i=0;i<numbers.length;i++){
        	targetMatchMap.put(numbers[i], i);
        }
        
        //look for target-numbers[i] in the map
        int output[] = new int[2];
        int contains = 0;
        for(int i=0;i<numbers.length;i++){
        	if(targetMatchMap.get(target - numbers[i])!=null)
        		contains = targetMatchMap.get(target - numbers[i]);
        	
            if(targetMatchMap.containsKey(target - numbers[i]) && contains!=i){
                output[0] = i+1;
                output[1] = targetMatchMap.get(target - numbers[i])+1;
                break;
            }
        }
        return output;
    }
    
    public static void main(String args[]){
    	TwoSum s = new TwoSum();
    	int input[] = {5,75,25};
    	int target = 100;
    	int value[] = s.twoSum(input, target);
    	System.out.println(value[0]+" "+value[1]);
    }
}
