public class PlusOne {
    public int[] plusOne(int[] digits) {
        int carry=0;
        digits[digits.length-1]+=1;
        for(int i=digits.length-1;i>=0;i--){
            if(digits[i]+carry>=10){
                digits[i]=digits[i]+carry-10;
                carry=1;
            }
            else{
                digits[i]+=carry;
            }
        }
        //if carry remains 1 at the end of the loop, then it means we need to store answer in a new array of 1 greater size
        if(carry==1){
            int newResultArray[] = new int[digits.length+1];
            for(int i=digits.length-1;i>=0;i--){
                newResultArray[i] = digits[i];
            }
            newResultArray[0] = carry;
            return newResultArray;
        }
        return digits;
    }
    
    public static void main(String args[]){
    	PlusOne pO = new PlusOne();
    	int digits[] = {8, 9,9,9};
    	int output[] = pO.plusOne(digits);
    	System.out.println("Done");
    }
}