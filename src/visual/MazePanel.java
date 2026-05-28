package visual;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.nio.channels.NonWritableChannelException;
import java.util.Iterator;

import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;

import MazeGeneratorCath.Block;
import MazeGeneratorCath.MazeMaker;
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
	
	MyLinearList<Integer> path;
	
	public MazeMaker getMazeMaker() {
		return mazeMaker;
	}
	
	public void setPath(MyLinearList<Integer> path) {
		this.path = path;
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
		rows = mazeMaker.getRows(); 
		cols = mazeMaker.getCols();
		
		
		
//		panels = new JPanel[rows][cols];
//	
////		adding panels to array
//		for (int i = 0; i < rows; i++) {
//			for (int j = 0; j < cols; j++) {
//				panels[i][j] = new JPanel();
//			}
//		}
		
//		this.setLayout(new GridLayout(rows, cols));
//		drawBorder();
		
////		adding panels to this
//		for (int i = 0; i < rows; i++) {
//			for (int j = 0; j < cols; j++) {
//				this.add(panels[i][j]);
//			}
//		}
	}
	
//	private void drawBorder() {
////		TODO 
//		Block[][] b =  mazeMaker.getBlocks();
//		
//		
//		// Iterate one by one
//		for (int i = 0; i < rows; i++) {
//			for (int j = 0; j < cols; j++) {
//				// get current blocks
//				Block currBlock = b[i][j];
//				JPanel currPanel = panels[i][j];
//				// top, right, bottom, left
//				boolean[] walls = currBlock.getWalls();
//				int t = (walls[0])? borderSize : 0;
//				int r = (walls[1])? borderSize : 0;
//				int d = (walls[2])? borderSize : 0;
//				int l = (walls[3])? borderSize : 0;
//				currPanel.setBorder(new MatteBorder(t,l,d,r, borderColor));
//				currPanel.setOpaque(true);
//				currPanel.setBackground(bgColor);
//			}
//		}
//	}
	
	public void redraw(int step) {
		visited = step;
		repaint();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D) g;

		g2d.setPaint(Color.blue);
		g2d.setStroke(new BasicStroke(borderSize));
		
		int cellSize = Math.max(1, Math.min(
	            getWidth() / cols,
	            getHeight() / rows
	        ));
	    
	    int mazeWidth = cellSize * cols;
	    int mazeHeight = cellSize * rows;

	    int offsetX = (getWidth() - mazeWidth) / 2;
	    int offsetY = (getHeight() - mazeHeight) / 2;

		
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
