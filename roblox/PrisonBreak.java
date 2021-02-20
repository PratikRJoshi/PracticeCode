package roblox;

public class PrisonBreak {
    private static int prison(int[] horizontal, int[] vertical) {
        boolean[] xs = new boolean[horizontal.length + 1];
        boolean[] ys = new boolean[vertical.length + 1];

        for (int h : horizontal) {
            xs[h] = true;
        }
        for (int v : vertical) {
            ys[v] = true;
        }

        int xMax = 0, yMax = 0;
        for (int i = 1, j = 0; i <= horizontal.length; i++) {
            if (!xs[i]) {
                j = 0;
            } else {
                xMax = Math.max(xMax, ++j);
            }
        }
        for (int i = 1, j = 0; i <= vertical.length; i++) {
            if (!ys[i]) {
                j = 0;
            } else {
                yMax = Math.max(yMax, ++j);
            }
        }

        return (xMax + 1) * (yMax + 1);
    }

    public static void main(String[] args) {

    }
}
