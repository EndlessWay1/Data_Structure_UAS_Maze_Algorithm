package graph;

public class Report<T> {
	private int nodeVisited; 
	private int cost;
	private long time;
	private MyLinearList<T> path;
	
	public Report(int nodeVisited, int cost, long time, MyLinearList<T> path) {
		// TODO Auto-generated constructor stub
		this.nodeVisited = nodeVisited;
		this.cost = cost;
		this.time = time;
		this.path = path;
	}

	public int getNodeVisited() {
		return nodeVisited;
	}

	public int getCost() {
		return cost;
	}

	public long getTime() {
		return time;
	}

	public MyLinearList<T> getPath() {
		return path;
	}
}
