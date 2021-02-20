package zeroOrder;

import java.util.ArrayList;
import java.util.List;

// SMS Split
// Input: char_limit = 20, message = "Hey Chengyu, your Uber is arriving now!"
// Output: ["Hey Chengyu, (1/3)", "your Uber is (2/3)", "arriving now! (3/3)"]

// Requirements:
// 1) Do not split a word across messages
// 2) Metadata (ie. ` (1/3)`) counts against the character limit. For example "Hey Chengyu, (1/3)" contains 18 characters. https://www.pramp.com/session/join/arGoyz7E8LslK3EQzXZ6

// lets say the input has 100 words
// metadata length -  (100/100) - length = 9 5, 7
public class SplitSMS {

    private static List<String> getSplitMessages(String s, int char_limit){
        List<String> result = new ArrayList<>();
        if(s == null || s.length() == 0){
            return result;
        }

        String[] splits = s.split("\\s+");
        int modifiedCharLimit = char_limit - 5; // 5 forthe metadata length;

        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < splits.length; i++){ //  arriving now! i = 5, len = 7, mcl = 15
            if(modifiedCharLimit > 0 && splits[i].length() < modifiedCharLimit){
                sb.append(splits[i]).append(" "); // sb = arriving (9)
                modifiedCharLimit -= (splits[i].length() + 1); // 6
            } else {
                result.add(sb.toString());
                sb = new StringBuilder();
                modifiedCharLimit = char_limit - 5;
                i = i - 1;
            }
        }

        if(sb.length() > 0){
            result.add(sb.toString());
        }

        // 2nd pass:

        return result;

    }

    public static void main(String[] args) {
        String input = "Hey Chengyu, your Uber is arriving now!";
        int char_limit = 20;
        List<String> result = getSplitMessages(input, char_limit);
        for(String s: result){
            System.out.println(s);
        }
    }
}
