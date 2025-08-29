package zeroOrder;

import java.util.ArrayList;
import java.util.List;

public class BestTeamWithNoConflicts {
    public static int bestTeamScore(int[] scores, int[] ages) {
        List<Player> list = new ArrayList<>();
        for (int i = 0; i < scores.length; i++){
            list.add(new Player(i, scores[i], ages[i]));
        }

        list.sort((p1, p2) -> {
            if (p1.age == p2.age){
                return p1.index - p2.index;
            } else {
                return p1.age - p2.age;
            }
        });

        int[] sdp = new int[scores.length];

        for (int i = 0; i < sdp.length; i++) {
            sdp[i] = list.get(i).score;
        }
        int sum = sdp[0];

        for(int i = 1; i < sdp.length; i++){
            for(int j = 0; j < i; j++){
                if(list.get(i).score >= list.get(j).score && sdp[i] <= sdp[j] + list.get(i).score){
                    sdp[i] = sdp[j] + list.get(i).score;
                }
            }
            sum = Math.max(sum, sdp[i]);
        }
        return sum;
    }

    public static void main(String[] args) {
//        int[] scores = {4,5,6,5};
//        int[] ages = {2,1,2,1};
//        int[] scores = {1,3,5,10,15};
//        int[] ages = {1,2,3,4,5};
//        int[] scores = {1,2,3,5};
//        int[] ages = {8,9,10,1};
        int[] scores = {319776,611683,835240,602298,430007,574,142444,858606,734364,896074};
        int[] ages = {1,1,1,1,1,1,1,1,1,1};

        System.out.println(bestTeamScore(scores, ages));
    }
}

class Player {
    public int index, score, age;

    public Player(int i , int s, int a){
        this.index = i;
        this.score = s;
        this.age = a;
    }
}