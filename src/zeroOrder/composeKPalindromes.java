package zeroOrder;

public class composeKPalindromes {
    static boolean composeKPalindromes(String s, int k) {

        int[] frequency = new int[26];

        for (char c : s.toCharArray()){
            frequency[c - 'a']++;
        }

        int evens = 0, odds = 0;

        for (int i : frequency){
            if(i != 0) {
                if (i % 2 == 0)
                    evens++;
                else
                    odds++;
            }
        }

        System.out.println(evens);
        System.out.println(odds);

        return false;
    }

    public static void main(String[] args) {
        String s = "abracadabra";
        int k = 2;

        boolean b = composeKPalindromes(s, k);
        System.out.println(b);
    }
}
