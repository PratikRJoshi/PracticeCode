package zeroOrder;

public class KthSymbolGrammar {
    public static int kthGrammar(int N, int K) {
        return kthGrammar(N, K, new StringBuilder("0"), new StringBuilder());
    }

    private static int kthGrammar(int n, int k, StringBuilder sb, StringBuilder sbNew) {
        if (n == 1){

            return k <= sb.length() ? Character.getNumericValue((sb.charAt(k - 1))) : -1;
        }

        for (int i = 0; i < sb.length(); i++){
            char c = sb.charAt(i);

            if (c == '0'){
                sbNew.append("01");
            } else if (c == '1'){
                sbNew.append("10");
            }
        }

        sb = sbNew;
        sbNew = new StringBuilder();
        return kthGrammar(n - 1, k, sb, sbNew);

    }

    public static void main(String[] args) {
        int N = 30;
        int K = 434991989;

        System.out.println(kthGrammar(N, K));
    }
}
