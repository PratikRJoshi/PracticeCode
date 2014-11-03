/* Write a function 
 * 
 * isBST(BinaryTree *node) 
 * 
 * to verify if a given binary tree is a Binary Search Tree (BST) or not.*/
public class IsBtBst {
	int previousValue = Integer.MIN_VALUE;
	public boolean isBTaBST(TreeNode root){
		return btBSTHelper(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
//		return btIsBST(root, previousValue);
	}
	
	public boolean btBSTHelper(TreeNode root, int min, int max){
		if(root == null)
			return true;
		
		//checks if the root value lies between the current max and min values
		if(min < root.nodeValue && root.nodeValue < max){
			return (btBSTHelper(root.left, min, root.nodeValue) && btBSTHelper(root.right, root.nodeValue, max));
		}
		else
			return false;
	}
	
	//does an inorder traversal
	//if the left subtree traversal returns true, then store the last previous value
	//and pass it to the right subtree check
	public boolean btIsBST(TreeNode root, int nodeValue){
		if(root == null)
			return true;
		else if(btIsBST(root.left, previousValue)){
				if(root.nodeValue < previousValue){
					previousValue = root.nodeValue;
					return btIsBST(root.right, previousValue);
				}
				else
					return false;
			}
		else
			return false;
	}
}

class TreeNode {
	public TreeNode left;
	public TreeNode right;
	public int nodeValue;
	
	TreeNode(int val){
		this.nodeValue = val;
	}
}
