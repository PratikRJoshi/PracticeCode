class Node {
	Node node;
	Node left, right;
	Character value;
	
	public Node(){
		
	}
	public Node(Character val){
		this.value = val;
	}
}
public class Ternarytree {
	
	public static void printTree(Node root){
		if(root == null)
			return;
		
		printTree(root.left);
		System.out.println(root.value);
		printTree(root.right);
	}
	
	public static Node createTree(String str, int start, int end,  int qCount, int cCount){

		if(start > end)
			return null;
		if(start == end)
			return new Node(str.charAt(start));

		int ptr = start + 1;
		
		while(ptr < end){
			if(str.charAt(ptr) == '?')
				qCount++;
			if(str.charAt(ptr) == ':')
				cCount++;
			if(qCount == cCount)
				break;
			ptr++;
		}
		Node rootNode = new Node(str.charAt(start));
		
		Node left =  createTree(str, start + 2, ptr - 1,  0, 0);
		Node right = createTree(str, ptr + 1, end, 0, 0);
		
		
		rootNode.left = left;
		rootNode.right = right;
		return rootNode;
	}
	
	
	public static void main(String args[]){
		String input = "a?b?c?d:e:f:g?h:i?j:k";
		int qCount = 0, cCount = 0;
		Node node = Ternarytree.createTree(input, 0, input.length() - 1,  qCount, cCount);
		printTree(node);
	}
}
