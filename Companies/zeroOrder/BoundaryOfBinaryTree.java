package zeroOrder;

public class BoundaryOfBinaryTree {
    public static void boundaryOfBinaryTree(TreeNode root) {
        if (root == null)
            return;

        System.out.print(root.val + " ");
        getLeftBoundary(root.left);
        getLeaves(root.left);
        getLeaves(root.right);
        getRightBoundary(root.right);
    }

    private static void getRightBoundary(TreeNode root){
        if (root == null)
            return;
        if (root.left == null && root.right == null) // skip printing leaves at this step
            return;

        if (root.right != null) {
            getRightBoundary(root.right);
        }
        else {
            getRightBoundary(root.left);
        }
        System.out.print(root.val + " ");
    }

    private static void getLeftBoundary(TreeNode root){
        if (root == null)
            return;
        if (root.left == null && root.right == null) // skip printing leaves at this step
            return;

        if (root.left != null) {
            getLeftBoundary(root.left);
        }
        else {
            getLeftBoundary(root.right);
        }
        System.out.print(root.val + " ");
    }

    private static void getLeaves(TreeNode root){
        if (root == null)
            return;
        if (root.left == null && root.right == null) {
            System.out.print(root.val + " ");
        }

        getLeaves(root.left);
        getLeaves(root.right);
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        root.right.left = new TreeNode(6);
        root.left.right.left = new TreeNode(7);
        root.left.right.right = new TreeNode(8);
        root.right.left.left = new TreeNode(9);
        root.right.left.right = new TreeNode(10);

        boundaryOfBinaryTree(root);

    }
}
