package zeroOrder;

import java.util.Collections;
import java.util.PriorityQueue;

public class LC {

    public static int maxProfit(int[] inventory, int orders) {
//        double MOD =   (Math.pow(10, 9) + 7);
        double MOD =  100000007;
        PriorityQueue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder());

        for (int i : inventory){
            pq.offer((int)(i % MOD));
        }

        double total = 0;
        orders = (int)(orders % MOD);
        while (!pq.isEmpty() && orders > 0){
            int val = (int)(pq.poll() % MOD);
            total = (total + val) % MOD;
            pq.offer(val - 1);
            orders--;
        }

        return (int)(total);
    }

    public int minDeletions(String s) {
        int[] freq = new int[26];
        for(char c : s.toCharArray()){
            freq[c - 'a']++;
        }

        int count = 0;
        PriorityQueue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder());

        for(int f : freq){
            if(f != 0){
                pq.offer(f);
            }
        }

        while(!pq.isEmpty()){
            int mostFrequent = pq.poll();

            if(pq.isEmpty())
                return count;

            if(pq.peek() == mostFrequent) {
                if(mostFrequent > 1){
                    pq.offer(mostFrequent - 1);
                }
                count++;
            }
        }

        return count;
    }

    public static void main(String[] args) {

/*
        int[] inventory = {3,5};
        int orders = 6;
        System.out.println(maxProfit(inventory, orders)); // 19
*/
/*
        int[] inventory = {2,8,4,10,6};
        int orders = 20;
        System.out.println(maxProfit(inventory, orders)); // 110
*/

/*
        int[] inventory = {2,5};
        int orders = 4;
        System.out.println(maxProfit(inventory, orders)); // 14
*/
        int[] inventory = {1000000000};
        int orders = 1000000000;
        System.out.println(maxProfit(inventory, orders)); // 21

    }
}
