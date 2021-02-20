package zeroOrder;

public class ConstructBSTFromPreorder {
    static int index = 0;
    private static TreeNode bstFromPreorder(int[] preorder) {
        return bstFromPreorder(preorder, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    private static TreeNode bstFromPreorder(int[] preorder, int lowerBound, int upperBound) {
        if (index  == preorder.length || preorder[index] < lowerBound || preorder[index] > upperBound){
            return null;
        }

        TreeNode root = new TreeNode(preorder[index++]);
        root.left = bstFromPreorder(preorder, index, root.val);
        root.right = bstFromPreorder(preorder, index, upperBound);

        return root;
    }

    public static void main(String[] args) {
        int[] preorder = new int[]{8,5,7,6,12};
        TreeNode rootNode = bstFromPreorder(preorder);

        printPreOrderTree(rootNode);
    }

    static void printPreOrderTree(TreeNode root) {
        if (root ==  null)
            return;

        System.out.print(root.val + " ");
        printPreOrderTree(root.left);
        printPreOrderTree(root.right);
    }
}
