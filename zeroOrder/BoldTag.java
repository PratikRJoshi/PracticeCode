package zeroOrder;

public class BoldTag {

    public static String addBoldTag(String s, String[] dict) {

        if (s.length() == 0 || dict.length == 0) {
            return s;
        }

        boolean[] boldTable = new boolean[s.length()];
        int end = 0;

        for (int i = 0; i < s.length(); i++) {
            for (String word : dict) {
                int wordLength = word.length();

                if (s.startsWith(word, i)) {
                    end = Math.max(end, i + wordLength);
                }
            }
            boldTable[i] = end > i;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if (!boldTable[i]) {
                sb.append(s.charAt(i));
            } else {
                int j = i;
                while (j < s.length() && boldTable[j]) {
                    j++;
                }
                sb.append("<b>").append(s.substring(i, j)).append("</b>");
                i = j - 1;
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        String input = "aaabbcc";
        String[] dict = {"aaa", "abb", "bc", "abc"}; //<b>aaabbc</b>c
        System.out.println(addBoldTag(input, dict));
    }
}
