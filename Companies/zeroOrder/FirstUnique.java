package zeroOrder;

public class FirstUnique {
    public static int firstUniqChar(String s) {
        int[] freq = new int[26];

        for(int i = 0; i < s.length(); i++){
            char c = s.charAt(i);
            freq[c - 'a']++;
        }

        int min = Integer.MAX_VALUE;
        for(int i = 0; i < freq.length; i++){
            if(freq[i] == 1){
                min = i;
                break;
            }
        }

        return min == Integer.MAX_VALUE ? -1 : min;
    }

    public static void main(String[] args) {
        String s = "leetcode";
        System.out.println(firstUniqChar(s));
    }
}
