import java.util.ArrayList;
import java.util.List;

public class Permutations {
    public List<List<Integer>> permute(int[] num) {
        List<List<Integer>> resultList = new ArrayList<List<Integer>>();
        permute(num, 0, resultList);
        return resultList;
    }
    
    void permute(int array[], int start, List<List<java.lang.Integer>> resultList){
        if(start>=array.length){
            ArrayList<Integer> tempList = arrayToList(array);
            resultList.add(tempList);
        }
        else{
            for(int j=start;j<array.length;j++){
                swap(array, start, j);
                permute(array, start+1, resultList);
                swap(array, start, j);
            }
        }
    }
    
    ArrayList<Integer> arrayToList(int tempArray[]){
        ArrayList<Integer> listToReturn = new ArrayList<Integer>();
        for(int i=0;i<tempArray.length;i++){
            listToReturn.add(tempArray[i]);
        }
        return listToReturn;
    }
    
    void swap(int array[], int i, int j){
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
    
    public static void main(String args[]){
    	Permutations p = new Permutations();
    	int inputArray[] = {1,2,3};
    	p.permute(inputArray);
    }
}