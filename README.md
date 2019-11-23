## Distributed Algorithms - Echo Wave

This project implements the echo wave algorithm for undirected networks. To simulate
a realistic execution in each repetition of the algorithm the nodes that are able to execute
the protocol are generate randomly.

### Outcome / Observation
Depending the purpose of the network an appropriate network structure must be 
selected because there is a correlation between network's connection pattern,
network's cost and time. Therefore, the execution time is dropped when the
network connectivity is strong but this increases the cost (total messages sent) and produce shallow trees.
On the other hand, if a network is not well connected the cost decreases but the time 
increases and the tree's depth.

### Input explanation
Input is read by a .txt file which must be located in the src/resources/ folder. 
This file must have a unique pattern that must be followed in order the program to run correctly.
The rules that are described bellow must be followed :
1.  The first line of the file must be the graph size. (to avoid reading file twice)

2.  Each of the rest lines must represent an edge with the following format : '$Integer + Space||Tab + $Integer' 
(eg line "3 2" OR "3	2" means that Nodes with value 3 & 2 are connected). 

Each node value (val) must be an Integer and satisfy the following unequality : 0 >= val > graphSize. 
It is strongly advised, since static structures have been used,  
that **ALL** nodes' values, in the range of [0,graphSize), are used in order to avoid unnecessary memory allocation
Also make sure that the graph is connected otherwise algorithm won't terminate, since performing the algorithm in
graph's connected components have not yet implemented

#### Configurations
@src.uk.ac.ncl.echo.UseAlgorithm#main : If you want to test your file add the name of the file in the inputFileName String array
 and the file itself in the src/resources/ folder.

@src.uk.ac.ncl.echo.UseAlgorithm : Two constants are declared for helping draw report's conclusions. If you change TEST = true, 
test mode is enabled and each graph will run the algorithm TEST_RUNS times. Then a results.txt is created containing the test results.

@src.uk.ac.ncl.echo.EchoWaveAlgorithm : K is set to 2 by default.


