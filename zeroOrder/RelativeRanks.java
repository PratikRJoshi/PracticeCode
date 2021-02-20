package zeroOrder;

import java.util.PriorityQueue;

public class RelativeRanks {
    public static String[] findRelativeRanks(int[] nums) {
        PriorityQueue<int[]> pq = new PriorityQueue<int[]>((a, b) -> (b[0] - a[0]));

        for (int i = 0; i < nums.length; i++){
            pq.offer(new int[]{nums[i], i});
        }

        String[] result = new String[nums.length];
        int index = 0, rank = 1;

        while (!pq.isEmpty()){
            index = pq.poll()[1];
            switch (rank) {
                case 1:
                    result[index] = "Gold Medal";
                    break;
                case 2:
                    result[index] = "Silver Medal";
                    break;
                case 3:
                    result[index] = "Bronze Medal";
                    break;
                default:
                    result[index] = String.valueOf(rank);

            }
            rank++;
        }

        return result;
    }

    public static void main(String[] args) {
        int[] input = {5, 4, 3, 2, 1};
        String[] relativeRanks = findRelativeRanks(input);
        for (String r : relativeRanks){
            System.out.print(r + " ");
        }
    }
}
