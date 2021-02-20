package zeroOrder;

public class KthSmallestBST {
    static int count = 0;
    static int result = Integer.MIN_VALUE;

    public static int kthSmallest(TreeNode root, int k) {
        if(root == null || k == 0)
            return -1;

        findKthSmallest(root, k);

        return result;
    }

    private static void findKthSmallest(TreeNode node, int k){
        if(node == null)
            return;

        findKthSmallest(node.left, k);
        count ++;
        if(count == k)
            result = node.val;

        findKthSmallest(node.right, k);
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(1);
        root.right = new TreeNode(4);
        root.left.right = new TreeNode(2);

        int k = 1;

        System.out.println(kthSmallest(root, k));
    }
}
