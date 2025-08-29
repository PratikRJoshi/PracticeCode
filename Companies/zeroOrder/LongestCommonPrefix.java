package zeroOrder;

public class LongestCommonPrefix {
    public String longestCommonPrefix(String[] strs) {
        if(strs == null || strs.length == 0)
            return null;

        String shortest = strs[0];
        for(String s : strs) {
            if(s.length() < shortest.length())
                shortest = s;
        }

        for(int i = 0; i < strs.length; i++){
            if(!strs[i].startsWith(shortest)) {
                shortest = shortest.substring(0, shortest.length() - 1);
                i = -1;
            }
        }

        return shortest;
    }

    public static void main(String[] args) {
        String[] strings = {"dog","racecar","car"};
        LongestCommonPrefix longestCommonPrefix = new LongestCommonPrefix();
        String commonPrefix = longestCommonPrefix.longestCommonPrefix(strings);
        System.out.println(commonPrefix);
    }
}
