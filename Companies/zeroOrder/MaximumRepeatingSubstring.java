package zeroOrder;

import java.util.LinkedList;

public class MaximumRepeatingSubstring {
    public static int maxRepeating(String sequence, String word) {
        if (sequence == null || sequence.length() == 0 || word.length() > sequence.length())
            return 0;

        int repeats = 0;
        StringBuilder sb = new StringBuilder(word);
        while (sequence.contains(sb.toString())){
            repeats++;
            sb.append(word);
        }

        return repeats;
    }

    public static void main(String[] args) {

        String sequence = "ababc", word = "ab";
        System.out.println(maxRepeating(sequence, word)); // 2

        sequence = "ababc"; word = "ba";
        System.out.println(maxRepeating(sequence, word)); // 1

        sequence = "ababc"; word = "ac";
        System.out.println(maxRepeating(sequence, word)); // 0

    }

    private static void printList(LinkedList<Integer> queue) {
        for (int i : queue){
            System.out.print(i + "\t");
        }
        System.out.println();
    }
}
