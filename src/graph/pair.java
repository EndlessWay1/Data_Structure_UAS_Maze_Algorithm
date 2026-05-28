package graph;

public class pair<T,V> {
	public T first;
	public V second;
	public pair(T pair1, V pair2){
		this.first = pair1;
		this.second = pair2;
	}

	@Override
	public String toString(){
		return "(" + first.toString() + ", " + second.toString() + ")";
	}

	// @Override
	public boolean equals(pair<T,V> a){
		return a.first.equals(this.first) && a.second.equals(this.second);
	}
}