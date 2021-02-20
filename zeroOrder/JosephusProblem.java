package zeroOrder;

import java.util.ArrayList;
import java.util.List;

public class JosephusProblem {
    static int result = -1;
    private static int whoLives(int N, int K){

        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < N; i++){
            list.add(i + 1);
        }

        solve(list, 1, K);
        return result;
    }

    private static void solve(List<Integer> list, int index, int k) {
        if (list.size() == 1){
            result = list.iterator().next();
            return;
        }

        index = (index + k - 1) % list.size();
        list.remove(index);
        solve(list, index, k);
    }

    public static void main(String[] args) {
        int N = 40, K = 7;
        System.out.println(whoLives(N, K));
    }

}
