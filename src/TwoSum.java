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
    	int inputArray[] = {5,75,25};
    	int target = 100;
    	int output[] = new int[2];
    	output = s.twoSum(inputArray, target);
    	System.out.println(output[0] + " "+output[1]);
    	
    }
}