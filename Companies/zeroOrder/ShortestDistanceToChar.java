package zeroOrder;

public class ShortestDistanceToChar {
    public int[] shortestToChar(String S, char C) {
        int[] result = new int[S.length()];

        int position = -S.length();
        // left to right pass
        for (int i = 0; i < S.length(); i++){
            if (S.charAt(i) == C) {
                position = i;
            }
            result[i] = i - position;
        }

        // right to left pass to update the distance if possible
        for (int i = S.length() - 1; i >= 0; i--) {
            if (S.charAt(i) == C){
                position = i;
            }
            result[i] = Math.min(result[i], Math.abs(i - position));
        }

        return result;
    }

    public static void main(String[] args) {
        String S = "loveleetcode";
        char c = 'e';
        ShortestDistanceToChar shortestDistanceToChar = new ShortestDistanceToChar();
        int[] shortestToChar = shortestDistanceToChar.shortestToChar(S, c);
        for (int i : shortestToChar) {
            System.out.print(i + "\t");
        }
    }
}
