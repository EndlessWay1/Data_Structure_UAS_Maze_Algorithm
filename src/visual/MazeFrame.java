package visual;

import java.awt.FlowLayout;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;

import graph.Graph;

public class MazeFrame<T> extends JFrame{
	private Graph<T> graph;
	
	public MazeFrame() {
		// TODO Make JPanel
		// TODO Make 3d visual
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(500, 500);
		
		JLayeredPane layered_pane = new JLayeredPane();
		layered_pane.setBounds(0, 0, 500, 500);
		
		this.add(layered_pane);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setLayout(new FlowLayout());
	}

	public MazeFrame(String title) {
		this();
		this.setTitle(title);
	}
	
	public MazeFrame(Graph<T> g) {
		this();
		graph = g;
	}

	public MazeFrame(String title, Graph<T> g) {
		this(title);
		graph = g;
	}

	public Graph<T> getGraph() {
		return graph;
	}

	public void setGraph(Graph<T> graph) {
		this.graph = graph;
	}
	
}
