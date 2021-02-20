package zeroOrder;

import java.util.Arrays;

public class ReorderLogFiles {
    public static String[] reorderLogFiles(String[] logs) {
        Arrays.sort(logs, (l1, l2) -> {
            String[] s1 = l1.split("\\s+", 2);
            String[] s2 = l2.split("\\s+", 2);

            boolean isDigit1 = Character.isDigit(s1[1].charAt(0));
            boolean isDigit2 = Character.isDigit(s2[1].charAt(0));

            if(!isDigit1 && !isDigit2){ // both are letter logs
                int compare = s1[1].compareTo(s2[1]);
                if(compare == 0){
                    return s1[0].compareTo(s2[0]);
                }
                return compare;
            } else if(isDigit1 && isDigit2){ // none is letter log
                return 0;
            } else if(!isDigit1 && isDigit2){ // first is letter log
                return -1;
            } else {
                return 1;
            }
        });

        return logs;
    }

    public static void main(String[] args) {
        String[] logs = {"a1 9 2 3 1","g1 act car","zo4 4 7","ab1 off key dog","a8 act zoo"};
//        String[] logs = {"dig1 8 1 5 1","let1 art can","dig2 3 6","let2 own kit dig","let3 art zero"};
        String[] logFiles = reorderLogFiles(logs);
        Arrays.stream(logFiles).forEach(System.out::print);
    }
}
