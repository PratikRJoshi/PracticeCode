package zeroOrder;

public class ReverseWordOrder {
    public String reverseWords(String s) {
        String[] individualWords = s.split("\\s+");

        StringBuilder sb = new StringBuilder();
        for (String word : individualWords) {
            sb.append(reverseGivenWord(word)).append(" ");
        }


        return sb.toString();
    }

    private String reverseGivenWord(String word) {
        StringBuilder sb = new StringBuilder();
        for (char c: word.toCharArray()){
            sb.insert(0, c);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        String input = "Let's take LeetCode contest";
        ReverseWordOrder reverseWordOrder = new ReverseWordOrder();
        String reversedWords = reverseWordOrder.reverseWords(input);
        System.out.println(reversedWords);
    }
}
