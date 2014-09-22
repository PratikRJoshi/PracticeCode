public class TwoDimSearch {
    public boolean searchMatrix(int[][] matrix, int target) {
        
        for(int i=0;i<matrix[0].length;i++){
            if(i==matrix.length-1){
                int result = binarySearch(matrix[i], target);
                if(result == -1)
                    return false;
                else
                    return true;
            }
            else if(target>=matrix[i][0] && target<matrix[i+1][0]){
                int result = binarySearch(matrix[i], target);
                if(result == -1)
                    return false;
                else
                    return true;
            }
        }
        return false;
    }
    public int binarySearch(int[] a, int x) {
        int low = 0;
        int high = a.length - 1;
        while (low <= high) {
            int mid = (low + high)/2;
            if (a[mid] == x) 
                return mid;
            else if (a[mid] < x) 
                low = mid + 1;
            else 
                high = mid - 1;
        }
      return -1;
   }
    
    public static void main(String args[]){
    	TwoDimSearch td = new TwoDimSearch();
    	int input[][]={{1},{3}};
    	int target = 0;
    	System.out.println("Result is: "+td.searchMatrix(input, target));
    }
}