import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    static int rows;
    static int cols;
    static Block[][] blocks;
    static Block current;
    // for backtracking
    static ArrayList<Block> stack = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter the number of rows: ");
        rows = scan.nextInt();
        System.out.print("Enter the number of columns: ");
        cols = scan.nextInt();

        scan.close();

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

        // start from the top-left block
        current = blocks[0][0];
        current.visited = true;
        generateMaze();
        printMaze();
    }

    // maze generation using depth-first search
    static void generateMaze() {
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

    // remove walls between current and next blocks
    static void removeWalls(Block current, Block next) {
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
    static void printMaze() {
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
}