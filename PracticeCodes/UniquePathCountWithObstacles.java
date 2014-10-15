public class UniquePathCountWithObstacles {
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        if(obstacleGrid == null || obstacleGrid.length == 0 || obstacleGrid[0].length == 0)
            return 0;
        
        int rowLength = obstacleGrid.length;
        int columnLength = obstacleGrid[0].length;
        
        int numberOfUniquePaths[][] = new int[rowLength][columnLength];
        
        for(int i=0;i<rowLength;i++){
            if(obstacleGrid[i][0] == 1)
                break;
            else
              numberOfUniquePaths[i][0] = 1;  
        }
        
        for(int j=0;j<columnLength;j++){
            if(obstacleGrid[0][j] == 1)
                break;
            else
                numberOfUniquePaths[0][j] = 1;
        }
        
        for(int i=1;i<rowLength;i++){
            for(int j=1;j<columnLength;j++){
                if(obstacleGrid[i][j] == 1)
                    numberOfUniquePaths[i][j] = 0;
                else
                    numberOfUniquePaths[i][j] = numberOfUniquePaths[i-1][j] + numberOfUniquePaths[i][j-1];
            }
        }
        return numberOfUniquePaths[rowLength-1][columnLength-1];
    }
    
    public static void main(String args[]){
    	UniquePathCountWithObstacles s = new UniquePathCountWithObstacles();
    	int input[][] = {{0, 0, 0}, {0, 1, 0}, {0, 0, 0}};
    	int result = s.uniquePathsWithObstacles(input);
    	System.out.println(result);
    }
}