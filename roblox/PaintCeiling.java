package roblox;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PaintCeiling {
    public static int findPossibleFreeSizesCount(int size0, int n, int mult, int offset, int modVal, int maxArea) {
        // Step 1. Create list of wall length
        List<Integer> walllengthlist = new ArrayList<>();
        walllengthlist.add(size0);

        for (int i = 1; i < n; i++) {
            int next = (mult * walllengthlist.get(i - 1) + offset) % modVal + 1 + walllengthlist.get(i - 1);
            walllengthlist.add(next);
        }
        Collections.sort(walllengthlist);

        // Step 2. find out all possible free
        int result = 0;
        int left = 0, right = walllengthlist.size() - 1;

        while (left < right && right < walllengthlist.size()) {
            if (walllengthlist.get(left) * walllengthlist.get(right) > maxArea) {
                right--;
            } else { // less or equal to maxArea
                // (i, i), (i, j), (j, i), (i, j-1), (j-1, i) are all OK
                result += 1 + (right - left) * 2;
                left++;
            }
        }

        return result;
    }
}
