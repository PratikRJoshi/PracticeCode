package zeroOrder;

public class InorderConstantSpace {

    public static void main(String[] args) {
        TreeNode root;
        root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);

        inorderConstantSpace(root);

    }

    private static void inorderConstantSpace(TreeNode root) {
        if (root == null) {
            return;
        }

        TreeNode current = root, prev;
        while (current != null) {
            if (current.left == null){
                System.out.print(current.val + "\t");
                current = current.right;
            } else {
                prev = current.left;
                while (prev.right != null && prev.right != current)
                    prev = prev.right;

                if (prev.right == null) {
                    prev.right = current;
                    current = current.left;
                } else {
                    prev.right = null;
                    System.out.print(current.val + "\t");
                    current = current.right;
                }
            }
        }
    }
}