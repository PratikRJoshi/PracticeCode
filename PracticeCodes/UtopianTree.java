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