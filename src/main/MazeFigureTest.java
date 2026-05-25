package main;

import MazeGeneratorCath.MazeMaker;
import graph.Graph;
import visual.MazeFrame;

public class MazeFigureTest {

    public static void main(String[] args) {

        int row = 20;
        int col = 20;

        MazeFrame kFrame = new MazeFrame(row, col);

        MazeMaker k = kFrame.getMazeMaker();

        Graph<Integer> mGraph = k.toGraph();

        int start = 0;

        int target = row * col - 1;

        // jalankan Dijkstra
        mGraph.dijkstraReport(start, target);

        System.out.println();

        // jalankan A*
        // col dipakai untuk menghitung row dan col dari index vertex
        mGraph.aStarReport(start, target, col);
    }
}