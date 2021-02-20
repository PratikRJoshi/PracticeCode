package roblox;

import java.util.Collections;
import java.util.List;

public class CompetitiveGaming {
    public static int levelUp(int k, List<Integer> score) {
        if (k <= 0) {
            return 0;
        }
        Collections.sort(score, Collections.reverseOrder());

        int rank = 0, res = 0;
        for (int i = 0; i < score.size(); i++) {
            if (i == 0) {
                rank = 1;
            } else if (!score.get(i).equals(score.get(i - 1))) {
                rank = i + 1;
            }
            if (rank <= k && score.get(i) > 0) {
                res++;
            } else {
                break;
            }
        }
        return res;
    }
}
