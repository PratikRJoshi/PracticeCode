package roblox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PossiblePairs {
    public static Map<int[], List<String>> getPossiblePairs(String[][] input) {
        Map<int[], List<String>> result = new HashMap();
        Map<Integer, Set<String>> adjList = new HashMap();
        List<Integer> studentIds = new ArrayList();
        for (String[] course : input) {
            int studentId = Integer.parseInt(course[0]);
            if (!adjList.containsKey(studentId)) {
                studentIds.add(studentId);
            }
            adjList.putIfAbsent(studentId, new HashSet());
            adjList.get(studentId).add(course[1]);
        }
        for (int i = 0; i < studentIds.size(); i++) {
            int curr = studentIds.get(i);
            for (int j = i + 1; j < studentIds.size(); j++) {
                List<String> commonCourses = findCommon(studentIds.get(j), curr, adjList);
                result.put(new int[]{curr, studentIds.get(j)}, commonCourses);
            }
        }
        return result;
    }

    public static List<String> findCommon(int id1, int id2, Map<Integer, Set<String>> adjMap) {
        Set<String> student1 = adjMap.get(id1);
        Set<String> student2 = adjMap.get(id2);
        List<String> common = new ArrayList();
        for (String course : student1) {
            if (student2.contains(course)) {
                common.add(course);
            }
        }
        return common;
    }
}
