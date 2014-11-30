/*
 * 											HackerRank
 * Problem Statement

You are given a number N. Find the digits in this number that exactly divide N and display their count. For N = 24, there are 2 digits - 2 & 4. Both these digits exactly divide 24. So our answer is 2.

Note

If the same number is repeated twice at different positions, it should be counted twice, e.g., For N=122, 2 divides 122 exactly and occurs at ones' and tens' position. So it should be counted twice. So for this case, our answer is 3.
Division by 0 is undefined.
Input Format

The first line contains T (number of test cases) followed by T lines (each containing an integer N).

Constraints 
1 <=T <= 15 
0 < N < 1010

Output Format

For each test case, display the count of digits in N that exactly divide N in separate line.

Sample Input

2
12
1012
Sample Output

2
3
Explanation

2 digits in the number 12 divide the number exactly. Digits at tens' place, 1, divides 12 exactly in 12 parts, and digit at ones' place, 2 divides 12 equally in 6 parts.

1 divides 1012 at two places and 2 divides it at one place. Divide by 0 is an undefined behaviour and it will not be counted.
 * */


import java.util.Scanner;

public class FindDigits {
    
    static int calculateNumberOfDigits(int currentInput){
        int count = 0;
        int tempInput = currentInput;
        while(tempInput > 0){
            int currentDigit = tempInput % 10;
            if(currentDigit!=0 && (currentInput % currentDigit) == 0)
                count++;
            tempInput = tempInput / 10;
        }
        
        return count;
    }

    public static void main(String[] args) {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */
        System.out.println("Enter the number of test cases");    
    	Scanner sc = new Scanner(System.in);
            int numberOfTests = sc.nextInt();
            
            
            for(int i = 0; i < numberOfTests; i++){
            	System.out.println("Enter number "+(i+1)+": ");
                int currentInput = sc.nextInt();
                int result = calculateNumberOfDigits(currentInput);
                System.out.println("The number of digits in the given number that divides the input number wholly = "+result);
            }
            sc.close();
    }
}