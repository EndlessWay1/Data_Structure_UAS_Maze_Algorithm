package visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.time.Duration;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import MazeGeneratorCath.MazeMaker;
import graph.Graph;
import graph.MyLinearList;
import graph.Report;

public class MazeFrame extends JFrame implements ActionListener{
	
	MazeMaker mazeMaker;
	Report<Integer> dijkstraReport;
	Report<Integer> aStarReport;
	MazePanel mazePanel;
	JButton dButton;
	JButton aButton;
	JButton cButton;
	
	double Convertion = 1E-6;

	public MazeFrame(int row, int col, String title) {

	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.setExtendedState(JFrame.MAXIMIZED_BOTH);
	    this.setTitle(title);
	    this.setSize(500, 500);

	    this.setLayout(new BorderLayout());
	    float factor = 750/row;
	    
	    mazeMaker = new MazeMaker(row, col);
	    
	    Graph<Integer> mGraph = mazeMaker.toGraph();
	    
	    
	    dijkstraReport = mGraph.dijkstraReport(0, row*col - 1);
	    aStarReport = mGraph.aStarReport(0, row*col - 1, col);
	    
	    
	    
	    JPanel topPanel = new JPanel();
	    topPanel.setPreferredSize(new Dimension(200, 120));
	    topPanel.setOpaque(true);
	    topPanel.setLayout(new GridLayout(3, 1));

	    JLabel label = new JLabel("Maze " + Integer.toString(row) + " x " +  Integer.toString(col));
	    Font font_used = new Font("Consolas", Font.PLAIN, 20);
	   
	    JLabel ket = new JLabel("1 s = 1 s");
	    ket.setHorizontalAlignment(JLabel.CENTER);
	    ket.setPreferredSize(new Dimension(200,100));
	    ket.setFont(new Font("Consolas", Font.PLAIN, 16));
	    ket.setForeground(Color.GREEN);
	    ket.setBackground(Color.black);
	    ket.setOpaque(true);
	    
	    label.setHorizontalAlignment(JLabel.CENTER);
	    label.setPreferredSize(new Dimension(200,100));
	    label.setFont(font_used);
	    label.setForeground(Color.GREEN);
	    label.setBackground(Color.black);
	    label.setOpaque(true);

	    
	    JPanel buttonsJPanel = new JPanel();
	    buttonsJPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
	    buttonsJPanel.setPreferredSize(new Dimension(200, 30));
	    buttonsJPanel.setOpaque(true);
	    buttonsJPanel.setForeground(Color.GREEN);
	    buttonsJPanel.setBackground(Color.black);
	    
	    
	    
	    JButton Dbutton = new JButton("Dijkstra");
	    Dbutton.setOpaque(true);
	    Dbutton.setFocusable(false);
	    Dbutton.setFont(font_used);
	    Dbutton.setSize(50, 25);
	    Dbutton.addActionListener(this);
	   
	    
	    dButton = Dbutton;

	    JButton Abutton = new JButton("A*");
	    Abutton.setOpaque(true);
	    Abutton.setFocusable(false);
	    Abutton.setFont(font_used);
	    Abutton.setSize(50, 25);
	    Abutton.addActionListener(this);

	    aButton = Abutton;
	    
	    JButton Cbutton = new JButton("Clear");
	    Cbutton.setOpaque(true);
	    Cbutton.setFocusable(false);
	    Cbutton.setFont(font_used);
	    Cbutton.setSize(50, 25);
	    Cbutton.addActionListener(this);
	    
	    cButton = Cbutton;

	    buttonsJPanel.add(Cbutton);
	    buttonsJPanel.add(Abutton);
	    buttonsJPanel.add(Dbutton);
	    
	    topPanel.add(label);
	    topPanel.add(buttonsJPanel);
	    topPanel.add(ket);
	    
	    
	    MazePanel panel = new MazePanel(mazeMaker);

	    panel.setBounds(0, 0, (int)(factor*col), 650);
	    panel.setBackground(Color.black);
	    panel.setOpaque(true);
	    
	    mazePanel = panel;

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
	  

	    this.add(topPanel, BorderLayout.NORTH);
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


	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == dButton) {
			MyLinearList<Integer> path = dijkstraReport.getPath();
			int lengths = path.length;
			
			int delay = Math.max(10,
	                (int)(dijkstraReport.getTime()*Convertion / dijkstraReport.getNodeVisited()));

			mazePanel.setPath(path);
			
			Timer timer = new Timer(delay, null);

	        timer.addActionListener(new ActionListener() {
	            int step = 0;

	            @Override
	            public void actionPerformed(ActionEvent ev) {
	                mazePanel.redraw(step);
	                step++;

	                if (step > lengths) {
	                    timer.stop();
	                }
	            }
	        });

	        timer.start();
		}
		
		if (e.getSource() == aButton) {
			
			MyLinearList<Integer> path = aStarReport.getPath();
			int lengths = path.length;
			
			int delay = Math.max(10,
					(int)(aStarReport.getTime()*Convertion / aStarReport.getNodeVisited()));
			
			mazePanel.setPath(path);
			
			Timer timer = new Timer(delay, null);
			
			timer.addActionListener(new ActionListener() {
				int step = 0;
				
				@Override
				public void actionPerformed(ActionEvent ev) {
					mazePanel.redraw(step);
					step++;
					
					if (step > lengths) {
						timer.stop();
					}
				}
			});
			
			timer.start();
		}
		
		if (e.getSource() == cButton) {
			mazePanel.redraw(0);
		}
	}



	public Report<Integer> getDijkstraReport() {
		return dijkstraReport;
	}



	public Report<Integer> getaStarReport() {
		return aStarReport;
	}
}
