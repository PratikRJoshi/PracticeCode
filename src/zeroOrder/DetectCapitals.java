package zeroOrder;

public class DetectCapitals {
    public static boolean detectCapitalUse(String word) {
        if(word == null || word.length() == 0)
            return false;

        if(word.toUpperCase().equals(word))
            return true;
        else if(word.toLowerCase().equals(word))
            return true;
        else {
            boolean isFirstOnlyUpper = Character.isUpperCase(word.charAt(0));

            for(int i = 1; i < word.length(); i++){
                if(Character.isUpperCase(word.charAt(i)))
                    return false;
            }

            return isFirstOnlyUpper;
        }
    }

    public static void main(String[] args) {
        String word = "uSa";
        System.out.println(detectCapitalUse(word));
    }
}
