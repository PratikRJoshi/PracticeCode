
public class QuickSort {
	static int input[] = {1, 2, 5, 3, 4};
	public static void main(String args[]){
		
		sort(input);
		
		for (int i : input) {
			System.out.print(i+" ");
		}
	}
	
	public static void sort(int[] input){
		if(input == null || input.length == 0)
			return;
		
		int length = input.length;
		
		quickSort(input, 0, length-1);
	}
	
	public static void quickSort(int[] array, int start, int end){
		//before starting the sorting, set the start and the end indices
		int i = start;
		int j = end;
		
		//now select the pivot element within the start and end bounds
		//usually the pivot is selected to be the middle element
		int pivot = array[start + (end - start)/2];
		
		//now divide the array around the pivot element
		while(i<=j){
			//traverse through the array and find the first element which is bigger than the pivot on the left side
			while(array[i] < pivot)
				i++;
			
			//in the same way as above, find the first element smaller than pivot on right side
			while(array[j] > pivot)
				j--;
			
			//once you have found out those two elements, swap them
			if(i<=j){
				swap(array, i, j);
				
				//once the swapping is done, increment the pointers to the corresponding next elements
				i++;
				j--;
			}
		}
		//now call the quick sort method recursively on the left side of pivot and right side of pivot
		if(start < j)
			quickSort(array, start, j);
		if(end > i)
			quickSort(array, i, end);
	}
	
	public static void swap(int array[], int firstNumber, int secondNumber){
		int temp = array[firstNumber];
		array[firstNumber] = array[secondNumber];
		array[secondNumber] = temp;
	}
}
