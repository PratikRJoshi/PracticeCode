package zeroOrder;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class SortCharactersByFreq {
    public String frequencySort(String s) {
        Map<Character, Integer> freqMap = new HashMap<>();

        for (char c : s.toCharArray()) {
            if (freqMap.containsKey(c)){
                freqMap.put(c, freqMap.get(c) + 1);
            } else {
                freqMap.put(c, 1);
            }
        }

        PriorityQueue<Map.Entry<Character, Integer>> priorityQueue = new PriorityQueue<>((a, b) -> b.getValue() - a.getValue());
        priorityQueue.addAll(freqMap.entrySet());

        StringBuilder sb = new StringBuilder();
        while (!priorityQueue.isEmpty()){
            Map.Entry<Character, Integer> entry = priorityQueue.poll();
            int frequency = entry.getValue();
            char c = entry.getKey();
            for (int i = 0; i < frequency; i++) {
                sb.append(c);
            }
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        String input = "eetr";
        SortCharactersByFreq sortCharactersByFreq = new SortCharactersByFreq();
        String frequencySortedString = sortCharactersByFreq.frequencySort(input);
        System.out.println(frequencySortedString);
    }
}
