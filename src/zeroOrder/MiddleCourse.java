package zeroOrder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class MiddleCourse {

    private static String findMidCourse(String[][] coursesList){
        if (coursesList == null || coursesList.length == 0)
            return "";

        // initialize graph
        Map<String, Integer> indegree = new HashMap<>();
        Map<String, List<String>> graph = new HashMap<>();

        // construct graph
        for (String[] courses : coursesList){
            for (int i = 0; i < courses.length; i++) {
                indegree.put(courses[i], indegree.getOrDefault(courses[i], 0) + i);
                if (!graph.containsKey(courses[i]))
                        graph.put(courses[i], new ArrayList<>());
                if (i < courses.length - 1)
                    graph.get(courses[i]).add(courses[i + 1]);
            }
        }

        // find all the 0 indegree nodes
        Queue<String> q = new LinkedList<>();
        for (Map.Entry<String, Integer> entry : indegree.entrySet()){
            if (entry.getValue() == 0)
                q.offer(entry.getKey());
        }

        StringBuilder sb = new StringBuilder();
        while (!q.isEmpty()){
            String course = q.poll();
            sb.append(course);
            for (String child : graph.getOrDefault(course, new ArrayList<>())){
                indegree.put(child, indegree.get(child) - 1);
                if (indegree.get(child) == 0)
                    q.offer(child);
            }
        }

        return String.valueOf(sb.charAt(sb.length() / 2));
    }

    public static void main(String[] args) {
        System.out.println("Middle course: " + findMidCourse(new String[][]{{"A", "B", "D"},
                                                                            {"A", "C", "E"},
                                                                            {"D","F"},
                                                                            {"D", "E"}})); // C
        System.out.println("Middle course: " + findMidCourse(new String[][]{{"A", "B", "D"},
                                                                            {"A", "C", "E"},
                                                                            {"D", "E"}})); // C
    }
}
