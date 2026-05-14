package graph;

/* 
 * Struktur data Graph dengan bobot pada setiap edge
 * sources: https://www.lavivienpost.net/weighted-graph-as-adjacency-list/  
 * 
 */

import java.util.*;

class Edge<T> { 
	private T neighbor; //connected vertex
	private int weight; //weight
	
	//Constructor, Time O(1) Space O(1)
	public Edge(T v, int w) {
		this.neighbor = v; 
		this.weight = w;
	}

	public void setNeighbor(T neighbor) {
		this.neighbor = neighbor;
	}
	public T getNeighbor() {
		return neighbor;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public int getWeight() {
		return weight;
	}
	
	//Time O(1) Space O(1)
	@Override
	public String toString() {
		return "(" + neighbor + "," + weight + ")";
	}
}

public class Graph<T> { 
    //Map<T, LinkedList<Edge<T>>> adj;
	private Map<T, MyLinearList<Edge<T>>> adj;
	boolean directed;
	private int vertex_length;
	private int edge_length;
	
	public int getVLength(){
		return vertex_length;
	}

	public int getELength(){
		return edge_length;
	}

	//Constructor, Time O(1) Space O(1)
	public Graph (boolean type) { 
        adj = new HashMap<>();
		directed = type; // false: undirected, true: directed
		vertex_length = 0;
		edge_length = 0;
	}

    //Add edges including adding nodes, Time O(1) Space O(1)
	public void addEdge(T a, T b, int w) {
		if (!adj.containsKey(a)){
			vertex_length +=1;
			adj.put(a, new MyLinearList<>()); //add node
		}
		if (!adj.containsKey(b)){
			vertex_length ++;
			adj.put(b, new MyLinearList<>()); //add node
		}
		Edge<T> edge1 = new Edge<>(b, w);
		adj.get(a).pushQ(edge1);//add(edge1); //add edge
		if (!directed) { //undirected
			Edge<T> edge2 = new Edge<>(a, w);
			adj.get(b).pushQ(edge2);
		}			

	}


	public void deleteVertex(T src){
		if (!adj.containsKey(src)) return;

		adj.remove(src);
		vertex_length--;
		for (T key: adj.keySet()){
			deleteEdge(key, src);
		}
	}

	// }
	public void deleteEdge(T a1, T a2){
		if (!adj.containsKey(a1)) return;
		if (!adj.containsKey(a2)) return;

		MyLinearList<Edge<T>> a = adj.get(a1);

		Node<Edge<T>> root = a.head;

		while(root != null){
			Edge<T> neighbor = root.getData();
			if (a2 == neighbor.getNeighbor()){
				a.remove(neighbor);
				edge_length--;
			}
			root = root.getNext();
		}
	}



    //Print graph as hashmap, Time O(V+E), Space O(1)
	public void printGraph() {
		for (T key: adj.keySet()) {
			//System.out.println(key.toString() + " : " + adj.get(key).toString());
            System.out.print(key.toString() + " : ");
			MyLinearList<Edge<T>> thelist = adj.get(key);
			Node<Edge<T>> curr = thelist.head;
			while(curr != null) {
				System.out.print(curr.getData());
				curr = curr.getNext();
			}
			System.out.println();
		}
	}

	//DFS 
	public void DFS(T src) {
		Map<T,Integer> a  = new HashMap<>();
		dfs(a, src);
		System.out.println();
	}

	private void dfs(Map<T,Integer> a, T src){
		if (!adj.containsKey(src)) return;

		if (a.containsKey(src)) return;

		System.out.print(src + " ");

		MyLinearList<Edge<T>> aa = adj.get(src);

		a.put(src, 1);
		Node<Edge<T>> root = aa.head;
		while(root != null){
			dfs(a, root.getData().getNeighbor());
			root = root.getNext();
		}

	}

	//BFS
	public void BFS(T src) { 
		
		// make queue curr node
		MyLinearList<Edge<T>> a = adj.get(src);

		MyLinearList<MyLinearList<Edge<T>>> q = new MyLinearList<>();

		Map<T, Integer> visited = new HashMap<>();
		visited.put(src, 1);
		System.out.print(src + " ");

		q.pushQ(a);

		while(!q.isEmpty()){
			MyLinearList<Edge<T>> curr_node = q.remove();

			Node<Edge<T>> root = curr_node.head;
			
			while(root != null){
				if (!visited.containsKey(root.getData().getNeighbor())){
					T neighbor = root.getData().getNeighbor();
					System.out.print(neighbor + " ");
					visited.put(neighbor, 1);
					q.pushQ(adj.get(neighbor));
				}
				
				root = root.getNext();	
			}

		}

		System.out.println();

	}

	// first n second attempt
	// public void shortestPath(T start){
    //     MyLinearList<T> path = new MyLinearList();
	// 	Heap<Integer, Edge<T>> min_heap = new Heap<>(edge_length + vertex_length, true);

	// 	int sums = 0;
	// 	Map<T, Integer> visited = new HashMap<>();
	// 	Map<T, String> pathMap = new HashMap<>();
	// 	visited.put(start, 0);
	// 	pathMap.put(start, start.toString());

	// 	MyLinearList<Edge<T>> root = adj.get(start);
	// 	Node<Edge<T>> r = root.head;
	// 	while (r != null){
	// 		min_heap.insert(
	// 			(r.getData().getWeight()), 
	// 			r.getData());
	// 		visited.put(r.getData().getNeighbor(), r.getData().getWeight());
	// 		pathMap.put(r.getData().getNeighbor(), start.toString() + "-->" + r.getData().getNeighbor().toString());

	// 		r = r.getNext();
	// 	}
	// 	while (min_heap.size() != 0){
	// 		BTNode<Integer, Edge<T>> res = min_heap.removeFirst();
	// 		Edge<T> new_edge = res.getData();
	// 		Integer weights_edge = res.getKey();
	// 		String curr_path = pathMap.get(res.getData().getNeighbor());
			
	// 		MyLinearList<Edge<T>> curr = adj.get(new_edge.getNeighbor());
	// 		Node<Edge<T>> curr_Iter = curr.head;
	// 		while(curr_Iter != null){
	// 			if (!visited.containsKey(curr_Iter.getData().getNeighbor())){
	// 				min_heap.insert(weights_edge + curr_Iter.getData().getWeight(), curr_Iter.getData());
	// 				visited.put(curr_Iter.getData().getNeighbor(), weights_edge + curr_Iter.getData().getWeight());
	// 				pathMap.put(curr_Iter.getData().getNeighbor(), curr_path + "-->" + curr_Iter.getData().getNeighbor().toString());
	// 			}
	// 			else if (visited.get(curr_Iter.getData().getNeighbor()) > weights_edge + curr_Iter.getData().getWeight() ){
	// 				visited.put(curr_Iter.getData().getNeighbor(), weights_edge + curr_Iter.getData().getWeight());
	// 				pathMap.put(curr_Iter.getData().getNeighbor(), curr_path + "-->" + curr_Iter.getData().getNeighbor().toString());
	// 			}

	// 			curr_Iter = curr_Iter.getNext();
	// 		} 

	// 	}
	// 	for (T key : pathMap.keySet()) {
	// 		System.out.println(start + " --> " + key);
	// 		System.out.println("Cost: " + visited.get(key));
	// 		System.out.println("Path: " + pathMap.get(key));
	// 		System.out.println();
	// 	}

	// 	// return visited.get(end);
    // }

	// private MyLinearList<T> copy_LL(MyLinearList<T> src){
	// 	Node<T> s = src.head;
	// 	MyLinearList<T> dst = new MyLinearList<>();
	// 	while(s != null){
	// 		dst.pushQ(s.getData());
	// 		s = s.getNext();
	// 	}
	// 	return dst;
	// }


	// public MyLinearList<T> shortestPath1(T start, T end){
	// 	if (!adj.containsKey(start) || !adj.containsKey(end)) return null;
	// 	Heap<Integer, T> min_heap = new Heap<>(vertex_length, true);
	// 	MyLinearList<Edge<T>> s = adj.get(start);
	// 	Map<T, Integer> T_weights = new HashMap<>();
	// 	Map<T, MyLinearList<T>> Paths = new HashMap<>(); 
	// 	T_weights.put(start, 0);

	// 	MyLinearList<T> start_path = new MyLinearList<>(); 
	// 	start_path.pushQ(start);
	// 	Paths.put(start, start_path);

	// 	Node<Edge<T>> r = s.head;
	// 	// adding the first edge
	// 	while(r != null){
	// 		Edge<T> edge = r.getData(); 

	// 		// add to min heap 
	// 		min_heap.add(edge.getWeight(), edge.getNeighbor()); 

	// 		// add weights 
	// 		T_weights.put(edge.getNeighbor(), edge.getWeight());

	// 		// adding path to Linked List and to Map
	// 		MyLinearList<T> copy = copy_LL(start_path);
	// 		copy.pushQ(edge.getNeighbor());
	// 		Paths.put(edge.getNeighbor(), copy);

	// 		r = r.getNext();
	// 	}

	// 	while(min_heap.size() > 0) {
	// 		BTNode<Integer, T> res = min_heap.removeFirst();
	// 		T curr_node = res.getData();
	// 		if (curr_node == end){
	// 			break;
	// 		}

			
	// 		Integer curr_weights = res.getKey();
			
	// 		if (curr_weights > T_weights.get(curr_node)){
    // 			continue;
	// 		}
	// 		// paths
	// 		MyLinearList<T> curr_paths = Paths.get(curr_node);

	// 		MyLinearList<Edge<T>> branch = adj.get(curr_node);
			
	// 		Node<Edge<T>> branch_node = branch.head;
	// 		while (branch_node != null){
	// 			T curr_branch_neighbor = branch_node.getData().getNeighbor();
	// 			int curr_branch_edge_weights = branch_node.getData().getWeight();
				
	// 			if (T_weights.containsKey(curr_branch_neighbor) && T_weights.get(curr_branch_neighbor) > curr_weights + curr_branch_edge_weights){ 
	// 				T_weights.put(curr_branch_neighbor, curr_weights + curr_branch_edge_weights);
	// 				min_heap.add(curr_weights + curr_branch_edge_weights, curr_branch_neighbor);

	// 				// adding path
	// 				MyLinearList<T> copy = copy_LL(curr_paths);
	// 				copy.pushQ(curr_branch_neighbor);
	// 				Paths.put(curr_branch_neighbor, copy);

	// 			}
	// 			else if (!T_weights.containsKey(curr_branch_neighbor)){
	// 				T_weights.put(curr_branch_neighbor, curr_weights + curr_branch_edge_weights);
	// 				min_heap.add(curr_weights + curr_branch_edge_weights, curr_branch_neighbor);
	// 				// adding path
	// 				MyLinearList<T> copy = copy_LL(curr_paths);
	// 				copy.pushQ(curr_branch_neighbor);
	// 				Paths.put(curr_branch_neighbor, copy);
	// 			}
	// 			branch_node = branch_node.getNext();
	// 		}
	// 	}


    //     return Paths.get(end);
    // }

	public BTNode<Integer,MyLinearList<T>> dijkstra(T src, T target){
		
		// map distance, such that when its unreacable, we know
		Map<T, Integer> distance = new HashMap<>();

		// writing the path history
		Map<T, T> path = new HashMap<>();
		
		// make min priority queue
		Heap<Integer, T> PQ = new Heap<>(vertex_length, true);
		
		
		distance.put(src, 0);
		PQ.add(0, src);

		while(PQ.size() > 0){
			BTNode<Integer, T> top = PQ.removeFirst();
			Integer curr_dis = top.getKey();
			T curr_vertex = top.getData();

			// if the distance is more than we need, it means there exist 
			// another path that has less cost than the current path 
			if (curr_dis > distance.get(curr_vertex)){
				continue;
			}

			// looping through the edges and verteces
			Node<Edge<T>> root = adj.get(curr_vertex).head;
			while (root != null){
				Edge<T> edge = root.getData();
				T n_vertex = edge.getNeighbor();
				Integer n_weights = edge.getWeight();

				// we have 2 assumsion
				// 1. assume n_vertex is not in map, thus has curr length of inf, 
				// thus we update the weights as its now reachable 
				// 2. assume n_vertex is in map, where n_vertex has finite length and is greater than curr_dis + n_weights 
				// this can be achived effectively by using an or
				Integer new_distance = curr_dis + n_weights;
				if (
					!distance.containsKey(n_vertex) ||
					distance.get(n_vertex) > new_distance
				){
					// update distance
					distance.put(n_vertex, new_distance);
					// update path
					path.put(n_vertex, curr_vertex);
					// insert to PQ
					PQ.add(new_distance, n_vertex);
				}
				root = root.getNext();
			}
		}

		// backtracking
		MyLinearList<T> return_path = new MyLinearList<>();
		BTNode<Integer, MyLinearList<T>> ret;
		T curr = target; 
		if (path.containsKey(curr)){
			while (path.containsKey(curr)){
				T next = path.get(curr);
				return_path.pushS(next);
				curr = next;
			}
			ret = new BTNode<>(distance.get(target), return_path);
			return ret;
		}
		
		// target is unreachable
		ret = new BTNode(Integer.MAX_VALUE, return_path);
		return ret;
	}

}


