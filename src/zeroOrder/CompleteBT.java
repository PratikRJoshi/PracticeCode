package zeroOrder;

import java.util.LinkedList;
import java.util.Queue;

public class CompleteBT {
    public static boolean isCompleteTree(TreeNode root) {
        if(root == null)
            return true;

        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);

        // keep adding nodes until you hit a null node at the front of the queue
        while(q.peek() != null){
            TreeNode node = q.poll();
            q.offer(node.left);
            q.offer(node.right);
        }


        // once you find a null node at the front of the queue, there should not be any more non-null elements in the queue for a tree to be complete
        while(!q.isEmpty()){
            if(q.poll() != null){
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(4);
        root.right.right = new TreeNode(6);

        System.out.println(isCompleteTree(root));
    }
}
