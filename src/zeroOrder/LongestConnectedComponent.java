package zeroOrder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class LongestConnectedComponent {
    public List<String> largestItemAssociation(List<PairString> itemAssociation) {
        Map<String, List<String>> graph = new HashMap<>();

        for (PairString pairString : itemAssociation){
            graph.putIfAbsent(pairString.first, new ArrayList<>());
            graph.get(pairString.first).add(pairString.second);
            graph.putIfAbsent(pairString.second, new ArrayList<>());
            graph.get(pairString.second).add(pairString.first);
        }

        Set<String> visited = new HashSet<>();
        List<List<String>> mergedLists = new ArrayList<>();
        for (String key : graph.keySet()){
            List<String> list = new ArrayList<>();
            dfs(key, graph, list, visited);
            if (!list.isEmpty()){
                Collections.sort(list);
                mergedLists.add(list);
            }
        }

        mergedLists.sort((l1, l2) -> {
            if (l1.size() == l2.size()) {
                for (int i = 0; i < l1.size(); i++) {
                    if (l1.get(i).equals(l2.get(i))) {
                        continue;
                    }
                    return l1.get(i).compareTo(l2.get(i));
                }
            }

            return l2.size() - l1.size();
        });

        return mergedLists.get(0);
    }

    private void dfs(String key, Map<String, List<String>> graph, List<String> list, Set<String> visited) {
        if (visited.add(key)){
            list.add(key);
            for (String neighbour : graph.get(key)){
                dfs(neighbour, graph, list, visited);
            }
        }
    }

    public static void main(String[] args) {
        List<PairString> itemAssociation1 = new ArrayList(){
            {
                add(new PairString("item1", "item2"));
                add(new PairString("item3", "item4"));
                add(new PairString("item4", "item5"));
            }
        };
        List<PairString> itemAssociation2 = new ArrayList(){
            {
                add(new PairString("item1", "item2"));
                add(new PairString("item3", "item4"));
                add(new PairString("item4", "item5"));
                add(new PairString("item6", "item7"));
                add(new PairString("item6", "item8"));
            }
        };
        List<PairString> itemAssociation3 = new ArrayList(){
            {
                add(new PairString("item1", "item2"));
                add(new PairString("item4", "item5"));
                add(new PairString("item3", "item4"));
                add(new PairString("item1", "item4"));
            }
        };
        LongestConnectedComponent s = new LongestConnectedComponent();
        System.out.println(s.largestItemAssociation(itemAssociation1)); // Output: [item3, item4, item5]
        System.out.println(s.largestItemAssociation(itemAssociation2)); // Output: [item3, item4, item5], here we got same size, so have to sort lexicographical.
        System.out.println(s.largestItemAssociation(itemAssociation3)); // Output: [item1, item2, item3, item4, item5]
    }
}

class PairString {
    String first;
    String second;

    public PairString(String first, String second) {
        this.first = first;
        this.second = second;
    }
}
