/*
 * There is an array A[N] of N numbers. You have to compose an array Output[N] such that 
 * Output[i] will be equal to multiplication of all the elements of A[N] except A[i]. 
 * Solve it without division operator and in O(n).
 * For example Output[0] will be multiplication of A[1] to A[N-1] and Output[1] will be 
 * multiplication of A[0] and from A[2] to A[N-1].
 * 
 * Example:
 * A: {4, 3, 2, 1, 2}
 * 
 * OUTPUT: {12, 16, 24, 48, 24}
 * 
 * */
public class MultiplyByExclusion {
	
	public int[] positionExcludedMatrix(int[] input){
		int[] temp = new int[input.length];
		int B[] = new int[input.length];
		B[0] = input[0];
		for(int i = 1; i < B.length; i++){
			B[i] = input[i] * B[i-1]; 
		}
		int product = 1;
		
		for(int i = input.length-1; i >= 1; i--){
			temp[i] = B[i-1] * product;
			product *= input[i];
		}
		temp[0] = product;
		return temp;
	}
	
	public static void main(String args[]){
		MultiplyByExclusion m = new MultiplyByExclusion();
		int input[] = {4, 3, 2, 1, 2};
		input = m.positionExcludedMatrix(input);
		for(int i = 0; i < input.length; i++)
			System.out.println(input[i]);
	}
}
/*
 * Source : http://leetcode.com/2010/04/multiplication-of-numbers.html
 * */
