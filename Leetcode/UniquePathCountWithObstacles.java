/*
 * A robot is located at the top-left corner of a m x n grid (marked 'Start' in the diagram below).
 * The robot can only move either down or right at any point in time. 
 * The robot is trying to reach the bottom-right corner of the grid (marked 'Finish').
 * Consider if some obstacles are added to the grids.
 * How many possible unique paths are there?
 * An obstacle and empty space is marked as 1 and 0 respectively in the grid.
 * 
 * For example,
 * There is one obstacle in the middle of a 3x3 grid as illustrated below.
 * [
 * [0,0,0],
 * [0,1,0],
 * [0,0,0]
 * ]
 * 
 * The total number of unique paths is 2.
 * Note: m and n will be at most 100.
 * 
 * */

public class UniquePathCountWithObstacles {
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
    	//base case
        if(obstacleGrid == null || obstacleGrid.length == 0 || obstacleGrid[0].length == 0)
            return 0;
        
        int rowLength = obstacleGrid.length;
        int columnLength = obstacleGrid[0].length;
        
        //an array to store the total number of unique paths at each point in the grid
        int numberOfUniquePaths[][] = new int[rowLength][columnLength];
        
        //set all the first column entries to 1 where there is no obstacle
        for(int i=0;i<rowLength;i++){
            if(obstacleGrid[i][0] == 1)
                break;
            else
              numberOfUniquePaths[i][0] = 1;  
        }

        //set all the first row entries to 1 where there is no obstacle
        for(int j=0;j<columnLength;j++){
            if(obstacleGrid[0][j] == 1)
                break;
            else
                numberOfUniquePaths[0][j] = 1;
        }
        
        //now traverse through the remaining entries in the grid and go on setting each entry
        //to the total number of unique paths till that point 
        for(int i=1;i<rowLength;i++){
            for(int j=1;j<columnLength;j++){
                if(obstacleGrid[i][j] == 1)
                    numberOfUniquePaths[i][j] = 0;
                else
                    numberOfUniquePaths[i][j] = numberOfUniquePaths[i-1][j] + numberOfUniquePaths[i][j-1];
            }
        }
        
        //the final answer is in the last cell of the grid
        return numberOfUniquePaths[rowLength-1][columnLength-1];
    }
    
    public static void main(String args[]){
    	UniquePathCountWithObstacles s = new UniquePathCountWithObstacles();
    	int input[][] = {{0, 0, 0}, {0, 1, 0}, {0, 0, 0}};
    	int result = s.uniquePathsWithObstacles(input);
    	System.out.println(result);
    }
}