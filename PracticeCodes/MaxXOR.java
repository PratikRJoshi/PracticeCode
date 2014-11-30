/*
  
 							MaxXOR
 					=======================
		Problem Statement

Given two integers: L and R,

find the maximal values of A xor B given, L ≤ A ≤ B ≤ R

Input Format 
The input contains two lines, L is present in the first line. 
R in the second line.

Constraints 
1 ≤ L ≤ R ≤ 103

Output Format 
The maximal value as mentioned in the problem statement.

Sample Input#00

1
10
Sample Output#00

15
Sample Input#01

10
15
Sample Output#01

7
Explanation

In the second sample let's say L=10, R=15, then all pairs which comply to above condition are 
10⊕10=0 
10⊕11=1 
10⊕12=6 
10⊕13=7 
10⊕14=4 
10⊕15=5 
11⊕11=0 
11⊕12=7 
11⊕13=6 
11⊕14=5 
11⊕15=4 
12⊕12=0 
12⊕13=1 
12⊕14=2 
12⊕15=3 
13⊕13=0 
13⊕14=3 
13⊕15=2 
14⊕14=0 
14⊕15=1 
15⊕15=0 
Here two pairs (10,13) and (11,12) have maximum xor value 7 and this is the answer.
 * 
 * */

import java.util.Scanner;

public class MaxXOR {
/*
 * Complete the function below.
 */

    static int maxXor(int l, int r) {
        int max = 0;
        
        for(int i = l; i <= r; i++){
            for(int j = l; j <= r; j++){
                int value = i ^ j;
                if(max < value)
                    max = value;
            }
        }
        return max;
    }

    public static void main(String[] args) {
    	System.out.println("Enter the value of L: ");
        Scanner in = new Scanner(System.in);
        int res;
        int _l;
        _l = Integer.parseInt(in.nextLine());
        
        System.out.println("Enter the value of R: ");
        int _r;
        _r = Integer.parseInt(in.nextLine());
        
        res = maxXor(_l, _r);
        System.out.println("The maximum value of Xor for "+_l+" and "+_r+" = "+res);
        in.close();
    }
}
