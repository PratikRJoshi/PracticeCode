package zeroOrder;

public class BackspaceStringCompare {
    public boolean backspaceCompare(String S, String T) {
        StringBuilder sb1 = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();

        int index = 0;
        while (index < S.length()) {
            if (S.charAt(index) != '#') {
                sb1.append(S.charAt(index));
            } else if (S.charAt(index) == '#' && sb1.length() > 0) {
                sb1.deleteCharAt(sb1.length() - 1);
            }
            index++;
        }

        index = 0;
        while (index < T.length()) {
            if (T.charAt(index) != '#') {
                sb2.append(T.charAt(index));
            } else if (T.charAt(index) == '#' && sb2.length() > 0) {
                sb2.deleteCharAt(sb2.length() - 1);
            }
            index++;
        }

        return sb1.toString().equals(sb2.toString());
    }

    public static void main(String[] args) {
        String S = "a#c";
        String T = "b";
        BackspaceStringCompare backspaceStringCompare = new BackspaceStringCompare();
        boolean backspaceCompare = backspaceStringCompare.backspaceCompare(S, T);
        System.out.println(backspaceCompare);
    }
}
