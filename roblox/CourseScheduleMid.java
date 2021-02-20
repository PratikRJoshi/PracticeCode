package roblox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class CourseScheduleMid {
    private static String isSchedulingPossible(String[] tasks, String[][] prerequisites) {
        List<String> result = new ArrayList<>();
        if (tasks.length == 0)
            return "";

        // initialize the graph
        Map<String, Integer> inDegree = new HashMap<>();
        Map<String, List<String>> graph = new HashMap<>();
        for (int i = 0; i < tasks.length; i++){
            inDegree.put(tasks[i], 0);
            graph.put(tasks[i], new ArrayList<>());
        }

        // build the graph
        for (String[] prerequisite : prerequisites) {
            String parent = prerequisite[0], child = prerequisite[1];
            inDegree.put(child, inDegree.getOrDefault(child, 0) + 1);
            graph.get(parent).add(child);
        }

        // find all nodes with indegree 0
        Queue<String> q = new LinkedList<>();
        for (Map.Entry<String, Integer> entry : inDegree.entrySet()){
            if (entry.getValue() == 0){
                q.offer(entry.getKey());
            }
        }

        // do topological traversal
        while (!q.isEmpty()){
            String node = q.poll();
            result.add(node);
            for (String child : graph.get(node)){
                inDegree.put(child, inDegree.get(child) - 1);
                if (inDegree.get(child) == 0){
                    q.offer(child);
                }
            }
        }

        return result.size() % 2 == 0
               ? result.get(result.size() / 2)
               : result.get(result.size() / 2 + 1);
    }
}
