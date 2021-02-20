package zeroOrder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InterviewBit {

    static public ArrayList<Integer> rotateArray(List<Integer> A, int B) {
        ArrayList<Integer> ret = new ArrayList<Integer>();
        B %= A.size();
        for (int i = 0; i < A.size(); i++) {
                ret.add(A.get((i + B) % A.size()));
        }
        return ret;
    }

    public static void main(String[] args) {
        Integer[] input = {1, 2, 3, 4, 5, 6};
//        Integer[] input = {14, 5, 14, 34, 42, 63, 17, 25, 39, 61, 97, 55, 33, 96, 62, 32, 98, 77, 35};
        List<Integer> ints = Arrays.asList(input);
        int B = 1;
//        int B = 56;
        ArrayList<Integer>  result = rotateArray(ints, B);
        for (int i = 0; i < result.size(); i++) {
                System.out.print(result.get(i) + " ");
            }
        }
    }
