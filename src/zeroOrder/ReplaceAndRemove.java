package zeroOrder;

public class ReplaceAndRemove {
    private int replaceAndRemove(int size, char[] s) {
        int aCount = 0, writeIndex= 0;
        for (int i = 0; i < size; i++){
            if (s[i] == 'a') {
                aCount++;
            }
            if (s[i] != 'b' && s[i] != 'x') {
                s[writeIndex++] = s[i];
            }
        }

        int ptr = writeIndex - 1;
        writeIndex = writeIndex + aCount - 1;
        int finalSize = writeIndex + 1;

        while (ptr >= 0) {
            if (s[ptr] == 'a') {
                s[writeIndex--] = 'd';
                s[writeIndex--] = 'd';
            } else {
                s[writeIndex--] = s[ptr];
            }
            ptr--;
        }
        return finalSize;
    }

    public static void main(String[] args) {
        char[] input = {'a', 'c', 'a', 'a', 'x', 'x', 'x', 'x'};
        int size = 4;
        ReplaceAndRemove replaceAndRemove = new ReplaceAndRemove();
        int result = replaceAndRemove.replaceAndRemove(size, input);
        System.out.println(result);
    }
}
