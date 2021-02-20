package zeroOrder;

public class FindFriendCircles {
    private int findCircleNum(int[][] M) {
    /*    int n = M.length;
        UnionFind uf = new UnionFind(n);
        for(int i = 0; i < M.length - 1; i++){
            for(int j = i + 1; j < M[i].length; j++){
                if(M[i][j] == 1){
                    uf.union(i, j);
                }
            }
        }
        return uf.getCount();*/
        return 0;
    }

    public static void main(String[] args) {
        int [][]inputFriends = {{1,1,0},{1,1,0},{0,0,1}};
//        int [][]inputFriends = {{1,1,0},{1,1,1},{0,1,1}};
        FindFriendCircles findFriendCircles = new FindFriendCircles();
        int result = findFriendCircles.findCircleNum(inputFriends);
        System.out.println(result);
    }
}
