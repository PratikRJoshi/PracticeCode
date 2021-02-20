package zeroOrder;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

public class TaskScheduler {
    private static int leastInterval(char[] tasks, int n) {
        if (n == 0) return tasks.length;

        Map<Character, Integer> taskToCount = new HashMap<>();
        for (char c : tasks) {
            taskToCount.put(c, taskToCount.getOrDefault(c, 0) + 1);
        }

        Queue<Integer> queue = new PriorityQueue<>((i1, i2) -> i2 - i1);
        for (char c : taskToCount.keySet()) queue.offer(taskToCount.get(c));

        Map<Integer, Integer> coolDown = new HashMap<>();
        int currTime = 0;
        while (!queue.isEmpty() || !coolDown.isEmpty()) {
            if (coolDown.containsKey(currTime - n - 1)) {
                queue.offer(coolDown.remove(currTime - n - 1));
            }
            if (!queue.isEmpty()) {
                int left = queue.poll() - 1;
                if (left != 0) coolDown.put(currTime, left);
            }
            currTime++;
        }

        return currTime;
    }

    public static void main(String[] args) {
        char[] tasks = {'A', 'B', 'B', 'B', 'A', 'A'};
        int n = 2;

        int result = leastInterval(tasks, n);
        System.out.println(result);
        
    }
}
