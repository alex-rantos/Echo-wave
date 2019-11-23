/**
 *
 */
package uk.ac.ncl.echo.wave;

import uk.ac.ncl.structures.graph.Graph;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class UseAlgorithm {

    private static final boolean TEST = false;
    private static final int TEST_RUNS = 30;

    private static File createFile(String filePath) throws IOException {
        File file = new File(filePath);

        //Create the file
        if (file.createNewFile()) {
            System.out.println("File is created!");
        } else {
            System.out.println("File already exists.");
        }

        return file;
    }

    public static void main(String[] args) throws IOException {
        String[] inputFileName = {
                "arbitraryGraph.txt",
                "cycleGraph.txt",
                "cycleGraph40.txt",
                "cycleGraph100.txt",
                "lineGraph.txt",
                "lineGraph40.txt",
                "lineGraph100.txt",
                "fullyConnectedGraph.txt",
                "fullyConnectedGraph40.txt",
                "fullyConnectedGraph100.txt",
        };
        if (TEST)
            testAlgorithm(inputFileName);
        else
            runAlgorithm(inputFileName);
        return;
    }

    private static void runAlgorithm(String[] inputFileName) {
        for (String input : inputFileName) {
            Graph g = new Graph("src/resources/" + input);
            System.out.println(g);
            g.printAdjMatrix();
            g.printNodeInfo();
            EchoWaveAlgorithm echo = new EchoWaveAlgorithm();
            while (echo.executeEchoWave(g)) {
                echo.doubleVariableK();
                g.resetGraph();
            }
            System.out.println(g);
        }
    }

    private static void testAlgorithm(String[] inputFileName) throws IOException {
        File file = createFile("./results.txt");
        FileWriter writer = new FileWriter(file);
        for (String input : inputFileName) {
            Graph g = new Graph("src/resources/" + input);
            System.out.println(g);
            g.printAdjMatrix();
            g.printNodeInfo();
            int minIter = Integer.MAX_VALUE,
                    maxIter = Integer.MIN_VALUE,
                    avgIter = 0, avgExec = 0;
            for (int i = 0; i < TEST_RUNS; i++) {
                EchoWaveAlgorithm echo = new EchoWaveAlgorithm();
                while (echo.executeEchoWave(g)) {
                    echo.doubleVariableK();
                    g.resetGraph();
                }
                System.out.println(g);
                if (g.getIterations() > maxIter) maxIter = g.getIterations();
                if (g.getIterations() < minIter) minIter = g.getIterations();
                avgExec += echo.getExecutions();
                avgIter += g.getIterations();
                if (i == 0)
                    writer.write(g.getGraphInputName() + " - " + g.getGraphEdges() + " - " + g.getTotalMessagesSent() + '\n');
                writer.write(g.getGraphInputLengthInSpaces() + " | " + g.getIterations() + " | " + echo.getExecutions() + '\n');
                if (i != TEST_RUNS - 1)
                    g.resetGraphForFurtherTesting();
                else
                    writer.write("MinIteration : " + minIter + " & MaxIteration " + maxIter + '\n' +
                            "AvgIterations: " + avgIter / TEST_RUNS + " AvgExecutions: " + (float) avgExec / TEST_RUNS + '\n');
            }
        }
        writer.close();
    }
}
