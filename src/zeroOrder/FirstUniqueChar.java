package zeroOrder;

public class FirstUniqueChar {
    public int firstUniqChar(String s) {
        if(s == null || s.length() == 0)
            return -1;

        int[] frequency = new int[26];

        for(int i = 0; i < s.length(); i++) {
            frequency[s.charAt(i) - 'a']++;
        }

        int min = Integer.MAX_VALUE;
        for(int i = 0; i < s.length(); i++){
            if(frequency[s.charAt(i) - 'a'] == 1){
                min = i;
                break;
            }
        }

        return (min == Integer.MAX_VALUE ? -1 : min);
    }

    public static void main(String[] args) {
        String input = "loveleetcode";
        FirstUniqueChar firstUniqueChar = new FirstUniqueChar();
        int firstUniqChar = firstUniqueChar.firstUniqChar(input);
        System.out.println(firstUniqChar);
    }
}
