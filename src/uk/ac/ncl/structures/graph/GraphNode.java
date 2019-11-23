package uk.ac.ncl.structures.graph;

public class GraphNode {
	private int father, totalNeigh, receivedTokens, value;
	private boolean visited, // yes : This node has been expanded. If eligible can sent token back to his father
							 // no  : This node has not been "discovered" yet in the graph.
					tokenSent;
	
	GraphNode(int val) {
		this.father = -2;
		this.totalNeigh = 0;
		this.receivedTokens = 0;
		this.value = val;
		
		this.visited = false;
		this.tokenSent = false;
	}
	
	/**
	 * Check if node is eligible to perform echo.
	 * If yes, sent token to its father.
	 * 
	 */
	public boolean echo() {
		if (this.tokenSent) return false;
		
		if (this.receivedTokens == this.totalNeigh - 1) {
			this.sentToken();
			return true;
		} 
		return false;		
	}
	
	void incrementNeighbors() {
		totalNeigh++;
		System.out.println("[GRAPH_STRUCTURE]: Added neighbor on node(" + this.value 
				+ "). New total of " + totalNeigh + " neighbors");
	}
	
	public void receiveToken() {
		receivedTokens++;			
		System.out.println("[GRAPH_STRUCTURE]: Node (" +this.value + ") received a token. "
				+ "New total of " + this.receivedTokens + "/" + this.totalNeigh);
	}

	public void sentToken(GraphNode val) {
		System.out.println("[GRAPH_STRUCTURE]: "+ this.value + " has sent token to neighbor " + val.getValue());
		val.receiveToken();
	}

	private int getValue() {
		return this.value;
	}

	public void sentToken() {
		System.out.println("[GRAPH_STRUCTURE]: "+this.value + " has sent token to his father " + this.father);
		this.tokenSent = true;
	}
	
	public void setFather(int val) {
		if (val == -1) {
			//System.out.println("[GRAPH_STRUCTURE]: Initiator's ("+ this.value 
			//		+ ") father has been set to " + val);
		} else
			System.out.println("[GRAPH_STRUCTURE]: "+ this.value 
					+ " father is " + val);
		this.father = val;
	}
	
	public void setVisited() {
		this.visited = true;
	}
	
	public int getFather() {
		return this.father;
	}

	public boolean isVisited() {		
		return this.visited;
	}
	public boolean hasFather() {	
		if (this.father >= -1 ) return true;
		else return false;
	}

	/**
	 * Set this Node eligible to send tokens to its father.
	 */
	public void setForEcho() {
		this.visited = true;	
		this.receivedTokens = this.totalNeigh - 1;
		System.out.println("[GRAPH_STRUCTURE]: Node " + this.value + " can start sending tokens");
	}
	
	public void resetNode() {
		this.father = -2;
		this.receivedTokens = 0;
		this.visited = false;
		this.tokenSent = false;
	}
	
	@Override
	public String toString() {
		return "Node with value(" +this.value+")"+" has a total of "+ this.totalNeigh
				+" neighbors and has received "+ this.receivedTokens+ " tokens" ;
	}

	public boolean canDecide() {
			return this.receivedTokens == this.totalNeigh;
	}
}
