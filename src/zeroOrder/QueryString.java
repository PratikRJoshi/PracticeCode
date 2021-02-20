package zeroOrder;

public class QueryString {
    public boolean queryString(String S, int N) {
        for (int i = N; i >= N/2; --i) {
            String binaryString = Integer.toBinaryString(i);
            if (!S.contains(binaryString))
                return false;
        }
        return true;
    }

    public static void main(String[] args) {
        String S = "1";
        int N = 1;
        QueryString queryString = new QueryString();
        boolean b = queryString.queryString(S, N);
        System.out.println(b);
    }
}
