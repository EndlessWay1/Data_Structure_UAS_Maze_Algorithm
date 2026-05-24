package visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import MazeGeneratorCath.MazeMaker;

public class MazeFrame extends JFrame{
	
	MazeMaker mazeMaker;

	public MazeFrame(int row, int col, String title) {

	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.setExtendedState(JFrame.MAXIMIZED_BOTH);
	    this.setTitle(title);
	    this.setSize(500, 500);

	    this.setLayout(new BorderLayout());
	    float factor = 750/row;
	    
	    mazeMaker = new MazeMaker(row, col);

	    JLabel label = new JLabel("Maze " + Integer.toString(row) + " x " +  Integer.toString(col));
	   
	    label.setHorizontalAlignment(JLabel.CENTER);
	    label.setPreferredSize(new Dimension(200,100));
	    label.setFont(new Font("Consolas", Font.PLAIN, 20));
	    label.setForeground(Color.GREEN);
	    label.setBackground(Color.black);
	    label.setOpaque(true);

	    MazePanel panel = new MazePanel(mazeMaker);
	    System.out.println("Here is fine!");

	    panel.setBounds(0, 0, (int)(factor*col), 650);
	    panel.setBackground(Color.black);
	    panel.setOpaque(true);

	    JLayeredPane layeredPane = new JLayeredPane();
	    layeredPane.addComponentListener(new ComponentAdapter() {
	        @Override
	        public void componentResized(ComponentEvent e) {

	            int x = (layeredPane.getWidth() - panel.getWidth()) / 2;
	            int y = (layeredPane.getHeight() - panel.getHeight()) / 2;

	            panel.setLocation(x, y);
	        }
	    });
	    
	    layeredPane.setPreferredSize(new Dimension(750, 650));
	    layeredPane.add(panel, Integer.valueOf(0));
	    layeredPane.setOpaque(true);
	    layeredPane.setBackground(Color.black);
	  

	    this.add(label, BorderLayout.NORTH);
	    this.add(layeredPane, BorderLayout.CENTER);

	    this.setLocationRelativeTo(null);
	    this.setVisible(true);
	}

	
	
	public MazeFrame(String title) {
		this(100, 100, title);
	}	

	public MazeFrame() {
		this(100, 100, "");
	}	
	
	public MazeFrame(int row, int col) {
		this(row, col, "");
	}



	public MazeMaker getMazeMaker() {
		// TODO Auto-generated method stub
		return mazeMaker;
	}
}
