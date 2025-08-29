package zeroOrder;

import java.util.*;

public class MRU {

    private static List<String>  getMostRecentlyUsed(List<String> urls) {
        LinkedHashSet<String> linkedHashSet = new LinkedHashSet<>();
        for (String u  :  urls) {
            linkedHashSet.add(u);
        }

        LinkedHashMap<String, String> linkedHashMap = new LinkedHashMap<>();
        for (String  u : urls) {
            if (linkedHashMap.containsKey(u)) {
                linkedHashMap.remove(u, u);
            }
                linkedHashMap.put(u, u);
        }

        LinkedList<String> list = new LinkedList<>();
        for (Map.Entry<String, String> entry : linkedHashMap.entrySet()) {
            list.addFirst(entry.getKey());
        }

        return list;
    }

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("https://www.bbc.com");
        list.add("https://www.cnn.com");
        list.add("https://www.abc.com");
        list.add("https://www.bbc.com");

        List<String> mostRecentlyUsed = getMostRecentlyUsed(list);
        mostRecentlyUsed.forEach(System.out::println);
    }
}
