package main;

import MazeGeneratorCath.MazeMaker;
import graph.BTNode;
import graph.Graph;
import graph.MyLinearList;
import visual.MazeFrame;

public class MazeFigureTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int row = 100;
		int col = 100;
		
		MazeFrame kFrame =  new MazeFrame(row, col);
		
		MazeMaker k = kFrame.getMazeMaker();
		
		Graph<Integer> mGraph = k.toGraph();
		mGraph.printGraph();
		
		BTNode<Integer, MyLinearList<Integer>> retBtNode = mGraph.dijkstra(0, row*col - 1);
		
		System.out.println("Cost: " + retBtNode.getKey());
		System.out.println("Path: ");
		retBtNode.getData().cetakList();
	}

}
