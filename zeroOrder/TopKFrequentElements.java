package zeroOrder;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

class TopKFrequentElements {
    public static int[] topKFrequent(int[] nums, int k) {
        if(nums == null || nums.length == 0)
            return new int[]{};

        Map<Integer, Integer> map = new HashMap<>(); // < number, frequency >
        for(int i : nums){
            map.put(i, map.getOrDefault(i, 0) + 1);
        }

        PriorityQueue<Pair> pq = new PriorityQueue<>();
        for(Map.Entry<Integer, Integer> entry : map.entrySet()){
            pq.offer(new Pair(entry.getKey(), entry.getValue()));
            if(pq.size() > k){
                pq.poll();
            }
        }

        int i = 0;
        int[] res = new int[k];
        while(!pq.isEmpty()) {
            res[i++] = (int) pq.poll().key;
        }

        return res;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{1,1,1,2,2,3};
        int k = 2;

        int[] topKFrequent = topKFrequent(nums, k);
        Arrays.stream(topKFrequent).forEach(System.out::println);
    }
}

class Pair {
    int key, value;

    public Pair(int k, int v) {
        this.key = k;
        this.value = v;
    }
}
