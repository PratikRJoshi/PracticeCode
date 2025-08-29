package roblox;

import java.util.ArrayList;
import java.util.List;

public class AllPeaks {
    private static List<Integer> findAllPeaks(int[] arr){
        int n = arr.length;
        ArrayList<Integer> peaks = new ArrayList<Integer>();
        if(arr[0]>arr[1])
            peaks.add(0);
        for(int i=1;i<n-1;i++) {
            if(arr[i]>arr[i-1] && arr[i]>arr[i+1])
                peaks.add(i);
        }
        if(arr[n-1]>arr[n-2])
            peaks.add(n-1);

        return peaks;
    }
}
