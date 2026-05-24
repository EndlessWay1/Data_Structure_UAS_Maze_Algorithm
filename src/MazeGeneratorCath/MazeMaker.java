package MazeGeneratorCath;


import graph.Graph;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MazeMaker{

    int rows;
    int cols;
    Block[][] blocks;
    Block current;
    // for backtracking

    public MazeMaker(int row, int col) {
		// TODO Auto-generated constructor stub
    	rows = row;
    	cols = col;
    	
    	blocks = new Block[rows][cols];
    	
    	// create blocks
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < cols; j++) {
                blocks[i][j] =
                        new Block(i, j);
            }
        }
        
     // add neighbors
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < cols; j++) {
                blocks[i][j].addNeighbors(blocks, rows, cols);
            }
        }
        
        current = blocks[0][0];
        current.visited = true;
        generateMaze();
        
    	
	}
    

    // maze generation using depth-first search
    private void generateMaze() {
        ArrayList<Block> stack = new ArrayList<>();

    	while(true) {
            // check if there are unvisited neighbors
            if(current.hasUnvisitedNeighbors()) {
                Block next = current.pickRandomNeighbor();
                stack.add(current);
                removeWalls(current, next);
                current = next;
            }

            // backtrack if there are no unvisited neighbors
            else if(stack.size() > 0) {
                current =stack.remove(stack.size() - 1);
            }

            // maze generation is complete when there are no more blocks to backtrack to
            else {
                System.out.println("Maze Finished!");
                break;
            }
        }
    }
    
    public void regenerateMaze() {
    	for (int i = 0; i < rows; i++) {
    		for (int j = 0; j < cols; j++) {
    			blocks[i][j].visited = false;
    			for (int k = 0; k < 4; k++) {    				
    				blocks[i][j].walls[k] = true;
    			}
    		}
    	}
    	
    	generateMaze();
    }

    // remove walls between current and next blocks
    private void removeWalls(Block current, Block next) {
        int x = current.col - next.col;
        int y = current.row - next.row;

        // left-right
        if(x == 1) {
            current.walls[3] = false;
            next.walls[1] = false;
        }
        else if(x == -1) {
            current.walls[1] = false;
            next.walls[3] = false;
        }
        // top-bottom
        if(y == 1) {
            current.walls[0] = false;
            next.walls[2] = false;
        }
        else if(y == -1) {
            current.walls[2] = false;
            next.walls[0] = false;
        }
    }
    // print the maze
    public void printMaze() {
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < cols; j++) {
                System.out.print("[");

                // print walls as 1 and 0
                for(boolean wall :
                        blocks[i][j].walls) {

                    if(wall) {
                        System.out.print("1");
                    } else {
                        System.out.print("0");
                    }
                }

                System.out.print("] ");
            }

            System.out.println();
        }
    }


	public int getRows() {
		return rows;
	}

	public int getCols() {
		return cols;
	}


	public Block[][] getBlocks() {
		return blocks;
	}

    public Graph<Integer> toGraph(){
        boolean[][] visited = new boolean[rows][cols];
        boolean[][] isGone = new boolean[rows][cols];
        
        // mark for straight only true is to be removed, false is a node. 
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < cols; j++){
                boolean[] wall = blocks[i][j].getWalls();
                // top, right, bottom, left
                int count = (wall[0]?1:0) + (wall[1]?1:0) + (wall[2]?1:0) + (wall[3]?1:0);
        
                boolean up_down = (wall[0] && wall[2]);
                boolean left_right = (wall[1] && wall[3]);
                
                if (count == 2 && (up_down || left_right)){
                    isGone[i][j] = true;
                }
            }
        }
 
        System.out.println("IsGone: ");
        System.out.println(Arrays.deepToString(isGone));
        

        Map<Integer, MazeNode> g = new HashMap<>();

   
        DFS(0, 0, visited, isGone, null, 0, g);
        
        Graph<Integer> retGraph = new Graph<>(false); // set as bidirectional graph
        
        
        Map<Integer, Integer> visited_m = new HashMap<>();
        
        
        
        DFS_map_toGraph(g, retGraph, visited_m, 0);

        
        return retGraph;
        
    }
    
    private void DFS_map_toGraph(
    		Map<Integer, MazeNode> m, Graph<Integer> g, 
    		Map<Integer, Integer> visited, int start) {
    	if (visited.containsKey(start)) return;
    	
    	MazeNode s = m.get(start);
    	
        visited.put(start, 1);
    	
    	//  Iterasi cords
    	for (int i = 0; i < 4; i++) {
    		int cord = s.cords[i];
    		if (cord != -1) {
                DFS_map_toGraph(m, g, visited, cord);
                g.addEdge(start, cord, s.distance[i]);
    		}
    	}
    	
    }
    
    private void DFS(
        int i, int j, boolean[][] visited, boolean[][] isGone, MazeNode prev, int arah, Map<Integer, MazeNode> g
        ){
    	
        MazeNode curr_node = null;      
        
    	// if prev is null, then this is the original nodes
        if (prev == null){
            // add new node
            curr_node = new MazeNode();

            // add index
            curr_node.index = i*cols + j;

            // put in graph
            g.put(curr_node.index, curr_node);
        }
        
        if (visited[i][j]) return;

        int distance = 1;

        // PROOF why i and j cant be out of bounds:
        /*
        * Proof by Contradiction:
        * Suppose i or j is out of bounds, 
        * then => there exist:
        * isGone[i - 1][j] or isGone[i + 1][j] or isGone[i][j + 1] or isGone[i][j - 1],
        * where isGone has reach the edge, but the value is True. 
        * This contradicts the original algorithm for making isGone. (We assume isGone is always correct).
        * Thus a contradiction has arrived, and implies i and j cant be out of bounds, while in the loop.
        *
        */
        while (isGone[i][j]){
            
            // mark node as visited
            visited[i][j] = true;
            
            distance += 1;
            // traversal
            // top
            // right
            // bottom
            // left
            switch(arah){
                case 1 -> i--;
                case 2 -> j++;
                case 3 -> i++;
                case 4 -> j--;
            }
        }
        
        int index = i*cols + j;
        // arahnya terbalik
        int new_arah = (arah == 1)? 3 : (arah == 3)? 1: (arah == 2)? 4: 2;


        if (!isGone[i][j]){
            // it means there exist another MazeNode in that cords
            if (visited[i][j]){
                // update the already made MazeNode
                curr_node = g.get(index);
                
                curr_node.cords[new_arah - 1] = prev.index;
    
                curr_node.distance[new_arah - 1] = distance;
    
                // update the prev node in graph
                prev.cords[arah - 1] = index;
                
                // set the prev node in graph to be distance
                prev.distance[arah - 1] = distance;
            }
            // make new MazeNode if it has moved, else, this is the first node
            else if (!visited[i][j] && prev != null) {
    
                curr_node = new MazeNode();
                curr_node.index = index;
    
                curr_node.distance[new_arah - 1] = distance;
    
                curr_node.cords[new_arah - 1] = prev.index;
    
                prev.cords[arah - 1] = curr_node.index;
                prev.distance[arah - 1] = distance;
    
                g.put(curr_node.index, curr_node);
    
            }

        }


        visited[i][j] = true;


        // traverse to posible left right, top bottom walls
        // its technically impossible for i or j to be 0 or rows or cols, because we only traverse to possible walls.
        boolean[] walls = blocks[i][j].getWalls();
        if (!walls[0]){
            // top
        	System.out.println("Top");
            DFS(i - 1, j, visited, isGone, curr_node,  1, g);
        }
        if (!walls[2]){
            // bottom
        	System.out.println("Bottom");
            DFS(i + 1, j, visited, isGone, curr_node,  3, g);
        }
        if (!walls[1]){
        	System.out.println("Right");
            // right
            DFS(i, j + 1, visited, isGone, curr_node,  2, g);
        }
        if (!walls[3]){
            // left
        	System.out.println("Left");
            DFS(i, j - 1, visited, isGone, curr_node,  4, g);
        }

    }

}

