package zeroOrder;

public class ConstructBTFromPreorderInorder {
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        if(preorder.length != inorder.length)
            return null;

        return constructBinaryTree(preorder, 0, preorder.length - 1, inorder, 0, inorder.length - 1);
    }

    private TreeNode constructBinaryTree(int[] preorder, int preStart, int preEnd, int[] inorder, int inStart, int inEnd) {
        if(preStart > preEnd || inStart > inEnd)
            return null;

        TreeNode root = new TreeNode(preorder[preStart]);
        int rootIndexInInorder = 0;
        for(int i = 0; i < inorder.length; i++){
            if(root.val == inorder[i]){
                rootIndexInInorder = i;
                break;
            }
        }

        int inorderLength = rootIndexInInorder - inStart;
        root.left = constructBinaryTree(preorder, preStart + 1, preStart + inorderLength,
                inorder, inStart, rootIndexInInorder - 1);
        root.right = constructBinaryTree(preorder, preStart + inorderLength + 1, preEnd,
                inorder, rootIndexInInorder + 1, inEnd);

        return root;
    }
}
