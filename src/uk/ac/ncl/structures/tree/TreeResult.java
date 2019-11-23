package uk.ac.ncl.structures.tree;

public class TreeResult {	
	int totalTreeNodes,root, maxLevel;
    TreeNode[] treeNodes;
	
	public TreeResult(int root, int totalTreeNodes) {
		this.maxLevel = 0;
		this.totalTreeNodes = totalTreeNodes;
		this.root = root;
		this.treeNodes = new TreeNode[totalTreeNodes];
		TreeNode rootNode = new TreeNode(root,0);
		this.treeNodes[root] = rootNode;
	}
	
	public void addChild(int father, int child) {
		TreeNode treeNode = new TreeNode(child,this.getNewLevel(father));
		this.treeNodes[child] = treeNode;
		this.treeNodes[father].addChild(child);
	}
	
	private int getNewLevel(int father) {
		int newLevel = this.treeNodes[father].getLevel() + 1;
		if (newLevel > this.maxLevel) this.maxLevel = newLevel;
		return newLevel;
	}
	
	public void printTree() {
		System.out.println("TREE REPRESENTATION [");
		printTreeByLevel(this.root);
		System.out.println("]");
	}
	
	private void printTreeByLevel(int valueNode) {
		TreeNode node = this.treeNodes[valueNode];
        System.out.println(node);
	    for (int child : node.getChildren()) {
	    	printTreeByLevel(child);
	    }
	}
}
