package zeroOrder;

import java.util.*;

public class BottomLeftTreeValue {

    public static int findBottomLeftValue(TreeNode root) {
        int result = 0;
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);

        while(!q.isEmpty()){
            int size = q.size();

            for(int i = 0; i < size; i++){
                TreeNode node = q.poll();
                result = node.val;
                if(node.right != null) {
                    q.offer(node.right);
                }
                if(node.left != null) {
                    q.offer(node.left);
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.right.left = new TreeNode(5);
        root.right.right = new TreeNode(6);
        root.right.left.left = new TreeNode(7);

        int bottomLeftValue = findBottomLeftValue(root);
        System.out.println(bottomLeftValue);
    }
}
