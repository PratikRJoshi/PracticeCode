/* 
 * Find the contiguous subarray within an array (containing at least one number) which has the largest product.
 * For example, given the array [2,3,-2,4], the contiguous subarray [2,3] has the largest product = 6. 
 */


public class MaxProductSubArray {
    public int maxProduct(int[] A) {
        if(A==null || A.length ==0)
            return 0;
            
        int maxSoFar = Integer.MIN_VALUE;
        int currentMax = 1;
        int currentMin = 1;
        
        for(int i=0;i<A.length;i++){
            if(A[i]>0){
                currentMax = currentMax * A[i];
                currentMin = Math.min(currentMin * A[i], 1);
                maxSoFar = Math.max(maxSoFar, currentMax);
            }
            else if(A[i] == 0){
                currentMin = 1;
                currentMax = 1;
                maxSoFar = Math.max(maxSoFar, 0);
            }
            else{
                maxSoFar = Math.max(maxSoFar, A[i]*currentMin);
                int tempResult = currentMax;
                currentMax = Math.max(currentMin * A[i], 1);
                currentMin = tempResult * A[i];
            }
            
            // if(maxSoFar<currentMax)
                // maxSoFar = currentMax;
        }
        return maxSoFar;
    }
    
    public static void main(String args[]){
    	MaxProductSubArray mpsa = new  MaxProductSubArray();
    	int input[] = {4, 1, -2, -3, 0, 19, -3, 4, -3};
    	int result = mpsa.maxProduct(input);
    	System.out.println(result);
    }
}
