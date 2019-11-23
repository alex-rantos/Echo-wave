package uk.ac.ncl.echo.wave;

import uk.ac.ncl.structures.graph.Graph;
import uk.ac.ncl.structures.graph.GraphNode;
import uk.ac.ncl.structures.tree.TreeResult;

import java.util.ArrayList;

/**
 * @author R-Alex95
 */

public class EchoWaveAlgorithm {
    private int K;
    private int executions;
    private TreeResult treeOutput;

    EchoWaveAlgorithm() {
        this.K = 2;
        this.executions = 1;
    }

    boolean executeEchoWave(Graph g) {
        int graphSize = g.getGraphSize();
        GraphNode[] nodes = g.getNodesInfo();

        //generate initiator
        int initiator = (int) (Math.random() * (graphSize));
        this.treeOutput = new TreeResult(initiator, graphSize);

        nodes[initiator].setFather(-1);
        System.out.println("Initiator for graph [" + g.getGraphInput() + "] is " + initiator);

        final int maxIterations = K * graphSize;
        for (int i = 0; i < maxIterations; i++) {
            // generate random number which corresponds to the total selected nodes for this iteration
            int R = (int) (Math.random() * (graphSize));
            System.out.println(R + " Nodes will be randomly generated for iteration #" + (i + 1) + "/" + maxIterations);
            boolean[] hasNodeBeenSelected = new boolean[graphSize];

            for (int j = 0; j < R; j++) {
                int nodeSelected = (int) (Math.random() * (graphSize));

                // generate another random number if current has already been generated.
                while (hasNodeBeenSelected[nodeSelected]) {
                    nodeSelected = (int) (Math.random() * (graphSize));
                }
                hasNodeBeenSelected[nodeSelected] = true;
                System.out.println("Node " + nodeSelected + " will perform the protocol");

                if (!nodes[nodeSelected].isVisited()) {
                    if (nodes[nodeSelected].hasFather()) {
                        // Send message to neighbors.
                        ArrayList<Integer> children = g.sentMessagesToNeighbors(nodeSelected);
                        for (int child : children) treeOutput.addChild(nodeSelected, child);
                    } else {
                        System.out.println("Node " + nodeSelected + " stayed IDLE. (has not yet discovered)");
                    }
                } else {
                    if (nodeSelected == initiator) {
                        if (nodes[nodeSelected].canDecide()) {
                            g.setIterations(i+1);
                            System.out.println("DECISION IS MADE!Execution #" + this.executions +
                                    " has finished with total of " + i + " repetitions");
                            if (g.getGraphEdges() * 2 != g.getMessages()) {
                                throw new IllegalStateException("THEOREM INVALID");
                            }
                            treeOutput.printTree();
                            return false;
                        }
                    } else
                        g.echo(nodeSelected);
                }
            }
        }
        this.executions++;
        return true;
    }

    public void doubleVariableK() {
        this.K *= 2;
    }

    public int getExecutions() {
        return this.executions;
    }
}
