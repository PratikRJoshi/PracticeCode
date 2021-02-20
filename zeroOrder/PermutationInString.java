package zeroOrder;

public class PermutationInString {
    public static boolean checkInclusion(String s1, String s2) {
        if(s1.length() > s2.length())
            return false;

        int[] freq = new int[26];
        for(int i = 0; i < s1.length(); i++){
            char c = s1.charAt(i);
            freq[c - 'a']++;
        }

        for(int i = 0; i < s2.length(); i++){
            freq[s2.charAt(i) - 'a']--;
            if(i >= s1.length()){ // if the sliding window is beyond the length of s1, that char is out of the window and it's freq needs to be increased since its no longer part of the possible permutation
                freq[s2.charAt(i - s1.length()) - 'a']++;
            }
            if(allFreqZero(freq)){
                return true;
            }
        }
        return false;
    }

    private static boolean allFreqZero(int[] freq){
        for(int i = 0; i < 26; i++){
            if(freq[i] != 0){
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args) {
        String s1 = "a";
        String s2 = "ab";

        System.out.println(checkInclusion(s1, s2));
    }
}
