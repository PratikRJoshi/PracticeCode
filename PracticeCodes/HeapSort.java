
public class HeapSort {
	static int N;
	public static void sort(int[] inputArray){
		//initially heapify to build the heap from the array
		heapify(inputArray);
		for(int i=N;i>0;i--){
			swap(inputArray, 0, i);
			N = N-1;
			maxHeap(inputArray, 0);
		}
	}
	
	public static void heapify(int[] array){
		N = array.length - 1;
		//in this method, we call maxHeap method to build a max heap from bottom up
		for(int i=N/2;i>=0;i--)
			maxHeap(array, i);
	}
	
	public static void maxHeap(int[] input, int index){
		int leftChild = 2*index;
		int rightChild = 2*index + 1 ;
		int max = index;
		
		//find which of the three - parent, left and right child - is maximum
		if(leftChild<=N && input[leftChild]>input[index])
			max = leftChild;
		
		if(rightChild<=N && input[rightChild]>input[index])
			max = rightChild;
		
		//if the max value is not the initially initialized index value, then swap the max with the index
		if(max!=index){
			swap(input, index, max);
			//after swapping we get the maximum value at the correct position in the heap. But we need to
			//check if the swapping broke the heap property .. so we do maxHeap again 
			maxHeap(input, max);
		}
	}
	
	public static void swap(int[] arr, int index, int max){
		int tempValue = arr[index];
		arr[index] = arr[max];
		arr[max] = tempValue;
	}
	
	public static void main(String args[]){
		int inputArrayToHeapSort[] = {10, 8, 7, -2, 0, 122};
		sort(inputArrayToHeapSort);
		
		//print the sorted elements
		for(int i=0;i<inputArrayToHeapSort.length;i++)
			System.out.print(inputArrayToHeapSort[i]+" ");
	}
}
