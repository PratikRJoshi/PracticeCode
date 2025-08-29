package zeroOrder;

public class BTreeGameWinningMove {
    int left = 0, right = 0;
    public boolean btreeGameWinningMove(TreeNode root, int n, int x) {
        count(root, x);
        return Math.max(Math.max(left, right), n - left - right - 1) > (n / 2);
    }

    private int count(TreeNode node, int x) {
        if(node == null)
            return 0;
        int l = count(node.left, x);
        int r = count(node.right, x);
        if(node.val == x) {
            left = l;
            right = r;
        }
        return l + r + 1;
    }
    

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);

        BTreeGameWinningMove bTreeGameWinningMove = new BTreeGameWinningMove();
        boolean b = bTreeGameWinningMove.btreeGameWinningMove(root, 3, 1);
        System.out.println(b);
    }
}
