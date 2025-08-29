package zeroOrder;

import java.util.LinkedList;
import java.util.Queue;

public class Test {
    public static boolean isEvenOddTree(TreeNode root) {
        if(root == null)
            return false;

        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);
        boolean even = true;


        while(!q.isEmpty()){
            int size = q.size();
            int prev = -1;
            for(int i = 0; i < size; i++){
                TreeNode node = q.poll();
                if(even){
                    if (i == 0){
                        if(node.val % 2 == 0){
                            return false;
                        }
                    } else if(node.val % 2 == 0 || node.val <= prev){
                        return false;
                    }
                } else if(!even){
                    if (i == 0){
                        if(node.val % 2 != 0){
                            return false;
                        }
                    } else if(node.val % 2 != 0 || node.val >= prev){
                        return false;
                    }
                }
                if(node.left != null){
                    q.offer(node.left);
                }
                if(node.right != null){
                    q.offer(node.right);
                }
                prev = node.val;
            }
            even = !even;
        }

        return true;
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(10);
        root.right = new TreeNode(4);
        root.left.left = new TreeNode(3);
        root.right.left = new TreeNode(7);
        root.right.right = new TreeNode(9);
        root.left.left.left = new TreeNode(12);
        root.left.left.right = new TreeNode(8);
        root.right.left.left = new TreeNode(6);
        root.right.right.right = new TreeNode(2);

/*        zeroOrder.TreeNode root = new zeroOrder.TreeNode(5);
        root.left = new zeroOrder.TreeNode(4);
        root.right = new zeroOrder.TreeNode(2);
        root.left.left = new zeroOrder.TreeNode(3);
        root.left.right = new zeroOrder.TreeNode(3);
        root.right.left = new zeroOrder.TreeNode(7);*/

        System.out.println(isEvenOddTree(root));
    }
}
