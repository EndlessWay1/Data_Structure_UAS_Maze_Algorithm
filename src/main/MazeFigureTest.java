package main;

import MazeGeneratorCath.MazeMaker;
import graph.Graph;
import graph.Report;
import visual.MazeFrame;

public class MazeFigureTest {

    public static void main(String[] args) {

        int row = 10;
        int col = 10;

        MazeFrame kFrame = new MazeFrame(row, col);

        MazeMaker k = kFrame.getMazeMaker();

        Graph<Integer> mGraph = k.toGraph();

        int start = 0;

        int target = row * col - 1;

        // jalankan Dijkstra
        Report<Integer> dijkstra = kFrame.getDijkstraReport();

        System.out.println("Dijkstra");
        System.out.println("\tCost\t\t: " + dijkstra.getCost());
        System.out.println("\tNode Visited\t: " + dijkstra.getNodeVisited());
        System.out.println("\tTime\t\t: " + dijkstra.getTime()*1E-6 + " ms");
        System.out.println("\tPath\t\t: ");
        System.out.print("\t");
        dijkstra.getPath().cetakList();

        // jalankan A*
        // col dipakai untuk menghitung row dan col dari index vertex
        Report<Integer> aStarReport = kFrame.getaStarReport();
        System.out.println("A*");
        System.out.println("\tCost\t\t: " + aStarReport.getCost());
        System.out.println("\tNode Visited\t: " + aStarReport.getNodeVisited());
        System.out.println("\tTime\t\t: " + aStarReport.getTime()*1E-6 + " ms");
        System.out.println("\tPath\t\t: ");
        System.out.print("\t");
        aStarReport.getPath().cetakList();
        
        
    }
}