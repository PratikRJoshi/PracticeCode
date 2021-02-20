package zeroOrder;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MostCommonWord {
    public String mostCommonWord(String paragraph, String[] banned) {
        Map<String, Integer> map = new HashMap<>();
        List<String> bannedList = Arrays.asList(banned);

        String[] paraWords = paragraph.replaceAll("\\W+", " ").toLowerCase().split("\\s+");
        for(String s : paraWords) {
            if(map.containsKey(s)){
                map.put(s, map.get(s) + 1);
            } else {
                if(!bannedList.contains(s))
                    map.put(s, 1);
            }
        }

        String mostCommonWord = "";
        int maxFreq = 0;
        for(Map.Entry<String, Integer> entry : map.entrySet()) {
            if(entry.getValue() > maxFreq) {
                maxFreq = entry.getValue();
                mostCommonWord = entry.getKey();
            }
        }

        return mostCommonWord;
    }

    public static void main(String[] args) {
        String para = "Bob hit a ball, the hit BALL flew far after it was hit.";
        String[] banned = {"hit"};
        MostCommonWord mostCommonWord = new MostCommonWord();
        String commonWord = mostCommonWord.mostCommonWord(para, banned);
        System.out.println(commonWord);
    }
}
