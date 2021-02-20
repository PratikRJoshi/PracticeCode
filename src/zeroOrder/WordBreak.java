package zeroOrder;

import java.util.Arrays;
import java.util.List;

public class WordBreak {
    public boolean wordBreak(String s, List<String> wordDict) {
        if(s == null || s.length() == 0 || wordDict == null)
            return false;

        boolean[] wordFormed = new boolean[s.length() + 1];
        wordFormed[0] = true;

        for(int i = 1; i <= s.length(); i++){
            for(int j = i - 1; j >= 0; j--) {
                if(wordFormed[j] && wordDict.contains(s.substring(j, i))) {
                    wordFormed[i] = true;
                    break;
                }
            }
        }
        return wordFormed[s.length()];
    }

    public static void main(String[] args) {
        List<String> list = Arrays.asList("leet", "code");
        String s = "leetcode";

        WordBreak wordBreak = new WordBreak();
        boolean b = wordBreak.wordBreak(s, list);
        System.out.println(b);
    }
}
