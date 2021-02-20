package zeroOrder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LongestSubarrayDistinctEntries {
    private static int longestSubarrayWithDistinctEntries(List<Integer> list) {
        Map<Integer, Integer> mostRecentOccurrence = new HashMap<>();
        int result = 0, lastOccurrence = 0, nextIdx = 0;
        for (int i = 0; i < list.size(); i++) {
            if (mostRecentOccurrence.containsKey(list.get(i))){
                lastOccurrence = mostRecentOccurrence.get(list.get(i));
                nextIdx = lastOccurrence + 1;
            } else {
                lastOccurrence = i;
            }
            mostRecentOccurrence.put(list.get(i), i);
            result = Math.max(result, i - lastOccurrence);
            if (lastOccurrence > nextIdx) {
                nextIdx = lastOccurrence + 1;
            }
        }
        result = Math.max(result, list.size() - nextIdx);
        return result;
    }
}
