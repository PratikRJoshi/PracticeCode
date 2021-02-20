package zeroOrder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

public class SameKeyCardAlert {
    public static List<String> alertNames(String[] keyName, String[] keyTime) {
        Map<String, TreeSet<Integer>> map = new HashMap<>();
        for (int i = 0; i < keyName.length; i++){
            String[] hourMinute = keyTime[i].split(":");
            int time = Integer.parseInt(hourMinute[0]) * 60 + Integer.parseInt(hourMinute[1]);
            map.computeIfAbsent(keyName[i], f -> new TreeSet<>()).add(time);
        }

        TreeSet<String> set = new TreeSet<>();
        for (Map.Entry<String, TreeSet<Integer>> entry : map.entrySet()){
            List<Integer> list = new ArrayList<>(entry.getValue());
            int low = 0, high = 1;
            while (high < list.size()) {
                if (low < high && list.get(high) - list.get(low) > 60) {
                    low++;
                }
                if (high - low >= 2 && list.get(high) - list.get(low) <= 60) {
                    set.add(entry.getKey());
                    break;
                }
                high++;
            }
        }

        return new ArrayList<>(set);
    }

    public static void main(String[] args) {
//        String[] keyName = {"daniel","daniel","daniel","luis","luis","luis","luis"};
//        String[] keyName = {"alice","alice","alice","bob","bob","bob","bob"};
//        String[] keyName = {"john","john","john"};
//        String[] keyName = {"leslie","leslie","leslie","clare","clare","clare","clare"};
        String[] keyName = {"a","a","a","a","a","b","b","b","b","b","b"};
//        String[] keyTime = {"10:00","10:40","11:00","09:00","11:00","13:00","15:00"};
//        String[] keyTime = {"12:01","12:00","18:00","21:00","21:20","21:30","23:00"};
//        String[] keyTime = {"23:58","23:59","00:01"};
//        String[] keyTime = {"13:00","13:20","14:00","18:00","18:51","19:30","19:49"};
        String[] keyTime = {"23:20","11:09","23:30","23:02","15:28","22:57","23:40","03:43","21:55","20:38","00:19"};
        List<String> alertNames = alertNames(keyName, keyTime);
        alertNames.forEach(System.out::println);
    }
}
