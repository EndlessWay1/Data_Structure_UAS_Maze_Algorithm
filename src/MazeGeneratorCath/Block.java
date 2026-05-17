package MazeGeneratorCath;

import java.util.ArrayList;
import java.util.Random;

public class Block {
    int row;
    int col;
    boolean visited = false;

    // top, right, bottom, left
    boolean[] walls = {true, true, true, true};

    // for storing neighbors
    ArrayList<Block> neighbors = new ArrayList<>();

    // for picking random neighbors
    Random random = new Random();

    public Block(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public void addNeighbors(Block[][] blocks, int rows, int cols) {
        // top neighbor
        if(row > 0) {
            neighbors.add(blocks[row - 1][col]);
        }

        // right neighbor
        if(col < cols - 1) {
            neighbors.add(blocks[row][col + 1]);
        }

        // bottom neighbor
        if(row < rows - 1) {
            neighbors.add(blocks[row + 1][col]);
        }

        // left neighbor
        if(col > 0) {
            neighbors.add(blocks[row][col - 1]);
        }
    }

    public boolean[] getWalls() {
		return walls;
	}

	public ArrayList<Block> getNeighbors() {
		return neighbors;
	}

	// check if there are unvisited neighbors
    public boolean hasUnvisitedNeighbors() {
        for(Block n : neighbors) {
            if(!n.visited) {
                return true;
            }
        }
        return false;
    }

    // pick a random unvisited neighbor
    public Block pickRandomNeighbor() {
        ArrayList<Block> unvisited = new ArrayList<>();
        for(Block n : neighbors) {
            if(!n.visited) {
                unvisited.add(n);
            }
        }

        if(unvisited.size() > 0) {
            Block chosen = unvisited.get(random.nextInt(unvisited.size()));
            chosen.visited = true;
            return chosen;
        }
        return null;
    }
}