package zeroOrder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.TreeMap;

public class StringCharCount {
    /**
     * Iterate through each line of input.
     */
    public static void main(String[] args) throws IOException {
        InputStreamReader reader = new InputStreamReader(System.in, StandardCharsets.UTF_8);
        BufferedReader in = new BufferedReader(reader);
        String line;
        while ((line = in.readLine()) != null) {
            System.out.println(line);
            String result = getSortedStringCount(line);
            System.out.println(result);
        }
    }

    private static String getSortedStringCount(String input){
        input = input.toLowerCase();
        Map<Character, Integer> freq = new TreeMap<>();

        for (char c : input.toCharArray()) {
            freq.put(c, freq.getOrDefault(c, 0) + 1);
        }

        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Character, Integer> entry : freq.entrySet()) {
            sb.append(entry.getKey()).append(entry.getValue());
        }

        return sb.toString();
    }
}