/**
 * Output all prime numbers up to a specified integer n.
 * 
 * */
public class AllPrimesTillN {
	
	public void calculatePrimesTillN(int limit){
		boolean notStrikedOutIntegers[] = new boolean[limit];
		for(int i = 2; i < limit;  i++)
			notStrikedOutIntegers[i] = true;
		
		for(int i = 2; i < limit; i++){
			if(notStrikedOutIntegers[i]){
				for(int j = i*i; j < limit; j+=i)
					notStrikedOutIntegers[j] = false;
			}
		}
		
		for(int i = 0; i < limit; i ++){
			if(notStrikedOutIntegers[i])
				System.out.println(i);
		}
	}
	
	public static void main(String args[]){
		AllPrimesTillN aptn = new AllPrimesTillN();
		int limit = 20;
		aptn.calculatePrimesTillN(limit);
	}
}

/**
 * The method used to get the output is known as the 'Sieve of Eratosthenes'.
 * Source: http://leetcode.com/2010/04/finding-prime-numbers.html
 * */
