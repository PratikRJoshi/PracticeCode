package zeroOrder;

public class SmallestStringWithAGivenNumericValue {

    public static String getSmallestString(int n, int k) {
        StringBuilder sb = new StringBuilder();

        String result = new String();
        result = generateString(n, k, sb, result);
        return result;
    }

    private static String generateString(int n, int k, StringBuilder sb, String result) {
       if (n == 0 && k == 0){
           result = sb.toString();
           return result;
       }



        for (char c = 'a'; c <= 'z'; c++) {
            sb.append(c);
            generateString(n - 1, k - (c - 'a'), sb, result);
        }
        return null;
    }

    public static void main(String[] args) {
        int n = 3, k = 27;
        System.out.println(getSmallestString(n, k));
    }
}
