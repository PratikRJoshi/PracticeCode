package zeroOrder;

public class FindPermutationSecret {
    public int[] findPermutation(String s) {
        s = s + ".";
        int[] result = new int[s.length()];
        int current = 1, index = 0;

        while(index < result.length){
            if(s.charAt(index) != 'D'){
                result[index++] = current++;
            } else {
                int j = index;
                while(s.charAt(j) == 'D'){
                    j++;
                }

                for(int k = j; k >= index; k--){
                    result[k] = current++;
                }
                index = j + 1;
            }
        }

        return result;
    }

    public static void main(String[] args) {
        String s = "DIIDI";
        FindPermutationSecret findPermutationSecret = new FindPermutationSecret();
        int[] permutation = findPermutationSecret.findPermutation(s);
        for (int i : permutation) {
            System.out.print(i + " ");
        }
    }
}
