package visual;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.nio.channels.NonWritableChannelException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;

import MazeGeneratorCath.Block;
import MazeGeneratorCath.MazeMaker;
import MazeGeneratorCath.MazeNode;
import graph.Graph;
import graph.MyLinearList;
import graph.Node;

public class MazePanel extends JPanel{
	MazeMaker mazeMaker;
	Color borderColor = Color.green;
	Color bgColor = Color.black;
	int rows;
	int cols;
	int visited = 0;
	
	boolean vertexs = false;
	Map<Integer, MazeNode> graph;
	
	MyLinearList<Integer> path;
	int cellSize;
    
    int mazeWidth;
    int mazeHeight;

    int offsetX;
    int offsetY;

	
	public MazeMaker getMazeMaker() {
		return mazeMaker;
	}
	
	public void setPath(MyLinearList<Integer> path) {
		this.path = path;
	}

	public void setVertex(boolean on) {
		this.vertexs = on;
	}

	

	int borderSize =  1;
	
	public MazePanel(MazeMaker mazeMaker) {
		this(mazeMaker, Color.green, 1, Color.black);
	}
	
	public MazePanel(MazeMaker mazeMaker, Color borderColor, int borderSize, Color bgColor) {
		
		this.borderColor = borderColor;
		this.borderSize = borderSize;
		this.bgColor = bgColor;
		
		
		this.setPreferredSize(new Dimension(750, 650));
		this.setBackground(bgColor);
		this.mazeMaker = mazeMaker;
		this.graph = mazeMaker.toMap();
	
		rows = mazeMaker.getRows(); 
		cols = mazeMaker.getCols();
		
	}
	
	public void redraw(int step) {
		visited = step;
		repaint();
	}


	private void DFS_draw_map(Map<Integer, Integer> visited, int start, Graphics2D g2d) {
	    if (visited.containsKey(start)) return;

	    visited.put(start, 1);
	    MazeNode s = graph.get(start);

	    int x1 = offsetX + cellSize/2 + (start % cols) * cellSize;
	    int y1 = offsetY + cellSize/2 + (start / cols) * cellSize;

	    // draw node
	    g2d.fillOval(x1 - cellSize/4, y1 - cellSize/4,
	                 cellSize/2, cellSize/2);

	    for (int i = 0; i < 4; i++) {
	        int cord = s.cords[i];

	        if (cord != -1) {
	            int x2 = offsetX + cellSize/2 + (cord % cols) * cellSize;
	            int y2 = offsetY + cellSize/2 + (cord / cols) * cellSize;

	            g2d.drawLine(x1, y1, x2, y2);
	            DFS_draw_map(visited, cord, g2d);
	        }
	    }
	}	 
	private void setSizes() {
		cellSize = Math.max(1, Math.min(
	            getWidth() / cols,
	            getHeight() / rows
	        ));
	    
	    mazeWidth = cellSize * cols;
	    mazeHeight = cellSize * rows;

	    offsetX = (getWidth() - mazeWidth) / 2;
	    offsetY = (getHeight() - mazeHeight) / 2;

	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D) g;

		g2d.setPaint(Color.blue);
		g2d.setStroke(new BasicStroke(borderSize));
		
		setSizes();
		
		g2d.fillRect(offsetX + (cols - 1)*cellSize, offsetY + (rows - 1)*cellSize, cellSize, cellSize);
	    
	    
	    g2d.fillRect(offsetX, offsetY, cellSize, cellSize);
	    
		
		
		g2d.setPaint(Color.red);
		
		
		
	    if (path != null) {	    	
	    	Node<Integer> head =  path.head;
	    	int curr = 1;
	    	int p_r = 0;
	    	int p_c = 0;
	    	while (curr < this.visited + 1 && head != null) {
	    		
	    		
	    		int idx = head.getData();
	    		int c_r = idx / cols;
	    		int c_c = idx % cols;
	    		
	    		// if we go up or left
	    		if (c_r < p_r || c_c < p_c) {
	    			g2d.fillRect(
	    				    offsetX + c_c*cellSize,
	    				    offsetY + c_r*cellSize,
	    				    cellSize * (p_c - c_c + 1),
	    				    cellSize * (p_r - c_r + 1)
	    				);
	    		}
	    		// if we go down or right	    	
	    		else {
	    			g2d.fillRect(
	    				    offsetX + p_c*cellSize,
	    				    offsetY + p_r*cellSize,
	    				    cellSize * (c_c - p_c + 1),
	    				    cellSize * (c_r - p_r + 1)
	    				);
	    		}
	    		
	    		p_c = c_c;
	    		p_r = c_r;
	    		
	    		curr++;
	    		head = head.getNext();
	    	}
	    }
	    
	    if (vertexs) {	    	
	    	g2d.setPaint(Color.CYAN);
	    	Map<Integer, Integer> visitedMap = new HashMap<>();
	    	DFS_draw_map(visitedMap, 0, g2d);
	    }
		g2d.setPaint(borderColor);
		g2d.setStroke(new BasicStroke(borderSize));

	    
		for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
            	Block block = mazeMaker.getBlocks()[row][col];
            	
            	int x = offsetX + col * cellSize;
                int y = offsetY + row * cellSize;

                boolean[] walls = block.getWalls();

            	
            	int thickness = 1;

            	if (walls[0]) { // top
            	    g2d.fillRect(x, y, cellSize, thickness);
            	}

            	if (walls[1]) { // right
            	    g2d.fillRect(
            	        x + cellSize - thickness,
            	        y,
            	        thickness,
            	        cellSize
            	    );
            	}

            	if (walls[2]) { // bottom
            	    g2d.fillRect(
            	        x,
            	        y + cellSize - thickness,
            	        cellSize,
            	        thickness
            	    );
            	}

            	if (walls[3]) { // left
            	    g2d.fillRect(
            	        x,
            	        y,
            	        thickness,
            	        cellSize
            	    );
            	}
            }
            
        }
	}
	
	
}
