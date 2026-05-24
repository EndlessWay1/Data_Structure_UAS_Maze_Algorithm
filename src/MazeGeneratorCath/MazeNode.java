package MazeGeneratorCath;

public class MazeNode {
    // top, right, bottom, left
    public int[] distance = {
        -1, -1, -1, -1
    };
    public int[] cords = {
        -1, -1, -1, -1
    };
    public int index = 0;

    @Override
    public String toString(){
        return "{distance: [" + 
            distance[0] + ", " + 
            distance[1] + ", "+ 
            distance[2] + ", "+ 
            distance[3] + "], cords: ["+
            cords[0] + ", " + 
            cords[1] + ", "+ 
            cords[2] + ", "+ 
            cords[3] + "], index: "+ index +"}";
    }
}