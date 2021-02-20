package zeroOrder;

import java.util.TreeMap;

public class SnapshotArray {
    TreeMap<Integer, Integer>[] A;
    int snap_id = 0;
    public SnapshotArray(int length) {
        A = new TreeMap[length];
        for (int i = 0; i < length; i++) {
            A[i] = new TreeMap<Integer, Integer>();
            A[i].put(0, 0);
        }
    }

    public void set(int index, int val) {
        A[index].put(snap_id, val);
    }

    public int snap() {
        return snap_id++;
    }

    public int get(int index, int snap_id) {
        return A[index].floorEntry(snap_id).getValue();
    }

    public static void main(String[] args) {
        SnapshotArray obj = new SnapshotArray(3);
        obj.set(0,1);
        obj.set(2,17);
        obj.set(0,19);

        System.out.println(obj.snap());
        System.out.println(obj.snap());

        int param_3  = obj.get(0,0);
        System.out.println(param_3);

        System.out.println(obj.snap());

        int param_4  = obj.get(1,1);
        System.out.println(param_4);
        obj.set(1,1);
        param_4  = obj.get(1,1);
        System.out.println(param_4);
        param_4  = obj.get(2,0);
        System.out.println(param_4);
    }
}
