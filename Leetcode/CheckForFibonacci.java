/*
 * Given a number ‘n’, check if n is a Fibonacci number.
 * 
 * */
public class CheckForFibonacci {
	
	//A number is Fibonacci if and only if one or both of 
	//(5*n2 + 4) or (5*n2 – 4) is a perfect square (Source: Wikipedia)
	public boolean isFibonacci(int input){
		int one = 5 * input * input + 4;
		int two = 5 * input * input - 4;
		int squareRootOne = (int) Math.sqrt(one);
		int squareRootTwo = (int) Math.sqrt(two);
		return (squareRootOne*squareRootOne == one
				||
				squareRootTwo * squareRootTwo == two);
	}
	
	public static void main(String args[]){
		CheckForFibonacci cf = new CheckForFibonacci();
		int input = 5;
		boolean result = cf.isFibonacci(input);
		System.out.println("The given number is "+ (result? "a fibonacci number" : "not a fibonacci number"));
	}
}
