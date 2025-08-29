package zeroOrder;

public class StraightLine {
    public static boolean checkStraightLine(int[][] coordinates) {
        double originalSlope = 0;
        originalSlope = (double)(coordinates[1][1] - coordinates[0][1]) / (coordinates[1][0] - coordinates[0][0]);
        for(int i = 2; i < coordinates.length; i++){
            double currentSlope = (double)(coordinates[i][1] - coordinates[i-1][1]) / (coordinates[i][0] - coordinates[i-1][0]);
            if(currentSlope != originalSlope){
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args) {
        int[][] coordinates = new int[][]{{-4,-3},{1,0},{3,-1},{0,-1},{-5,2}};
        System.out.println(checkStraightLine(coordinates));
    }
}
