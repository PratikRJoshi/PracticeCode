package zeroOrder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TimeDifference {
    public int findMinDifference(List<String> timePoints) {
        if(timePoints == null || timePoints.size() == 0)
            return 0;

        List<Integer> timePointsMins = new ArrayList<>();
        for(String t : timePoints){
            int hour = Integer.parseInt(t.split(":")[0]);
            int mins = Integer.parseInt(t.split(":")[1]);

            timePointsMins.add(((hour * 60) + mins));
        }

        Collections.sort(timePointsMins);

        int minDiff = Integer.MAX_VALUE;
        for(int i = 1; i < timePointsMins.size(); i++){
            int diff = timePointsMins.get(i) - timePointsMins.get(i - 1);
            if(diff < minDiff)
                minDiff = diff;
        }
        minDiff = Math.min(minDiff, timePointsMins.get(0) - timePointsMins.get(timePoints.size() - 1) + 1440);
        return minDiff;
    }

    public static void main(String[] args) {
        String[] time = {"23:59","00:00"};
        List<String> clockTimes = Arrays.asList(time);
        TimeDifference timeDifference = new TimeDifference();
        int minDifference = timeDifference.findMinDifference(clockTimes);
        System.out.println(minDifference);
    }
}
