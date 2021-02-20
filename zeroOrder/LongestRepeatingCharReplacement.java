package zeroOrder;

public class LongestRepeatingCharReplacement {
    private int characterReplacement(String s, int k) {
        if(s == null || s.length() == 0)
            return 0;

        int[] count = new int[26];
        int start = 0, maxCount = 0, maxLength = 0;
        for (int end = 0; end < s.length(); end++) {
            char c = s.charAt(end);
            count[c - 'A']++;
            maxCount = Math.max(maxCount, count[c - 'A']);
            if (end - start + 1 - maxCount > k) {
                count[s.charAt(start) - 'A']--;
                start++;
            }
            maxLength = Math.max(maxLength, end - start + 1);
        }
        return maxLength;
    }

    public static void main(String[] args) {
        String s = "ABAA";
        int k = 0;

        LongestRepeatingCharReplacement longestRepeatingCharReplacement = new LongestRepeatingCharReplacement();
        int length = longestRepeatingCharReplacement.characterReplacement(s, k);
        System.out.println(length);
    }
}
