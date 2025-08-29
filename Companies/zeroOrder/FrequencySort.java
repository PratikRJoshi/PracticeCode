package zeroOrder;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class FrequencySort {
    public static  String frequencySort(String s) {
        if(s == null || s.length() == 0)
            return null;

        Map<Character, Integer> map = new HashMap<>();
        PriorityQueue<Map.Entry<Character, Integer>> pq = new PriorityQueue<>((e1, e2) -> (e2.getValue() -  e1.getValue()));

        for(char c : s.toCharArray()){
            map.put(c, map.getOrDefault(c, 0) + 1);
        }

        for(Map.Entry<Character, Integer> entry : map.entrySet()){
            pq.offer(entry);
        }

        StringBuilder sb = new StringBuilder();
        while(!pq.isEmpty()){
            for(int i = 0; i < pq.peek().getValue(); i++){
                sb.append(pq.peek().getKey());
            }
            pq.poll();
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        String s = "tree";
        System.out.println(frequencySort(s));
    }
}
