package roblox;

public class CommonPrefix {
    public static void main(String[] args) {

    }

    public int commonPrefix(String input) {
        int n = input.length();
        if (n == 0) {
            return 0;
        }

        int res = n;
        char[] arr = input.toCharArray();
        for (int i = 1; i < n; i++) {
            if (arr[i] == arr[0]) {
                res += getPrefixLength(arr, i);
            }
        }

        return res;
    }

    public int getPrefixLength(char[] arr, int start) {
        int res = 0, i = 0, j = start;

        while (j < arr.length) {
            if (arr[i++] == arr[j++]) {
                res++;
            } else {
                break;
            }
        }
        return res;
    }
}
