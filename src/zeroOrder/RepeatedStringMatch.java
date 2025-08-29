package zeroOrder;

public class RepeatedStringMatch {
    public int repeatedStringMatch(String A, String B) {
        int count = 0;
        StringBuilder sb = new StringBuilder();
        while(sb.length() < B.length()) {
            sb.append(A);
            count++;
        }

        if(sb.toString().contains(B))
            return count;
        if(sb.append(A).toString().contains(B))
            return ++count;

        return -1;
    }

    public static void main(String[] args) {
        String A = "abcd",B = "cdabcdab";
        RepeatedStringMatch repeatedStringMatch = new RepeatedStringMatch();
        int count = repeatedStringMatch.repeatedStringMatch(A, B);
        System.out.println(count);
    }
}
