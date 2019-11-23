/**
 * 
 */
package uk.ac.ncl.structures.graph;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author b9058710
 *
 */

public class Graph {

	private int initiator;
    private int graphSize;
    private int edges;
    private int totalMessages;
    GraphNode[] graphNodes;
	private boolean adjMatrix[][];  
	private String graphInput;
	private int iterations;


	public Graph(String filePath) {
		this.iterations = 0;
		this.totalMessages = 0;
		this.graphInput = filePath;
		readInputFile(filePath);
	}

	private void readInputFile(String filePath) {
		
		// Read file input and create the edges
	    try {
	        File file = new File(filePath);
	
	        BufferedReader br = new BufferedReader(new FileReader(file));
	        
	        String line = br.readLine();

	        // Read & check first line which is the size of Graph
        	if (line.matches("-?(0|[1-9]\\d*)")) {
        		System.out.println("A graph of total " + line + " graphSize has been created.");
        		initializeGraph(Integer.parseInt(line));
        	}
        	// read graph's edges.
	        while ((line = br.readLine()) != null) {
	        	if (line.matches("\\d*\\s\\d*")) {
	        		populateGraph(line);
	        	} else {
	        		throw new IllegalArgumentException("Please use the following input format "
	        				+ "'$Integer + Space/Tab + $Integer' at line =" + line);
	        	}
	        }
	        br.close();
	
	    } catch (Exception e) {
	        System.out.println("Working Directory = " +
	                System.getProperty("user.dir") + " ERROR : " + e );
	        e.printStackTrace();
	    }
		
	}

	private void populateGraph(String line) {
		
		int inputNodes[] = new int[2];
		
		inputNodes = Arrays.asList(line.split("\\s")).stream().mapToInt(Integer::parseInt).toArray();
    	this.addEdge(inputNodes[0],inputNodes[1]);
		System.out.println("Edge " + Arrays.toString(inputNodes) + " has been added.");
		
    	if (inputNodes[0] >= this.graphSize || inputNodes[0] < 0) {
    		throw new IllegalArgumentException("Wrong node value " + inputNodes[0] + ".\n"
    		 		+ "Be sure you have inserted the correct graph size and the graphSize value are not negative and are smaller than the graph_size.");
    	}
    	if (inputNodes[1] >= this.graphSize || inputNodes[1] < 0) {
    		throw new IllegalArgumentException("Wrong node value " + inputNodes[1] + ".\n "
        		 		+ "Be sure you have inserted the correct graph size and the graphSize value are not negative and are smaller than the graph_size.");
    	}
		
	}

	private void initializeGraph(int size) {
		this.iterations = 0;
		this.totalMessages = 0;
		this.graphSize = size;
		adjMatrix = new boolean[this.graphSize][this.graphSize];
		graphNodes = new GraphNode[this.graphSize];
		for (int i = 0 ; i < this.graphSize ; i++ ) {
			graphNodes[i] = new GraphNode(i);
		}
	}

	public void resetGraph() {
		System.out.println("Graph [" + this.graphInput + "] has been resetted");
		this.totalMessages = 0;
		this.iterations = 0;
		for (GraphNode n : this.graphNodes) {
			n.resetNode();
		}
	}

	public void resetGraphForFurtherTesting() {
		System.out.println("TESTING AGAIN : Graph [" + this.graphInput + "] so it has been resetted");
		this.totalMessages = 0;
		this.iterations = 0;
		for (GraphNode n : this.graphNodes) {
			n.resetNode();
		}
	}

	private void addEdge(int i, int j) {
		adjMatrix[i][j] = true;
		graphNodes[i].incrementNeighbors();
    	adjMatrix[j][i] = true;
    	graphNodes[j].incrementNeighbors();
    	this.edges++;
    	
	}

	/**
	 * If every neighbor has a father set as visited
	 * so this node can proceed to echo
	 * @param val value of the Node
	 */
	public void checkForUnexploredNeighbors(int val) {
		for (int n = 0 ; n < this.graphSize ; n++) {
			if (adjMatrix[val][n]) {
				if (this.graphNodes[n].getFather() < -1) return;
			}
		}
		System.out.println("Node " + val + " has been set as visited due to no unexplored neighbors");
		this.graphNodes[val].setForEcho();
	}
	
	/**
	 * Fires echo on Node with value == val.
	 * Then increment father's token.
	 * @param val is the value of the Node
	 */
	public void echo(int val) {
		GraphNode curNode = this.graphNodes[val];
		if (curNode.echo()) {
			this.graphNodes[curNode.getFather()].receiveToken();
			this.totalMessages++;
		} else {
			System.out.println("Node " + val + " stayed IDLE. (has either already echoed or is waiting to)");
		}
	}
	
	@SuppressWarnings("unused")
	private void calculateNeighbors() {
		for (int i = 0 ; i < this.graphSize ; i++) {
		   for (int j = 0 ; j < this.graphSize ; j++) {
			   if (this.adjMatrix[i][j]) {
				   graphNodes[i].incrementNeighbors();
			   }
		   }
		}
	}
	
	/**
	 * If all nodes are visited on the graph start sending tokens
	 * @return true if nodes must send the tokens to their parent
	 
	public boolean hasStartedEcho() {
		return (this.totalMessages == this.graphSize);
	}*/

	
	public void setInitiator(int val) {
		this.initiator = val;
	}

	public GraphNode[] getNodesInfo() {
		return this.graphNodes;
	}
	
	public boolean[][] getAdjMatrix() {
		return this.adjMatrix;
	}
	
	public int getGraphSize() {
		return this.graphSize;
	}
	
	public int getGraphEdges() {
		return this.edges;
	}
	
	public int getMessages() {
		return this.totalMessages;
	}
	
	public String getGraphInput() {
		return this.graphInput;
	}

	public void printNodeInfo() {
		for (int i = 0 ; i < this.graphSize ; i++ ) {
			System.out.println(this.graphNodes[i]);
		}
	}
	
	public void printAdjMatrix() {
		for (int i = 0 ; i < this.graphSize; i++) {
			System.out.print("______");
		}
		System.out.print(" {Adjacency Matrix} ");
		for (int i = 0 ; i < this.graphSize; i++) {
			System.out.print("______");
		}

		System.out.print("_\n|  N     ");
		for (int i = 0 ; i < this.graphSize; i++) {
			int indexSpace = (i == 0) ? 1 : (int) (Math.log10(i) + 1);
			System.out.print("|    (" + i + ")");
			for (int k = indexSpace ; k <= 4 ; k++) System.out.print(" ");
			System.out.print("|");
		}
		System.out.print("\n");
		System.out.print("|        |");
		System.out.print("\n");
		for (int i = 0 ; i < this.adjMatrix.length ; i++) {
			int indexSpace = (i == 0) ? 1 : (int) (Math.log10(i) + 1);
			System.out.print("| (" + (i) +")");
			for (int k = indexSpace ; k <= 4 ; k++) System.out.print(" ");
			for (int j = 0 ; j < this.adjMatrix.length ; j++) {
				System.out.print("|     " + (this.adjMatrix[i][j] ? 1 : 0) + "     |");
			}
			System.out.print("\n");
		}

		for (int i = 0 ; i < this.graphSize; i++) {
			System.out.print("_________");
		}
		System.out.println("____");
	}
	
	public String toString() {
		return "Graph " + this.graphInput + " has "+this.edges+ 
				" total edges and "+ this.totalMessages +" total sent messages in #" + this.iterations + " iterations";
	}

	public ArrayList<Integer> sentMessagesToNeighbors(int father) {
		ArrayList<Integer> children = new ArrayList<Integer>();
		for (int n = 0 ; n < this.graphSize ; n++) {
			if (n == graphNodes[father].getFather()) continue;
			if (adjMatrix[father][n]) {
				this.totalMessages++;
				GraphNode neigh = this.graphNodes[n];
				if (neigh.getFather() < -1) {
					neigh.setFather(father);
					children.add(n);
				} else {
					graphNodes[father].sentToken(neigh);
				}
			}
		}

		/*
		 * First set ALL children to the father
		 * and then check if the children can start echo

		for (int n : children) {
			this.checkForUnexploredNeighbors(n);
		}*/

		this.graphNodes[father].setVisited();
		return children;
	}

	public int getIterations() {
		return iterations;
	}

	public void setIterations(int iterations) {
		this.iterations = iterations;
	}

	public String getGraphInputName() {
		String[] graphInputName = this.graphInput.split("/");
		return graphInputName[graphInputName.length - 1];
	}

	public String getGraphInputLengthInSpaces() {
		String[] graphInputName = this.graphInput.split("/");
		StringBuffer s=new StringBuffer();
		for (int i = 0 ; i < graphInputName[graphInputName.length - 1].length() ; i++)
			s.append(" ");
		return s.toString();
	}

	public int getTotalMessagesSent() {
		return this.totalMessages;
	}
}
