/*

                                                    Utopian Tree
                                            ===========================
The Utopian tree goes through 2 cycles of growth every year. The first growth cycle occurs during the spring, when it doubles in height. The second growth cycle occurs during the summer, when its height increases by 1 meter. 
Now, a new Utopian tree sapling is planted at the onset of the spring. Its height is 1 meter. Can you find the height of the tree after N growth cycles?

Input Format 
The first line contains an integer, T, the number of test cases. 
T lines follow. Each line contains an integer, N, that denotes the number of cycles for that test case.

Constraints 
1 <= T <= 10 
0 <= N <= 60

Output Format 
For each test case, print the height of the Utopian tree after N cycles.

Sample Input #00:

2
0
1
Sample Output #00:

1
2
Explanation #00: 
There are 2 test cases. When N = 0, the height of the tree remains unchanged. When N = 1, the tree doubles its height as it's planted just before the onset of spring.

Sample Input: #01:

2
3
4
Sample Output: #01:

6
7
Explanation: #01: 
There are 2 testcases. 
N = 3: 
the height of the tree at the end of the 1st cycle = 2 
the height of the tree at the end of the 2nd cycle = 3 
the height of the tree at the end of the 3rd cycle = 6

N = 4: 
the height of the tree at the end of the 4th cycle = 7

*/

import java.util.*;

public class UtopianTree {
    
    static int calculateFinalHeight(int currentInput){
        if(currentInput == 0)
            return 1;
        int height = 1;
        for(int i = 1; i <= currentInput; i++){
            if(i % 2 != 0){  //if it is the odd cycle, then double the current height
                height *= 2;
            }
            else{
                height += 1;
            }
        }
        return height;
    }

    public static void main(String[] args) {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */
        System.out.println("Enter the number of test cases:");
    	Scanner sc = new Scanner(System.in);
        int numberOfTests = sc.nextInt();
        
        for(int i = 0; i < numberOfTests; i++){
        	System.out.println("Enter ther number of cycles in test case "+(i+1)+": ");
            int currentInput = sc.nextInt();
            
            int result = calculateFinalHeight(currentInput);
            System.out.println("The height of Utopian tree after "+numberOfTests+" cycles = "+result);
        }
        System.out.println("Thanks! :)");
        sc.close();
    }
}
