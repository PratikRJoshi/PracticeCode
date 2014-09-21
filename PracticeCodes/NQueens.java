public class NQueens {
 
	//print the configuration of the passed board
	static void printSolution(int[][] board){
		for(int i=0;i<board.length;i++){
			for(int j=0;j<board[0].length;j++){
				System.out.print(board[i][j]==1?"|Q":"|_");
			}
			System.out.println("|");
		}
	}
	
	/*This function checks if the passed (row, column) position is */
	static boolean isThisPositionSafe(int board[][], int rowPosition, int columnPosition, int noOfQueens){
		/*The idea of safety check is to check positions only one the left side of the current column
		 * since we are placing queen from left to right at every iteration*/
		int row, column;
		
		//Check positions in this row on the left side
		for(column=0;column<columnPosition;column++){
			if(board[rowPosition][column] == 1)
				return false;
		}
		
		//Check the positions in the upper diagonal on left side
		for(row = rowPosition, column = columnPosition;row>=0 && column>=0;row--, column--){
			if(board[row][column] == 1)
				return false;
		}
		
		//Check lower diagonal positions on left side
		for(row = rowPosition, column = columnPosition;row<noOfQueens && column>=0;row++, column--){
			if(board[row][column] == 1)
				return false;
		}
		
		return true;
	}
	
	/*A recursive solution to check to solve n-queens problem*/
	static boolean solveNQueensUtilityFunction(int[][] board, int columnNumber, int noOfQueens){
		//this means that we have placed queens in all the previous columns correctly
		if(columnNumber>=noOfQueens)
			return true;
		
		//if there is still a column remaining, check in which row a queen can be placed
		for(int i=0;i<noOfQueens;i++){
			//if this position is a safe one to place a queen
			if(isThisPositionSafe(board, i, columnNumber, noOfQueens)){
				//set the queen's position to the current (row, column)
				board[i][columnNumber] = 1;
				
				//now that the current queen is correctly placed, check for the next queen from the remaining ones
				if(solveNQueensUtilityFunction(board, columnNumber+1, noOfQueens))
					return true;
				
				//if the above recursive call yields false, then we need to remove the queen from the current position
				//snice it means that due to this queen, the next ones all can't be placed correctly
				//this line thus, forms the 'backtracking' step
				board[i][columnNumber] = 0;
			}
		}
		return false;
	}
	
	static boolean solveNQueens(int numberOfQueens){
		int board[][] = new int[numberOfQueens][numberOfQueens];
		
		if(!solveNQueensUtilityFunction(board, 0, numberOfQueens)){
			System.out.println("Solution doesn't exist");
			return false;
		}
		printSolution(board);
		return true;
	}
	
	
	public static void main(String args[]){
		boolean result = solveNQueens(Integer.parseInt(args[0]));
		System.out.println("Is there a solution to the given number of queens? :"+result);
	}
}