package zeroOrder;

public class SurroundedRegions {
    public void solve(char[][] board) {
        if(board == null || board.length == 0)
            return;

        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[i].length; j++){
                if((i == 0 || j == 0 || i == board.length - 1 || j == board[0].length - 1)
                        && board[i][j]  == 'O'){
                    dfs(board, i, j);
                }
            }
        }

        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[i].length; j++){
                if(board[i][j] == 'B'){
                    board[i][j] = 'O';
                } else if(board[i][j] == 'O'){
                    board[i][j] = 'X';
                }
            }
        }
    }

    private void dfs(char[][] board, int row, int col){
        if(row < 0 || col < 0 || row >= board.length || col >= board[0].length || board[row][col] != 'O'){
            return;
        }

        board[row][col] = 'B';
        dfs(board, row + 1, col);
        dfs(board, row - 1, col);
        dfs(board, row, col + 1);
        dfs(board, row, col - 1);
    }

    public static void main(String[] args) {
        char[][] board = {{'X','X','X','X'},{'X','O','O','X'},{'X','X','O','X'},{'X','O','X','X'}};
        SurroundedRegions surroundedRegions = new SurroundedRegions();
        surroundedRegions.solve(board);
        System.out.println();
    }
}
